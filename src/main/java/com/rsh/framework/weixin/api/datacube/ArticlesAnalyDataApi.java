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
 * 图文分析数据 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 11:15
 */
public class ArticlesAnalyDataApi {

    private static final String getArticleSummaryUrl = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN";
    private static final String getArticleTotalUrl = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN";
    private static final String getUserReadUrl = "https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN";
    private static final String getUserReadHourUrl = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN";
    private static final String getUserShareUrl = "https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN";
    private static final String getUserShareHourUrl = "https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN";


    /**
     * 获取图文群发每日数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getArticleSummary(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getArticleSummaryUrl, 1, beginDate, endDate);
    }

    /**
     * 获取图文群发总数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getArticleTotal(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getArticleTotalUrl, 1, beginDate, endDate);
    }

    /**
     * 获取图文统计数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserRead(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getUserReadUrl, 3, beginDate, endDate);
    }

    /**
     * 获取图文统计分时数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserReadHour(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getUserReadHourUrl, 1, beginDate, endDate);
    }

    /**
     * 获取图文分享转发数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserShare(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getUserShareUrl, 7, beginDate, endDate);
    }

    /**
     * 获取图文分享转发分时数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUserShareHour(Date beginDate, Date endDate) {
        return getArticlesAnalyData(getUserShareHourUrl, 1, beginDate, endDate);
    }


    /**
     * 获取图文分析数据
     *
     * @param url
     * @param maxTimeLimit
     * @param beginDate
     * @param endDate
     * @return
     */
    private static ApiResult getArticlesAnalyData(String url, int maxTimeLimit, Date beginDate, Date endDate) {
        if (beginDate == null) {
            throw new WeixinApiException("beginDate Cannot be null");
        }
        if (endDate == null) {
            throw new WeixinApiException("endDate Cannot be null");
        }
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        if (days >= maxTimeLimit) {
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
