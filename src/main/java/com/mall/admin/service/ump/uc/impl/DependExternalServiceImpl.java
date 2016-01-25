/**
 * 
 */
package com.mall.admin.service.ump.uc.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.ump.uc.DependExternalService;
import com.mall.admin.util.HttpService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.impl.HttpServiceImpl;


/**
 * @author liqiang
 *
 */
@Service
public class DependExternalServiceImpl implements DependExternalService {

	private static final Logger logger = LogConstant.mallLog;
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	@Autowired
	HttpService httpService;

	/* (non-Javadoc)
	 * @see com.imxiaomai.mall.service.model.ump.DependExternalService#sendGetRequest(java.lang.String, java.lang.Object[])
	 */
	@Override
	public JsonNode sendGetRequest(String url, Object... obj) {
		if (obj.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + obj.length);
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < obj.length - 1; i += 2){
			param.append("?")
			.append(String.valueOf(obj[i]))
			.append("=")
			.append(obj[i + 1])
			.append("&");
		}
		JsonNode resultNode;
		String str = "";
		try {
			str =httpService.sendRequest(url+StringUtils.stripEnd(param.toString(),"&"));
			resultNode = JsonUtil.parse(str);
			return resultNode;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			logger.error("url:" + url + "，入参：" + param.toString() + "，出参：" + str );
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.imxiaomai.mall.service.model.ump.DependExternalService#sendPostRequest(java.lang.String, java.lang.Object[])
	 * post发送 以json字符串形式
	 */
	@Override
	public JsonNode sendPostRequestJson(String url, Object... obj) {
		if (obj.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + obj.length);
		StringBuffer param = new StringBuffer();
		param.append("{");
		for (int i = 0; i < obj.length - 1; i += 2){
			param.append(String.valueOf(obj[i]))
			.append(":")
			.append(obj[i + 1])
			.append(",");
		}
		String content =StringUtils.stripEnd(param.toString(),",");
		content=content+("}");
		JsonNode resultNode;
		logger.info("DependExternalServiceImpl url{} content{}", url,content);
		String str = "";
		try {
			str =httpService.sendPostRequest(url, content,DEFAULT_CHARSET);
			resultNode = JsonUtil.parse(str);
			return resultNode;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			logger.error("url:" + url + "，入参：" + content + "，出参：" + str );
		}
		return null;
	}
	
	
	/**
	 * post形式发送 key=value方式
	 * @param url
	 * @param obj
	 * @return
	 */
	public JsonNode sendPostRequestNameValue(String url, Object... obj) {
		if (obj.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + obj.length);
		StringBuffer param = new StringBuffer();
		//param.append("{");
		for (int i = 0; i < obj.length - 1; i += 2){
			param.append(String.valueOf(obj[i]))
			.append("=")
			.append(obj[i + 1])
			.append("&");
		}
		String content =StringUtils.stripEnd(param.toString(),"&");
		//content=content+("}");
		JsonNode resultNode;
		String str = "";
		try {
			str =httpService.sendPostRequest(url, content,DEFAULT_CHARSET);
			resultNode = JsonUtil.parse(str);
			return resultNode;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			logger.error("url:" + url + "，入参：" + content + "，出参：" + str);
		}
		return null;
	}
	
}
