package com.rsh.framework.weixin_mp.model.web;

/**
 * 微信js 卡券cardExt配置信息
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 16:11
 */
public class JsapiCardExtConfig {

    private String ticket; // 票据
    private String cardId;
    private String code; // 指定的卡券code码，只能被领一次。自定义code模式的卡券必须填写，非自定义code和预存code模式的卡券不必填写。详情见： 是否自定义code码
    private String openid; // 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，bind_openid字段为false不必填写。
    private String timestamp;
    private String nonceStr;
    private String fixedBegintimestamp;
    private String outerStr;
    private String signature;

    public JsapiCardExtConfig(String ticket, String cardId, String code, String openid, String timestamp, String nonceStr, String signature) {
        this.ticket = ticket;
        this.cardId = cardId;
        this.code = code;
        this.openid = openid;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getFixedBegintimestamp() {
        return fixedBegintimestamp;
    }

    public void setFixedBegintimestamp(String fixedBegintimestamp) {
        this.fixedBegintimestamp = fixedBegintimestamp;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
