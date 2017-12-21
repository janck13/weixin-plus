package com.rsh.framework.weixin.model.msg.out;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:17
 */
public class NewsItemArticle {

    @JSONField(name = "title")
    private String title;
    @JSONField(name = "description")
    private String description;
    @JSONField(name = "url")
    private String url;
    @JSONField(name = "picurl")
    private String picurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
