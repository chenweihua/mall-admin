package com.mall.admin.service.subscribe;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.subscribe.SubscribeDetail;

public interface ISubscribeService {
	
	/**
	 * 添加订阅广告、活动和信息
	 * @param subscribeDetail
	 * @return
	 */
	public int insertSubscribeDetail(SubscribeDetail subscribeDetail);
	
	/**
	 * 通过主键  查询订阅广告、活动和信息
	 * 
	 * @param subscribeId
	 * @return
	 */
	public SubscribeDetail selectSubscribeDetailById(Long subscribeId);
	
	/**
	 * 编辑订阅广告、活动和信息
	 * @param subscribeDetail
	 * @return
	 */
	public int updateSubscribeDetailById(SubscribeDetail subscribeDetail);
	
	
	/**
	 * 分页查询couponGive
	 * @param paginationInfo
	 * @return
	 */
	public List<SubscribeDetail> getSubscribeDetailList(Map<String,Object> param,PaginationInfo paginationInfo);

}
