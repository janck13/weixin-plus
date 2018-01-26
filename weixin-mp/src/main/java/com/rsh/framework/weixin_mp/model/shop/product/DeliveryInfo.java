package com.rsh.framework.weixin_mp.model.shop.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 * 运费信息
 *
 * @Description
 * @Date: 2018/1/26
 * @Time: 13:34
 */
public class DeliveryInfo {

    @JSONField(name = "delivery_type")
    private Integer deliveryType; // 运费类型(0-使用下面express字段的默认模板, 1-使用template_id代表的邮费模板, 详见邮费模板相关API)
    @JSONField(name = "template_id")
    private String templateId; // 邮费模板ID
    @JSONField(name = "express")
    private List<Express> express;

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<Express> getExpress() {
        return express;
    }

    public void setExpress(List<Express> express) {
        this.express = express;
    }

    public class Express {
        @JSONField(name = "id")
        private String id; // 快递ID
        @JSONField(name = "price")
        private Integer price; // 运费(单位 : 分)

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }

}
