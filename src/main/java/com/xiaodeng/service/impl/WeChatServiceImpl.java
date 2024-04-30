package com.xiaodeng.service.impl;

import com.thoughtworks.xstream.XStream;
import com.xiaodeng.model.entity.anime.Anime;
import com.xiaodeng.model.entity.wechat.BaseMsg;
import com.xiaodeng.model.entity.wechat.NewsMsg;
import com.xiaodeng.model.entity.wechat.TextMsg;
import com.xiaodeng.service.AnimeTraceService;
import com.xiaodeng.service.WeChatService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    AnimeTraceService animeTraceService;


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
                //msg = dealTextMsg(requestMap);
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
    private String beanToXml(BaseMsg msg) {
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
    private BaseMsg dealTextMsg(Map<String, String> requestMap) {
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
    private BaseMsg dealImageMsg(Map<String, String> requestMap) {
        String picUrl = requestMap.get("PicUrl");
        Anime animeTrace = animeTraceService.getAnimeTrace(picUrl);
        if (animeTrace == null) {
            return new TextMsg(requestMap, "查找失败，请检查图片是否清晰，另请确保您需要识别图片中角色的人脸的方向是正向的，不能倒向。");
        }
        String message = "角色:" + animeTrace.getName() + ", 番剧:《" + animeTrace.getCartoonName() + "》(信息只供参考，如不准确，可联系作者获得帮助)";
        return new TextMsg(requestMap, message);
    }


}
