package com.mall.admin.service.ump.uc;

import java.util.List;

import com.mall.admin.vo.ump.UcUser;

public interface UcUserService {
	
	
	/**
	 * 根据openId查询用户信息 
	 */
	public UcUser getUcUserByOpenId(String openId) throws Exception;
	
	
	/**
	 * 根据userId查询用户信息 
	 */
	public UcUser getUcUserByUserId(Long userId) throws Exception ;
	
	
	/**
	 * 根据多个userId查询多个用户信息 
	 */
	public List<UcUser> getUcUsersByUserIds(List<Long> userIds)throws Exception ;
	
	/**
	 * 根据多个手机号码查询多个用户信息
	 * @param phoneNos
	 * @return
	 */
	public List<UcUser> getUcUsersByPhoneNos(List<String> phoneNos) throws Exception;
	
	/**
	 * 根据多个areaId区域ID查询多个用户信息
	 * @param areaIds
	 * @return
	 */
	public List<UcUser> getUcUserByAreaIds(List<Long> areaIds) throws Exception ;
	
	

}
