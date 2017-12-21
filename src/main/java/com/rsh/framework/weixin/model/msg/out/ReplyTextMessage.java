package com.rsh.framework.weixin.model.msg.out;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 回复文本消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:22
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyTextMessage extends BaseReplyMessage {

    @XmlElement(name = "Content")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
