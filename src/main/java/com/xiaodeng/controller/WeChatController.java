package com.xiaodeng.controller;

import com.xiaodeng.service.WeChatService;
import com.xiaodeng.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 09:18
 **/
@Controller
@RequestMapping("/wechat")
public class WeChatController {
    private static final Logger log = LoggerFactory.getLogger(WeChatController.class);


    @Resource
    private WeChatService weChatService;

    @RequestMapping(value = "security", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam(value = "signature", required = true) String signature,
                      @RequestParam(value = "timestamp", required = true) String timestamp,
                      @RequestParam(value = "nonce", required = true) String nonce,
                      @RequestParam(value = "echostr", required = true) String echostr
    ) {
        log.info(".....");
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                log.info("这里存在非法请求！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * post方法用于接收微信服务端消息
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "security", method = RequestMethod.POST)
    public void DoPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //将请求中的xml参数转成map
        Map<String, String> map = weChatService.parseRequest(req.getInputStream());
        log.info("{}", map);
        //转换为xml格式回复消息
        String textMsg = weChatService.getRepose(map);
        resp.getWriter().print(textMsg);
    }

}
