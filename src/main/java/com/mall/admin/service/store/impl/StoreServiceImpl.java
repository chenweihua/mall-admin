package com.mall.admin.service.store.impl;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.base._;
import com.mall.admin.service.store.StoreService;
import com.mall.admin.util.HttpService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.PropertyUtils;
@Service
public class StoreServiceImpl implements StoreService {
	@Autowired
	HttpService httpService;
	
	@Override
	public long getCollegeIdByStoreId(long storeId) {
		long collegeId = -1;
		if(storeId < 0){
			return -3L;
		}
		String param = "?id="+storeId;
		String cookie = PropertyUtils.getProperty("panshi.Cookie");
		String storeUrl = PropertyUtils.getProperty("panshi.store");
		Header[] headers = {new BasicHeader("Cookie", cookie)};
		String ret = httpService.sendRequest(storeUrl+param, "", headers);
		
		if(_.trimToNull(ret) == null){
			return -2L;
		}
		try {
			JsonNode node = JsonUtil.parse(ret);
			JsonNode code = node.get("code") != null ? node.get("code") : null;
			if(code == null || code.asInt() != 1){
				return -1L;
			}
			
			JsonNode data = node.get("data") != null ? node.get("data") : null;
			if(data == null){
				return -1L;
			}
			
			JsonNode collegeIdNode = data.get("college_id") != null ? data.get("college_id") : null;
			if(collegeIdNode == null){
				return -1L;
			}
			collegeId = collegeIdNode.asLong();
			
		} catch (IOException e) {
			e.printStackTrace();
			return -1L;
		}
		
		return collegeId;
	}

}
