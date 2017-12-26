package com.rsh.framework.weixin.api;

import com.rsh.framework.weixin.cache.IAccessTokenCache;
import com.rsh.framework.weixin.cache.IJsapiTicketCache;
import com.rsh.framework.weixin.cache.impl.DefaultAccessTokenCache;
import com.rsh.framework.weixin.cache.impl.DefaultJsapiTicketCache;
import com.rsh.framework.weixin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * App 配置工具类
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 17:15
 */
public class ApiConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(ApiConfigUtils.class);

    // 将appid绑定到ThreadLocal，以方便在当前线程的各个地方获取AppConfig对象：
    private static final ThreadLocal<String> appidTL = new ThreadLocal<String>();
    private static final Map<String, AppConfig> appConfigMap = new ConcurrentHashMap<String, AppConfig>();
    // 默认appConfig key
    private static final String DEFAULT_APPCONFIG_KEY = "default_appconfig_key";

    // access_token缓存机制使用默认缓存实现
    private static IAccessTokenCache accessTokenCache = new DefaultAccessTokenCache();
    // jsapi_ticket缓存机制使用默认缓存实现
    private static IJsapiTicketCache jsapiTicketCache = new DefaultJsapiTicketCache();

    // debug模式将输出API请求报文和响应报文
    private static boolean debug = false;

    /**
     * 添加公众号配置，每个appId只需添加一次，相同appId将被覆盖。
     * 第一个添加的将作为默认公众号配置
     *
     * @param appConfig 公众号配置
     * @return
     */
    public static AppConfig putApiConfig(AppConfig appConfig) {
        if (appConfigMap.size() == 0) {
            appConfigMap.put(DEFAULT_APPCONFIG_KEY, appConfig);
        }
        return appConfigMap.put(appConfig.getAppId(), appConfig);
    }

    public static AppConfig removeApiConfig(String appId) {
        return appConfigMap.remove(appId);
    }

    /**
     * 将默认appid绑定到当前线程，当前只有一个公众号使用
     */
    public static void setAppIdToThreadLocal() {
        String appId = appConfigMap.get(DEFAULT_APPCONFIG_KEY).getAppId();
        setAppIdToThreadLocal(appId);
    }

    /**
     * 将appid绑定到当前线程
     *
     * @param appId
     */
    public static void setAppIdToThreadLocal(String appId) {
        if (StringUtils.isBlank(appId)) {
            throw new IllegalStateException("appId Cannot be null");
        }
        if (appConfigMap.get(appId) == null) {
            throw new IllegalStateException("该appId未配置，需先调用ApiConfigUtils.putAppConfig(AppConfig appConfig)存入AppConfig.");
        }
        appidTL.set(appId);
    }

    /**
     * 移除当前线程绑定的appid
     */
    public static void removeAppIdFormThreadLocal() {
        appidTL.remove();
    }

    /**
     * 获取appId，先从当前线程ThreadLocal上取，为空则取默认的
     *
     * @return
     */
    public static String getAppId() {
        String appId = appidTL.get();
        if (StringUtils.isBlank(appId)) {
            appId = appConfigMap.get(DEFAULT_APPCONFIG_KEY).getAppId();
        }
        if (StringUtils.isBlank(appId)) {
            throw new IllegalStateException("需先调用ApiConfigUtils.putAppConfig(AppConfig appConfig)存入AppConfig，\n" +
                    "如存在多个AppConfig需先调用ApiConfigUtils.setAppIdToThreadLocal(String appId)线程绑定appId.");
        }
        return appId;
    }

    /**
     * 获取当前线程绑定的或默认的AppConfig
     *
     * @return
     */
    public static AppConfig getAppConfig() {
        String appId = getAppId();
        return getAppConfig(appId);
    }

    /**
     * 获取AppConfig
     *
     * @param appId
     * @return
     */
    public static AppConfig getAppConfig(String appId) {
        AppConfig cfg = appConfigMap.get(appId);
        if (cfg == null)
            throw new IllegalStateException("该appId对应的AppConfig为配置，需先调用ApiConfigUtils.putAppConfig(AppConfig appConfig)存入AppConfig.");
        return cfg;
    }

    public static boolean isDebug() {
        return ApiConfigUtils.debug;
    }

    public static void setDebug(boolean debug) {
        ApiConfigUtils.debug = debug;
    }

    public static void setAccessTokenCache(IAccessTokenCache accessTokenCache) {
        ApiConfigUtils.accessTokenCache = accessTokenCache;
    }

    public static IAccessTokenCache getAccessTokenCache() {
        return ApiConfigUtils.accessTokenCache;
    }

    public static IJsapiTicketCache getJsapiTicketCache() {
        return jsapiTicketCache;
    }

    public static void setJsapiTicketCache(IJsapiTicketCache jsapiTicketCache) {
        ApiConfigUtils.jsapiTicketCache = jsapiTicketCache;
    }
}
