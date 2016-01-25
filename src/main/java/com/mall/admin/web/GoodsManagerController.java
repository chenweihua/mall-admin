package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.base.StorageTypeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.service.goods.SkuPropertyService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.BgSkuGbm;
import com.mall.admin.vo.goods.PropertyCategory;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.WmsGoods;

/**
 * 商品管理
 * 
 * @author zhangshuai
 *
 */
@Controller
@RequestMapping("/goods/")
public class GoodsManagerController extends BaseController {

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

	@RequestMapping("manager")
	public Object toManger(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/goods/manager", "跳转到商品管理界面...");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Category> categoryList = new ArrayList<Category>();
		List<Storage> storageList = new ArrayList<Storage>();
		List<StorageTypeBean> storageTypeList = new ArrayList<StorageTypeBean>();
		// 检查用户是否有管理的仓
		if (user.getAllStorageList().size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户可管理的仓库为空，请维护用户的仓库信息~"));
		}
		// 如果是管理员，增加全部类目的查询
		if (user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG || user.is_all_category == 1) {
			Category category = new Category();
			category.setCategoryId(0);
			category.setCategoryName("全部");
			categoryList.add(category);
		}
		// 如果是管理员，增加全部仓库的查询
		if (user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG || user.is_all_storage == 1) {
			Storage storage = new Storage();
			storage.setStorageId(0);
			storage.setStorageName("全部");
			storageList.add(storage);
		}
		// 如果用户有rdc仓，增加查询rdc仓的选项
		if (user.getRdcStorageList() != null && user.getRdcStorageList().size() > 0) {
			StorageTypeBean rdcTypeBean = new StorageTypeBean(Constants.RDC_STORAGE, "RDC仓");
			storageTypeList.add(rdcTypeBean);
		}
		// 如果用户有ldc仓，增加查询ldc仓的选项
		if (user.getLdcStorageList() != null && user.getLdcStorageList().size() > 0) {
			StorageTypeBean ldcTypeBean = new StorageTypeBean(Constants.LDC_STORAGE, "LDC仓");
			storageTypeList.add(ldcTypeBean);
		}
		// 如果用户有虚拟仓，增加查询虚拟仓的选项
		if (user.getVmStorageList() != null && user.getVmStorageList().size() > 0) {
			StorageTypeBean vmTypeBean = new StorageTypeBean(Constants.VM_STORAGE, "虚拟仓");
			storageTypeList.add(vmTypeBean);
		}
		if (storageTypeList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户可管理的仓库为空，请维护用户的仓库信息~"));
		}else if(storageTypeList.size() > 0){
			StorageTypeBean allTypeBean = new StorageTypeBean("-1", "全部");
			storageTypeList.add(0,allTypeBean);
		}
		StorageTypeBean defaultTypeBean = storageTypeList.get(0);
		List<Category> categoryList_temp = new ArrayList<Category>();
		List<Storage> storageList_temp = new ArrayList<Storage>();
		boolean hasRdcStorage = false;
		boolean hasLdcStorage = false;
		boolean hasVmStorage = false;
		if (Constants.RDC_STORAGE.equals(defaultTypeBean.getTypeId())) {
			// 有rdc仓
			categoryList_temp = user.getCategoryList();
			storageList_temp = user.getRdcStorageList();
			hasRdcStorage = true;
		} else if(Constants.LDC_STORAGE.equals(defaultTypeBean.getTypeId())){
			// 如果只有ldc仓，则不需要按类目分权限
			categoryList_temp = categoryService.getAllCategories();
			storageList_temp = user.getLdcStorageList();
			hasLdcStorage = true;
		}else if(Constants.VM_STORAGE.equals(defaultTypeBean.getTypeId())){
			categoryList_temp = user.getCategoryList();
			storageList_temp = user.getVmStorageList();
		}else {
			categoryList_temp = categoryService.getAllCategories();
			storageList_temp = user.getAllStorageList();
		}
		if (categoryList_temp == null || categoryList_temp.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户可管理的类目为空，请维护用户的类目信息~"));
		}
		categoryList.addAll(categoryList_temp);
		storageList.addAll(storageList_temp);
		Storage defaultStorage = storageList.get(0);
		Category defaultCategory = categoryList.get(0);
		List<College> collegeList = new ArrayList<>();
		if (hasRdcStorage) {
			collegeList = collegeService.getListByRdcStorageId(defaultStorage.getStorageId());
		} else if(hasLdcStorage) {
			collegeList = collegeService.getListByLdcStorageId(defaultStorage.getStorageId());
		} else if(hasVmStorage){
			List<Long> vmCollegeIdList = storageCollegeService.getCollegeIdListByStorageId(defaultStorage.getStorageId());
			for(Long collegeId : vmCollegeIdList){
				College temp = CollegeConstant.getCollegeById(collegeId);
				if(temp != null){
					collegeList.add(temp);
				}
			}
		}else{
			collegeList = CollegeConstant.getCollegeList();
		}
		ModelAndView mav = new ModelAndView("goods/manager", _.asMap(
				"categoryList", categoryList, "storageList", storageList,
				"defaultStorage", defaultStorage, "defaultCategory",
				defaultCategory, "collegeList", collegeList, "storageTypeList", storageTypeList,
				"defaultStorageType", defaultTypeBean));
		return mav;
	}

	@RequestMapping("singleProduct")
	public Object toSingleProduct(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {

		logInputData(request, "/goods/manager", "跳转到单品上单界面...");
		List<Category> categories = categoryService.getAllCategories();
		request.setAttribute("categories", categories);
		Category defaultCategory = new Category();
		defaultCategory.setCategoryId(1);
		request.setAttribute("category", defaultCategory);

		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr != null && bgGoodsIdStr.matches("[0-9]{1,}")) {
			long bgGoodsId = _.toLong(bgGoodsIdStr);
			request.setAttribute("bgGoods",
					bgGoodsService.getBgGoodsById(bgGoodsId));
			List<BgGoodsImage> bgGoodsImageList = bgGoodsService.getBgGoodsImageListByBgGoodsId(bgGoodsId);
			List<BgGoodsImage> commerceImagelist = Lists.newArrayList();
			List<BgGoodsImage> detailImagelist = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(bgGoodsImageList)) {
				for(BgGoodsImage bgGoodsImage : bgGoodsImageList) {
					if(BgGoodsImage.GOODS_IMAGE_TYPE_COMMERCE == bgGoodsImage.getImageType()){//商品大图
						commerceImagelist.add(bgGoodsImage);
					}
					else {//商品详情页图
						detailImagelist.add(bgGoodsImage);
					}
				}
			}
			request.setAttribute("commerceImagelist", commerceImagelist);
			request.setAttribute("detailImagelist", detailImagelist);
			
			List<BgSkuGbm> bgSkuGbmList = bgSkuGbmService.getBgSkuGbmByBgGoodsId(bgGoodsId);
			
			for (BgSkuGbm o : bgSkuGbmList) {
				WmsGoods wmsGood = wmsGoodsService.queryById(o.getWmsGoodsId());
				if (wmsGood != null) {
					o.setWmsGoodsName(wmsGood.getWms_goods_name());
					o.setWmsGoodsGbm(wmsGood.getWms_goods_gbm());
				}
			}
			request.setAttribute("bgSkuGbmList", bgSkuGbmList);
		}
		return "goods/singleProduct";
	}

	@RequestMapping("multiProduct")
	public Object toMultiProduct(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/goods/manager", "跳转到聚合上单界面...");
		List<Category> categories = categoryService.getAllCategories();
		request.setAttribute("categories", categories);
		Category defaultCategory = new Category();
		defaultCategory.setCategoryId(1);
		request.setAttribute("category", defaultCategory);

		List<PropertyCategory> pCategoryList = propertyCategoryService
				.getByPid(0);
		request.setAttribute("pCategoryList", pCategoryList);
		
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr != null && bgGoodsIdStr.matches("[0-9]{1,}")) {
			long bgGoodsId = _.toLong(bgGoodsIdStr);
			request.setAttribute("bgGoods",
					bgGoodsService.getBgGoodsById(bgGoodsId));
			List<BgGoodsImage> bgGoodsImageList = bgGoodsService.getBgGoodsImageListByBgGoodsId(bgGoodsId);
			List<BgGoodsImage> commerceImagelist = Lists.newArrayList();
			List<BgGoodsImage> detailImagelist = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(bgGoodsImageList)) {
				for(BgGoodsImage bgGoodsImage : bgGoodsImageList) {
					if(BgGoodsImage.GOODS_IMAGE_TYPE_COMMERCE == bgGoodsImage.getImageType()){//商品大图
						commerceImagelist.add(bgGoodsImage);
					}
					else {//商品详情页图
						detailImagelist.add(bgGoodsImage);
					}
				}
			}
			request.setAttribute("commerceImagelist", commerceImagelist);
			request.setAttribute("detailImagelist", detailImagelist);
			//bgSku
			List<Long> bgSkuIds = skuPropertyService.getBgSkuByBgGoodsId(bgGoodsId);
			List<BgGoodsDto> bgGoodsDtos = new ArrayList<>();
			for(Long bgSkuId : bgSkuIds){
				BgGoodsDto temp = bgGoodsService.getBgGoodsDtoByBgSkuId(bgSkuId);
				if(temp != null){
					bgGoodsDtos.add(temp);
				}
			}
			request.setAttribute("bgGoodsDtos", bgGoodsDtos);
			
			//goodsPropertyName----value     修改成ajax异步请求
		}
		return "goods/multiProduct";
	}

	@ResponseBody
	@RequestMapping("singleProduct/edit")
	public Object insertSingleProduct(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		User user = (User) request.getAttribute("user");

		// 获取关联商品及数量
		Map<String, Long> map = new HashMap<>();
		String[] wms_goods_ids = request.getParameterValues("wms_goods_id");
		if (wms_goods_ids == null) {
			return buildJson(1, "请单击添加商品，选择商品..");
		}
		// 准备bg_goods关联商品
		long num = 0;
		long wms_goods_id = -1;
		for (String s : wms_goods_ids) {
			wms_goods_id = _.toLong(s, -1);
			if (wms_goods_id == -1) {
				LogConstant.mallLog.info("前台传过来的wms_goods_id是：" + s);
				return buildErrJson("系统错误，请联系管理员！");
			}
			num = _.toLong(request.getParameter(s + "num"), -1);
			if (num < 0) {
				return buildJson(2, "商品打包数量是必填项，且必须是大于0的数字！");
			}
			map.put(s, num);
		}
		BgGoods bgGoods = RequestUtil.toBean(request, BgGoods.class);
		if(StringUtils.isBlank(bgGoods.getImageUrl())){
			return buildJson(2, "单品必须上传图片！");
		}
		List<BgGoodsImage> imageList = Lists.newArrayList();
		String imageUrls[] = StringUtils.split(bgGoods.getImageUrl(), ",");
		for(int i = 0; i < imageUrls.length; i++) {
			if(i == 0) {
				bgGoods.setImageUrl(imageUrls[i]);//获取第一个
			}
			else {
				imageList.add(new BgGoodsImage(bgGoods.getBgGoodsId(), imageUrls[i],
						BgGoodsImage.GOODS_IMAGE_TYPE_COMMERCE, i));
			}
		}
		//商品详情页图片
		String detailImageUrls[] = StringUtils.split(bgGoods.getDetailImageUrl(), ",");
		for(int i = 0; i < detailImageUrls.length; i++) {
			imageList.add(new BgGoodsImage(bgGoods.getBgGoodsId(), detailImageUrls[i],
					BgGoodsImage.GOODS_IMAGE_TYPE_DETAIL, i+1));
		}
		bgGoods.setOperator(user.getUser_id());
		Map<String, Object> result = bgGoodsService.addGoods(bgGoods, map, imageList);
		return result;
	}

	@ResponseBody
	@RequestMapping("multiProduct/edit")
	public Object insertMultiProduct(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		User user = (User) request.getAttribute("user");

		// 获取关联商品
		Map<String, String[]> map = new HashMap<>();
		String[] pnIds = request.getParameterValues("pnId");
		String[] bg_sku_ids = request.getParameterValues("bg_sku_id");
		String[] pvIds = request.getParameterValues("pvId");
		if(bg_sku_ids.length < 2){
			return buildJson(-1, "聚合品必须选择两种以上商品");
		}
		
		if(pvIds == null || pvIds.length < 1){
			return buildJson(-1,"必须为选中的商品添加规格属性值");
		}
		
		for (String s : bg_sku_ids) {
			String[] skuProperty = request.getParameterValues("sku" + s);
			if(skuProperty == null || skuProperty.length < 1){
				return buildJson(-1, "选中商品必须配置规格属性");
			}
			if (skuProperty.length < pnIds.length) {
				return buildJson(-1, "每种商品必须具有选中规格属性的取值");
			} else if (skuProperty.length > pnIds.length) {
				return buildJson(-1, "每种商品只能具有规格属性的一种取值");
			}
			map.put(s, skuProperty);
		}

		// 校验sku属性是否唯一
		for(String key : map.keySet()){
			String[] temp = map.get(key);

			for(String comKey : map.keySet()){
				if(!key.equals(comKey)){
					String[] comTemp = map.get(comKey);
					if (compareStringArray(temp, comTemp)) {
						return buildJson(-1, "多个sku配置的规格值不唯一");
					}
				}
			}
		}
		
		BgGoods bgGoods = RequestUtil.toBean(request, BgGoods.class);
		if(StringUtils.isBlank(bgGoods.getImageUrl())){
			return buildJson(2, "聚合品必须上传图片！");
		}
		List<BgGoodsImage> imageList = Lists.newArrayList();
		String imageUrls[] = StringUtils.split(bgGoods.getImageUrl(), ",");
		for(int i = 0; i < imageUrls.length; i++) {
			if(i == 0) {
				bgGoods.setImageUrl(imageUrls[i]);//获取第一个
			}
			else {
				imageList.add(new BgGoodsImage(bgGoods.getBgGoodsId(), imageUrls[i],
						BgGoodsImage.GOODS_IMAGE_TYPE_COMMERCE, i));
			}
		}
		//商品详情页图片
		String detailImageUrls[] = StringUtils.split(bgGoods.getDetailImageUrl(), ",");
		for(int i = 0; i < detailImageUrls.length; i++) {
			imageList.add(new BgGoodsImage(bgGoods.getBgGoodsId(), detailImageUrls[i],
					BgGoodsImage.GOODS_IMAGE_TYPE_DETAIL, i+1));
		}
		bgGoods.setOperator(user.getUser_id());

		Map<String, Object> result = bgGoodsService.addMultiGoods(bgGoods, map, pvIds, imageList);
		return result;
	}

	private boolean compareStringArray(String[] a, String[] b) {
		for (String as : a) {
			boolean comFlag = true;
			for (String bs : b) {
				if (as.equals(bs)) {
					comFlag = false;
					continue;
				}
			}
			if (comFlag) {
				return false;
			}

		}
		return true;
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
	@RequestMapping("/query")
	@ResponseBody
	public Object querySale(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage) {
		
		
		/** 仓库类型 ldc仓还是rdc仓 */
		String storageFlag = request.getParameter("stoageflag");
		int storageType = NumberUtils.toInt(storageFlag, -2);
		if(storageType != Storage.ALL_STORAGE && storageType != Storage.LDC_STORAGE 
				&& storageType != Storage.RDC_STORAGE && storageType != Storage.VM_STORAGE){
			return buildJson(-1, "仓库类型错误");
		}
		/** 类目id */
		String categoryIdStr = request.getParameter("categoryId");
		/** 仓库id */
		String storageIdStr = request.getParameter("storageId");
		/** 商品名称 */
		String goods_name = request.getParameter("goodsName");
		/* 学校id */
		String collegeId = request.getParameter("collegeId");

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<BgGoods4Manager> bgGoods4Manager = new ArrayList<BgGoods4Manager>();
		Map<String, Object> param = new HashMap<>();
		if (!Strings.isEmpty(storageIdStr) && storageIdStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(storageIdStr) != 0) {
				param.put("storage_id", Long.parseLong(storageIdStr));
			} 
		}
		if (!Strings.isEmpty(goods_name)) {
			param.put("goods_name", goods_name);
		}
		if (!Strings.isEmpty(categoryIdStr)
				&& categoryIdStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(categoryIdStr) != 0) {
				param.put("category_id", Long.parseLong(categoryIdStr));
			} 
		}
		if (!Strings.isEmpty(collegeId) && collegeId.matches("[0-9]{1,}")) {
			if (Long.parseLong(collegeId) != 0) {
				param.put("collegeId", Long.parseLong(collegeId));
			}
		}
		bgGoods4Manager = bgGoodsService.getBgGoodsInfoByPage(storageType,
				param, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0,
				paginationInfo.getTotalRecord(), bgGoods4Manager, start));
	}
	

	@RequestMapping("/wms/query")
	@ResponseBody
	public Object wmsGoodsListQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		Map<String,Object> param = new HashMap<>();
		param.put("wms_goods_name_like", searchStr);
		param.put("wms_goods_gbm_like", searchStr);
		//当storageId为-2时，默认查询全部
		param.put("storageId", Constants.ALL_WMS_STORAGE_ID);
		List<WmsGoods> wmsGoodsList = wmsGoodsService.getWmsGoods(param, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), wmsGoodsList, start));
	}
}
