package com.rsh.framework.weixin_mp.model.msg.in;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 发送地理位置信息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 9:35
 */
@XmlRootElement(name = "SendLocationInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SendLocationInfo {

    @XmlElement(name = "Location_X")
    private String locationX; // 地理位置维度
    @XmlElement(name = "Location_Y")
    private String locationY; // 地理位置经度
    @XmlElement(name = "Scale")
    private String scale; // 精度，可理解为精度或者比例尺、越精细的话 scale越高
    @XmlElement(name = "Label")
    private String label; // 地理位置的字符串信息
    @XmlElement(name = "Poiname")
    private String poiname; // 朋友圈POI的名字，可能为空

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }
}
