package com.rsh.framework.weixin_mp.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin_mp.model.msg.out.BaseSendMessage;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 客服消息接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 17:47
 */
public class CustomserviceMessageApi {

    private static final String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    private static final String typingUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN";

    /**
     * 发消息
     *
     * @param baseSendMessage
     * @return
     */
    public static ApiResult sendMessage(BaseSendMessage baseSendMessage) {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = sendUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, baseSendMessage.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 客服输入状态
     *
     * @param touserOpenId 普通用户（openid）
     * @param isTyping     true:对用户下发“正在输入"状态，false:取消对用户的”正在输入"状态
     * @return
     */
    public static ApiResult typing(String touserOpenId, boolean isTyping) {
        if (StringUtils.isBlank(touserOpenId)) {
            throw new WeixinApiException("touserOpenId Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        Map<String, String> param = new HashMap<>();
        param.put("touser", touserOpenId);
        param.put("command", isTyping ? "Typing" : "CancelTyping");

        String url = sendUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }

}
