package com.rsh.framework.weixin.api.web;

import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.web.WebAccessToken;
import com.rsh.framework.weixin.model.web.WebUserinfo;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.RetryUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

/**
 * 微信网页授权获取access_token API
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
public class WebAccessTokenApi {

    private static final String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    private static final String getWebAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final String refreshWebAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    private static final String getUserinfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private static final String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

    /**
     * 获取用户微信网页授权链接
     *
     * @param appid       公众号的唯一标识
     * @param redirectUri 授权后重定向的回调链接地址
     * @param snsapiBase  应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     * @param state       重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     */
    public static String getAuthorizeURL(String appid, String redirectUri, boolean snsapiBase, String state) {
        if (StringUtils.isNotBlank(redirectUri)) {
            try {
                redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String scope = snsapiBase ? "snsapi_base" : "snsapi_userinfo";

        return authorizeUrl.replace("APPID", appid)
                .replace("REDIRECT_URI", redirectUri)
                .replace("SCOPE", scope)
                .replace("STATE", state);
    }

    /**
     * 通过code换取网页授权access_token
     * code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     *
     * @param appid     公众号的唯一标识
     * @param appsecret 公众号的appsecret
     * @param code      填写第一步获取的code参数
     * @return
     */
    public static WebAccessToken getWebAccessToken(String appid, String appsecret, String code) {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid Cannot be null");
        }
        if (StringUtils.isBlank(appsecret)) {
            throw new RuntimeException("appsecret Cannot be null");
        }
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code Cannot be null");
        }

        final String url = getWebAccessTokenUrl.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        // 失败，连续重试3次
        return RetryUtils.retryOnException(3, new Callable<WebAccessToken>() {
            @Override
            public WebAccessToken call() throws Exception {
                String json = HttpUtils.get(url);
                return new WebAccessToken(json);
            }
        });
    }

    /**
     * 刷新access_token
     * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
     *
     * @param appid        公众号的唯一标识
     * @param refreshToken 刷新access_token
     * @return
     */
    public static WebAccessToken refreshWebAccessToken(String appid, String refreshToken) {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid Cannot be null");
        }
        if (StringUtils.isBlank(refreshToken)) {
            throw new RuntimeException("refreshToken Cannot be null");
        }

        final String url = refreshWebAccessTokenUrl.replace("APPID", appid).replace("REFRESH_TOKEN", refreshToken);
        // 失败，连续重试3次
        return RetryUtils.retryOnException(3, new Callable<WebAccessToken>() {
            @Override
            public WebAccessToken call() throws Exception {
                String json = HttpUtils.get(url);
                return new WebAccessToken(json);
            }
        });
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param openid      用户的唯一标识
     * @param accessToken 网页授权接口调用凭证
     * @return
     */
    public static WebUserinfo getUserinfo(String openid, String accessToken) {
        if (StringUtils.isBlank(openid)) {
            throw new RuntimeException("openid Cannot be null");
        }
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = getUserinfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
        String json = HttpUtils.get(url);
        return new WebUserinfo(json);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param openid      用户的唯一标识
     * @param accessToken 网页授权接口调用凭证
     * @return
     */
    public static ApiResult checkAccessToken(String openid, String accessToken) {
        if (StringUtils.isBlank(openid)) {
            throw new RuntimeException("openid Cannot be null");
        }
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = checkAccessTokenUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

}
