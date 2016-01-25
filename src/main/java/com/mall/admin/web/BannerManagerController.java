package com.mall.admin.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.BannerHrefUrlConstant;
import com.mall.admin.constant.CategoryConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.enumdata.BannerTypeEnum;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.banner.BannerCollegeService;
import com.mall.admin.service.banner.BannerService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.navigation.NavigationService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.util.StreamUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.banner.Banner;
import com.mall.admin.vo.base.SelectDto;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.user.User;

/** 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/banner/")
public class BannerManagerController extends BaseController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ActivityService activityService;
	@Autowired
	BannerService bannerService;
	@Autowired
	ZtreeUtil ztreeUtil;
	@Autowired
	BannerCollegeService bannerCollegeService;
	@Autowired
	CityService cityService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	private NavigationService navigationService;
	
	@RequestMapping("list")
	public Object toList(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/banner/list", "跳转到广告管理界面...");
		//城市
		List<City> cities = new ArrayList<City>();
		List<City> cityTemp = cityService.getCityListByPid(0);
		for(City city : cityTemp){
			if(city.getIsDel() == 0){
				cities.add(city);
			}
		}
		City defaultCity = new City();
		if(cities != null && cities.size() > 0){
			defaultCity = cities.get(0);
		}
		//学校
		List<College> colleges = collegeService.getListByCityId(defaultCity.getCityId());
		
		return new ModelAndView("banner/listBanner",_.asMap("cities",cities,"colleges",colleges));
	}
	
	@ResponseBody
	@RequestMapping("query")
	public Object query(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage) throws SQLException, IOException {
		logInputData(request, "/banner/query", "查询Banner...");
	
		/** bannerType 0全部；1wap;2app */
		String bannerTypeStr = request.getParameter("bannerType");
		/** 城市id */
		String cityIdStr = request.getParameter("cityId");
		/* 学校id */
		String collegeId = request.getParameter("collegeId");
		/** banner名称 */
		String bannerName = request.getParameter("searchStr");

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String, Object> param = new HashMap<>();
		if (!Strings.isEmpty(bannerTypeStr) && bannerTypeStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(bannerTypeStr) != 0) {
				param.put("bannerType", Long.parseLong(bannerTypeStr));
			} 
		}
		if (!Strings.isEmpty(bannerName)) {
			param.put("bannerName", _.trimToNull(bannerName));
		}
		if (!Strings.isEmpty(cityIdStr) && cityIdStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(cityIdStr) != 0) {
				param.put("cityId", Long.parseLong(cityIdStr));
			}
		}
		if (!Strings.isEmpty(collegeId) && collegeId.matches("[0-9]{1,}")) {
			if (Long.parseLong(collegeId) != 0) {
				param.put("collegeId", Long.parseLong(collegeId));
			}
		}
		List<Banner> bannerList = bannerService.queryBannerByPage(param, paginationInfo);
		return buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), bannerList);
	}

	@RequestMapping("edit/page")
	public Object toEdit(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/banner/edit", "跳转到广告添加界面...");
		//判断编辑/新加入
		String bannerIdStr = request.getParameter("bannerId");
		if (bannerIdStr != null && bannerIdStr.matches("[0-9]{1,}")) {
			long bannerId = _.toLong(bannerIdStr);
			Banner banner = bannerService.getById(bannerId);
			request.setAttribute("banner", banner);
			parseActionTypeAndHref(banner, request);
		}else{
			request.setAttribute("hrefType", "score");
			request.setAttribute("mallModelList", 
					BannerHrefUrlConstant.generateMallModelList(BannerTypeEnum.BANNER_HREF_TYPE_SCORE));
		}
		return "banner/editBanner";
	}
	
	
	@ResponseBody
	@RequestMapping("edit")
	public Object edit(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		User user = (User)request.getAttribute("user");
		logInputData(request, "/banner/edit", "跳转到广告添加界面...");
		Banner banner = RequestUtil.toBean(request, Banner.class);
		if(banner.getConnectId() == -1){
			banner.setConnectId(Banner.BANNER_POSITION_HOME);
		}
		if(banner.getImageUrl().equals("") || banner.getImageUrl() == null){
			return buildJson(-1, "广告必须上传图片");
		}
		BufferedImage image = StreamUtil.getBufferedImage(banner.getImageUrl()); 
        if (image == null) { 
        	return buildJson(-1, "解析图片失败");
        }
        //设置banner图片高度
        banner.setBannerHeight(image.getHeight());
        banner.setHeigthWidthRatio(String.format("%.2f",(double)image.getHeight() / image.getWidth()));
		if(banner.getWeight() < 0){
			return buildJson(-1, "广告权重必须为数字,且为正数");
		}
		//设置跳转类型和跳转链接
		Object ret = setActionTypeAndHref(banner, request);
		if(ret != null){
			return ret;
		}
		String startTimeStr = request.getParameter("startTime");
		Timestamp startTime = _.parseTimeStr(startTimeStr);
		if(startTime == null){
			return buildJson(-1, "投放时间格式错误，正确格式yyyy-MM-dd HH:mm:ss");
		}
		banner.setStartTime(startTime);
		String endTimeStr = request.getParameter("endTime");
		Timestamp endTime = _.parseTimeStr(endTimeStr);
		if(endTime == null){
			return buildJson(-1, "投放时间格式错误，正确格式yyyy-MM-dd HH:mm:ss");
		}
		//结束时间要晚于开始时间
		if(!endTime.after(startTime)){
			return buildJson(-1, "结束时间必须晚于开始时间！");
		} 
		banner.setEndTime(endTime);
		banner.setIsDel(0);
		banner.setOperator(user.getUser_id());
		long bannerId = bannerService.insertObject(banner);
		return buildJson(0,"添加成功",bannerId);
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "banner/delete", "删除banner信息...");
		String bannerIdStr = request.getParameter("bannerId");

		if (bannerIdStr == null || !bannerIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("BannerID错误，id是：" + bannerIdStr);
			return buildJson(1, "BannerID错误，请联系管理员~");
		}
		long bannerId = _.toLong(bannerIdStr);
		int di = bannerService.deleteById(bannerId);
		if(di < 0){
			return buildJson(-1, "banner删除失败！");
		}else{
			return buildJson(0, "删除成功！");
		}
	}
	@ResponseBody
	@RequestMapping("getRegion/byBannerId")
	public Object getRegion(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		
		logInputData(request, "category/all", "获取区域信息...");
		String bannerIdStr = request.getParameter("bannerId");

		if (bannerIdStr == null || !bannerIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("BannerID错误，id是：" + bannerIdStr);
			return buildJson(1, "BannerID错误，请联系管理员~");
		}
		long bannerId = _.toLong(bannerIdStr);
		ZtreeBean ztreeBean = ztreeUtil.getCityCollegeZtree();
		bannerService.setValue4Region(ztreeBean, bannerId);
		return buildSuccJson(ztreeBean);
	}
	
	@ResponseBody
	@RequestMapping("setRegion/byBannerId")
	public Object setRegion(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {

		logInputData(request, "category/all", "设置区域信息...");
		
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		String bannerIdStr = request.getParameter("bannerId");

		if (bannerIdStr == null || !bannerIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("BannerID错误，id是：" + bannerIdStr);
			return buildJson(1, "BannerID错误，请联系管理员~");
		}
		long bannerId = _.toLong(bannerIdStr);
		String treeInfo = request.getParameter("treeInfo");
		List<ZtreeBean> bean = gson.fromJson(treeInfo,
				new TypeToken<List<ZtreeBean>>() {
				}.getType());

		if (bean == null || bean.size() == 0) {
			return buildJson(1, "获取范围信息为空~");
		}
		ZtreeBean ztreeBean = bean.get(0);
		Map<String, Object> result = bannerService.setRegion(ztreeBean, bannerId,user);
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping("activity/all")
	public Object getActivity(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "activity/all", "获取所有活动...");
		List<Activity> activities = activityService.getActivityList(null);
		return buildSuccJson(activities);
	}
	
	@ResponseBody
	@RequestMapping("category/all")
	public Object getCategory(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "category/all", "获取所有类目...");
		List<Category> categories = categoryService.getAllCategories();
		return buildSuccJson(categories);
	}
	
	@ResponseBody
	@RequestMapping("model/all")
	public Object getModel(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "model/all", "获取商城所有模块...");
		List<SelectDto> modelList = BannerHrefUrlConstant.generateMallModelList(BannerTypeEnum.BANNER_HREF_TYPE_SCORE);
		return buildSuccJson(modelList);
	}
	
	@ResponseBody
	@RequestMapping("mallPage/all")
	public Object getNavigation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Integer type) throws SQLException, IOException {
		logInputData(request, "navigation/all", "获取所有发现导航...");
		BannerTypeEnum tempEnum = BannerTypeEnum.getEnum(type);
		List<SelectDto> modelList = new ArrayList<>();
		switch (tempEnum) {
		case BANNER_HREF_TYPE_DISCOVERY:
			List<Navigation> navigations = navigationService.getNavigationList();
			for(Navigation n : navigations){
				modelList.add(new SelectDto(n.getNavigationId(), n.getNavigationName(), false));
			}
			return buildSuccJson(modelList);
		default:
			return buildSuccJson(modelList);
		}
	}
	
	@ResponseBody
	@RequestMapping("city/byCityId")
	public Object getCityCollege(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String bannerIdStr = request.getParameter("cityId");

		if (bannerIdStr == null || !bannerIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("BannerID错误，id是：" + bannerIdStr);
			return buildJson(1, "cityID错误，请联系管理员~");
		}
		long cityId = _.toLong(bannerIdStr);
		List<College> collegeList = collegeService.getListByCityId(cityId);
		return buildSuccJson(collegeList);
	}
	
	private Object setActionTypeAndHref(Banner banner,HttpServletRequest request){
		//处理跳转链接
		String hrefType = request.getParameter("hrefType");
		BannerTypeEnum hrefTypeEnum = BannerTypeEnum.getEnum(hrefType);
		if(hrefTypeEnum == null){
			return buildJson(-1, "系统错误，请联系管理员");
		}
		//设置跳转类型
		banner.setActionType(hrefTypeEnum.getValue());
		String newHrefUrl = "";
		//设置hrefUrl(主要兼容老版本)和newHrefUrl
		switch (hrefTypeEnum) {
		case BANNER_HREF_TYPE_ACTIVITY:
			long activityId = NumberUtils.toLong(request.getParameter("hrefActivityId"),-1L);
			Activity activity = activityService.getActivityById(activityId);
			//设置newHrefUrl
			if(activity != null){
				newHrefUrl = BannerHrefUrlConstant.generateActivityUrl(hrefTypeEnum.getName(), activityId, 
						(int)activity.getActivityType(), activity.getActivityName());
				banner.setNewHrefUrl(newHrefUrl);
				//兼容老版本
				switch (activity.getActivityType()) {
				case Activity.ACTIVITY_TYPE_SECKILL:
					banner.setHrefUrl(Constants.ACTIVITY_SECKILL_FLAG+activity.getActivityId());
					break;
				case Activity.ACTIVITY_TYPE_NORMAL:
					banner.setHrefUrl(Constants.ACTIVITY_NORMAL_FLAG+activity.getActivityId());
					break;
				default:
					return buildJson(-1, "活动类型错误，请联系管理员！");
				}
			}
			break;
		case BANNER_HREF_TYPE_CATEGORY:
			long categoryId = NumberUtils.toLong(request.getParameter("hrefCategoryId"),-1L);
			Category category = CategoryConstant.getCategoryById(categoryId);
			if(category != null){
				newHrefUrl = BannerHrefUrlConstant.generateCategoryUrl(hrefTypeEnum.getName(), categoryId,category.getCategoryName());
			}
			banner.setNewHrefUrl(newHrefUrl);
			//兼容老版本
			banner.setHrefUrl(Constants.CATEGORY_FLAG+categoryId);
			break;
		case BANNER_HREF_TYPE_WEBVIEW:
			String webViewName = request.getParameter("webViewName").trim();
			String webViewUrl = request.getParameter("webViewUrl").trim();
			if(StringUtils.isEmpty(webViewName) || StringUtils.isEmpty(webViewUrl)){
				return buildJson(-1, "链接标题和地址不能为空！");
			}
			newHrefUrl = BannerHrefUrlConstant.generateWebViewUrl(hrefTypeEnum.getName(), banner.getNeedLogin(), webViewUrl, webViewName);
			banner.setNewHrefUrl(newHrefUrl);
			//兼容老版本
			banner.setHrefUrl(webViewUrl);
			break;
		case BANNER_HREF_TYPE_GOODSDETAIL:
			
			break;
		case BANNER_HREF_TYPE_DISCOVERY:
			long discoveryId = NumberUtils.toLong(request.getParameter("mallPage"),-1L);
			newHrefUrl = BannerHrefUrlConstant.generateDiscoveryUrl(hrefTypeEnum.getName(), discoveryId);
			banner.setNewHrefUrl(newHrefUrl);
			break;
		case BANNER_HREF_TYPE_SCORE:
			newHrefUrl = BannerHrefUrlConstant.generateScoreUrl(hrefTypeEnum.getName());
			banner.setNewHrefUrl(newHrefUrl);
			break;
		case BANNER_HREF_TYPE_USERCENTER:
			newHrefUrl = BannerHrefUrlConstant.generateUsercenterUrl(hrefTypeEnum.getName());
			banner.setNewHrefUrl(newHrefUrl);
			break;
		default:
			return buildJson(-1, "Banner类型错误，请联系管理员！");
		}
		return null;
		
		
		/*
		//老版本
		String hrefUrl = request.getParameter("hrefUrl"+banner.getActionType());
		switch (banner.getActionType()) {
		case Banner.ACTION_TYPE_ACTIVITY:
		    Activity activity = activityService.getActivityById(_.toLong(_.trimToEmpty(hrefUrl)));
		    if(activity != null){
		    	switch (activity.getActivityType()) {
		    	case Activity.ACTIVITY_TYPE_SECKILL:
		    		banner.setHrefUrl(Constants.ACTIVITY_SECKILL_FLAG+activity.getActivityId());
		    		break;
				case Activity.ACTIVITY_TYPE_NORMAL:
					banner.setHrefUrl(Constants.ACTIVITY_NORMAL_FLAG+activity.getActivityId());
					break;
				default:
					return buildJson(-1, "活动类型错误，请联系管理员！");
				}
		    }
			break;
		case Banner.ACTION_TYPE_CATEGORY:
			banner.setHrefUrl(Constants.CATEGORY_FLAG+_.trimToEmpty(hrefUrl));
			break;
		case Banner.ACTION_TYPE_URL:
			banner.setHrefUrl(hrefUrl);
			break;
		default:
			return buildJson(-1, "Banner跳转类型错误，请联系管理员！");
		}*/
	}
	
	private Object parseActionTypeAndHref(Banner banner,HttpServletRequest request){
		BannerTypeEnum hrefTypeEnum = BannerTypeEnum.getEnum(banner.getActionType());
		if(hrefTypeEnum == null){
			return buildJson(-1, "系统错误，请联系管理员");
		}
		//设置跳转类型
		request.setAttribute("hrefType", hrefTypeEnum.getName());
		switch (hrefTypeEnum) {
		case BANNER_HREF_TYPE_ACTIVITY:
			List<Activity> activities = activityService.getActivityList(null);
			List<SelectDto> activityList = new ArrayList<>();
			for(Activity a : activities){
				if(a.getActivityId() == NumberUtils.toLong(banner.getHrefUrl())){
					activityList.add(new SelectDto(a.getActivityId(),a.getActivityName(), true));
				}else{
					activityList.add(new SelectDto(a.getActivityId(),a.getActivityName(), false));
				}
			}
			request.setAttribute("activityList", activityList);
			break;
		case BANNER_HREF_TYPE_CATEGORY:
			List<Category> categories = categoryService.getAllCategories();
			List<SelectDto> categoryList = new ArrayList<>();
			for(Category c : categories){
				if(c.getCategoryId() == NumberUtils.toLong(banner.getHrefUrl())){
					categoryList.add(new SelectDto(c.getCategoryId(), c.getCategoryName(), true));
				}else{
					categoryList.add(new SelectDto(c.getCategoryId(), c.getCategoryName(), false));
				}
			}
			request.setAttribute("categoryList", categoryList);
			break;
		case BANNER_HREF_TYPE_WEBVIEW:
			Map<String, String> params = BannerHrefUrlConstant.parseWebview(banner.getNewHrefUrl());
			if(params == null){
				request.setAttribute("webViewName", "");
				request.setAttribute("webViewUrl", banner.getHrefUrl());
			}else{
				request.setAttribute("webViewName", params.get(BannerHrefUrlConstant.WEBVIEW_NAME));
				request.setAttribute("webViewUrl", params.get(BannerHrefUrlConstant.WEBVIEW_ADDRESS));
			}
			break;
		case BANNER_HREF_TYPE_GOODSDETAIL:
			
			break;
		case BANNER_HREF_TYPE_DISCOVERY:
			//默认商城模块
			request.setAttribute("mallModelList", 
					BannerHrefUrlConstant.generateMallModelList(hrefTypeEnum));
			List<Navigation> navigations = navigationService.getNavigationList();
			List<SelectDto> mallPageList = new ArrayList<>();
			for(Navigation n : navigations){
				if(n.getNavigationId() == NumberUtils.toLong(banner.getHrefUrl())){
					mallPageList.add(new SelectDto(n.getNavigationId(), n.getNavigationName(), true));
				}else{
					mallPageList.add(new SelectDto(n.getNavigationId(), n.getNavigationName(), false));
				}
			}
			request.setAttribute("mallPageList", mallPageList);
			break;
		case BANNER_HREF_TYPE_SCORE:
			request.setAttribute("mallModelList", 
					BannerHrefUrlConstant.generateMallModelList(hrefTypeEnum));
			break;
		case BANNER_HREF_TYPE_USERCENTER:
			request.setAttribute("mallModelList", 
					BannerHrefUrlConstant.generateMallModelList(hrefTypeEnum));
			break;
		default:
			return buildJson(-1, "Banner类型错误，请联系管理员！");
		}
		return null;
	}
	
	
}
