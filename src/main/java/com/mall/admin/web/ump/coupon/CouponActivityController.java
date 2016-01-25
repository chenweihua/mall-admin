package com.mall.admin.web.ump.coupon;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.enumdata.CouponActivityPlan;
import com.mall.admin.enumdata.CouponActivityPlatform;
import com.mall.admin.enumdata.CouponActivityType;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.banner.BannerService;
import com.mall.admin.service.couponRule.CouponRuleService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.ump.coupon.CouponService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityTime;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/coupon/activity")
public class CouponActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private CouponRuleService couponRuleService;
	@Autowired
	private ZtreeUtil ztreeUtil;
	@Autowired
	private WmsGoodsService wmsGoodsService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	CityService cityService;
	@Autowired
	BannerService bannerService;
	@Autowired
	CouponService couponService;

	private static final Logger log = LogConstant.mallLog;
	
	//23:59:59
	private static final int HOUR=23;
	private static final int MINUTE=59;
	private static final int SECOND=59;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "0") String type) throws SQLException,
			IOException {
		ModelAndView model = new ModelAndView("ump/coupon/couponActivity", _.asMap("have_nav", true, "type", type));
		model.addObject("ActivityType", CouponActivityType.values());
		model.addObject("ActivityPlatform", CouponActivityPlatform.values());
		model.addObject("ActivityPlan", CouponActivityPlan.values());
		return model;

	}

	@RequestMapping("/ajaxListData")
	@ResponseBody
	public Object ajaxListData(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam String startDate, @RequestParam String endDate, @RequestParam String name,
			@RequestParam int type, @RequestParam int platformType, @RequestParam int programType,
			@RequestParam int status, @RequestParam int city, @RequestParam int college)
			throws SQLException, IOException, ParseException {
		User loginInfo = (User) request.getAttribute("user");
		try {
			long total = 0;
			Date startTime =null;
			Date endTime =null;
			if(StringUtils.isNotBlank(startDate)){
				startTime = DateUtil.formatDateToDate(startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				endTime =  DateUtil.formatDateToDate(endDate);
			}
			
			Pair<Integer, List<Activity>> datas = activityService.getCouponActivityList(startTime, endTime, name,
					type, platformType, programType, status, city, college, start, numPerPage);// .getCouponRules(typeInt,
															// start,
															// numPerPage);
															// //CouponRule.getListBySearchUserNameAndLimit(collegeId,
															// typeInt,
															// start,
															// numPerPage);
			log.info("user[{}:{}] to query Activity type:{},start:{},numPerPage:{}",
					loginInfo.getUser_id(), loginInfo.getUser_name(), type, start, numPerPage);
			return buildDataTableResult(draw, total, datas.getLeft(), datas.getRight());
		} catch (Exception e) {
			e.printStackTrace();
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}

	}

	@RequestMapping("/ajaxMateData")
	@ResponseBody
	public Object ajaxMateData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		Collection<City> cityList = CityConstant.getcityMap().values();
		List<City> list = new ArrayList<City>();
		Map<Long, List<College>> collegeMap = Maps.newHashMap();
		for (City city : cityList) {
			if(city.getLevel()==1L){
				list.add(city);
				collegeMap.put(city.getCityId(), CollegeConstant.getCollegesByCityId(city.getCityId()));
			}
		}
		result.put("citiList", list);
		result.put("collegeMap", collegeMap);
		return result;
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Object doEdit(HttpServletRequest request, HttpServletResponse response, @RequestParam String activityId,
			@RequestParam String addType, @RequestParam String addClickType,
			@RequestParam String addPlatformType, @RequestParam String addProgramType,
			@RequestParam String batchIds, @RequestParam String value, @RequestParam String addOpenType,
			@RequestParam String addShowType, @RequestParam String bgName, @RequestParam String name,
			@RequestParam String imageUrl2, @RequestParam String weight, @RequestParam String startDate,
			@RequestParam String endDate) throws SQLException {

		User loginInfo = (User) request.getAttribute("user");

		try {

			checkState(loginInfo != null, "请先登录");
			Date now = new Date();
			int id = _.toInt(activityId, -1);
			int type = _.toInt(addType, -1);
			int clickType = _.toInt(addClickType, -1);
			int platformType = _.toInt(addPlatformType, -1);
			int openType = _.toInt(addOpenType, -1);
			int weightInt = _.toInt(weight, 0);
			int status = 0;
			int isOpen = 0;
			int isShow = _.toInt(addShowType, -1);
			int programType = _.toInt(addProgramType, -1);
			long values = _.toLong(value);
			checkArgument((isShow == 0 || isShow == 1), "是否显示类型错误");
			checkArgument(id > 0, "活动id类型错误");
			checkArgument((type > 0 || type < 9), "活动类型错误");
			checkArgument((clickType > 0 || clickType < 9), "点击活动类型错误");
			checkArgument((platformType > 0 || platformType < 9), "平台类型错误");
			checkArgument((openType == 0 || openType == 1), "活动开启方式错误");
			checkArgument(!_.isEmpty(bgName) && bgName.length() < 100, "活动名称不规范");
			checkArgument(!_.isEmpty(name) && name.length() < 100, "活动后台显示名称不规范");
			checkArgument(!_.isEmpty(imageUrl2) && name.length() < 100, "图片地址不规范");
			Date startTime =null;
			 Date endTime = null;
	        if (openType == 1) {
				checkNotNull(startDate, "开始时间不能为空");
				checkNotNull(endDate, "结束时间不能为空");
				startTime = DateUtil.formatDateToDate(startDate);
		        endTime =  DateUtil.getTimeChangeHMS(endDate,HOUR,MINUTE,SECOND);
				checkArgument(startTime.before(endTime), "开始时间不能晚于结束时间");
				status = 1;
				isOpen = 1;
			}

			Activity activity = activityService.getActivityById(id);

			checkState(activity != null, "[%s]对应activity不存在", activityId);
			activity.setOperator(loginInfo.getUser_id());
			activity.setActivityName(bgName);
			activity.setActivityShowName(name);
			// activity.setActivityType((byte)type);
			activity.setActionType((byte) clickType);
			activity.setPlatformType((byte) platformType);
			activity.setIsOpen((byte) isOpen);
			activity.setWeight(weightInt);
			activity.setIsShow((byte) isShow);
			activity.setImageUrl(imageUrl2);
			activity.setUpdateTime(now);
			activity.setOpenType((byte) openType);
			activity.setProgramType((byte) programType);
			activity.setBatchIds(batchIds);
			activity.setValue(values * 100);
		
			
			activityService.editActivityWhole(activity ,startTime, endTime, batchIds);

			return _.asMap("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(_.f("修改Activity异常,[userId:%s,type:%s,name:%s,showName:%s,platformType:%s,openType:%s]，error：",
					loginInfo.getUser_id(), addType, bgName, name, addPlatformType, addOpenType), e);
			return _.asMap("status", e.getMessage());
		}
	}

	@RequestMapping("/add")
	@ResponseBody
	public Object doAdd(HttpServletRequest request, HttpServletResponse response, @RequestParam String addType,
			@RequestParam String addClickType, @RequestParam String addPlatformType,
			@RequestParam String addProgramType, @RequestParam String batchIds, @RequestParam String value,
			@RequestParam String addOpenType, @RequestParam String addShowType,
			@RequestParam String bgName, @RequestParam String name, @RequestParam String imageUrl2,
			@RequestParam String weight, @RequestParam String startDate, @RequestParam String endDate)
			throws SQLException {

		User loginInfo = (User) request.getAttribute("user");

		try {

			checkState(loginInfo != null, "请先登录");
			Date now = new Date();
			int type = _.toInt(addType, -1);
			int clickType = _.toInt(addClickType, -1);
			int platformType = _.toInt(addPlatformType, -1);
			int openType = _.toInt(addOpenType, -1);
			int weightInt = _.toInt(weight, 0);
			int status = 0;
			int isOpen = 0;
			int isShow = _.toInt(addShowType, -1);

			int programType = _.toInt(addProgramType, -1);
			long values = _.toLong(value);
	        
			// 检查名称是否存在，可用活动不能出现相同的名称
			Activity activity_temp = activityService.getActivityByBgName(bgName.trim());
			checkArgument(activity_temp == null, "活动名称已存在，请修改活动名称");

			checkArgument((isShow == 0 || isShow == 1), "是否显示类型错误");
			checkArgument((type > 0 || type < 9), "活动类型错误");
			checkArgument((clickType == 0 || clickType == 1 || clickType == 2), "点击活动类型错误");
			checkArgument((platformType > 0 || platformType < 9), "平台类型错误");
			checkArgument((openType == 0 || openType == 1), "活动开启方式错误");
			checkArgument(!_.isEmpty(bgName) && bgName.length() < 100, "活动名称不规范");
			checkArgument(!_.isEmpty(name) && name.length() < 100, "活动后台显示名称不规范");
			checkArgument(!_.isEmpty(imageUrl2) && name.length() < 100, "图片地址不规范");
			if(StringUtils.isNotEmpty(batchIds)) {
				checkArgument(CommonUtil.validateNumComma(batchIds),"优惠券批次号格式不正确");
			}
			
			Date startTime =null;
			 Date endTime = null;
	        if (openType == 1) {
				checkNotNull(startDate, "开始时间不能为空");
				checkNotNull(endDate, "结束时间不能为空");
				startTime = DateUtil.formatDateToDate(startDate);
		        endTime =  DateUtil.getTimeChangeHMS(endDate,HOUR,MINUTE,SECOND);
				checkArgument(startTime.before(endTime), "开始时间不能晚于结束时间");
				status = 1;
				isOpen = 1;
			}
			Activity activity = new Activity();
			activity.setOperator(loginInfo.getUser_id());
			activity.setActivityName(bgName);
			activity.setActivityShowName(name);
			activity.setActivityType((byte) type);
			activity.setActionType((byte) clickType);
			activity.setPlatformType((byte) platformType);
			activity.setOpenType((byte) openType);
			activity.setWeight(weightInt);
			activity.setImageUrl(imageUrl2);
			activity.setIsShow((byte) isShow);
			activity.setIsOpen((byte) isOpen);
			activity.setCreateTime(now);
			activity.setUpdateTime(now);
			activity.setProgramType((byte) programType);
			activity.setBatchIds(batchIds);
			activity.setValue(values * 100);
			activityService.insertActivityWhole(activity, openType ,startTime, endTime, batchIds);
			/*
			LogConstant.mallLog.info("插入Activity[{}]", JSON.toJSONString(activity));
			if (openType == 1) {
				ActivityTime activityTime = new ActivityTime();
				activityTime.setActivityId(activity.getActivityId());
				activityTime.setBeginTime(startTime);
				activityTime.setEndTime(endTime);
				activityTime.setCreateTime(now);
				activityTime.setUpdateTime(now);
				activityTime.setIsdel((byte) 0);
				activityService.insertActivityTime(activityTime);
				LogConstant.mallLog.info("插入ActivityTime[{}]", JSON.toJSONString(activityTime));
			}
			if (batchIds != null && batchIds.trim().length() > 0) {
				// 把coupon_batch的activity_id字段填充
				for (String batchId : batchIds.split(",")) {
					CouponBatch couponBatch = new CouponBatch();
					couponBatch.setBatchId(Long.parseLong(batchId));
					couponBatch.setActivityId(activity.getActivityId());
					couponService.updateCoupon(couponBatch);
				}
			}
			*/

			return _.asMap("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(_.f("增加Activity异常,[userId:%s,type:%s,name:%s,showName:%s,platformType:%s,openType:%s]，error：",
					loginInfo.getUser_id(), addType, bgName, name, addPlatformType, addOpenType), e);
			return _.asMap("status", e.getMessage());
		}

	}
	
	@RequestMapping("/del")
	@ResponseBody
	public Object delAction(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String activityId) throws SQLException {
		User loginInfo = (User) request.getAttribute("user");
		try {
			long activityIdInt = _.toLong(activityId);
			checkArgument(activityIdInt > 0, "错误操作，没有Activity[%s]", activityId);
			int count = activityService.delete(activityIdInt);
			// checkState(count == 1, "删除失败");
			log.info("用户[{}],成功删除CouponRule[{}]", loginInfo.getUser_id(), activityIdInt);
			bannerService.batchDeleteByActivityId(activityIdInt);
			return _.asMap("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(_.f("删除Activity失败，用户[%s],Activity[%s]", loginInfo.getUser_id(), activityId), e);
			return _.asMap("status", e.getMessage());
		}
	}

	@RequestMapping("/open")
	@ResponseBody
	public Object open(HttpServletRequest request, HttpServletResponse response, @RequestParam String activityId,
			@RequestParam String status) throws SQLException {
		User loginInfo = (User) request.getAttribute("user");
		try {
			long activityIdInt = _.toLong(activityId);
			checkArgument(activityIdInt > 0, "错误操作，没有Activity[%s]", activityIdInt);
			int statusInt = _.toInt(status);
			checkArgument(statusInt == 0 || statusInt == 1, "错误状态");
			statusInt = (statusInt == 0) ? 1 : 0;
			int count = activityService.open(activityIdInt, statusInt);
			// checkState(count == 1, "删除失败");
			log.info("用户[{}],成功修改Activity[{}]状态[{}->{}]", loginInfo.getUser_id(), activityIdInt, status,
					statusInt);
			return _.asMap("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(_.f("修改Activity失败，用户[%s],Activity[%s],status[%s]", loginInfo.getUser_id(),
					activityId, status), e);
			return _.asMap("status", e.getMessage());
		}
	}

	@RequestMapping("/getregion")
	@ResponseBody
	public Object couponRuleRegion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String activityId) {

		try {
			long activityIdLong = _.toLong(activityId);
			checkArgument(activityIdLong > 0, "错误操作，没有couponRuleId[%s]", activityId);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkState(user != null, "请先登录");

			/*
			ZtreeBean ztreeBean = ztreeUtil.getCollegeZtree4Activity(
					activityService.getActivityReginsByActivityId(activityIdLong),
					activityService.getActivityCollegeByActivityId(activityIdLong));
			*/
			List<ActivityCollege> activityColleges = activityService.queryActivityCollegeList(activityIdLong);
			List<Long> collegeIdList = Lists.newArrayList();
			if(activityColleges != null) {
				for(ActivityCollege college : activityColleges) {
					collegeIdList.add(college.getCollegeId());
				}
			}
			ZtreeBean ztreeBean = ztreeUtil.getCityAreaCollegeZtree(collegeIdList);
			return buildJson(0, "商品范围查询成功~", ztreeBean);
		} catch (Exception e) {
			log.error("查询活动范围设置发生异常",e);
			return buildErrJson(e.getMessage());// _.asMap("status",
								// e.getMessage());
		}

	}

	@RequestMapping("/setregion")
	@ResponseBody
	public Object couponRuleSetRegion(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String region = request.getParameter("region");
		String couponRuleId = request.getParameter("couponRuleId");

		checkArgument(StringUtils.isNotEmpty(region), "没有选中学校节点");
		checkArgument(StringUtils.isNotEmpty(couponRuleId), "没有活动ID");
		
		try {
			Long activityId = Long.parseLong(couponRuleId);
			activityService.setActivityCollegeRelation(activityId, region, user);
			return buildJson(0, "设置优惠规则区域成功~");
		} catch (Exception e) {
			log.error("更新活动范围设置发生异常",e);
			long userId = -1;
			if (user != null) {
				userId = user.getUser_id();
			}
			LogConstant.mallLog.error(_.f("设置优惠规则区域失败，用户[%s],couponRule[%s]", userId, couponRuleId), e);
			return buildErrJson("设置优惠规则区域失败," + e.getMessage());// _.asMap("status",
										// e.getMessage());
		}
	}

}
