package com.rsh.framework.weixin_component.cache.impl;

import com.rsh.framework.weixin_component.cache.IComponentAccessTokenCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存AccessToken实现（缓存到内存中）
 * 在分布式环境中不建议使用
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 9:42
 */
public class DefaultComponentAccessTokenCache implements IComponentAccessTokenCache {

    /**
     * key前缀
     */
    private static String KEY_PREFIX = "weixin:componentAccessToken:";

    private Map<String, String> accessTokenMap = new ConcurrentHashMap<String, String>();

    @Override
    public String getComponentAccessToken(String key) {
        key = KEY_PREFIX + key;
        return accessTokenMap.get(key);
    }

    @Override
    public boolean setComponentAccessToken(String key, String value, int expireTime) {
        key = KEY_PREFIX + key;
        accessTokenMap.put(key, value);
        return true;
    }

    @Override
    public void removeComponentAccessToken(String key) {
        key = KEY_PREFIX + key;
        accessTokenMap.remove(key);
    }
}
