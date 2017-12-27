package com.rsh.framework.weixin.api.newsmsgcomment;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.exception.WeixinApiException;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 群发图文消息评论功能 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 14:20
 */
public class NewsMsgCommentApi {

    private static final String openCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/open?access_token=ACCESS_TOKEN";
    private static final String closeCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/close?access_token=ACCESS_TOKEN";
    private static final String getCommentDataUrl = "https://api.weixin.qq.com/cgi-bin/comment/list?access_token=ACCESS_TOKEN";
    private static final String markelectCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/markelect?access_token=ACCESS_TOKEN";
    private static final String unmarkelectCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/unmarkelect?access_token=ACCESS_TOKEN";
    private static final String deleteCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/delete?access_token=ACCESS_TOKEN";
    private static final String addreplyCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/reply/add?access_token=ACCESS_TOKEN";
    private static final String deleteReplyCommentUrl = "https://api.weixin.qq.com/cgi-bin/comment/reply/delete?access_token=ACCESS_TOKEN";

    /**
     * 打开已群发文章评论
     *
     * @param msgDataId 群发消息返回的msg_data_id
     * @param index     多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return
     */
    public static ApiResult openComment(Integer msgDataId, Integer index) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        String url = openCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 关闭已群发文章评论
     *
     * @param msgDataId 群发消息返回的msg_data_id
     * @param index     多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return
     */
    public static ApiResult closeComment(Integer msgDataId, Integer index) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        String url = closeCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 查看指定文章的评论数据
     *
     * @param msgDataId 群发消息返回的msg_data_id
     * @param index     多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param begin     起始位置
     * @param count     获取数目（>=50会被拒绝）
     * @param type      type=0 普通评论&精选评论 type=1 普通评论 type=2 精选评论
     * @return
     */
    public static ApiResult getCommentData(Integer msgDataId, Integer index, int begin, int count, int type) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) index = 0;
        if (begin < 0) begin = 0;
        if (count < 1) count = 1;
        if (count >= 50) count = 40;

        String url = getCommentDataUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("begin", begin);
        param.put("count", count);
        param.put("type", type);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 将评论标记精选
     *
     * @param msgDataId     群发消息返回的msg_data_id
     * @param index         多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId 用户评论id
     * @return
     */
    public static ApiResult markelectComment(Integer msgDataId, Integer index, Integer userCommentId) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        if (userCommentId == null) {
            throw new WeixinApiException("userCommentId Cannot be null");
        }
        String url = markelectCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("user_comment_id ", userCommentId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 将评论取消精选
     *
     * @param msgDataId     群发消息返回的msg_data_id
     * @param index         多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId 用户评论id
     * @return
     */
    public static ApiResult unmarkelectComment(Integer msgDataId, Integer index, Integer userCommentId) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        if (userCommentId == null) {
            throw new WeixinApiException("userCommentId Cannot be null");
        }
        String url = unmarkelectCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("user_comment_id ", userCommentId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 删除评论
     *
     * @param msgDataId     群发消息返回的msg_data_id
     * @param index         多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId 用户评论id
     * @return
     */
    public static ApiResult deleteComment(Integer msgDataId, Integer index, Integer userCommentId) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        if (userCommentId == null) {
            throw new WeixinApiException("userCommentId Cannot be null");
        }
        String url = deleteCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("user_comment_id ", userCommentId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 回复评论
     *
     * @param msgDataId     群发消息返回的msg_data_id
     * @param index         多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId 用户评论id
     * @param content       回复内容
     * @return
     */
    public static ApiResult addreplyComment(Integer msgDataId, Integer index, Integer userCommentId, String content) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        if (userCommentId == null) {
            throw new WeixinApiException("userCommentId Cannot be null");
        }
        if (StringUtils.isEmpty(content)) {
            throw new WeixinApiException("content Cannot be null");
        }
        String url = addreplyCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("user_comment_id ", userCommentId);
        param.put("content ", content);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 删除回复
     *
     * @param msgDataId     群发消息返回的msg_data_id
     * @param index         多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId 用户评论id
     * @return
     */
    public static ApiResult deleteReplyComment(Integer msgDataId, Integer index, Integer userCommentId) {
        if (msgDataId == null) {
            throw new WeixinApiException("msgDataId Cannot be null");
        }
        if (index == null) {
            index = 0;
        }
        if (userCommentId == null) {
            throw new WeixinApiException("userCommentId Cannot be null");
        }
        String url = deleteReplyCommentUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("msg_data_id", msgDataId);
        param.put("index", index);
        param.put("user_comment_id ", userCommentId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}