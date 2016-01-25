package com.mall.admin.service.outside.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.constant.IniBean;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.outside.ExpressBillMarkService;
import com.mall.admin.util.HttpService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.vo.express.ExpressBill;
@Service
public class ExpressBillMarkServiceImpl implements ExpressBillMarkService {
	@Autowired
	private HttpService httpService;
	@Override
	public Map<String, Object> getListByTime(Date date, int hour,int start,int numPerPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<ExpressBill> billList = new ArrayList<ExpressBill>();
		map.put("total", total);
		map.put("billList", billList);
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(IniBean.getIniValue("getExpressBillIp", "http://10.171.61.136:8868/"));
		urlBuilder.append(IniBean.getIniValue("getExpressBillList", "api/list/"));
		//天
		long diff = (System.currentTimeMillis() - date.getTime());
		if(diff < 0){
			return map;
		}
		long day = diff/(1000*60*60*24);
		urlBuilder.append(day);
		//分页
		urlBuilder.append("?pn="+start);
		urlBuilder.append("&rn="+numPerPage);
		//小时
		if(hour >= 0){
			urlBuilder.append("&hour="+(hour-1));
		}
		try {
			String ret = httpService.sendRequest(urlBuilder.toString());
			LogConstant.mallLog.info("[get express bill list]date:"+date+"|hour:"+(hour-1)+"|start:"+start+"|numPerPage:"+numPerPage+"|ret:"+ret+"|currentTime:"+new Date());
			JsonNode node = JsonUtil.parse(ret);
			JsonNode status = node.get("status");
			if(status == null || status.asInt(-1) != 0){
				return map;
			}
			total = node.get("total").asInt();
			JsonNode result = node.get("results");
			Iterator<JsonNode> iterator = result.iterator();
			while (iterator.hasNext()) {
				JsonNode bill = iterator.next();
				ExpressBill expressBill = new ExpressBill();
				expressBill.setRowkey(bill.get("rowkey").asText());
				expressBill.setMendianId(bill.get("mendianId").asText());
				expressBill.setKuaidiId(bill.get("kuaidiId").asText());
				expressBill.setKuaidiNo(bill.get("kuaidiNo").asText());
				expressBill.setPhoneNo(bill.get("phoneNo").asText());
				expressBill.setPicurl(bill.get("picurl").asText());
				long time = bill.get("time").asLong();
				expressBill.setTime(new Timestamp(time));
				billList.add(expressBill);
			}
			map.put("total", total);
			map.put("billList", billList);
		} catch (IOException e) {
			LogConstant.mallLog.error("[get express bill list error]e:"+e);
			map.put("total", total);
			map.put("billList", billList);
		}
		return map;
	}

	@Override
	public int markBill(ExpressBill expressBill) {
		int result = 1;//成功
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(IniBean.getIniValue("getExpressBillIp", "http://10.171.61.136:8868/"));
		urlBuilder.append(IniBean.getIniValue("getExpressBillMark", "api/mark/"));
		urlBuilder.append(expressBill.getRowkey());
		Map<String, Object> params = new HashMap<>();
		params.put("time", String.valueOf(expressBill.getTime().getTime()));
		params.put("phoneNo", expressBill.getPhoneNo());
		params.put("name", expressBill.getName());
		params.put("address", expressBill.getAddress());
		params.put("mark", expressBill.getMark());
		String content = JSONArray.toJSONString(params);
		String ret = httpService.sendPostRequest(urlBuilder.toString(), content, "UTF-8", 2000, 3000, "application/json; charset=UTF-8");
		LogConstant.mallLog.info("[express bill mark]content:"+content+"|ret:"+ret+"|currentTime:"+new Date());
		try {
			JsonNode node = JsonUtil.parse(ret);
			JsonNode status = node.get("status");
			if(status == null || status.asInt() != 0){
				result = -1;//失败
			}
		} catch (IOException e) {
			LogConstant.mallLog.error("[express bill mark]content:"+content+"|ret:"+ret+"|error:"+e+"|currentTime:"+new Date());
			result = -1;//失败
		}
		return result;
	}

}
