package com.rsh.framework.weixin.api.web;

import com.rsh.framework.weixin.api.ApiConfigUtils;
import com.rsh.framework.weixin.api.AppConfig;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.cache.IJsapiTicketCache;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.web.JsapiTicket;
import com.rsh.framework.weixin.model.web.JsapiTicketType;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.RetryUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.concurrent.Callable;

/**
 * jsapi_ticket API
 * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
 * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 15:52
 */
public class JsapiTicketApi {

    private static final String getJsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 获取jsapi_ticket
     *
     * @return
     */
    public static JsapiTicket getJsapiTicket(JsapiTicketType jsapiTicketType) {
        AppConfig appConfig = ApiConfigUtils.getAppConfig();

        JsapiTicket jsapiTicket = getAvailableJsapiTicketByCache(appConfig, jsapiTicketType);
        if (jsapiTicket != null) {
            return jsapiTicket;
        }
        // 刷新JsapiTicket
        jsapiTicket = refreshJsapiTicketIsInvalid(appConfig, jsapiTicketType);
        if (jsapiTicket == null) {
            throw new WeixinApiException("获取jsapi_ticket失败，请检查access_token是否有效！");
        }
        return jsapiTicket;
    }

    /**
     * 从缓存中获取可用的jsapi_ticket
     *
     * @param appConfig
     * @param jsapiTicketType
     * @return
     */
    private static JsapiTicket getAvailableJsapiTicketByCache(AppConfig appConfig, JsapiTicketType jsapiTicketType) {
        IJsapiTicketCache jsapiTicketCache = ApiConfigUtils.getJsapiTicketCache();

        String key = appConfig.getAppId() + "-" + jsapiTicketType.name();
        String jsapiTicketJson = jsapiTicketCache.getJsapiTicket(key);
        if (StringUtils.isNotBlank(jsapiTicketJson)) {
            JsapiTicket result = new JsapiTicket(jsapiTicketJson);
            if (result != null && result.isAvailable()) {
                return result;
            }
        }
        return null;
    }

    /**
     * 更新 jsapi_ticket
     * synchronized 防止多线程刷新jsapi_ticket
     * 先获取缓存jsapi_ticket有效性，防止多线程重复刷新jsapi_ticket
     *
     * @param appConfig
     * @param jsapiTicketType
     * @return
     */
    public static synchronized JsapiTicket refreshJsapiTicketIsInvalid(AppConfig appConfig, JsapiTicketType jsapiTicketType) {
        JsapiTicket jsapiTicket = getAvailableJsapiTicketByCache(appConfig, jsapiTicketType);
        if (jsapiTicket != null) {
            return jsapiTicket;
        }
        return refreshJsapiTicket(appConfig, jsapiTicketType);
    }

    /**
     * 更新 jsapi_ticket，该方法不会判断缓存jsapi_ticket有效性，所以谨慎调用
     *
     * @param jsapiTicketType
     * @return
     */
    public static JsapiTicket refreshJsapiTicket(AppConfig appConfig, JsapiTicketType jsapiTicketType) {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        final String url = getJsapiTicketUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", jsapiTicketType.name());
        // 失败，连续重试3次
        JsapiTicket jsapiTicket = RetryUtils.retryOnException(3, new Callable<JsapiTicket>() {
            @Override
            public JsapiTicket call() throws Exception {
                String json = HttpUtils.get(url);
                return new JsapiTicket(json);
            }
        });

        // 将jsapiTicket加入缓存
        if (jsapiTicket != null) {
            IJsapiTicketCache jsapiTicketCache = ApiConfigUtils.getJsapiTicketCache();
            String key = appConfig.getAppId() + "-" + jsapiTicketType.name();
            jsapiTicketCache.setJsapiTicket(key, jsapiTicket.getCacheJson(), jsapiTicket.getExpiresIn());
        }
        return jsapiTicket;
    }


}
