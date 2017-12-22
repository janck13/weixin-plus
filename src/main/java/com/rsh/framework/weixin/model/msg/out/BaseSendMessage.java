package com.rsh.framework.weixin.model.msg.out;

import com.alibaba.fastjson.annotation.JSONField;
import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 发消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:21
 */
public abstract class BaseSendMessage {

    protected String touser;
    protected String msgtype;
    protected Customservice customservice;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public abstract String toJsonString();

    public Customservice getCustomservice() {
        return customservice;
    }

    public void setCustomservice(Customservice customservice) {
        this.customservice = customservice;
    }
}

class Customservice {

    @JSONField(name = "kf_account")
    protected String kfAccount;

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }
}