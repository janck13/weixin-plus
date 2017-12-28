package com.rsh.framework.weixin.api.datacube;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口分析数据 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 10:49
 */
public class ApiAnalyDataApi {

    private static final String getInterfaceSummaryUrl = "https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN";
    private static final String getInterfaceSummaryHourUrl = "https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token=ACCESS_TOKEN";


    /**
     * 获取接口分析数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getInterfaceSummary(Date beginDate, Date endDate) {
        return getApiAnalyData(getInterfaceSummaryUrl, 7, beginDate, endDate);
    }

    /**
     * 获取接口分析分时数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getInterfaceSummaryHour(Date beginDate, Date endDate) {
        return getApiAnalyData(getInterfaceSummaryHourUrl, 7, beginDate, endDate);
    }

    /**
     * 获取接口分析数据
     *
     * @param url
     * @param maxTimeLimit
     * @param beginDate
     * @param endDate
     * @return
     */
    public static ApiResult getApiAnalyData(String url, int maxTimeLimit, Date beginDate, Date endDate) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days >= 7) {
            throw new WeixinApiException("endDate max time limit");
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
