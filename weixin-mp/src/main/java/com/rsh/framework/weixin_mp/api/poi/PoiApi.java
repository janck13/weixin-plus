package com.rsh.framework.weixin_mp.api.poi;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin_mp.model.poi.Poi;
import com.rsh.framework.weixin_mp.model.poi.UpatePoi;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信门店 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 15:21
 */
public class PoiApi {

    private static final String addpoiUrl = "https://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=ACCESS_TOKEN";
    private static final String getpoiUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token=ACCESS_TOKEN";
    private static final String getpoiListUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoilist?access_token=ACCESS_TOKEN";
    private static final String updatepoiUrl = "https://api.weixin.qq.com/cgi-bin/poi/updatepoi?access_token=ACCESS_TOKEN";
    private static final String delpoiUrl = "https://api.weixin.qq.com/cgi-bin/poi/delpoi?access_token=ACCESS_TOKEN";
    private static final String getwxCategoryUrl = "http://api.weixin.qq.com/cgi-bin/poi/getwxcategory?access_token=ACCESS_TOKEN";

    /**
     * 创建门店
     *
     * @param poi 门店数据对象
     * @return
     */
    public static ApiResult addpoi(Poi poi) {
        if (poi == null) {
            throw new WeixinApiException("poi Cannot be null");
        }
        Map<String, Poi> baseInfoMap = new HashMap<String, Poi>(1);
        baseInfoMap.put("base_info", poi);

        Map<String, Object> param = new HashMap<>(1);
        param.put("business", baseInfoMap);

        return addpoi(JSON.toJSONString(param));
    }

    /**
     * 创建门店
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult addpoi(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = addpoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 查询门店信息
     * 创建门店后获取poi_id 后，商户可以利用poi_id，查询具体某条门店的信息。
     * 若在查询时，update_status 字段为1，表明在5 个工作日内曾用update 接口修改过门店扩展字段，该扩展字段为最新的修改字段，尚未经过审核采纳，因此不是最终结果。
     * 最终结果会在5 个工作日内，最终确认是否采纳，并前端生效（但该扩展字段的采纳过程不影响门店的可用性，即available_state仍为审核通过状态）
     * <p>
     * 注：修改扩展字段将会推送审核，但不会影响该门店的生效可用状态。
     *
     * @param poiId 门店id
     * @return
     */
    public static ApiResult getpoi(String poiId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = getpoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("poi_id", poiId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查询门店列表
     * 商户可以通过该接口，批量查询自己名下的门店list，并获取已审核通过的poiid、商户自身sid 用于对应、商户名、分店名、地址字段。
     *
     * @param begin 开始位置，0 即为从第一条开始查询
     * @param limit 返回数据条数，最大允许50，默认为20
     * @return
     */
    public static ApiResult getpoiList(int begin, int limit) {
        if (begin < 0) {
            begin = 0;
        }
        if (limit < 0) {
            limit = 20;
        }
        if (limit > 50) {
            limit = 50;
        }

        String url = getpoiListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("begin", begin);
        param.put("limit", limit);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 修改门店服务信息
     * 商户可以通过该接口，修改门店的服务信息，包括：sid、图片列表、营业时间、推荐、特色服务、简介、人均价格、电话8个字段（名称、坐标、地址等不可修改）修改后需要人工审核。
     * <p>
     * 特别注意：
     * 以上8个字段，若有填写内容则为覆盖更新，若无内容则视为不修改，维持原有内容。 photo_list 字段为全列表覆盖，若需要增加图片，需将之前图片同样放入list 中，在其后增加新增图片。
     * 如：已有A、B、C 三张图片，又要增加D、E 两张图，则需要调用该接口，photo_list 传入A、B、C、D、E 五张图片的链接。
     *
     * @param upatePoi 修改门店数据
     * @return
     */
    public static ApiResult updatepoi(UpatePoi upatePoi) {
        if (upatePoi == null) {
            throw new WeixinApiException("upatePoi Cannot be null");
        }
        Map<String, UpatePoi> baseInfoMap = new HashMap<String, UpatePoi>(1);
        baseInfoMap.put("base_info", upatePoi);

        Map<String, Object> param = new HashMap<>(1);
        param.put("business", baseInfoMap);

        return addpoi(JSON.toJSONString(param));
    }

    /**
     * 修改门店服务信息
     * 商户可以通过该接口，修改门店的服务信息，包括：sid、图片列表、营业时间、推荐、特色服务、简介、人均价格、电话8个字段（名称、坐标、地址等不可修改）修改后需要人工审核。
     * <p>
     * 特别注意：
     * 以上8个字段，若有填写内容则为覆盖更新，若无内容则视为不修改，维持原有内容。 photo_list 字段为全列表覆盖，若需要增加图片，需将之前图片同样放入list 中，在其后增加新增图片。
     * 如：已有A、B、C 三张图片，又要增加D、E 两张图，则需要调用该接口，photo_list 传入A、B、C、D、E 五张图片的链接。
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult updatepoi(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = updatepoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 删除门店
     * 商户可以通过该接口，删除已经成功创建的门店。请商户慎重调用该接口。
     *
     * @param poiId 微信门店id
     * @return
     */
    public static ApiResult delpoi(String poiId) {
        if (StringUtils.isBlank(poiId)) {
            throw new WeixinApiException("poiId Cannot be null");
        }

        String url = delpoiUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("poi_id", poiId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 门店类目表
     * 类目名称接口是为商户提供自己门店类型信息的接口。门店类目定位的越规范，能够精准的吸引更多用户，提高曝光率。
     *
     * @return
     */
    public static ApiResult getwxCategory() {
        String url = getwxCategoryUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

}
