package com.rsh.framework.weixin.api.user;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户标签管理 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 15:15
 */
public class UserTagApi {

    private static final String createTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
    private static final String getTagsUrl = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
    private static final String updateTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
    private static final String deleteTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
    private static final String getTagUserUrl = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
    private static final String batchAddUserTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
    private static final String batchCancelUserTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
    private static final String getUserTagUrl = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";

    /**
     * 创建标签
     * 一个公众号，最多可以创建100个标签。
     *
     * @param name 标签名（30个字符以内）
     * @return
     */
    public static ApiResult createTag(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new WeixinApiException("name Cannot be null");
        }
        if (name.length() > 30) {
            throw new WeixinApiException("name length max 30");
        }

        String url = createTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> tag = new HashMap<>();
        tag.put("name", name);
        param.put("tag", tag);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取公众号已创建的标签
     *
     * @return
     */
    public static ApiResult getTags() {
        String url = getTagsUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    /**
     * 编辑标签
     *
     * @param tagId 标签id
     * @param name  标签名（30个字符以内）
     * @return
     */
    public static ApiResult updateTag(int tagId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new WeixinApiException("name Cannot be null");
        }
        if (name.length() > 30) {
            throw new WeixinApiException("name length max 30");
        }

        String url = updateTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> tag = new HashMap<>();
        tag.put("id", tagId);
        tag.put("name", name);
        param.put("tag", tag);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     * @return
     */
    public static ApiResult deleteTag(int tagId) {
        String url = deleteTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> tag = new HashMap<>();
        tag.put("id", tagId);
        param.put("tag", tag);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId 标签id
     * @return
     */
    public static ApiResult getTagUser(int tagId) {
        return getTagUser(tagId, null);
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId      标签id
     * @param nextOpenid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return
     */
    public static ApiResult getTagUser(int tagId, String nextOpenid) {
        String url = getTagUserUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("tagid", tagId);
        if (nextOpenid != null) {
            param.put("next_openid", nextOpenid);
        }

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 批量为用户打标签
     * 标签功能目前支持公众号为用户打上最多20个标签。
     *
     * @param tagId      标签id
     * @param openidList openid列表
     * @return
     */
    public static ApiResult batchAddUserTag(int tagId, List<String> openidList) {
        if (openidList != null && openidList.size() > 50) {
            throw new WeixinApiException("openidList size max 50");
        }
        String url = batchAddUserTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("tagid", tagId);
        param.put("openid_list", openidList);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 批量为用户取消标签
     *
     * @param tagId      标签id
     * @param openidList openid列表
     * @return
     */
    public static ApiResult batchCancelUserTag(int tagId, List<String> openidList) {
        if (openidList != null && openidList.size() > 50) {
            throw new WeixinApiException("openidList size max 50");
        }
        String url = batchCancelUserTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("tagid", tagId);
        param.put("openid_list", openidList);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取用户身上的标签列表
     *
     * @param openid
     * @return
     */
    public static ApiResult getUserTag(String openid) {
        if (StringUtils.isBlank(openid)) {
            throw new WeixinApiException("openid Cannot be null");
        }
        String url = getUserTagUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("openid", openid);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
