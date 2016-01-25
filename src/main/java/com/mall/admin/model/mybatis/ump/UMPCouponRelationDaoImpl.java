package com.mall.admin.model.mybatis.ump;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponRelationDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.ump.CouponBatchRelation;

@Repository
public class UMPCouponRelationDaoImpl extends BaseMallDaoImpl implements UMPCouponRelationDao {

	/* (non-Javadoc)
	 * @see com.mall.admin.model.dao.ump.UMPCouponRelationDao#insert(com.mall.admin.vo.ump.CouponBatchRelation)
	 */
	@Override
	public int insert(CouponBatchRelation couponBatchRelation) {
		return this.getSqlSession().insert("CouponBatchRelation.insert",couponBatchRelation);
	}
	
	
	public List<CouponBatchRelation> queryCouponBatchRelations(Long couponBatchId) {
		
		return this.getSqlSession().selectList("CouponBatchRelation.selectByCouponBatchId",couponBatchId);
		
	}


}
