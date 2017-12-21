package com.rsh.framework.weixin.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送图片消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendImageMessage extends BaseSendMessage{

    private ImageItem image;

    public ImageItem getImage() {
        return image;
    }

    public void setImage(ImageItem image) {
        this.image = image;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
