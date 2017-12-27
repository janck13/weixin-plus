package com.rsh.framework.weixin.api;

import com.rsh.framework.weixin.exception.WeixinApiException;

import java.io.Serializable;

/**
 * app配置，公众号信息
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 17:19
 */
public class AppConfig implements Serializable {
    private static final long serialVersionUID = -7927638907720565277L;

    private String appId;
    private String appSecret;
    private String encodingAesKey;
    private boolean messageEncrypt = false; // 消息是否加密，true进行加密且必须配置 encodingAesKey，false采用明文模式，同时也支持混合模式

    public AppConfig() {
    }

    public AppConfig(String appId, String appSecret) {
        setAppId(appId);
        setAppSecret(appSecret);
    }

    public AppConfig(String appId, String appSecret, String encodingAesKey, boolean messageEncrypt) {
        setAppId(appId);
        setAppSecret(appSecret);
        setEncodingAesKey(encodingAesKey);
        setMessageEncrypt(messageEncrypt);
    }

    public String getAppId() {
        if (appId == null) {
            throw new WeixinApiException("appId 为空！");
        }
        return appId;
    }

    public void setAppId(String appId) {
        if (appId == null) {
            throw new WeixinApiException("appId 不能为空！");
        }
        this.appId = appId;
    }

    public String getAppSecret() {
        if (appSecret == null) {
            throw new WeixinApiException("appSecret 为空！");
        }
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        if (appSecret == null) {
            throw new WeixinApiException("appSecret 不能为空！");
        }
        this.appSecret = appSecret;
    }

    public String getEncodingAesKey() {
        if (encodingAesKey == null) {
            throw new WeixinApiException("encodingAesKey 为空！");
        }
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        if (encodingAesKey == null) {
            throw new WeixinApiException("encodingAesKey 不能为空！");
        }
        this.encodingAesKey = encodingAesKey;
    }

    public boolean isMessageEncrypt() {
        return messageEncrypt;
    }

    public void setMessageEncrypt(boolean messageEncrypt) {
        this.messageEncrypt = messageEncrypt;
    }
}
