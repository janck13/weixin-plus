package com.rsh.framework.weixin_mp.api.base;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Rsh
 * 将一条长链接转成短链接 API
 *
 * @Description
 * @Date: 2018/1/24
 * @Time: 11:03
 */
public class ShorturlApi {

    private static final String shorturlUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

    /**
     *
     * @param longUrl
     * @return
     */
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
