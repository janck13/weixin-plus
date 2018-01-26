package com.rsh.framework.weixin_mp.model.shop.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 * 商品
 *
 * @Description
 * @Date: 2018/1/25
 * @Time: 17:51
 */
public class Product {

    @JSONField(name = "product_id")
    private String productId; // 商品ID，修改商品时必填
    @JSONField(name = "product_base")
    private BaseAttr baseAttr; // 基本属性
    @JSONField(name = "sku_list")
    private List<Sku> skuList; // sku信息列表
    @JSONField(name = "attrext")
    private Attrext attrext; // 运费信息
    @JSONField(name = "delivery_info")
    private DeliveryInfo deliveryInfo; // 运费信息

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BaseAttr getBaseAttr() {
        return baseAttr;
    }

    public void setBaseAttr(BaseAttr baseAttr) {
        this.baseAttr = baseAttr;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public Attrext getAttrext() {
        return attrext;
    }

    public void setAttrext(Attrext attrext) {
        this.attrext = attrext;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getJson() {
        return JSON.toJSONString(this);
    }
}
