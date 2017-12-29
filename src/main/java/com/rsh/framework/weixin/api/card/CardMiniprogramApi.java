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
 * 卡券-小程序 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/29
 * @Time: 15:32
 */
public class CardMiniprogramApi {

    private static final String getMembercardActivateUrl = "https://api.weixin.qq.com/card/membercard/activate/geturl?access_token=ACCESS_TOKEN";
    private static final String getMembercardActivateInfoUrl = "https://api.weixin.qq.com/card/membercard/activatetempinfo/get?access_token=ACCESS_TOKEN";
    private static final String getCardCodeUrl = "https://api.weixin.qq.com/card/code/get?access_token=ACCESS_TOKEN";
    private static final String activateMembercardUrl = "https://api.weixin.qq.com/card/membercard/activate?access_token=ACCESS_TOKEN";

    /**
     * 获取开卡插件参数
     *
     * @param cardId   会员卡的card_id
     * @param outerStr 渠道值，用于统计本次领取的渠道参数
     * @return
     */
    public static ApiResult getMembercardActivate(String cardId, String outerStr) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        if (outerStr != null) {
            param.put("outer_str", outerStr);
        }

        String url = getMembercardActivateUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取用户开卡时提交的信息（跳转型开卡组件）
     *
     * @param activateTicket 跳转型开卡组件开卡后回调中的激活票据，可以用来获取用户开卡资料
     * @return
     */
    public static ApiResult getMembercardActivateInfo(String activateTicket) {
        if (StringUtils.isBlank(activateTicket)) {
            throw new WeixinApiException("activateTicket Cannot be null");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("activate_ticket", activateTicket);

        String url = getMembercardActivateInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取用户开卡时提交的信息（非跳转型开卡组件）
     *
     * @param cardId 卡券id，非自定义code类型会员卡不必填写
     * @param code   会员卡的code
     * @return
     */
    public static ApiResult getCardCode(String cardId, String code) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }

        String url = getCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 激活用户领取的会员卡（跳转型开卡组件）
     *
     * @param activateMembercard
     * @return
     */
    public static ApiResult activateMembercard(ActivateMembercard activateMembercard) {
        if (activateMembercard == null) {
            throw new WeixinApiException("activateMembercard Cannot be null");
        }

        String url = activateMembercardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(activateMembercard));
        return new ApiResult(json);
    }

}
