package com.rsh.framework.weixin.model.msg.in;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 接收普通消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/20
 * @Time: 14:01
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiveMessage extends BaseReceiveMessage {
    // 文本消息
    @XmlElement(name = "Content")
    private String content;

    // 图片消息
    @XmlElement(name = "PicUrl")
    private String picUrl; // 图片链接

    // 语音消息
    @XmlElement(name = "Format")
    private String format; // 语音格式，如amr，speex等
    @XmlElement(name = "Recognition")
    private String recognition; // 语音识别结果，UTF8编码（需要开通语音识别功能）

    // 视频消息，小视频消息
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId; // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

    // 公共属性
    @XmlElement(name = "MediaId")
    private String mediaId; // 消息媒体id，可以调用多媒体文件下载接口拉取数据。

    // 地理位置消息
    @XmlElement(name = "Location_X")
    private String locationX; // 地理位置维度
    @XmlElement(name = "Location_Y")
    private String locationY; // 地理位置经度
    @XmlElement(name = "Scale")
    private String scale; // 地图缩放大小
    @XmlElement(name = "Label")
    private String label; // 地理位置信息

    // 链接消息
    @XmlElement(name = "Title")
    private String title; // 消息标题
    @XmlElement(name = "Description")
    private String description; // 消息描述
    @XmlElement(name = "Url")
    private String url; // 消息链接


    //===================================================
    //==================事件消息=========================

    /**
     * 事件类型
     * subscribe 订阅
     * unsubscribe 取消订阅
     * SCAN 扫描带参数二维码已订阅
     * LOCATION  上报地理位置
     * CLICK 点击菜单拉取消息
     * VIEW 点击菜单跳转链接
     * scancode_push 扫码推事件
     * scancode_waitmsg 扫码推事件且弹出“消息接收中”提示框的事件推送
     * pic_sysphoto 弹出系统拍照发图的事件推送
     * pic_photo_or_album 弹出拍照或者相册发图的事件推送
     * pic_weixin 弹出微信相册发图器的事件推送
     * location_select 弹出地理位置选择器的事件推送
     */
    @XmlElement(name = "Event")
    private String event;
    @XmlElement(name = "EventKey")
    private String eventKey; // 事件KEY值

    // 关注、取消关注事件

    // 扫描带参数二维码事件
    // 用户未关注时，进行关注后的事件推送 EventKey	事件KEY值，qrscene_为前缀，后面为二维码的参数值
    // 用户已关注时的事件推送 EventKey	事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    @XmlElement(name = "Ticket")
    private String ticket; // 二维码的ticket，可用来换取二维码图片

    // 上报地理位置事件
    @XmlElement(name = "Latitude")
    private String latitude; // 地理位置纬度
    @XmlElement(name = "Longitude")
    private String longitude; // 地理位置经度
    @XmlElement(name = "Precision")
    private String precision; // 地理位置精度

    // 点击菜单拉取消息时的事件推送

    // 点击菜单跳转链接时的事件推送
    @XmlElement(name = "MenuID")
    private String menuID; // 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。

    // 扫码推事件的事件推送
    // 扫码推事件且弹出“消息接收中”提示框的事件推送
    @XmlElement(name = "ScanCodeInfo")
    private ScanCodeInfo scanCodeInfo;

    // 弹出系统拍照发图的事件推送
    // 弹出拍照或者相册发图的事件推送
    // 弹出微信相册发图器的事件推送
    @XmlElement(name = "SendPicsInfo")
    private SendPicsInfo sendPicsInfo;

    // 弹出地理位置选择器的事件推送
    @XmlElement(name = "SendLocationInfo")
    private SendLocationInfo sendLocationInfo;

    // 模板消息发送结束事件推送
    @XmlElement(name = "Status")
    private String status; // success：成功，failed:user block：用户拒收，failed: system failed：失败

    // 资质认证成功，名称认证成功，年审通知，认证过期失效通知
    @XmlElement(name = "ExpiredTime")
    private Integer expiredTime; // 有效期 (整形)，指的是时间戳，将于该时间戳认证过期

    // 资质认证失败，名称认证失败
    @XmlElement(name = "FailTime")
    private Integer failTime; // 失败发生时间 (整形)，时间戳
    @XmlElement(name = "FailReason")
    private String failReason; // 认证失败的原因

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public ScanCodeInfo getScanCodeInfo() {
        return scanCodeInfo;
    }

    public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }

    public SendPicsInfo getSendPicsInfo() {
        return sendPicsInfo;
    }

    public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }

    public SendLocationInfo getSendLocationInfo() {
        return sendLocationInfo;
    }

    public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
        this.sendLocationInfo = sendLocationInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
