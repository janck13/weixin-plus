package com.rsh.framework.weixin_mp.api.account;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Rsh
 * 将一条长链接转成短链接 API
 * 主要使用场景： 开发者用于生成二维码的原链接（商品、支付二维码等）太长导致扫码速度和成功率下降，将原长链接通过此接口转成短链接再生成二维码将大大提升扫码速度和成功率。
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
