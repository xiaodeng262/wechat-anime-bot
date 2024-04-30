package com.xiaodeng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaodeng.model.entity.anime.Anime;
import com.xiaodeng.service.AnimeTraceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 13:31
 **/
@Service
public class AnimeTraceServiceImpl implements AnimeTraceService {


    @Value("${anime.temp}")
    private String tempPath;

    @Value("{anime.modelName}")
    private String modelName;


    @Override
    public Anime getAnimeTrace(String imgUrl) {


        String localImagePath = downloadImage(imgUrl);
        if (localImagePath == null) {
            return null;
        }

        String url = String.format("https://aiapiv2.animedb.cn/ai/api/detect?model=%s&ai_detect=1&is_multi=0", modelName);

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
                return new Anime(name, cartoonname);
            }
            return null;
        } else {
            return null;
        }
    }


    /**
     * 将图片下载到本地临时文件
     *
     * @param imageUrl
     * @return
     */
    private String downloadImage(String imageUrl) {
        // 指定下载后的本地文件路径
        String localPath = tempPath + "temp_image.png";
        try {
            HttpUtil.downloadFile(imageUrl, FileUtil.file(localPath));
            return localPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
