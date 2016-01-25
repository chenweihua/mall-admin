package com.mall.admin.service.ump.uc;


import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 依赖的外部接口
 * @author liqiang
 *
 */

public interface DependExternalService {

	/**
	 * get接口
	 * @param url 地址
	 * @param Object 参数形式 key:参数key，value：参数值
	 * @return JsonNode
	 */
	JsonNode sendGetRequest(String url,Object... obj);
	
	/**
	 * post接口
	 * @param url 地址
	 * @param map 参数形式  json形式
	 * @return JsonNode
	 */
	JsonNode sendPostRequestJson(String url,Object... obj);
	
	
	/**
	 * post key=value形式
	 * @param url
	 * @param obj
	 * @return
	 */
	JsonNode sendPostRequestNameValue(String url, Object... obj);

	
}
