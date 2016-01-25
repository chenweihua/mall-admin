package com.mall.admin.web.activity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityTime;
import com.mall.admin.vo.enums.ActivityPlan;
import com.mall.admin.vo.enums.ActivityPlatform;
import com.mall.admin.vo.enums.ActivityType;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

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
	
	private static final int ACTIVITY_FIRST_INDEX_VALUE = 10;

	private static final Logger log = LogConstant.mallLog;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "0") String type) throws SQLException,
			IOException {
		ModelAndView model = new ModelAndView("activity/listActivity", _.asMap("have_nav", true, "type", type));
		model.addObject("ActivityType", ActivityType.values());
		model.addObject("ActivityPlatform", ActivityPlatform.values());
		model.addObject("ActivityPlan", ActivityPlan.values());
		return model;

	}

	@RequestMapping("/ajaxListData")
	@ResponseBody
	public Object ajaxListData(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam String startDate, @RequestParam String endDate, @RequestParam String name,
			@RequestParam int type, @RequestParam int platformType, @RequestParam int programType,
			@RequestParam int status, @RequestParam int city, @RequestParam int college,@RequestParam int displayType)
			throws SQLException, IOException, ParseException {
		User loginInfo = (User) request.getAttribute("user");
		try {
			long total = 0;
			Date startTime = _.toDate(startDate);
			Date endTime = _.toDate(endDate);
			Pair<Integer, List<Activity>> datas = activityService.getActivityList(startTime, endTime, name,
					type, platformType, programType, status, city, college, displayType, start, numPerPage);// .getCouponRules(typeInt,
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
		
		List<City> cityList = Lists.newArrayList(); 
		List<District> districtList = cityService.getDistrictList();
		for(District district: districtList) {
			cityList.add(district.getCity());
		}
		CityConstant.getcityMap().values();
		Map<Long, List<College>> collegeMap = Maps.newHashMap();
		for (City city : cityList) {
			collegeMap.put(city.getCityId(), CollegeConstant.getCollegesByCityId(city.getCityId()));
		}
		result.put("citiList", cityList);
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
			@RequestParam String imageUrl2, @RequestParam(required = false) String wapImageUrl, @RequestParam String weight, @RequestParam String startDate,
			@RequestParam String endDate,@RequestParam(required = false) String displayType,@RequestParam(required = false) String labelType,
			@RequestParam(required = false) String isLinkUrl,@RequestParam(required = false) String linkUrl,
			@RequestParam(defaultValue = "0") int isOpen) throws SQLException {
		
		User loginInfo = (User) request.getAttribute("user");

		try {

			checkState(loginInfo != null, "请先登录");
			Date now = new Date();
			int id = _.toInt(activityId, -1);
			int type = _.toInt(addType, -1);
			int clickType = _.toInt(addClickType, -1);
			int platformType = _.toInt(addPlatformType, -1);
			int displayTypeInt = _.toInt(displayType, -1);
			int labelTypeInt = _.toInt(labelType, -1);
			int isLinkUrlInt = _.toInt(isLinkUrl, -1);
			int openType = _.toInt(addOpenType, -1);
			int weightInt = _.toInt(weight, 0);
			int status = 0;
//			int isOpen = 0;
			int isShow = _.toInt(addShowType, -1);
			int programType = _.toInt(addProgramType, -1);
			long values = _.toLong(value);
			Date startTime = _.toDate(startDate);
			Date endTime = _.toDate(endDate);
			//
			checkArgument((isOpen == 0 || isOpen == 1), "是否开启状态类型错误");
			checkArgument((isShow == 0 || isShow == 1), "是否显示类型错误");
			checkArgument(id > 0, "活动id类型错误");
			checkArgument((type > 0 || type < 9), "活动类型错误");
			checkArgument((clickType > 0 || clickType < 9), "点击活动类型错误");
			checkArgument((platformType > 0 || platformType < 9), "平台类型错误");
			checkArgument((displayTypeInt != -1), "展示方式类型错误");
			checkArgument((labelTypeInt != -1), "选择标签类型错误");
			checkArgument((isLinkUrlInt != -1), "是否支持链接类型错误");
			checkArgument((openType == 0 || openType == 1), "活动开启方式错误");
			checkArgument(!_.isEmpty(bgName) && bgName.length() < 100, "活动名称不规范");
			checkArgument(!_.isEmpty(name) && name.length() < 100, "活动后台显示名称不规范");
			
			if (openType == 1) {
				checkNotNull(startTime, "活动置为手动开始时，需要设置开始时间");
				checkNotNull(endTime, "活动置为手动开始时，需要设置结束");
				checkArgument(startTime.before(endTime), "活动置为手动开始时，开始时间不能晚于结束时间");
				status = 1;
//				isOpen = 1;
			}

			Activity activity = activityService.getActivityById(id);

			checkState(activity != null, "[%s]对应activity不存在", activityId);
			activity.setOperator(loginInfo.getUser_id());
			activity.setActivityName(bgName);
			activity.setActivityShowName(name);
			// activity.setActivityType((byte)type);
			activity.setActionType((byte) clickType);
			activity.setPlatformType((byte) platformType);
			if(type == Activity.ACTIVITY_TYPE_RECOMMEND) {
				activity.setFirstIndex(ACTIVITY_FIRST_INDEX_VALUE);
			}
			activity.setDisplayType(displayTypeInt);
			activity.setLabelType(labelTypeInt);
			activity.setIsLinkUrl(isLinkUrlInt);
			activity.setLinkUrl(linkUrl);
			activity.setIsOpen((byte) isOpen);
			activity.setWeight(weightInt);
			activity.setIsShow((byte) isShow);
			activity.setImageUrl(imageUrl2);
			activity.setWapImageUrl(wapImageUrl);
			activity.setUpdateTime(now);
			activity.setOpenType((byte) openType);
			activity.setProgramType((byte) programType);
			activity.setBatchIds(batchIds);
			activity.setValue(values * 100);
			if (activity.getOpenType() == 1) {
				activityService.delActivityTimeByActivityId(id);
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
			long count = activityService.updateActivity(activity);

			checkState(count == 1, "系统错误,修改失败");
			
			//删除原activityId标记
			couponService.clearCouponBatchActivityId(new Long(id));
			

			// 把coupon_batch的activity_id字段填充
			if (batchIds != null && batchIds.trim().length() > 0) {
				for (String batchId : batchIds.split(",")) {
					CouponBatch couponBatch = new CouponBatch();
					couponBatch.setBatchId(Long.parseLong(batchId));
					couponBatch.setActivityId(activity.getActivityId());
					couponService.updateCoupon(couponBatch);
				}
			}

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
			@RequestParam String bgName, @RequestParam String name, @RequestParam String imageUrl2,@RequestParam(required = false) String wapImageUrl,
			@RequestParam String weight, @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) String displayType,@RequestParam(required = false) String labelType,
			@RequestParam(required = false) String isLinkUrl,@RequestParam(required = false) String linkUrl)
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
			int displayTypeInt = _.toInt(displayType, -1);
			int labelTypeInt = _.toInt(labelType, -1);
			int isLinkUrlInt = _.toInt(isLinkUrl, -1);

			int programType = _.toInt(addProgramType, -1);
			long values = _.toLong(value);

			Date startTime = _.toDate(startDate);
			Date endTime = _.toDate(endDate);
			// 检查名称是否存在，可用活动不能出现相同的名称
			Activity activity_temp = activityService.getActivityByBgName(bgName.trim());
			checkArgument(activity_temp == null, "活动名称已存在，请修改活动名称");

			checkArgument((isShow == 0 || isShow == 1), "是否显示类型错误");
			checkArgument((type > 0 || type < 9), "活动类型错误");
			checkArgument((clickType == 0 || clickType == 1 || clickType == 2), "点击活动类型错误");
			checkArgument((platformType > 0 || platformType < 9), "平台类型错误");
			checkArgument((displayTypeInt != -1), "展示方式类型错误");
			checkArgument((labelTypeInt != -1), "选择标签类型错误");
			checkArgument((isLinkUrlInt != -1), "是否支持链接类型错误");
			checkArgument((openType == 0 || openType == 1), "活动开启方式错误");
			checkArgument(!_.isEmpty(bgName) && bgName.length() < 100, "活动名称不规范");
			checkArgument(!_.isEmpty(name) && name.length() < 100, "活动后台显示名称不规范");
			
			if(StringUtils.isNotEmpty(batchIds)) {
				checkArgument(CommonUtil.validateNumComma(batchIds),"优惠券批次号格式不正确");
			}
			if (openType == 1) {
				checkNotNull(startTime, "活动置为手动开始时，需要设置开始时间");
				checkNotNull(endTime, "活动置为手动开始时，需要设置结束");
				checkArgument(startTime.before(endTime), "活动置为手动开始时，开始时间不能晚于结束时间");
				status = 1;
				isOpen = 1;
			}
			
			if(type == Activity.ACTIVITY_TYPE_RECOMMEND) {//系统推荐只能添加一次
				List<Activity> activityList = activityService.getActivityByActivityType(type);
				checkArgument(CollectionUtils.isEmpty(activityList), "系统推荐活动已经存在，只能创建一个");
			}

			Activity activity = new Activity();
			activity.setOperator(loginInfo.getUser_id());
			activity.setActivityName(bgName);
			activity.setActivityShowName(name);
			activity.setActivityType((byte) type);
			activity.setActionType((byte) clickType);
			activity.setPlatformType((byte) platformType);
			if(type == Activity.ACTIVITY_TYPE_RECOMMEND) {
				activity.setFirstIndex(ACTIVITY_FIRST_INDEX_VALUE);
			}
			activity.setDisplayType(displayTypeInt);
			activity.setLabelType(labelTypeInt);
			activity.setIsLinkUrl(isLinkUrlInt);
			activity.setLinkUrl(linkUrl);
			activity.setOpenType((byte) openType);
			activity.setWeight(weightInt);
			activity.setImageUrl(imageUrl2);
			activity.setWapImageUrl(wapImageUrl);
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

			ZtreeBean ztreeBean = ztreeUtil.getCollegeZtree4Activity(
					activityService.getActivityReginsByActivityId(activityIdLong),
					activityService.getActivityCollegeByActivityId(activityIdLong));
			return buildJson(0, "商品范围查询成功~", ztreeBean);
		} catch (Exception e) {
			e.printStackTrace();
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

		try {
			List<ZtreeBean> bean = gson.fromJson(region, new TypeToken<List<ZtreeBean>>() {
			}.getType());
			checkArgument(bean != null && bean.size() > 0, "获取范围信息为空~");
			int couponRuleIdInt = Integer.parseInt(couponRuleId);
			ZtreeBean ztreeBean = bean.get(0);
			activityService.setActivityRegion(ztreeBean, couponRuleIdInt, user);
			return buildJson(0, "设置优惠规则区域成功~");
		} catch (Exception e) {
			e.printStackTrace();
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
