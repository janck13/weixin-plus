package com.rsh.framework.weixin.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.msg.out.BaseSendMessage;
import com.rsh.framework.weixin.utils.HttpUtils;

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
     * @param accessToken
     * @param baseSendMessage
     * @return
     */
    public ApiResult sendMessage(String accessToken, BaseSendMessage baseSendMessage) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = sendUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, baseSendMessage.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 客服输入状态
     *
     * @param accessToken
     * @param touserOpenId 普通用户（openid）
     * @param isTyping     true:对用户下发“正在输入"状态，false:取消对用户的”正在输入"状态
     * @return
     */
    public ApiResult typing(String accessToken, String touserOpenId, boolean isTyping) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (touserOpenId == null) {
            throw new RuntimeException("touserOpenId Cannot be null");
        }

        Map<String, String> param = new HashMap<>();
        param.put("touser", touserOpenId);
        param.put("command", isTyping ? "Typing" : "CancelTyping");

        String url = sendUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }

}
