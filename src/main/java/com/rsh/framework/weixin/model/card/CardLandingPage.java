package com.rsh.framework.weixin.model.card;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 卡券货架
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/29
 * @Time: 9:42
 */
public class CardLandingPage {

    @JSONField(name = "banner")
    private String banner; // 页面的banner图片链接，须调用，建议尺寸为640*300。
    @JSONField(name = "page_title")
    private String pageTitle; // 页面的title。
    @JSONField(name = "can_share")
    private boolean canShare; // 页面是否可以分享,填入true/false
    @JSONField(name = "scene")
    private String scene; // 投放页面的场景值； SCENE_NEAR_BY 附近 SCENE_MENU 自定义菜单 SCENE_QRCODE 二维码 SCENE_ARTICLE 公众号文章 SCENE_H5 h5页面 SCENE_IVR 自动回复 SCENE_CARD_CUSTOM_CELL 卡券自定义cell
    @JSONField(name = "card_list")
    private List<CardInfo> cardList; // 卡券列表

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public boolean getCanShare() {
        return canShare;
    }

    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public List<CardInfo> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardInfo> cardList) {
        this.cardList = cardList;
    }
}

class CardInfo {
    @JSONField(name = "cardId")
    private String card_id; // 所要在页面投放的card_id
    @JSONField(name = "thumbUrl")
    private String thumb_url; // 缩略图url

    public CardInfo() {
    }

    public CardInfo(String card_id, String thumb_url) {
        this.card_id = card_id;
        this.thumb_url = thumb_url;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
}
