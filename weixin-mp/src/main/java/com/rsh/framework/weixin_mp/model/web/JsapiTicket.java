package com.rsh.framework.weixin_mp.model.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rsh.framework.weixin.utils.RetryUtils;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/25
 * @Time: 15:55
 */
public class JsapiTicket implements RetryUtils.ResultCheck {

    private Integer errcode;
    private String errmsg;
    private String ticket;
    private Integer expiresIn;

    private Long expiredTime; // 正确获取到 jsapi_ticket 时有值，存放过期时间
    private String json;

    public JsapiTicket(String json) {
        this.json = json;
        try {
            JSONObject jb = JSON.parseObject(json);
            errcode = jb.getInteger("errcode");
            errmsg = jb.getString("errmsg");
            ticket = jb.getString("ticket");
            expiresIn = jb.getInteger("expires_in");

            if (expiresIn != null) {
                expiredTime = System.currentTimeMillis() + ((expiresIn - 5) * 1000);
            }

            // 通过缓存获取到的json反序列化为JsapiTicket
            if (jb.containsKey("expiredTime")) {
                expiredTime = jb.getLongValue("expiredTime");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取jsapi_ticket缓存存入的json字符串
     * 放入expiredTime过期时间（该时间已减去5秒延迟），
     * 去除expires_in（通过AccessToken构造函数实例化JsapiTicket时会把expires_in减去5秒延迟，因为减去5秒延迟只应该是在第一次通过接口获取jsapi_ticket时才能减）
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
     * 判断jsapi_ticket是否有效
     *
     * @return
     */
    public boolean isAvailable() {
        if (expiredTime == null)
            return false;
        if (errcode == null || errcode != 0)
            return false;
        if (expiredTime < System.currentTimeMillis())
            return false;
        return ticket != null;
    }

    @Override
    public boolean isSuccessful() {
        return isAvailable();
    }

    @Override
    public String getJson() {
        return json;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
