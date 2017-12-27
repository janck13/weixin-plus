package com.rsh.framework.weixin.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 一次性订阅消息接口
 * <p>
 * 开发者可以通过一次性订阅消息授权让微信用户授权第三方移动应用（接入说明）或公众号，获得发送一次订阅消息给到授权微信用户的机会。
 * 授权微信用户可以不需要关注公众号。微信用户每授权一次，开发者可获得一次下发消息的权限。
 * 注意：同一用户在同一scene场景值下的多次授权不累积下发权限，只能下发一条。若要订阅多条，需要不同scene场景值。
 * 消息下发位置说明：对于已关注公众号的，消息将下发到公众号会话里；未关注公众号的，将下发到服务通知。
 * <p>
 * 公众号或网页使用一次性订阅消息流程如下：
 * 第一步：需要用户同意授权，获取一次给用户推送一条订阅模板消息的机会，用户同意或取消授权后会返回相关信息
 * 第二步：通过API推送订阅模板消息给到授权微信用户
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/22
 * @Time: 13:57
 */
public class SubscribeMessageApi {

    private static final String authorizeUrl = "https://mp.weixin.qq.com/mp/subscribemsg?action=ACTION&appid=APPID&scene=SCENE&template_id=TEMPLATE_ID&redirect_url=REDIRECT_URL&reserved=RESERVED#wechat_redirect";
    private static final String pushSubscribemsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/subscribe?access_token=ACCESS_TOKEN";

    /**
     * 获取订阅消息授权链接，引导用户在微信客户端打开返回的链接
     *
     * @param appId       公众号的唯一标识
     * @param scene       重定向后会带上scene参数，开发者可以填0-10000的整形值，用来标识订阅场景值
     * @param templateId  订阅消息模板ID，登录公众平台后台，在接口权限列表处可查看订阅模板ID
     * @param redirectUrl 授权后重定向的回调地址
     * @param reserved    用于保持请求和回调的状态，授权请后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验，开发者可以填写a-zA-Z0-9的参数值，最多128字节，要求做urlencode
     * @return
     */
    public static String getAuthorizeURL(String appId, int scene, String templateId, String redirectUrl, String reserved) {
        if (StringUtils.isNotBlank(redirectUrl)) {
            try {
                redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return authorizeUrl.replace("ACTION", "get_confirm")
                .replace("APPID", appId)
                .replace("SCENE", scene + "")
                .replace("TEMPLATE_ID", templateId)
                .replace("REDIRECT_URL", redirectUrl)
                .replace("RESERVED", reserved);
    }

    /**
     * 推送订阅模板消息给到授权微信用户
     *
     * @param json 消息json报文
     * @return
     */
    public static ApiResult pushSubscribeMsg(String json) {
        if (StringUtils.isBlank(json)) {
            throw new WeixinApiException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = pushSubscribemsgUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

}
