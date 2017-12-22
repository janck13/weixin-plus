package com.rsh.framework.weixin.api.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信网页授权接口
 * 1 第一步：用户同意授权，获取code
 * <p>
 * 2 第二步：通过code换取网页授权access_token
 * <p>
 * 3 第三步：刷新access_token（如果需要）
 * <p>
 * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * <p>
 * 5 附：检验授权凭证（access_token）是否有效
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/22
 * @Time: 15:09
 */
public class WebAuthApi {

    private static final String webAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 获取用户微信网页授权链接，获取code
     * @param appid 公众号的唯一标识
     * @param redirectUri 授权后重定向的回调链接地址
     * @param scope 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     */
    public String getWebAuthUrl(String appid, String redirectUri, String scope, String state){
        if (redirectUri != null) {
            try {
                redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return webAuthUrl.replace("APPID", appid)
                .replace("REDIRECT_URI", redirectUri)
                .replace("SCOPE", scope)
                .replace("STATE", state);
    }

}
