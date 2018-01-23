package com.rsh.framework.weixin_component.api;

import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_component.cache.IComponentAccessTokenCache;
import com.rsh.framework.weixin_component.cache.IComponentVerifyTicketCache;
import com.rsh.framework.weixin_component.cache.impl.DefaultComponentAccessTokenCache;
import com.rsh.framework.weixin_component.cache.impl.DefaultComponentVerifyTicketCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第三方平台 配置工具类
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 17:15
 */
public class ApiConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(ApiConfigUtils.class);

    // 将componentAppid绑定到ThreadLocal，以方便在当前线程的各个地方获取ComponentAppConfig对象：
    private static final ThreadLocal<String> appidTL = new ThreadLocal<String>();
    private static final Map<String, ComponentAppConfig> appConfigMap = new ConcurrentHashMap<String, ComponentAppConfig>();
    // 默认ComponentAppConfig key
    private static final String DEFAULT_APPCONFIG_KEY = "default_componentAppConfig_key";

    // component_access_token缓存机制使用默认缓存实现
    private static IComponentAccessTokenCache componentAccessTokenCache = new DefaultComponentAccessTokenCache();

    // component_verify_ticket缓存机制使用默认缓存实现
    private static IComponentVerifyTicketCache componentVerifyTicketCache = new DefaultComponentVerifyTicketCache();


    // debug模式将输出API请求报文和响应报文
    private static boolean debug = false;

    /**
     * 添加第三方平台配置，每个componentAppid只需添加一次，相同componentAppid将被覆盖。
     * 第一个添加的将作为默认配置
     *
     * @param componentAppConfig 公众号配置
     * @return
     */
    public static ComponentAppConfig putAppConfig(ComponentAppConfig componentAppConfig) {
        if (appConfigMap.size() == 0) {
            appConfigMap.put(DEFAULT_APPCONFIG_KEY, componentAppConfig);
        }
        return appConfigMap.put(componentAppConfig.getComponentAppid(), componentAppConfig);
    }

    public static ComponentAppConfig removeAppConfig(String componentAppid) {
        return appConfigMap.remove(componentAppid);
    }

    /**
     * 将默认componentAppid绑定到当前线程
     */
    public static void setComponentAppidToThreadLocal() {
        String componentAppid = appConfigMap.get(DEFAULT_APPCONFIG_KEY).getComponentAppid();
        setComponentAppidToThreadLocal(componentAppid);
    }

    /**
     * 将componentAppid绑定到当前线程
     *
     * @param componentAppid
     */
    public static void setComponentAppidToThreadLocal(String componentAppid) {
        if (StringUtils.isBlank(componentAppid)) {
            throw new WeixinApiException("componentAppid Cannot be null");
        }
        if (appConfigMap.get(componentAppid) == null) {
            throw new WeixinApiException("该componentAppid未配置，需先调用ApiConfigUtils.putAppConfig(ComponentAppConfig componentAppConfig)存入ComponentAppConfig.");
        }
        appidTL.set(componentAppid);
    }

    /**
     * 移除当前线程绑定的componentAppid
     */
    public static void removeComponentAppidToThreadLocal() {
        appidTL.remove();
    }

    /**
     * 获取componentAppid，先从当前线程ThreadLocal上取，为空则取默认的
     *
     * @return
     */
    public static String getComponentAppid() {
        String componentAppid = appidTL.get();
        if (StringUtils.isBlank(componentAppid)) {
            componentAppid = appConfigMap.get(DEFAULT_APPCONFIG_KEY).getComponentAppid();
        }
        if (StringUtils.isBlank(componentAppid)) {
            throw new WeixinApiException("需先调用ApiConfigUtils.putAppConfig(ComponentAppConfig componentAppConfig)存入ComponentAppConfig，\n" +
                    "如存在多个ComponentAppConfig需先调用ApiConfigUtils.setAppIdToThreadLocal(String componentAppid)线程绑定componentAppid.");
        }
        return componentAppid;
    }

    /**
     * 获取当前线程绑定的或默认的ComponentAppConfig
     *
     * @return
     */
    public static ComponentAppConfig getComponentAppConfig() {
        String componentAppid = getComponentAppid();
        return getComponentAppConfig(componentAppid);
    }

    /**
     * 获取ComponentAppConfig
     *
     * @param componentAppid
     * @return
     */
    public static ComponentAppConfig getComponentAppConfig(String componentAppid) {
        ComponentAppConfig cfg = appConfigMap.get(componentAppid);
        if (cfg == null) {
            throw new WeixinApiException("该componentAppid对应的ComponentAppConfig为配置，需先调用ApiConfigUtils.putAppConfig(ComponentAppConfig componentAppConfig)存入ComponentAppConfig.");
        }
        return cfg;
    }

    public static boolean isDebug() {
        return ApiConfigUtils.debug;
    }

    public static void setDebug(boolean debug) {
        ApiConfigUtils.debug = debug;
    }

    public static void setComponentAccessTokenCache(IComponentAccessTokenCache componentAccessTokenCache) {
        if (componentAccessTokenCache == null) {
            throw new WeixinApiException("componentAccessTokenCache Cannot be null");
        }
        ApiConfigUtils.componentAccessTokenCache = componentAccessTokenCache;
    }

    public static IComponentAccessTokenCache getComponentAccessTokenCache() {
        return ApiConfigUtils.componentAccessTokenCache;
    }

    public static void setComponentVerifyTicketCache(IComponentVerifyTicketCache componentVerifyTicketCache) {
        if (componentVerifyTicketCache == null) {
            throw new WeixinApiException("componentVerifyTicketCache Cannot be null");
        }
        ApiConfigUtils.componentVerifyTicketCache = componentVerifyTicketCache;
    }

    public static IComponentVerifyTicketCache getComponentVerifyTicketCache() {
        return componentVerifyTicketCache;
    }

}
