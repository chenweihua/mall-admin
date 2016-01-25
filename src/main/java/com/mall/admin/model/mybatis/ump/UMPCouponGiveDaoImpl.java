package com.mall.admin.model.mybatis.ump;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponGiveDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.ump.CouponGive;
import com.mall.admin.vo.user.User;

@Repository
public class UMPCouponGiveDaoImpl extends BaseMallDaoImpl implements UMPCouponGiveDao {

	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponGive.deleteByPrimaryKey", id);
	}

	public int insert(CouponGive record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("CouponGive.insert",record);
	}

	

	public CouponGive selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponGive.selectByPrimaryKey", id);
	}

	public int updateByPrimaryKeySelective(CouponGive record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponGive.updateByPrimaryKeySelective",record);
	}
	
	
	public int updateStatusAndMsg(CouponGive record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("CouponGive.updateStatusAndMsg",record);
	}

	public List<CouponGive> getList(Map<String,String> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<CouponGive> couponGiveList = selectPaginationList("CouponGive.getPageCouponGiveByPage", param, paginationInfo);
		return couponGiveList;
	}
	
	public int delCouponGive(Long couponGiveId) {
		return this.getSqlSession().update("CouponGive.delCouponGive",couponGiveId);
	}
	

}
