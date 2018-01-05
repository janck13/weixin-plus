package com.rsh.framework.weixin.utils.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/20
 * @Time: 16:17
 */
public class AdapterCDATA extends XmlAdapter<String, String> {
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }
}
