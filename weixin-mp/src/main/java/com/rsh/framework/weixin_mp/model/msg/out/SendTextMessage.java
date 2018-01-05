package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送文本消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendTextMessage extends BaseSendMessage{

    private TextItem text;

    public TextItem getText() {
        return text;
    }

    public void setText(TextItem text) {
        this.text = text;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
