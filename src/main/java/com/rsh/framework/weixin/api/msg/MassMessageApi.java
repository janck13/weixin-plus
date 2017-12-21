package com.rsh.framework.weixin.api.msg;

import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.msg.out.BaseSendMessage;
import com.rsh.framework.weixin.utils.HttpUtils;

/**
 * 群发消息接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 18:03
 */
public class MassMessageApi {

    private static final String sendAllUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";

    /**
     * 群发
     * @param accessToken
     * @param json
     * @return
     */
    public ApiResult sendall(String accessToken, String json){
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (json == null) {
            throw new RuntimeException("json Cannot be null");
        }

        String url = sendAllUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

}
