package com.rsh.framework.weixin_mp.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 15:15
 */
public class MenuItem {

    @JSONField(name = "name")
    private String name;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "key")
    private String key; // 菜单KEY值，用于消息接口推送，不超过128字节，click等点击类型必须
    @JSONField(name = "url")
    private String url; // 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url。view、miniprogram类型必须
    @JSONField(name = "media_id")
    private String mediaId; // 调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型必须
    @JSONField(name = "appid")
    private String appid; // 小程序的appid（仅认证公众号可配置），miniprogram类型必须
    @JSONField(name = "pagepath")
    private String pagepath; // 小程序的页面路径，miniprogram类型必须
    @JSONField(name = "sub_button")
    private List<MenuItem> subMenu; // 二级菜单数组，个数应为1~5个

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
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

    public List<MenuItem> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuItem> subMenu) {
        this.subMenu = subMenu;
    }
}
