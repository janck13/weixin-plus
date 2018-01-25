package com.rsh.framework.weixin_component.api.media;

import com.rsh.framework.weixin.common.exception.WeixinApiException;
import com.rsh.framework.weixin.common.model.ApiResult;
import com.rsh.framework.weixin.utils.FileUtil;
import com.rsh.framework.weixin.utils.HttpUtils;
import com.rsh.framework.weixin_component.api.auth.ComponentAccessTokenApi;
import com.rsh.framework.weixin_component.model.media.MediaFileType;

import java.io.File;

/**
 * 素材 API
 * Created By Rsh
 * 请使用第三方平台账号的ACCESS_TOKEN调用（已开权限）
 *
 * @Description
 * @Date: 2018/1/23
 * @Time: 17:03
 */
public class MediaApi {

    private static final String updateUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

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

        String url = updateUrl.replace("ACCESS_TOKEN", ComponentAccessTokenApi.getComponentAccessToken().getToken()).replace("TYPE", mediaFileType.name());

        String json = HttpUtils.upload(url, media, null);
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
