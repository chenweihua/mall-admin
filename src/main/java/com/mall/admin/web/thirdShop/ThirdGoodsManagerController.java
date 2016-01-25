package com.mall.admin.web.thirdShop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.activity.ActivityGoodsApplyBean;
import com.mall.admin.model.bean.activity.ActivitySkuApplyBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.activity.BgSkuForActivityService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.service.goods.SkuPropertyService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.supplier.SupplierService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.BgGoodsForActivity;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.PropertyCategory;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.supplier.Suppiler;
import com.mall.admin.vo.user.User;

/**
 * 商品管理
 * 
 * @author zhangshuai
 *
 */
@Controller
@RequestMapping("/goods/third/")
public class ThirdGoodsManagerController extends BaseController {

	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	BgSkuGbmService bgSkuGbmService;
	@Autowired
	WmsGoodsService wmsGoodsService;
	@Autowired
	PropertyCategoryService propertyCategoryService;
	@Autowired
	SkuPropertyService skuPropertyService;
	@Autowired
	private StorageCollegeService storageCollegeService;
	@Autowired
	private SupplierService suppilerService;
	@Autowired
	private BgGoodsForActivityService bgGoodsForActivityService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private BgSkuForActivityService bgSkuForActivityService;
	
	@RequestMapping("list")
	public Object toManger(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/goods/manager", "跳转到第三方商品管理界面...");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> vmStorageList = user.getVmStorageList();
		// 检查用户是否有管理的仓
		if (vmStorageList.size() == 0) {
			return new ModelAndView("info", CommonUtil.asMap("message", "用户可管理的店铺为空，请联系管理员~"));
		}
		//转化为店铺
		List<Suppiler> suppilerList = new ArrayList<>();
		Suppiler suppiler = new Suppiler();
		suppiler.setStorageId(-1);
		suppiler.setShopName("全部");
		suppilerList.add(suppiler);
		for(Storage storage : vmStorageList){
			suppiler = suppilerService.getSupplierByStorageId(storage.getStorageId());
			if(suppiler != null)	{
				suppilerList.add(suppiler);
			}
		}
		if(suppilerList.size() == 1){
			return new ModelAndView("info", CommonUtil.asMap("message", "用户可管理的店铺为空，请联系管理员~"));
		}
		//类目
		List<PropertyCategory> categoryList = propertyCategoryService.getByPid(PropertyCategory.PROPERTY_CATEGORY_PARENT);
		PropertyCategory category = new PropertyCategory();
		category.setPropertyCategoryId(-1);
		category.setPropertyCategoryName("全部");
		categoryList.add(0, category);
		ModelAndView mav = new ModelAndView("/thirdShop/thirdGoodsList", CommonUtil.asMap(
				"categoryList", categoryList, "shopProfileList", suppilerList,
				"defaultShopProfile", suppilerList.get(0)));
		return mav;
	}

	@RequestMapping("addPage")
	public Object toAddPage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long storageId
			) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/editPage", "跳转到第三方库存货品编辑界面...");
		Suppiler suppiler = suppilerService.getSupplierByStorageId(storageId);
		if(suppiler == null){
			return buildJson(-1, "系统错误，请联系管理员");
		}
		ModelAndView mav = null;
		if(Suppiler.SHOP_TYPE_EC == suppiler.getShopType()){
			mav = new ModelAndView("thirdShop/editThirdGoods", CommonUtil.asMap("storageId", storageId));
		}else if(Suppiler.SHOP_TYPE_O2O == suppiler.getShopType()){
			mav = new ModelAndView("info", CommonUtil.asMap("message", "目前仅支持电商模式店铺上单~"));
		}
		return mav;
	}
	
	@RequestMapping("editPage")
	public Object toEditPage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long activityBgGoodsId,
			@RequestParam(defaultValue = "-1") Long storageId
			) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/editPage", "跳转到第三方库存货品编辑界面...");
		BgGoodsForActivity bgGoodsForActivity = bgGoodsForActivityService.getById(activityBgGoodsId);
		if(bgGoodsForActivity == null){
			return new ModelAndView("info", CommonUtil.asMap("message", "系统错误，请联系管理员~"));
		}
		Suppiler suppiler = suppilerService.getSupplierByStorageId(bgGoodsForActivity.getStorageId());
		if(suppiler == null){
			return new ModelAndView("info", CommonUtil.asMap("message", "系统错误，请联系管理员~"));
		}
		ModelAndView mav = null;
		if(Suppiler.SHOP_TYPE_EC == suppiler.getShopType()){
			mav = new ModelAndView("thirdShop/editThirdGoods", CommonUtil.asMap("bgGoods",bgGoodsForActivity,"bgGoodsId",bgGoodsForActivity.getBgGoodsId(),"storageId", storageId));
		}else if(Suppiler.SHOP_TYPE_O2O == suppiler.getShopType()){
			mav = new ModelAndView("info", CommonUtil.asMap("message", "目前仅支持电商模式店铺上单~"));
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("edit")	
	public Object insertSingleProduct(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue="-1") Long bgGoodsId,
			@RequestParam(defaultValue="-1") Long activityBgGoodsId,
			@RequestParam(defaultValue="-1") Integer goodsStatus,
			@RequestParam(defaultValue="-1") Long storageId
			) throws SQLException, IOException {
		// 获取关联商品及数量
		String[] bgSkuIds = request.getParameterValues("bgSkuId");
		if(bgGoodsId == -1 || bgSkuIds.length == 0){
			return buildJson(-1, "没有选中货品");
		}
		String maxNum = request.getParameter("maxNum");
		if(!StringUtils.isEmpty(maxNum) && !maxNum.matches("^[1-9]\\d*$|^0$")){
			return buildJson(-1,"限购数量必须为非负整数");
		}
		String weight = request.getParameter("weight");
		if(!StringUtils.isEmpty(weight) && !weight.matches("^[1-9]\\d*$|^0$")){
			return buildJson(-1,"权重必须为非负整数");
		}
		StringBuffer message = new StringBuffer("");
		BgGoodsForActivity bgGoodsForActivity = bgGoodsForActivityService.getById(activityBgGoodsId);
		if(bgGoodsForActivity != null){
			//编辑
			bgGoodsForActivityService.updateBgGoodsForActivity(NumberUtils.toLong(weight),
					NumberUtils.toLong(maxNum), goodsStatus, activityBgGoodsId);
			for(String bgSkuId : bgSkuIds){
				String activityBgSkuId = request.getParameter("activityBgSkuId"+bgSkuId);
				String skuOriginPrice = request.getParameter("originPrice"+bgSkuId);
				String skuPrice = request.getParameter("price"+bgSkuId);
				if(StringUtils.isEmpty(skuPrice) || !skuPrice.matches("^([1-9]\\d*|0)(\\.\\d{1,2})?$")){
					return buildJson(-1,"货品价格格式：数值且精确到小数两位");
				}
				bgSkuForActivityService.updateActivityBgSku(MoneyUtils.yuan2Fen(NumberUtils.toDouble(skuOriginPrice)),
						MoneyUtils.yuan2Fen(NumberUtils.toDouble(skuPrice)), Constants.SKU_STOCK_NAN,
						NumberUtils.toLong(activityBgSkuId));
			}
			message.append("修改成功");
		}else{
			//插入
			List<ActivitySkuApplyBean> activitySkuApplyBeanList = new ArrayList<>();
			String originPrice = "",price = "";
			for(String bgSkuId : bgSkuIds){
				String skuOriginPrice = request.getParameter("originPrice"+bgSkuId);
				String skuPrice = request.getParameter("price"+bgSkuId);
				if(StringUtils.isEmpty(skuPrice) || !skuPrice.matches("^([1-9]\\d*|0)(\\.\\d{1,2})?$")){
					return buildJson(-1,"货品价格格式：数值且精确到小数两位");
				}
				originPrice = skuOriginPrice;
				price = skuPrice;
				ActivitySkuApplyBean activiActivitySkuApplyBean = new ActivitySkuApplyBean();
				activiActivitySkuApplyBean.setOriginPrice(originPrice);
				activiActivitySkuApplyBean.setSkuId(NumberUtils.toLong(bgSkuId));
				activiActivitySkuApplyBean.setActivityPrice(price);
				activiActivitySkuApplyBean.setStock(Constants.SKU_STOCK_NAN);
				activitySkuApplyBeanList.add(activiActivitySkuApplyBean);
			}
			ActivityGoodsApplyBean bean = new ActivityGoodsApplyBean();
			bean.setBgGoodsId(bgGoodsId);
			bean.setOriginPrice(originPrice);
			bean.setActivityPrice(price);
			bean.setStock(Constants.SKU_STOCK_NAN);
			bean.setWeight(NumberUtils.toInt(weight));
			bean.setMaxNum(NumberUtils.toInt(maxNum));
			bean.setSkuListBean(activitySkuApplyBeanList);
			//log
			LogConstant.mallLog.info(new Date() + "[add multiBgGoods]bgGoodsId"+bgGoodsId+"|info:"+JSONObject.toJSONString(bean));
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			Activity activity = activityService.getActivityByStorageId(storageId);
			Storage storage = storageService.getStorageById(storageId);
			int addNum = bgGoodsForActivityService.insertThirdGoods2Activity(bean, activity.getActivityId(), user.getUser_id(), activity.getActivityType(), storage.getStorageType(), goodsStatus);
			LogConstant.mallLog.info("活动{}添加商品{}成功，添加学校{}个", activity.getActivityId(), bean.getBgGoodsId(), addNum);
			BgGoods bggoods = bgGoodsService.getBgGoodsById(bean.getBgGoodsId());
			if (addNum == -1) {
				return buildJson(1, "添加失败，请先设置活动的范围~");
			} else if (addNum == -2) {
				message.append("添加失败，商品【" + bggoods.getBgGoodsName() + "】和活动的范围不存在交集。\n");
			} else if (addNum == -3) {
				message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】在该活动下已存在。\n");
			} else {
				message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】添加成功，添加到" + addNum + "个学校内。\n");
			}
		}
		return buildJson(0, message.toString());
	}

	
	/**
	 * 查询仓库商品
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/query4Activity")
	@ResponseBody
	public Object querySale(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage) {
			//商品状态
			String bgGoodsStatusStr = request.getParameter("bgGoodsStatus");
			//类别id
			String categoryIdStr = request.getParameter("categoryId");
			//仓库id
			String storageIdStr = request.getParameter("storageId");
			//商品名称
			String bgGoodsName = request.getParameter("bgGoodsName");
			//分页数据
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageByStartNum(start, numPerPage);
			paginationInfo.setRecordPerPage(numPerPage);
			//构造查询数据
			Map<String, Object> param = new HashMap<>();
			if (!StringUtils.isEmpty(storageIdStr) && storageIdStr.matches("[0-9]{1,}")) {
				param.put("storageId", NumberUtils.toLong(storageIdStr));
			}else{
				User user = (User)request.getAttribute(Constants.MALLADMIN_USER);
				List<Storage> vmStorageList = user.getVmStorageList();
				if(vmStorageList == null || vmStorageList.size() == 0){
					return buildJson(-1, "对不起，您的账号没有查看数据的权限！");
				}
				List<Long> storageIdList = new ArrayList<>();
				for (Storage s : vmStorageList) {
					storageIdList.add(s.getStorageId());
				}
				param.put("storageIdList", storageIdList);
			}
			//商品名称
			if (!StringUtils.isEmpty(bgGoodsName)) {
				param.put("bgGoodsName", bgGoodsName);
			}
			//类别
			if (!StringUtils.isEmpty(categoryIdStr)	&& categoryIdStr.matches("[0-9]{1,}")) {
				param.put("categoryId", NumberUtils.toLong(categoryIdStr));
			}
			//状态
			if (!StringUtils.isEmpty(bgGoodsStatusStr)	&& bgGoodsStatusStr.matches("[0-9]{1,}")) {
				param.put("bgGoodsStatus", NumberUtils.toLong(bgGoodsStatusStr));
			}
			List<BgGoodsForActivity> bgGoodsForActivityList = bgGoodsForActivityService.getThirdBgGoods4Activity(param, paginationInfo);
			return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), bgGoodsForActivityList, start));
	}
	
	@ResponseBody
	@RequestMapping("editGoodsStatus")
	public Object editGoodsStatus(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long activityBgGoodsId,
			@RequestParam(defaultValue = "-1") Integer goodsStatus
			) throws SQLException, IOException {
		//更新goods
		bgGoodsForActivityService.updateStatus4ActivityGoods(goodsStatus, activityBgGoodsId);
		/*//更新sku
		List<BgSkuForActivity> bgSkuForActivityList = bgSkuForActivityService.getListByActivityBgGoodsId(activityBgGoodsId);
		if(bgSkuForActivityList == null || bgSkuForActivityList.size() == 0){
			return buildJson(-1, "该商品没有对应的sku，请联系管理员！");
		}
		for(BgSkuForActivity bean : bgSkuForActivityList){
			bgSkuForActivityService.updateActivityBgSku(originPrice, activityPrice, stock, activityBgSkuId)
		}*/
		return buildJson(0, "更新成功");
	}
	
}
