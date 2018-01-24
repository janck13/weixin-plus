package com.rsh.framework.weixin_mp.api.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Rsh
 * 卡券-第三方代制模式
 * 为了降低商户接入卡券的难度，微信公众平台向所有已具备卡券功能的公众号开放“第三方代制”功能。
 * 申请并开通此功能后，具备开发能力的开发者，可通过API接口协助无公众号的商户快速接入并使用卡券。协助制券的开发者称为“母商户”，被协助制券的商户称为“子商户”。
 * 母商户需将旗下子商户资料提前上传报备，通过审核方可生效。在制券过程中允许母商户从报备的子商户列表中，选择一个子商户协助制券。
 *
 * @Description
 * @Date: 2018/1/22
 * @Time: 18:19
 */
public class ThirdPartyMakeApi {

    private static final String createSubmerchantUrl = "https://api.weixin.qq.com/card/submerchant/submit?access_token=ACCESS_TOKEN";
    private static final String getapplyprotocolUrl = "https://api.weixin.qq.com/card/getapplyprotocol?access_token=ACCESS_TOKEN";
    private static final String updateSubmerchantUrl = "https://api.weixin.qq.com/card/submerchant/update?access_token=ACCESS_TOKEN";
    private static final String getSubmerchantUrl = "https://api.weixin.qq.com/card/submerchant/get?access_token=ACCESS_TOKEN";
    private static final String batchGetSubmerchantUrl = "https://api.weixin.qq.com/card/submerchant/batchget?access_token=ACCESS_TOKEN";

    /**
     * 创建子商户
     * 支持母商户调用该接口传入子商户的相关资料，并获取子商户ID，用于子商户的卡券功能管理。 子商户的资质包括：商户名称、商户logo（图片）、卡券类目、授权函（扫描件或彩照）、授权函有效期截止时间。
     *
     * @param jsonData Json数据
     * @return
     */
    public static ApiResult createSubmerchant(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = createSubmerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 卡券开放类目查询
     * 通过调用该接口查询卡券开放的类目ID，类目会随业务发展变更，请每次用接口去查询获取实时卡券类目。
     *
     * @return
     */
    public static ApiResult getapplyprotocol() {
        String url = getapplyprotocolUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 更新子商户
     *
     * @param jsonData Json数据
     * @return
     */
    public static ApiResult updateSubmerchant(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = updateSubmerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 拉取单个子商户信息
     *
     * @param merchantId 子商户id
     * @return
     */
    public static ApiResult getSubmerchant(Integer merchantId) {
        if (merchantId == null) {
            throw new WeixinApiException("merchantId Cannot be null");
        }

        String url = getSubmerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("merchant_id", merchantId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 批量拉取子商户信息
     * 母商户可以通过该接口批量拉取子商户的相关信息，一次调用最多拉取100个子商户的信息，可以通过多次拉去满足不同的查询需求
     *
     * @param beginId 起始的子商户id
     * @param limit   拉取的子商户的个数，最大值为100
     * @param status  子商户审核状态,"CHECKING" 审核中, "APPROVED" , 已通过；"REJECTED"被驳回, "EXPIRED"协议已过期
     * @return
     */
    public static ApiResult batchGetSubmerchant(int beginId, int limit, String status) {
        if (limit > 100) {
            throw new WeixinApiException("limit max 100");
        }

        String url = batchGetSubmerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(3);
        param.put("begin_id", beginId);
        param.put("limit", limit);
        param.put("status", status);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
