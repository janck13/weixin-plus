package com.rsh.framework.weixin_mp.model.media;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 图文素材
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 17:24
 */
public class MediaArticle implements Serializable {
    private static final long serialVersionUID = 8535567624230733658L;

    @JSONField(name = "title")
    private String title; // 标题
    @JSONField(name = "thumb_media_id")
    private String thumbMediaId; // 图文消息的封面图片素材id（必须是永久mediaID）
    @JSONField(name = "author")
    private String author; // 作者
    @JSONField(name = "digest")
    private String digest; // 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字。
    @JSONField(name = "show_cover_pic")
    private int showCoverPic; // 是否显示封面，0为false，即不显示，1为true，即显示
    @JSONField(name = "content")
    private String content; // 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源 "上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
    @JSONField(name = "content_source_url")
    private String contentSourceUrl; // 图文消息的原文地址，即点击“阅读原文”后的URL

    // 以下字段为【图文消息评论能力管理-新增永久素材】新增字段
    @JSONField(name = "need_open_comment")
    private Integer needOpenComment; // 是否打开评论，0不打开，1打开
    @JSONField(name = "only_fans_can_comment")
    private Integer onlyFansCanComment; // 是否粉丝才可评论，0所有人可评论，1粉丝才可评论

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(int showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public Integer getNeedOpenComment() {
        return needOpenComment;
    }

    public void setNeedOpenComment(Integer needOpenComment) {
        this.needOpenComment = needOpenComment;
    }

    public Integer getOnlyFansCanComment() {
        return onlyFansCanComment;
    }

    public void setOnlyFansCanComment(Integer onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment;
    }
}
