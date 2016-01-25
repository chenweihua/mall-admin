package com.mall.admin.constant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UrlConstants {
	public static final String LOGIN = "/admin/login";
	public static final String INDEX = "/user/index";

	public static final String HOME = "/user/index";

	public static final String LOGOUT = "/admin/logout";

	public static final String REG = "reg";

	public static final String NO_PRIVILEGE = "no_privilege";

	public static final Map<String, String> urlMap = new HashMap<String, String>();

	static {

		urlMap.put(HOME, "/admin/index");
		urlMap.put(LOGIN, "/admin/login");
		urlMap.put(LOGOUT, "/admin/logout.do");
		urlMap.put(NO_PRIVILEGE, "/no_privilege");
		urlMap.put(REG, "/admin/reg");
	}

	public static String getUrl(HttpServletRequest request, String type) {

		String url = urlMap.get(type);
		if (url != null) {
			return url;
		} else {
			return type;
		}

	}
}
