package com.mall.admin.model.mybatis.ump;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponDetailDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.ump.CouponDetail;
import com.mall.admin.vo.ump.CouponRewardsInfo;
import com.mall.admin.vo.ump.MallIni;


@Repository
public class UMPCouponDetailDaoImpl extends BaseMallDaoImpl implements UMPCouponDetailDao {

	public int deleteByPrimaryKey(Long couponDetailId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponDetail.deleteByPrimaryKey",couponDetailId);
	}

	public int insert(CouponDetail record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("CouponDetail.insert",record);
	}

	public int insertSelective(CouponDetail record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponDetail.insertSelective",record);
	}

	public CouponDetail selectByPrimaryKey(Long couponDetailId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponDetail.selectByPrimaryKey",couponDetailId);
	}

	public int updateByPrimaryKeySelective(CouponDetail record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponDetail.updateByPrimaryKeySelective",record);
	}

	public int updateByPrimaryKey(CouponDetail record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int updateAUserIdWhenNull(Long couponBatchId,Long userId) {
		Map<String,Long> paramMap = new HashMap<String,Long>();
		paramMap.put("couponBatchId", couponBatchId);
		paramMap.put("userId", userId);
		return this.getSqlSession().update("CouponDetail.updateAUserIdWhenNull");
		
	}
	
	@Override
	public List<CouponRewardsInfo> getCouponRewardsInfo(Map<String, Object> map) {
		return this.getSqlSession().selectList("CouponDetail.getCouponRewardsInfo",map);
	}
	
	@Override
	public Integer getCouponRewardsInfoCount(Map<String, Object> map) {
		return this.getSqlSession().selectOne("CouponDetail.getCouponRewardsInfoCount",map);
	}
	
	@Override
	public List<MallIni> getSwitchFromMallIni(Map<String, Object> map) {
		return this.getSqlSession().selectList("MallIni.getSwitchFromMallIni",map);
	}
	
	@Override
	public Integer getSwitchFromMallIniCount(Map<String, Object> map) {
		return this.getSqlSession().selectOne("MallIni.getSwitchFromMallIniCount",map);
	}
	
	@Override
	public Integer updateSwitchFromMallIni(String name, String value) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("name", name);
		paramMap.put("value", value);
		return this.getSqlSession().update("MallIni.updateSwitchFromMallIni",paramMap);
	}
}