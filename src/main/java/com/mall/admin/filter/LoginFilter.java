package com.mall.admin.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.constant.Constants;
import com.mall.admin.constant.DictionaryConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.UrlConstants;
import com.mall.admin.service.user.UserService;
import com.mall.admin.util.BaseUtil;
import com.mall.admin.util.DictionaryUtil;
import com.mall.admin.vo.user.Menu;
import com.mall.admin.vo.user.User;

public class LoginFilter implements Filter {

	private Logger log = LogConstant.mallLog;

	private static final String LOG_NAME = "userAction";
	private static final String ELEMENT_SPLIT_CHAR = ":";
	private static final String PARAM_SPLIT_CHAR = "|";

	ExecutorService service = Executors.newFixedThreadPool(10);

	@Autowired
	private UserService userService;

	private String errorurl = "/page/error.jsp";

	/** 不检查的路径 */
	private String excludeURL = "*/admin/*,*/js/*,*/css/*,*/fonts/*,*/img/*,*/bower_componentss/*,*/postAllStorage,*/getByCollegeIdAndExpressCode,*/selleradmin/customer/*,*/tool/image/upload";
	private String excludeInLoginURL = "*/wms/goods/import,*/storage/goodsstock/importFile";

	private String byPassServletPath = "/delivery/query";
	
	private String systemName;

	static final Pattern postfixPattern = regex("\\.(\\w+)$");
	static final Set<String> staticSuffixes = new HashSet<>(Arrays.asList(
	//
			"ico", "css", "js", "gif", "png", "jpg", "jpeg", "swf", "txt", "woff", "ttf"));

	/**
	 * 禁止再filter中获取request的参数。否则对退款有影响。
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestURL = request.getRequestURI();
		String url = request.getServletPath();
		
		String[] byPassServletPathArr = byPassServletPath.split(";");
		if(ArrayUtils.contains(byPassServletPathArr, url)) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean isStaticFile = isStaticFile(requestURL);
		if ("\\".equals(requestURL.trim())) {
			chain.doFilter(request, response);
			return;
		}
		if ("/".equals(requestURL.trim())) {
			chain.doFilter(request, response);
			return;
		}
		if (isStaticFile) {// 静态资源放行
			chain.doFilter(request, response);
			return;
		} else if (isNotCheck(requestURL)) {
			chain.doFilter(request, response);
			return;
		}

		// 对接商家
		Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
		String token = null;
		Long userId = null;

		if (cookies == null || cookies.length == 0) {
			gotoPage(request, response, UrlConstants.LOGIN);
			return;
		}
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if (cookieName.equalsIgnoreCase(Constants.COOKIE_USER_TOKEN) && cookieValue != null
					&& !cookieValue.equals("")) {
				token = cookieValue;
			}
			if (cookieName.equalsIgnoreCase(Constants.COOKIE_USER_ID) && cookieValue != null
					&& !cookieValue.equals("")) {
				userId = new Long(cookieValue);
			}
		}

		if (!userService.checkUser(userId, token)) {
			gotoPage(request, response, UrlConstants.LOGIN);
			return;
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			gotoPage(request, response, UrlConstants.LOGIN);
			return;
		}
		// 用户被删除了，禁止再做其它操作。
		if (user.is_del == 1) {
			gotoPage(request, response, UrlConstants.LOGIN);
			return;
		}
		
		
		List<Menu> userMenuList = user.getMenuList();
		// 是否需要检查该url
		boolean hasPermission = userService.checkPermission(url, userMenuList);
		if (!hasPermission) {
			LogConstant.mallLog.info("用{}({})户没有访问{}的权限~", user.getUser_id(), user.getUser_name(), url);
			gotoPage(request, response, UrlConstants.LOGIN);
			return;
		}
		// service.execute(new LogUserAction(user, request, url));
		if (!isNotCheckInLoginUrl(url)) {
			recordUserActionLog(user, request, url);
		}
		
		boolean thirdSellerPermission = validateThirdSellerUser(user,request.getServletPath());
		if(!thirdSellerPermission) {
			LogConstant.mallLog.info("用{}({})户为第三方卖家用户，没有访问{}的权限~", user.getUser_id(), user.getUser_name(), request.getServletPath());
			return;
		}

		request.setAttribute(Constants.MALLADMIN_USER, user);
		chain.doFilter(request, response);

	}
	
	/**
	 * 如果该用户为第三方卖家用户，校验是否为指定的url
	 * 校验通过true，校验不通过false
	 */
	private boolean validateThirdSellerUser(User user,String uri) {
		if(user.getUser_type() != User.USER_TYPE_THIRD_SELLER)  {
			return true;
		}
		
		List<String> allowUris = DictionaryUtil.getValuesByTypeId(DictionaryConstant.THIRD_SELLER_USER_TYPE_ID);
		//LogConstant.mallLog.info("allowUris" + allowUris);
		//LogConstant.mallLog.info("uri" + uri);
		return allowUris.contains(uri);
	}

	public void gotoPage(HttpServletRequest request, HttpServletResponse response, String pageUrl)
			throws IOException {

		String url = UrlConstants.getUrl(request, pageUrl);

		if (!url.startsWith("http://")) {
			url = getRequestURLBase(request, response) + url;
		}

		response.sendRedirect(url);
	}

	/**
	 * 获得url的基础路径
	 * 
	 * 比如:http://www.imxiaomai.com/abc/ddd 会得到http://www.imxiaomai.com
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getRequestURLBase(HttpServletRequest request, HttpServletResponse response) {
		String port = "";
		if (request.getServerPort() != 80) {
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// errorurl = config.getInitParameter("errorurl");
		// no_check_url = config.getInitParameter("no_check_url");
		// excludeURL = config.getInitParameter("excludeURL");
		// systemName = config.getInitParameter("systemName");

	}

	@Override
	public void destroy() {
	}

	private boolean isNotCheck(String url) {
		if (excludeURL == null || excludeURL.trim().length() < 1) {
			return false;
		}
		String[] notCheckUrls = excludeURL.split(",");
		for (String notCheckUrl : notCheckUrls) {
			if (BaseUtil.simpleMatch(notCheckUrl, url)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNotCheckInLoginUrl(String url) {

		String[] notCheckUrls = excludeInLoginURL.split(",");
		for (String notCheckUrl : notCheckUrls) {
			if (BaseUtil.simpleMatch(notCheckUrl, url)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isStaticFile(String uri) {
		Matcher m = postfixPattern.matcher(uri);
		if (!m.find())
			return false;
		String group = m.group(1).toLowerCase();
		return staticSuffixes.contains(group);
	}

	public static Pattern regex(String regex) {
		return Pattern.compile(regex);
	}

	public void recordUserActionLog(User user, ServletRequest request, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("【url】").append(ELEMENT_SPLIT_CHAR).append(url).append(PARAM_SPLIT_CHAR);
		sb.append("【userId】").append(ELEMENT_SPLIT_CHAR).append(user.getUser_id()).append(PARAM_SPLIT_CHAR);
		sb.append("【userName】").append(ELEMENT_SPLIT_CHAR).append(user.getUser_name()).append(PARAM_SPLIT_CHAR);
		sb.append("【parameter】").append(ELEMENT_SPLIT_CHAR).append("{");
		Enumeration e = request.getParameterNames();
		int count = 0;// 计数器，信息用|分隔，参数用&分隔
		while (e.hasMoreElements()) {
			String paramName = (String) e.nextElement();
			String paramValue = request.getParameter(paramName) == null ? null : StringUtils
					.trimToEmpty(request.getParameter(paramName));
			if (StringUtils.isNotBlank(paramName)) {
				if (count > 0) {
					sb.append(PARAM_SPLIT_CHAR);
				}
				sb.append(paramName).append(ELEMENT_SPLIT_CHAR).append(paramValue);
				count++;
			}
		}
		sb.append("}");
		log.info("【user action log】:{" + sb.toString() + "}");
	}

	private class LogUserAction extends Thread {

		private Logger log = LogConstant.mallLog;

		private static final String LOG_NAME = "userAction";
		private static final String ELEMENT_SPLIT_CHAR = ":";
		private static final String PARAM_SPLIT_CHAR = "|";

		private User user;
		private ServletRequest request;
		private String url;

		public LogUserAction(User user, ServletRequest request, String url)
		{
			this.user = user;
			this.request = request;
			this.url = url;
		}

		@Override
		public void run() {
			StringBuffer sb = new StringBuffer();
			sb.append("【url】").append(ELEMENT_SPLIT_CHAR).append(url).append(PARAM_SPLIT_CHAR);
			sb.append("【userId】").append(ELEMENT_SPLIT_CHAR).append(user.getUser_id())
					.append(PARAM_SPLIT_CHAR);
			sb.append("【userName】").append(ELEMENT_SPLIT_CHAR).append(user.getUser_name())
					.append(PARAM_SPLIT_CHAR);
			sb.append("【parameter】").append(ELEMENT_SPLIT_CHAR).append("{");
			Enumeration e = request.getParameterNames();
			int count = 0;// 计数器，信息用|分隔，参数用&分隔
			while (e.hasMoreElements()) {
				String paramName = (String) e.nextElement();
				String paramValue = request.getParameter(paramName) == null ? null : StringUtils
						.trimToEmpty(request.getParameter(paramName));
				if (StringUtils.isNotBlank(paramName)) {
					if (count > 0) {
						sb.append(PARAM_SPLIT_CHAR);
					}
					sb.append(paramName).append(ELEMENT_SPLIT_CHAR).append(paramValue);
					count++;
				}
			}
			sb.append("}");
			log.info("【user action log】:{" + sb.toString() + "}");
		}
	}
}
