package com.mall.admin.web.wms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.SellerConstant;
import com.mall.admin.model.bean.base.StorageTypeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.util.FileUtils;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.seller.Seller;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;

@Controller
@RequestMapping("/storage/")
public class StorageGoodsStockController extends BaseController {
	@Autowired
	private StorageGoodsStockService wmsGoodsService;

	@Autowired
	private StorageGoodsRecordService storageGoodsRecordService;

	@Autowired
	private StorageService storageService;

	@Autowired
	CityService cityService;
	// @Autowired
	// private StroageConstant stroageConstant;

	@Autowired
	RecordTypeService recordTypeService;

	@RequestMapping("goodsstock/view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		LogConstant.mallLog.info("StorageGoodsStockController storageGoodsStock");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Seller> sellerList = new ArrayList<Seller>();

		Map<Long, Seller> sellerMap = SellerConstant.getSellerMap();
		if (sellerMap == null || sellerMap.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建供应商~"));
		}
		for (Seller seller : sellerMap.values()) {
			sellerList.add(seller);
		}
		List<Storage> storageList = new ArrayList<Storage>();
		List<StorageTypeBean> storageTypeList = new ArrayList<StorageTypeBean>();
		// 检查用户是否有管理的仓
		if (user.getAllStorageList().size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户可管理的仓库为空，请维护用户的仓库信息~"));
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
		StorageTypeBean defaultTypeBean = storageTypeList.get(0);
		if (Constants.RDC_STORAGE.equals(defaultTypeBean.getTypeId())) {
			// 有rdc仓
			storageList = user.getRdcStorageList();
		} else if(Constants.LDC_STORAGE.equals(defaultTypeBean.getTypeId())) {
			// 如果只有ldc仓，则不需要按类目分权限
			storageList = user.getLdcStorageList();
		}else if(Constants.VM_STORAGE.equals(defaultTypeBean.getTypeId())){
			storageList = user.getVmStorageList();
		}
		Storage defaultStorage = storageList.get(0);
		List<District> districtList = cityService.getDistrictList();
		District defaultDistrict = new District();
		defaultDistrict.setCity(new City());
		for (District d : districtList) {
			if (d.getCity().getCityId() == defaultStorage.getCityId()) {
				defaultDistrict = d;
			}
		}
		Map<Integer, RecordType> recordTypeMap = new HashMap<Integer, RecordType>();
		List<RecordType> RecordTypeList = recordTypeService.getAll();

		if (RecordTypeList == null || RecordTypeList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建记录类型~"));
		}

		if (!RecordTypeList.isEmpty()) {
			for (RecordType recordType : RecordTypeList) {
				recordTypeMap.put(recordType.getId(), recordType);
			}
		}
		if (districtList == null || districtList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建城市~"));
		}
		if (storageList == null || storageList.size() == 0) {
			storageList = user.getLdcStorageList();
			if (storageList == null || storageList.size() == 0) {
				return new ModelAndView("info", ImmutableMap.of("message", "请为该用户添加仓库~"));
			}
		}
		Set<Long> cityIdList = new HashSet<Long>();
		// 获得负责的城市id
		if (user.getLdcStorageList() != null && user.getLdcStorageList().size() > 0) {
			List<Storage> ldcStorageList = user.getLdcStorageList();
			for (Storage storage : ldcStorageList) {
				cityIdList.add(storage.getCityId());
			}
		}
		List<District> userDistrictList = new ArrayList<District>();
		for (District district : districtList) {
			if (cityIdList.contains(district.getCity().getCityId())) {
				userDistrictList.add(district);
			}
		}
		return new ModelAndView("goodsstock/goodsstock", _.asMap("storageTypeList", storageTypeList,
				"defaultTypeBean", defaultTypeBean, "sellerList", sellerList, "districtList",
				userDistrictList, "defaultDistrict", defaultDistrict, "storageList", storageList,
				"defaultStorage", defaultStorage, "recordTypeMap", recordTypeMap));
	}

	@RequestMapping("goodsstock/queryStorageGoodsStock")
	@ResponseBody
	public Object queryCollege(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String numPerPage,
			@RequestParam(value = "searchStr", required = false) String searchStr,
			@RequestParam(value = "storageid", required = false) String storageid,
			@RequestParam(value = "storagetype", required = false) String storagetype) throws SQLException,
			IOException {
		int startInt = _.toInt(start, 0);
		int numPerPageInt = _.toInt(numPerPage, 10);
		int storageidInt = _.toInt(storageid, 1);
		int storagetypeInt = _.toInt(storagetype, 0);
		int wmsgooodsid = _.toInt(searchStr, 0);
		if (storageidInt == 0 || storagetypeInt == -1) {
			return buildJson(0, "参数错误~");
		}
		// if(wmsgooodsid == 0){
		// return buildJson(0,"商品编号输入错误~");
		// }
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPageInt);
		paginationInfo.setCurrentPageByStartNum(startInt, numPerPageInt);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("searchStr", searchStr);
		paramMap.put("storageId", storageid);
		paramMap.put("storageType", storagetype);
		Pair<Long, PaginationList<StorageGoodsStock>> p = wmsGoodsService.getPageStorageGoodsStock(
				paginationInfo, paramMap);
		int total = 0;
		long filteredTotal = p.getLeft();
		List<StorageGoodsStock> storageGoodsStockList = p.getRight();
		return gson.toJson(buildDataTableResult(draw, total, filteredTotal, storageGoodsStockList));
	}

	@RequestMapping("goodsstock/addStock")
	@ResponseBody
	public Object addStock(HttpServletRequest request, HttpServletResponse response, @RequestParam long goodsid,
			@RequestParam long storageid, @RequestParam long seller, @RequestParam String price,
			@RequestParam int num, @RequestParam int recordtype, @RequestParam String recordcode,
			@RequestParam(defaultValue = "") String remark) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		StorageGoodsStock storageGoodsStock = wmsGoodsService.getStorageGoodsStockByStorageGoodsId(goodsid);
		long currentStock = storageGoodsStock.getStock();
		long addedStock = currentStock + num;
		storageGoodsStock.setStock(addedStock);
		try {
			wmsGoodsService.update(storageGoodsStock);
			LogConstant.mallLog.info(new Date() + "[update stock:add]wmsGoodsId:"+goodsid+"|storageId:"+storageid+"|originStock:"+currentStock+"|addStock:"+num+"|totalStock:"+addedStock);
		} catch (Exception e) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "更新商品库存失败", "goodsid:" + goodsid);
			return buildJson(1, "更新商品库存失败");
		}
		StorageGoodsRecord storageGoodsRecord = new StorageGoodsRecord();
		storageGoodsRecord.setGoodsId(storageGoodsStock.getWms_goods_id());
		storageGoodsRecord.setStorageId(storageid);
		storageGoodsRecord.setNum(num);
		storageGoodsRecord.setPrice((int) MoneyUtils.yuan2Fen(Double.parseDouble(price)));
		// storageGoodsRecord.setAccount_price((int)
		// MoneyUtils.yuan2Fen(price));
		// storageGoodsRecord.setAccount_num(num);
		storageGoodsRecord.setSellerId(seller);
		storageGoodsRecord.setRecordtype(recordtype);
		storageGoodsRecord.setOperator(user.user_id);
		storageGoodsRecord.setModify_remark(remark);
		storageGoodsRecord.setRecordcode(recordcode.trim());
		storageGoodsRecord.setCreator(user.user_id);
		try {
			storageGoodsRecordService.insert(storageGoodsRecord);
		} catch (Exception e) {
			LogConstant.mallLog.error("code:{},msg:{}", 1, "添加库存修改记录失败");
			return buildJson(1, "添加库存修改记录失败");
		}
		return buildJson(0, "入库成功");
	}

	@RequestMapping("goodsstock/ajustStock")
	@ResponseBody
	public Object ajustStock(HttpServletRequest request, HttpServletResponse response, @RequestParam long goodsid,
			@RequestParam long storageid, @RequestParam long seller, @RequestParam String price,
			@RequestParam int num, @RequestParam int selectedtype, @RequestParam String recordcode,
			@RequestParam String remark) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		StorageGoodsStock storageGoodsStock = wmsGoodsService.getStorageGoodsStockByStorageGoodsId(goodsid);
		long currentStock = storageGoodsStock.getStock();
		RecordType recordType = recordTypeService.getById(selectedtype);
		int ratio = recordType.getFlag();
		if (ratio != 1 && ratio != -1) {
			return buildJson(1, "系数错误");
		}
		long addedStock = currentStock + num * ratio;
		if (addedStock < 0) {
			return buildJson(1, "更新商品库存失败,库存数不能小于0");
		}
		storageGoodsStock.setStock(addedStock);
		try {
			wmsGoodsService.update(storageGoodsStock);
			LogConstant.mallLog.info(new Date() + "[update stock:type"+selectedtype+"]wmsGoodsId:"+goodsid+"|storageId:"+storageid+"|originStock:"+currentStock+"|addStock:"+num+"|totalStock:"+addedStock);
		} catch (Exception e) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}", 1, "更新商品库存失败", "goodsid:" + goodsid);
			return buildJson(1, "更新商品库存失败");
		}
		StorageGoodsRecord storageGoodsRecord = new StorageGoodsRecord();
		storageGoodsRecord.setGoodsId(storageGoodsStock.getWms_goods_id());
		storageGoodsRecord.setStorageId(storageid);
		// storageGoodsRecord.setAccount_price((int)
		// MoneyUtils.yuan2Fen(Double.parseDouble(price)));
		// storageGoodsRecord.setAccount_num(num);
		storageGoodsRecord.setPrice((int) MoneyUtils.yuan2Fen(Double.parseDouble(price)));
		storageGoodsRecord.setNum(num);
		storageGoodsRecord.setSellerId(seller);
		storageGoodsRecord.setRecordtype(selectedtype);
		storageGoodsRecord.setOperator(user.user_id);
		storageGoodsRecord.setModify_remark(remark);
		storageGoodsRecord.setRecordcode(recordcode.trim());
		storageGoodsRecord.setCreator(user.user_id);
		try {
			storageGoodsRecordService.insert(storageGoodsRecord);
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{}", 1, "添加库存修改记录失败");
			return buildJson(1, "添加库存修改记录失败");
		}
		return buildJson(0, "调整库存成功");
	}

	@RequestMapping("goodsstock/ajaxStorage")
	@ResponseBody
	public Object ajaxStorage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int storagetype, @RequestParam int cityid) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> tempStorageList = null;
		List<Storage> storageList = new ArrayList();
		if (storagetype == Storage.RDC_STORAGE) {
			// rdc仓与城市无关
			storageList = user.getRdcStorageList();
		}else if (storagetype == Storage.VM_STORAGE) {
			// rdc仓与城市无关
			storageList = user.getVmStorageList();
		} else if (storagetype == Storage.LDC_STORAGE) {
			tempStorageList = user.getLdcStorageList();
			for (Storage storage : tempStorageList) {
				if (storage.getCityId() == cityid) {
					storageList.add(storage);
				}
			}
		}
		if (storageList.size() == 0) {
			// storageList = new ArrayList<Storage>();
			return buildJson(1, "未获取到该城市下相应类型的仓库");
		}
		return ImmutableMap.of("storageList", storageList);
	}

	/**
	 * 导入文件
	 * 
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 */
	@RequestMapping("goodsstock/importFile")
	@ResponseBody
	public Object importFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile myfiles, @RequestParam long storageId) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		response.setContentType("text/html;charset=UTF-8");
		String type = request.getParameter("type");
		String fileName = myfiles.getOriginalFilename();
		if (fileName == null) {
			return buildJson(1, "导入文件名为空~");
		}
		if (!fileName.endsWith("csv")) {
			return buildJson(1, "导入文件格式错误~");
		}
		try {
			List<String[]> data = FileUtils.readCsv(myfiles.getInputStream());
			String message = wmsGoodsService.importGoods(data, user.user_id, storageId);
			if (!"0".equals(message)) {
				return buildJson(1, message);
			} else {
				return buildJson(0, "批量入库成功~");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return buildJson(0, "导入文件异常，" + e.getMessage());
		}
	}

}
