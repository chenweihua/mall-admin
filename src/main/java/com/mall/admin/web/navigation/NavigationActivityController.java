package com.mall.admin.web.navigation;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.NavigationMenuConstant;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.navigation.NavigationActivityService;
import com.mall.admin.util._;
import com.mall.admin.vo.navigation.NavigationActivity;
import com.mall.admin.vo.navigation.NavigationActivityType;
import com.mall.admin.vo.navigation.NavigationMenu;
import com.mall.admin.vo.user.User;


@Controller
@RequestMapping("/navigationActivity")
public class NavigationActivityController extends BaseController {
	
	private static final Logger log = LogConstant.mallLog;
	
	@Autowired
	private NavigationActivityService navigationActivityService;
	
	@Autowired
	private NavigationMenuConstant navMenuConstant;
	
	
	
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("navigation/navigationActivityList");
		mav.addObject("NavigationActivityType", NavigationActivityType.values());
		List<NavigationMenu> menus = navMenuConstant.getNavMenuByMenuType(NavigationMenu.MENU_TYPE_ACTIVITY);
		mav.addObject("menus", menus);
		return mav;
	}
	
	
	
	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response,
		@RequestParam int draw, @RequestParam int start,@RequestParam(value = "length") int numPerPage,
		Long navMenuId,
		String activityName,
		String activityType,
		String status
		) {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String,Object> param = Maps.newHashMap();
		param.put("navMenuId",navMenuId);
		param.put("activityName",activityName);
		param.put("activityType",activityType);
		param.put("status",status);
		param.put("isDel", 0);
		
		List<NavigationActivity> navigationActivityList = navigationActivityService.getNavigationActivityList(param, paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), navigationActivityList));
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false) Long navActivityId,
			 @RequestParam(required=false)Integer navMenuId
			 ,@RequestParam(required=false)Integer activityType
			 ,@RequestParam(required=false)String activityName
			 ,@RequestParam(required=false)String activityUrl
			 ,@RequestParam(required=false)Integer weight
			 ,@RequestParam(required=false)String remark
			 ,@RequestParam(required=false)String imageUrl
			 ,@RequestParam(required=false)Integer openType
			 ,@RequestParam(required=false)Integer status
			 ,@RequestParam(required=false)String beginTime
			 ,@RequestParam(required=false)String endTime
		) {

		try {
			//检查入参
			//checkArgument(!_.isEmpty() && name.length() <= 100, "某某字段不能为空或者超过100字符");
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			
			Date startDate = _.toDate(beginTime);
			Date endDate = _.toDate(endTime);
			
			NavigationActivity navigationActivity = new NavigationActivity();
			navigationActivity.setNavMenuId(navMenuId);
			navigationActivity.setActivityType(activityType);
			navigationActivity.setActivityName(activityName);
			navigationActivity.setActivityUrl(activityUrl);
			navigationActivity.setWeight(weight);
			navigationActivity.setRemark(remark);
			navigationActivity.setImageUrl(imageUrl);
			navigationActivity.setOpenType(openType);
			navigationActivity.setStatus(status);
			navigationActivity.setCreateTime(new Date());
			navigationActivity.setCreator(user.getUser_id());
			navigationActivity.setOperator(user.getUser_id());
			navigationActivity.setIsDel(0);
			navigationActivity.setBeginTime(startDate);
			navigationActivity.setEndTime(endDate);
			
			int affectNum = 0;
			if(navActivityId == null) {  // 添加
				GroupSequence groupSequence = new GroupSequence();
				navigationActivity.setNavActivityId(groupSequence.nextValue());
				navigationActivity.setCreateTime(new Date());
				navigationActivity.setUpdateTime(new Date());
				affectNum = navigationActivityService.addNavigationActivity(navigationActivity);
			} else {  //修改
				navigationActivity.setNavActivityId(navActivityId);
				navigationActivity.setUpdateTime(new Date());
				affectNum = navigationActivityService.updateNavigationActivity(navigationActivity);
			}
			if(affectNum == 0) {
				return buildJson(1, "操作失败~");
			}
			return buildJson(0, "操作成功~");
		} catch (Exception e) {
			log.error("insert navigationActivity：", e);
			return buildJson(1, "操作失败：" + e.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false) Long navActivityId) {
		
		try {
			checkArgument(navActivityId != null,"navActivityId不能为空");
			
			NavigationActivity navigationActivity = new NavigationActivity();
			navigationActivity.setNavActivityId(navActivityId);
			navigationActivity.setIsDel(1);
			int affectNum = navigationActivityService.updateNavigationActivity(navigationActivity);
			if(affectNum == 0) {
				return buildJson(1, "操作失败~");
			} else {
				return buildJson(0, "操作成功~");
			}
		} catch(Exception ex) {
			log.error("insert navigationActivity：", ex);
			return buildJson(1, "操作失败：" + ex.getMessage());
		}
	}
	

}
