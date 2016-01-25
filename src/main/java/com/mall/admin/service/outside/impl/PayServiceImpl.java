package com.mall.admin.service.outside.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mall.admin.constant.IniBean;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.outside.PayService;
import com.mall.admin.service.outside.dto.PayItem;
import com.mall.admin.util.HttpService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.Md5Util;
@Service
public class PayServiceImpl implements PayService {
	private static final int CONNECT_TIME = 3000;
	private static final int READ_TIME = 3000;
	private static final String CHAR_SET = "UTF-8";
	private static final int MAX_ORDER_NUM = 100;
	private static final String SUCCESS = "0";
	
	@Autowired
	private HttpService httpService;
	/**
	 *  {source: store, reqTime: 11111111, nonceStr: 1111111, data:[{outId:1111, type:0, appType:1},{outId:111, type:1,           appType:1}]}
    source, reqTime, nonceStr和之前保持一致，
   appType为来源：0为支付宝，1为微信，
   outId为mall订单id，type：0为h5, 1为app

   2.返回数据不变

   3.接口已部署test环境，地址为http://pay.test.imxiaomai.com/order_info/out_ids
	 */
	@Override
	public Map<Long, PayItem> getPayId(Map<Long, PayItem> params) {
		if(params == null || params.size() == 0){
			return null;
		}
		String url = IniBean.getIniValue("getPayIdUrl", "http://pay.ms.imxiaomai.com:8080/order_info/out_ids");
		JsonObject object = new JsonObject();
		//验证信息
		long date = System.currentTimeMillis();
		String userName = IniBean.getIniValue("PaySource", "store");
		String token = IniBean.getIniValue("PaySourceToken", "token");
		object.addProperty("source", userName);
		object.addProperty("reqTime", date);
		object.addProperty("nonceStr", getNonceStr(userName, token, date));
		//数据
		JsonArray data = new JsonArray();
		for(Long orderId : params.keySet()){
			PayItem item = params.get(orderId);
			if(item != null){
				JsonObject param = new JsonObject();
				param.addProperty("outId", item.getOrderId());
				param.addProperty("type", item.getType());
				param.addProperty("appType", item.getAppType());
				data.add(param);
			}
		}
		object.add("data", data);
		String ret = httpService.sendPostRequest(url, object.toString(), CHAR_SET, CONNECT_TIME, READ_TIME);
		LogConstant.mallLog.info("[request payId]request:"+object.toString()+"|response:"+ret);
		try {
			JsonNode node = JsonUtil.parse(ret);
			JsonNode code = node == null ? null : node.get("code");
			if(code != null && SUCCESS.equals(code.asText())){
				JsonNode dataNode = node == null ? null : node.get("data");
				return parseData(dataNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private  Map<Long, PayItem> parseData(JsonNode data){
		if(data == null){
			return null;
		}
		Map<Long, PayItem> map = new HashMap<Long, PayItem>();
		Iterator<JsonNode> iterator = data.elements();
		while(iterator.hasNext()){
			JsonNode node = iterator.next();
			if(node != null){
				Long orderId = node.get("outId") == null ? -1L : node.get("outId").asLong(-1L);
				String payId = node.get("payId") == null ? "" : node.get("payId").asText();
				Long fee = node.get("fee") == null ? -1L : node.get("fee").asLong(-1L);
				if(orderId != -1L && !map.containsKey(orderId)){
					map.put(orderId, new PayItem(orderId,-1,-1,payId,fee));
				}
			}
		}
		return map;
	}
	
	private String getNonceStr(String userName,String token,long date) {
		try {
			byte[] bs = (userName + token + date).getBytes(CHAR_SET);
			return Md5Util.md5AsLowerHex(bs);
		} catch (UnsupportedEncodingException e) {
			LogConstant.mallLog.info("[request payId generate nonceStr failure]msg:"+e);
		}
		return null;
	}
}


