package com.rsh.framework.weixin_mp.model.msg.out;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:21
 */
public class BaseReplyMessage {

    // 开发者微信号
    @XmlElement(name = "ToUserName")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    protected String toUserName;

    // 发送方帐号（一个OpenID）
    @XmlElement(name = "FromUserName")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    protected String fromUserName;

    // 消息创建时间 （整型）
    @XmlElement(name = "CreateTime")
    protected String createTime;

    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * shortvideo 小视频
     * location 地址位置消息
     * link 链接消息
     * event 事件
     */
    @XmlElement(name = "MsgType")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    protected String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
