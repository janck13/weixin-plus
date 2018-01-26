package com.rsh.framework.weixin_mp.model.shop.product;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 * 商品其他属性
 *
 * @Description
 * @Date: 2018/1/26
 * @Time: 12:06
 */
public class Attrext {

    @JSONField(name = "isPostFree")
    private Integer isPostFree; // 是否包邮(0-否, 1-是), 如果包邮delivery_info字段可省略
    @JSONField(name = "isHasReceipt")
    private Integer isHasReceipt; // 是否提供发票(0-否, 1-是)
    @JSONField(name = "isUnderGuaranty")
    private Integer isUnderGuaranty; // 是否保修(0-否, 1-是)
    @JSONField(name = "isSupportReplace")
    private Integer isSupportReplace; // 是否支持退换货(0-否, 1-是)
    @JSONField(name = "location")
    private Location location; // 商品所在地地址

    public Integer getIsPostFree() {
        return isPostFree;
    }

    public void setIsPostFree(Integer isPostFree) {
        this.isPostFree = isPostFree;
    }

    public Integer getIsHasReceipt() {
        return isHasReceipt;
    }

    public void setIsHasReceipt(Integer isHasReceipt) {
        this.isHasReceipt = isHasReceipt;
    }

    public Integer getIsUnderGuaranty() {
        return isUnderGuaranty;
    }

    public void setIsUnderGuaranty(Integer isUnderGuaranty) {
        this.isUnderGuaranty = isUnderGuaranty;
    }

    public Integer getIsSupportReplace() {
        return isSupportReplace;
    }

    public void setIsSupportReplace(Integer isSupportReplace) {
        this.isSupportReplace = isSupportReplace;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
