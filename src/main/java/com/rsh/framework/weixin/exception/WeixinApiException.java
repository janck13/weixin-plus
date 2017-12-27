package com.rsh.framework.weixin.exception;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/27
 * @Time: 9:54
 */
public class WeixinApiException extends RuntimeException {

    public WeixinApiException(){
        super();
    }

    public WeixinApiException(String message){
        super(message);
    }

    public WeixinApiException(Throwable cause){
        super(cause);
    }

    public WeixinApiException(String message, Throwable cause){
        super(message, cause);
    }
}
