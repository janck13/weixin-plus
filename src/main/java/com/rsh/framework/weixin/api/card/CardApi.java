package com.rsh.framework.weixin.api.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.card.CardLandingPage;
import com.rsh.framework.weixin.model.card.QrcardInfo;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卡券 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 14:52
 */
public class CardApi {

    private static final String createCardUrl = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
    private static final String setCardPaycellUrl = "https://api.weixin.qq.com/card/paycell/set?access_token=ACCESS_TOKEN";
    private static final String setCardSelfconsumeCellUrl = "https://api.weixin.qq.com/card/selfconsumecell/set?access_token=ACCESS_TOKEN";
    private static final String createCardQrcodeUrl = "https://api.weixin.qq.com/card/qrcode/create?access_token=ACCESS_TOKEN";
    private static final String createCardLandingPageUrl = "https://api.weixin.qq.com/card/landingpage/create?access_token=ACCESS_TOKEN";
    private static final String depositCardCodeUrl = "http://api.weixin.qq.com/card/code/deposit?access_token=ACCESS_TOKEN";
    private static final String getDepositcountCardCodeUrl = "http://api.weixin.qq.com/card/code/getdepositcount?access_token=ACCESS_TOKEN";
    private static final String checkCardCodeUrl = "http://api.weixin.qq.com/card/code/checkcode?access_token=ACCESS_TOKEN";
    private static final String getCardNewsHtmlUrl = "https://api.weixin.qq.com/card/mpnews/gethtml?access_token=ACCESS_TOKEN";
    private static final String setCardTestwhiteUrl = "https://api.weixin.qq.com/card/testwhitelist/set?access_token=ACCESS_TOKEN";

    /**
     * 创建卡券
     *
     * @param jsonData Json数据
     * @return
     */
    public static ApiResult createCard(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = createCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 设置卡券买单功能
     *
     * @param cardId 卡券ID
     * @param isOpen 是否开启买单功能，填true/false
     * @return
     */
    public static ApiResult setCardPaycell(String cardId, boolean isOpen) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        String url = setCardPaycellUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("is_open", isOpen);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 设置卡券自助核销
     *
     * @param cardId           卡券ID
     * @param isOpen           是否开启买单功能，填true/false
     * @param needVerifyCod    用户核销时是否需要输入验证码， 填true/false， 默认为fals
     * @param needRemarkAmount 用户核销时是否需要备注核销金额， 填true/false， 默认为false
     * @return
     */
    public static ApiResult setCardSelfconsumeCell(String cardId, boolean isOpen, boolean needVerifyCod, boolean needRemarkAmount) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        String url = setCardSelfconsumeCellUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("is_open", isOpen);
        param.put("need_verify_cod", needVerifyCod);
        param.put("need_remark_amount", needRemarkAmount);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 创建卡券二维码
     * 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
     * 自定义Code码的卡券调用接口时，POST数据中需指定code，非自定义code不需指定，指定openid同理。指定后的二维码只能被用户扫描领取一次。
     *
     * @param qrcardInfo
     * @return
     */
    public static ApiResult createCardQrcode(QrcardInfo qrcardInfo) {
        return createCardQrcode(qrcardInfo, null);
    }

    /**
     * 创建卡券二维码
     * 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
     * 自定义Code码的卡券调用接口时，POST数据中需指定code，非自定义code不需指定，指定openid同理。指定后的二维码只能被用户扫描领取一次。
     *
     * @param qrcardInfo
     * @param expireSeconds 指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
     * @return
     */
    public static ApiResult createCardQrcode(QrcardInfo qrcardInfo, Integer expireSeconds) {
        if (qrcardInfo == null) {
            throw new WeixinApiException("qrcardInfo Cannot be null");
        }
        String cardId = qrcardInfo.getCardId();

        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("action_name", "QR_CARD");
        if (expireSeconds != null) {
            param.put("expire_seconds", "expireSeconds");
        }

        Map<String, Object> actionInfoMap = new HashMap<>();
        actionInfoMap.put("card", qrcardInfo);

        param.put("action_info", actionInfoMap);

        String url = createCardQrcodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 创建多张卡券二维码
     * 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
     * 自定义Code码的卡券调用接口时，POST数据中需指定code，非自定义code不需指定，指定openid同理。指定后的二维码只能被用户扫描领取一次。
     *
     * @param qrcardInfoList
     * @return
     */
    public static ApiResult createMultipleCardQrcode(List<QrcardInfo> qrcardInfoList) {
        return createMultipleCardQrcode(qrcardInfoList, null);
    }

    /**
     * 创建多张卡券二维码
     * 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
     * 自定义Code码的卡券调用接口时，POST数据中需指定code，非自定义code不需指定，指定openid同理。指定后的二维码只能被用户扫描领取一次。
     *
     * @param qrcardInfoList
     * @param expireSeconds  指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
     * @return
     */
    public static ApiResult createMultipleCardQrcode(List<QrcardInfo> qrcardInfoList, Integer expireSeconds) {
        if (qrcardInfoList == null) {
            throw new WeixinApiException("qrcardInfoList Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("action_name", "QR_MULTIPLE_CARD");
        if (expireSeconds != null) {
            param.put("expire_seconds", "expireSeconds");
        }

        Map<String, Object> multipleCardMap = new HashMap<>();
        multipleCardMap.put("card_list", qrcardInfoList);

        Map<String, Object> actionInfoMap = new HashMap<>();
        actionInfoMap.put("multiple_card", multipleCardMap);

        param.put("action_info", actionInfoMap);

        String url = createCardQrcodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 创建货架
     * 开发者需调用该接口创建货架链接，用于卡券投放。
     *
     * @param cardLandingPage
     * @return
     */
    public static ApiResult createCardLandingPage(CardLandingPage cardLandingPage) {
        if (cardLandingPage == null) {
            throw new WeixinApiException("cardLandingPage Cannot be null");
        }

        String url = createCardLandingPageUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(cardLandingPage));
        return new ApiResult(json);
    }

    /**
     * 导入自定义code
     *
     * @param cardId   需要进行导入code的卡券ID
     * @param codeList 需导入微信卡券后台的自定义code，上限为100个。
     * @return
     */
    public static ApiResult depositCardCode(String cardId, List<String> codeList) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        if (codeList == null) {
            throw new WeixinApiException("codeList Cannot be null");
        }
        if (codeList.size() > 100) {
            throw new WeixinApiException("codeList size max 100");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("code", codeList);

        String url = depositCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询导入code数目
     * 为了避免出现导入差错，强烈建议开发者在查询完code数目的时候核查code接口校验code导入微信后台的情况。
     *
     * @param cardId 进行导入code的卡券ID
     * @return
     */
    public static ApiResult getDepositcountCardCode(String cardId) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);

        String url = getDepositcountCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 核查code
     *
     * @param cardId   进行导入code的卡券ID
     * @param codeList 已经微信卡券后台的自定义code，上限为100个。
     * @return
     */
    public static ApiResult checkCardCode(String cardId, List<String> codeList) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        if (codeList == null) {
            throw new WeixinApiException("codeList Cannot be null");
        }
        if (codeList.size() > 100) {
            throw new WeixinApiException("codeList size max 100");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        param.put("code", codeList);

        String url = checkCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取群发卡券图文消息标准代码
     * 支持开发者调用该接口获取卡券嵌入图文消息的标准格式代码，将返回代码填入上传图文素材接口中content字段，即可获取嵌入卡券的图文消息素材。
     * 特别注意：目前该接口仅支持填入非自定义code的卡券,自定义code的卡券需先进行code导入后调用。
     *
     * @param cardId 卡券ID
     * @return
     */
    public static ApiResult getCardNewsHtml(String cardId) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);

        String url = getCardNewsHtmlUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 设置测试白名单
     * 由于卡券有审核要求，为方便公众号调试，可以设置一些测试帐号，这些帐号可领取未通过审核的卡券，体验整个流程。
     * 开发者注意事项
     * <p>
     * 1.同时支持“openid”、“username”两种字段设置白名单，总数上限为10个。
     * <p>
     * 2.设置测试白名单接口为全量设置，即测试名单发生变化时需调用该接口重新传入所有测试人员的ID.
     * <p>
     * 3.白名单用户领取该卡券时将无视卡券失效状态，请开发者注意。
     *
     * @param openidList   测试的openid列表
     * @param usernameList 测试的微信号列表
     * @return
     */
    public static ApiResult setCardTestwhite(List<String> openidList, List<String> usernameList) {
        if (openidList == null && usernameList == null) {
            throw new WeixinApiException("openidList and  usernameList Cannot be all null");
        }
        Map<String, Object> param = new HashMap<>();
        if (openidList != null) {
            param.put("openid", openidList);
        }
        if (usernameList != null) {
            param.put("username", usernameList);
        }

        String url = setCardTestwhiteUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    //========================核销卡券========================

    private static final String getCardCodeUrl = "https://api.weixin.qq.com/card/code/get?access_token=ACCESS_TOKEN";
    private static final String consumeCardCodeUrl = "https://api.weixin.qq.com/card/code/consume?access_token=ACCESS_TOKEN";
    private static final String decryptCardCodeUrl = "https://api.weixin.qq.com/card/code/decrypt?access_token=ACCESS_TOKEN";

    /**
     * 查询卡券Code
     *
     * @param code         单张卡券code，唯一
     * @param cardId       卡券ID，自定义code卡券必填。
     * @param checkConsume 是否校验code核销状态，填入true和false时的code异常状态返回数据不同。
     * @return
     */
    public static ApiResult getCardCode(String code, String cardId, boolean checkConsume) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("check_consume", checkConsume);

        String url = getCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 核销卡券Code
     *
     * @param code   单张卡券code，唯一
     * @param cardId 卡券ID，自定义code卡券必填。
     * @return
     */
    public static ApiResult consumeCardCode(String code, String cardId) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }

        String url = consumeCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 卡券Code解码
     *
     * @param encryptCode 经过加密的Code码
     * @return
     */
    public static ApiResult decryptCardCode(String encryptCode) {
        if (StringUtils.isBlank(encryptCode)) {
            throw new WeixinApiException("encryptCode Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("encrypt_code", encryptCode);

        String url = decryptCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    //========================管理卡券========================

    private static final String getUserCardsUrl = "https://api.weixin.qq.com/card/user/getcardlist?access_token=ACCESS_TOKEN";
    private static final String getCardUrl = "https://api.weixin.qq.com/card/get?access_token=ACCESS_TOKEN";
    private static final String batchgetCardUrl = "https://api.weixin.qq.com/card/batchget?access_token=ACCESS_TOKEN";
    private static final String updateCardUrl = "https://api.weixin.qq.com/card/update?access_token=ACCESS_TOKEN";
    private static final String modifyStockUrl = "https://api.weixin.qq.com/card/modifystock?access_token=ACCESS_TOKEN";
    private static final String updateCardCodeUrl = "https://api.weixin.qq.com/card/code/update?access_token=ACCESS_TOKEN";
    private static final String deleteCardUrl = "https://api.weixin.qq.com/card/delete?access_token=ACCESS_TOKEN";

    /**
     * 获取用户已领取卡券
     *
     * @param openid 用户openid
     * @param cardId 卡券ID。不填写时默认查询当前appid下的卡券。
     * @return
     */
    public static ApiResult getUserCards(String openid, String cardId) {
        if (StringUtils.isBlank(openid)) {
            throw new WeixinApiException("openid Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("openid", openid);
        if (cardId != null) {
            param.put("card_id", cardId);
        }

        String url = getUserCardsUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查看卡券详情
     *
     * @param cardId 卡券ID
     * @return
     */
    public static ApiResult getCard(String cardId) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);

        String url = getCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 批量查询卡券列表
     *
     * @param offset     查询卡列表的起始偏移量，从0开始，即offset: 5是指从从列表里的第六个开始读取。
     * @param count      需要查询的卡片的数量（数量最大50）。
     * @param statusList 支持开发者拉出指定状态的卡券列表
     *                   “CARD_STATUS_NOT_VERIFY”, 待审核 ；
     *                   “CARD_STATUS_VERIFY_FAIL”, 审核失败；
     *                   “CARD_STATUS_VERIFY_OK”， 通过审核；
     *                   “CARD_STATUS_DELETE”， 卡券被商户删除；
     *                   “CARD_STATUS_DISPATCH”， 在公众平台投放过的卡券；
     * @return
     */
    public static ApiResult batchgetCard(int offset, int count, List<String> statusList) {
        if (offset < 0) {
            offset = 0;
        }
        if (count < 1) {
            count = 1;
        }
        if (count > 50) {
            count = 50;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("count", count);
        if (statusList != null && statusList.size() > 0) {
            param.put("status_list", statusList);
        }

        String url = batchgetCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 更改卡券信息
     *
     * @param jsonData Json数据
     * @return
     */
    public static ApiResult updateCard(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("json Cannot be null");
        }

        String url = updateCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 修改库存
     *
     * @param cardId             卡券ID
     * @param increaseStockValue 增加多少库存，支持不填或填0。
     * @param reduceStockValue   减少多少库存，可以不填或填0。
     * @return
     */
    public static ApiResult modifyStock(String cardId, Integer increaseStockValue, Integer reduceStockValue) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);
        if (increaseStockValue != null) {
            if (increaseStockValue < 0) {
                throw new WeixinApiException("increaseStockValue min 0");
            }
            param.put("increase_stock_value", increaseStockValue);
        }
        if (reduceStockValue != null) {
            if (reduceStockValue < 0) {
                throw new WeixinApiException("reduceStockValue min 0");
            }
            param.put("reduce_stock_value", reduceStockValue);
        }

        String url = modifyStockUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 修改库存
     *
     * @param cardId  卡券ID。自定义Code码卡券为必填。
     * @param code    需变更的Code码。
     * @param newCode 变更后的有效Code码。
     * @return
     */
    public static ApiResult updateCardCode(String cardId, String code, String newCode) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        if (StringUtils.isBlank(newCode)) {
            throw new WeixinApiException("newCode Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("code", code);
        param.put("new_code", newCode);

        String url = updateCardCodeUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 删除卡券
     *
     * @param cardId 卡券ID
     * @return
     */
    public static ApiResult deleteCard(String cardId) {
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("card_id", cardId);

        String url = deleteCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 设置卡券失效
     * 设置卡券失效的操作不可逆，即无法将设置为失效的卡券调回有效状态
     *
     * @param cardId 卡券ID
     * @param code   设置失效的Code码
     * @param reason 失效理由
     * @return
     */
    public static ApiResult unavailableCardCode(String cardId, String code, String reason) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        Map<String, Object> param = new HashMap<>();
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("code", code);
        param.put("reason", reason);

        String url = deleteCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    //========================统计卡券数据========================

    private static final String getCardbizuinInfoUrl = "https://api.weixin.qq.com/datacube/getcardbizuininfo?access_token=ACCESS_TOKEN";
    private static final String getCardInfoUrl = "https://api.weixin.qq.com/datacube/getcardcardinfo?access_token=ACCESS_TOKEN";
    private static final String getCardMembercardInfoUrl = "https://api.weixin.qq.com/datacube/getcardmembercardinfo?access_token=ACCESS_TOKEN";
    private static final String getCardMembercardDetailUrl = "https://api.weixin.qq.com/datacube/getcardmembercarddetail?access_token=ACCESS_TOKEN";

    /**
     * 拉取卡券概况数据
     * 设置卡券失效的操作不可逆，即无法将设置为失效的卡券调回有效状态
     *
     * @param beginDate  查询数据的起始时间。
     * @param endDate    查询数据的截至时间
     * @param condSource 卡券来源，0为公众平台创建的卡券数据 、1是API创建的卡券数据
     * @return
     */
    public static ApiResult getCardbizuinInfo(Date beginDate, Date endDate, int condSource) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days > 62) {
            throw new WeixinApiException("date range error, max 62");
        }
        if (condSource != 0 && condSource != 1) {
            condSource = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> param = new HashMap<>();
        param.put("begin_date", sdf.format(beginDate));
        param.put("end_date", sdf.format(endDate));
        param.put("cond_source", condSource);

        String url = getCardbizuinInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取免费券数据
     * 支持开发者调用该接口拉取免费券（优惠券、团购券、折扣券、礼品券）在固定时间区间内的相关数据。
     *
     * @param beginDate  查询数据的起始时间。
     * @param endDate    查询数据的截至时间
     * @param condSource 卡券来源，0为公众平台创建的卡券数据 、1是API创建的卡券数据
     * @param cardId     卡券ID，指定拉出该卡券的相关数据
     * @return
     */
    public static ApiResult getCardInfo(Date beginDate, Date endDate, int condSource, String cardId) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days > 62) {
            throw new WeixinApiException("date range error, max 62");
        }
        if (condSource != 0 && condSource != 1) {
            condSource = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> param = new HashMap<>();
        param.put("begin_date", sdf.format(beginDate));
        param.put("end_date", sdf.format(endDate));
        param.put("cond_source", condSource);
        if (cardId != null) {
            param.put("card_id", cardId);
        }

        String url = getCardInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 拉取会员卡概况数据
     * 支持开发者调用该接口拉取公众平台创建的会员卡相关数据。
     *
     * @param beginDate  查询数据的起始时间。
     * @param endDate    查询数据的截至时间
     * @param condSource 卡券来源，0为公众平台创建的卡券数据 、1是API创建的卡券数据
     * @return
     */
    public static ApiResult getCardMembercardInfo(Date beginDate, Date endDate, int condSource) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days > 62) {
            throw new WeixinApiException("date range error, max 62");
        }
        if (condSource != 0 && condSource != 1) {
            condSource = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> param = new HashMap<>();
        param.put("begin_date", sdf.format(beginDate));
        param.put("end_date", sdf.format(endDate));
        param.put("cond_source", condSource);

        String url = getCardMembercardInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 拉取单张会员卡数据
     * 支持开发者调用该接口拉取API创建的会员卡数据情况
     *
     * @param beginDate 查询数据的起始时间。
     * @param endDate   查询数据的截至时间
     * @param cardId    卡券id
     * @return
     */
    public static ApiResult getCardMembercardDetail(Date beginDate, Date endDate, String cardId) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days > 62) {
            throw new WeixinApiException("date range error, max 62");
        }
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> param = new HashMap<>();
        param.put("begin_date", sdf.format(beginDate));
        param.put("end_date", sdf.format(endDate));
        param.put("card_id", cardId);

        String url = getCardMembercardDetailUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }


}
