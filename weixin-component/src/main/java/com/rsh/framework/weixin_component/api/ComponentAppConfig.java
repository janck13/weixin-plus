package com.rsh.framework.weixin_component.api;

import com.rsh.framework.weixin.common.exception.WeixinApiException;

import java.io.Serializable;

/**
 * Created By Rsh
 * 第三方平台配置信息
 *
 * @Description
 * @Date: 2018/1/23
 * @Time: 16:07
 */
public class ComponentAppConfig implements Serializable {
    private static final long serialVersionUID = -2753755518634920231L;

    private String componentAppid; // 第三方平台appid
    private String componentAppsecret; // 第三方平台appsecret
    private String componentVerifyTicket; // 微信后台推送的ticket，此ticket会定时推送

    private String encodingAesKey;
    private boolean messageEncrypt = false; // 消息是否加密，true进行加密且必须配置 encodingAesKey，false采用明文模式，同时也支持混合模式

    public ComponentAppConfig() {
    }

    public ComponentAppConfig(String componentAppid, String componentAppsecret) {
        setComponentAppid(componentAppid);
        setComponentAppsecret(componentAppsecret);
    }

    public ComponentAppConfig(String componentAppid, String componentAppsecret, String encodingAesKey, boolean messageEncrypt) {
        setComponentAppid(componentAppid);
        setComponentAppsecret(componentAppsecret);
        setEncodingAesKey(encodingAesKey);
        setMessageEncrypt(messageEncrypt);
    }

    public String getComponentAppid() {
        if (componentAppid == null) {
            throw new WeixinApiException("componentAppid 为空！");
        }
        return componentAppid;
    }

    public void setComponentAppid(String componentAppid) {
        if (componentAppid == null) {
            throw new WeixinApiException("componentAppid 不能为空！");
        }
        this.componentAppid = componentAppid;
    }

    public String getComponentAppsecret() {
        if (componentAppsecret == null) {
            throw new WeixinApiException("componentAppsecret 为空！");
        }
        return componentAppsecret;
    }

    public void setComponentAppsecret(String componentAppsecret) {
        if (componentAppsecret == null) {
            throw new WeixinApiException("componentAppsecret 不能为空！");
        }
        this.componentAppsecret = componentAppsecret;
    }

    public String getComponentVerifyTicket() {
        if (componentVerifyTicket == null) {
            throw new WeixinApiException("componentVerifyTicket 不能为空！");
        }
        return componentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        if (componentVerifyTicket == null) {
            throw new WeixinApiException("componentVerifyTicket 不能为空！");
        }
        this.componentVerifyTicket = componentVerifyTicket;
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
