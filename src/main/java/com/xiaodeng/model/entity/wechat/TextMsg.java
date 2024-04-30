package com.xiaodeng.model.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author yu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("xml")
public class TextMsg extends BaseMsg {

    //回复的文本内容
    @XStreamAlias("Content")
    private String content;

    public TextMsg(Map<String, String> requestMap, String content) {
        super(requestMap);
        this.setMsgType("text");
        this.content = content;
    }

}