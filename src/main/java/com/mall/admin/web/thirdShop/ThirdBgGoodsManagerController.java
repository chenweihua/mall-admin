package com.mall.admin.web.thirdShop;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.BgSkuForActivityService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.service.goods.GoodsPropertyService;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.service.goods.PropertyNameService;
import com.mall.admin.service.goods.PropertyValueService;
import com.mall.admin.service.goods.SkuPropertyService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.supplier.SupplierService;
import com.mall.admin.service.thirdShop.BgGoods4ThirdService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.GoodsProperty;
import com.mall.admin.vo.goods.PropertyCategory;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.SkuProperty;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;
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
@RequestMapping("/bgGoods/third")
public class ThirdBgGoodsManagerController extends BaseController {
	@Autowired
	private BgGoods4ThirdService bgGoods4ThirdService;
	@Autowired
	private PropertyCategoryService propertyCategoryService;
	@Autowired
	private SupplierService suppilerService;
	@Autowired
	private PropertyNameService propertyNameService;
	@Autowired
	private PropertyValueService propertyValueService;
	@Autowired
	private BgSkuService bgSkuService;
	@Autowired
	private SkuPropertyService skuPropertyService;
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
	private StorageCollegeService storageCollegeService;
	@Autowired
	private GoodsPropertyService goodsPropertyService;
	@Autowired
	private BgSkuForActivityService bgSkuForActivityService;
	
	@RequestMapping("list")
	public Object toManger(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/list", "跳转到第三方库存管理界面...");
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
		ModelAndView mav = new ModelAndView("thirdShop/thirdBgGoodsList", CommonUtil.asMap(
				"categoryList", categoryList, "shopProfileList", suppilerList,
				"defaultShopProfile", suppilerList.get(0)));
		return mav;
	}
	
	@RequestMapping("propertySelect")
	public Object propertySelect(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Long storageId) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/addPage", "跳转到第三方库存货品添加界面...");
		List<PropertyCategory> firstCategoryList = propertyCategoryService.getByPid(PropertyCategory.PROPERTY_CATEGORY_PARENT);
		if(firstCategoryList == null || firstCategoryList.size() == 0){
			return new ModelAndView("info", CommonUtil.asMap("message", "目前还没有维护属性类目，请联系管理员~"));
		}
		List<PropertyCategory> secondCategoryList = propertyCategoryService.getByPid(firstCategoryList.get(0).getPropertyCategoryId());
		List<PropertyCategory> thirdCategoryList = new ArrayList<PropertyCategory>();
		if(secondCategoryList == null || secondCategoryList.size() == 0){
			secondCategoryList = new ArrayList<>();
		}else{
			thirdCategoryList = propertyCategoryService.getByPid(secondCategoryList.get(0).getPropertyCategoryId());
		}
		ModelAndView mav = new ModelAndView("thirdShop/propertyCategorySelect", CommonUtil.asMap(
				"firstCategoryList", firstCategoryList, "secondCategoryList", secondCategoryList,
				"thirdCategoryList", thirdCategoryList,"storageId",storageId));
		return mav;
	}
	
	@RequestMapping("addPage")
	public Object toAddPage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long bgGoodsId,
			@RequestParam(defaultValue = "-1") Long categoryId,
			@RequestParam(defaultValue = "-1") Long firstCategoryId,
			@RequestParam(defaultValue = "-1") Long storageId
			) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/editPage", "跳转到第三方库存货品编辑界面...");
		List<PropertyName> propertyNameList = propertyNameService.getByCategoryIdWithValues(categoryId);
		ModelAndView mav = new ModelAndView("thirdShop/editThirdBgGoods", CommonUtil.asMap(
				"propertyNameList", propertyNameList,"storageId", storageId,"categoryId", categoryId));
		return mav;
	}
	
	@RequestMapping("editPage")
	public Object toEditPage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long bgGoodsId,
			@RequestParam(defaultValue = "-1") Long categoryId,
			@RequestParam(defaultValue = "-1") Long firstCategoryId,
			@RequestParam(defaultValue = "-1") Long storageId
			) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/editPage", "跳转到第三方库存货品编辑界面...");
		ModelAndView mav = null;
		if(bgGoodsId == -1){
			List<PropertyName> propertyNameList = propertyNameService.getByCategoryIdWithValues(categoryId);
			mav = new ModelAndView("thirdShop/editThirdBgGoods", CommonUtil.asMap(
					"propertyNameList", propertyNameList,"storageId", storageId,"categoryId", firstCategoryId,"propertyCategoryId", categoryId));
		}else{
			BgGoods bgGoods = bgGoods4ThirdService.getBgGoodsById(bgGoodsId);
			if(bgGoods == null){
				return new ModelAndView("info", CommonUtil.asMap("message", "需要编辑的商品不存在，请联系管理员~"));
			}
			//商品详情图
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
			//商品属性
			List<PropertyName> propertyNameList = propertyNameService.getByCategoryIdWithValues(bgGoods.getPropertyCategoryId());
			mav = new ModelAndView("thirdShop/editThirdBgGoods", CommonUtil.asMap(
					"propertyNameList", propertyNameList,"storageId", bgGoods.getStorageId(),"categoryId", bgGoods.getCategoryId(),"propertyCategoryId", bgGoods.getPropertyCategoryId()
					,"bgGoods",bgGoods));
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("edit")
	public Object insertThirdProduct(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		User user = (User) request.getAttribute("user");
		BgGoods bgGoods = RequestUtil.toBean(request, BgGoods.class);
		if(StringUtils.isBlank(bgGoods.getImageUrl())){
			return buildJson(2, "单品必须上传图片！");
		}
		bgGoods.setOperator(user.getUser_id());
		Map<String,Map<String, Object>> param = new HashMap<String, Map<String,Object>>();
		String selectedIamgeValuesStr = request.getParameter("selectedIamgeValues");
		String selectValuesStr = request.getParameter("selectedValues");
		if(StringUtils.isEmpty(selectValuesStr)){
			return buildJson(-1, "货品没有选择属性，或已选择的属性价格/数量/商品条码存在空值");
		}
		//获取图片
		Map<String, String> imageMap = new HashMap<>();
		if(!StringUtils.isEmpty(selectedIamgeValuesStr)){
			for(String key : selectedIamgeValuesStr.split(",")){
				String imageUrl = request.getParameter("image"+key);
				if(StringUtils.isEmpty(imageUrl)){
					return buildJson(-1, "必须上传属性图片");
				}
				imageMap.put(key, imageUrl);
			}
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
		//属性值去重,为添加BgGoods准备数据
		String[] selectValues = selectValuesStr.split(",");
		List<String> valueList = new ArrayList<>();
		for(String key : selectValues){
			String[] values = key.split(":");
			for(String v : values){
				if(!valueList.contains(v)){
					valueList.add(v);
				}
			}
		}
		//获取sku对应属性,并填充图片
		for(String key : selectValues){
			Map<String, Object> kv = new HashMap<>();
			String imageUrl = "";
			for(String imageKey : imageMap.keySet()){
				if(key.contains(imageKey)){
					imageUrl = imageMap.get(imageKey);
				}
			}
			kv.put("imageUrl", imageUrl);
			String bgSkuId = request.getParameter("bgSkuId"+key);
			List<Long> wmsGoodsIdList = bgSkuGbmService.getWmsGoodsIdByBgSkuId(NumberUtils.toLong(bgSkuId,-1));
			kv.put("bgSkuId", bgSkuId);
			if(wmsGoodsIdList != null && wmsGoodsIdList.size() > 0){
				kv.put("wmsGoodsId", wmsGoodsIdList.get(0));
			}
			String stock = request.getParameter("stock"+key);
			if(!StringUtils.isEmpty(stock) && !stock.matches("^[1-9]\\d*$|^0$")){
				return buildJson(-1,"货品数量必须为非负整数");
			}
			String price = request.getParameter("price"+key);
			if(!StringUtils.isEmpty(price) && !price.matches("^([1-9]\\d*|0)(\\.\\d{1,2})?$")){
				return buildJson(-1,"货品价格格式：数值且精确到小数两位");
			}
			String code = request.getParameter("code"+key);
			if(!StringUtils.isEmpty(stock) && !StringUtils.isEmpty(price) && !StringUtils.isEmpty(code)){
				kv.put("stock", stock);
				kv.put("price", price);
				kv.put("code", code);
				param.put(key, kv);
			}
		}
		Map<String, Object> result = bgGoods4ThirdService.addBgGoods(bgGoods, param,valueList,imageList);
		return result;
	}

	
	/**
	 * 查询BgGoods
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
		List<BgGoods4Manager> bgGoods4Manager = new ArrayList<BgGoods4Manager>();
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
		bgGoods4Manager = bgGoods4ThirdService.getBgGoodsDtoByPage(param, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0,
				paginationInfo.getTotalRecord(), bgGoods4Manager, start));
	}
	
	@RequestMapping("/query4Select")
	@ResponseBody
	public Object query4Select(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "search[value]") String bgGoodsName,
			@RequestParam Long storageId,
			@RequestParam(value = "length") int numPerPage) {
		//分页数据
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		//构造查询数据
		List<BgGoods4Manager> bgGoods4Manager = new ArrayList<BgGoods4Manager>();
		Map<String, Object> param = new HashMap<>();
		param.put("storageId", storageId);
		//商品名称
		if (!StringUtils.isEmpty(bgGoodsName)) {
			param.put("bgGoodsName", bgGoodsName);
		}
		//状态
		param.put("bgGoodsStatus", Constants.GOOD_STATUS_SALING);
		bgGoods4Manager = bgGoods4ThirdService.getBgGoodsDtoByPage(param, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0,
				paginationInfo.getTotalRecord(), bgGoods4Manager, start));
	}
	
	@ResponseBody
	@RequestMapping("propertyCategory")
	public Object propertyCategory(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Long propertyCategoryId) throws SQLException, IOException {
		List<PropertyCategory> categoryList = new ArrayList<PropertyCategory>();
		List<PropertyCategory> categoryListTemp = propertyCategoryService.getByPid(propertyCategoryId);
		if(categoryListTemp != null){
			categoryList = categoryListTemp;
		}
		return buildJson(0, "", categoryList);
	}
	
	@ResponseBody
	@RequestMapping("getBgGoodsProperty")
	public Object getBgGoodsProperty(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long bgGoodsId
			) throws SQLException, IOException {
		logInputData(request, "/bgGoods/third/editPage", "跳转到第三方库存货品编辑界面...");
		BgGoods bgGoods = bgGoods4ThirdService.getBgGoodsById(bgGoodsId);
		if(bgGoods == null){
			return new ModelAndView("info", CommonUtil.asMap("message", "需要编辑的商品不存在，请联系管理员~"));
		}
		List<GoodsProperty> selectedProperty = goodsPropertyService.getByBgGoodsId(bgGoodsId);
		Map<Long, Map<Long,SkuProperty>> bgSkuPropertyValues = bgGoods4ThirdService.getBgGoodsProperty(bgGoodsId);
		if(bgSkuPropertyValues == null){
			return buildJson(-1, "需要编辑的商品没有属性，请联系管理员~");
		}
		List<PropertyName> propertyNameList = propertyNameService.getByCategoryIdWithValues(bgGoods.getPropertyCategoryId());
		//获取需要图片的key
		Map<String, String> propertyImageMap = bgGoods4ThirdService.producePropertyImageMap(propertyNameList, selectedProperty);
		Map<String, BgSku> propertyBgSkuMap = bgGoods4ThirdService.producePropertyBgSkuMap(propertyNameList, bgSkuPropertyValues,propertyImageMap);
		return buildJson(0,"",CommonUtil.asMap("selectedProperty", selectedProperty,"propertyBgSkuMap",propertyBgSkuMap,"propertyImageMap",propertyImageMap));
	}
	
	@ResponseBody
	@RequestMapping("selectBgGoods")
	public Object selectBgGoods(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue="-1") Long activityBgGoodsId,
			@RequestParam Long bgGoodsId
			) throws SQLException, IOException {
		BgGoods bgGoods = bgGoodsService.getBgGoodsById(bgGoodsId);
		List<PropertyName> selectedPnList = propertyNameService.getPnListByBgGoodsId(bgGoodsId);
		Map<Long, Map<Long,SkuProperty>> bgSkuPropertyValues = bgGoods4ThirdService.getBgGoodsProperty(bgGoodsId);
		List<BgSku> bgSkuList = bgGoods4ThirdService.produceBgSkuList(bgSkuPropertyValues);
		Map<String, Object> ret = CommonUtil.asMap("bgGoods",bgGoods,"selectedPnList",selectedPnList,"bgSkuList",bgSkuList);
		if(activityBgGoodsId != -1){
			List<BgSkuForActivity> bgSkuForActivities = bgSkuForActivityService.getListByActivityBgGoodsId(activityBgGoodsId);
			ret.put("bgSkuForActivities", bgSkuForActivities);
		}
		return buildJson(0, "",ret);
	}
	
	@ResponseBody
	@RequestMapping("editBgGoodsStatus")
	public Object editBgGoodsStatus(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long bgGoodsId,
			@RequestParam(defaultValue = "-1") Integer goodsStatus
			) throws SQLException, IOException {
		//更新bgGoods
		int ret = bgGoods4ThirdService.updateBgGoodsStatus(bgGoodsId, goodsStatus);
		if(ret > 0){
			return buildJson(0, "更新成功");
		}else{
			return buildJson(-1, "更新失败");
		}
		
	}
}
