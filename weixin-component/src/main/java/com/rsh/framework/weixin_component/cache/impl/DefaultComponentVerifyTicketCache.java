package com.rsh.framework.weixin_component.cache.impl;

import com.rsh.framework.weixin_component.cache.IComponentAccessTokenCache;
import com.rsh.framework.weixin_component.cache.IComponentVerifyTicketCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存component_verify_ticket实现（缓存到内存中）
 * 在分布式环境中不建议使用
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 9:42
 */
public class DefaultComponentVerifyTicketCache implements IComponentVerifyTicketCache {

    /**
     * key前缀
     */
    private static String KEY_PREFIX = "weixin:componentVerifyTicket:";

    private Map<String, String> verifyTicketMap = new ConcurrentHashMap<String, String>();

    @Override
    public String getComponentVerifyTicket(String key) {
        key = KEY_PREFIX + key;
        return verifyTicketMap.get(key);
    }

    @Override
    public boolean setComponentVerifyTicket(String key, String value, int expireTime) {
        key = KEY_PREFIX + key;
        verifyTicketMap.put(key, value);
        return true;
    }

    @Override
    public void removeComponentVerifyTicket(String key) {
        key = KEY_PREFIX + key;
        verifyTicketMap.remove(key);
    }
}
