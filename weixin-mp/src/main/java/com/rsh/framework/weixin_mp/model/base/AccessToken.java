package com.rsh.framework.weixin_mp.model.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rsh.framework.weixin.common.model.ApiResultErrorCode;
import com.rsh.framework.weixin.utils.RetryUtils;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/22
 * @Time: 15:02
 */
public class AccessToken implements RetryUtils.ResultCheck {

    private String token; // 凭证
    private Integer expiresIn; // access_token接口调用凭证超时时间，单位（秒）
    private Integer errcode; // 出错时有值
    private String errmsg;  // 出错时有值

    private Long expiredTime; // 正确获取到 access_token 时有值，存放过期时间
    private String json;

    public AccessToken(String json) {
        this.json = json;
        try {
            JSONObject jb = JSON.parseObject(json);
            token = jb.getString("access_token");
            expiresIn = jb.getInteger("expires_in");
            errcode = jb.getInteger("errcode");
            errmsg = jb.getString("errmsg");

            if (expiresIn != null) {
                expiredTime = System.currentTimeMillis() + ((expiresIn - 5) * 1000);
            }

            // 通过缓存获取到的json反序列化为AccessToken
            if (jb.containsKey("expiredTime")) {
                expiredTime = jb.getLongValue("expiredTime");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取AccessToken缓存存入的json字符串
     * 放入expiredTime过期时间（该时间已减去5秒延迟），
     * 去除expires_in（通过AccessToken构造函数实例化AccessToken时会把expires_in减去5秒延迟，因为减去5秒延迟只应该是在第一次通过接口获取AccessToken时才能减）
     *
     * @return
     */
    public String getCacheJson() {
        JSONObject jb = JSON.parseObject(json);
        jb.put("expiredTime", expiredTime);
        jb.remove("expires_in");
        return jb.toJSONString();
    }

    /**
     * 判断access_token是否有效
     *
     * @return
     */
    public boolean isAvailable() {
        if (expiredTime == null) {
            return false;
        }
        if (errcode != null) {
            return false;
        }
        if (expiredTime < System.currentTimeMillis()) {
            return false;
        }
        return token != null;
    }

    @Override
    public boolean isSuccessful() {
        return isAvailable();
    }

    @Override
    public String getJson() {
        return json;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        if (errcode != null) {
            String result = ApiResultErrorCode.get(errcode);
            if (result != null) {
                return result;
            }
        }
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

}
