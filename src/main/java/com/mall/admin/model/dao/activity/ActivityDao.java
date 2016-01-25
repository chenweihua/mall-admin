package com.mall.admin.model.dao.activity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.activity.ActivityTime;

public interface ActivityDao {

	public Activity getActivityById(long activityId);

	public Activity getActivityByBgName(String bgName);
	
	public Activity getActivityByStorageId(long storageId);

	public List<ActivityTime> getActivityTimeListByActivityId(long activityId);

	public int insertActivity(Activity activity);

	public int insertActivityCollege(ActivityCollege activityCollege);

	public int insertActivityRegion(ActivityRegion activityRegion);

	public int insertActivityTime(ActivityTime activityTime);

	public long delActivityTimeByActivityId(long activityId);

	public long updateActivity(Activity activity);

	public Pair<Integer, List<Activity>> getActivityList(Date startTime, Date endTime, String name, int type,
			int programType, int platformType, int status, int city, List<Long> collegeIds, int displayType, int start,
			int limit);
	
	public Pair<Integer, List<Activity>> getCouponActivityList(Date startTime, Date endTime, String name, int type,
			int programType, int platformType, int status, int city, List<Long> collegeIds, int start,
			int limit);

	public int delActivityById(long activityIdInt);

	public int open(long activityId, int status);

	public List<ActivityCollege> getActivityCollegesByActivityId(long activityId);

	public List<ActivityRegion> getActivityReginsByActivityId(long activityId);

	public List<ActivityRegion> getActivityReginsByCityId(long cityId);

	public void deleteActivityCollegeByActivityId(long activityId);

	public void deleteActivityRegionByCityIdAndActivityId(long cityId, long activityId);

	public void deleteActivityRegionByActivityId(long activityId);

	/**
	 * 获取活动列表
	 * 
	 * @param isSeckill
	 *                true:秒杀活动，false:日常活动
	 * @return
	 */
	public List<Activity> getActivityList(Boolean isSeckill);
	
	/**
	 * 通过活动类型获取活动
	 * @param activityType
	 * @return
	 * @throws Exception
	 */
	public List<Activity> getActivityByActivityType(int activityType) throws Exception;

}
