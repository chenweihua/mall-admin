package com.mall.admin.model.mybatis.couponRule;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.couponRule.CouponRuleDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponCollegeExample;
import com.mall.admin.vo.couponRule.CouponCollegeMapper;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.couponRule.CouponRegionExample;
import com.mall.admin.vo.couponRule.CouponRegionMapper;
import com.mall.admin.vo.couponRule.CouponRule;
import com.mall.admin.vo.couponRule.CouponRuleExample;
import com.mall.admin.vo.couponRule.CouponRuleExample.Criteria;
import com.mall.admin.vo.couponRule.CouponRuleMapper;

@Repository
public class CouponRuleDaoImpl extends BaseMallDaoImpl implements CouponRuleDao  {

	public CouponRuleMapper getCouponRuleMapper() {
		return this.getSqlSession().getMapper(CouponRuleMapper.class);
	}
	
	public CouponCollegeMapper getCouponCollegeMapper() {
		return this.getSqlSession().getMapper(CouponCollegeMapper.class);
	}
	
	public CouponRegionMapper getCouponRegionMapper() {
		return this.getSqlSession().getMapper(CouponRegionMapper.class);
	}
	
	@Override
	public long insert(CouponRule couponRule) {
		return getCouponRuleMapper().insertSelective(couponRule);
	}

	@Override
	public Pair<Integer, List<CouponRule>> getCouponRules(int type, int start, int numPerPage) {
		CouponRuleExample example = new CouponRuleExample().setStart(start).setLimit(numPerPage);
		Criteria criteria = example.createCriteria().andIsDelEqualTo(_.toByte(0));
		if(type != 0) {
			criteria.andCouponTypeEqualTo(_.toByte(type));
		}
		List<CouponRule> datas = getCouponRuleMapper().selectByExample(example);
		int count = getCouponRuleMapper().countByExample(example);
		return Pair.of(count, datas);
	}

	@Override
	public int delete(int couponId) {
		return getCouponRuleMapper().deleteByPrimaryKey(couponId);
	}

	@Override
	public int update(CouponRule couponRule) {
		return getCouponRuleMapper().updateByPrimaryKey(couponRule);
	}
	
	@Override
	public CouponRule getById(int couponId) {
		return getCouponRuleMapper().selectByPrimaryKey(couponId);
	}
	
	@Override
	public List<CouponCollege> getCouponCollegesByCouponRuleId(int couponRuleId) {
		CouponCollegeExample example = new CouponCollegeExample();
		example.createCriteria().andCouponRuleIdEqualTo(couponRuleId).andIsDelEqualTo(_.toByte(0));
		return getCouponCollegeMapper().selectByExample(example);
	}
	
	@Override
	public List<CouponRegion> getCouponReginsByCouponRuleId(int couponRuleId) {
		CouponRegionExample example = new CouponRegionExample();
		example.createCriteria().andCouponRuleIdEqualTo(couponRuleId).andIsDelEqualTo(_.toByte(0));
		return getCouponRegionMapper().selectByExample(example);
	}
	
	@Override
	public List<CouponRegion> getCouponReginsByCityId(long cityId) {
		CouponRegionExample example = new CouponRegionExample();
		example.createCriteria().andCityIdEqualTo((int)cityId).andIsDelEqualTo(_.toByte(0));
		return getCouponRegionMapper().selectByExample(example);
	}
	
	@Override
	public int insert(CouponCollege couponCollege) {
		return getCouponCollegeMapper().insertSelective(couponCollege);
	}
	
	@Override
	public long insert(CouponRegion couponRegion) {
		return getCouponRegionMapper().insertSelective(couponRegion);
	}
	
	@Override
	public void deleteCouponCollegeByCouoponRuleId(int couponRuleId) {
		CouponCollegeExample example = new CouponCollegeExample();
		example.createCriteria().andCouponRuleIdEqualTo(couponRuleId);
		getCouponCollegeMapper().deleteByExample(example);
	}
	
	@Override
	public void deleteCouponRegionByCityIdAndCouponRuleId(int cityId,
			int couponRuleId) {
		CouponRegionExample example = new CouponRegionExample();
		example.createCriteria().andCityIdEqualTo(cityId).andCouponRuleIdEqualTo(couponRuleId);
		getCouponRegionMapper().deleteByExample(example);
	}
	
	@Override
	public List<CouponRule> getFirstSubCouponRule(long collegeId, long excludeCouponRuleId) {
		return getCouponRuleMapper().selectFirstSub(collegeId, excludeCouponRuleId);
	}

	@Override
	public List<CouponRule> getFullSubCouponRuleByAmount(long collegeId, int totalMoney, long excludeCouponRuleId) {
		return getCouponRuleMapper().selectFullSubByTotalMoney(collegeId, totalMoney, excludeCouponRuleId);
	}
	
	@Override
	public void deleteCouponRegionByCouponRuleId(int couponRuleId) {
		CouponRegionExample example = new CouponRegionExample();
		example.createCriteria().andCouponRuleIdEqualTo(couponRuleId);
		getCouponRegionMapper().deleteByExample(example);
	}
}
