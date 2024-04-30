package com.xiaodeng.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图文类消息子节点，对应item节点
 *
 * @author yu
 */
@XStreamAlias("item")
public class Article {
    //图文消息标题
    @XStreamAlias("Title")
    private String title;
    //图文消息描述
    @XStreamAlias("Description")
    private String description;
    //图片链接
    @XStreamAlias("PicUrl")
    private String picUrl;
    //点击图文消息跳转链接
    @XStreamAlias("Url")
    private String url;

}