package com.rsh.framework.weixin.model.card;

/**
 * 劵点订单类型
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/3
 * @Time: 17:00
 */
public enum CardPayOrderType {

    ORDER_TYPE_SYS_ADD, // 平台赠送
    ORDER_TYPE_WXPAY, // 充值
    ORDER_TYPE_REFUND, // 库存回退
    ORDER_TYPE_REDUCE, // 券点兑换库存
    ORDER_TYPE_SYS_REDUCE; // 平台扣减
}
