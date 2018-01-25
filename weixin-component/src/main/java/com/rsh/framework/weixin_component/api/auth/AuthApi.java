package com.rsh.framework.weixin_component.api.auth;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方强授权相关接口 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2018/1/23
 * @Time: 17:20
 */
public class AuthApi {

    private static final String queryAuthUrl = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=COMPONENT_ACCESS_TOKEN";
    private static final String confirmAuthorizationUrl = "https://api.weixin.qq.com/ cgi-bin/component/api_confirm_authorization?component_access_token=COMPONENT_ACCESS_TOKEN";
    private static final String getAuthorizerInfoUrl = "https://api.weixin.qq.com/ cgi-bin/component/api_get_authorizer_info?component_access_token=COMPONENT_ACCESS_TOKEN";

    /**
     * 使用授权码换取公众号的授权信息
     * 该API用于使用授权码换取授权公众号的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
     * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后，在回调URI中通过URL参数提供给第三方平台方。
     *
     * @param componentAppid    第三方平台appid
     * @param authorizationCode 授权code,会在授权成功时返回给第三方平台，详见第三方平台授权流程说明。
     * @return
     */
    public static ApiResult queryAuth(String componentAppid, String authorizationCode) {
        if (StringUtils.isBlank(componentAppid)) {
            throw new WeixinApiException("componentAppid Cannot be null");
        }
        if (StringUtils.isBlank(authorizationCode)) {
            throw new WeixinApiException("authorizationCode Cannot be null");
        }

        String url = queryAuthUrl.replace("COMPONENT_ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("component_appid", componentAppid);
        param.put("authorization_code", authorizationCode);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 确认授权
     *
     * @param componentAppid      第三方平台appid
     * @param authorizerAppid     授权方appid
     * @param funcscopeCategoryId 授权集id
     * @param confirmValue        是否确认，1为确认，2为取消
     * @return
     */
    public static ApiResult confirmAuthorization(String componentAppid, String authorizerAppid, int funcscopeCategoryId, int confirmValue) {
        if (StringUtils.isBlank(componentAppid)) {
            throw new WeixinApiException("componentAppid Cannot be null");
        }
        if (StringUtils.isBlank(authorizerAppid)) {
            throw new WeixinApiException("authorizerAppid Cannot be null");
        }

        String url = confirmAuthorizationUrl.replace("COMPONENT_ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(4);
        param.put("component_appid", componentAppid);
        param.put("authorizer_appid", authorizerAppid);
        param.put("funscope_category_id", funcscopeCategoryId);
        param.put("confirm_value", confirmValue);


        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取授权方的账户信息
     * 该API用于获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
     * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；而若有客服接口，则可以选择暂时不响应，而选择后续通过客服接口来发送消息触达粉丝。
     *
     * @param componentAppid  第三方平台appid
     * @param authorizerAppid 授权方appid
     * @return
     */
    public static ApiResult getAuthorizerInfo(String componentAppid, String authorizerAppid) {
        if (StringUtils.isBlank(componentAppid)) {
            throw new WeixinApiException("componentAppid Cannot be null");
        }
        if (StringUtils.isBlank(authorizerAppid)) {
            throw new WeixinApiException("authorizerAppid Cannot be null");
        }

        String url = getAuthorizerInfoUrl.replace("COMPONENT_ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("component_appid", componentAppid);
        param.put("authorizer_appid", authorizerAppid);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
