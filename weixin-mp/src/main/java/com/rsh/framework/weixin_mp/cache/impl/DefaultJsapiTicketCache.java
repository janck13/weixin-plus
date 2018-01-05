package com.rsh.framework.weixin_mp.cache.impl;

import com.rsh.framework.weixin_mp.cache.IJsapiTicketCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存jsapi_ticket实现（缓存到内存中）
 * 在分布式环境中不建议使用
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 9:42
 */
public class DefaultJsapiTicketCache implements IJsapiTicketCache {

    /**
     * key前缀
     */
    private static String KEY_PREFIX = "weixin:jsapiTicket:";

    private Map<String, String> accessTokenMap = new ConcurrentHashMap<String, String>();

    @Override
    public String getJsapiTicket(String key) {
        key = KEY_PREFIX + key;
        return accessTokenMap.get(key);
    }

    @Override
    public boolean setJsapiTicket(String key, String value, int expireTime) {
        key = KEY_PREFIX + key;
        accessTokenMap.put(key, value);
        return true;
    }

    @Override
    public void removeJsapiTicket(String key) {
        key = KEY_PREFIX + key;
        accessTokenMap.remove(key);
    }
}
