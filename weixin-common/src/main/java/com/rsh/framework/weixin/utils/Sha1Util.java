package com.rsh.framework.weixin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/*
'============================================================================
'api说明：
'createSHA1Sign创建签名SHA1
'getSha1()Sha1签名
'============================================================================
'*/
public class Sha1Util {

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
   //创建签名SHA1
	public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
			//要采用URLENCODER的原始值！
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
		System.out.println("sha1 sb:" + params);
		return getSha1(params);
	}
	//Sha1签名
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("GBK"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	
	// 微信conf 加密
	public static String shaForConfSignature(String decript){
	    try {  
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.update(decript.getBytes());  
	        byte messageDigest[] = digest.digest();  
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        // 字节数组转换为 十六进制 数  
	            for (int i = 0; i < messageDigest.length; i++) {  
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
	                if (shaHex.length() < 2) {  
	                    hexString.append(0);  
	                }  
	                hexString.append(shaHex);  
	            }  
	            return hexString.toString();  
	   
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	}
}
