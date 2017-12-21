package com.rsh.framework.weixin.model.msg.in;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/20
 * @Time: 13:45
 */
public class BaseReceiveMessage implements Serializable {

    // 开发者微信号
    @XmlElement(name = "ToUserName")
    protected String toUserName;

    // 发送方帐号（一个OpenID）
    @XmlElement(name = "FromUserName")
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
    protected String msgType;

    // 消息id，64位整型
    @XmlElement(name = "MsgId")
    protected String msgId;

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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
