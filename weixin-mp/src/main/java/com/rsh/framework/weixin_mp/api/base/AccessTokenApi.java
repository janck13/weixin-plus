package com.rsh.framework.weixin_mp.api.base;

import com.rsh.framework.weixin_mp.api.WeixinMpApiConfigUtils;
import com.rsh.framework.weixin_mp.api.AppConfig;
import com.rsh.framework.weixin_mp.cache.IAccessTokenCache;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin_mp.model.base.AccessToken;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.RetryUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.concurrent.Callable;

/**
 * access_token API
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
 * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * <p>
 * 公众平台的API调用所需的access_token的使用及生成方式说明：
 * <p>
 * 1、建议公众号开发者使用中控服务器统一获取和刷新Access_token，其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则容易造成冲突，导致access_token覆盖而影响业务；
 * <p>
 * 2、目前Access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。中控服务器需要根据这个有效时间提前去刷新新access_token。在刷新过程中，中控服务器可对外继续输出的老access_token，此时公众平台后台会保证在5分钟内，新老access_token都可用，这保证了第三方业务的平滑过渡；
 * <p>
 * 3、Access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，还需要提供被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。
 * <p>
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 16:32
 */
public class AccessTokenApi {

    private static final String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取 AccessToken，如果未取到或者 AccessToken 不可用则先更新再获取
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        AppConfig appConfig = WeixinMpApiConfigUtils.getAppConfig();
        AccessToken accessToken = getAvailableAccessTokenByCache(appConfig.getAppId());
        if (accessToken != null) {
            return accessToken;
        }
        // 刷新AccessToken
        accessToken = refreshAccessTokenIsInvalid(appConfig);
        if (accessToken == null) {
            throw new WeixinApiException("获取access_token失败，请检查公众号基础配置！");
        }
        return accessToken;
    }

    /**
     * 从缓存中获取可用的 AccessToken
     *
     * @param appId
     * @return
     */
    private static AccessToken getAvailableAccessTokenByCache(String appId) {
        IAccessTokenCache accessTokenCache = WeixinMpApiConfigUtils.getAccessTokenCache();

        String accessTokenJson = accessTokenCache.getAccessToken(appId);
        if (StringUtils.isNotBlank(accessTokenJson)) {
            AccessToken result = new AccessToken(accessTokenJson);
            if (result != null && result.isAvailable()) {
                return result;
            }
        }
        return null;
    }

    /**
     * 更新 access token
     * synchronized 防止多线程刷新AccessToken
     * 先获取缓存AccessToken有效性，防止多线程重复刷新AccessToken
     *
     * @param appConfig
     * @return
     */
    public static synchronized AccessToken refreshAccessTokenIsInvalid(AppConfig appConfig) {
        AccessToken accessToken = getAvailableAccessTokenByCache(appConfig.getAppId());
        if (accessToken != null) {
            return accessToken;
        }
        return refreshAccessToken(appConfig);
    }

    /**
     * 更新 access token，该方法不会判断缓存AccessToken有效性，所以谨慎调用
     *
     * @param appConfig
     * @return
     */
    public static AccessToken refreshAccessToken(AppConfig appConfig) {
        String appId = appConfig.getAppId();
        String appSecret = appConfig.getAppSecret();

        final String url = getAccessTokenUrl.replace("APPID", appId).replace("SECRET", appSecret);
        // 失败，连续重试3次
        AccessToken accessToken = RetryUtils.retryOnException(3, new Callable<AccessToken>() {
            @Override
            public AccessToken call() throws Exception {
                String json = HttpUtils.get(url);
                return new AccessToken(json);
            }
        });
        // 将accessToken加入缓存
        if (accessToken != null) {
            IAccessTokenCache accessTokenCache = WeixinMpApiConfigUtils.getAccessTokenCache();
            accessTokenCache.setAccessToken(appId, accessToken.getCacheJson(), accessToken.getExpiresIn());
        }
        return accessToken;
    }

}
