package com.rsh.framework.weixin_mp.model.web;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 16:11
 */
public class JsapiConfig {

    private String ticket;
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;

    public JsapiConfig(String ticket, String appId, String timestamp, String nonceStr, String signature) {
        this.ticket = ticket;
        this.appId = appId;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
