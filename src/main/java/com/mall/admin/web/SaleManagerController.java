package com.mall.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.model.bean.base.StorageTypeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.goods.BgGoodsRegionService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.sale.SaleService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.goods.BgGoodsRegion;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.sale.SaleInCollege;
import com.mall.admin.vo.sale.SaleInStorage;
import com.mall.admin.vo.sale.SaleSkuInfo;
import com.mall.admin.vo.user.User;

/**
 * 售卖管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sale")
public class SaleManagerController extends BaseController {
	@Autowired
	SaleService saleService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	SkuService skuService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	BgGoodsRegionService bgGoodsRegionService;
	@Autowired
	private StorageCollegeService storageCollegeService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	private BgGoodsService bgGoodsService;

	@RequestMapping("/view")
	public Object viewSale(HttpServletRequest request, HttpServletResponse response) {
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
		}
		List<Category> categoryList_temp = new ArrayList<Category>();
		List<Storage> storageList_temp = new ArrayList<Storage>();
		boolean hasRdcStorage = false;
		boolean hasLdcStorage = false;
		boolean hasVmStorage = false;
		StorageTypeBean defaultTypeBean = storageTypeList.get(0);
		if (Constants.RDC_STORAGE.equals(defaultTypeBean.getTypeId())) {
			// 有rdc仓
			categoryList_temp = user.getCategoryList();
			storageList_temp = user.getRdcStorageList();
			hasRdcStorage = true;
		} else if(Constants.LDC_STORAGE.equals(defaultTypeBean.getTypeId())) {
			// 如果只有ldc仓，则不需要按类目分权限
			categoryList_temp = categoryService.getAllCategories();
			storageList_temp = user.getLdcStorageList();
			hasLdcStorage = true;
		}else if(Constants.VM_STORAGE.equals(defaultTypeBean.getTypeId())){
			categoryList_temp = user.getCategoryList();
			storageList_temp = user.getVmStorageList();
			hasVmStorage = true;
		}
		if (categoryList_temp == null || categoryList_temp.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户可管理的类目为空，请维护用户的类目信息~"));
		}
		Storage defaultStorage = new Storage();
		Category defaultCategory = new Category();
		categoryList.addAll(categoryList_temp);
		storageList.addAll(storageList_temp);
		defaultStorage = storageList.get(0);
		defaultCategory = categoryList.get(0);
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
		}
		ModelAndView mav = new ModelAndView("sale/salelist", _.asMap("categoryList", categoryList,
				"storageList", storageList, "defaultStorage", defaultStorage, "defaultCategory",
				defaultCategory, "collegeList", collegeList, "storageTypeList", storageTypeList,
				"defaultStorageType", defaultTypeBean));
		return mav;
	}

	/**
	 * 查询仓库商品售卖信息
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
	public Object querySale(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage) {

		/** 仓库类型 ldc仓还是rdc仓 */
		String storageFlag = request.getParameter("stoageflag");
		/** 类目id */
		String categoryIdStr = request.getParameter("categoryId");
		/** 仓库id */
		String storageIdStr = request.getParameter("storageId");
		/** 售卖状态 */
		String statusStr = request.getParameter("status");
		/** 商品名称 */
		String goods_name = request.getParameter("goodsName");
		String collegeId = request.getParameter("collegeId");

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<SaleInStorage> saleInStorageList = new ArrayList<SaleInStorage>();

		Map param = new HashMap();
		if (!Strings.isEmpty(storageIdStr) && storageIdStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(storageIdStr) > 0) {
				param.put("storage_id", Long.parseLong(storageIdStr));
			}
		} else {
			return gson.toJson(buildDataTableResult(draw, 0, 0, saleInStorageList));
		}
		if (!Strings.isEmpty(statusStr) && statusStr.matches("[0-9]{1,}")) {
			if (Integer.parseInt(statusStr) != 0) {
				param.put("status", Integer.parseInt(statusStr));
			}
		} else {
			return gson.toJson(buildDataTableResult(draw, 0, 0, saleInStorageList));
		}
		if (!Strings.isEmpty(goods_name)) {
			param.put("goods_name", goods_name);
		}
		if (!Strings.isEmpty(categoryIdStr) && categoryIdStr.matches("[0-9]{1,}")) {
			if (Long.parseLong(categoryIdStr) > 0) {
				param.put("category_id", Long.parseLong(categoryIdStr));
			}
		} else {
			return gson.toJson(buildDataTableResult(draw, 0, 0, saleInStorageList));
		}
		if (!Strings.isEmpty(collegeId) && collegeId.matches("[0-9]{1,}")) {
			if (Long.parseLong(collegeId) != 0) {
				param.put("collegeId", Long.parseLong(collegeId));
			}
		} else {
			return gson.toJson(buildDataTableResult(draw, 0, 0, saleInStorageList));
		}
		if (Constants.RDC_STORAGE.equals(storageFlag)) {
			saleInStorageList = saleService.getSaleInRDCStorageInfo(param, paginationInfo);
		} else if (Constants.LDC_STORAGE.equals(storageFlag)) {
			saleInStorageList = saleService.getSaleInLDCStorageInfo(param, paginationInfo);
		}else if (Constants.VM_STORAGE.equals(storageFlag)) {
			saleInStorageList = saleService.getSaleInVMStorageInfo(param, paginationInfo);
		}

		// 根据goodsid和仓库id获得对应的stock，status和weight
		if (saleInStorageList != null && saleInStorageList.size() > 0) {
			for (SaleInStorage saleInStorage : saleInStorageList) {
				BgGoodsRegion goodsRegion = bgGoodsRegionService.getByBgGoodsIdAndRegionId(
						saleInStorage.bg_goods_id, saleInStorage.storage_id, 1);
				if (goodsRegion != null) {
					saleInStorage.setStock(goodsRegion.getStock());
					saleInStorage.setWeight(goodsRegion.getWeight());
					saleInStorage.setStatus(goodsRegion.getStatus());
					if (saleInStorage.getStatus() == 1) {
						saleInStorage.setStatusStr("待售");
					} else if (saleInStorage.getStatus() == 2) {
						saleInStorage.setStatusStr("在售");
					} else if (saleInStorage.getStatus() == 3) {
						saleInStorage.setStatusStr("售罄");
					} else {
						saleInStorage.setStatusStr("未知");
					}
					if (saleInStorage.getStock() > 999999) {
						saleInStorage.setStockStr("不限制");
					} else {
						saleInStorage.setStockStr(saleInStorage.getStock() + "");
					}
				}
				saleInStorage.setGoodsType();
				if (saleInStorage.getGoods_type() == 1) {
					// 单品需要查询该商品所在仓库的库存
					int storageStock = saleService.queryStorageStock(
							saleInStorage.getBg_goods_id(), saleInStorage.getStorage_id());
					saleInStorage.setStorageStock(storageStock + "");
				}
			}
		}
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), saleInStorageList,
				start));
	}

	/**
	 * 查询学校下的goods
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/getcollegegoods")
	@ResponseBody
	public Object querySaleGoodsInCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long bggoodsId, @RequestParam String storageflag, @RequestParam long storageId,
			@RequestParam long collegeId, @RequestParam int goodsType) {

		List<SaleSkuInfo> saleSkuInfoList = saleService.getSaleSkuInfoByGoodsId(bggoodsId, goodsType);

		List<SaleInCollege> saleInCollegeList = new ArrayList<SaleInCollege>();
		if (Constants.RDC_STORAGE.equals(storageflag)) {
			saleInCollegeList = saleService.getSaleInRdcStorageInCollege(storageId, collegeId, bggoodsId);
		} else if (Constants.LDC_STORAGE.equals(storageflag)) {
			saleInCollegeList = saleService.getSaleInLdcStorageInCollege(storageId, collegeId, bggoodsId);
		} else if(Constants.VM_STORAGE.equals(storageflag)){
			saleInCollegeList = saleService.getSaleInVmStorageInCollege(storageId, collegeId, bggoodsId);
		}
		for (SaleInCollege saleInCollege : saleInCollegeList) {
			saleInCollege.setStorageId(storageId);
		}
		return buildJson(0, "查询成功~",
				ImmutableMap.of("saleInCollege", saleInCollegeList, "saleSkuInfo", saleSkuInfoList));

	}

	/**
	 * 修改商品的权重、限购和状态
	 * 
	 * @param request
	 * @param response
	 * @param modifytype
	 *                1:修改单个商品状态 2：修改某个仓下所有学校这个商品的状态
	 * @param weight
	 *                权重
	 * @param storageId
	 *                仓库
	 * @param goodsId
	 *                待修改的商品id，如果是修改单个学校，则是前台的goodsid，如果批量修改仓库下所有学校的该商品，
	 *                则是bg_goods_id
	 * @param stock
	 *                库存 大于999999表示不限购
	 * @param status
	 *                状态 1：待售；2：在售；3：售罄
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Object modifyGoodsStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int modifytype, @RequestParam int weight, @RequestParam long storageId,
			@RequestParam long goodsId, @RequestParam int stock, @RequestParam int status,
			@RequestParam int storageType, @RequestParam long collegeId) {
		//
		if (modifytype != 1 && modifytype != 2) {
			return buildJson(1, "修改类型错误~");
		}
		if (weight < 0) {
			return buildJson(1, "权重不能小于0~");
		}
		if (stock < 0) {
			return buildJson(1, "限购不能小于0~");
		}
		if (storageType != Sku.RDC_DISTRIBUTE_TYPE && storageType != Sku.LDC_DISTRIBUTE_TYPE && storageType != Sku.VM_DISTRIBUTE_TYPE) {
			return buildJson(1, "仓库类型错误~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> userAllStorageList = user.getAllStorageList();
		boolean hasPermission = false;
		for (Storage storage : userAllStorageList) {
			if (storage.getStorageId() == storageId) {
				hasPermission = true;
				break;
			}
		}
		if (!hasPermission) {
			return buildJson(1, "骚年，没有修改该仓商品的权限~");
		}

		if (status != 1 && status != 2 && status != 3) {
			return buildJson(1, "商品状态不正确~");
		}
		if (modifytype == 1) {
			Goods goods = goodsService.getById(goodsId);
			// 修改为售卖状态。需要检查对应的单价是否为0，如果为0，则禁止售卖
			if (status == 2) {
				List<Sku> skuList = skuService.getSkuListByGoodsId(goodsId);
				for (Sku sku : skuList) {
					if (sku.getWapPrice() <= 0 || sku.getAppPrice() <= 0) {
						//Goods goods = goodsService.getById(goodsId);
						return buildJson(1, "商品" + goods.getBgGoodsName() + "的售价为0，禁止售卖~");
					}
				}
			}
			//临时支持平台组
			bgGoodsService.updateUpdateTimeByBgGoodsId(goods.getBgGoodsId());
			
			goodsService.updateWeightStockStatus(goodsId, weight, stock, status, storageType);
			long bggoodsId = goods.getBgGoodsId();
			int goodsType = goods.getGoodsType();
			List<SaleSkuInfo> saleSkuInfoList = saleService.getSaleSkuInfoByGoodsId(bggoodsId, goodsType);
			List<SaleInCollege> saleInCollegeList = new ArrayList<SaleInCollege>();
			if (Constants.RDC_STORAGE.equals(storageType + "")) {
				saleInCollegeList = saleService.getSaleInRdcStorageInCollege(storageId, collegeId,
						bggoodsId);
			} else if (Constants.LDC_STORAGE.equals(storageType + "")) {
				saleInCollegeList = saleService.getSaleInLdcStorageInCollege(storageId, collegeId,
						bggoodsId);
			} else if(Constants.VM_STORAGE.equals(storageType + "")){
				saleInCollegeList = saleService.getSaleInVmStorageInCollege(storageId, collegeId, bggoodsId);
			}
			for (SaleInCollege saleInCollege : saleInCollegeList) {
				saleInCollege.setStorageId(storageId);
			}
			return buildJson(0, "修改成功~", ImmutableMap.of("saleInCollege", saleInCollegeList, "saleSkuInfo",
					saleSkuInfoList, "bggoodsId", bggoodsId));
		} else {
			//临时支持平台组
			//goodsId为bgGoodsId,修改仓
			bgGoodsService.updateUpdateTimeByBgGoodsId(goodsId);
			String resultInfo = goodsService.updateWeightStockStatus(goodsId, storageId, weight, stock,
					status, storageType);
			return buildJson(0, "修改结果\r\n" + resultInfo);
		}

	}

	/**
	 * 批量修改仓库下商品id售卖状态
	 * 
	 * @param request
	 * @param response
	 * @param status
	 * @param goodsrecordids
	 * @param storageId
	 * @param storageType
	 * @return
	 */
	@RequestMapping("/batchmodify")
	@ResponseBody
	public Object batchmodify(HttpServletRequest request, HttpServletResponse response, @RequestParam int status,
			@RequestParam String bggoodsIds, @RequestParam int storageType) {
		if (Strings.isEmpty(bggoodsIds)) {
			return buildJson(1, "待修改的商品为空，禁止修改~");
		}
		if (status != 1 && status != 2 && status != 3) {
			return buildJson(1, "待修改的状态错误，禁止修改~");
		}
		if (storageType != Sku.RDC_DISTRIBUTE_TYPE && storageType != Sku.LDC_DISTRIBUTE_TYPE && storageType != Sku.VM_DISTRIBUTE_TYPE) {
			return buildJson(1, "仓库类型错误~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		String[] bgGoodsIdArry = bggoodsIds.split(",");
		if (bgGoodsIdArry.length == 0) {
			return buildJson(1, "骚年，没有修改仓库商品的信息~");
		}
		for (String bgGoodsIdStr : bgGoodsIdArry) {
			if (bgGoodsIdStr.split("_").length != 2) {
				return buildJson(1, "骚年，修改仓库商品的信息的格式错误~");
			}
		}
		if (!(user.is_all_storage == 1 || user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG)) {
			String[] infoArry = bgGoodsIdArry[0].split("_");
			long storageId = Long.parseLong(infoArry[1]);
			List<Storage> userAllStorageList = user.getAllStorageList();
			boolean hasPermission = false;
			for (Storage storage : userAllStorageList) {
				if (storage.getStorageId() == storageId) {
					hasPermission = true;
					break;
				}
			}
			if (!hasPermission) {
				return buildJson(1, "骚年，没有修改该仓商品的权限~");
			}
		}
		StringBuffer resultInfo = new StringBuffer("");
		for (String bgGoodsIdStr : bgGoodsIdArry) {

			long bgGoodsId = Long.parseLong(bgGoodsIdStr.split("_")[0]);
			long storageId = Long.parseLong(bgGoodsIdStr.split("_")[1]);
			//临时支持平台组
			bgGoodsService.updateUpdateTimeByBgGoodsId(bgGoodsId);
			String result = goodsService.updateStatus(bgGoodsId, storageId, status, storageType);
			resultInfo.append(result);
		}
		return buildJson(0, "批量修改结果\r\n" + resultInfo);

	}
}
