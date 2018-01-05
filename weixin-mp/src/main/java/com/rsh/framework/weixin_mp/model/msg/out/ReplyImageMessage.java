package com.rsh.framework.weixin_mp.model.msg.out;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 回复图片消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:22
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyImageMessage extends BaseReplyMessage {

    @XmlElement(name = "MediaId")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
