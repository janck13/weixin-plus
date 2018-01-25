package com.rsh.framework.weixin_mp.model.poi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created By Rsh
 * 微信门店数据
 *
 * @Description
 * @Date: 2018/1/24
 * @Time: 11:13
 */
public class Poi implements Serializable {
    private static final long serialVersionUID = 6544199898183212278L;

    //base_info
    //门店基础信息字段（重要）---------------------------------------------------------------------
    @JSONField(name = "sid")
    private String sid; // 商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
    @JSONField(name = "business_name")
    private String businessName; // 门店名称（仅为商户名，如：国美、麦当劳，不应包含地区、地址、分店名等信息，错误示例：北京国美） 不能为空，15个汉字或30个英文字符内
    @JSONField(name = "branch_name")
    private String branchName; // 分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店） 20 个字 以内
    @JSONField(name = "province")
    private String province; // 门店所在的省份（直辖市填城市名,如：北京 市） 10个字 以内
    @JSONField(name = "city")
    private String city; // 门店所在的城市 10个字 以内
    @JSONField(name = "district")
    private String district; // 门店所在地区 10个字 以内
    @JSONField(name = "address")
    private String address; // 门店所在的详细街道地址（不要填写省市信息）
    @JSONField(name = "telephone")
    private String telephone; // 门店的电话（纯数字，区号、分机号均由“-”隔开）
    @JSONField(name = "categories")
    private List<String> categories; // 门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
    @JSONField(name = "offset_type")
    private Integer offsetType; // 坐标类型： 1 为火星坐标 2 为sogou经纬度 3 为百度经纬度 4 为mapbar经纬度 5 为GPS坐标 6 为sogou墨卡托坐标 注：高德经纬度无需转换可直接使用，高德地图采用的是火星坐标
    @JSONField(name = "longitude")
    private String longitude; // 门店所在地理位置的经度
    @JSONField(name = "latitude")
    private String latitude; // 门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）

    //门店服务信息字段，均为非必须 ---------------------------------------------------------------------
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getOffsetType() {
        return offsetType;
    }

    public void setOffsetType(Integer offsetType) {
        this.offsetType = offsetType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
        return "Poi{" +
                "sid='" + sid + '\'' +
                ", businessName='" + businessName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", categories=" + categories +
                ", offsetType=" + offsetType +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", photoList=" + photoList +
                ", recommend='" + recommend + '\'' +
                ", special='" + special + '\'' +
                ", introduction='" + introduction + '\'' +
                ", openTime='" + openTime + '\'' +
                ", avgPrice=" + avgPrice +
                '}';
    }

    public String getJson(){
        return JSON.toJSONString(this);
    }

}
