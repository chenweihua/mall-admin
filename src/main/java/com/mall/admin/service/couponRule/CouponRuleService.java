package com.mall.admin.service.couponRule;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.couponRule.CouponRule;
import com.mall.admin.vo.user.User;

public interface CouponRuleService {
	public long insert(CouponRule couponRule);
	
	public Pair<Integer, List<CouponRule>> getCouponRules(int type, int start, int numPerPage);
	
	public int delete(int couponId);
	
	public int update(CouponRule conpuRule);
	
	public CouponRule getCouponRuleById(int couponId);
	
	//==========================
	
	public int insert(CouponCollege couponCollege);
	
	public Map<Integer, CouponCollege> getCouponCollegesByCouponRuleId(int couponRuleId);
	
	//=============================
	
	public long insert(CouponRegion couponRegion);
	
	public Map<Integer, CouponRegion> getCouponReginsByCouponRuleId(int couponRuleId);

	public void setCouponRuleRegion(ZtreeBean ztreeBean, int couponRuleIdInt, User user);
	
	public void deleteCouponCollegeByCouoponRuleId(int couponRuleId);
	
	public void deleteCouponRegionByCityIdAndCouponRuleId(int cityId, int couponRuleId);
	
	/**
	 * 获取某学校首减优惠规则
	 * @return
	 */
	public List<CouponRule> getFirstSubCouponRule(long collegeId, long excludeCouponRuleId);
	
	/**
	 * 根据满额获取某学校优惠规则
	 * @return
	 */
	public List<CouponRule> getFullSubCouponRuleByAmount(long collegeId, int totalMoney, long excludeCouponRuleId);
	
	/**
	 * 设置某优惠规则为城市范围，当此城市添加学校自动添加此规则
	 * @param cityId
	 * @param collegeId
	 * @param userId
	 * @return
	 */
	public List<Integer> autoInsertCouponRule(long cityId, long collegeId, long userId);
}
