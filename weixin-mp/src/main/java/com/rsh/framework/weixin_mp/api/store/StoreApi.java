package com.rsh.framework.weixin_mp.api.store;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信门店 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 15:21
 */
public class StoreApi {

    private static final String shorturlUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

    public static ApiResult shortUrl(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            throw new WeixinApiException("longUrl Cannot be null");
        }

        String url = shorturlUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("action", "long2short");
        param.put("long_url", longUrl);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }
}
