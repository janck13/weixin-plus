package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送音乐消息
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendMusicMessage extends BaseSendMessage{

    private MusicItem music;

    public MusicItem getMusic() {
        return music;
    }

    public void setMusic(MusicItem music) {
        this.music = music;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
