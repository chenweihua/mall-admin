package com.mall.admin.model.mybatis.ump;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponGiveLogDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.ump.CouponGive;
import com.mall.admin.vo.ump.CouponGiveLog;

@Repository
public class UMPCouponGiveLogDaoImpl extends BaseMallDaoImpl implements UMPCouponGiveLogDao {


	public int insert(CouponGiveLog record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("CouponGiveLog.insert",record);
	}

	

	public CouponGiveLog selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponGiveLog.selectByPrimaryKey", id);
	}

	public int updateByPrimaryKeySelective(CouponGiveLog record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponGiveLog.updateByPrimaryKeySelective",record);
	}

	public List<CouponGiveLog> getList(Map<String,String> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<CouponGiveLog> couponGiveList = selectPaginationList("CouponGiveLog.getPageCouponGiveLogByPage", param, paginationInfo);
		return couponGiveList;
	}
	
	public int updateByCouponGiveIdAndUserId(CouponGiveLog record) {
		return this.getSqlSession().update("CouponGiveLog.updateByCouponGiveIdAndUserId",record);
	}
	
	public List<CouponGiveLog> querySendedCouponGiveLogByCouponGiveId(Long couponGiveId) {
		return this.getSqlSession().selectList("CouponGiveLog.querySendedCouponGiveLogByCouponGiveId", couponGiveId);
	}

}
