package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送视频消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendVideoMessage extends BaseSendMessage{

    private VideoItem video;

    public VideoItem getVideo() {
        return video;
    }

    public void setVideo(VideoItem video) {
        this.video = video;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
