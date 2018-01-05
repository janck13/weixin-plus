package com.rsh.framework.weixin_mp.model.msg;

/**
 * 消息类型
 * Created By Rsh
 * @Description
 * @Date: 2017/12/20
 * @Time: 18:20
 */
public enum OutMsgType {
    text, // 文本
    image, // 图片
    voice, // 语言
    video, // 视频
    music, // 音乐
    news, // 图文消息（点击跳转到外链）
    mpnews, // 图文消息（点击跳转到图文消息页面）
    wxcard, // 卡券
    miniprogrampage; // 小程序卡片

}
