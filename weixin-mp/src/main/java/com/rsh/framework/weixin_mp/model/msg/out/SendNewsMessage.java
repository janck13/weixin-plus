package com.rsh.framework.weixin_mp.model.msg.out;

import com.alibaba.fastjson.JSON;

/**
 * 发送图文消息（点击跳转到外链） 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应。
 * Created By Rsh
 * @Description
 * @Date: 2017/12/21
 * @Time: 11:15
 */
public class SendNewsMessage extends BaseSendMessage{

    private NewsItem news;

    public NewsItem getNews() {
        return news;
    }

    public void setNews(NewsItem news) {
        this.news = news;
    }

    @Override
    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
