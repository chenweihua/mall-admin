package com.mall.admin.web.storage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.activity.ActivityGoodsService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.store.StoreService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.Md5Util;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;

/**
 * 
 * @author gaozhou city管理未创建,暂时用college代替 复制全国商品未做,等接口
 */

@Controller
@RequestMapping("/storage/storage")
public class StorageController extends BaseController {
	private static final int CITY_PID = 0;

	@Autowired
	private StorageService storageService;

	@Autowired
	private CityService cityService;

	@Autowired
	private StroageConstant stroageConstant;

	@Autowired
	private WmsGoodsService wmsGoodsService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private ActivityGoodsService activityGoodsService;

	@Autowired
	private StorageGoodsStockService storageGoodsStockService;

	@Autowired
	private BgGoodsService bgGoodsService;
	@Autowired
	private ZtreeUtil ztreeUtil;
	@Autowired
	private StorageCollegeService storageCollegeService;

	private final static String KEY = "cb";

	@RequestMapping("view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		LogConstant.mallLog.info("StorageController storage");

		List<City> cityList = null;
		cityList = cityService.getCityListByPid(CITY_PID);

		if (cityList == null || cityList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建城市~"));
		}

		return new ModelAndView("storage/storagelist", ImmutableMap.of("cityList", cityList));
	}

	@RequestMapping("queryStorage")
	@ResponseBody
	public Object queryStorage(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer numPerPage,
			@RequestParam(value = "searchStr", required = false) String searchStr,
			@RequestParam(value = "storagetype", required = false) Integer storagetype,
			@RequestParam(value = "cityid", required = false) Long cityid,
			@RequestParam(value = "isclose", required = false) Integer isclose) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPage);
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		List<Storage> storageList;
		Map paramMap = new HashMap();
		paramMap.put("storage_id_name", searchStr);
		paramMap.put("storage_type", storagetype);
		paramMap.put("city_id", cityid);
		paramMap.put("is_close", isclose);
		try {
			Pair<Long, PaginationList<Storage>> p;
			if (user.getIs_all_storage() == 1 || user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG)
				p = storageService.getPageStorage(paginationInfo, paramMap);
			else {
				paramMap.put("user_id", user.getUser_id());
				p = storageService.getPageStorageByUser(paginationInfo, paramMap);
			}
			storageList = p.getRight();
			long filteredTotal = p.getLeft();
			long total = 0;
			for (Storage storage : storageList) {
				if (storage.getCityId() != 0 && storage.getStorageType() != 2) {
					// City city =
					// cityService.getCityById(storage.getCityId());
					City city = CityConstant.getcityById(storage.getCityId());
					if (city == null) {
						return buildJson(0, "未找到对应城市");
					}
					storage.setCityName(city.getCityName());
				} else if (storage.getCityId() == 0 && storage.getStorageType() == Storage.VM_STORAGE) {
					storage.setCityName("全部");
				} else {
					storage.setCityName("无");
				}
			}
			return gson.toJson(buildDataTableResult(draw, total, filteredTotal, storageList));
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "获取仓库列表", "searchStr:" + searchStr
					+ "&storagetype:" + storagetype + "&cityid:" + cityid + "&isclose:" + isclose);
			return buildJson(1, "获取仓库列表失败");
		}
	}

	@RequestMapping("addStorage")
	@ResponseBody
	public Object addStorage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "0") Integer ldcType, @RequestParam String storagename,
			@RequestParam Integer storagetype, @RequestParam(required = false) Integer cityid,
			@RequestParam Integer isclose, @RequestParam String vmstoreid,
			@RequestParam String vmcollegeid, @RequestParam Integer freightType,
			@RequestParam Integer freightSubType, @RequestParam(defaultValue = "0") Integer ldcOwnerType)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		long vmstoreidLong = _.toLong(vmstoreid, 0);
		long vmcollegeidLong = _.toLong(vmcollegeid, 0);
		if (vmstoreidLong != 0) {
			if (vmcollegeidLong == 0) {
				vmcollegeidLong = storeService.getCollegeIdByStoreId(vmstoreidLong);
				if (vmcollegeidLong == -1) {
					return buildJson(1, "没找到与门店id对应的学校");
				}
				if (vmcollegeidLong == -2) {
					return buildJson(2, "p2p服务器没反应,请手动输入虚拟学校id");
				}
			}
		}

		Map<Long, Storage> storageMap = StroageConstant.getStorageMap();
		Map<String, Storage> storageNameMap = StroageConstant.getStorageNameMap();
		if (storageNameMap.get(storagename.trim()) != null) {
			return buildJson(1, "添加仓库失败,仓库名重复");
		}

		// for(Map.Entry<Long,Storage> entry : storageMap.entrySet()){
		// if(vmstoreidLong == entry.getValue().getVmStoreId()){
		// return buildJson(1,"虚拟门店id重复");
		// }
		// if(vmcollegeidLong == entry.getValue().getVmCollegeId()){
		// return buildJson(1,"虚拟学校id重复");
		// }
		// }
		Storage storage = new Storage();
		storage.setStorageName(storagename);
		storage.setStorageType(storagetype);
		storage.setVmStoreId((int) vmstoreidLong);
		// 根据vmstoreid获取vmcollegeid
		storage.setVmCollegeId((int) vmcollegeidLong);
		if (storagetype == Storage.LDC_STORAGE) {
			storage.setCityId(cityid);
		} else if (storagetype == Storage.RDC_STORAGE) {
			storage.setCityId(0);
		} else if (storagetype == Storage.VM_STORAGE) {
			storage.setCityId(0);
		} else {
			return buildJson(1, "添加仓库失败，仓库类型错误");
		}
		if (storagetype != Storage.LDC_STORAGE) {
			ldcOwnerType = 0;
		}
		storage.setLdcType(ldcType);// 1:商超隐藏29分钟达 0:显示29分钟达]
		storage.setLdcOwnerType(ldcOwnerType);
		storage.setIsClose(isclose);
		storage.setOperatorId(user.getUser_id());
		storage.setIsDel(0);
		// 配送费--张帅
		storage.setFreight(-1);
		storage.setFreightSub(-1);
		storage.setFreightType(-1);
		// 第三方仓
		if (storagetype == Storage.VM_STORAGE && freightType == 1) {
			String freightStr = request.getParameter("freight" + freightSubType);
			if (!freightStr.matches("^\\d+(\\.\\d{1,2})?$")) {
				return buildJson(1, "运费必须精确到小数点后两位，且大于等于零");
			}
			long freight = MoneyUtils.yuan2Fen(NumberUtils.toDouble(freightStr));
			storage.setFreightType(freightSubType);
			storage.setFreight(freight);
			// 自定义类型
			switch (freightSubType) {
			case Storage.FREIGHT_TYPE_NONE:
				break;
			case Storage.FREIGHT_TYPE_PRICE:
				String freightSubStr = request.getParameter("freightSub");
				if (!freightSubStr.matches("^\\d+(\\.\\d{1,2})?$")) {
					return buildJson(1, "运费满减值必须精确到小数点后两位，且大于等于零");
				}
				long freightSub = MoneyUtils.yuan2Fen(NumberUtils.toDouble(freightSubStr));
				storage.setFreightSub(freightSub);
				break;
			}
		} else if (storagetype == Storage.VM_STORAGE && freightType == 2) {
			// 卖家承担运费
			storage.setFreight(0);
			storage.setFreightType(Storage.FREIGHT_TYPE_NONE);
		}

		try {
			long num = storageService.addStorage(storage);
			stroageConstant.refresh();
			// 复制全国商品(等接口)
			if (storagetype == 0)
				wmsGoodsService.copyRdcWmsGoodsToStorage(storage.getStorageId(), user.getUser_id());
			if (storagetype == 1)
				wmsGoodsService.copyLdcWmsGoodsToStorage(storage, cityid, user.getUser_id());
			return buildJson(0, "添加仓库成功");
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "添加仓库失败", "storagename:" + storagename
					+ "&storagetype:" + storagetype + "&cityid:" + cityid + "&isclose:" + isclose);
			return buildJson(1, "添加仓库失败");
		}
	}

	@RequestMapping("editStorage")
	@ResponseBody
	public Object editStorage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "0") Integer ldcType, @RequestParam("storageid") long storageid,
			@RequestParam("storagename") String storagename, @RequestParam("isclose") int isclose,
			@RequestParam String vmstoreid, @RequestParam String vmcollegeid,
			@RequestParam Integer freightType, @RequestParam Integer freightSubType,
			@RequestParam(defaultValue = "0") Integer ldcOwnerType) throws SQLException, IOException {
		try {
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			long vmstoreidLong = _.toLong(vmstoreid, 0);
			Map<Long, Storage> storageMap = StroageConstant.getStorageMap();
			// vmcollegeid在p2p服务器崩后改手动输入
			long vmcollegeidLong = _.toLong(vmcollegeid, 0);
			boolean on2closeflag = false;
			boolean close2onflag = false;
			int type = 0;
			if (vmstoreidLong != 0) {
				if (vmcollegeidLong == 0) {
					vmcollegeidLong = storeService.getCollegeIdByStoreId(vmstoreidLong);
					if (vmcollegeidLong == -1) {
						return buildJson(1, "没找到与门店id对应的学校");
					}
					if (vmcollegeidLong == -2) {
						return buildJson(2, "p2p服务器没反应,请手动输入学校id");
					}
				}
			}
			// for(Map.Entry<Long,Storage> entry :
			// storageMap.entrySet()){
			// if(vmstoreidLong == entry.getValue().getVmStoreId()){
			// return buildJson(1,"虚拟门店id重复");
			// }
			// if(vmcollegeidLong ==
			// entry.getValue().getVmCollegeId()){
			// return buildJson(1,"虚拟学校id重复");
			// }
			// }

			Storage storage = storageService.getStorageById(storageid);
			Map<String, Storage> storageNameMap = StroageConstant.getStorageNameMap();
			if (storageNameMap.get(storagename.trim()) != null
					&& !((storagename.trim()).equals(storage.getStorageName()))) {
				return buildJson(1, "编辑仓库失败,仓库名重复");
			}
			if (storage == null) {
				return buildJson(1, "未找到对应仓库");
			}
			if (storage.getIsClose() == 0 && isclose == 1) {
				// 由开到关
				on2closeflag = true;
				type = storage.getStorageType();
			}
			if (storage.getIsClose() == 1 && isclose == 0) {
				close2onflag = true;
				type = storage.getStorageType();
			}
			storage.setStorageId(storageid);
			storage.setStorageName(storagename);
			storage.setIsClose(isclose);
			storage.setOperatorId(user.getUser_id());
			// LDC
			if (storage.getStorageType() == Storage.LDC_STORAGE) {
				storage.setLdcOwnerType(ldcOwnerType);
				storage.setLdcType(ldcType);// 1:商超隐藏29分钟达
								// 0:显示29分钟达
				storage.setVmStoreId(_.toInt(vmstoreid, 0));
				// 根据vmstoreid获取vmcollegeid
				storage.setVmCollegeId((int) vmcollegeidLong);
			} else {
				storage.setLdcOwnerType(0);
			}
			// 配送费--张帅
			storage.setFreight(-1);
			storage.setFreightSub(-1);
			storage.setFreightType(-1);
			// 第三方仓
			if (storage.getStorageType() == Storage.VM_STORAGE && freightType == 1) {
				String freightStr = request.getParameter("freight" + freightSubType);
				if (!freightStr.matches("^\\d+(\\.\\d{1,2})?$")) {
					return buildJson(1, "运费必须精确到小数点后两位，且大于等于零");
				}
				long freight = MoneyUtils.yuan2Fen(NumberUtils.toDouble(freightStr));
				storage.setFreightType(freightSubType);
				storage.setFreight(freight);
				// 自定义类型
				switch (freightSubType) {
				case Storage.FREIGHT_TYPE_NONE:
					break;
				case Storage.FREIGHT_TYPE_PRICE:
					String freightSubStr = request.getParameter("freightSub");
					if (!freightSubStr.matches("^\\d+(\\.\\d{1,2})?$")) {
						return buildJson(1, "运费满减值必须精确到小数点后两位，且大于等于零");
					}
					long freightSub = MoneyUtils.yuan2Fen(NumberUtils.toDouble(freightSubStr));
					storage.setFreightSub(freightSub);
					break;
				}
			} else if (storage.getStorageType() == Storage.VM_STORAGE && freightType == 2) {
				// 卖家承担运费
				storage.setFreight(0);
				storage.setFreightType(Storage.FREIGHT_TYPE_NONE);
			}
			storageService.updateStorage(storage);
			stroageConstant.refresh();

			if (on2closeflag) {
				storageGoodsStockService.setStorageStockIsDel(storageid);
				Map<Long, College> collegeMap = CollegeConstant.getCollegeMap();
				for (Map.Entry<Long, College> entry : collegeMap.entrySet()) {
					College college = entry.getValue();
					if (college.getIsDel() == 0) {
						if (type == 0) {
							if (college.getRdcStorageId() == storageid) {
								activityGoodsService.deletActivityGoodsByCollegeId(
										college.getCollegeId(), type);
								bgGoodsService.batchDelete(college.getCollegeId(), type);
							}
						} else {
							if (college.getLdcStorageId() == storageid) {
								activityGoodsService.deletActivityGoodsByCollegeId(
										college.getCollegeId(), type);
								bgGoodsService.batchDelete(college.getCollegeId(), type);
							}
						}
					}
				}

			}

			if (close2onflag) {
				if (type == 0)
					wmsGoodsService.copyRdcWmsGoodsToStorage(storage.getStorageId(),
							user.getUser_id());
				if (type == 1)
					wmsGoodsService.copyLdcWmsGoodsToStorage(storage, storage.getCityId(),
							user.getUser_id());
			}

			return buildJson(0, "编辑仓库成功");
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "辑仓库失败", "storageid:" + storageid
					+ "&storagename:" + storagename + "&isclose:" + isclose);
			return buildJson(1, "编辑仓库失败");
		}
	}

	@RequestMapping("deleteStorage")
	@ResponseBody
	public Object deleteStorage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("storageid") long storageid) throws SQLException, IOException {
		try {
			storageService.deleteStorage(storageid);
			return buildJson(0, "删除仓库成功");
		} catch (Exception e) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "辑仓库失败", "storageid:" + storageid);
			return buildJson(1, "编辑仓库失败");
		}
	}

	@RequestMapping("postAllStorage")
	@ResponseBody
	public Object postAllStorage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Long timeStamp, @RequestParam String cipherText) throws SQLException, IOException {
		String value = Long.toString(timeStamp) + KEY;

		byte[] bs = (value).getBytes("UTF-8");
		String Token = Md5Util.md5AsLowerHex(bs);
		// String Token1 = Md5Util.md5AsUpperHex(bs);

		// Date now = new Date();
		long now = System.currentTimeMillis();
		long differ = Math.abs(now - timeStamp);
		long differminutes = differ / (1000 * 60);

		if (Token.equalsIgnoreCase(cipherText) && differminutes < 5) {
			Map<Long, City> cityMap = CityConstant.getcityMap();
			Map<Long, Storage> rdcStorageMap = StroageConstant.getRdcStorageMap();
			Map<Long, Storage> ldcStorageMap = StroageConstant.getLdcStorageMap();
			Map<Long, Storage> resultMap = new HashMap<Long, Storage>();
			for (Map.Entry<Long, Storage> entry : ldcStorageMap.entrySet()) {
				long cityId = entry.getValue().getCityId();
				City city = cityMap.get(cityId);
				entry.getValue().setCityName(city.getCityName());
				resultMap.put(entry.getKey(), entry.getValue());
			}
			if (rdcStorageMap != null && resultMap != null) {
				resultMap.putAll(rdcStorageMap);
				return CommonUtil.asMap("code", 0, "msg", "success", "data", resultMap);
			} else {
				return CommonUtil.asMap("code", 1, "msg", "fail");
			}
		}
		return CommonUtil.asMap("code", 1, "msg", "验证不通过");
	}

	@ResponseBody
	@RequestMapping("/getRegion")
	public Object getRegion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "0") Long storageId) throws SQLException, IOException {
		List<Long> collegeIdList = storageCollegeService.getCollegeIdListByStorageId(storageId);
		ZtreeBean ztreeBean = ztreeUtil.getCityCollegeZtree(collegeIdList);
		return buildSuccJson(ztreeBean);
	}

	@ResponseBody
	@RequestMapping("/setRegion")
	public Object setRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String storageIdStr = request.getParameter("storageId");
		String treeInfo = request.getParameter("treeInfo");
		if (storageIdStr == null || !storageIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("仓库ID错误，id是：" + storageIdStr);
			return buildJson(1, "仓库ID错误，请联系管理员~");
		}
		List<ZtreeBean> bean = gson.fromJson(treeInfo, new TypeToken<List<ZtreeBean>>() {
		}.getType());
		if (bean == null || bean.size() == 0) {
			return buildJson(1, "获取范围信息为空~");
		}
		long storageId = _.toLong(storageIdStr);
		ZtreeBean ztreeBean = bean.get(0);
		Map<String, Object> result = storageService.setVMRegion(storageId, ztreeBean);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getStorageById")
	public Object getStorageById(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		String storageIdStr = request.getParameter("storageId");
		if (storageIdStr == null || !storageIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("仓库ID错误，id是：" + storageIdStr);
			return buildJson(1, "仓库ID错误，请联系管理员~");
		}
		long storageId = NumberUtils.toLong(storageIdStr);
		Storage storageTemp = StroageConstant.getStorageById(storageId);
		if (storageTemp == null) {
			storageTemp = storageService.getStorageById(storageId);
		}
		return buildSuccJson(storageTemp);
	}
}
