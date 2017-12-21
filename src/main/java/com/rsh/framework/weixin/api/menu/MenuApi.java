package com.rsh.framework.weixin.api.menu;

import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.model.menu.Menu;
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
     * @param accessToken
     * @param menu
     * @return
     */
    public static ApiResult createMenu(String accessToken, Menu menu){
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (menu == null) {
            throw new RuntimeException("menu Cannot be null");
        }
        String url = createMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, menu.toJsonString());
        return new ApiResult(jsonResult);
    }

    /**
     * 查询菜单
     * @param accessToken
     * @return
     */
    public static ApiResult getMenu(String accessToken){
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        String url = getMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 删除自定义菜单
     * @param accessToken
     * @return
     */
    public static ApiResult deleteMenu(String accessToken){
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        String url = deleteMenuUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

}
