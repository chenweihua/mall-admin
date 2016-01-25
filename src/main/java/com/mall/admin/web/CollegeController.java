package com.mall.admin.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.banner.BannerService;
import com.mall.admin.service.couponRule.impl.CouponRuleImpl;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.store.StoreService;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/college/")
public class CollegeController extends BaseController {

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private CityService cityService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private CollegeConstant collegeConstant;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private BgGoodsService bgGoodsService;

	@Autowired
	private BannerService bannerService;

	@Autowired
	private CouponRuleImpl couponRuleImpl;

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StorageCollegeService storageCollegeService;

	@RequestMapping("view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		LogConstant.mallLog.info("CollegeController college");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		List<District> districtList = cityService.getDistrictList();
		City defaultCity = null;

		if (districtList != null && districtList.size() > 0) {
			defaultCity = districtList.get(0).getCity();
		} else {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建城市~"));
		}

		List<Storage> ldcStorageList = null;

		if (defaultCity != null) {
			ldcStorageList = storageService.getLdcStorageByCityId(defaultCity.getCityId());
		}

		List<Storage> rdcStorageList = user.getRdcStorageList();

		List<Storage> tempLdcStorageList = user.getLdcStorageList();
		if (tempLdcStorageList != null && ldcStorageList != null) {
			ldcStorageList.retainAll(tempLdcStorageList);
		}

		// if (rdcStorageList == null || rdcStorageList.size() == 0) {
		// return new ModelAndView("info", ImmutableMap.of("message",
		// "没有该用户权限下的RDC仓,请先创建RDC仓~"));
		// }

		// if (ldcStorageList == null || ldcStorageList.size() == 0) {
		// return new ModelAndView("info", ImmutableMap.of("message",
		// "没有该用户权限下的LDC仓,请先创建LDC仓~"));
		// }

		return new ModelAndView("college/collegelist", ImmutableMap.of("districtList", districtList,
				"rdcStorageList", rdcStorageList, "ldcStorageList", ldcStorageList));
	}

	@RequestMapping("queryCollege")
	@ResponseBody
	public Object queryCollege(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String numPerPage,
			@RequestParam(value = "searchStr", required = false) String searchStr,
			@RequestParam(value = "cityid", required = false) String cityid) throws SQLException,
			IOException {

		int startInt = _.toInt(start, 0);
		int limit = _.toInt(numPerPage, 10);
		int cityId = _.toInt(cityid, 0);

		List<College> collegeList;
		String keyword = "";
		Pair<Long, List<College>> p = collegeService.getPageCollege(cityId, searchStr, startInt, limit);
		collegeList = p.getRight();
		long filteredTotal = p.getLeft();
		long total = 0;
		for (College college : collegeList) {
			City city = CityConstant.getcityById(college.getCityId());
			City area = CityConstant.getcityById(college.getAreaId());
			String cityName = "未知";
			String areaName = "未知";
			if (city != null) {
				cityName = city.getCityName();
			}
			if(area != null){
				areaName = area.getCityName();
			}

			String city_name = cityName + " " + areaName;

			String rdcstorage_name = "无";
			String ldcstorage_name = "无";

			if (college.getRdcStorageId() != 0) {
				Storage rdcStorage = storageService.getStorageById(college.getRdcStorageId());
				if (rdcStorage != null) {
					rdcstorage_name = rdcStorage.getStorageName();
				}
			}

			if (college.getLdcStorageId() != 0) {
				Storage ldcStorage = storageService.getStorageById(college.getLdcStorageId());
				if (ldcStorage != null) {
					ldcstorage_name = ldcStorage.getStorageName();
				}
			}
			college.setCityName(city_name);
			college.setRdcStorageName(rdcstorage_name);
			college.setLdcStorageName(ldcstorage_name);
		}
		return gson.toJson(buildDataTableResult(draw, total, filteredTotal, collegeList));
	}

	@RequestMapping("addCollege")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	// @Transactional(propagation=Propagation.REQUIRED,
	// isolation=Isolation.READ_COMMITTED)
	public Object addCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String storeid, @RequestParam(required = false) String collegeid,
			@RequestParam(required = false) String collegename,
			@RequestParam(required = false) Long cityid, @RequestParam(required = false) Long areaid,
			@RequestParam(required = false) Long ldcstorageid,
			@RequestParam(required = false) Long rdcstorageid,
			@RequestParam(required = false) BigDecimal latitude,
			@RequestParam(required = false) BigDecimal longitude,
			@RequestParam(required = false) Byte rdcDeliveryType,
			@RequestParam(required = false) String rdcDeliveryAddress,
			@RequestParam(required = false) Byte ldcDeliveryType,
			@RequestParam(required = false) String collegeAddr,
			@RequestParam(required = false) String ldcDeliveryAddress) throws SQLException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		// long collegeidLong = _.toLong(collegeid, 0);
		int storeidInt = _.toInt(storeid, 0);
		long collegeidLong = 0;
		College college = new College();
		if (collegeidLong == 0) {
			collegeidLong = storeService.getCollegeIdByStoreId(storeidInt);
			if (collegeidLong == -1) {
				return buildJson(1, "没找到与门店id对应的学校");
			}
			if (collegeidLong == -2) {
				return buildJson(2, "p2p服务器没反应,请手动输入学校id");
			}
		}

		Map<Long, College> collegeMap = CollegeConstant.getCollegeMap();
		if (collegeMap.get(collegeidLong) != null) {
			return buildJson(1, "该门店id对应的学校已存在");
		}

		college.setStoreId(storeidInt);
		// 等借口
		college.setCollegeId(collegeidLong);
		college.setCollegeName(collegename);

		college.setLdcStorageId(ldcstorageid);
		college.setRdcStorageId(rdcstorageid);
		college.setIsOpen((byte) 1);
		college.setIsShow((byte) 0);
		college.setIsDel((byte) 0);
		college.setOperator(user.getUser_id());
		college.setLatitude(latitude);
		college.setLongitude(longitude);
		college.setRdcDeliveryType(rdcDeliveryType);
		college.setRdcDeliveryAddress(rdcDeliveryAddress);
		college.setLdcDeliveryType(ldcDeliveryType);
		college.setLdcDeliveryAddress(ldcDeliveryAddress);
		college.setAreaId(areaid);

		college.setCityId(cityid);
		college.setCollegeAddr(collegeAddr);
		Date now = new Date();
		college.setCreateTime(now);
		college.setUpdateTime(now);

		try {
			collegeService.addCollege(college);

			collegeConstant.refresh();

			bgGoodsService.batchCopy(college);

			bannerService.batchCopy(college, user);

			couponRuleImpl.autoInsertCouponRule(cityid, collegeidLong, user.getUser_id());

			activityService.autoInsertActivity(cityid, collegeidLong, user.getUser_id());
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "添加学校失败");
		}
		return buildJson(0, "添加学校成功");
	}

	@RequestMapping("editCollege")
	@ResponseBody
	public Object editCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String collegeid,
			@RequestParam(required = false) String collegename,
			@RequestParam(required = false) Byte isShow, @RequestParam(required = false) Byte isOpen,
			@RequestParam(required = false) BigDecimal latitude,
			@RequestParam(required = false) BigDecimal longitude,
			@RequestParam(required = false) Byte rdcDeliveryType,
			@RequestParam(required = false) String rdcDeliveryAddress,
			@RequestParam(required = false) Byte ldcDeliveryType,
			@RequestParam(required = false) String ldcDeliveryAddress,
			@RequestParam(required = false) Long ldcstorageid4edit,
			@RequestParam(required = false) Long rdcstorageid,
			@RequestParam(required = false) String collegeAddr,
			@RequestParam(required = false) Long ldcstorageid) throws SQLException, IOException {

		long collegeidLong = _.toLong(collegeid, 0);

		// Map<Long,College> collegeMap =
		// CollegeConstant.getCollegeMap();
		// if(collegeMap.get(collegeidLong) != null){
		// return buildJson(1,"学校id重复");
		// }

		College college = collegeService.getCollegeById(collegeidLong);
		if (college == null) {
			return buildJson(1, "未找到对应学校");
		}

		// 等借口
		// college.setCollegeId(collegeid);
		college.setCollegeAddr(collegeAddr);
		college.setCollegeName(collegename);
		college.setIsOpen(isOpen);
		college.setIsShow(isShow);
		college.setLatitude(latitude);
		college.setLongitude(longitude);
		college.setRdcDeliveryType(rdcDeliveryType);
		college.setRdcDeliveryAddress(rdcDeliveryAddress);
		college.setLdcDeliveryType(ldcDeliveryType);
		college.setLdcDeliveryAddress(ldcDeliveryAddress);
		if(ldcstorageid4edit == 0){
			college.setLdcStorageId(ldcstorageid);
		}
		college.setRdcStorageId(rdcstorageid);
		// City city = CityConstant.getcityById(cityid);
		//
		// // 一般情况下，学校对应区
		// if (city.getPid() == 0) {
		// college.setCityId(cityid);
		// college.setAreaId(cityid);
		// } else {
		// college.setCityId(city.getPid());
		// college.setAreaId(cityid);
		// }

		collegeService.updateCollege(college);

		collegeConstant.refresh();

		return buildJson(0, "编辑学校成功");
	}

	@RequestMapping("deletecollege")
	@ResponseBody
	public Object deletecollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String collegeId) throws SQLException, IOException {
		try {
			collegeService.deleCollege(_.toLong(collegeId, 0));
			collegeConstant.refresh();
			return buildJson(0, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "删除失败");
		}
	}

	// City管理模块还未创建，先用模拟数据

	@RequestMapping("ajaxCity")
	@ResponseBody
	public Object ajaxCity(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		List<District> districtList = cityService.getDistrictList();
		List<Storage> rdcStorages = storageService.getAllRdcStorage();
		// List<Storage> ldcStorages =
		// storageService.getAllLdcStorage();\
		List<Storage> ldcStorages = storageService
				.getLdcStorageByCityId(districtList.get(0).getCity().getPid());
		return _.asMap("districtList", districtList, "rdcStorages", rdcStorages, "ldcStorages", ldcStorages);
	}

	@RequestMapping("ajaxLdcByCityId")
	@ResponseBody
	public Object ajaxLdcByCityId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Long cityId) throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		City city = CityConstant.getcityById(cityId);
		List<Storage> ldcStorageList;

		ldcStorageList = storageService.getLdcStorageByCityId(city.getCityId());

		List<Storage> tempLdcStorageList = user.getLdcStorageList();

		ldcStorageList.retainAll(tempLdcStorageList);

		if (ldcStorageList == null || ldcStorageList.size() == 0) {
			return buildJson(1, "没有获取到" + city.getCityName() + "下ldc仓信息");
		}

		return _.asMap("ldcStorages", ldcStorageList);
	}

	@RequestMapping("getCollegeByStorageId")
	@ResponseBody
	public Object getCollegeByStorageId(HttpServletRequest request, HttpServletResponse response) {
		String storageTypeFlag = request.getParameter("stoagetypeflag");
		String storageId = request.getParameter("storageId");
		List<College> collegeList = new ArrayList<College>();
		if(storageTypeFlag.equals("-1")){
			Storage storage = storageService.getStorageById(_.toLong(storageId));
			if(storage.getStorageType() == Storage.RDC_STORAGE){
				collegeList = collegeService.getListByRdcStorageId(Long.parseLong(storageId));
			}else if(storage.getStorageType() == Storage.LDC_STORAGE){
				collegeList = collegeService.getListByLdcStorageId(Long.parseLong(storageId));
			}
		}else if (Constants.RDC_STORAGE.equals(storageTypeFlag)) {
			collegeList = collegeService.getListByRdcStorageId(Long.parseLong(storageId));
		} else if (Constants.LDC_STORAGE.equals(storageTypeFlag)) {
			collegeList = collegeService.getListByLdcStorageId(Long.parseLong(storageId));
		} else if(Constants.VM_STORAGE.equals(storageTypeFlag)){
			List<Long> collegeIdList = storageCollegeService.getCollegeIdListByStorageId(Long.parseLong(storageId));
			for(Long collegeId : collegeIdList){
				College temp = CollegeConstant.getCollegeById(collegeId);
				if(temp != null){
					collegeList.add(temp);
				}
			}
		}
		return buildJson(0, "查询成功~", ImmutableMap.of("college", collegeList));
	}
}
