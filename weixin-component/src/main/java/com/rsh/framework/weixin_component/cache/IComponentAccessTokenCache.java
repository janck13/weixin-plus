package com.rsh.framework.weixin_component.cache;

/**
 * component_access_token 缓存
 * 可以通过实现该接口扩展不同的实现，现支持（内存，redis）
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 18:28
 */
public interface IComponentAccessTokenCache {

    /**
     * 获取component_access_token
     *
     * @param key
     * @return
     */
    String getComponentAccessToken(String key);

    /**
     * 缓存component_access_token
     *
     * @param key
     * @param value
     * @param expireTime 过期时间（单位：秒）
     */
    boolean setComponentAccessToken(String key, String value, int expireTime);

    /**
     * 删除component_access_token
     *
     * @param key
     */
    void removeComponentAccessToken(String key);

}
