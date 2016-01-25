package com.mall.admin.service.delivery.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.delivery.DeliverInfoService;
import com.mall.admin.service.ump.uc.DependExternalService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.vo.delivery.DeliverDetailInfo;

/**
 * 快递100 官网：http://www.kuaidi100.com/help/qa.shtml
 * 查询配送信息
 * 接口说明：http://www.kuaidi100.com/openapi/api_post.shtml
 */

public class Kuaidi100DeliverServiceImpl implements DeliverInfoService {
	
	private static final Logger logger = LogConstant.mallLog;
	
	/**
	 * 调用URL
	 */
	private final String URL = "http://api.kuaidi100.com/api";

	/**
	 * 身份授权KEY
	 * TODO Kong 等待快递100的KEY
	 */
	private final String KEY = "";
	
	
	@Autowired
	DependExternalService service;
	
	@Override
	public List<DeliverDetailInfo> queryDeliverInfo(String deliverCompanyCode, String deliverSheetCode) {
		
		List<DeliverDetailInfo> deliverInfos = Lists.newArrayList();
		
		try {
		
			/*
			StringBuffer param = new StringBuffer();
			param.append("id=" + KEY) 
				 .append("&")
				 .append("com=" + deliverCompanyCode) //物流公司编码
				 .append("&")
				 .append("nu=" + deliverSheetCode)  //物流单号
				 .append("&")
				 .append("show=0") //返回格式json
				 .append("&")
				 .append("muti=1")  //返回多行完整信息
				 .append("&")
				 .append("order=desc")  //按时间倒叙排列
			;
			*/
			
			/*
			JsonNode jsonNode = service.sendGetRequest(URL, 
					"id", KEY , 				//身份授权key
					"com" , deliverCompanyCode, //物流公司编码
					"nu" , deliverSheetCode,	//物流单号
					"show", "0",	//返回格式json
					"muti", "1",	//返回多行完整信息
					"order", "desc"	//按时间倒叙排列
			);
			*/
			
			//logger.info("kuaidi100 deliverCompanyCode{} deliverSheetCode{} result{}", deliverCompanyCode, deliverSheetCode, JsonUtil.format(jsonNode));
			
			//Pair<Integer,String> result = crawler.get(URL + "?" + param.toString());
			
			
			//if(result != null && result.getLeft() == 200) {
			
			
				//String deliverInfo = result.getRight();
			
			String deliverInfo = "{\"message\":\"ok\",\"status\":\"1\",\"state\":\"3\",\"data\":" + 
		            "[{\"time\":\"2012-07-07 13:35:14\",\"context\":\"客户已签收\"}, " + 
		             "{\"time\":\"2012-07-07 09:10:10\",\"context\":\"离开 [北京石景山营业厅] 派送中，递送员[温]，电话[]\"}," +
		             "{\"time\":\"2012-07-06 19:46:38\",\"context\":\"到达 [北京石景山营业厅]\"}," +
		             "{\"time\":\"2012-07-06 15:22:32\",\"context\":\"离开 [北京石景山营业厅] 派送中，递送员[温]，电话[]\"}," +
		             "{\"time\":\"2012-07-06 15:05:00\",\"context\":\"到达 [北京石景山营业厅]\"}," +
		             "{\"time\":\"2012-07-06 13:37:52\",\"context\":\"离开 [北京_同城中转站] 发往 [北京石景山营业厅]\"}," +
		             "{\"time\":\"2012-07-06 12:54:41\",\"context\":\"到达 [北京_同城中转站]\"}," +
		             "{\"time\":\"2012-07-06 11:11:03\",\"context\":\"离开 [北京运转中心驻站班组] 发往 [北京_同城中转站]\"}," +
		             "{\"time\":\"2012-07-06 10:43:21\",\"context\":\"到达 [北京运转中心驻站班组]\"}," +
		             "{\"time\":\"2012-07-05 21:18:53\",\"context\":\"离开 [福建_厦门支公司] 发往 [北京运转中心_航空]\"}," +
		             "{\"time\":\"2012-07-05 20:07:27\",\"context\":\"已取件，到达 [福建_厦门支公司]\"}" +
		            "]} ";
				
				//解析返回JSON报文
				JsonNode rootNode = JsonUtil.parse(deliverInfo);
				String status = rootNode.get("status").asText();
				if("1".equals(status)) {  //查询成功
					JsonNode dataNode = rootNode.withArray("data");
					Iterator<JsonNode> jsonNodes = dataNode.iterator();
					while(jsonNodes.hasNext()) {
						JsonNode jsonNode = jsonNodes.next();
						deliverInfos.add(new DeliverDetailInfo(jsonNode.get("time").asText(),jsonNode.get("context").asText()));
					}
				}
			//}
		} catch(Exception ex) {
			logger.error("快递kuaidi100查询接口异常，deliverCompanyCode=" + deliverCompanyCode + ",deliverSheetCode=" + deliverSheetCode,ex);
		}
		return deliverInfos;
		
	}
	
	/*
	public static void main(String[] args) throws Exception {
		
		String mockResult = "{\"message\":\"ok\",\"status\":\"1\",\"state\":\"3\",\"data\":" + 
	            "[{\"time\":\"2012-07-07 13:35:14\",\"context\":\"客户已签收\"}, " + 
	             "{\"time\":\"2012-07-07 09:10:10\",\"context\":\"离开 [北京石景山营业厅] 派送中，递送员[温]，电话[]\"}," +
	             "{\"time\":\"2012-07-06 19:46:38\",\"context\":\"到达 [北京石景山营业厅]\"}," +
	             "{\"time\":\"2012-07-06 15:22:32\",\"context\":\"离开 [北京石景山营业厅] 派送中，递送员[温]，电话[]\"}," +
	             "{\"time\":\"2012-07-06 15:05:00\",\"context\":\"到达 [北京石景山营业厅]\"}," +
	             "{\"time\":\"2012-07-06 13:37:52\",\"context\":\"离开 [北京_同城中转站] 发往 [北京石景山营业厅]\"}," +
	             "{\"time\":\"2012-07-06 12:54:41\",\"context\":\"到达 [北京_同城中转站]\"}," +
	             "{\"time\":\"2012-07-06 11:11:03\",\"context\":\"离开 [北京运转中心驻站班组] 发往 [北京_同城中转站]\"}," +
	             "{\"time\":\"2012-07-06 10:43:21\",\"context\":\"到达 [北京运转中心驻站班组]\"}," +
	             "{\"time\":\"2012-07-05 21:18:53\",\"context\":\"离开 [福建_厦门支公司] 发往 [北京运转中心_航空]\"}," +
	             "{\"time\":\"2012-07-05 20:07:27\",\"context\":\"已取件，到达 [福建_厦门支公司]\"}" +
	            "]} ";
		//解析返回JSON报文
		JsonNode rootNode = JsonUtil.parse(mockResult);
		String status = rootNode.get("status").asText();
		if("1".equals(status)) {  //查询成功
			JsonNode dataNode = rootNode.withArray("data");
			Iterator<JsonNode> jsonNodes = dataNode.iterator();
			while(jsonNodes.hasNext()) {
				JsonNode jsonNode = jsonNodes.next();
				System.out.println(jsonNode.get("time").asText() + "	"  + jsonNode.get("context").asText());
			}
		}
		
	}
	*/

	public boolean registerDeliverInfo(String deliverCompanyCode, String deliverSheetCode) {
		throw new RuntimeException("不支持该操作");
	}
	
	public static void main(String[] args) {
		System.out.println("shenganwuliu,123321123".hashCode());
		System.out.println(1081567402 % 1024);
	}
}
