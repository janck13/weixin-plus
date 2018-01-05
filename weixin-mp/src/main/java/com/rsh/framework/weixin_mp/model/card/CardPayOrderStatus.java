package com.rsh.framework.weixin_mp.model.card;

/**
 * 劵点订单状态
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/3
 * @Time: 17:00
 */
public enum CardPayOrderStatus {

    ORDER_STATUS_WAITING, // 等待支付
    ORDER_STATUS_SUCC, // 支付成功
    ORDER_STATUS_FINANCE_SUCC, // 加代币成功
    ORDER_STATUS_QUANTITY_SUCC, // 加库存成功
    ORDER_STATUS_HAS_REFUND, // 已退币
    ORDER_STATUS_REFUND_WAITING, // 等待退币确认
    ORDER_STATUS_ROLLBACK, // 已回退,系统失败
    ORDER_STATUS_HAS_RECEIPT; // 已开发票
}
