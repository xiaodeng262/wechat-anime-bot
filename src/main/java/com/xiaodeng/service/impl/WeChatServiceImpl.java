package com.xiaodeng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.xiaodeng.entity.BaseMsg;
import com.xiaodeng.entity.NewsMsg;
import com.xiaodeng.entity.TextMsg;
import com.xiaodeng.service.WeChatService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 09:34
 **/
@Service
public class WeChatServiceImpl implements WeChatService {
    @Override
    public Map<String, String> parseRequest(InputStream is) {
        Map<String, String> map = new HashMap<String, String>();
        //1.通过io流得到文档对象
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //2.通过文档对象得到根节点对象
        Element root = document.getRootElement();
        //3.通过根节点对象获取所有子节点对象
        List<Element> elements = root.elements();
        //4.将所有节点放入map
        for (Element element : elements) {
            map.put(element.getName(), element.getStringValue());
        }
        return map;


    }

    @Override
    public String getRepose(Map<String, String> requestMap) {
        BaseMsg msg = null;
        // 根据用户发送消息的类型,做不同的处理
        String msgType = requestMap.get("MsgType");
        switch (msgType) {
            case "text":
                msg = dealTextMsg(requestMap);
                break;
            case "image":
                msg = dealImageMsg(requestMap);
                break;
            default:
                break;
        }
        // 将处理结果转化成xml的字符串返回
        if (null != msg) {
            return beanToXml(msg);
        }
        return null;

    }


    /**
     * 将回复的消息类转成xml字符串
     *
     * @param msg
     * @return
     */
    private static String beanToXml(BaseMsg msg) {
        XStream stream = new XStream();
        stream.processAnnotations(TextMsg.class);
        stream.processAnnotations(NewsMsg.class);
        String xml = stream.toXML(msg);
        return xml;
    }


    /**
     * 当用户发送是文本消息的处理逻辑
     *
     * @return
     */
    private static BaseMsg dealTextMsg(Map<String, String> requestMap) {
        // 获取用户发送的消息内容
        String msg = requestMap.get("Content");
        return new TextMsg(requestMap, new Date(System.currentTimeMillis()) + "你好");
    }


    /**
     * 当用户发送是图片消息的处理逻辑
     *
     * @param requestMap
     * @return
     */
    private static BaseMsg dealImageMsg(Map<String, String> requestMap) {
        String picUrl = requestMap.get("PicUrl");
        return new TextMsg(requestMap, Recognize("动漫模型", false, picUrl));
    }

    static Map<String, String> modelList = new HashMap<String, String>() {{
        put("动漫模型", "anime");
        put("高准确率公测动漫模型", "anime_model_lovelive");
        put("GalGame模型", "game");
        put("高准确率公测GalGame模型", "game_model_kirakira");
    }};

    private static String downloadImage(String imageUrl) {
        // 指定下载后的本地文件路径
        //String localPath = "/Users/yu/Desktop/temp/temp_image.png";
        String localPath = "/root/temp/temp_image.png";
        try {
            HttpUtil.downloadFile(imageUrl, FileUtil.file(localPath));
            return localPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String Recognize(String modelName, boolean aiDetect, String imageUrl) {
        if (modelList.get(modelName) == null) {
            return "模型不存在";
        }

        String localImagePath = downloadImage(imageUrl);
        if (localImagePath == null) {
            return "图片下载失败";
        }

        int flagAiDetect = aiDetect ? 1 : 0;
        String url = String.format("https://aiapiv2.animedb.cn/ai/api/detect?force_one=0&model=%s&ai_detect=%s",
                modelName, flagAiDetect);

        HttpResponse response = HttpRequest.post(url)
                .form("image", FileUtil.file(localImagePath))
                .execute();

        // 删除临时文件
        FileUtil.del(localImagePath);

        if (response.isOk()) {
            JSONObject jsonObject = JSON.parseObject(response.body());
            JSONArray dataArray = jsonObject.getJSONArray("data");
            if (dataArray != null && !dataArray.isEmpty()) {
                JSONObject dataItem = dataArray.getJSONObject(0);
                String name = dataItem.getString("name");
                String cartoonname = dataItem.getString("cartoonname");
                return "识别成功！角色:" + name + ",番名:" + cartoonname;
            }
            return "未查到!";
        } else {
            return "请求失败，状态码：" + response.getStatus();
        }
    }


}
