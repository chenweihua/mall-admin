package com.mall.admin.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.util.StreamUtil;
import com.mall.admin.vo.user.User;

public class BaseController {

	public static final Gson gson = new Gson();
	private static final Logger LOGGER = LogConstant.mallLog;

	/**
	 * 获取请求参数
	 */
	public String getDataFromRequest(HttpServletRequest request) throws IOException {
		byte[] bytes = StreamUtil.consume(request.getInputStream());
		String data = new String(bytes, "UTF-8");
		return data;
	}

	/**
	 * json util
	 */

	public static Object buildErrJson(String msg) {
		return buildJson(1, msg, null);
	}

	public static Object buildSuccJson(Object data) {
		return buildJson(0, "success", data);
	}

	public static Object buildJson(int code, String msg) {
		return buildJson(code, msg, null);
	}

	public static Object buildJson(int code, String msg, Object data) {
		String time = DateUtil.timestampFormat(new Date());
		if (data == null) {
			return CommonUtil.asMap("code", code, "msg", msg, "serverTime", time, "serverInfo", "");
		} else {
			return CommonUtil.asMap("code", code, "msg", msg, "serverTime", time, "serverInfo", "", "data",
					data);
		}
	}

	/**
	 * js侧datatable需要的返回数据格式
	 * 
	 * @param draw
	 *                序列号
	 * @param recordsTotal
	 *                总数
	 * @param recordsFiltered
	 *                满足条件的总数
	 * @param data
	 *                数据
	 * @return
	 */
	public static Map<String, Object> buildDataTableResult(int draw, long recordsTotal, long recordsFiltered,
			List data) {
		return ImmutableMap.of("draw", draw, "recordsTotal", recordsTotal, "recordsFiltered", recordsFiltered,
				"data", data);
	}
	
	public static Map<String, Object> buildDataTableResult(int code,String msg,int draw, Object recordsTotal, Object recordsFiltered,
			Object data) {
		return CommonUtil.asMap("code",code,"msg",msg,"draw", draw, "recordsTotal", recordsTotal, "recordsFiltered", recordsFiltered,
				"data", data);
	}

	/**
	 * js侧datatable需要的返回数据格式
	 * 
	 * @param draw
	 *                序列号
	 * @param recordsTotal
	 *                总数
	 * @param recordsFiltered
	 *                满足条件的总数
	 * @param data
	 *                数据
	 * @return
	 */
	public static Map<String, Object> buildDataTableResult(int draw, long recordsTotal, long recordsFiltered,
			List data, int start) {
		return ImmutableMap.of("draw", draw, "recordsTotal", recordsTotal, "recordsFiltered", recordsFiltered,
				"data", data, "start", start);
	}

	/**
	 * 日志记录
	 */
	public void logInputData(HttpServletRequest request, String apiName, Object data)
			throws UnsupportedEncodingException {
		LOGGER.debug("[requestURL]{} [apiName]{} [request data] {}", request.getRequestURI(), apiName, data);
	}

	public void logOutputData(HttpServletRequest request, String apiName, Object data)
			throws UnsupportedEncodingException {
		LOGGER.debug("[requestURL]{} [apiName]{} [response data] {}", request.getRequestURI(), apiName, data);
	}

	public void gotoPage(HttpServletRequest request, HttpServletResponse response, String pageUrl)
			throws IOException {
		response.sendRedirect(getRequestURLBase(request, response) + pageUrl);
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

	public Long getNowLoginedUserId(HttpServletRequest request) {
		User loginInfo = (User) request.getAttribute("user");
		return loginInfo.getUser_id();
	}

	/**
	 * 错误提示信息view
	 */
	private static final String ERROR_VIEW_NAME = "errorMsg";

	/**
	 * 错误提示model_name
	 */
	private static final String ERROR_MODEL_NAME = "errorMsg";

	/**
	 * 默认的页面错误提示信息
	 */
	private static final String DEFAULT_ERROR_MSG = "您的操作出错了!";

	public ModelAndView createErrorView(String errorMsg) {
		return new ModelAndView(ERROR_VIEW_NAME, ERROR_MODEL_NAME, errorMsg);
	}

	public ModelAndView createErrorView() {
		return new ModelAndView(ERROR_VIEW_NAME, ERROR_MODEL_NAME, DEFAULT_ERROR_MSG);
	}

}
