package com.rsh.framework.weixin.api.msg;

import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;

/**
 * 微信服务器IP地址 API
 * 如果公众号基于安全等考虑，需要获知微信服务器的IP地址列表，以便进行相关限制，可以通过该接口获得微信服务器IP地址列表或者IP网段信息。
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 14:21
 */
public class CallbackIpApi {

    private static final String getCallbackIpUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

    /**
     * 获取微信服务器IP地址
     *
     * @return
     */
    public static ApiResult getCallbackIp() {
        String url = getCallbackIpUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

}
