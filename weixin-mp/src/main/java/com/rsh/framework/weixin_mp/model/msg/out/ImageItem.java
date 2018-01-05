package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:17
 */
public class ImageItem {

    @JSONField(name = "media_id")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
