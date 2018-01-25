package com.rsh.framework.weixin_mp.model.poi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 * 门店小程序
 *
 * @Description
 * @Date: 2018/1/24
 * @Time: 17:48
 */
public class Merchant {

    @JSONField(name = "first_catid")
    private String firstCatid;
    @JSONField(name = "second_catid")
    private String secondCatid;
    @JSONField(name = "qualification_list")
    private List<String> qualificationList;
    @JSONField(name = "headimg_mediaid")
    private String headimgMediaid;
    @JSONField(name = "nickname")
    private String nickname;
    @JSONField(name = "intro")
    private String intro;
    @JSONField(name = "org_code")
    private String orgCode;
    @JSONField(name = "other_files")
    private List<String> otherFiles;

    public String getFirstCatid() {
        return firstCatid;
    }

    public void setFirstCatid(String firstCatid) {
        this.firstCatid = firstCatid;
    }

    public String getSecondCatid() {
        return secondCatid;
    }

    public void setSecondCatid(String secondCatid) {
        this.secondCatid = secondCatid;
    }

    public List<String> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<String> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getHeadimgMediaid() {
        return headimgMediaid;
    }

    public void setHeadimgMediaid(String headimgMediaid) {
        this.headimgMediaid = headimgMediaid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getOtherFiles() {
        return otherFiles;
    }

    public void setOtherFiles(List<String> otherFiles) {
        this.otherFiles = otherFiles;
    }

    @Override
    public String toString() {
        return "PoiMiniprogram{" +
                "firstCatid='" + firstCatid + '\'' +
                ", secondCatid='" + secondCatid + '\'' +
                ", qualificationList=" + qualificationList +
                ", headimgMediaid='" + headimgMediaid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", intro='" + intro + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", otherFiles=" + otherFiles +
                '}';
    }

    public String getJson() {
        return JSON.toJSONString(this);
    }
}
