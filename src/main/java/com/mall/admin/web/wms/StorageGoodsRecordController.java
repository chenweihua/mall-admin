package com.mall.admin.web.wms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.ApplyPayUtil;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.FileDownLoadUtil;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.SellerConstant;
import com.mall.admin.model.bean.base.StorageTypeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.ApplyPayLogService;
import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.storage.StorageRecordModifylogService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.seller.Seller;
import com.mall.admin.vo.storage.ApplyPayLog;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.storage.StorageRecordModifylog;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

@Controller
@RequestMapping("/storage")
public class StorageGoodsRecordController extends BaseController {

	@Autowired
	StorageGoodsRecordService storageGoodsRecordService;

	@Autowired
	WmsGoodsService wmsGoodsService;

	@Autowired
	UserService userService;

	@Autowired
	ApplyPayLogService applyPayLogService;

	@Autowired
	StorageRecordModifylogService storageRecordModifylogService;

	@Autowired
	StorageGoodsStockService storageGoodsStockService;

	@Autowired
	CityService cityService;

	@Autowired
	StorageService storageService;

	@Autowired
	RecordTypeService recordTypeService;

	private final int DEFAULT_ACCOUNT_MONEY = 0;
	private final int DEFAULT_APPLY_STATUS = 0;

	@RequestMapping("stockrecord/view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		LogConstant.mallLog.info("StorageGoodsRecordController storageGoodsRecordController");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		// map对象传入后台用${}方式获取会报错，未找到解决方法，转成list传
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
		if (districtList == null || districtList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建城市~"));
		}
		District defaultDistrict = new District();
		defaultDistrict.setCity(new City());
		for (District d : districtList) {
			if (d.getCity().getCityId() == defaultStorage.getCityId()) {
				defaultDistrict = d;
			}
		}

		List<RecordType> recordTypeList = recordTypeService.getAll();
		if (recordTypeList == null || recordTypeList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建记录类型~"));
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

		return new ModelAndView("goodsrecord/goodsrecord", _.asMap("sellerList", sellerList, "storageTypeList",
				storageTypeList, "defaultTypeBean", defaultTypeBean, "districtList", userDistrictList,
				"defaultDistrict", defaultDistrict, "storageList", storageList, "defaultStorage",
				defaultStorage, "recordTypeList", recordTypeList, "user", user));
	}

	@RequestMapping("stockrecord/queryStorageGoodsRecord")
	@ResponseBody
	public Object queryCollege(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String numPerPage,
			@RequestParam(required = false) Long sellerid, @RequestParam(required = false) Long storageid,
			@RequestParam(required = false) Long recordtype,
			@RequestParam(required = false) String searchStr,
			@RequestParam(required = false) String paystatus,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate)
			throws SQLException, IOException {
		int startInt = _.toInt(start, 0);
		int numPerPageInt = _.toInt(numPerPage, 10);

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPageInt);
		paginationInfo.setCurrentPageByStartNum(startInt, numPerPageInt);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		if (startDate != null && startDate.trim().length() > 0) {
			try {
				beginTime = format.parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (endDate != null && endDate.trim().length() > 0) {
			try {
				endTime = format.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		Map paramMap = new HashMap();
		paramMap.put("seller_id", sellerid);
		paramMap.put("storage_id", storageid);
		paramMap.put("record_type_id", recordtype);
		paramMap.put("searchStr", searchStr);
		paramMap.put("pay_status", paystatus);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);

		Pair<Long, PaginationList<StorageGoodsRecord>> p = storageGoodsRecordService.getPageStorage(
				paginationInfo, paramMap);
		int total = 0;
		long filteredTotal = p.getLeft();
		List<StorageGoodsRecord> storageGoodsRecordList = p.getRight();
		Map<Long, City> cityMap = CityConstant.getcityMap();
		return _.asMap("draw", draw, "recordsTotal", total, "recordsFiltered", filteredTotal, "data",
				storageGoodsRecordList, "start", start, "cityMap", cityMap);
	}

	/**
	 * 将入库记录添加到结算池
	 * 
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param searchstr
	 * @param sellerId
	 */
	@RequestMapping("stockrecord/addtopool")
	@ResponseBody
	public Object addRecordToPool(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long storageid) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String goodsRecordIdsStr = request.getParameter("goodsrecordids");
		System.out.println(goodsRecordIdsStr);
		if (goodsRecordIdsStr == null || goodsRecordIdsStr.trim().length() < 1) {
			return buildJson(1, "添加失败，待申请的入库记录为空~");
		}
		goodsRecordIdsStr = goodsRecordIdsStr.substring(0, goodsRecordIdsStr.length() - 1);
		if (goodsRecordIdsStr.trim().length() < 1) {
			return buildJson(1, "添加失败，待申请的入库记录为空~");
		}
		String[] goodsRecordIds = goodsRecordIdsStr.split(",");

		// 判断是否是同一个供应商，同一笔结算只能是同一个供应商
		List<StorageGoodsRecord> goodsRecordList = new ArrayList<StorageGoodsRecord>();
		long sellerId = -1;
		for (String id : goodsRecordIds) {
			StorageGoodsRecord goodsRecord = storageGoodsRecordService.queryById(Long.parseLong(id));
			goodsRecordList.add(goodsRecord);
			if (sellerId == -1) {
				sellerId = goodsRecord.sellerId;
			} else {
				if (goodsRecord.sellerId != sellerId) {
					return buildJson(1, "添加失败，待添加到结算池中的记录不是同一供应商~");
				}
			}
		}
		List<StorageGoodsRecord> lockRecordList = storageGoodsRecordService.getGoodsRecordByLockUserId(
				user.user_id, storageid);
		if (lockRecordList != null && lockRecordList.size() > 0) {
			for (StorageGoodsRecord goodsRecord : lockRecordList) {
				if (sellerId != goodsRecord.sellerId) {
					return buildJson(1, "添加失败，待添加到结算池中的记录和已添加到结算池的记录不是同一个供应商~");
				}
			}
		}
		// 加锁
		for (String id : goodsRecordIds) {
			storageGoodsRecordService.lockRecord(Long.parseLong(id.trim()), user.user_id);
		}
		return buildJson(0, "添加成功~");
	}

	/**
	 * 将入库记录添加到结算池
	 * 
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param searchstr
	 * @param sellerId
	 */
	@RequestMapping("stockrecord/removefrompool")
	@ResponseBody
	public Object removeFromPool(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String recordIdStr = request.getParameter("recordId");
		if (!recordIdStr.matches("[1-9][0-9]{0,}")) {
			return buildJson(1, "取消失败，编号错误~");
		}

		long recordId = Long.parseLong(recordIdStr);
		StorageGoodsRecord record = storageGoodsRecordService.queryById(recordId);

		if (!(record.getPay_status() == 0 && record.getLock_status() == 1)) {
			return buildJson(1, "取消失败，取消的记录状态错误~");
		}
		if (record.getLock_user() != user.getUser_id()) {
			return buildJson(1, "取消失败，操作员错误~");
		}
		try {
			storageGoodsRecordService.unlockRecord(recordId, user.getUser_id());
			return buildJson(0, "取消成功~");

		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "取消失败~");
		}
	}

	/**
	 * 用户对入库记录加锁
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("stockrecord/lockRecordList")
	@ResponseBody
	public Object getLockRecordlist(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage, @RequestParam(required = false) Long sellerid,
			@RequestParam(required = false) Long storageid,
			@RequestParam(required = false) Long recordtype,
			@RequestParam(required = false) String searchStr,
			@RequestParam(required = false) String paystatus,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Long> goodsIds = new ArrayList<Long>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPage);
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		if (startDate != null && startDate.trim().length() > 0) {
			try {
				beginTime = format.parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (endDate != null && endDate.trim().length() > 0) {
			try {
				endTime = format.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		Map paramMap = new HashMap();
		paramMap.put("lock_user", user.getUser_id());
		// paramMap.put("seller_id", sellerid);
		paramMap.put("storage_id", storageid);
		paramMap.put("record_type_id", recordtype);
		paramMap.put("searchStr", searchStr);
		paramMap.put("lock_status", 1);
		paramMap.put("pay_status", 0);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);

		try {
			Pair<Long, PaginationList<StorageGoodsRecord>> p = storageGoodsRecordService.getPageStorage(
					paginationInfo, paramMap);
			long total = 0;
			long filteredTotal = p.getLeft();
			List<StorageGoodsRecord> goodsRecordList = p.getRight();
			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				// List<Seller> sellerList = Seller.getAll();
				Map<Long, Seller> sellerMap = SellerConstant.getSellerMap();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					// List<WmsGoods> wmsGoods =
					// wmsGoodsService.queryById(goodsRecord.goodsId);
					WmsGoods wmsGoods = wmsGoodsService.queryById(goodsRecord.goodsId);
					if (wmsGoods != null) {
						// goodsRecord.gbm =
						// goods.get(0).gbm;
						// goodsRecord.goodsName =
						// goods.get(0).name;

					}
					for (Seller seller : sellerMap.values()) {
						if (goodsRecord.sellerId == seller.getSellerId()) {
							goodsRecord.sellerName = seller.getSellerName();
						}
					}
					if (goodsRecord.getLock_user() != 0) {
						User u = userService.getUserById((long) goodsRecord.getLock_user());
						goodsRecord.setLock_userName(u.getUser_name());
					}

				}
			}
			return _.asMap("draw", draw, "recordsTotal", total, "recordsFiltered", filteredTotal, "data",
					goodsRecordList, "start", start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return _.asMap("draw", draw, "recordsTotal", 0, "recordsFiltered", 0, "data",
					new ArrayList<StorageGoodsRecord>(), "start", start);
		}
	}

	/**
	 * 确认申请的支付信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("stockrecord/applycheck")
	@ResponseBody
	public Object applycheck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Long storageid,
			@RequestParam(required = false) Long recordtype,
			@RequestParam(required = false) String searchStr,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		if (startDate != null && startDate.trim().length() > 0) {
			try {
				beginTime = format.parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (endDate != null && endDate.trim().length() > 0) {
			try {
				endTime = format.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		Map paramMap = new HashMap();

		paramMap.put("lock_user", user.getUser_id());
		paramMap.put("storage_id", storageid);
		paramMap.put("record_type_id", recordtype);
		paramMap.put("searchStr", searchStr);
		paramMap.put("lock_status", 1);
		paramMap.put("pay_status", 0);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);

		long sumMoney = 0;
		String sellerName = "未知";
		long sellerId = 0;
		int num = 0;
		try {
			Pair<Long, List<StorageGoodsRecord>> p = storageGoodsRecordService.getStorage(paramMap);
			List<StorageGoodsRecord> goodsRecordList = p.getRight();
			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				num = goodsRecordList.size();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					sumMoney += goodsRecord.getNum() * goodsRecord.getPrice();
					sellerId = goodsRecord.sellerId;
				}
			}
			Seller seller = SellerConstant.getSellerById(sellerId);

			if (seller != null) {
				sellerName = seller.getSellerName();
			}

		} catch (Exception e) {
			buildJson(1, "统计结算池信息失败，错误信息：" + e.getStackTrace() + "~");

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("summoney", sumMoney);
		map.put("sellername", sellerName);
		map.put("num", num);
		return buildJson(0, "添加成功~", map);
	}

	/**
	 * 确认申请的支付信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("stockrecord/applypay")
	@ResponseBody
	public Object applyPay(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Long storageId,
			@RequestParam(required = false) Long recordtype,
			@RequestParam(required = false) String searchStr,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String applyPayCode = ApplyPayUtil.applyCode();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		if (startDate != null && startDate.trim().length() > 0) {
			try {
				beginTime = format.parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (endDate != null && endDate.trim().length() > 0) {
			try {
				endTime = format.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		Map paramMap = new HashMap();
		paramMap.put("lock_user", user.user_id);
		paramMap.put("storage_id", storageId);
		paramMap.put("record_type_id", recordtype);
		paramMap.put("searchStr", searchStr);
		paramMap.put("lock_status", 1);
		paramMap.put("pay_status", 0);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);

		long sumMoney = 0;
		long sellerId = 0;
		int num = 0;
		try {
			// goodsRecordPair =
			// GoodsRecord.getGoodsRecordBySearch(query, 0, 0);
			Pair<Long, List<StorageGoodsRecord>> recordPair = storageGoodsRecordService
					.getStorage(paramMap);
			if (recordPair.getLeft() < 1) {
				buildJson(1, "统计结算池信息失败，结算池中没有数据，请刷新页面后重新提交~");
			}
			List<StorageGoodsRecord> goodsRecordList = recordPair.getRight();
			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				num = goodsRecordList.size();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					sumMoney += goodsRecord.getNum() * goodsRecord.getPrice();
					sellerId = goodsRecord.getSellerId();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			buildJson(1, "统计结算池信息失败，错误信息：" + e.getMessage() + "~");

		}

		ApplyPayLog payLog = new ApplyPayLog();
		payLog.setApplyCode(applyPayCode);
		payLog.setStorageId(storageId);
		payLog.setSellerId(sellerId);
		payLog.setSumMoney(sumMoney);
		payLog.setRecordCount(num);
		payLog.setApplyUserId(user.user_id);
		payLog.setApplyUserName(user.user_name);
		payLog.setAccountMoney(DEFAULT_ACCOUNT_MONEY);
		payLog.setCreateTime(new Date());
		payLog.setStatus((byte) DEFAULT_APPLY_STATUS);

		try {
			storageGoodsRecordService.setRecordPayingStatus(user.user_id, applyPayCode, storageId);
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "更新入库记录信息失败~");
		}
		try {
			applyPayLogService.insert(payLog);
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "添加申请记录失败，请管理员整理入库记录更新记录，申请支付编码是:" + applyPayCode + "~");
		}
		return buildJson(0, "添加成功，结算单号是:" + applyPayCode);
	}

	@RequestMapping("stockrecord/modifyrecord")
	@ResponseBody
	public Object modifyRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) long recordId, @RequestParam(required = false) String price,
			@RequestParam(required = false) long sellerId, @RequestParam(required = false) long count,
			@RequestParam(required = false) String remark) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		StorageGoodsRecord goodsRecord = storageGoodsRecordService.queryById(recordId);
		if (goodsRecord == null) {
			return buildJson(1, "被修改的入库记录不存在~");
		}

		if (price != null && !price.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱格式错误~");
		}
		// RecordType recordType =
		// RecordTypeConstant.getRecordTypeById(goodsRecord.getRecordtype());
		RecordType recordType = recordTypeService.getById(goodsRecord.getRecordtype());
		StorageRecordModifylog modify = new StorageRecordModifylog();
		modify.setNum((int) count);
		modify.setOperator(user.getUser_name());
		modify.setRecordCode(goodsRecord.getRecordcode());
		modify.setRecordTypeId(goodsRecord.getRecordtype());
		modify.setStorageRecordId(recordId);
		modify.setSellerId(sellerId);
		modify.setPrice((int) MoneyUtils.yuan2Fen(Double.parseDouble(price)));

		try {
			storageRecordModifylogService.addStorageRecordModifylog(modify);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildJson(1, "添加修改记录失败~");
		}

		// 更新实际库存
		int modifyNum = (int) count - goodsRecord.getNum();
		if (modifyNum != 0) {
			try {
				StorageGoodsStock storageGoodsStock = storageGoodsStockService.getByGoodsId(
						goodsRecord.getGoodsId(), goodsRecord.getStorageId());
				long modifiedStock = storageGoodsStock.getStock() + modifyNum * recordType.getFlag();
				if (modifiedStock < 0) {
					return buildJson(1, "减库数大于库存数");
				}
				storageGoodsStockService.updateStock(storageGoodsStock.getStorage_goods_id(),
						modifiedStock);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return buildJson(1, "更新实际库存失败~");
			}
		}

		// 更新库存记录
		goodsRecord.setPrice(MoneyUtils.yuan2Fen(Double.parseDouble(price)));
		goodsRecord.setSellerId(sellerId);
		goodsRecord.setNum((int) count);
		if (remark.trim() != "") {
			goodsRecord.setModify_remark(remark);
		}
		try {
			storageGoodsRecordService.update(goodsRecord);
			return buildJson(0, "修改成功~");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildJson(1, "修改记录失败~");
		}
	}

	@RequestMapping("stockrecord/getStorageOption")
	@ResponseBody
	public Object getStorageOption(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) long storagetype, @RequestParam(required = false) long cityid) {
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
		if (storageList == null) {
			storageList = new ArrayList<Storage>();
		}
		return ImmutableMap.of("storageList", storageList);
	}

	@RequestMapping("stockrecord/getParamMap")
	@ResponseBody
	public Object getParamMap(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		// Map<Integer,RecordType> recordTypeMap =
		// RecordTypeConstant.getRecordTypeMap();
		Map<Integer, RecordType> recordTypeMap = new HashMap<Integer, RecordType>();
		List<RecordType> RecordTypeList = recordTypeService.getAll();
		Map<Long, Seller> sellerMap = SellerConstant.getSellerMap();

		if (!RecordTypeList.isEmpty()) {
			for (RecordType recordType : RecordTypeList) {
				recordTypeMap.put(recordType.getId(), recordType);
				// tempNameRecordTypeMap.put(recordType.getName(),
				// recordType);
			}
		}
		return ImmutableMap.of("recordTypeMap", recordTypeMap, "sellerMap", sellerMap);
	}

	@RequestMapping("stockrecord/exportrecord")
	@ResponseBody
	public void exprotGoodsRecordList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String searchstr,
			@RequestParam(required = false) Long sellerId, @RequestParam(required = false) Long paystatus,
			@RequestParam(required = false) String applypaycode,
			@RequestParam(required = false) Long storageid, @RequestParam(required = false) Long recordtype) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Long> goodsIds = new ArrayList<Long>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		if (startDate != null && startDate.trim().length() > 0) {
			try {
				beginTime = format.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// log.error("下载文件失败,格式化开始时间失败，【"+startDate+","+endDate+","+searchstr+","+sellerId+"】~",
				// e);
			}

		}

		if (endDate != null && endDate.trim().length() > 0) {
			try {
				endTime = format.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// log.error("下载文件失败,格式化结束时间失败，【"+startDate+","+endDate+","+searchstr+","+sellerId+"】~",
				// e);
			}

		}
		Map recordParamMap = new HashMap();
		recordParamMap.put("seller_id", sellerId);
		recordParamMap.put("storage_id", storageid);
		recordParamMap.put("record_type_id", recordtype);
		recordParamMap.put("searchStr", searchstr);
		recordParamMap.put("pay_status", paystatus);
		recordParamMap.put("applypaycode", applypaycode);
		recordParamMap.put("beginTime", beginTime);
		recordParamMap.put("endTime", endTime);

		Pair<Long, List<StorageGoodsRecord>> goodsRecordPair;
		try {
			goodsRecordPair = storageGoodsRecordService.getStorage(recordParamMap);
			long filteredTotal = goodsRecordPair.getLeft();
			List<StorageGoodsRecord> goodsRecordList = goodsRecordPair.getRight();
			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				Map<Long, Seller> sellerMap = SellerConstant.getSellerMap();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					for (Map.Entry<Long, Seller> entry : sellerMap.entrySet()) {
						if (goodsRecord.getSellerId() == entry.getKey().longValue()) {
							goodsRecord.setSellerName(entry.getValue().getSellerName());
						}
					}
					if (goodsRecord.getLock_user() != 0) {
						User lockuser = userService.getUserById((long) goodsRecord
								.getLock_user());
						goodsRecord.setLock_userName(lockuser.getUser_name());
					}
				}
			}

			// 号 商品名称 条码 供应商 数量 价格 入库类型 入库单号 入库时间 状态 结算单号 会计数量 会计单价
			// 支付金额 应付金额 操作

			StringBuffer contentBuffer = new StringBuffer(
					"商品名称\t条码\t供应商\t数量\t单价\t入库类型\t入库单号\t入库时间\t状态\t结算单号\t会计数量\t会计单价\t支付金额\t应付金额\t\n");
			int i = 0;
			DecimalFormat doubleformat = new DecimalFormat("#0.00");
			for (StorageGoodsRecord record : goodsRecordList) {
				i++;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = record.getRecord_time();
				String inStorageType = "未知";
				if (record.recordtype == 1) {
					inStorageType = "采购入库";
				} else if (record.recordtype == 2) {
					inStorageType = "回收入库";
				}
				String statusStr = "未知";
				if (record.getPay_status() == 0 && record.getLock_status() == 0
						&& record.getRecordtype() == 1) {
					statusStr = "未支付";
				}
				if (record.getPay_status() == 0 && record.getLock_status() == 0
						&& record.getRecordtype() == 2) {
					statusStr = "未支付";
				} else if (record.getPay_status() == 0 && record.getLock_status() == 1) {
					statusStr = "未支付(" + record.getLock_userName() + "已占用)";
				} else if (record.getPay_status() == 1) {
					statusStr = "待支付";
				} else if (record.getPay_status() == 2) {
					statusStr = "已支付";
				}
				String recordCode = record.getRecordcode() == null ? "" : record.getRecordcode();
				String applyPayCode = record.getApply_pay_code() == null ? "" : record
						.getApply_pay_code();
				contentBuffer.append(record.getWmsgoodsname() + "\t\"" + record.getWmsgoodsgbm()
						+ "\t\"\t" + record.getSellerName() + "\t\"" + record.getNum()
						+ "\t\"\t\"" + doubleformat.format((record.getPrice() / 100.0))
						+ "\t\"\t" + inStorageType + "\t\"" + recordCode + "\t\"\t"
						+ sdf.format(date) + "\t" + statusStr + "\t\"" + applyPayCode
						+ "\t\"\t\"" + record.getAccount_num() + "\t\"\t"
						+ doubleformat.format((record.getAccount_price() / 100.0)) + "\t\""
						+ doubleformat.format((record.getAccount_money() / 100.0)) + "\t\"\t\""
						+ doubleformat.format((record.getPrice() * record.getNum() / 100.0))
						+ "\t\"\t\n");
			}
			try {
				FileDownLoadUtil.downloadCSV(response, "商品入库记录.csv", contentBuffer.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
