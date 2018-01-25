package com.rsh.framework.weixin_mp.api.poi;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin_mp.model.poi.MapPoi;
import com.rsh.framework.weixin_mp.model.poi.Merchant;
import com.rsh.framework.weixin_mp.model.poi.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信门店小程序 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 15:23
 */
public class MerchantApi {

    private static final String getMerchantCategoryUrl = "https://api.weixin.qq.com/wxa/get_merchant_category?access_token=ACCESS_TOKEN";
    private static final String applyMerchantUrl = "https://api.weixin.qq.com/wxa/apply_merchant?access_token=ACCESS_TOKEN";
    private static final String getMerchantAuditInfoUrl = "https://api.weixin.qq.com/wxa/get_merchant_audit_info?access_token=ACCESS_TOKEN";
    private static final String modifyMerchantUrl = "https://api.weixin.qq.com/wxa/modify_merchant?access_token=ACCESS_TOKEN";
    private static final String getDistrictUrl = "https://api.weixin.qq.com/wxa/get_district?access_token=ACCESS_TOKEN";
    private static final String searchMapPoiUrl = "https://api.weixin.qq.com/wxa/search_map_poi?access_token=ACCESS_TOKEN";
    private static final String createMapPoiUrl = "https://api.weixin.qq.com/wxa/create_map_poi?access_token=ACCESS_TOKEN";
    private static final String addStoreUrl = "https://api.weixin.qq.com/wxa/add_store?access_token=ACCESS_TOKEN";
    private static final String updateStoreUrl = "https://api.weixin.qq.com/wxa/update_store?access_token=ACCESS_TOKEN";
    private static final String getStoreInfoUrl = "https://api.weixin.qq.com/wxa/get_store_info?access_token=ACCESS_TOKEN";
    private static final String getStoreListUrl = "https://api.weixin.qq.com/wxa/get_store_list?access_token=ACCESS_TOKEN";
    private static final String delStoreUrl = "https://api.weixin.qq.com/wxa/del_store?access_token=ACCESS_TOKEN";
    private static final String getStoreCardUrl = "https://api.weixin.qq.com/card/storewxa/get?access_token=ACCESS_TOKEN";
    private static final String setStoreCardUrl = "https://api.weixin.qq.com/card/storewxa/set?access_token=ACCESS_TOKEN";

    /**
     * 拉取门店小程序类目
     *
     * @return
     */
    public static ApiResult getMerchantCategory() {
        String url = getMerchantCategoryUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 创建门店小程序
     * 创建门店小程序提交后需要公众号管理员确认通过后才可进行审核。如果主管理员24小时超时未确认，才能再次提交。
     *
     * @param merchant 门店小程序信息
     * @return
     */
    public static ApiResult applyMerchant(Merchant merchant) {
        if (merchant == null) {
            throw new WeixinApiException("poiMiniprogram Cannot be null");
        }

        String url = applyMerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, merchant.getJson());
        return new ApiResult(json);
    }

    /**
     * 查询门店小程序审核结果
     *
     * @return
     */
    public static ApiResult getMerchantAuditInfo() {
        String url = getMerchantAuditInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 修改门店小程序信息
     *
     * @param headimgMediaid 门店头像的临时素材mediaid,如果不想改，参数传空值
     * @param intro          门店小程序的介绍,如果不想改，参数传空值
     * @return
     */
    public static ApiResult modifyMerchant(String headimgMediaid, String intro) {
        String url = modifyMerchantUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("headimg_mediaid", headimgMediaid);
        param.put("intro", intro);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 从腾讯地图拉取省市区信息
     *
     * @return
     */
    public static ApiResult getDistrict() {
        String url = getDistrictUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 在腾讯地图中搜索门店
     *
     * @param districtid 对应 拉取省市区信息接口 中的id字段
     * @param keyword    搜索的关键词
     * @return
     */
    public static ApiResult searchMapPoi(String districtid, String keyword) {
        if (StringUtils.isBlank(districtid)) {
            throw new WeixinApiException("districtid Cannot be null");
        }
        if (StringUtils.isBlank(keyword)) {
            throw new WeixinApiException("keyword Cannot be null");
        }

        String url = searchMapPoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("districtid", districtid);
        param.put("keyword", keyword);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 在腾讯地图中创建门店
     *
     * @param mapPoi
     * @return
     */
    public static ApiResult createMapPoi(MapPoi mapPoi) {
        if (mapPoi == null) {
            throw new WeixinApiException("mapPoi Cannot be null");
        }
        return createMapPoi(mapPoi.getJson());
    }

    /**
     * 在腾讯地图中创建门店
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult createMapPoi(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = createMapPoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 添加门店
     *
     * @param store
     * @return
     */
    public static ApiResult addStore(Store store) {
        if (store == null) {
            throw new WeixinApiException("store Cannot be null");
        }
        return addStore(store.getJson());
    }

    /**
     * 添加门店
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult addStore(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = addStoreUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 更新门店信息
     * 注意：如果要更新门店的图片，实际相当于走一次重新为门店添加图片的流程，之前的旧图片会全部废弃。并且如果重新添加的图片中有与之前旧图片相同的，此时这个图片不需要重新审核。
     *
     * @param poiId         为门店小程序添加门店，审核成功后返回的门店id
     * @param mapPoiId      从腾讯地图换取的位置点id， 即search_map_poi接口返回的sosomap_poi_uid字段
     * @param hour          自定义营业时间，格式为10:00-12:00
     * @param contractPhone 自定义联系电话
     * @param picList       门店图片
     * @param cardId        卡券id，如果不想修改的话，设置为空
     * @return
     */
    public static ApiResult updateStore(String poiId, String mapPoiId, String hour, String contractPhone, List<String> picList, String cardId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = updateStoreUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(6);
        param.put("poi_id", poiId);
        param.put("map_poi_id", mapPoiId);
        param.put("hour", hour);
        param.put("contract_phone", contractPhone);
        Map<String, Object> picMap = new HashMap<>(6);
        picMap.put("list", picList);
        param.put("pic_list", picMap);
        param.put("card_id", cardId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取单个门店信息
     *
     * @param poiId 为门店小程序添加门店，审核成功后返回的门店id
     * @return
     */
    public static ApiResult getStoreInfo(String poiId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = getStoreInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("poi_id", poiId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取门店信息列表
     *
     * @param offset 获取门店列表的初始偏移位置，从0开始计数
     * @param limit  获取门店个数
     * @return
     */
    public static ApiResult getStoreList(int offset, int limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            offset = 20;
        }
        if (offset > 50) {
            offset = 50;
        }

        String url = getStoreListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("offset", offset);
        param.put("limit", limit);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 删除门店
     *
     * @param poiId 为门店小程序添加门店，审核成功后返回的门店id
     * @return
     */
    public static ApiResult delStore(String poiId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = delStoreUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("poi_id", poiId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取门店小程序配置的卡券
     *
     * @param poiId 门店id
     * @return
     */
    public static ApiResult getStoreCard(String poiId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = getStoreCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("poi_id", poiId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 设置门店小程序卡券
     *
     * @param poiId  门店id
     * @param cardId 微信卡券id
     * @return
     */
    public static ApiResult setStoreCard(String poiId, String cardId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }
        if (StringUtils.isBlank(cardId)) {
            throw new WeixinApiException("cardId Cannot be null");
        }

        String url = setStoreCardUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("poi_id", poiId);
        param.put("card_id", cardId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
