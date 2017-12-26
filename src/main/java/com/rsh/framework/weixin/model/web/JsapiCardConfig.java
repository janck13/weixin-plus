package com.rsh.framework.weixin.model.web;

/**
 * 微信js 卡券配置信息
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 16:11
 */
public class JsapiCardConfig {

    private String ticket; // 票据
    private String shopId; // 门店ID。shopID用于筛选出拉起带有指定location_list(shopID)的卡券列表，非必填。
    private String cardType; // 卡券类型，用于拉起指定卡券类型的卡券列表。当cardType为空时，默认拉起所有卡券的列表，非必填。
    private String cardId; // 卡券ID，用于拉起指定cardId的卡券列表，当cardId为空时，默认拉起所有卡券的列表，非必填。
    private String timestamp;
    private String nonceStr;
    private String signType;
    private String cardSign;

    public JsapiCardConfig(String ticket, String shopId, String cardType, String cardId, String timestamp, String nonceStr, String signType, String cardSign) {
        this.ticket = ticket;
        this.shopId = shopId;
        this.cardType = cardType;
        this.cardId = cardId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signType = signType;
        this.cardSign = cardSign;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getCardSign() {
        return cardSign;
    }

    public void setCardSign(String cardSign) {
        this.cardSign = cardSign;
    }
}
