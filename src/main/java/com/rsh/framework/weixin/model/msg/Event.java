package com.rsh.framework.weixin.model.msg;

/**
 * 事件类型
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 9:23
 */
public enum Event {
    subscribe,  // 订阅
    unsubscribe, // 取消订阅
    SCAN, // 扫描带参数二维码已订阅
    LOCATION, //  上报地理位置
    CLICK, // 点击菜单拉取消息
    VIEW, // 点击菜单跳转链接
    scancode_push, // 扫码推事件
    scancode_waitmsg, // 扫码推事件且弹出“消息接收中”提示框的事件推送
    pic_sysphoto, // 弹出系统拍照发图的事件推送
    pic_photo_or_album, // 弹出拍照或者相册发图的事件推送
    pic_weixin, // 弹出微信相册发图器的事件推送
    location_select, // 弹出地理位置选择器的事件推送
    MASSSENDJOBFINISH, // 群发结果推送
    TEMPLATESENDJOBFINISH, // 模板消息发送结束推送
    qualification_verify_success, // 资质认证成功
    qualification_verify_fail, // 资质认证失败
    naming_verify_success, // 名称认证成功
    naming_verify_fail, // 名称认证失败
    annual_renew, // 年审通知
    verify_expired, // 认证过期失效通知
}
