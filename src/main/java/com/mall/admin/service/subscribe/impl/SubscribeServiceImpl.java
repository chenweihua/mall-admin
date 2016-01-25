package com.mall.admin.service.subscribe.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.subsribe.SubscribeDetailDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.subscribe.ISubscribeService;
import com.mall.admin.vo.subscribe.SubscribeDetail;

@Service
public class SubscribeServiceImpl implements ISubscribeService {
	
	@Autowired
	private SubscribeDetailDao subscribeDetailDao;
	
	/**
	 * 添加订阅广告、活动和信息
	 * 
	 * @param subscribeDetail
	 * @return
	 */
	@Override
	public int insertSubscribeDetail(SubscribeDetail subscribeDetail){
		int affectNum = 0;
		
		if(subscribeDetail == null) {
			LogConstant.mallLog.info("insertSubscribeDetail.subscribeDetail is null");
			return affectNum;
		}
		LogConstant.mallLog.info("insertSubscribeDetail.subscribeDetail:", subscribeDetail.toString());
		try {
			affectNum = subscribeDetailDao.insertSelective(subscribeDetail);
			
		} catch (Exception e) {
			LogConstant.mallLog.error("SubscribeServiceImpl.insertAdvertise:", e);
		}

		return affectNum;
	}
	
	/**
	 * 通过主键  查询订阅广告、活动和信息
	 * 
	 * @param subscribeId
	 * @return
	 */
	@Override
	public SubscribeDetail selectSubscribeDetailById(Long subscribeId){
		LogConstant.mallLog.info("selectSubscribeDetail.subscribeId:", subscribeId);
		
		SubscribeDetail subscribeDetail = subscribeDetailDao.selectByPrimaryKey(subscribeId);
		return subscribeDetail;
	}

	/**
	 * 编辑订阅广告、活动和信息
	 * @param subscribeDetail
	 * @return
	 */
	@Override
	public int updateSubscribeDetailById(SubscribeDetail subscribeDetail) {
		int affectNum = 0;
		
		if(subscribeDetail == null) {
			LogConstant.mallLog.info("updateSubscribeDetail.subscribeDetail is null");
			return affectNum;
		}
		LogConstant.mallLog.info("updateSubscribeDetail.subscribeDetail:", subscribeDetail.toString());
		affectNum = subscribeDetailDao.updateByPrimaryKeySelective(subscribeDetail);
			
		return affectNum;
	}
	
	
	/**
	 * 分页查询couponGive
	 * @param paginationInfo
	 * @return
	 */
	public List<SubscribeDetail> getSubscribeDetailList(Map<String,Object> param,PaginationInfo paginationInfo) {
		List<SubscribeDetail> subscribeDetailList = subscribeDetailDao.getList(param, paginationInfo);
		return subscribeDetailList;
	}


}
