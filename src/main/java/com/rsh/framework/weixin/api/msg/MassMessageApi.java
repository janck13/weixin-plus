package com.rsh.framework.weixin.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.msg.out.BaseSendMessage;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 群发消息接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 18:03
 */
public class MassMessageApi {

    private static final String sendAllUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    private static final String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    private static final String deleteUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
    private static final String previewUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    private static final String getUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
    private static final String getSpeedrl = "https://api.weixin.qq.com/cgi-bin/message/mass/speed/get?access_token=ACCESS_TOKEN";
    private static final String setSpeedrl = "https://api.weixin.qq.com/cgi-bin/message/mass/speed/set?access_token=ACCESS_TOKEN";

    /**
     * 根据标签进行群发【订阅号与服务号认证后均可用】
     *
     * @param json
     * @return
     */
    public static ApiResult sendAll(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = sendAllUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

    /**
     * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
     *
     * @param json
     * @return
     */
    public static ApiResult send(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = sendUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

    /**
     * 删除群发【订阅号与服务号认证后均可用】
     *
     * @param json
     * @return
     */
    public static ApiResult delete(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = deleteUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

    /**
     * 预览接口【订阅号与服务号认证后均可用】
     *
     * @param json
     * @return
     */
    public static ApiResult preview(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = previewUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     * msg_status	消息发送后的状态，SEND_SUCCESS表示发送成功，SENDING表示发送中，SEND_FAIL表示发送失败，DELETE表示已删除
     *
     * @param json
     * @return
     */
    public static ApiResult getStatus(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

    /**
     * 获取群发速度
     *
     * @return
     */
    public static ApiResult getSpeed() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getSpeedrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 设置群发速度
     *
     * @param speed 群发速度的级别，是一个0到4的整数，数字越大表示群发速度越慢。
     *              0	80w/分钟
     *              1	60w/分钟
     *              2	45w/分钟
     *              3	30w/分钟
     *              4	10w/分钟
     * @return
     */
    public static ApiResult setSpeed(int speed) {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        Map<String, Integer> param = new HashMap<>();
        param.put("speed", speed);

        String url = setSpeedrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }


}