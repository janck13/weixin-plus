package com.rsh.framework.weixin_mp.api.media;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rsh.framework.weixin_mp.api.base.AccessTokenApi;
import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.MediaFile;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin_mp.model.media.*;
import com.rsh.framework.weixin.utils.FileUtil;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材管理 API
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/26
 * @Time: 16:25
 */
public class MediaApi {

    private static final String updateUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static final String uploadVideoUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
    private static final String uploadNewsUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    private static final String getMediaUrl = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    private static final String getJssdkMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get/jssdk?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    /*注意点：
        1、临时素材media_id是可复用的。
        2、媒体文件在微信后台保存时间为3天，即3天后media_id失效。
        3、上传临时素材的格式、大小限制与公众平台官网一致。
        图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
        语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
        视频（video）：10MB，支持MP4格式
        缩略图（thumb）：64KB，支持JPG格式
    */

    /**
     * 新增临时素材
     *
     * @param mediaFileType 素材文件类型
     * @param media         素材文件
     * @return
     */
    public static ApiResult updateMedia(MediaFileType mediaFileType, File media) {
        if (mediaFileType == null) {
            throw new WeixinApiException("mediaType Cannot be null");
        }
        if (media == null || !media.exists()) {
            throw new WeixinApiException("media file is null or not exists");
        }
        checkMediaFile(mediaFileType, media);

        String url = updateUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("TYPE", mediaFileType.name());

        String json = HttpUtils.upload(url, media, null);
        return new ApiResult(json);
    }

    /**
     * 视频群发消息素材上传
     *
     * @param mediaId     素材id
     * @param title
     * @param description
     * @return
     */
    public static ApiResult updateVideo(String mediaId, String title, String description) {
        if (mediaId == null) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = uploadVideoUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("media_id", mediaId);
        param.put("title", title);
        param.put("description", description);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     *
     * @param mediaArticles 图文消息
     * @return
     */
    public static ApiResult uploadnews(List<MediaArticle> mediaArticles) {
        if (mediaArticles == null) {
            throw new WeixinApiException("mediaArticles Cannot be null");
        }
        String url = uploadNewsUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("articles", JSON.toJSONString(mediaArticles));

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取临时素材
     * 请注意，视频文件不支持https下载，调用该接口需http协议。
     *
     * @param mediaId 素材id
     * @return
     */
    public static MediaFile getMedia(String mediaId) {
        if (mediaId == null) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = getMediaUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("MEDIA_ID", mediaId);
        return HttpUtils.download(url);
    }

    /**
     * 高清语音素材获取接口
     * 公众号可以使用本接口获取从JSSDK的uploadVoice接口上传的临时语音素材，格式为speex，16K采样率。
     * 该音频比上文的临时素材获取接口（格式为amr，8K采样率）更加清晰，适合用作语音识别等对音质要求较高的业务。
     *
     * @param mediaId 媒体文件ID，即uploadVoice接口返回的serverID
     * @return
     */
    public static MediaFile getJssdkMedia(String mediaId) {
        if (mediaId == null) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = getJssdkMediaUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("MEDIA_ID", mediaId);
        return HttpUtils.download(url);
    }

    /**
     * 永久素材：
     * 1、最近更新：永久图片素材新增后，将带有URL返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。
     * 2、公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为5000，其他类型为1000。
     * 3、素材的格式大小等要求与公众平台官网一致：
     * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
     * 语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式
     * 视频（video）：10MB，支持MP4格式
     * 缩略图（thumb）：64KB，支持JPG格式
     * 4、图文消息的具体内容中，微信后台将过滤外部的图片链接，图片url需通过"上传图文消息内的图片获取URL"接口上传图片获取。
     * 5、"上传图文消息内的图片获取URL"接口所上传的图片，不占用公众号的素材库中图片数量的5000个的限制，图片仅支持jpg/png格式，大小必须在1MB以下。
     * 6、图文消息支持正文中插入自己帐号和其他公众号已群发文章链接的能力。
     */

    private static final String addNewsUrl = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";

    /**
     * 新增永久图文素材
     *
     * @param mediaArticles 图片素材
     * @return
     */
    public static ApiResult addNews(List<MediaArticle> mediaArticles) {
        if (mediaArticles == null) {
            throw new WeixinApiException("mediaArticles Cannot be null");
        }
        String url = addNewsUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("articles", JSON.toJSONString(mediaArticles));

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    private static final String uploadimgUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

    /**
     * 上传图文消息内的图片获取URL
     *
     * @param media 图片文件, 图片仅支持jpg/png格式，大小必须在1MB以下
     * @return
     */
    public static ApiResult uploadimg(File media) {
        if (media == null || !media.exists()) {
            throw new WeixinApiException("media file is null or not exists");
        }
        if (!FileUtil.checkFileType(media.getName(), new String[]{"jpg, png"})) {
            throw new WeixinApiException("图片仅支持jpg/png格式");
        }
        if (media.length() >= (1 * 1024 * 1024)) {
            throw new WeixinApiException("图片大小必须在1MB以下");
        }

        String url = uploadimgUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.upload(url, media, null);
        return new ApiResult(json);
    }

    private static final String addMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 新增其他类型永久素材
     *
     * @param mediaFileType     素材类型
     * @param media             图片文件
     * @param videoTitle        视频素材的标题
     * @param videoIntroduction 视频素材的描述
     * @return
     */
    public static ApiResult addMaterial(MediaFileType mediaFileType, File media, String videoTitle, String videoIntroduction) {
        if (mediaFileType == null) {
            throw new WeixinApiException("mediaType Cannot be null");
        }
        if (media == null || !media.exists()) {
            throw new WeixinApiException("media file is null or not exists");
        }
        Map<String, String> param = null;
        // 视频素材
        if (MediaFileType.video == mediaFileType) {
            if (StringUtils.isBlank(videoTitle)) {
                throw new WeixinApiException("videoTitle Cannot be null");
            }
            if (StringUtils.isBlank(videoIntroduction)) {
                throw new WeixinApiException("videoIntroduction Cannot be null");
            }
            JSONObject jb = new JSONObject();
            jb.put("title", videoTitle);
            jb.put("introduction", videoIntroduction);

            param = new HashMap<>();
            param.put("description", jb.toJSONString());
        }
        checkMediaFile(mediaFileType, media);

        String url = addMaterialUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken()).replace("TYPE", mediaFileType.name());

        String json = HttpUtils.upload(url, media, param);
        return new ApiResult(json);
    }

    private static final String getMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

    /**
     * 获取永久素材，仅支持：图文，视频
     *
     * @param mediaId 素材的media_id
     * @return
     */
    public static ApiResult getMaterialJson(String mediaId) {
        if (StringUtils.isBlank(mediaId)) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = getMaterialUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("media_id", mediaId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 获取永久素材，其他类型的素材文件
     *
     * @param mediaId 素材的media_id
     * @return
     */
    public static MediaFile getMaterialFile(String mediaId) {
        if (StringUtils.isBlank(mediaId)) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = getMaterialUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("media_id", mediaId);

        return HttpUtils.download(url, JSON.toJSONString(param));
    }

    private static final String delMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";

    /**
     * 删除永久素材
     *
     * @param mediaId 素材的media_id
     * @return
     */
    public static ApiResult delMaterial(String mediaId) {
        if (StringUtils.isBlank(mediaId)) {
            throw new WeixinApiException("mediaId Cannot be null");
        }
        String url = delMaterialUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("media_id", mediaId);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    private static final String updateNewsUrl = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";

    /**
     * 修改永久图文素材
     *
     * @param updateMediaArticle
     * @return
     */
    public static ApiResult updateNews(UpdateMediaArticle updateMediaArticle) {
        if (updateMediaArticle == null) {
            throw new WeixinApiException("updateMediaArticle Cannot be null");
        }
        String url = updateNewsUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.post(url, JSON.toJSONString(updateMediaArticle));
        return new ApiResult(json);
    }

    private static final String getMaterialcountUrl = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";

    /**
     * 获取素材总数
     * <pre>
     * {
     *  "voice_count":COUNT,
     *  "video_count":COUNT,
     *  "image_count":COUNT,
     *  "news_count":COUNT
     * }
     * </pre>
     *
     * @return
     */
    public static ApiResult getMaterialcount() {
        String url = getMaterialcountUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        String json = HttpUtils.get(url);
        return new ApiResult(json);
    }

    private static final String batchgetMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    /**
     * 获取素材列表
     *
     * @param mediaType 素材类型
     * @param offset    从全部素材的该偏移位置开始返回，0表示从第一个素材返回
     * @param count     返回素材的数量，取值在1到20之间
     * @return
     */
    public static ApiResult batchgetMaterial(MediaType mediaType, int offset, int count) {
        if (mediaType == null) {
            throw new WeixinApiException("mediaType Cannot be null");
        }
        if (offset < 0) {
            offset = 0;
        }
        if (count < 1) {
            count = 1;
        }
        if (count > 20) {
            count = 20;
        }

        String url = batchgetMaterialUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessToken().getToken());

        Map<String, Object> param = new HashMap<>();
        param.put("type", mediaType.name());
        param.put("offset", offset);
        param.put("count", count);

        String json = HttpUtils.post(url, JSON.toJSONString(param));
        return new ApiResult(json);
    }

    /**
     * 校验素材文件格式及大小
     *
     * @param mediaFileType
     * @param media
     */
    private static void checkMediaFile(MediaFileType mediaFileType, File media) {
        String fileTypes[] = null;
        long maxFileSize = 0;
        switch (mediaFileType) {
            case image:
                fileTypes = new String[]{"png", "jpeg", "jpg", "gif"};
                maxFileSize = 2 * 2014 * 1024;
                break;
            case voice:
                fileTypes = new String[]{"amr", "mp3"};
                maxFileSize = 2 * 2014 * 1024;
                break;
            case video:
                fileTypes = new String[]{"mp4"};
                maxFileSize = 10 * 2014 * 1024;
                break;
            case thumb:
                fileTypes = new String[]{"jpg"};
                maxFileSize = 64 * 2014;
                break;
            default:
                throw new WeixinApiException("mediaFileType无效！");
        }

        if (!FileUtil.checkFileType(media.getName(), fileTypes)) {
            throw new WeixinApiException("素材文件格式不支持！");
        }

        if (media.length() > maxFileSize) {
            throw new WeixinApiException("素材文件大小超过限制" + maxFileSize + "(b)！");
        }
    }

}
