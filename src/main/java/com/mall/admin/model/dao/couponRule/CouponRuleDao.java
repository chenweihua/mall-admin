package com.mall.admin.model.dao.couponRule;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.couponRule.CouponRule;

public interface CouponRuleDao {
	
	public long insert(CouponRule couponRule);
	
	public Pair<Integer, List<CouponRule>> getCouponRules(int type, int start, int numPerPage);
	
	public int delete(int couponId);
	
	public int update(CouponRule conpuRule);
	
	public CouponRule getById(int couponId);
	
	//==========================
	
	public int insert(CouponCollege couponCollege);
	
	public List<CouponCollege> getCouponCollegesByCouponRuleId(int couponRuleId);
	
	//=============================
	
	public long insert(CouponRegion couponRegion);
	
	public List<CouponRegion> getCouponReginsByCouponRuleId(int couponRuleId);

	/**
	 * 删除某优惠规则对应的所有学校范围
	 * @param couponRuleId
	 */
	public void deleteCouponCollegeByCouoponRuleId(int couponRuleId);

	/**
	 * 删除某优惠规则对应的城市全选范围
	 * @param cityId
	 * @param couponRuleId
	 */
	public void deleteCouponRegionByCityIdAndCouponRuleId(int cityId, int couponRuleId);

	/**
	 * 获取某学校首减优惠规则
	 * @param collegeId
	 * @return
	 */
	public List<CouponRule> getFirstSubCouponRule(long collegeId, long excludeCouponRuleId);

	/**
	 * 获取某学校满减额为XX的优惠规则
	 * @param collegeId
	 * @param totalMoney
	 * @return
	 */
	public List<CouponRule> getFullSubCouponRuleByAmount(long collegeId, int totalMoney, long excludeCouponRuleId);

	public void deleteCouponRegionByCouponRuleId(int couponRuleId);

	public List<CouponRegion> getCouponReginsByCityId(long cityId);
	
}
