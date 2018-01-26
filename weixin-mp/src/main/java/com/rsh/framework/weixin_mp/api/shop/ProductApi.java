package com.rsh.framework.weixin_mp.api.shop;

import com.alibaba.fastjson.JSON;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin_mp.model.shop.product.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Rsh
 * 商品管理 API
 *
 * @Description
 * @Date: 2018/1/25
 * @Time: 14:43
 */
public class ProductApi {

    private static final String createUrl = "https://api.weixin.qq.com/merchant/create?access_token=ACCESS_TOKEN";
    private static final String delUrl = "https://api.weixin.qq.com/merchant/del?access_token=ACCESS_TOKEN";
    private static final String updateUrl = "https://api.weixin.qq.com/merchant/update?access_token=ACCESS_TOKEN";
    private static final String getUrl = "https://api.weixin.qq.com/merchant/get?access_token=ACCESS_TOKEN";
    private static final String getByStatusUrl = "https://api.weixin.qq.com/merchant/getbystatus?access_token=ACCESS_TOKEN";
    private static final String modProductStatusUrl = "https://api.weixin.qq.com/merchant/modproductstatus?access_token=ACCESS_TOKEN";
    private static final String getSubCategoryUrl = "https://api.weixin.qq.com/merchant/category/getsub?access_token=ACCESS_TOKEN";
    private static final String getSkuByCategoryUrl = "https://api.weixin.qq.com/merchant/category/getsku?access_token=ACCESS_TOKEN";
    private static final String getPropertyByCategoryUrl = "https://api.weixin.qq.com/merchant/category/getproperty?access_token=ACCESS_TOKEN";

    /**
     * 增加商品
     *
     * @param product
     * @return
     */
    public static ApiResult create(Product product) {
        if (product == null) {
            throw new WeixinApiException("product Cannot be null");
        }

        return create(product.getJson());
    }

    /**
     * 增加商品
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult create(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = createUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 删除商品
     *
     * @param productId 商品id
     * @return
     */
    public static ApiResult del(String productId) {
        if (StringUtils.isBlank(productId)) {
            throw new WeixinApiException("productId Cannot be null");
        }

        String url = delUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, String> param = new HashMap<>(1);
        param.put("product_id", productId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    public static ApiResult update(Product product) {
        if (product == null) {
            throw new WeixinApiException("product Cannot be null");
        }

        return update(product.getJson());
    }

    /**
     * 修改商品
     *
     * @param jsonData json数据
     * @return
     */
    public static ApiResult update(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new WeixinApiException("jsonData Cannot be null");
        }

        String url = updateUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, jsonData);
        return new ApiResult(json);
    }

    /**
     * 查询商品
     *
     * @param productId 商品ID
     * @return
     */
    public static ApiResult get(String productId) {
        if (StringUtils.isBlank(productId)) {
            throw new WeixinApiException("productId Cannot be null");
        }

        String url = getUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, String> param = new HashMap<>(1);
        param.put("product_id", productId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取指定状态的所有商品
     *
     * @param status 商品状态(0-全部, 1-上架, 2-下架)
     * @return
     */
    public static ApiResult getByStatus(int status) {
        if (status != 0 && status != 1 && status != 2) {
            throw new WeixinApiException("status range of value[0,1,2]");
        }

        String url = getByStatusUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("status", status);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 商品上下架
     *
     * @param productId 商品ID
     * @param status    商品上下架标识(0-下架, 1-上架)
     * @return
     */
    public static ApiResult modProductStatus(String productId, int status) {
        if (StringUtils.isBlank(productId)) {
            throw new WeixinApiException("productId Cannot be null");
        }
        if (status != 0 && status != 1) {
            throw new WeixinApiException("status range of value[0,1]");
        }

        String url = modProductStatusUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(2);
        param.put("product_id", productId);
        param.put("status", status);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取指定分类的所有子分类
     *
     * @param cateId 大分类ID(根节点分类id为1)
     * @return
     */
    public static ApiResult getSubCategory(String cateId) {
        if (StringUtils.isBlank(cateId)) {
            throw new WeixinApiException("cateId Cannot be null");
        }
        String url = getSubCategoryUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("cate_id", cateId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取指定子分类的所有SKU
     *
     * @param cateId 商品子分类ID
     * @return
     */
    public static ApiResult getSkuByCategory(String cateId) {
        if (StringUtils.isBlank(cateId)) {
            throw new WeixinApiException("cateId Cannot be null");
        }
        String url = getSkuByCategoryUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("cate_id", cateId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取指定分类的所有属性
     *
     * @param cateId 分类ID
     * @return
     */
    public static ApiResult getPropertyByCategory(String cateId) {
        if (StringUtils.isBlank(cateId)) {
            throw new WeixinApiException("cateId Cannot be null");
        }
        String url = getPropertyByCategoryUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());
        Map<String, Object> param = new HashMap<>(1);
        param.put("cate_id", cateId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

}
