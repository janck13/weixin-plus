package com.rsh.framework.weixin.model.msg.in;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 拍照发图信息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/20
 * @Time: 16:56
 */
@XmlRootElement(name = "SendPicsInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SendPicsInfo {

    @XmlElement(name = "Count")
    private String count; // 发送的图片数量

    @XmlElementWrapper(name = "PicList")
    @XmlElement(name = "item")
    private List<PicItem> picList; // 图片列表

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<PicItem> getPicList() {
        return picList;
    }

    public void setPicList(List<PicItem> picList) {
        this.picList = picList;
    }

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class PicItem {
        @XmlElement(name = "PicMd5Sum")
        private PicItem picMd5Sum; // 图片的MD5值，开发者若需要，可用于验证接收到图片

        public PicItem getPicMd5Sum() {
            return picMd5Sum;
        }

        public void setPicMd5Sum(PicItem picMd5Sum) {
            this.picMd5Sum = picMd5Sum;
        }
    }
}
