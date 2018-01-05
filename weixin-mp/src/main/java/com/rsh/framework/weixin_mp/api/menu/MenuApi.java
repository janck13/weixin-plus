package com.rsh.framework.weixin_mp.api.menu;

import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin_mp.model.menu.Menu;
import com.rsh.framework.weixin.utils.HttpUtils;

/**
 * 自定义菜单接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 15:27
 */
public class MenuApi {

    private static final String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String getMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    private static final String deleteMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    /**
     * 创建自定义菜单
     *
     * @param menu
     * @return
     */
    public static ApiResult createMenu(Menu menu) {
        if (menu == null) {
            throw new WeixinApiException("menu Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = createMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, menu.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 查询菜单
     *
     * @return
     */
    public static ApiResult getMenu() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 删除自定义菜单
     *
     * @return
     */
    public static ApiResult deleteMenu() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = deleteMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

}
