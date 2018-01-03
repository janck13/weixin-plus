package com.rsh.framework.weixin.api.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.card.CardPayOrderStatus;
import com.rsh.framework.weixin.model.card.CardPayOrderType;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 朋友的劵 API
 * “朋友共享的优惠券”（简称“朋友的券”），是基于微信优惠券推出的新功能，实现“一人领取多人共享”的快速社交传播和转化的效果。
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/3
 * @Time: 13:41
 */
public class FriendCardApi {

    private static final String activateCardPayUrl = "https://api.weixin.qq.com/card/pay/activate?access_token=ACCESS_TOKEN";
    private static final String getPaypriceUrl = "https://api.weixin.qq.com/card/pay/getpayprice?access_token=ACCESS_TOKEN";
    private static final String getCoinsinfoUrl = "https://api.weixin.qq.com/card/pay/getcoinsinfo?access_token=ACCESS_TOKEN";
    private static final String confirmUrl = "https://api.weixin.qq.com/card/pay/confirm?access_token=ACCESS_TOKEN";
    private static final String rechargeUrl = "https://api.weixin.qq.com/card/pay/recharge?access_token=ACCESS_TOKEN";
    private static final String getOrderUrl = "https://api.weixin.qq.com/card/pay/getorder?access_token=ACCESS_TOKEN";
    private static final String getOrderListUrl = "https://api.weixin.qq.com/card/pay/getorderlist?access_token=ACCESS_TOKEN";


    /**
     * 开通券点账户
     * 本接口用于开发者为当前appid开通券点账户并获得免费券点奖励
     *
     * @return
     */
    public static ApiResult activateCardPay() {
        String url = activateCardPayUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 对优惠券批价
     * 本接口用于提前查询本次新增库存需要多少券点
     * 注意：接口返回的order_id须在60s内使用，否则确认兑换库存接口将会失效
     *
     * @param cardId   需要来配置库存的card_id
     * @param quantity 本次需要兑换的库存数目
     * @return
     */
    public static ApiResult getPayprice(String cardId, int quantity) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }

        String url = getPaypriceUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("quantity", quantity);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询券点余额
     * 本接口用于开发者查询当前券点账户中的免费券点/付费券点数目以及总额。
     *
     * @return
     */
    public static ApiResult getCoinsinfo() {
        String url = getCoinsinfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 确认兑换库存
     * 本接口用于确认兑换库存，确认后券点兑换为库存，过程不可逆。
     *
     * @param cardId   需要来配置库存的card_id
     * @param quantity 本次需要兑换的库存数目
     * @param orderId  对优惠券批价接口返回得到的订单号，保证批价有效性
     * @return
     */
    public static ApiResult confirm(String cardId, int quantity, String orderId) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        if (StringUtils.isBlank(orderId)) {
            throw new WeixinApiException("orderId Cannot be null");
        }

        String url = confirmUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("quantity", quantity);
        param.put("order_id", orderId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 充值券点
     * 开发者可以通过此接口为券点账户充值券点，1元等于1点。开发者调用接口后可以获得一个微信支付的支付二维码链接，开发者可以将链接转化为二维码扫码支付。
     *
     * @param coinCount 需要充值的券点数目，1点=1元
     * @return
     */
    public static ApiResult recharge(int coinCount) {
        if (coinCount <= 0) {
            throw new WeixinApiException("coinCount min 1");
        }

        String url = rechargeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("coin_count", coinCount);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询订单详情
     * 本接口用于查询充值订单的状态
     *
     * @param orderId 订单号，充值券点接口返回
     * @return
     */
    public static ApiResult getOrder(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new WeixinApiException("orderId Cannot be null");
        }

        String url = getOrderUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("order_id", orderId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询券点流水详情
     * 本接口用于查询券点的流水详情。
     *
     * @param cardPayOrderType   劵点订单类型，默认查询全部
     * @param cardPayOrderStatus 劵点订单状态，默认查询全部
     * @param beginTime          查询订单的起始时间，默认1周前
     * @param endTime            查询订单的结束时间，默认为当前时间
     * @param offset             查询起点，默认0
     * @param count              查询数量，默认1
     * @return
     */
    public static ApiResult getOrderList(CardPayOrderType cardPayOrderType, CardPayOrderStatus cardPayOrderStatus, Date beginTime, Date endTime, int offset, int count) {
        if (offset < 0) {
            offset = 0;
        }
        if (count < 1) {
            count = 1;
        }
        String url = getOrderListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("count", count);
        if (cardPayOrderType != null) {
            param.put("order_type", cardPayOrderType.name());
        }
        if (cardPayOrderStatus != null) {
            Map<String, Object> norfilterMap = new HashMap<>();
            norfilterMap.put("status", cardPayOrderStatus.name());
            param.put("nor_filter", norfilterMap);
        }
        Map<String, Object> sortInfoMap = new HashMap<>();
        sortInfoMap.put("sort_key", "SORT_BY_TIME");
        sortInfoMap.put("sort_type", "SORT_DESC");
        param.put("sort_info", sortInfoMap);
        param.put("begin_time", beginTime.getTime());
        param.put("end_time", endTime.getTime());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
