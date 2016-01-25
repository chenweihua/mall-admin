package com.mall.admin.model.mybatis.ump;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.ump.UMPCouponRestrictDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.ump.CouponRestrict;

@Repository
public class UMPCouponRestrictDaoImpl extends BaseMallDaoImpl implements UMPCouponRestrictDao {

	public int deleteByPrimaryKey(Long restrictId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponRestrict.deleteByPrimaryKey",restrictId);
	}

	public int insert(CouponRestrict record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponRestrict.insert",record);

	}

	public int insertSelective(CouponRestrict record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("CouponRestrict.insertSelective",record);
	}

	public CouponRestrict selectByPrimaryKey(Long restrictId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponRestrict.selectByPrimaryKey",restrictId);
	}

	public int updateByPrimaryKeySelective(CouponRestrict record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponRestrict.updateByPrimaryKeySelective",record);

	}

	public int updateByPrimaryKey(CouponRestrict record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("CouponRestrict.updateByPrimaryKey",record);
	}

}
