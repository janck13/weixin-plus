package com.rsh.framework.weixin_mp.api.customservice;

import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin_mp.model.customservice.Kfaccount;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.io.File;

/**
 * 客服账号接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 17:19
 */
public class CustomserviceAccountApi {

    private static final String addAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
    private static final String updateAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
    private static final String deleteAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
    private static final String uploadAccountHeadimgUrl = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=";
    private static final String getAccountListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";

    /**
     * 添加客服帐号
     *
     * @param kfaccount
     * @return
     */
    public static ApiResult addAccount(Kfaccount kfaccount) {
        if (kfaccount == null) {
            throw new WeixinApiException("kfaccount Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = addAccountUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, kfaccount.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 修改客服帐号
     *
     * @param kfaccount
     * @return
     */
    public static ApiResult updateAccount(Kfaccount kfaccount) {
        if (kfaccount == null) {
            throw new WeixinApiException("kfaccount Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = updateAccountUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, kfaccount.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 删除客服帐号
     *
     * @param kfaccount
     * @return
     */
    public static ApiResult deleteAccount(Kfaccount kfaccount) {
        if (kfaccount == null) {
            throw new WeixinApiException("kfaccount Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = deleteAccountUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, kfaccount.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 设置客服帐号的头像
     *
     * @param kfaccount
     * @param headImg
     * @return
     */
    public static ApiResult updateAccountHeadimg(String kfaccount, File headImg) {
        if (StringUtils.isBlank(kfaccount)) {
            throw new WeixinApiException("kfaccount Cannot be null");
        }
        if (headImg == null || !headImg.exists()) {
            throw new WeixinApiException("headImg file is null or not exists");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = uploadAccountHeadimgUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        url = url + kfaccount;
        String jsonResult = HttpUtils.upload(url, headImg, null);
        return new ApiResult(jsonResult);
    }

    /**
     * 获取所有客服账号
     *
     * @return
     */
    public static ApiResult getAccountList() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getAccountListUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }


}
