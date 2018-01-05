package com.rsh.framework.weixin_mp.model.msg.out;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * 回复图文消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 10:22
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyNewsMessage extends BaseReplyMessage {

    @XmlElement(name = "ArticleCount")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String aticleCount;

    @XmlElementWrapper(name = "Articles")
    @XmlElement(name = "item")
    private List<ArticleItem> articles;

    public String getAticleCount() {
        return aticleCount;
    }

    public void setAticleCount(String aticleCount) {
        this.aticleCount = aticleCount;
    }

    public List<ArticleItem> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleItem> articles) {
        this.articles = articles;
    }
}
