package com.rsh.framework.weixin_component.cache.impl;

import com.rsh.framework.weixin_component.cache.IComponentAccessTokenCache;
import com.rsh.framework.weixin_component.cache.IComponentVerifyTicketCache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.UnsupportedEncodingException;

/**
 * redis缓存component_verify_ticket实现
 * 使用redis集中管理component_verify_ticket，分布式环境中使用
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 9:31
 */
public class RedisComponentVerifyTicketCache implements IComponentVerifyTicketCache {

    /**
     * key前缀
     */
    private static String KEY_PREFIX = "weixin:componentVerifyTicket:";
    /**
     * StringRedisTemplate
     */
    private StringRedisTemplate redisTemplate;

    public RedisComponentVerifyTicketCache(StringRedisTemplate redisTemplate) {
        if (redisTemplate == null) {
            throw new IllegalArgumentException("redisTemplate 为空！");
        }
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getComponentVerifyTicket(String key) {
        String newkey = KEY_PREFIX + key;
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] tmp = connection.get(newkey.getBytes("UTF-8"));
                    if (tmp == null) {
                        return null;
                    } else {
                        return new String(tmp, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public boolean setComponentVerifyTicket(String key, String value, int expireTime) {
        String newkey = KEY_PREFIX + key;
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.setEx(newkey.getBytes("UTF-8"), expireTime, value.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public void removeComponentVerifyTicket(String key) {
        String newkey = KEY_PREFIX + key;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.del(newkey.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return 0L;
            }
        });
    }
}
