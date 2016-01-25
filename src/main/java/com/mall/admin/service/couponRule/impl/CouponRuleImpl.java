package com.mall.admin.service.couponRule.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base._;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.couponRule.CouponRuleDao;
import com.mall.admin.service.couponRule.CouponRuleService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.couponRule.CouponRule;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.user.User;

@Service
public class CouponRuleImpl implements CouponRuleService {

	@Autowired
	private CouponRuleDao couponRuleDao;
	@Autowired
	private CityService cityService;
	@Autowired
	private CollegeService collegeService;

	@Override
	public long insert(CouponRule couponRule) {
		return couponRuleDao.insert(couponRule);
	}

	@Override
	public Pair<Integer, List<CouponRule>> getCouponRules(int type, int start,
			int numPerPage) {
		return couponRuleDao.getCouponRules(type, start, numPerPage);
	}

	@Override
	public int delete(int couponId) {
		return couponRuleDao.delete(couponId);
	}

	@Override
	public int update(CouponRule conpuRule) {
		return couponRuleDao.update(conpuRule);
	}
	
	@Override
	public CouponRule getCouponRuleById(int couponId) {
		return couponRuleDao.getById(couponId);
	}

	@Override
	public int insert(CouponCollege couponCollege) {
		return couponRuleDao.insert(couponCollege);
	}

	@Override
	public Map<Integer, CouponCollege> getCouponCollegesByCouponRuleId(
			int couponRuleId) {
		Map<Integer, CouponCollege> couponCollegesMap = Maps.newHashMap();
		List<CouponCollege> couponColleges = couponRuleDao.getCouponCollegesByCouponRuleId(couponRuleId);
		if(!_.isEmpty(couponColleges)) {
			for(CouponCollege couponCollege : couponColleges) {
				couponCollegesMap.put(couponCollege.getCollegeId(), couponCollege);
			}
		}
		return couponCollegesMap;
	}

	@Override
	public long insert(CouponRegion couponRegion) {
		return couponRuleDao.insert(couponRegion);
	}

	@Override
	public Map<Integer, CouponRegion> getCouponReginsByCouponRuleId(
			int couponRuleId) {
		Map<Integer, CouponRegion> couponRegionsMap = Maps.newHashMap();
		List<CouponRegion> couponRegions = couponRuleDao.getCouponReginsByCouponRuleId(couponRuleId);
		if(!_.isEmpty(couponRegions)) {
			for(CouponRegion couponRegin : couponRegions) {
				couponRegionsMap.put(couponRegin.getCouponRegionId(), couponRegin);
			}
		}
		return couponRegionsMap;
	}
	
	@Override
	public void setCouponRuleRegion(ZtreeBean ztreeBean, int couponRuleId,
			User user) {
		
		checkArgument(couponRuleId>0, "couponRuleId");
		checkArgument(ztreeBean != null && ztreeBean.getChildren().size()>0, "操作失败");
		
		CouponRule couponRule = this.getCouponRuleById(couponRuleId);
		checkState(couponRule!=null, "couponRule with id[%s] is null", couponRuleId);

		/**
		 * 全选的城市范围
		 */
		List<CouponRegion> couponRegions = Lists.newArrayList();
		/**
		 * 需要插入的学校范围
		 */
		List<Long> collegeIds = Lists.newArrayList();
		
		for(ZtreeBean city: ztreeBean.getChildren()) {
			
			if("false".equals(city.checked)) {
				continue;
			}
			
			// 选中某城市下所有学校
			if("2".equals(city.getCheck_Child_State())) {
				int cityId = _.toInt(city.getId().split("_")[1]);
				CouponRegion couponRegion = CouponRegion.getBean();
				couponRegion.setCityId(cityId);
				couponRegion.setCouponRuleId(couponRuleId);
				couponRegion.setOperator(_.toInt(user.getUser_id()+""));
				couponRegions.add(couponRegion);
				
				List<College> colleges = collegeService.getListByCityId(cityId);
				if(!_.isEmpty(colleges)) {
//					couponRuleDao.deleteCouponCollegeByCouoponRuleId(couponRuleId);
					for(College college : colleges) {
						collegeIds.add(college.getCollegeId());
					}
				}
				
			} else {
				if(!_.isEmpty(city.getChildren())) {
					for(ZtreeBean college: city.getChildren()) {
						if("false".equals(college.checked)) {
							continue;
						}
						collegeIds.add(_.toLong(college.getId()));
					}
				}
			}
			
			// 检查学校是否满足添加优惠规则条件
			checkCouponRule(collegeIds, couponRule);
			
			couponRuleDao.deleteCouponRegionByCouponRuleId(couponRuleId);
			couponRuleDao.deleteCouponCollegeByCouoponRuleId(couponRuleId);
			
			// 插入区域全选
			if(!_.isEmpty(couponRegions)) {
				for(CouponRegion couponRegion : couponRegions) {
					
					couponRuleDao.insert(couponRegion);
				}
			}
			// 插入优惠规则、学校关联信息
			if(!_.isEmpty(collegeIds)) {
				
				for(Long collegeId: collegeIds) {
					CouponCollege couponCollege = CouponCollege.getBean();
					couponCollege.setCollegeId(_.toInt(collegeId+""));
					couponCollege.setCouponRuleId(couponRuleId);
					couponCollege.setOperator(_.toInt(user.getUser_id()+""));
					couponRuleDao.insert(couponCollege);
				}
			}
		}
	}
	
	/**
	 * 首减只能有一个
	 * 满减额不能相同
	 * @param collegeIds
	 * @param couponRule
	 */
	private void checkCouponRule(List<Long> collegeIds, CouponRule couponRule) {
		
		checkArgument(collegeIds!=null&&collegeIds.size()>0, "选择学校不能为空");
		for(Long collegeId : collegeIds) {
			College college = CollegeConstant.getCollegeById(collegeId);
			checkState(college!=null, "college with id(%s) is null", collegeId);
			// 1:首单免检 
			if(couponRule.getCouponType() == 1) {
				List<CouponRule> firstSubCouponRules = couponRuleDao.getFirstSubCouponRule(collegeId, couponRule.getCouponId());
				checkState(firstSubCouponRules == null || firstSubCouponRules.size()==0, "[%s] 首减只能有一个", college.getCollegeName());
			} 
			// 2:满额减'
			else {
				
				List<CouponRule> fullSubCouponRules = couponRuleDao.getFullSubCouponRuleByAmount(collegeId, couponRule.getTotalMoney(), couponRule.getCouponId());
				checkState(fullSubCouponRules == null || fullSubCouponRules.size()==0, "[%s] 满减额[%s]为已经存在", college.getCollegeName(), couponRule.getTotalMoney());
			}
		}
		
	}
	
	@Override
	public void deleteCouponCollegeByCouoponRuleId(int couponRuleId) {
		couponRuleDao.deleteCouponCollegeByCouoponRuleId(couponRuleId);
	}
	
	@Override
	public void deleteCouponRegionByCityIdAndCouponRuleId(int cityId,
			int couponRuleId) {
		couponRuleDao.deleteCouponRegionByCityIdAndCouponRuleId(cityId, couponRuleId);
	}
	
	@Override
	public List<CouponRule> getFirstSubCouponRule(long collegeId, long excludeCouponRuleId) {
		return couponRuleDao.getFirstSubCouponRule(collegeId,excludeCouponRuleId);
	}
	
	@Override
	public List<CouponRule> getFullSubCouponRuleByAmount(long collegeId, int totalMoney, long excludeCouponRuleId) {
		return couponRuleDao.getFullSubCouponRuleByAmount(collegeId, totalMoney,excludeCouponRuleId);
	}
	
	@Override
	public List<Integer> autoInsertCouponRule(long cityId, long collegeId, long userId) {
		
		List<Integer> couponRuleIds = Lists.newArrayList();
		List<CouponRegion> couponRegions = couponRuleDao.getCouponReginsByCityId(cityId);// activityDao.getActivityReginsByCityId(cityId);
		if(!_.isEmpty(couponRegions)) {
			for(CouponRegion couponRegion : couponRegions) {
				couponRuleIds.add(couponRegion.getCouponRuleId());
			}
		}
		
		// 插入优惠规则、学校关联信息
		if(!_.isEmpty(couponRuleIds)) {
			// 轮训插入优惠规则、学校对应关系
			for(int couponRuleId : couponRuleIds) {
				CouponCollege couponCollege = CouponCollege.getBean();
				couponCollege.setCollegeId(_.toInt(collegeId+""));
				couponCollege.setCouponRuleId(couponRuleId);
				couponCollege.setOperator(_.toInt(userId+""));
				couponRuleDao.insert(couponCollege);
			}
		}
		
		return couponRuleIds;
	}
}
