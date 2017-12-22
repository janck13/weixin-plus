package com.rsh.framework.weixin.api.msg;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;

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
     * @param accessToken
     * @param industryId1 公众号模板消息所属行业编号
     * @param industryId2 公众号模板消息所属行业编号
     * @return
     */
    public ApiResult setIndustry(String accessToken, String industryId1, String industryId2) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (industryId1 == null) {
            throw new RuntimeException("industryId1 Cannot be null");
        }
        if (industryId2 == null) {
            throw new RuntimeException("industryId2 Cannot be null");
        }

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
     * @param accessToken
     * @return
     */
    public ApiResult getIndustry(String accessToken) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = getIndustryUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 获得模板ID
     *
     * @param accessToken
     * @param templateIdShort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return
     */
    public ApiResult addTemplate(String accessToken, String templateIdShort) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (templateIdShort == null) {
            throw new RuntimeException("templateIdShort Cannot be null");
        }

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
     * @param accessToken
     * @return
     */
    public ApiResult getAllPrivateTemplate(String accessToken) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }

        String url = getAllPrivateTemplateUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    /**
     * 删除模板
     *
     * @param accessToken
     * @param templateId  公众帐号下模板消息ID
     * @return
     */
    public ApiResult deletePrivateTemplate(String accessToken, String templateId) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (templateId == null) {
            throw new RuntimeException("templateId Cannot be null");
        }

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
     * @param accessToken
     * @param json        post报文
     * @return
     */
    public ApiResult sendTemplateMessage(String accessToken, String json) {
        if (accessToken == null) {
            throw new RuntimeException("accessToken Cannot be null");
        }
        if (json == null) {
            throw new RuntimeException("json Cannot be null");
        }

        String url = sendTemplateMessageUrl;
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonResult = HttpUtils.post(url, json);
        return new ApiResult(jsonResult);
    }

}
