package com.mall.admin.model.mybatis.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.model.dao.activity.ActivityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityCollegeExample;
import com.mall.admin.vo.activity.ActivityCollegeMapper;
import com.mall.admin.vo.activity.ActivityExample;
import com.mall.admin.vo.activity.ActivityMapper;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.activity.ActivityRegionExample;
import com.mall.admin.vo.activity.ActivityRegionMapper;
import com.mall.admin.vo.activity.ActivityTime;
import com.mall.admin.vo.activity.ActivityTimeExample;
import com.mall.admin.vo.activity.ActivityTimeMapper;

@Repository
public class ActivityDaoImpl extends BaseMallDaoImpl implements ActivityDao {

	public ActivityMapper getActivityMapper() {
		return this.getSqlSession().getMapper(ActivityMapper.class);
	}

	public ActivityCollegeMapper getActivityCollegeMapper() {
		return this.getSqlSession().getMapper(ActivityCollegeMapper.class);
	}

	public ActivityRegionMapper getActivityRegionMapper() {
		return this.getSqlSession().getMapper(ActivityRegionMapper.class);
	}

	public ActivityTimeMapper getActivityTimeMapper() {
		return this.getSqlSession().getMapper(ActivityTimeMapper.class);
	}

	@Override
	public Activity getActivityById(long activityId) {
		return this.getActivityMapper().selectByPrimaryKey(activityId);
	}
	
	@Override
	public Activity getActivityByStorageId(long storageId) {
		return this.getActivityMapper().getActivityByStorageId(storageId);
	}

	@Override
	public Activity getActivityByBgName(String bgName) {
		ActivityExample example = new ActivityExample();
		example.createCriteria().andActivityNameEqualTo(bgName).andIsDelEqualTo(_.toByte(0));
		List<Activity> activityList = getActivityMapper().selectByExample(example);
		if (activityList == null || activityList.size() == 0) {
			return null;
		}
		return activityList.get(0);
	}

	@Override
	public List<ActivityTime> getActivityTimeListByActivityId(long activityId) {
		ActivityTimeExample example = new ActivityTimeExample().setOrderByClause(" activity_time_id desc");
		example.createCriteria().andActivityIdEqualTo(activityId);
		return getActivityTimeMapper().selectByExample(example);
	}

	@Override
	public int insertActivity(Activity activity) {
		return getActivityMapper().insert(activity);
	}

	@Override
	public int insertActivityCollege(ActivityCollege activityCollege) {
		return getActivityCollegeMapper().insert(activityCollege);
	}

	@Override
	public int insertActivityRegion(ActivityRegion activityRegion) {
		return getActivityRegionMapper().insert(activityRegion);
	}

	@Override
	public int insertActivityTime(ActivityTime activityTime) {
		return getActivityTimeMapper().insert(activityTime);
	}

	@Override
	public long delActivityTimeByActivityId(long activityId) {
		ActivityTimeExample example = new ActivityTimeExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		return this.getActivityTimeMapper().deleteByExample(example);
	}

	@Override
	public long updateActivity(Activity activity) {
		return this.getActivityMapper().updateByPrimaryKeySelective(activity);
	}

	@Override
	public Pair<Integer, List<Activity>> getActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, List<Long> collegeIds, int displayType, int start,
			int limit) {

		Map<String, Object> example = Maps.newHashMap();

		if (startTime != null) {
			example.put("startTime", startTime);
		}
		if (endTime != null) {
			example.put("endTime", endTime);
		}
		if (type != -1) {
			example.put("type", type);
		}
		if (platformType != -1) {
			example.put("platformType", platformType);
		}
		if (programType != -1) {
			example.put("programType", programType);
		}
		if (status != -1) {
			example.put("status", status);
		}
		if (!_.isEmpty(name)) {
			example.put("name", "%" + name + "%");
		}
		if (!_.isEmpty(collegeIds)) {
			example.put("collegeIds", "(" + _.join(collegeIds, ",") + ")");
		}
		if (displayType != -1) {
			example.put("displayType", displayType);
		}
		if (start > -1 && limit > 0) {
			example.put("start", start);
			example.put("limit", limit);
		}

		List<Activity> datas = getActivityMapper().selectPage(example);
		int count = getActivityMapper().countPage(example);
		return Pair.of(count, datas);
	}
	
	@Override
	public Pair<Integer, List<Activity>> getCouponActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, List<Long> collegeIds, int start,
			int limit) {

		Map<String, Object> example = Maps.newHashMap();

		if (startTime != null) {
			example.put("startTime", startTime);
		}
		if (endTime != null) {
			example.put("endTime", endTime);
		}
		if (type != -1) {
			example.put("type", type);
		}
		if (platformType != -1) {
			example.put("platformType", platformType);
		}
		if (programType != -1) {
			example.put("programType", programType);
		}
		if (status != -1) {
			example.put("status", status);
		}
		if (!_.isEmpty(name)) {
			example.put("name", "%" + name + "%");
		}
		if (!_.isEmpty(collegeIds)) {
			example.put("collegeIds", "(" + _.join(collegeIds, ",") + ")");
		}
		if (start > -1 && limit > 0) {
			example.put("start", start);
			example.put("limit", limit);
		}

		List<Activity> datas = getActivityMapper().selectCouponPage(example);
		int count = getActivityMapper().countCouponPage(example);
		return Pair.of(count, datas);
	}

	@Override
	public int delActivityById(long activityId) {
		return getActivityMapper().deleteByPrimaryKey(activityId);
	}

	@Override
	public int open(long activityId, int status) {
		return getActivityMapper().open(activityId, status);
	}

	@Override
	public List<ActivityCollege> getActivityCollegesByActivityId(long activityId) {
		ActivityCollegeExample example = new ActivityCollegeExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andIsDelEqualTo(_.toByte(0));
		return getActivityCollegeMapper().selectByExample(example);
	}

	@Override
	public List<ActivityRegion> getActivityReginsByActivityId(long activityId) {
		ActivityRegionExample example = new ActivityRegionExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andIsDelEqualTo(_.toByte(0));
		return getActivityRegionMapper().selectByExample(example);
	}

	@Override
	public List<ActivityRegion> getActivityReginsByCityId(long cityId) {
		ActivityRegionExample example = new ActivityRegionExample();
		example.createCriteria().andCityIdEqualTo(cityId).andIsDelEqualTo(_.toByte(0));
		return getActivityRegionMapper().selectByExample(example);
	}

	@Override
	public void deleteActivityCollegeByActivityId(long activityId) {
		ActivityCollegeExample example = new ActivityCollegeExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		getActivityCollegeMapper().deleteByExample(example);
	}

	@Override
	public void deleteActivityRegionByCityIdAndActivityId(long cityId, long activityId) {
		ActivityRegionExample example = new ActivityRegionExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andCityIdEqualTo(cityId);
		getActivityRegionMapper().deleteByExample(example);
	}

	@Override
	public void deleteActivityRegionByActivityId(long activityId) {
		ActivityRegionExample example = new ActivityRegionExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		getActivityRegionMapper().deleteByExample(example);
	}

	@Override
	public List<Activity> getActivityList(Boolean isSeckill) {

		ActivityExample example = new ActivityExample();
		if (isSeckill == null) {
			List<Byte> types = Lists.newArrayList();
			types.add((byte) 0);
			types.add((byte) 1);
			example.createCriteria().andActivityTypeIn(types).andIsDelEqualTo((byte) 0);
		} else {
			int type = isSeckill ? 0 : 1;
			example.createCriteria().andActivityTypeEqualTo((byte) type).andIsDelEqualTo((byte) 0);
		}

		return getActivityMapper().selectByExample(example);
	}
	
	/**
	 * 通过活动类型获取活动
	 * @param activityTpye
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Activity> getActivityByActivityType(int activityTpye) throws Exception{
		ActivityExample example = new ActivityExample();
		example.createCriteria().andActivityTypeEqualTo((byte) activityTpye).andIsDelEqualTo((byte) 0);
		return getActivityMapper().selectByExample(example);
	}

}
