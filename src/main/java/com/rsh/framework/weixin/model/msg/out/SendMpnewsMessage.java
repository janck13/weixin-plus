package com.rsh.framework.weixin.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送图文消息（点击跳转到图文消息页面） 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应。
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendMpnewsMessage extends BaseSendMessage{

    private MpnewsItem mpnews;

    public MpnewsItem getMpnews() {
        return mpnews;
    }

    public void setMpnews(MpnewsItem mpnews) {
        this.mpnews = mpnews;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
