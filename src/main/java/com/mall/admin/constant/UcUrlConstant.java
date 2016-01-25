/**
 * 
 */
package com.mall.admin.constant;

import com.mall.admin.util.DictionaryUtil;

/**
 * 调用外部接口的url配置
 * TODO 以后可用配置线上线下访问
 * @author liqiang
 *
 */
public class UcUrlConstant {
	/**
	 * uc域名
	 */
	//http://100.98.25.14:8090  测试环境地址
	//春哥本机测试地址 http://172.16.102.109:8080
	private static final String UC_PREFIX = DictionaryUtil.getValueByTypeIdAndKey(4L, "UC_URL_PREX");
	//修改手机号
	public static String UC_UPDATE_PHONE=UC_PREFIX+"/user/updateBindPhone";
	//验证多个手机号
	public static String UC_VALIDATE_PHONES=UC_PREFIX+"/user/validateMutiPhone";
	
	
	 //通过OpenId查询用户 
	public static final String GET_USER_BY_OPENID = UC_PREFIX + "/user/getExtraUserByOpenId"; //  openId
	 
	//通过UserId查询用户 
	public static final String GET_USER_BY_USERID = UC_PREFIX + "/user/getExtraUserByUserId"; //	userId
	
	//根据多个userId,查询用户 
	public static final String GET_USER_BY_USERIDS = UC_PREFIX + "/user/getUserByMutiUserId";	//userIds 数量？
	
	// 根据多个手机号查询用户 
	public static final String GET_USER_BY_PHONENO = UC_PREFIX + "/user/getUsersByMutiPhone";	//phones  最多5万 post请求
	 
	//根据多个区域ID查询用户	
	public static final String GET_USER_BY_AREAID = UC_PREFIX + "/user/getUserByMutiAreaId";  //	areaIds
	
}
