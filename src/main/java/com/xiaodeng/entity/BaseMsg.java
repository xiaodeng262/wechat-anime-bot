package com.xiaodeng.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 消息类型实体类父类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMsg {
    /*
    @XStreamAlias使用了xstream的依赖，用来生成xml格式
     */
    //接收方的账号(收到的openid)
    @XStreamAlias("ToUserName")
    private String toUserName;
    //开发者的微信号
    @XStreamAlias("FromUserName")
    private String fromUserName;
    //消息创建时间
    @XStreamAlias("CreateTime")
    private String createTime;
    //消息类型
    @XStreamAlias("MsgType")
    private String msgType;

    //可直接设置所有消息的发送、接收账号以及创建时间（这些属性所有类型消息都一样，不一样的属性会单独设置）
    public BaseMsg(Map<String, String> requestMap) {
        this.toUserName = requestMap.get("FromUserName");
        this.fromUserName = requestMap.get("ToUserName");
        this.createTime = requestMap.get("CreateTime");
    }


}