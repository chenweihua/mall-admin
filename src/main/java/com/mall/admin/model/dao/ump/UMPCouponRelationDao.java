package com.mall.admin.model.dao.ump;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.ump.CouponBatchRelation;


public interface UMPCouponRelationDao {

	/**
	 * @param couponBatchRelation
	 * @return
	 */
	public int insert(CouponBatchRelation couponBatchRelation);
	
	
	public List<CouponBatchRelation> queryCouponBatchRelations(Long couponBatchId);

}