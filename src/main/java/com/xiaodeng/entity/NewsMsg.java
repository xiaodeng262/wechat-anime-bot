package com.xiaodeng.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/*
图文消息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class NewsMsg extends BaseMsg {

    //图文消息个数
    @XStreamAlias("ArticleCount")
    private String articleCount;
    @XStreamAlias("Articles")
    private List<Article> articles;

    public NewsMsg(Map<String, String> requestMap, List<Article> articles) {
        super(requestMap);
        this.setMsgType("news");
        this.articles = articles;
        this.setArticleCount(this.articles.size() + "");
    }


}