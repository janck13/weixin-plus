package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:17
 */
public class MiniprogrampageItem {

    @JSONField(name = "title")
    private String title; // 小程序卡片的标题
    @JSONField(name = "appid")
    private String appid; // 小程序的appid，要求小程序的appid需要与公众号有关联关系
    @JSONField(name = "pagepath")
    private String pagepath; // 小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
    @JSONField(name = "thumb_media_id")
    private String thumbMediaId; // 小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
