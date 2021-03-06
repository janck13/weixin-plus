package com.rsh.framework.weixin.utils;

/**
 * Created with IntelliJ IDEA
 * Created By Rsh
 * Date: 2017/12/15
 * Time: 13:19
 */
public class ClassUtils {

    /**
     * 确定class是否可以被加载
     * @param className 完整类名
     * @param classLoader 类加载
     * @return {boolean}
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            Class.forName(className, true, classLoader);
            return true;
        }
        catch (Throwable ex) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }

}
