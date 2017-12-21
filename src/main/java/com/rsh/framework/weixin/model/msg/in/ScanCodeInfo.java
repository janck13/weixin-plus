package com.rsh.framework.weixin.model.msg.in;

import com.rsh.framework.weixin.utils.adapter.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 扫码信息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/20
 * @Time: 16:08
 */
@XmlRootElement(name = "ScanCodeInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScanCodeInfo {

    @XmlElement(name = "ScanType")
    private String scanType;
    @XmlElement(name = "ScanResult")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scanResult;
    @XmlElement(name = "EventKey")
    private String eventKey;

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
