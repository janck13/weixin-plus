package com.rsh.framework.weixin_mp.api.web;

import com.rsh.framework.weixin_mp.api.ApiConfigUtils;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin_mp.model.web.*;
import com.rsh.framework.weixin.utils.Sha1Util;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.*;

/**
 * jsAPI 配置信息获取 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 15:52
 */
public class JsapiConfigApi {

    /**
     * 获取微信js接口配置信息
     *
     * @param url 当前网页的URL，不包含#及其后面部分
     * @return
     */
    public static JsapiConfig getJsapiConfig(String url) {
        if (StringUtils.isBlank(url)) {
            throw new WeixinApiException("url Cannot be null");
        }
        String appId = ApiConfigUtils.getAppId();
        String jsapiTicket = JsapiTicketApi.getJsapiTicket(JsapiTicketType.jsapi).getTicket();
        // 随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // 时间戳
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        String str = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = Sha1Util.shaForConfSignature(str);

        return new JsapiConfig(jsapiTicket, appId, timestamp, noncestr, signature);
    }

    /**
     * 获取微信js卡券配置信息
     *
     * @param shopId   门店ID。shopID用于筛选出拉起带有指定location_list(shopID)的卡券列表，非必填。
     * @param cardType 卡券类型，用于拉起指定卡券类型的卡券列表。当cardType为空时，默认拉起所有卡券的列表，非必填。
     * @param cardId   卡券ID，用于拉起指定cardId的卡券列表，当cardId为空时，默认拉起所有卡券的列表，非必填。
     * @return
     */
    public static JsapiCardConfig getJsapiCardConfig(String shopId, String cardType, String cardId) {
        String appId = ApiConfigUtils.getAppId();
        String jsapiTicket = JsapiTicketApi.getJsapiTicket(JsapiTicketType.wx_card).getTicket();
        // 随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // 时间戳
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        List<String> list = new ArrayList<>();
        list.add(shopId);
        list.add(jsapiTicket);
        list.add(appId);
        list.add(timestamp);
        list.add(noncestr);
        list.add(cardId);
        list.add(cardType);

        String cardSign = Sha1Util.shaForConfSignature(sort(list));

        return new JsapiCardConfig(jsapiTicket, appId, cardType, cardId, timestamp, noncestr, "SHA1", cardSign);
    }

    /**
     * 获取微信js卡券CardExt配置信息
     *
     * @param cardId 卡券ID，用于拉起指定cardId的卡券列表，当cardId为空时，默认拉起所有卡券的列表，非必填。
     * @param code   指定的卡券code码，只能被领一次。自定义code模式的卡券必须填写，非自定义code和预存code模式的卡券不必填写
     * @param openId 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，bind_openid字段为false不必填写。
     * @return
     */
    public static JsapiCardExtConfig getJsapiCardExtConfig(String cardId, String code, String openId) {
        String appId = ApiConfigUtils.getAppId();
        String jsapiTicket = JsapiTicketApi.getJsapiTicket(JsapiTicketType.wx_card).getTicket();
        // 随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // 时间戳
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        List<String> list = new ArrayList<>();
        list.add(jsapiTicket);
        list.add(timestamp);
        list.add(noncestr);
        list.add(cardId);
        list.add(code);
        list.add(openId);

        String signature = Sha1Util.shaForConfSignature(sort(list));

        return new JsapiCardExtConfig(jsapiTicket, cardId, code, openId, timestamp, noncestr, signature);
    }

    /**
     * 字符串的字典序排序
     *
     * @param list
     * @return
     */
    private static String sort(List<String> list) {
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (arrayToSort[i] != null) {
                sb.append(arrayToSort[i]);
            }
        }
        return sb.toString();
    }

}
