package com.mall.admin.web.navigation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.NavigationMenuConstant;
import com.mall.admin.enumdata.NavMenuType;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.navigation.NavigationGoodsService;
import com.mall.admin.service.navigation.NavigationMenuService;
import com.mall.admin.service.navigation.NavigationService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.navigation.NavigationCollege;
import com.mall.admin.vo.navigation.NavigationMenu;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/navigation")
public class NavigationController extends BaseController {

	private static final Logger log = LogConstant.mallLog;

	@Autowired
	private NavigationService navigationService;

	@Autowired
	private NavigationMenuService navigationMenuService;

	@Autowired
	private CityService cityService;

	@Autowired
	private ZtreeUtil ztreeUtil;

	@Autowired
	private NavigationGoodsService nvaGoodsService;

	@Autowired
	private NavigationMenuConstant menuConstant;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("navigation/list");
		return mav;
	}

	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage, String navigationName,
			String status) {

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String, Object> param = Maps.newHashMap();
		param.put("navigationName", navigationName);
		param.put("status", status);

		List<Navigation> navigationList = navigationService.getNavigationList(param, paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), navigationList));
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Long navigationId,
			@RequestParam(required = false) String navigationName,
			@RequestParam(required = false) String navigationDesc,
			@RequestParam(required = false) String navigationUrl,
			@RequestParam(required = false) String insideUrl, @RequestParam(required = false) int weight,
			@RequestParam(required = false) int status,
			@RequestParam(required = false) Integer navigationType,
			@RequestParam(required = true) int isShow) {

		try {

			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkArgument(user != null, "获取操作员信息为空！");
			// 检查入参
			// checkArgument(!_.isEmpty() && name.length() <= 100,
			// "某某字段不能为空或者超过100字符");

			Navigation navigation = new Navigation();
			navigation.setNavigationName(navigationName);
			navigation.setNavigationDesc(navigationDesc);
			navigation.setNavigationUrl(navigationUrl);
			navigation.setInsideUrl(insideUrl);
			navigation.setWeight(weight);
			navigation.setStatus(status);
			navigation.setIsShow(isShow);

			int affectNum = 0;
			if (navigationId == null) { // 添加
				navigation.setNavigationType(navigationType);
				GroupSequence groupSequence = new GroupSequence();
				navigation.setNavigationId(groupSequence.nextValue());
				navigation.setCreator(user.getUser_id());
				navigation.setOperator(user.getUser_id());
				affectNum = navigationService.addNavigation(navigation);
			} else { // 修改
				navigation.setNavigationId(navigationId);
				navigation.setOperator(user.getUser_id());
				affectNum = navigationService.updateNavigation(navigation);
			}
			if (affectNum == 0) {
				return buildJson(1, "操作失败~");
			}
			return buildJson(0, "操作成功~");
		} catch (Exception e) {
			log.error("insert navigation：", e);
			return buildJson(1, "操作失败：" + e.getMessage());
		}
	}

	@RequestMapping("/getCityCollege")
	@ResponseBody
	public Object getCityCollege(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();

		List<City> cityList = Lists.newArrayList();
		List<District> districtList = cityService.getDistrictList();
		for (District district : districtList) {
			cityList.add(district.getCity());
		}
		Map<Long, List<College>> collegeMap = Maps.newHashMap();
		for (City city : cityList) {
			collegeMap.put(city.getCityId(), CollegeConstant.getCollegesByCityId(city.getCityId()));
		}
		result.put("citiList", cityList);
		result.put("collegeMap", collegeMap);
		return result;
	}

	@RequestMapping("/getNavigationCollege")
	@ResponseBody
	public Object getNavigationCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String navigationId) {

		try {
			long navigationIdLong = _.toLong(navigationId);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkState(user != null, "请先登录");

			List<NavigationCollege> navigationColleges = navigationService
					.getNavigationCollegeByNavigationId(navigationIdLong);
			List<Long> collegeIds = new ArrayList<Long>();
			for (NavigationCollege navigationCollege : navigationColleges) {
				collegeIds.add(navigationCollege.getCollegeId());
			}

			ZtreeBean ztreeBean = ztreeUtil.getCityAreaCollegeZtreeWithoutEmptyCity(collegeIds);
			return buildJson(0, "导航与学校关联查询成功", ztreeBean);
		} catch (Exception e) {
			log.error("导航与学校关联查询发生异常", e);
			return buildErrJson(e.getMessage());
		}

	}

	@RequestMapping("/setNavigationCollege")
	@ResponseBody
	public Object setNavigationCollege(HttpServletRequest request, HttpServletResponse response) {
		String collegeIdStr = request.getParameter("collegeIds");
		String navigationIdStr = request.getParameter("navigationId");

		checkArgument(StringUtils.isNotEmpty(collegeIdStr), "没有选中学校节点");
		checkArgument(StringUtils.isNotEmpty(navigationIdStr), "没有导航ID");

		try {
			User loginInfo = (User) request.getAttribute("user");
			String account = loginInfo.getAccount();
			Long navigationIdS = Long.parseLong(navigationIdStr);
			String[] collegeIdArr = collegeIdStr.split(",");
			// List<Long> collegeIds =
			// Lists.transform(Arrays.asList(collegeIdArr), s ->
			// Long.parseLong(s));
			List<Long> collegeIds = new ArrayList<Long>();
			for (String collegeIdStrEach : collegeIdArr) {
				collegeIds.add(Long.parseLong(collegeIdStrEach));
			}
			navigationService.updateNavigationColleges(navigationIdS, collegeIds);
			return buildJson(0, "设置导航学校关联操作成功~");
		} catch (Exception e) {
			log.error("设置导航学校关联操作发生异常", e);
			LogConstant.mallLog.error(_.f("设置导航学校关联操作失败，导航[%s],学校[%s]", navigationIdStr, collegeIdStr), e);
			return buildErrJson("设置导航学校关联操作失败," + e.getMessage());
		}
	}

	@RequestMapping("/setstatus")
	@ResponseBody
	public Object setstatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) long navigationId, @RequestParam(required = true) int status) {
		int num = navigationService.setStatus(navigationId, status);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置失败");
		}

	}

	@RequestMapping("/menuinfo")
	public Object menuInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long navigationId) throws Exception {
		ModelAndView mav = new ModelAndView("navigation/menulist", _.asMap("navigationId", navigationId));
		return mav;
	}

	@RequestMapping("/querymenu")
	@ResponseBody
	public Object queryMenu(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(required = true) long navigationId) {

		List<NavigationMenu> navigationMenuList = navigationMenuService.queryMenuList(navigationId);

		return gson.toJson(buildDataTableResult(draw, 0, navigationMenuList.size(), navigationMenuList));
	}

	@RequestMapping("/setmenuisshow")
	@ResponseBody
	public Object setMenuIsShow(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long navMenuId, @RequestParam(required = true) int isShow) {
		NavigationMenu navigationMenu = navigationMenuService.queryNavigationMenu(navMenuId);
		if (navigationMenu == null) {
			return buildErrJson("设置失败，找不到对应的菜单项");
		}
		if (navigationMenu.getLevel() == NavigationMenu.LEVEL_ROOT && isShow == 0) { // 如果是关闭操作且操作对象是个父菜单，需要同时关闭子菜单
			navigationMenuService.setShowStatusByPid(navigationMenu.getNavMenuId(), isShow);
		}
		int num = navigationMenuService.setShowStatus(navMenuId, isShow);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置失败");
		}

	}

	@RequestMapping("/saveMenu")
	@ResponseBody
	public Object saveMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long navigationId,
			@RequestParam(required = true) String menuName, @RequestParam(required = true) int weight,
			@RequestParam(required = true) int isShow, @RequestParam(required = true) long pid,
			@RequestParam(required = true) int menuType) {
		NavigationMenu menu = new NavigationMenu();
		menu.setNavigationId(navigationId);
		menu.setMenuName(menuName);
		menu.setPid(pid);
		menu.setWeight(weight);
		menu.setIsShow(isShow);
		if (pid == 0) {
			menu.setMenuType(menuType);
			menu.setLevel(1);
		} else {
			menu.setMenuType(NavMenuType.Goods.getType());
			menu.setLevel(2);
		}
		int num = navigationMenuService.insert(menu);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("添加失败");
		}

	}

	@RequestMapping("/deleteMenu")
	@ResponseBody
	public Object deleteMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long menuId, @RequestParam(required = true) int pid) {
		if (pid == 0) {
			// 根据父类id删除所有类目下的商品
			List<NavigationMenu> navMenuList = menuConstant.getChildMenuByPid(menuId);
			for (NavigationMenu menu : navMenuList) {
				nvaGoodsService.deleteGoodsByMenuId(menu.getNavMenuId());
			}
			// 在删除该父类目的所有子类目
			navigationMenuService.deleteMenuByPid(menuId);

		} else {
			// 删除该类目下的商品
			nvaGoodsService.deleteGoodsByMenuId(menuId);
		}
		// 根据类目id删除类目
		int num = navigationMenuService.deleteMenuById(menuId);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置失败");
		}

	}
}
