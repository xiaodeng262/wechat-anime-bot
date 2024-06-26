package com.xiaodeng.service;

import java.io.InputStream;
import java.util.Map;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 09:33
 **/
public interface WeChatService {

    /**
     * 解析请求
     * @param inputStream
     * @return
     */
    Map<String, String> parseRequest(InputStream inputStream);

    /**
     * 获取响应
     * @param requestMap
     * @return
     */
    String getRepose(Map<String, String> requestMap);
}
