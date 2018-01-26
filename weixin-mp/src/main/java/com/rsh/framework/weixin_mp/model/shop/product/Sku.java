package com.rsh.framework.weixin_mp.model.shop.product;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/26
 * @Time: 12:01
 */
public class Sku {

    @JSONField(name = "sku_id")
    private String skuId; // sku信息, 参照上述sku_table的定义;  格式 : "id1:vid1;id2:vid2"
    @JSONField(name = "ori_price")
    private Integer oriPrice; // sku原价(单位 : 分)
    @JSONField(name = "price")
    private Integer price; // sku微信价(单位 : 分, 微信价必须比原价小, 否则添加商品失败)
    @JSONField(name = "icon_url")
    private String iconUrl; // sku iconurl(图片需调用图片上传接口获得图片Url)
    @JSONField(name = "quantity")
    private Integer quantity; // sku库存
    @JSONField(name = "product_code")
    private String productCode; // 商家商品编码

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(Integer oriPrice) {
        this.oriPrice = oriPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
