package com.mall.admin.service.base;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;

public class BaseServiceImpl {
	protected static final Logger logger = LogConstant.mallLog;

	public static Map<String, Object> buildErrObj(String msg) {
		return buildObj(1, msg, null);
	}

	public static Map<String, Object> buildSuccObj(Object data) {
		return buildObj(0, "success", data);
	}

	public static Map<String, Object> buildObj(int code, String msg) {
		return buildObj(code, msg, null);
	}

	public static Map<String, Object> buildObj(int code, String msg, Object data) {

		String time = DateUtil.timestampFormat(new Date());
		if (data == null) {
			return CommonUtil.asMap("code", code, "msg", msg, "serverTime",
					time, "serverInfo", "");
		} else {
			return CommonUtil.asMap("code", code, "msg", msg, "serverTime",
					time, "serverInfo", "", "data", data);
		}
	}
}
