package com.rsh.framework.weixin.api.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.card.ActivateMembercard;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/2
 * @Time: 9:38
 */
public class MemberCardApi {

    private static final String createUrl = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
    private static final String activateUrl = "https://api.weixin.qq.com/card/membercard/activate?access_token=ACCESS_TOKEN";
    private static final String activateUserFormUrl = "https://api.weixin.qq.com/card/membercard/activateuserform/set?access_token=ACCESS_TOKEN";
    private static final String getUserinfoUrl = "https://api.weixin.qq.com/card/membercard/userinfo/get?access_token=ACCESS_TOKEN";
    private static final String getActivatetempinfoUrl = "https://api.weixin.qq.com/card/membercard/activatetempinfo/get?access_token=ACCESS_TOKEN";
    private static final String updateuserUrl = "https://api.weixin.qq.com/card/membercard/updateuser?access_token=ACCESS_TOKEN";
    private static final String addPayGiftcardUrl = "https://api.weixin.qq.com/card/paygiftcard/add?access_token=ACCESS_TOKEN";
    private static final String deletePayGiftcardUrl = "https://api.weixin.qq.com/card/paygiftcard/delete?access_token=ACCESS_TOKEN";
    private static final String getPayGiftcardUrl = "https://api.weixin.qq.com/card/paygiftcard/getbyid?access_token=ACCESS_TOKEN";
    private static final String batchgetPayGiftcardUrl = "https://api.weixin.qq.com/card/paygiftcard/batchget?access_token=";

    /**
     * 创建会员卡
     *
     * @param jsonData Json数据
     * @return
     */
    public static ApiResult create(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = createUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 激活会员卡
     *
     * @param activateMembercard
     * @return
     */
    public static ApiResult activate(ActivateMembercard activateMembercard) {
        if (activateMembercard == null) {
            throw new WeixinApiException("activateMembercard Cannot be null");
        }

        String url = activateUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(activateMembercard));
        return new ApiResult(json);
    }

    /**
     * 设置激活会员卡字段
     *
     * @param jsonData
     * @return
     */
    public static ApiResult activateUserForm(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = activateUserFormUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 拉取会员信息
     * 支持开发者根据CardID和Code查询会员信息。
     *
     * @param cardId
     * @param code
     * @return
     */
    public static ApiResult getUserinfo(String cardId, String code) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }

        String url = getUserinfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, String> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("code", code);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 跳转型一键激活—获取用户提交资料
     * 用户填写并提交开卡资料后，会跳转到商户的网页，商户可以在网页内获取用户已填写的信息并进行开卡资质判断，信息确认等动作。
     *
     * @param activateTicket
     * @return
     */
    public static ApiResult getActivatetempinfo(String activateTicket) {
        if (StringUtils.isBlank(activateTicket)) {
            throw new WeixinApiException("activateTicket Cannot be null");
        }

        String url = getActivatetempinfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, String> param = new HashMap<>();
        param.put("activate_ticket", activateTicket);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 更新会员信息
     * 当会员持卡消费后，支持开发者调用该接口更新会员信息。会员卡交易后的每次信息变更需通过该接口通知微信，便于后续消息通知及其他扩展功能。
     *
     * @param jsonData
     * @return
     */
    public static ApiResult updateuser(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = updateuserUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 设置支付后投放卡券
     * 开通微信支付的商户可以设置在用户微信支付后自动为用户发送一条领卡消息，用户点击消息即可领取会员卡/优惠券。
     * 支持商户设置支付后投放卡券规则，可以区分时间段和金额区间发会员卡。
     *
     * @param jsonData
     * @return
     */
    public static ApiResult addPayGiftcard(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = addPayGiftcardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 删除支付后投放卡券规则
     * 删除之前已经设置的支付即会员规则。
     *
     * @param ruleId 设置支付后投放卡券接口返回的规则id
     * @return
     */
    public static ApiResult deletePayGiftcard(Integer ruleId) {
        if (ruleId == null) {
            throw new WeixinApiException("ruleId Cannot be null");
        }

        String url = deletePayGiftcardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("rule_id", ruleId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询支付后投放卡券规则详情
     * 可以查询某个支付即会员规则内容。
     *
     * @param ruleId 设置支付后投放卡券接口返回的规则id
     * @return
     */
    public static ApiResult getPayGiftcard(Integer ruleId) {
        if (ruleId == null) {
            throw new WeixinApiException("ruleId Cannot be null");
        }

        String url = getPayGiftcardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("rule_id", ruleId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 批量查询支付后投放卡券规则详情
     * 可以批量查询某个商户支付即会员规则内容。
     *
     * @param effective 是否仅查询生效的规则
     * @param offset    查询起始偏移量
     * @param count     查询的数量
     * @return
     */
    public static ApiResult batchgetPayGiftcard(boolean effective, int offset, int count) {
        if (offset < 0) {
            offset = 0;
        }
        if (count < 1) {
            count = 1;
        }

        String url = batchgetPayGiftcardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("type", "RULE_TYPE_PAY_MEMBER_CARD");
        param.put("effective", effective);
        param.put("offset", offset);
        param.put("count", count);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
