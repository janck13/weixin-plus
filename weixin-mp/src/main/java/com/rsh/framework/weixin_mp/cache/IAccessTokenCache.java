package com.rsh.framework.weixin_mp.cache;

/**
 * access_token 缓存
 * 可以通过实现该接口扩展不同的实现，现支持（内存，redis）
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 18:28
 */
public interface IAccessTokenCache {

    /**
     * 获取AccessToken
     *
     * @param key
     * @return
     */
    String getAccessToken(String key);

    /**
     * 缓存AccessToken
     *
     * @param key
     * @param value
     * @param expireTime 过期时间（单位：秒）
     */
    boolean setAccessToken(String key, String value, int expireTime);

    /**
     * 删除AccessToken
     *
     * @param key
     */
    void removeAccessToken(String key);

}
