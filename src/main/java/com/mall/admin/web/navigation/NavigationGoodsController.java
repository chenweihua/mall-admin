package com.mall.admin.web.navigation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.NavigationMenuConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.navigation.NavigationGoodsService;
import com.mall.admin.vo.navigation.NavigationGoods;
import com.mall.admin.vo.navigation.NavigationMenu;

@Controller
@RequestMapping("/navgoods")
public class NavigationGoodsController extends BaseController {

	private static final Logger log = LogConstant.mallLog;

	@Autowired
	NavigationGoodsService navGoodsService;
	@Autowired
	NavigationMenuConstant menuConstant;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NavigationMenu> navMenuList = menuConstant.getAllChildMenu();
		if (navMenuList == null || navMenuList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建菜单~"));
		}
		for (NavigationMenu menu : navMenuList) {
			long pid = menu.getPid();
			NavigationMenu parentMenu = menuConstant.getNavMenuById(pid);
			menu.setShowName(parentMenu.getMenuName() + "--" + menu.getMenuName());
		}
		ModelAndView mav = new ModelAndView("navigation/goods/navgoodslist",
				_.asMap("navmenulist", navMenuList));

		return mav;
	}

	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage, String navGoodsName,
			int goodsStatus, long navMenuId, Integer isOpen) {

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		if (goodsStatus == 1) {
			// 如果是在选品区，则不需要传递isOpen的状态
			isOpen = null;
		}

		List<NavigationGoods> navigationGoodsList = navGoodsService.queryNavGoods(goodsStatus, navGoodsName,
				navMenuId, isOpen, paginationInfo);
		for (NavigationGoods navGoods : navigationGoodsList) {
			long menuId = navGoods.getNavMenuId();
			NavigationMenu menu = menuConstant.getNavMenuById(menuId);
			NavigationMenu parentMenu = menuConstant.getNavMenuById(menu.getPid());
			navGoods.setNavMenuName(parentMenu.getMenuName() + "--" + menu.getMenuName());
		}

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), navigationGoodsList));
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Integer navGoodsId,
			@RequestParam(required = true) String navGoodsName,
			@RequestParam(required = true) long navMenuId, @RequestParam(required = true) String itemUrl,
			@RequestParam(required = true) String clickUrl, @RequestParam(required = true) int weight) {

		Pattern patter = Pattern.compile("id=([^&.]*)[&]{0,1}");
		Matcher mater = patter.matcher(itemUrl);
		String goodsId = "";
		if (mater.find()) {
			goodsId = mater.group(1);
		} else {
			return buildErrJson("没有找到该商品的id");
		}
		NavigationGoods navGoods = new NavigationGoods();
		navGoods.setNavMenuId(navMenuId);
		navGoods.setNavGoodsName(navGoodsName);
		navGoods.setItemUrl(itemUrl);
		navGoods.setClickUrl(clickUrl);
		navGoods.setWeight(weight);
		navGoods.setGoodsId(goodsId);
		boolean initResult = navGoodsService.initNavGoods(navGoods);
		if (!initResult) {
			return buildErrJson("获取商品信息失败");
		}
		if (navGoodsId == null || navGoodsId < 1L) {
			navGoodsService.insert(navGoods);
		} else {
			navGoods.setNavGoodsId(navGoodsId);
			navGoodsService.update(navGoods);
		}

		return buildSuccJson(null);

	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object deleteNavGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long navGoodsId) {
		int num = navGoodsService.deleteNavGoods(navGoodsId);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("删除失败");
		}
	}

	/**
	 * 批量移动到售卖池
	 * 
	 * @param request
	 * @param response
	 * @param navGoodsIds
	 * @return
	 */
	@RequestMapping("/moveToSaleBatch")
	@ResponseBody
	public Object moveToSaleBatch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String navGoodsIds) {

		// TODO:稍后要改成更改一次数据库，不要循环

		String[] navGoodsIdStr = navGoodsIds.split(",");
		for (int i = 0; i < navGoodsIdStr.length; i++) {
			navGoodsService.putNavGoodsToSalePool(Long.parseLong(navGoodsIdStr[i]));
		}
		return buildSuccJson(null);

	}

	/**
	 * 批量移动到选品池
	 * 
	 * @param request
	 * @param response
	 * @param navGoodsIds
	 * @return
	 */
	@RequestMapping("/moveToSelectBatch")
	@ResponseBody
	public Object moveToSelectBatch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String navGoodsIds) {

		// TODO:稍后要改成更改一次数据库，不要循环

		String[] navGoodsIdStr = navGoodsIds.split(",");
		for (int i = 0; i < navGoodsIdStr.length; i++) {
			navGoodsService.putNavGoodsToSelectPool(Long.parseLong(navGoodsIdStr[i]));
		}
		return buildSuccJson(null);

	}

	/**
	 * 转移到售卖池中
	 * 
	 * @param request
	 * @param response
	 * @param navGoodsId
	 * @return
	 */
	@RequestMapping("/movsalepool")
	@ResponseBody
	public Object movSalePool(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long navGoodsId) {
		int num = navGoodsService.putNavGoodsToSalePool(navGoodsId);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置售卖状态失败~");
		}
	}

	/**
	 * 移动到选品池中
	 * 
	 * @param request
	 * @param response
	 * @param navGoodsId
	 * @return
	 */
	@RequestMapping("/moveSelectPool")
	@ResponseBody
	public Object moveSelectPool(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long navGoodsId) {
		int num = navGoodsService.putNavGoodsToSelectPool(navGoodsId);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置选品状态失败~");
		}
	}

	@RequestMapping("/setopen")
	@ResponseBody
	public Object setNavigationCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long navGoodsId, @RequestParam(required = true) int isOpen) {
		int num = navGoodsService.setOpenStatus(navGoodsId, isOpen);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置开启状态失败~");
		}
	}

	/**
	 * 根据菜单id，获得菜单下的商品。如果是1级菜单，获得的是该一级下二级菜单的所有商品
	 * 
	 * @param request
	 * @param response
	 * @param menuId
	 * @param menuType
	 *                如果menuType = 1:表明是1级类目 2：表明是2级目录
	 * @return
	 */
	@RequestMapping("/goodscountbymenuid")
	@ResponseBody
	public Object getGoodsCountByMenuId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long menuId, @RequestParam(required = true) int menuType) {
		int num = 0;
		if (menuType == 1) {
			List<NavigationMenu> navMenuList = menuConstant.getChildMenuByPid(menuId);

			for (NavigationMenu menu : navMenuList) {
				List<NavigationGoods> navGoodsList = navGoodsService.getGoodsByMenuId(menu
						.getNavMenuId());
				if (navGoodsList == null || navGoodsList.size() == 0) {
					num += 0;
				} else {
					num += navGoodsList.size();
				}
			}
		} else if (menuType == 2) {
			List<NavigationGoods> navGoodsList = navGoodsService.getGoodsByMenuId(menuId);
			if (navGoodsList == null || navGoodsList.size() == 0) {
				num = 0;
			} else {
				num = navGoodsList.size();
			}
		}
		return buildSuccJson(num);
	}
}
