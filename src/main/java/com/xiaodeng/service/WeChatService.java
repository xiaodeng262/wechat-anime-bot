package com.xiaodeng.service;

import java.io.InputStream;
import java.util.Map;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 09:33
 **/
public interface WeChatService {

    Map<String, String> parseRequest(InputStream inputStream);

    String getRepose(Map<String, String> requestMap);
}
