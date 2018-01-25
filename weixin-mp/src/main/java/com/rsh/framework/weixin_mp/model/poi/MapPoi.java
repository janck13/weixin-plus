package com.rsh.framework.weixin_mp.model.poi;

import com.alibaba.fastjson.JSON;

/**
 * Created By Rsh
 * 腾讯地图中创建门店信息
 *
 * @Description
 * @Date: 2018/1/25
 * @Time: 10:18
 */
public class MapPoi {

    private String name; // 名字
    private String longitude; // 经度
    private String latitude; // 纬度
    private String province; // 省份
    private String city; // 城市
    private String district; // 区
    private String address; // 详细地址
    private String category; // 类目，比如美食:中餐厅
    private String telephone; // 电话，可多个，使用英文分号间隔 010-6666666-111; 010-6666666; 010- 6666666-222
    private String photo; // 门店图片url
    private String license; // 营业执照url
    private String introduct; // 介绍
    private String districtid; // 腾讯地图拉取省市区信息接口返回的id
    private String poi_id; // 如果是迁移门店， 必须填 poi_id字段

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getPoi_id() {
        return poi_id;
    }

    public void setPoi_id(String poi_id) {
        this.poi_id = poi_id;
    }

    @Override
    public String toString() {
        return "MapPoi{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", category='" + category + '\'' +
                ", telephone='" + telephone + '\'' +
                ", photo='" + photo + '\'' +
                ", license='" + license + '\'' +
                ", introduct='" + introduct + '\'' +
                ", districtid='" + districtid + '\'' +
                ", poi_id='" + poi_id + '\'' +
                '}';
    }

    public String getJson() {
        return JSON.toJSONString(this);
    }
}
