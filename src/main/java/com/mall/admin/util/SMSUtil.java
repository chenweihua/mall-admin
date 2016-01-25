package com.mall.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.util.impl.HttpServiceImpl;

public class SMSUtil {


	private static final Logger logger = LogConstant.mallLog;

	/**
	 * 发送短信服务的URL
	 */
	public static final String SMS_URL = "http://sms.imxiaomai.com/sendSms";
	/**
	 * 发送短信服务的用户名（网站）
	 */
	public static final String SMS_USER_NAME = "wx_platform";
	/**
	 * 发送短信服务的密码（网站）
	 */
	public static final String SMS_PASSWORD = "wx_2013";

	/**
	 * 发送短信
	 * @author sqj
	 * @created 2014-3-10 下午2:24:38
	 *
	 * @param phoneNums 手机号码，多个用英文逗号隔开
	 * @return
	 */

	public static boolean sendSMSText(String phoneNums, String template){
		return send(SMS_USER_NAME, SMS_PASSWORD, phoneNums, template);
	}

	/**
	 * 通过Apache HttpComponents发送请求到短信服务器，发送短信
	 *
	 * @param smsUsername 短信服务用户名
	 *
	 * @param smsPassword 短信服务密码
	 *
	 * @param phoneNums 手机号码，多个用英文逗号隔开
	 *
	 * @param text 短信文本
	 */
	private static boolean send(String smsUsername, String smsPassword, String phoneNums, String text) {
		if (!StringUtils.hasText(phoneNums) || !StringUtils.hasText(text)) {
			return false;
		}

		try {
			// 处理请求参数
			String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String str = smsUsername + smsPassword + nowDateStr;
			String token = DigestUtils.md5DigestAsHex(str.getBytes()).toLowerCase();

			HttpService service = new HttpServiceImpl();
			
			StringBuffer sb = new StringBuffer();
			sb.append("name=" + smsUsername)
				.append("&")
				.append("token=" + token)
				.append("&")
				.append("phones=" + phoneNums)
				.append("&")
				.append("content=" + text)
				.append("&")
				.append("sendtime=" + nowDateStr)
				.append("&")
				.append("sendtype=2")
				;
			String result = service.sendPostRequest(SMS_URL, sb.toString(), "UTF-8");
			logger.error("phoneNums:" + phoneNums + ",text:" + text + ",result:" + result);
			return true;
		}catch (Exception ex){
			logger.error("",ex);
			return false;
		}

	}


}
