package com.rsh.framework.weixin_component.api.mp.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_component.api.auth.ComponentAccessTokenApi;
import com.rsh.framework.weixin_component.model.mp.card.CardMerchantQualification;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Rsh
 * 卡券 - 第三方强授权模式
 * <p>
 * “第三方强授权模式”是腾讯为降低卡券接入门槛，商户可快速接入，由具备开发能力的开发者代商户制卡券的功能。为商户提供开发能力的开发者简称“母商户”，商户简称“子商户”。
 * 该模式下，子商户公众号无需微信认证，子商户也不需单独申请卡券功能，只需将资料提供给母商户代为提交。 该模式下，卡券的商户名和logo均为子商户的商户名和logo，且卡券创建、投放、核销等流程均只能由母商户通过调用API接口完成，子商户本身不具备直接调用卡券接口或在卡券商户后台手工操作的能力。
 * “第三方强授权”功能，面向微信开放平台所有已全网发布的第三方平台开放，第三方平台需达到一定注册资本金额，提供相关资质，经审核通过后才可获得协助制券能力。 “子商户”面向未认证的微信公众号开放，可选择已开放的卡券类目接入，子商户资质需由母商户代为提交，审核通过后可由母商户协助使用卡券功能。
 * 母商户每月可为每个子商户代制券10个cardid，且每个cardid累计sku不超过5万。具体的数量限制会随运营规则调整。
 *
 * @Description
 * @Date: 2018/1/22
 * @Time: 18:19
 */
public class AgentCardApi {

    private static final String uploadCardAgentQualificationUrl = "http://api.weixin.qq.com/cgi-bin/component/upload_card_agent_qualification?access_token=ACCESS_TOKEN";
    private static final String checkCardAgentQualificationUrl = "http://api.weixin.qq.com/cgi-bin/component/check_card_agent_qualification?access_token=ACCESS_TOKEN";
    private static final String uploadCardMerchantQualificationUrl = "http://api.weixin.qq.com/cgi-bin/component/upload_card_merchant_qualification?access_token=ACCESS_TOKEN";

    /**
     * 母商户资质申请
     * 母商户资质申请接口是第三方平台用以申请第三方强授权能力，并提交自身资质资料的上传接口，只有上传相关资质，并审核通过后才可代名下子商户提交资质。
     * 母商户提交资质包括：注册资本、营业执照（扫描件）、税务登记证（扫描件）、上季度缴税清单（扫描件）；
     * 母商户必须先上传相应扫描件获取media_id后，传入media_id。详见上传图片media接口
     * 同一个appid的申请，仅当驳回时可以再次提交，审核中和审核通过时不可重复提交
     *
     * @param registerCapital                   注册资本，数字，单位：分
     * @param businessLicenseMediaId            营业执照扫描件的media_id
     * @param taxRegistrationCertificateMediaId 税务登记证扫描件的media_id
     * @param lastQuarterTaxListingMediaId      上个季度纳税清单扫描件media_id
     * @return
     */
    public static ApiResult uploadCardAgentQualification(int registerCapital, String businessLicenseMediaId, String taxRegistrationCertificateMediaId, String lastQuarterTaxListingMediaId) {
        if (StringUtils.isBlank(businessLicenseMediaId)) {
            throw new WeixinApiException("businessLicenseMediaId Cannot be null");
        }
        if (StringUtils.isBlank(taxRegistrationCertificateMediaId)) {
            throw new WeixinApiException("taxRegistrationCertificateMediaId Cannot be null");
        }
        if (StringUtils.isBlank(lastQuarterTaxListingMediaId)) {
            throw new WeixinApiException("lastQuarterTaxListingMediaId Cannot be null");
        }

        String url = uploadCardAgentQualificationUrl.replace("ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(4);
        param.put("register_capital", registerCapital);
        param.put("business_license_media_id", businessLicenseMediaId);
        param.put("tax_registration_certificate_media_id", taxRegistrationCertificateMediaId);
        param.put("last_quarter_tax_listing_media_id", lastQuarterTaxListingMediaId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 母商户资质审核查询
     * 该接口用于查询母商户资质审核的结果，审核通过后才能用接口继续提交子商户资质。
     *
     * @return result    查询结果 1.RESULT_PASS：审核通过 2.RESULT_NOT_PASS：审核驳回 3.RESULT_CHECKING：待审核 4.RESULT_NOTHING_TO_CHECK：无提审记录
     */
    public static ApiResult checkCardAgentQualification() {
        String url = checkCardAgentQualificationUrl.replace("ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 子商户资质申请
     * 母商户（第三方平台）申请获得第三方强授权能力后，才可提交名下子商户的资质。
     * 子商户资质审核通过后，才可进行后续的授权流程。
     *
     * @param cardMerchantQualification 子商户资质信息
     * @return
     */
    public static ApiResult uploadCardMerchantQualification(CardMerchantQualification cardMerchantQualification) {
        if (cardMerchantQualification == null) {
            throw new WeixinApiException("cardMerchantQualification Cannot be null");
        }

        String url = uploadCardMerchantQualificationUrl.replace("ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());

        String json = HttpUtils.post(url, cardMerchantQualification.getJson());
        return new ApiResult(json);
    }

}
