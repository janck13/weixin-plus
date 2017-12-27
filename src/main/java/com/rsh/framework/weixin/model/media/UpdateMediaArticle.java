package com.rsh.framework.weixin.model.media;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 11:19
 */
public class UpdateMediaArticle {

    @JSONField(name = "mediaId")
    private String mediaId; // 图文消息的id
    @JSONField(name = "index")
    private String index; // 更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
    @JSONField(name = "articles")
    private MediaArticle articles;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public MediaArticle getArticles() {
        return articles;
    }

    public void setArticles(MediaArticle articles) {
        this.articles = articles;
    }
}
