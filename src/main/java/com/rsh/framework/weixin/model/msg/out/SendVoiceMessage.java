package com.rsh.framework.weixin.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送语言消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendVoiceMessage extends BaseSendMessage{

    private VoiceItem voice;

    public VoiceItem getVoice() {
        return voice;
    }

    public void setVoice(VoiceItem voice) {
        this.voice = voice;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
