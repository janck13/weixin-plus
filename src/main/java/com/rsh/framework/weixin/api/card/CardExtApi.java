package com.rsh.framework.weixin.api.card;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 特殊卡券 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/4
 * @Time: 9:59
 */
public class CardExtApi {

    private static final String updateMeetingticketUserUrl = "https://api.weixin.qq.com/card/meetingticket/updateuser?access_token=ACCESS_TOKEN";
    private static final String updateMovieticketUserUrl = "https://api.weixin.qq.com/card/movieticket/updateuser?access_token=ACCESS_TOKEN";
    private static final String checkinBoardingpassUrl = "https://api.weixin.qq.com/card/boardingpass/checkin?access_token=ACCESS_TOKEN";


    /**
     * 更新会议门票
     * 支持调用“更新会议门票”接口update 入场时间、区域、座位等信息。
     *
     * @param code       卡券Code码,必填
     * @param cardId     要更新门票序列号所述的card_id，生成券时use_custom_code 填写true 时必填。
     * @param zone       区域,必填
     * @param entrance   入口,必填
     * @param seatNumber 座位号,必填
     * @param beginTime  开场时间
     * @param endTime    结束时间
     * @return
     */
    public static ApiResult updateMeetingticketUser(String code, String cardId, String zone, String entrance, String seatNumber, Date beginTime, Date endTime) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        if (StringUtils.isBlank(zone)) {
            throw new WeixinApiException("zone Cannot be null");
        }
        if (StringUtils.isBlank(entrance)) {
            throw new WeixinApiException("entrance Cannot be null");
        }
        if (StringUtils.isBlank(seatNumber)) {
            throw new WeixinApiException("seatNumber Cannot be null");
        }

        String url = updateMeetingticketUserUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("zone", zone);
        param.put("entrance", entrance);
        param.put("seat_number", seatNumber);
        if (beginTime != null) {
            param.put("begin_time", beginTime.getTime());
        }
        if (endTime != null) {
            param.put("end_time", endTime.getTime());
        }

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 更新电影票
     * 领取电影票后通过调用“更新电影票”接口update电影信息及用户选座信息。
     *
     * @param code          卡券Code码,必填
     * @param cardId        要更新电影票序列号所述的card_id，生成券时use_custom_code 填写true 时必填。
     * @param ticketClass   电影票的类别，如2D、3D,必填
     * @param showTime      电影的放映时间,必填
     * @param duration      放映时长,，填写整数,必填
     * @param screeningRoom 该场电影的影厅信息
     * @param seatNumber    座位号
     * @return
     */
    public static ApiResult updateMovieticketUser(String code, String cardId, String ticketClass, Date showTime, int duration, String screeningRoom, String[] seatNumber) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        if (StringUtils.isBlank(ticketClass)) {
            throw new WeixinApiException("ticketClass Cannot be null");
        }
        if (showTime == null) {
            throw new WeixinApiException("showTime Cannot be null");
        }
        if (duration <= 0) {
            throw new WeixinApiException("duration min 1");
        }

        String url = updateMovieticketUserUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("ticket_class", ticketClass);
        param.put("show_time", showTime.getTime());
        param.put("duration", seatNumber);
        if (screeningRoom != null) {
            param.put("screening_room", screeningRoom);
        }
        if (seatNumber != null) {
            param.put("seat_number", seatNumber);
        }

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 更新飞机票信息
     *
     * @param code          卡券Code码,必填
     * @param cardId        要更新电影票序列号所述的card_id，生成券时use_custom_code 填写true 时必填。
     * @param etktBnr       电子客票号，上限为14个数字,必填
     * @param cardClass     舱等，如头等舱等，上限为5个汉字,必填
     * @param qrcodeData    二维码数据。乘客用于值机的二维码字符串，微信会通过此数据为用户生成值机用的二维码
     * @param passengerName 乘客姓名
     * @param seat          乘客座位号
     * @param isCancel      是否取消值机。填写true或false。true代表取消，如填写true上述字段（如calss等）均不做判断，机票返回未值机状态，乘客可重新值机。默认填写false。
     * @return
     */
    public static ApiResult checkinBoardingpass(String code, String cardId, String etktBnr, String cardClass, String qrcodeData, String passengerName, String seat, boolean isCancel) {
        if (StringUtils.isBlank(code)) {
            throw new WeixinApiException("code Cannot be null");
        }
        if (StringUtils.isBlank(etktBnr)) {
            throw new WeixinApiException("etktBnr Cannot be null");
        }
        if (StringUtils.isBlank(cardClass)) {
            throw new WeixinApiException("cardClass Cannot be null");
        }

        String url = checkinBoardingpassUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        if (cardId != null) {
            param.put("card_id", cardId);
        }
        param.put("etkt_bnr", etktBnr);
        param.put("class", cardClass);
        if (qrcodeData != null) {
            param.put("qrcode_data", qrcodeData);
        }
        if (passengerName != null) {
            param.put("passenger_name", passengerName);
        }
        if (seat != null) {
            param.put("seat", seat);
        }
        param.put("is_cancel ", isCancel);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
