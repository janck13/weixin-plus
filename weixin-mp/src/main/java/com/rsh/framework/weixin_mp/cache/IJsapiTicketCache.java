package com.rsh.framework.weixin_mp.cache;

/**
 * jsapi_ticket 缓存
 * 可以通过实现该接口扩展不同的实现，现支持（内存，redis）
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 18:28
 */
public interface IJsapiTicketCache {

    /**
     * 获取jsapi_ticket
     *
     * @param key
     * @return
     */
    String getJsapiTicket(String key);

    /**
     * 缓存jsapi_ticket
     *
     * @param key
     * @param value
     * @param expireTime 过期时间（单位：秒）
     */
    boolean setJsapiTicket(String key, String value, int expireTime);

    /**
     * 删除jsapi_ticket
     *
     * @param key
     */
    void removeJsapiTicket(String key);

}
