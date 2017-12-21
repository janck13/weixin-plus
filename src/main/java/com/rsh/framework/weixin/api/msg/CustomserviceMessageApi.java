package com.rsh.framework.weixin.api.msg;

import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.msg.out.BaseSendMessage;
import com.rsh.framework.weixin.utils.HttpUtils;

/**
 * 客服消息接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 17:47
 */
public class CustomserviceMessageApi {

    private static final String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    /**
     * 发消息
     * @param accessToken
     * @param baseSendMessage
     * @return
     */
    public ApiResult sendMessage(String accessToken, BaseSendMessage baseSendMessage){
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = sendMsgUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, baseSendMessage.toJsonString());
        return new ApiResult(jsonResult);
    }

}
