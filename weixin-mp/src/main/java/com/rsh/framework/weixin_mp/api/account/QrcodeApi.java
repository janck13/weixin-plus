package com.rsh.framework.weixin_mp.api.account;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.common.model.MediaFile;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 带参数的二维码 API
 * 为了满足用户渠道推广分析和用户帐号绑定等场景的需要，公众平台提供了生成带参数二维码的接口。使用该接口可以获得多个带不同场景值的二维码，用户扫描后，公众号可以接收到事件推送。
 * <p>
 * 目前有2种类型的二维码：
 * <p>
 * 1、临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
 * 2、永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
 * <p>
 * 用户扫描带场景值二维码时，可能推送以下两种事件：
 * <p>
 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
 * <p>
 * 如果用户已经关注公众号，在用户扫描后会自动进入会话，微信也会将带场景值扫描事件推送给开发者。
 * <p>
 * 获取带参数的二维码的过程包括两步，首先创建二维码ticket，然后凭借ticket到指定URL换取二维码。
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 9:28
 */
public class QrcodeApi {

    private static final String craeteQrcodeUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
    private static final String dowloadQrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    /**
     * 创建临时二维码 ticket
     *
     * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param sceneId       二维码类型为临时的整型参数值时的场景值，二者选填一个
     * @param sceneStr      二维码类型为临时的字符串参数值时的场景值，长度限制为1到64，二者选填一个
     * @return
     */
    public static ApiResult createTempQrcode(Integer expireSeconds, Long sceneId, String sceneStr) {
        if (expireSeconds != null && (expireSeconds <= 0 || expireSeconds > 2592000)) {
            throw new WeixinApiException("expireSeconds min 1 and max 2592000");
        }
        if (sceneId == null && StringUtils.isEmpty(sceneStr)) {
            throw new WeixinApiException("sceneId and sceneStr Cannot be all null");
        }
        if (sceneId != null && sceneId.longValue() > 4294967295L) {
            throw new WeixinApiException("sceneId max 4294967295");
        }
        if (StringUtils.isNotEmpty(sceneStr) && sceneStr.length() < 1 && sceneStr.length() > 64) {
            throw new WeixinApiException("sceneStr length min 1 and max 64");
        }

        String actionName = null;
        Map<String, Object> sceneMap = new HashMap<>();
        if (sceneId != null) {
            actionName = "QR_SCENE";
            sceneMap.put("scene_id", sceneId);
        } else {
            actionName = "QR_STR_SCENE";
            sceneMap.put("scene_str", sceneStr);
        }
        Map<String, Object> actionInfoMap = new HashMap<>();
        actionInfoMap.put("scene", sceneMap);

        Map<String, Object> param = new HashMap<>();
        param.put("expire_seconds", expireSeconds);
        param.put("action_name", actionName);
        param.put("action_info", actionInfoMap);

        String url = craeteQrcodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 创建永久二维码 ticket
     *
     * @param sceneId  二维码类型为临时的整型参数值时的场景值，最大值为100000（目前参数只支持1--100000），二者选填一个
     * @param sceneStr 二维码类型为临时的字符串参数值时的场景值，长度限制为1到64，二者选填一个
     * @return
     */
    public static ApiResult createPermanentQrcode(Integer sceneId, String sceneStr) {
        if (sceneId == null && StringUtils.isEmpty(sceneStr)) {
            throw new WeixinApiException("sceneId and sceneStr Cannot be all null");
        }
        if (sceneId != null && (sceneId.intValue() < 1 || sceneId.intValue() > 100000)) {
            throw new WeixinApiException("sceneId min 1 and max 100000");
        }
        if (StringUtils.isNotEmpty(sceneStr) && sceneStr.length() < 1 && sceneStr.length() > 64) {
            throw new WeixinApiException("sceneStr length min 1 and max 64");
        }

        String actionName = null;
        Map<String, Object> sceneMap = new HashMap<>();
        if (sceneId != null) {
            actionName = "QR_LIMIT_SCENE";
            sceneMap.put("scene_id", sceneId);
        } else {
            actionName = "QR_LIMIT_STR_SCENE";
            sceneMap.put("scene_str", sceneStr);
        }
        Map<String, Object> actionInfoMap = new HashMap<>();
        actionInfoMap.put("scene", sceneMap);

        Map<String, Object> param = new HashMap<>();
        param.put("action_name", actionName);
        param.put("action_info", actionInfoMap);

        String url = craeteQrcodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 下载二维码图片 获取的二维码ticket
     *
     * @param ticket
     * @return
     */
    public static MediaFile dowloadQrcode(String ticket) {
        if (StringUtils.isBlank(ticket)) {
            throw new WeixinApiException("ticket Cannot be null");
        }
        try {
            ticket = URLEncoder.encode(ticket, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = dowloadQrcodeUrl.replace("TICKET", ticket);
        return HttpUtils.download(url);
    }
}
