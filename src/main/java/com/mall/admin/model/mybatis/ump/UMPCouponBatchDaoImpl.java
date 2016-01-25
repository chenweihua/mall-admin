package com.mall.admin.model.mybatis.ump;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponBatchDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.ump.Coupon;
import com.mall.admin.vo.ump.CouponBatch;

@Repository
public class UMPCouponBatchDaoImpl extends BaseMallDaoImpl implements UMPCouponBatchDao {

	public int deleteByPrimaryKey(Long batchId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponBatch.deleteByPrimaryKey", batchId);
	}

	public int insert(CouponBatch record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("CouponBatch.insert",record);
	}

	public int insertSelective(CouponBatch record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("CouponBatch.insertSelective",record);
	}

	public CouponBatch selectByPrimaryKey(Long batchId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponBatch.selectByPrimaryKey", batchId);
	}

	public int updateByPrimaryKeySelective(CouponBatch record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponBatch.updateByPrimaryKeySelective",record);
	}

	public int updateByPrimaryKey(CouponBatch record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponBatch.updateByPrimaryKey",record);
	}
	
	public int updateCouponBatchStatus(CouponBatch couponBatch) {
		 return this.getSqlSession().update("CouponBatch.updateCouponBatchStatus",couponBatch);
	 }
	
	public int lockCouponBatchByActiveSend(Long couponBatchId) {
		return this.getSqlSession().update("CouponBatch.lockCouponBatchByActiveSend",couponBatchId);
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponBatchDao#getCouponBatchList(java.util.Map)
	 */
	@Override
	public List<CouponBatch> getCouponBatchList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("CouponBatch.getCouponBatchList",map);
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponBatchDao#getCouponBatchAndDetailList(java.util.Map)
	 */
	@Override
	public List<CouponBatch> getCouponBatchAndRelationList(Map<String, Object> map) {
		return this.getSqlSession().selectOne("CouponBatch.getCouponBatchAndRelationList",map);
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponBatchDao#getCountCouponBatchList(java.util.Map)
	 */
	@Override
	public Integer getCountCouponBatchList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponBatch.getCountCouponBatchList",map);
	}


	
	public int minusOneCouponBatch(CouponBatch couponBatch) {
		
		int affectRows = this.getSqlSession().update("CouponBatch.minusOneCouponBatch",couponBatch);
//		this.getSqlSession().update("CouponBatch.minusOneCouponBatchValidStatus",couponBatchId);
		return affectRows;
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponBatchDao#endCouponBatch(java.util.Map)
	 */
	@Override
	public Integer endCouponBatch(Map<String, Object> map) {
		return this.getSqlSession().update("CouponBatch.endCouponBatch",map);
	}
	
	public void clearCouponBatchActivityId(Long activityId) {
		this.getSqlSession().update("CouponBatch.clearCouponBatchActivityId",activityId);
	}
	
	/**
	 * 锁定优惠券批次，按上活动ID
	 * @param couponBatchId
	 * @param activityId
	 * @return
	 */
	public int lockCouponBatchActivity(Long couponBatchId,Long activityId) {
		Map paramterMap = new HashMap();
		paramterMap.put("couponBatchId", couponBatchId);
		paramterMap.put("activityId", activityId);
		return this.getSqlSession().update("CouponBatch.lockCouponBatchActivity",paramterMap);
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponBatchDao#getUserAllByPhone(java.lang.Integer)
	 */
	@Override
	public List<Coupon> getUserAllByPhone(Integer userId) {
		return this.getSqlSession().selectList("Coupon.getUserAllByPhone",userId);
		
	}
	
	@Override
	public List<Coupon> getCouponRewardsAllByPhone(Integer userId) {
		return this.getSqlSession().selectList("Coupon.getCouponRewardsAllByPhone",userId);
		
	}

}
