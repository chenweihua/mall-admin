package com.mall.admin.service.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.activity.ActivityTime;
import com.mall.admin.vo.activity.TemplateActivity;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.user.User;

public interface ActivityService {

	public Activity getActivityById(long activityId);

	/**
	 * 根据活动名称，查询活动，
	 * 
	 * @param bgName
	 * @return
	 */
	public Activity getActivityByBgName(String bgName);
	
	public Activity getActivityByStorageId(long storageId);

	public long insertActivity(Activity activity);

	public long insertActivityCollege(ActivityCollege activityCollege);

	public long insertActivityRegion(ActivityRegion activityRegion);

	public long insertActivityTime(ActivityTime activityTime);

	public long delActivityTimeByActivityId(long activityId);

	public long updateActivity(Activity activity);

	public Pair<Integer, List<Activity>> getActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, int college, int displayType, int start, int limit);
	
	public Pair<Integer, List<Activity>> getCouponActivityList(Date startTime, Date endTime, String name, int type,
			int platformType, int programType, int status, int city, int college, int start, int limit);

	public int open(long activityId, int status);

	public int delete(long activityId);

	// ====================

	// ==========================

	public int insert(ActivityCollege activityCollege);

	public Map<Long, ActivityCollege> getActivityCollegeByActivityId(long activityId);
	
	public List<ActivityCollege> queryActivityCollegeList(long activityId);

	// =============================

	public long insert(ActivityRegion activityRegion);

	public Map<Long, ActivityRegion> getActivityReginsByActivityId(long activityId);

	public void setActivityRegion(ZtreeBean ztreeBean, long activityId, User user);

	public void deleteActivityCollegeByActivityId(long activityId);

	public void deleteActivityRegionByCityIdAndActivityId(long cityId, long activityId);

	/**
	 * 获取活动列表
	 * 
	 * @param isSeckill
	 *                true:秒杀活动，false:日常活动
	 * @return
	 */
	public List<Activity> getActivityList(Boolean isSeckill);

	public List<College> getActivityCollegeList(long activityId);

	/**
	 * 设置某活动为城市范围，当此城市添加学校自动添加此活动
	 * 
	 * @param cityId
	 * @param collegeId
	 * @param userId
	 * @return
	 */
	public List<Long> autoInsertActivity(long cityId, long collegeId, long userId);
	
	public long insertActivityWhole(Activity activity, int openType, Date startTime, Date endTime, String batchIds) throws Exception ;
	
	public void editActivityWhole(Activity activity, Date startTime, Date endTime, String batchIds) throws Exception ;
	
	public void setActivityCollegeRelation(Long activityId, String collegeIdStrs, User user);
	/**
	 * 通过活动类型获取活动
	 * @param activityTpye
	 * @return
	 * @throws Exception
	 */
	public List<Activity> getActivityByActivityType(int activityTpye) throws Exception;
	
	public void saveOrUpdateTemplateActivity(TemplateActivity templateActivity);
	
	public TemplateActivity queryTemplateActivityByActivityId(Long activityId);
	
	/**
	 * 保存模板活动信息
	 */
	public void saveTemplateActivity(TemplateActivity templateActivity);

}
