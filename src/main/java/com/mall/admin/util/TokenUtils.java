/**
 * @FileName: TokenUtil.java
 * @Package com.imxiaomai.api.util
 *
 * @author liangchengyu
 * @created 2014年5月14日 下午4:06:11
 *
 * Copyright 2013-2050 小麦公社 版权所有
 */
package com.mall.admin.util;

import java.io.UnsupportedEncodingException;

import com.mall.admin.constant.Constants;

public class TokenUtils {

	private static final String AUTHENTICATE_KEY = "EEE25CE9DAD833D8B7BB6463A5668133";

	public static String getToken(String userid, String password) throws UnsupportedEncodingException {
		byte[] bs = (userid + password + AUTHENTICATE_KEY).getBytes("UTF-8");
		return Md5Util.md5AsLowerHex(bs);
	}
	
	public static String getNonceStr(String userid, String password,long date) throws UnsupportedEncodingException {
		byte[] bs = (userid + password + date).getBytes("UTF-8");
		return Md5Util.md5AsLowerHex(bs);
	}
	
	public static String getCustomerSign(String str) throws UnsupportedEncodingException{
		System.out.println("计算签名字符串："+str+"&"+Constants.CUSTOMER_KEY);
		byte[] bs = (str+"&"+Constants.CUSTOMER_KEY).getBytes("UTF-8");
		return Md5Util.md5AsLowerHex(bs);
	}

}
