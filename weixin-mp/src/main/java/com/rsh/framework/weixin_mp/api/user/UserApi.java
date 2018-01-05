package com.rsh.framework.weixin_mp.api.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin_mp.model.user.UserInfo;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 16:43
 */
public class UserApi {

    private static final String updateremarkUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
    private static final String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private static final String batchGetUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    private static final String getUsersUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    private static final String getBlackListUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN";
    private static final String batchBlackListUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN";
    private static final String batchUnblackListUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN";

    /**
     * 设置用户备注名
     *
     * @param openid
     * @param remark 新的备注名，长度必须小于30字符
     * @return
     */
    public static ApiResult updateremark(String openid, String remark) {
        if (StringUtils.isEmpty(openid)) {
            throw new WeixinApiException("openid Cannot be null");
        }
        if (StringUtils.isEmpty(remark)) {
            throw new WeixinApiException("remark Cannot be null");
        }
        if (remark.length() > 30) {
            throw new WeixinApiException("remark length max 30");
        }

        String url = updateremarkUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("openid", openid);
        param.put("remark", remark);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取用户基本信息（包括UnionID机制）
     *
     * @param openid
     * @return
     */
    public static UserInfo getUserInfo(String openid) {
        if (StringUtils.isEmpty(openid)) {
            throw new WeixinApiException("openid Cannot be null");
        }

        String url = getUserInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("OPENID", openid);
        String json = HttpUtils.get(url);

        return new UserInfo(json);
    }

    /**
     * 批量获取用户基本信息
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     *
     * @param openidList
     * @return
     */
    public static List<UserInfo> batchGetUserInfo(List<String> openidList) {
        if (openidList == null) {
            throw new WeixinApiException("openidList Cannot be null");
        }
        if (openidList.size() > 100) {
            throw new WeixinApiException("openidList size max 100");
        }

        Map<String, Object> param = new HashMap<>();
        List<Map<String, String>> userList = new ArrayList<>();
        for (String openid : openidList) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("openid", openid);
            userMap.put("lang", "zh_CN");
            userList.add(userMap);
        }
        param.put("user_list", userList);

        String url = batchGetUserInfoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        String json = HttpUtils.post(url, JSON.toJSONString(param));

        ApiResult result = new ApiResult(json);
        if (result.isSucceed()) {
            List<UserInfo> userInfos = new ArrayList<>();
            List list = result.getList("user_info_list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject jb = (JSONObject) list.get(i);
                UserInfo userInfo = JSONObject.toJavaObject(jb, UserInfo.class);
                userInfos.add(userInfo);
            }
            return userInfos;
        } else {
            throw new WeixinApiException("api调用失败：" + result.getErrorCode() + "-" + result.getErrorMsg());
        }
    }

    /**
     * 获取用户列表
     * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
     * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
     * 注意：
     * 当公众号关注者数量超过10000时，可通过填写next_openid的值，从而多次拉取列表的方式来满足需求。
     * 具体而言，就是在调用接口时，将上一次调用得到的返回中的next_openid值，作为下一次调用中的next_openid值。
     *
     * @param nextOpenid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return
     */
    public static ApiResult getUsers(String nextOpenid) {
        String url = getUsersUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("NEXT_OPENID", nextOpenid);
        String json = HttpUtils.get(url);

        return new ApiResult(json);
    }

    /**
     * 获取公众号的黑名单列表
     * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
     *
     * @param beginOpenid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return
     */
    public static ApiResult getBlackList(String beginOpenid) {

        Map<String, Object> param = new HashMap<>();
        param.put("begin_openid", beginOpenid);

        String url = getBlackListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 拉黑用户
     * 公众号可通过该接口来拉黑一批用户
     *
     * @param openidList 需要拉入黑名单的用户的openid，一次拉黑最多允许20个
     * @return
     */
    public static ApiResult batchBlackList(List<String> openidList) {
        if (openidList == null) {
            throw new WeixinApiException("openidList Cannot be null");
        }
        if (openidList.size() > 20) {
            throw new WeixinApiException("openidList size max 20");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("openid_list", openidList);

        String url = batchBlackListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 取消拉黑用户
     * 公众号可通过该接口来取消拉黑一批用户
     *
     * @param openidList 需要取消拉黑用户的openid，一次拉黑最多允许20个
     * @return
     */
    public static ApiResult batchUnblackList(List<String> openidList) {
        if (openidList == null) {
            throw new WeixinApiException("openidList Cannot be null");
        }
        if (openidList.size() > 20) {
            throw new WeixinApiException("openidList size max 20");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("openid_list", openidList);

        String url = batchUnblackListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
