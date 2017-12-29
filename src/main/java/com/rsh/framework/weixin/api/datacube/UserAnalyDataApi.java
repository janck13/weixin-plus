package com.rsh.framework.weixin.api.datacube;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户分析数据 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 10:49
 */
public class UserAnalyDataApi {

    private static final String getUserSummaryUrl = "https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN";
    private static final String getUserCumulateUrl = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN";


    /**
     * 获取用户增减数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserSummary(Date beginDate, Date endDate) {
        return getUserAnalyData(getUserSummaryUrl, 7, beginDate, endDate);
    }

    /**
     * 获取累计用户数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserCumulate(Date beginDate, Date endDate) {
        return getUserAnalyData(getUserCumulateUrl, 7, beginDate, endDate);
    }

    /**
     * 获取用户分析数据
     *
     * @param url
     * @param maxTimeLimit
     * @param beginDate
     * @param endDate
     * @return
     */
    public static ApiResult getUserAnalyData(String url, int maxTimeLimit, Date beginDate, Date endDate) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days >= maxTimeLimit) {
            throw new WeixinApiException("date range error, max " + maxTimeLimit);
        }
        url = url.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Object> param = new HashMap<>();
        param.put("begin_date", format.format(beginDate));
        param.put("end_date", format.format(endDate));

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }


}
