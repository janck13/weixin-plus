package com.rsh.framework.weixin.model.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/21
 * @Time: 15:14
 */
public class Menu {

    @JSONField(name = "button")
    List<MenuItem> button;

    public List<MenuItem> getButton() {
        return button;
    }

    public void setButton(List<MenuItem> button) {
        this.button = button;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
