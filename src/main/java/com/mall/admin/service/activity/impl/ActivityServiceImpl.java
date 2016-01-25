package com.mall.admin.service.activity.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base._;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.activity.ActivityDao;
import com.mall.admin.model.dao.activity.TemplateActivityDao;
import com.mall.admin.model.dao.ump.UMPCouponBatchDao;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.activity.ActivityTime;
import com.mall.admin.vo.activity.TemplateActivity;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.user.User;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private BgGoodsForActivityService bgGoodsForActivityService;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private UMPCouponBatchDao couponBatchDao;
	
	@Autowired
	private TemplateActivityDao templateActivityDao;

	@Override
	public Activity getActivityById(long activityId) {
		return activityDao.getActivityById(activityId);
	}
	
	@Override
	public Activity getActivityByStorageId(long storageId) {
		return activityDao.getActivityByStorageId(storageId);
	}

	@Override
	public Activity getActivityByBgName(String bgName) {
		return activityDao.getActivityByBgName(bgName);
	}

	@Override
	public long insertActivity(Activity activity) {
		return activityDao.insertActivity(activity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long insertActivityWhole(Activity activity, int openType, Date startTime, Date endTime, String batchIds)
			throws Exception {
		Date nowDate = new Date();
		insertActivity(activity);
		LogConstant.mallLog.info("插入Activity[{}]", JSON.toJSONString(activity));
		if (openType == 1) {
			ActivityTime activityTime = new ActivityTime();
			activityTime.setActivityId(activity.getActivityId());
			activityTime.setBeginTime(startTime);
			activityTime.setEndTime(endTime);
			activityTime.setCreateTime(nowDate);
			activityTime.setUpdateTime(nowDate);
			activityTime.setIsdel((byte) 0);
			insertActivityTime(activityTime);
			LogConstant.mallLog.info("插入ActivityTime[{}]", JSON.toJSONString(activityTime));
		}
		if (batchIds != null && batchIds.trim().length() > 0) {
			// 把coupon_batch的activity_id字段填充
			for (String batchId : batchIds.split(",")) {
				int affectNum = couponBatchDao.lockCouponBatchActivity(Long.parseLong(batchId),
						activity.getActivityId());
				if (affectNum == 0) {
					throw new Exception("优惠券批次号" + batchId + "不正确，请检查");
				}
			}
		}

		return activity.getActivityId();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void editActivityWhole(Activity activity, Date startTime, Date endTime, String batchIds)
			throws Exception {

		if (activity.getOpenType() == 1) {
			delActivityTimeByActivityId(activity.getActivityId());
			ActivityTime activityTime = new ActivityTime();
			activityTime.setActivityId(activity.getActivityId());
			activityTime.setBeginTime(startTime);
			activityTime.setEndTime(endTime);
			activityTime.setCreateTime(new Date());
			activityTime.setUpdateTime(new Date());
			activityTime.setIsdel((byte) 0);
			insertActivityTime(activityTime);
			LogConstant.mallLog.info("插入ActivityTime[{}]", JSON.toJSONString(activityTime));
		}
		long count = updateActivity(activity);

		checkState(count == 1, "系统错误,修改失败");

		// 删除原activityId标记
		couponBatchDao.clearCouponBatchActivityId(activity.getActivityId());

		// 把coupon_batch的activity_id字段填充
		if (batchIds != null && batchIds.trim().length() > 0) {
			for (String batchId : batchIds.split(",")) {
				int affectNum = couponBatchDao.lockCouponBatchActivity(Long.parseLong(batchId),
						activity.getActivityId());
				if (affectNum == 0) {
					throw new Exception("优惠券批次号" + batchId + "不正确，请检查");
				}
			}
		}

	}

	@Override
	public long insertActivityCollege(ActivityCollege activityCollege) {
		return activityDao.insertActivityCollege(activityCollege);
	}

	@Override
	public long insertActivityRegion(ActivityRegion activityRegion) {
		return activityDao.insertActivityRegion(activityRegion);
	}

	@Override
	public long insertActivityTime(ActivityTime activityTime) {
		return activityDao.insertActivityTime(activityTime);
	}

	@Override
	public long delActivityTimeByActivityId(long activityId) {
		return activityDao.delActivityTimeByActivityId(activityId);
	}

	@Override
	public long updateActivity(Activity activity) {
		return activityDao.updateActivity(activity);
	}

	@Override
	public Pair<Integer, List<Activity>> getActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, int college, int displayType, int start, int limit) {

		List<Long> collegeIds = Lists.newArrayList();

		if (city != -1) {
			if (college != -1) {
				List<College> colleges = collegeService.getListByCityId(city);
				if (!_.isEmpty(colleges)) {
					for (College c : colleges) {
						collegeIds.add(c.getCollegeId());
					}
				}
			} else {
				collegeIds.add((long) college);
			}
		} else {
			if (college != -1) {
				collegeIds.add((long) college);
			}
		}

		Pair<Integer, List<Activity>> result = activityDao.getActivityList(startTime, endTime, name, type,
				platformType, programType, status, city, collegeIds, displayType, start, limit);
		if (!_.isEmpty(result.getRight())) {
			for (Activity activity : result.getRight()) {
				if (activity.getOpenType() == 1) {
					ActivityTime activityTime = getActivityTimeByActivityId(activity
							.getActivityId());
					if (activityTime != null) {
						activity.setStartDate(activityTime.getBeginTime());
						activity.setEndDate(activityTime.getEndTime());
					}
				}
				activity.setValue(activity.getValue() / 100);
			}
		}
		return result;
	}

	@Override
	public Pair<Integer, List<Activity>> getCouponActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, int college, int start, int limit) {

		List<Long> collegeIds = Lists.newArrayList();

		if (city != -1) {
			if (college != -1) {
				collegeIds.add((long) college);
			} else {
				List<College> colleges = collegeService.getListByCityId(city);
				if (!_.isEmpty(colleges)) {
					for (College c : colleges) {
						collegeIds.add(c.getCollegeId());
					}
				}
				if (collegeIds.isEmpty()) { // 选择了城市，但没有选择学校时，如果该城市下的学校无学校，这里不能为空，否则会查询所有；这里放一个不存在的学校-1
					collegeIds.add(-1L);
				}
			}
		} else {
			if (college != -1) {
				collegeIds.add((long) college);
			}
		}

		Pair<Integer, List<Activity>> result = activityDao.getCouponActivityList(startTime, endTime, name,
				type, platformType, programType, status, city, collegeIds, start, limit);
		if (!_.isEmpty(result.getRight())) {
			for (Activity activity : result.getRight()) {
				if (activity.getOpenType() == 1) {
					ActivityTime activityTime = getActivityTimeByActivityId(activity
							.getActivityId());
					if (activityTime != null) {
						activity.setStartDate(activityTime.getBeginTime());
						activity.setEndDate(activityTime.getEndTime());
					}
				}
				activity.setValue(activity.getValue() / 100);
			}
		}
		return result;
	}

	private ActivityTime getActivityTimeByActivityId(long activityId) {
		ActivityTime activityTime = null;
		List<ActivityTime> activityTimeList = activityDao.getActivityTimeListByActivityId(activityId);
		if (!_.isEmpty(activityTimeList)) {
			activityTime = activityTimeList.get(0);
		}
		return activityTime;
	}

	@Override
	public int delete(long activityId) {
		return activityDao.delActivityById(activityId);
	}

	@Override
	public int open(long activityId, int status) {
		return activityDao.open(activityId, status);
	}

	@Override
	public int insert(ActivityCollege activityCollege) {
		return activityDao.insertActivityCollege(activityCollege);
	}

	@Override
	public Map<Long, ActivityCollege> getActivityCollegeByActivityId(long activityId) {
		Map<Long, ActivityCollege> activityCollegesMap = Maps.newHashMap();
		List<ActivityCollege> activityColleges = activityDao.getActivityCollegesByActivityId(activityId);
		if (!_.isEmpty(activityColleges)) {
			for (ActivityCollege activityCollege : activityColleges) {
				activityCollegesMap.put(activityCollege.getCollegeId(), activityCollege);
			}
		}
		return activityCollegesMap;
	}

	// =============================
	@Override
	public long insert(ActivityRegion activityRegion) {
		return activityDao.insertActivityRegion(activityRegion);
	}

	@Override
	public Map<Long, ActivityRegion> getActivityReginsByActivityId(long activityId) {
		Map<Long, ActivityRegion> activityRegionsMap = Maps.newHashMap();
		List<ActivityRegion> activityRegions = activityDao.getActivityReginsByActivityId(activityId);
		if (!_.isEmpty(activityRegions)) {
			for (ActivityRegion activityRegin : activityRegions) {
				activityRegionsMap.put(activityRegin.getCityId(), activityRegin);
			}
		}
		return activityRegionsMap;
	}

	@Override
	public void setActivityRegion(ZtreeBean ztreeBean, long activityId, User user) {

		checkArgument(activityId > 0, "couponRuleId");
		checkArgument(ztreeBean != null && ztreeBean.getChildren().size() > 0, "操作失败");

		Activity activity = this.getActivityById(activityId);// (couponRuleId);
		checkState(activity != null, "activity with id[%s] is null", activityId);

		List<ActivityCollege> oldActivityColleges = activityDao.getActivityCollegesByActivityId(activityId);
		List<Long> oldCollegeIds = Lists.newArrayList();
		if (!_.isEmpty(oldActivityColleges)) {
			for (ActivityCollege activityCollege : oldActivityColleges) {
				oldCollegeIds.add(activityCollege.getCollegeId());
			}
		}

		/**
		 * 全选的城市范围
		 */
		List<ActivityRegion> activityRegions = Lists.newArrayList();
		/**
		 * 需要插入的学校范围
		 */
		List<Long> collegeIds = Lists.newArrayList();

		for (ZtreeBean city : ztreeBean.getChildren()) {

			if ("false".equals(city.checked)) {
				continue;
			}

			// 选中某城市下所有学校
			if ("2".equals(city.getCheck_Child_State())) {
				long cityId = _.toLong(city.getId().split("_")[1]);

				ActivityRegion activityRegion = ActivityRegion.getBean();
				activityRegion.setCityId(cityId);
				activityRegion.setActivityId(activityId);
				activityRegion.setOperator(_.toInt(user.getUser_id() + ""));
				activityRegions.add(activityRegion);

				List<College> colleges = collegeService.getListByCityId(cityId);
				if (!_.isEmpty(colleges)) {
					// activityDao.deleteActivityCollegeByActivityId(activityId);
					for (College college : colleges) {
						collegeIds.add(college.getCollegeId());
					}
				}

			} else {
				if (!_.isEmpty(city.getChildren())) {
					for (ZtreeBean college : city.getChildren()) {
						if ("false".equals(college.checked)) {
							continue;
						}
						collegeIds.add(_.toLong(college.getId()));
					}
				}
			}

		}

		// 删除已有的城市范围、学校范围
		activityDao.deleteActivityRegionByActivityId(activityId);
		activityDao.deleteActivityCollegeByActivityId(activityId);

		if (_.isEmpty(collegeIds)) {
			return;
		}

		// 插入区域全选
		if (!_.isEmpty(activityRegions)) {
			for (ActivityRegion activityRegion : activityRegions) {
				activityDao.insertActivityRegion(activityRegion);
			}
		}
		// 插入优惠规则、学校关联信息
		if (!_.isEmpty(collegeIds)) {
			for (Long collegeId : collegeIds) {
				ActivityCollege activityCollege = ActivityCollege.getBean();
				activityCollege.setCollegeId(collegeId);
				activityCollege.setActivityId(activityId);
				activityCollege.setOperator(_.toInt(user.getUser_id() + ""));
				activityDao.insertActivityCollege(activityCollege);
			}
		}

		// 处理范围修改后，变更的学校下面的商品
		Collection<Long> newCollegeIds = Lists.newArrayList();
		Collection<Long> delCollegeIds = Lists.newArrayList();
		if (!_.isEmpty(oldCollegeIds)) {
			// 范围修改中，新增的学校Ids
			newCollegeIds = CollectionUtils.subtract(collegeIds, oldCollegeIds);
			// 范围修改中，删除的学校Ids
			delCollegeIds = CollectionUtils.subtract(oldCollegeIds, collegeIds);

			if (!_.isEmpty(delCollegeIds)) {
				Long[] cids = delCollegeIds.toArray(new Long[0]);
				long[] cids2 = new long[cids.length];
				for (int i = 0; i < cids.length; i++) {
					cids2[i] = cids[i];
				}
				bgGoodsForActivityService.deleteCollegeInActivity(activityId, cids2);
			}

			if (!_.isEmpty(newCollegeIds)) {
				Long[] cids = newCollegeIds.toArray(new Long[0]);
				long[] cids2 = new long[cids.length];
				for (int i = 0; i < cids.length; i++) {
					cids2[i] = cids[i];
				}
				bgGoodsForActivityService.addCollegeInActivity(activityId, cids2);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setActivityCollegeRelation(Long activityId, String collegeIdStrs, User user) {

		checkArgument(activityId > 0, "couponRuleId");

		Activity activity = this.getActivityById(activityId);// (couponRuleId);
		checkState(activity != null, "activity with id[%s] is null", activityId);

		activityDao.deleteActivityCollegeByActivityId(activityId);

		for (String collegeId : collegeIdStrs.split(",")) {
			ActivityCollege activityCollege = ActivityCollege.getBean();
			activityCollege.setCollegeId(Long.parseLong(collegeId));
			activityCollege.setActivityId(activityId);
			activityCollege.setOperator(_.toInt(user.getUser_id() + ""));
			activityDao.insertActivityCollege(activityCollege);
		}

	}

	@Override
	public void deleteActivityCollegeByActivityId(long activityId) {
		activityDao.deleteActivityCollegeByActivityId(activityId);
	}

	@Override
	public void deleteActivityRegionByCityIdAndActivityId(long cityId, long activityId) {
		activityDao.deleteActivityRegionByCityIdAndActivityId(cityId, activityId);
	}

	@Override
	public List<Activity> getActivityList(Boolean isSeckill) {
		return activityDao.getActivityList(isSeckill);
	}

	@Override
	public List<College> getActivityCollegeList(long activityId) {
		List<College> colleges = Lists.newArrayList();
		List<ActivityCollege> activityColleges = activityDao.getActivityCollegesByActivityId(activityId);
		if (!_.isEmpty(activityColleges)) {
			for (ActivityCollege activityCollege : activityColleges) {
				College college = CollegeConstant.getCollegeById(activityCollege.getCollegeId());
				if (college != null) {
					colleges.add(college);
				}
			}
		}
		return colleges;
	}

	@Override
	public List<ActivityCollege> queryActivityCollegeList(long activityId) {
		List<ActivityCollege> activityColleges = activityDao.getActivityCollegesByActivityId(activityId);
		return activityColleges;
	}

	@Override
	public List<Long> autoInsertActivity(long cityId, long collegeId, long userId) {
		List<Long> activityids = Lists.newArrayList();
		List<ActivityRegion> activityRegions = activityDao.getActivityReginsByCityId(cityId);
		if (!_.isEmpty(activityRegions)) {
			for (ActivityRegion activityRegion : activityRegions) {
				activityids.add(activityRegion.getActivityId());
			}
		}

		// 插入优惠规则、学校关联信息
		if (!_.isEmpty(activityids)) {
			// 轮训插入活动、学校对应关系，活动商品
			for (Long activityId : activityids) {
				ActivityCollege activityCollege = ActivityCollege.getBean();
				activityCollege.setCollegeId(collegeId);
				activityCollege.setActivityId(activityId);
				activityCollege.setOperator((int) userId);

				activityDao.insertActivityCollege(activityCollege);
				bgGoodsForActivityService.addCollegeInActivity(activityId, new long[] { collegeId });
			}
		}

		return activityids;
	}
	
	/**
	 * 通过活动类型获取活动
	 * @param activityTpye
	 * @return
	 * @throws Exception
	 */
	public List<Activity> getActivityByActivityType(int activityTpye) throws Exception {
		return activityDao.getActivityByActivityType(activityTpye);
	}
	
	
	/**
	 * 添加或更新模板活动信息
	 */
	public void saveOrUpdateTemplateActivity(TemplateActivity templateActivity) {
		TemplateActivity queryTemplateActivity = templateActivityDao.queryById(templateActivity.getActivityId());
		if(queryTemplateActivity == null) {
			templateActivityDao.insert(templateActivity);
		} else {
			templateActivityDao.update(templateActivity);
		}
	}
	
	/**
	 * 保存模板活动信息
	 */
	public void saveTemplateActivity(TemplateActivity templateActivity) {
		TemplateActivity queryTemplateActivity = queryTemplateActivityByActivityId(templateActivity.getActivityId());
		if(queryTemplateActivity != null) {
			templateActivityDao.update(templateActivity);
		} else {
			templateActivityDao.insert(templateActivity);
		}
	}
	
	/**
	 * 查询模板活动信息 
	 */
	public TemplateActivity queryTemplateActivityByActivityId(Long activityId) {
		return templateActivityDao.queryById(activityId);
	}

}
