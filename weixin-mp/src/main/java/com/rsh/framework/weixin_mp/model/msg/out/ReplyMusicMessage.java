package com.rsh.framework.weixin_mp.model.msg.out;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 回复音乐消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:22
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyMusicMessage extends BaseReplyMessage {

    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String description;

    @XmlElement(name = "MusicURL")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String musicURL;

    @XmlElement(name = "HQMusicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String hqMusicUrl;

    @XmlElement(name = "ThumbMediaId")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String thumbMediaId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicURL() {
        return musicURL;
    }

    public void setMusicURL(String musicURL) {
        this.musicURL = musicURL;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
