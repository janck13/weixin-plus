package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送卡券
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendWxcardMessage extends BaseSendMessage{

    private WxcardItem wxcard;

    public WxcardItem getWxcard() {
        return wxcard;
    }

    public void setWxcard(WxcardItem wxcard) {
        this.wxcard = wxcard;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
