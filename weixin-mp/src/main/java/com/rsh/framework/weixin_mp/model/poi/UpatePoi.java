package com.rsh.framework.weixin_mp.model.poi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created By Rsh
 * 修改门店信息
 *
 * @Description
 * @Date: 2018/1/24
 * @Time: 11:13
 */
public class UpatePoi implements Serializable {
    private static final long serialVersionUID = 6544199898183212278L;

    //base_info
    @JSONField(name = "poi_id ")
    private String poiId; // 微信内部门店id
    @JSONField(name = "sid")
    private String sid; // 商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
    @JSONField(name = "telephone")
    private String telephone; // 门店的电话（纯数字，区号、分机号均由“-”隔开）
    @JSONField(name = "photo_list")
    private List<PoiPhoto> photoList; // 图片列表
    @JSONField(name = "recommend")
    private String recommend; // 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容 200字以内
    @JSONField(name = "special")
    private String special; // 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
    @JSONField(name = "introduction")
    private String introduction; // 商户简介，主要介绍商户信息等 300字以内
    @JSONField(name = "open_time")
    private String openTime; // 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
    @JSONField(name = "avg_price")
    private Integer avgPrice; // 人均价格，大于0 的整数

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<PoiPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PoiPhoto> photoList) {
        this.photoList = photoList;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    // 图片
    public class PoiPhoto {
        @JSONField(name = "photo_url")
        private String photoUrl; // 图片url

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }

    @Override
    public String toString() {
        return "UpatePoi{" +
                "poiId='" + poiId + '\'' +
                ", sid='" + sid + '\'' +
                ", telephone='" + telephone + '\'' +
                ", photoList=" + photoList +
                ", recommend='" + recommend + '\'' +
                ", special='" + special + '\'' +
                ", introduction='" + introduction + '\'' +
                ", openTime='" + openTime + '\'' +
                ", avgPrice=" + avgPrice +
                '}';
    }

    public String getJson() {
        return JSON.toJSONString(this);
    }

}
