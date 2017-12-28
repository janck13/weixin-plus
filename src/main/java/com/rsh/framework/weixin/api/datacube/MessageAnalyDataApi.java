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
 * 消息分析数据 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/28
 * @Time: 10:49
 */
public class MessageAnalyDataApi {

    private static final String getUpstreamMsgUrl = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgHourUrl = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgWeekUrl = "https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgMonthUrl = "https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgDistUrl = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgDistWeekUrl = "https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token=ACCESS_TOKEN";
    private static final String getUpstreamMsgDistMonthUrl = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN";


    /**
     * 获取消息发送概况数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsg(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息分送分时数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgHour(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgHourUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息发送周数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgWeek(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgWeekUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息发送月数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgMonth(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgMonthUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息发送分布数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgDist(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgDistUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息发送分布周数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgDistWeek(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgDistWeekUrl, 7, beginDate, endDate);
    }

    /**
     * 获取消息发送分布月数据
     *
     * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”,最大时间跨度7
     * @param endDate   获取数据的结束日期，end_date允许设置的最大值为昨日
     * @return
     */
    public static ApiResult getUpstreamMsgDistMonth(Date beginDate, Date endDate) {
        return getMessageAnalyData(getUpstreamMsgDistMonthUrl, 7, beginDate, endDate);
    }

    /**
     * 消息分析数据
     *
     * @param url
     * @param maxTimeLimit
     * @param beginDate
     * @param endDate
     * @return
     */
    private static ApiResult getMessageAnalyData(String url, int maxTimeLimit, Date beginDate, Date endDate) {
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
