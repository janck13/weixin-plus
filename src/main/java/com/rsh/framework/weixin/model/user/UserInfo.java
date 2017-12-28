package com.rsh.framework.weixin.model.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 关注者用户信息
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 17:06
 */
public class UserInfo {

    private String subscribe;
    private String openid; // 用户的唯一标识
    private String nickname; // 用户昵称
    private Integer sex; // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String language; // 用户的语言，简体中文为zh_CN
    private String country; // 国家，如中国为CN
    private String province; // 用户个人资料填写的省份
    private String city; // 普通用户个人资料填写的城市
    private String headimgurl; // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    @JSONField(name = "subscribe_time")
    private Long subscribeTime; // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String unionid; // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String remark; // 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private Integer groupid; // 用户所在的分组ID（兼容旧的用户分组接口）
    @JSONField(name = "tagid_list")
    private List<Integer> tagidList; // 用户被打上的标签ID列表

    private String json;

    public UserInfo() {

    }

    public UserInfo(String json) {
        this.json = json;
        try {
            JSONObject jb = JSON.parseObject(json);
            this.subscribe = jb.getString("subscribe");
            this.openid = jb.getString("openid");
            this.nickname = jb.getString("nickname");
            this.sex = jb.getInteger("sex");
            this.language = jb.getString("language");
            this.country = jb.getString("country");
            this.province = jb.getString("province");
            this.city = jb.getString("city");
            this.headimgurl = jb.getString("headimgurl");
            this.subscribeTime = jb.getLongValue("subscribe_time");
            this.unionid = jb.getString("unionid");
            this.remark = jb.getString("remark");
            this.groupid = jb.getInteger("groupid");
            JSONArray privileges = jb.getJSONArray("tagid_list");
            this.tagidList = privileges.toJavaList(Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public List<Integer> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Integer> tagidList) {
        this.tagidList = tagidList;
    }
}
