package com.mall.admin.service.ump.uc.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.google.common.collect.Lists;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.UcUrlConstant;
import com.mall.admin.service.ump.uc.DependExternalService;
import com.mall.admin.service.ump.uc.UcUserService;
import com.mall.admin.util.CollectionUtil;
import com.mall.admin.vo.ump.UcUser;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UcUserServiceImpl implements UcUserService {
	
	
	private final String DATE_FROMATTER = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	DependExternalService service;
	
	
	/**
	 * 根据openId查询用户信息 
	 */
	@Override
	public UcUser getUcUserByOpenId(String openId) throws Exception {
		checkArgument(StringUtils.isNotEmpty(openId), "openId不能为空");
		
		JsonNode json = service.sendGetRequest(UcUrlConstant.GET_USER_BY_OPENID, "openId", openId);
		JsonNode dataNode = parseRetJsonNodeSingle(json);
		UcUser ret = getUcUserByJson(dataNode);
		return ret;
		
	}
	
	
	
	/**
	 * 根据userId查询用户信息 
	 */
	@Override
	public UcUser getUcUserByUserId(Long userId) throws Exception {
		checkArgument(userId != null, "userId不能为空");
		
		JsonNode json = service.sendGetRequest(UcUrlConstant.GET_USER_BY_USERID, "userId", userId);
		JsonNode dataNode = parseRetJsonNodeSingle(json);
		return getUcUserByJson(dataNode);
		
	}
	
	
	/**
	 * 根据多个userId查询多个用户信息 
	 */
	@Override
	public List<UcUser> getUcUsersByUserIds(List<Long> userIds)throws Exception {
		checkArgument(userIds != null && userIds.size() > 0, "userIds不能为空");
		
		JsonNode json = service.sendGetRequest(UcUrlConstant.GET_USER_BY_USERIDS, "userIds", CollectionUtil.join(userIds, ","));
		if(json==null){
			return new ArrayList<UcUser>();
		}
		JsonNode dataNode = parseRetJsonNodeMuti(json);
		return getUcUsersByJson(dataNode);
		
	}
	
	
	/**
	 * 根据多个手机号码查询多个用户信息
	 * @param phoneNos
	 * @return
	 */
	@Override
	public List<UcUser> getUcUsersByPhoneNos(List<String> phoneNos) throws Exception {
		
		checkArgument(phoneNos != null && phoneNos.size() > 0, "phoneNos不能为空");
		
		JsonNode json = service.sendPostRequestNameValue(UcUrlConstant.GET_USER_BY_PHONENO, "phones", CollectionUtil.join(phoneNos, ","));
		JsonNode dataNode = parseRetJsonNodeMuti(json);
		return getUcUsersByJson(dataNode);
		
		
		/*
		List<UcUser> users = Lists.newArrayList();
		UcUser ucUser1 = new UcUser();
		ucUser1.setId(123);
		ucUser1.setMobilephone("12345678901");
		users.add(ucUser1);
		UcUser ucUser2 = new UcUser();
		ucUser2.setId(456);
		ucUser2.setMobilephone("19876543210");
		users.add(ucUser2);
		return users;
		*/
	}
	
	
	/**
	 * 根据多个areaId区域ID查询多个用户信息
	 * @param areaIds
	 * @return
	 */
	@Override
	public List<UcUser> getUcUserByAreaIds(List<Long> areaIds) throws Exception {
		checkArgument(areaIds != null && areaIds.size() > 0, "areaIds不能为空");
		
		
		JsonNode json = service.sendGetRequest(UcUrlConstant.GET_USER_BY_AREAID, "areaIds", CollectionUtil.join(areaIds, ","));
		JsonNode dataNode = parseRetJsonNodeMuti(json);
		return getUcUsersByJson(dataNode);
	}
	
	
	/**
	 * 解析UC返回报文外围
	 * 单个用户信息
	 * @param json
	 */
	private JsonNode parseRetJsonNodeSingle(JsonNode json) throws Exception {
		
		int code = json.get("code").asInt();
		if(code != 0) {
			String errorMsg = (json.get("msg") == null ? "" : json.get("msg").asText());
			throw new Exception(errorMsg);			
		} else {
			
			return json.get("data");
			
		}
		
	}
	
	
	/**
	 * 解析UC返回报文外围
	 * 多个用户信息，data节点下多了一个foo节点
	 * @param json
	 */
	private JsonNode parseRetJsonNodeMuti(JsonNode json) throws Exception {
		
		int code = json.get("code").asInt();
		if(code != 0) {
			String errorMsg = (json.get("msg") == null ? "" : json.get("msg").asText());
			throw new Exception(errorMsg);			
		} else {
			
			return json.get("data").get("foo");
			
		}
		
	}
	
	
	
	/**
	 * 解析返回的报文是单个用户信息
	 * @param json
	 * @return
	 */
	private UcUser assembleUcUserByJson(JsonNode json) {
		
		UcUser user = new UcUser();
		user.setId(isNullOrNullNode(json.get("id")) ? null : json.get("id").asInt());
		user.setMobilephone(isNullOrNullNode(json.get("mobilephone")) ? null : json.get("mobilephone").asText());
		user.setPassword(isNullOrNullNode(json.get("password")) ? null : json.get("password").asText());
		user.setToken(isNullOrNullNode(json.get("token")) ? null : json.get("token").asText());
		user.setCollegeid(isNullOrNullNode(json.get("collegeid")) ? null : json.get("collegeid").asInt());
		user.setRealname(isNullOrNullNode(json.get("realname")) ? null : json.get("realname").asText());
		user.setUsername(isNullOrNullNode(json.get("username")) ? null : json.get("username").asText());
		user.setEmail(isNullOrNullNode(json.get("email")) ? null : json.get("email").asText());
		user.setSex(isNullOrNullNode(json.get("sex")) ? null : Byte.valueOf(json.get("sex").asText()));
		user.setPhonevalidate(isNullOrNullNode(json.get("phonevalidate")) ? null : Byte.valueOf(json.get("phonevalidate").asText()));
		user.setEmailvalidate(isNullOrNullNode(json.get("emailvalidate")) ? null : Byte.valueOf(json.get("emailvalidate").asText()));
		user.setSource(isNullOrNullNode(json.get("source")) ? null : json.get("source").asInt());
		user.setIsdel(isNullOrNullNode(json.get("isdel")) ? null : Byte.valueOf(json.get("isdel").asText()));
		user.setHeadimgurl(isNullOrNullNode(json.get("headimgurl")) ? null : json.get("headimgurl").asText());
		user.setCityId(isNullOrNullNode(json.get("cityId")) ? null : json.get("cityId").asInt());
		user.setAreaId(isNullOrNullNode(json.get("areaId")) ? null : json.get("areaId").asInt());
		user.setOpenId(isNullOrNullNode(json.get("openId")) ? null : json.get("openId").asText());
		
		try {
			user.setCreatetime(isNullOrNullNode(json.get("createtime")) ? null : new SimpleDateFormat(DATE_FROMATTER).parse(json.get("createtime").asText()));
			user.setUpdatetime(isNullOrNullNode(json.get("updatetime")) ? null : new SimpleDateFormat(DATE_FROMATTER).parse(json.get("updatetime").asText()));
			user.setBirthday(isNullOrNullNode(json.get("birthday")) ? null : new SimpleDateFormat(DATE_FROMATTER).parse(json.get("birthday").asText()));
		} catch(Exception ex) {
			LogConstant.mallLog.error("", ex);
		}
		
		return user;
	}
	
	
	/**
	 * 判断一个jsonNode节点是否为空
	 * @param jsonNode
	 * @return
	 */
	private boolean isNullOrNullNode(JsonNode jsonNode) {
		return jsonNode == null || jsonNode == NullNode.instance;
	}
	
	
	/**
	 * 解析返回的报文是单个用户信息
	 * @param json
	 * @return
	 */
	private UcUser getUcUserByJson(JsonNode json) throws Exception {
		JsonNode userInfoNode = json.get("userInfo");
		if(userInfoNode == null) {
			throw new Exception("没有从UC查找到userInfo");
		}
		return assembleUcUserByJson(userInfoNode);
	}
	
	
	/**
	 * 解析返回的报文是多个用户信息
	 * @param json
	 * @return
	 */
	private List<UcUser> getUcUsersByJson(JsonNode json) {
		
		List<UcUser> users = new ArrayList<UcUser>();
		Iterator<JsonNode> it = json.elements();
		while(it.hasNext()) {
			JsonNode jsonNode = it.next();
			UcUser user = assembleUcUserByJson(jsonNode);
			users.add(user);
		}
		return users;		
	}
	
	public List<UcUser> getUcUsersByPhoneNosTest(List<String> phoneNos) throws Exception {
		
		checkArgument(phoneNos != null && phoneNos.size() > 0, "phoneNos不能为空");
		
		JsonNode json = new DependExternalServiceImpl().sendPostRequestNameValue(UcUrlConstant.GET_USER_BY_PHONENO, "phones", CollectionUtil.join(phoneNos, ","));
		JsonNode dataNode = parseRetJsonNodeMuti(json);
		return getUcUsersByJson(dataNode);
		
		
		/*
		List<UcUser> users = Lists.newArrayList();
		UcUser ucUser1 = new UcUser();
		ucUser1.setId(123);
		ucUser1.setMobilephone("12345678901");
		users.add(ucUser1);
		UcUser ucUser2 = new UcUser();
		ucUser2.setId(456);
		ucUser2.setMobilephone("19876543210");
		users.add(ucUser2);
		return users;
		*/
	}
	
	

}
