package com.rsh.framework.weixin.model.card;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 二维码卡券
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 17:26
 */
public class QrcardInfo {

    @JSONField(name = "card_id")
    private String cardId; // 卡券ID
    @JSONField(name = "code")
    private String code; // 卡券Code码,use_custom_code字段为true的卡券必须填写，非自定义code和导入code模式的卡券不必填写。
    @JSONField(name = "openid")
    private String openid; // 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写。
    @JSONField(name = "is_unique_code")
    private boolean isUniqueCode = false; // 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，卡券须通过审核且库存不为0。
    @JSONField(name = "outer_id")
    private Integer outerId; // 领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的 事件推送 中会带上此自定义场景值。
    @JSONField(name = "outer_str")
    private String outerStr; // outer_id字段升级版本，字符串类型，用户首次领卡时，会通过 领取事件推送 给商户； 对于会员卡的二维码，用户每次扫码打开会员卡后点击任何url，会将该值拼入url中，方便开发者定位扫码来源

    public QrcardInfo() {
    }

    public QrcardInfo(String cardId, String code, String outerStr) {
        this.cardId = cardId;
        this.code = code;
        this.outerStr = outerStr;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean isUniqueCode() {
        return isUniqueCode;
    }

    public void setUniqueCode(boolean uniqueCode) {
        isUniqueCode = uniqueCode;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }
}
