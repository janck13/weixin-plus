package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送小程序卡片（要求小程序与公众号已关联）
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendMiniprogrampageMessage extends BaseSendMessage{

    private MiniprogrampageItem miniprogrampage;

    public MiniprogrampageItem getMiniprogrampage() {
        return miniprogrampage;
    }

    public void setMiniprogrampage(MiniprogrampageItem miniprogrampage) {
        this.miniprogrampage = miniprogrampage;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
