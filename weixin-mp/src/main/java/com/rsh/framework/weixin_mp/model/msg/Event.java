package com.rsh.framework.weixin_mp.model.msg;

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
    user_pay_from_pay_cell, // 微信卡券买单事件推送
    card_pass_check, // 卡券通过审核
    card_not_pass_check, // 卡券未通过审核
    user_get_card, // 用户领取卡券
    user_gifting_card, // 用户转赠卡券
    user_del_card, // 用户删除卡券
    user_consume_card, // 核销事件
    user_view_card, // 用户点击会员卡
    user_enter_session_from_card, // 从卡券进入公众号会话事件推送
    update_member_card, // 会员卡内容更新事件
    card_sku_remind, // 库存报警
    card_pay_order, // 券点流水详情事件
    submit_membercard_user_info, // 会员卡激活事件推送
    card_merchant_check_result, // 卡券第三方开发者模式 — 子商户审核事件
    poi_check_notify, // 新建门店审核事件推送
    apply_merchant_audit_info, // 创建门店小程序的审核结果事件推送
    create_map_poi_audit_info, // 腾讯地图中创建门店的审核结果事件推送
    add_store_audit_info, // 创建门店的审核结果事件推送
    modify_store_audit_info, // 修改门店图片的审核结果事件推送
}
