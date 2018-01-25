package com.rsh.framework.weixin.utils;


import com.sun.istack.internal.NotNull;

/**
 * Created By Rsh
 *
 * @Description
 * @Date: 2017/12/19
 * @Time: 11:18
 */
public class Test {

    public static void main(String[] args) {
        String str = "123|456";
        System.out.println(str.split("\\|")[0]);
        System.out.println(9%8);

        System.out.println(Long.valueOf("10000000100"));
        System.out.println(Integer.MAX_VALUE);

        System.out.println("--------------");
        int concurrencyLevel = 16;
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
            System.out.println(ssize);
            System.out.println("==");
        }
        System.out.println(sshift);
        System.out.println(ssize);

    }

    /**
     * author：rsh
     * date：${DATE}
     * desc：
     * @param str
     */
    public void test(String str){
        String str1 = "123|456";
        System.out.print(str1.split("\\|")[0]);
    }
}
