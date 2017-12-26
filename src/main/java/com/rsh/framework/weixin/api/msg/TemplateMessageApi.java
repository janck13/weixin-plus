package com.rsh.framework.weixin.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.api.base.AccessTokenApi;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板消息接口
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/22
 * @Time: 10:55
 */
public class TemplateMessageApi {

    private static final String setIndustryUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
    private static final String getIndustryUrl = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    private static final String addTemplateUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
    private static final String getAllPrivateTemplateUrl = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
    private static final String deletePrivateTemplateUrl = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
    private static final String sendTemplateMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


    /**
     * 设置所属行业
     *
     * @param industryId1 公众号模板消息所属行业编号
     * @param industryId2 公众号模板消息所属行业编号
     * @return
     */
    public static ApiResult setIndustry(String industryId1, String industryId2) {
        if (StringUtils.isBlank(industryId1)) {
            throw new RuntimeException("industryId1 Cannot be null");
        }
        if (StringUtils.isBlank(industryId2)) {
            throw new RuntimeException("industryId2 Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        Map<String, String> param = new HashMap<>();
        param.put("industry_id1", industryId1);
        param.put("industry_id2", industryId2);

        String url = setIndustryUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }

    /**
     * 获取设置的行业信息
     *
     * @return
     */
    public static ApiResult getIndustry() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getIndustryUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 获得模板ID
     *
     * @param templateIdShort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return
     */
    public static ApiResult getTemplateId(String templateIdShort) {
        if (StringUtils.isBlank(templateIdShort)) {
            throw new RuntimeException("templateIdShort Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        Map<String, String> param = new HashMap<>();
        param.put("template_id_short", templateIdShort);

        String url = addTemplateUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }

    /**
     * 获取模板列表
     *
     * @return
     */
    public static ApiResult getAllPrivateTemplate() {
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = getAllPrivateTemplateUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 删除模板
     *
     * @param templateId  公众帐号下模板消息ID
     * @return
     */
    public static ApiResult deletePrivateTemplate(String templateId) {
        if (StringUtils.isBlank(templateId)) {
            throw new RuntimeException("templateId Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        Map<String, String> param = new HashMap<>();
        param.put("template_id", templateId);

        String url = deletePrivateTemplateUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(jsonResult);
    }

    /**
     * 发送模板消息
     *
     * @param json        post报文
     * @return
     */
    public static ApiResult sendTemplateMessage(String json) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json Cannot be null");
        }
        String accessToken = AccessTokenApi.getAccessToken().getToken();

        String url = sendTemplateMessageUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

}
