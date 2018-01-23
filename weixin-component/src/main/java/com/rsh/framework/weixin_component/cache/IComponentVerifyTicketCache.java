package com.rsh.framework.weixin_component.cache;

/**
 * component_verify_ticket 缓存
 * 可以通过实现该接口扩展不同的实现，现支持（内存，redis）
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 18:28
 */
public interface IComponentVerifyTicketCache {

    /**
     * 获取component_verify_ticket
     *
     * @param key
     * @return
     */
    String getComponentVerifyTicket(String key);

    /**
     * 缓存component_verify_ticket
     *
     * @param key
     * @param value
     * @param expireTime 过期时间（单位：秒）
     */
    boolean setComponentVerifyTicket(String key, String value, int expireTime);

    /**
     * 删除component_verify_ticket
     *
     * @param key
     */
    void removeComponentVerifyTicket(String key);

}
