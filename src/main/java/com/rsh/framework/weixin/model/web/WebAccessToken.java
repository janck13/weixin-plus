package com.rsh.framework.weixin.model.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rsh.framework.weixin.model.ApiResultErrorCode;
import com.rsh.framework.weixin.utils.RetryUtils;

import java.util.Map;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/22
 * @Time: 15:02
 */
public class WebAccessToken implements RetryUtils.ResultCheck {

    private String accessToken;
    private Integer expiresIn; // access_token接口调用凭证超时时间，单位（秒）
    private String refreshToken;
    private String openid;
    private String scope;
    private Integer errcode; // 出错时有值
    private String errmsg;  // 出错时有值

    private Long expiredTime; // 正确获取到 access_token 时有值，存放过期时间
    private String json;

    public WebAccessToken(String json) {
        this.json = json;
        try {
            JSONObject jb = JSON.parseObject(json);
            accessToken = jb.getString("access_token");
            expiresIn = jb.getInteger("expires_in");
            refreshToken = jb.getString("refresh_token");
            openid = jb.getString("openid");
            scope = jb.getString("scope");
            errcode = jb.getInteger("errcode");
            errmsg = jb.getString("errmsg");

            if (expiresIn != null)
                expiredTime = System.currentTimeMillis() + ((expiresIn - 5) * 1000);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAvailable() {
        if (expiredTime == null)
            return false;
        if (errcode != null)
            return false;
        if (expiredTime < System.currentTimeMillis())
            return false;
        return accessToken != null;
    }

    @Override
    public boolean isSuccessful() {
        return isAvailable();
    }

    @Override
    public String getJson() {
        return json;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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
            if (result != null)
                return result;
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
