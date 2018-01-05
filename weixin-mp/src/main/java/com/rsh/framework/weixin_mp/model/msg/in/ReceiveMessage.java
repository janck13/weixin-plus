package com.rsh.framework.weixin_mp.model.msg.in;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 接收普通消息
 * Created By Rsh
 *
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

    //=====================微信卡券=====================
    // 公共字段
    @XmlElement(name = "CardId")
    private String cardId; // 卡券ID
    @XmlElement(name = "UserCardCode")
    private String userCardCode; // 卡券Code码

    // 微信卡券买单事件推送
    @XmlElement(name = "TransId")
    private String transId; // 微信支付交易订单号（只有使用买单功能核销的卡券才会出现）
    @XmlElement(name = "LocationName")
    private String locationName; // 门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）
    @XmlElement(name = "Fee")
    private String fee; // 实付金额，单位为分
    @XmlElement(name = "OriginalFee")
    private String originalFee; // 应付金额，单位为分

    // 微信卡券审核事件推送
    @XmlElement(name = "RefuseReason")
    private String refuseReason; // 审核不通过原因

    // 微信卡券用户领取卡券事件推送
    @XmlElement(name = "IsGiveByFriend")
    private String isGiveByFriend; // 是否为转赠领取，1代表是，0代表否。
    @XmlElement(name = "OldUserCardCode")
    private String oldUserCardCode; // 为保证安全，微信会在转赠发生后变更该卡券的code号，该字段表示转赠前的code
    @XmlElement(name = "OuterStr")
    private String outerStr; // 领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加Addcard接口中自定义该字段的字符串值。
    @XmlElement(name = "IsRestoreMemberCard")
    private String isRestoreMemberCard; // 用户删除会员卡后可重新找回，当用户本次操作为找回时，该值为1，否则为0

    // 微信卡券用户转赠卡券事件推送
    //@XmlElement(name = "FriendUserName")
    //private String friendUserName; // 接收卡券用户的openid
    @XmlElement(name = "IsReturnBack")
    private Integer isReturnBack; // 是否转赠退回，0代表不是，1代表是
    @XmlElement(name = "IsChatRoom")
    private Integer isChatRoom; // 是否是群转赠

    @XmlElement(name = "FriendUserName")
    // 微信卡券用户领取卡券事件推送：当IsGiveByFriend为1时填入的字段，表示发起转赠用户的openid
    // 微信卡券用户转赠卡券事件推送：接收卡券用户的openid
    private String friendUserName;

    // 用户删除卡券事件推送

    // 微信卡券核销事件推送
    @XmlElement(name = "ConsumeSource")
    private String consumeSource; // 核销来源。支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
    //@XmlElement(name = "LocationName")
    //private String locationName; // 门店名称，当前卡券核销的门店名称（只有通过自助核销和买单核销时才会出现该字段）
    @XmlElement(name = "StaffOpenId")
    private String staffOpenId; // 核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）
    @XmlElement(name = "VerifyCode")
    private String verifyCode; // 自助核销时，用户输入的验证码
    @XmlElement(name = "RemarkAmount")
    private String remarkAmount; // 自助核销 时 ，用户输入的备注金额
    //@XmlElement(name = "OuterStr")
    //private String outerStr; // 开发者发起核销时传入的自定义参数，用于进行核销渠道统计

    // 用户点击会员卡

    // 从卡券进入公众号会话事件推送

    // 会员卡内容更新事件
    @XmlElement(name = "ModifyBonus")
    private Integer modifyBonus; // 变动的积分值
    @XmlElement(name = "ModifyBalance")
    private Integer modifyBalance; // 变动的余额值

    // 库存报警
    @XmlElement(name = "Detail")
    private String detail; // 报警详细信息

    // 券点流水详情事件
    @XmlElement(name = "OrderId")
    private String orderId; // 本次推送对应的订单号
    //@XmlElement(name = "Status")
   //private String status; // 本次订单号的状态,ORDER_STATUS_WAITING 等待支付 ORDER_STATUS_SUCC 支付成功 ORDER_STATUS_FINANCE_SUCC 加代币成功 ORDER_STATUS_QUANTITY_SUCC 加库存成功 ORDER_STATUS_HAS_REFUND 已退币 ORDER_STATUS_REFUND_WAITING 等待退币确认 ORDER_STATUS_ROLLBACK 已回退,系统失败 ORDER_STATUS_HAS_RECEIPT 已开发票
    @XmlElement(name = "CreateOrderTime")
    private Integer createOrderTime; // 购买券点时，支付二维码的生成时间
    @XmlElement(name = "PayFinishTime")
    private Integer payFinishTime; // 购买券点时，实际支付成功的时间
    @XmlElement(name = "Desc")
    private String desc; // 支付方式，一般为微信支付充值
    @XmlElement(name = "FreeCoinCount")
    private String freeCoinCount; // 剩余免费券点数量
    @XmlElement(name = "PayCoinCount")
    private String payCoinCount; // 剩余付费券点数量
    @XmlElement(name = "RefundFreeCoinCount")
    private String refundFreeCoinCount; // 本次变动的免费券点数量
    @XmlElement(name = "RefundPayCoinCount")
    private String refundPayCoinCount; // 本次变动的付费券点数量
    @XmlElement(name = "OrderType")
    private String orderType; // 所要拉取的订单类型 ORDER_TYPE_SYS_ADD 平台赠送券点 ORDER_TYPE_WXPAY 充值券点 ORDER_TYPE_REFUND 库存未使用回退券点 ORDER_TYPE_REDUCE 券点兑换库存 ORDER_TYPE_SYS_REDUCE 平台扣减
    @XmlElement(name = "Memo")
    private String memo; // 系统备注，说明此次变动的缘由，如开通账户奖励、门店奖励、核销奖励以及充值、扣减。
    @XmlElement(name = "ReceiptInfo")
    private String receiptInfo; // 所开发票的详情

    // 会员卡激活事件推送

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

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getFailTime() {
        return failTime;
    }

    public void setFailTime(Integer failTime) {
        this.failTime = failTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserCardCode() {
        return userCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        this.userCardCode = userCardCode;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOriginalFee() {
        return originalFee;
    }

    public void setOriginalFee(String originalFee) {
        this.originalFee = originalFee;
    }
}
