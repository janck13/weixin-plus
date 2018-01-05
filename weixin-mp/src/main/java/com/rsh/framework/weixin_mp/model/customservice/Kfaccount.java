package com.rsh.framework.weixin_mp.model.customservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 17:30
 */
public class Kfaccount {

    @JSONField(name = "kf_account")
    private String kfAccount;
    @JSONField(name = "nickname")
    private String nickname;
    @JSONField(name = "password")
    private String password;

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }

}
