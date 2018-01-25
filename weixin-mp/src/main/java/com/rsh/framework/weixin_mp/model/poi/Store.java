package com.rsh.framework.weixin_mp.model.poi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 * 微信门店小程序 - 门店信息
 *
 * @Description
 * @Date: 2018/1/25
 * @Time: 10:39
 */
public class Store {

    @JSONField(name = "map_poi_id")
    private String mapPoiId; // 从腾讯地图换取的位置点id， 即search_map_poi接口返回的sosomap_poi_uid字段
    @JSONField(name = "pic_list")
    private Pic pic; // 门店图片，可传多张图片 pic_list 字段是一个 json
    @JSONField(name = "contract_phone")
    private String contractPhone; // 联系电话
    @JSONField(name = "hour")
    private String hour; // 营业时间，格式11:11-12:12
    @JSONField(name = "credential")
    private String credential; // 经营资质证件号
    @JSONField(name = "company_name")
    private String companyName; // 主体名字 临时素材mediaid 如果复用公众号主体，则company_name为空 如果不复用公众号主体，则company_name为具体的主体名字
    @JSONField(name = "qualification_list")
    private List<String> qualificationList; // 相关证明材料 临时素材mediaid 不复用公众号主体时，才需要填 支持0~5个mediaid，例如mediaid1
    @JSONField(name = "card_id")
    private String cardId; // 卡券id，如果不需要添加卡券，该参数可为空 目前仅开放支持会员卡、买单和刷卡支付券，不支持自定义code，需要先去公众平台卡券后台创建cardid
    @JSONField(name = "poi_id")
    private String poiId; // 如果是从门店管理迁移门店到门店小程序，则需要填该字段

    public String getMapPoiId() {
        return mapPoiId;
    }

    public void setMapPoiId(String mapPoiId) {
        this.mapPoiId = mapPoiId;
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<String> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }


    public class Pic {
        @JSONField(name = "list")
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    @Override
    public String toString() {
        return "Store{" +
                "mapPoiId='" + mapPoiId + '\'' +
                ", pic=" + pic +
                ", contractPhone='" + contractPhone + '\'' +
                ", hour='" + hour + '\'' +
                ", credential='" + credential + '\'' +
                ", companyName='" + companyName + '\'' +
                ", qualificationList=" + qualificationList +
                ", cardId='" + cardId + '\'' +
                ", poiId='" + poiId + '\'' +
                '}';
    }

    public String getJson() {
        return JSON.toJSONString(this);
    }
}
