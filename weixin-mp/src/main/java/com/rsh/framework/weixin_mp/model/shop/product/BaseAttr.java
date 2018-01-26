package com.rsh.framework.weixin_mp.model.shop.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Map;

/**
 * Created By Rsh
 * 商品基本属性
 *
 * @Description
 * @Date: 2018/1/26
 * @Time: 11:51
 */
public class BaseAttr {

    @JSONField(name = "name")
    private String name; // 商品名称
    @JSONField(name = "category_id")
    private List<String> categoryId; // 商品分类id
    @JSONField(name = "main_img")
    private String mainImg; // 商品主图(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
    @JSONField(name = "img")
    private List<String> img; // 商品图片列表(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
    @JSONField(name = "detail")
    private List<Map<String, String>> detail; // 商品详情列表
    @JSONField(name = "property")
    private List<Property> property; // 商品属性列表
    @JSONField(name = "sku_info")
    private List<SkuInfo> skuInfo; // 商品sku定义
    @JSONField(name = "buy_limit")
    private Integer buyLimit; // 用户商品限购数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<String> categoryId) {
        this.categoryId = categoryId;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<Map<String, String>> getDetail() {
        return detail;
    }

    public void setDetail(List<Map<String, String>> detail) {
        this.detail = detail;
    }

    public List<Property> getProperty() {
        return property;
    }

    public void setProperty(List<Property> property) {
        this.property = property;
    }

    public List<SkuInfo> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<SkuInfo> skuInfo) {
        this.skuInfo = skuInfo;
    }

    public Integer getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Integer buyLimit) {
        this.buyLimit = buyLimit;
    }
}
