package com.rsh.framework.weixin_mp.model.shop.product;

/**
 * Created By Rsh
 * 商品所在地地址
 *
 * @Description
 * @Date: 2018/1/26
 * @Time: 12:08
 */
public class Location {

    private String country;
    private String province;
    private String city;
    private String address;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
