package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:17
 */
public class NewsItem {

    @JSONField(name = "articles")
    private List<NewsItemArticle> articles;

    public List<NewsItemArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsItemArticle> articles) {
        this.articles = articles;
    }
}
