package com.rsh.framework.weixin_component.api.auth;

import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.RetryUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_component.api.ApiConfigUtils;
import com.rsh.framework.weixin_component.api.ComponentAppConfig;
import com.rsh.framework.weixin_component.cache.IComponentAccessTokenCache;
import com.rsh.framework.weixin_component.model.auth.ComponentAccessToken;

import java.util.concurrent.Callable;

/**
 * Created By Rsh
 * 获取第三方平台component_access_token
 *
 * @Description
 * @Date: 2018/1/23
 * @Time: 15:50
 */
public class ComponentAccessTokenApi {

    private static final String getComponentAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

    /**
     * 获取 component_access_token，如果未取到或者 component_access_token 不可用则先更新再获取
     *
     * @return
     */
    public static ComponentAccessToken getComponentAccessToken() {
        ComponentAppConfig componentAppConfig = ApiConfigUtils.getComponentAppConfig();
        ComponentAccessToken componentAccessToken = getAvailableComponentAccessTokenByCache(componentAppConfig.getComponentAppid());
        if (componentAccessToken != null) {
            return componentAccessToken;
        }
        // 刷新ComponentAccessToken
        componentAccessToken = refreshComponentAccessTokenIsInvalid(componentAppConfig);
        if (componentAccessToken == null) {
            throw new WeixinApiException("获取component_access_token失败，请检查公众号基础配置！");
        }
        return componentAccessToken;
    }

    /**
     * 从缓存中获取可用的 component_access_token
     *
     * @param componentAppid
     * @return
     */
    private static ComponentAccessToken getAvailableComponentAccessTokenByCache(String componentAppid) {
        IComponentAccessTokenCache componentAccessTokenCache = ApiConfigUtils.getComponentAccessTokenCache();

        String componentAccessTokenJson = componentAccessTokenCache.getComponentAccessToken(componentAppid);
        if (StringUtils.isNotBlank(componentAccessTokenJson)) {
            ComponentAccessToken result = new ComponentAccessToken(componentAccessTokenJson);
            if (result != null && result.isAvailable()) {
                return result;
            }
        }
        return null;
    }

    /**
     * 更新 component_access_token
     * synchronized 防止多线程刷新component_access_token
     * 先获取缓存component_access_token有效性，防止多线程重复刷新component_access_token
     *
     * @param componentAppConfig
     * @return
     */
    public static synchronized ComponentAccessToken refreshComponentAccessTokenIsInvalid(ComponentAppConfig componentAppConfig) {
        ComponentAccessToken componentAccessToken = getAvailableComponentAccessTokenByCache(componentAppConfig.getComponentAppid());
        if (componentAccessToken != null) {
            return componentAccessToken;
        }
        return refreshComponentAccessToken(componentAppConfig);
    }

    /**
     * 更新 component_access_token，该方法不会判断缓存component_access_token有效性，所以谨慎调用
     *
     * @param componentAppConfig
     * @return
     */
    public static ComponentAccessToken refreshComponentAccessToken(ComponentAppConfig componentAppConfig) {
        String appId = componentAppConfig.getComponentAppid();
        String appSecret = componentAppConfig.getComponentAppsecret();

        final String url = getComponentAccessTokenUrl;
        // 失败，连续重试3次
        ComponentAccessToken componentAccessToken = RetryUtils.retryOnException(3, new Callable<ComponentAccessToken>() {
            @Override
            public ComponentAccessToken call() throws Exception {
                String json = HttpUtils.get(url);
                return new ComponentAccessToken(json);
            }
        });
        // 将accessToken加入缓存
        if (componentAccessToken != null) {
            IComponentAccessTokenCache componentAccessTokenCache = ApiConfigUtils.getComponentAccessTokenCache();
            componentAccessTokenCache.setComponentAccessToken(appId, componentAccessToken.getCacheJson(), componentAccessToken.getExpiresIn());
        }
        return componentAccessToken;
    }
}
