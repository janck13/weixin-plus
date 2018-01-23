package com.rsh.framework.weixin_component.model.mp.card;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.internal.NotNull;

/**
 * Created By Rsh
 * 卡商户资质(强制授权模式-子商户资质)
 * @Description
 * @Date: 2018/1/23
 * @Time: 14:19
 */
public class CardMerchantQualification {

    private String appid; // 子商户公众号的appid
    private String name; // 子商户商户名，用于显示在卡券券面
    @JSONField(name = "logo_meida_id")
    private String logoMeidaId; // 子商户logo，用于显示在子商户卡券的券面
    @JSONField(name = "business_license_media_id")
    private String businessLicenseMediaId; // 营业执照或个体工商户执照扫描件的media_id
    @JSONField(name = "operator_id_card_media_id")
    private String operatorIdCardMediaId; // 当子商户为个体工商户且无公章时，授权函须签名，并额外提交该个体工商户经营者身份证扫描件的media_id
    @JSONField(name = "agreement_file_media_id")
    private String agreementFileMediaId; // 子商户与第三方签署的代理授权函的media_id
    @JSONField(name = "primary_category_id")
    private String primaryCategoryId; // 一级类目id
    @JSONField(name = "secondary_category_id")
    private String secondaryCategoryId; // 二级类目id

    @NotNull
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getLogoMeidaId() {
        return logoMeidaId;
    }

    public void setLogoMeidaId(String logoMeidaId) {
        this.logoMeidaId = logoMeidaId;
    }

    @NotNull
    public String getBusinessLicenseMediaId() {
        return businessLicenseMediaId;
    }

    public void setBusinessLicenseMediaId(String businessLicenseMediaId) {
        this.businessLicenseMediaId = businessLicenseMediaId;
    }

    @NotNull
    public String getOperatorIdCardMediaId() {
        return operatorIdCardMediaId;
    }

    public void setOperatorIdCardMediaId(String operatorIdCardMediaId) {
        this.operatorIdCardMediaId = operatorIdCardMediaId;
    }

    @NotNull
    public String getAgreementFileMediaId() {
        return agreementFileMediaId;
    }

    public void setAgreementFileMediaId(String agreementFileMediaId) {
        this.agreementFileMediaId = agreementFileMediaId;
    }

    @NotNull
    public String getPrimaryCategoryId() {
        return primaryCategoryId;
    }

    public void setPrimaryCategoryId(String primaryCategoryId) {
        this.primaryCategoryId = primaryCategoryId;
    }

    @NotNull
    public String getSecondaryCategoryId() {
        return secondaryCategoryId;
    }

    public void setSecondaryCategoryId(String secondaryCategoryId) {
        this.secondaryCategoryId = secondaryCategoryId;
    }

    @Override
    public String toString() {
        return "CardMerchantQualification{" +
                "appid='" + appid + '\'' +
                ", name='" + name + '\'' +
                ", logoMeidaId='" + logoMeidaId + '\'' +
                ", businessLicenseMediaId='" + businessLicenseMediaId + '\'' +
                ", operatorIdCardMediaId='" + operatorIdCardMediaId + '\'' +
                ", agreementFileMediaId='" + agreementFileMediaId + '\'' +
                ", primaryCategoryId='" + primaryCategoryId + '\'' +
                ", secondaryCategoryId='" + secondaryCategoryId + '\'' +
                '}';
    }

    @NotNull
    public String getJson() {
        return JSON.toJSONString(this);
    }

}
