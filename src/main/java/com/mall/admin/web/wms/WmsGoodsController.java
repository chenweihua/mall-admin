package com.mall.admin.web.wms;

import static com.google.common.base.Preconditions.checkArgument;
import static com.mall.admin.base._.isEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.base._;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.FileUtils;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

@Controller
@RequestMapping("/wms/goods")
public class WmsGoodsController extends BaseController {
	@Autowired
	private WmsGoodsService wmsGoodsService;

	@Autowired
	private StorageService storageService;
	@Autowired
	private CityService cityService;
	@Autowired
	private UserService userService;
	@Autowired
	private ZtreeUtil ztreeUtil;
	@Autowired
	private StorageGoodsStockService storageGoodsStockService;
	@Autowired
	private RecordTypeService recordTypeService;
	@Autowired
	private StorageGoodsRecordService storageGoodsRecordService;

	private static final Logger log = LogConstant.mallLog;

	@RequestMapping("/list")
	public Object wmsGoodsList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("wms/wmsgoodslist");
		return mav;
	}
	
	@RequestMapping("/third")
	public Object wmsGoodsList4Third(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> allStorages = user.getAllStorageList();
		List<Storage> storageList = storageService.getVMStorage();
		storageList.retainAll(allStorages);
		Storage defaultStorage = new Storage();
		//如果没有第三方商家，默认值设为-1
		defaultStorage.setStorageId(-1);
		if(storageList.size() > 0){
			defaultStorage = storageList.get(0);
		}
		return new ModelAndView("wms/wmsGoodsList4Third",_.asMap("storageList",storageList,"defaultStorage",defaultStorage));
	}

	@RequestMapping("/query")
	@ResponseBody
	public Object wmsGoodsListQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr,
			@RequestParam(value = "searchStr",required = false) String searchStr2,
			@RequestParam(defaultValue = "0", value = "storageId") Long storageId) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		if(storageId != 0){
			searchStr = searchStr2;
		}
		Map<String,Object> param = new HashMap<>();
		param.put("wms_goods_name_like", searchStr);
		param.put("wms_goods_gbm_like", searchStr);
		//当storageId为-2时，默认查询全部
		param.put("storageId", storageId);
		List<WmsGoods> wmsGoodsList = wmsGoodsService.getWmsGoods(param, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), wmsGoodsList, start));
	}
	

	@RequestMapping("/get")
	@ResponseBody
	public Object wmsGoodsListQuery(HttpServletRequest request, HttpServletResponse response) {

		String wms_goods_id_str = request.getParameter("wms_goods_id");
		if (Strings.isEmpty(wms_goods_id_str) || !wms_goods_id_str.matches("[0-9]{1,}")) {
			return buildJson(1, "商品id错误~");
		}
		long wms_goods_id = Long.parseLong(wms_goods_id_str);
		WmsGoods wmsGoods = wmsGoodsService.queryById(wms_goods_id);

		return buildJson(0, "查找成功~", wmsGoods);
	}

	/**
	 * 添加商品或编辑商品
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/addoredit")
	@ResponseBody
	public Object wmsGoodsAddOrEdit(HttpServletRequest request, HttpServletResponse response, WmsGoods wmsGoods) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		try {
			wmsGoods.operator_id = user.getUser_id();
			checkArgument(!Strings.isEmpty(wmsGoods.wms_goods_name.trim()), "商品名称为空~");
			checkArgument(!Strings.isEmpty(wmsGoods.wms_goods_gbm.trim()), "商品条码为空~");
			checkArgument(!Strings.isEmpty(wmsGoods.unit.trim()), "商品单位为空~");
			checkArgument(wmsGoods.wms_goods_gbm.trim().matches("^[0-9a-zA-Z]{1,20}$"), "商品条码格式错误~");
			wmsGoods.wms_goods_gbm = wmsGoods.wms_goods_gbm.trim();
			if (wmsGoods.wms_goods_id == -1) {
				// 添加商品
				if(wmsGoods.getStorageId() == 0){
					//小麦新增商品验证条码是否重复
					List<WmsGoods> wmsGoods_temp = wmsGoodsService.getByGbmAndStorageId(wmsGoods.wms_goods_gbm.trim(),Constants.XM_WMS_STORAGE_ID);
					checkArgument(wmsGoods_temp == null || wmsGoods_temp.size() == 0, "添加失败，商品条码已存在~");
					wmsGoodsService.insert(wmsGoods);
				}else{
					//格式化条码
					wmsGoods.setWms_goods_gbm(Constants.SF_WMS_PREFIX+wmsGoods.getWms_goods_gbm());
					long wmsGoodsId = wmsGoodsService.insert(wmsGoods);
					if(wmsGoodsId == -1){
						return buildJson(-1, "第三方商品添加失败！");
					}
					int ir = storageGoodsStockService.insert(wmsGoodsId,
							wmsGoods.getStorageId(), Storage.VM_STORAGE, user.user_id);
					if(ir < 1){
						return buildJson(-1, "第三方商品库存设置失败！");
					}
				}
				return buildJson(0, "商品添加成功~");
			} else {
				// 修改商品
				WmsGoods wmsGoods_temp = wmsGoodsService.queryById(wmsGoods.wms_goods_id);
				checkArgument(wmsGoods_temp != null, "修改失败，商品不存在~");
				if (!wmsGoods.wms_goods_gbm.equals(wmsGoods_temp.wms_goods_gbm)) {
					// 商品的条码被修改
					if(wmsGoods.getStorageId() == 0){
						List<WmsGoods> wmsGoods_temp_List = wmsGoodsService.getByGbmAndStorageId(wmsGoods.wms_goods_gbm.trim(),Constants.XM_WMS_STORAGE_ID);
						checkArgument(wmsGoods_temp_List == null || wmsGoods_temp_List.size() == 0, "修改失败，修改后的商品条码已存在~");
						wmsGoods_temp = wmsGoods_temp_List.get(0);
					}
				}
				wmsGoods.setWmsGoodsCode(wmsGoods_temp.getWmsGoodsCode());
				wmsGoodsService.update(wmsGoods);
				
				//弥补商品插入成功，库存插入失败的情况
				if(wmsGoods.getStorageId() != 0){
					storageGoodsStockService.insert(wmsGoods.getWms_goods_id(),
							wmsGoods.getStorageId(), Storage.VM_STORAGE, user.user_id);
				}
				return buildJson(0, "商品修改成功~");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("增加或编辑商品异常,[userId:{},wms_goods_id:{},gbm:{},name:{}]，error：{}", user.getUser_id(),
					wmsGoods.getWms_goods_id(), wmsGoods.getWms_goods_gbm(),
					wmsGoods.getWms_goods_name(), e);
			return buildJson(1, e.getMessage());
		}

	}

	@RequestMapping("/getregion")
	@ResponseBody
	public Object wmsGoodsGetRegion(HttpServletRequest request, HttpServletResponse response) {
		// 首先获得该商品所在仓的范围
		String wms_goods_id_str = request.getParameter("wms_goods_id");
		if (wms_goods_id_str == null || !wms_goods_id_str.matches("[0-9]{1,}")) {
			return buildJson(1, "待设置的商品id错误~");
		}
		long wms_goods_id = Long.parseLong(wms_goods_id_str);
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		ZtreeBean ztreeBean = ztreeUtil.getStorageZtree(user.allStorageList,false);
		if (wms_goods_id != 0) {
			wmsGoodsService.setZtreeBeanStatus(ztreeBean, wms_goods_id);
		}
		return buildJson(0, "商品范围查询成功~", ztreeBean);
	}

	/**
	 * 修改商品的售卖范围
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/setregion")
	@ResponseBody
	public Object wmsGoodsSetRegion(HttpServletRequest request, HttpServletResponse response) {

		String region = request.getParameter("region");
		String batchwms_goods_id_str = request.getParameter("wms_goods_id");
		String[] wmsGoodsIdsStr = batchwms_goods_id_str.split(",");

		List<ZtreeBean> bean = gson.fromJson(region, new TypeToken<List<ZtreeBean>>() {
		}.getType());

		if (bean == null || bean.size() == 0) {
			return buildJson(1, "获取范围信息为空~");
		}
		for (String wms_goods_id_str : wmsGoodsIdsStr) {
			if (wms_goods_id_str == null || !wms_goods_id_str.matches("[0-9]{1,}")) {
				continue;
			}

			long wms_goods_id = Long.parseLong(wms_goods_id_str);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			ZtreeBean ztreeBean = bean.get(0);
			wmsGoodsService.setWmsGoodsRegion(ztreeBean, wms_goods_id, user);
		}
		return buildJson(0, "商品修改成功~");

	}

	/**
	 * 批量获得商品可设置的范围，由于是批量设置，都不选中
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/batchgetregion")
	@ResponseBody
	public Object wmsGoodsBatchGetRegion(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		ZtreeBean ztreeBean = ztreeUtil.getStorageZtree(user.allStorageList,false);
		return buildJson(0, "商品范围查询成功~", ztreeBean);
	}

	/**
	 * 批量修改商品的售卖范围
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/batchsetregion")
	@ResponseBody
	public Object wmsGoodsBatchSetRegion(HttpServletRequest request, HttpServletResponse response) {
		String region = request.getParameter("region");
		String batchwms_goods_id_str = request.getParameter("batch_wms_goods_id");
		String[] wmsGoodsIdsStr = batchwms_goods_id_str.split(",");
		for (String wms_goods_id_str : wmsGoodsIdsStr) {
			if (wms_goods_id_str == null || !wms_goods_id_str.matches("[0-9]{1,}")) {
				return buildJson(1, "待设置的商品id错误~");
			}
			List<ZtreeBean> bean = gson.fromJson(region, new TypeToken<List<ZtreeBean>>() {
			}.getType());

			if (bean == null || bean.size() == 0) {
				return buildJson(1, "获取范围信息为空~");
			}
			long wms_goods_id = Long.parseLong(wms_goods_id_str);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			ZtreeBean ztreeBean = bean.get(0);
			wmsGoodsService.setWmsGoodsRegion(ztreeBean, wms_goods_id, user);
		}
		return buildJson(0, "商品修改成功~");
	}

	/**
	 * 商品入库
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/putinstorage")
	@ResponseBody
	public Object putInStorage(HttpServletRequest request, HttpServletResponse response, WmsGoods wmsGoods) {
		String priceStr = request.getParameter("price");
		String countStr = request.getParameter("count");
		int recordType = Integer.parseInt(request.getParameter("recordtype"));
		String recordCode = request.getParameter("recordcode");
		long storageId = Long.parseLong(request.getParameter("storageId"));
		Long sellerid = Long.parseLong(request.getParameter("sellerid"));
		if (priceStr == null) {
			return buildJson(1, "价钱为空！");
		}
		if (!priceStr.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱格式错误！");
		}
		if (isEmpty(countStr)) {
			return buildJson(1, "入库数量不能为空！");
		} else {
			if (!countStr.matches("[1-9][0-9]{0,}")) {
				return buildJson(1, "入库数量错误！");
			}
		}

		RecordType type = recordTypeService.getById(recordType);

		if (type == null) {
			return buildJson(1, "入库类型不正确");
		}
		if (type.name.equals("采购")) {
			if (isEmpty(recordCode)) {
				return buildJson(1, "采购入库必须输入单号。");
			}
		}

		/** 单号不作判断,调整类型不再只限采购和回收 */

		Long goodsId = Long.parseLong(request.getParameter("addid"));

		WmsGoods goods = wmsGoodsService.queryById(goodsId);
		if (goods == null) {
			return buildJson(1, "入库失败，待添加商品在仓库中不存在~");
		}

		// Double price = Double.parseDouble(priceStr);
		long price = MoneyUtils.yuan2Fen(Double.parseDouble(priceStr));
		int count = type.flag * Integer.parseInt(countStr);

		StorageGoodsStock storageGoods = new StorageGoodsStock();
		storageGoods.setWms_goods_id(goodsId);
		storageGoods.setStock(count);
		storageGoods.setStorage_Id(storageId);

		StorageGoodsRecord goodsRecord = new StorageGoodsRecord();
		goodsRecord.goodsId = goodsId;
		goodsRecord.storageId = storageId;
		goodsRecord.num = Integer.parseInt(countStr);// count;
		goodsRecord.sellerId = sellerid;
		goodsRecord.price = price;
		goodsRecord.recordtype = recordType;// 调整类型1、入库 2、回收 3、报残
		goodsRecord.recordcode = recordCode;
		storageGoodsRecordService.insert(goodsRecord);

		// Pair<Long, List<StorageGoods>> pair =
		// StorageGoods.getStorageGoodsBySearch(storageGoodsQuery, 0,
		// -1);
		StorageGoodsStock stockList = storageGoodsStockService.getByGoodsId(goodsId, storageId);
		if (stockList != null) {
			StorageGoodsStock pre = stockList; // 如果查找到入库记录已有该库存，则只做更新
			pre.stock = pre.stock + count;
			storageGoodsStockService.update(pre);
		} else {
			storageGoodsStockService.insert(storageGoods);
		}
		return buildJson(0, "入库成功！");

	}

	/**
	 * 导入文件
	 * 
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 */
	@RequestMapping("import")
	@ResponseBody
	public Object importFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile myfiles) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (user == null) {
			return buildJson(1, "用户未登录");
		}
		response.setContentType("text/html");
		String type = request.getParameter("type");
		String storageIdStr = request.getParameter("storageId");
		Long storageId = 0L;
		if(_.isNotEmpty(storageIdStr) && storageIdStr.matches("[0-9]{1,}")){
			storageId = _.toLong(storageIdStr);
		}
		String fileName = myfiles.getOriginalFilename();
		if (fileName == null) {
			return buildJson(1, "导入文件名为空~");
		}
		if (!fileName.endsWith("csv")) {
			return buildJson(1, "仅支持导入csv文件~");
		}
		try {
			List<String[]> data = FileUtils.readCsv(myfiles.getInputStream());
			if (data == null || data.size() == 0) {
				return buildJson(0, "导入失败，数据为空~");
			}
			if ("1".equals(type)) {
				// 入库
				// String message =
				// storageGoodsStockService.importGoods(data,
				// user.user_id, storageId);
				// if (!"0".equals(message)) {
				// return buildJson(1, message);
				// } else {
				// return buildJson(0, "批量入库成功~");
				// }
				return buildJson(1, "暂不支持~");
			} else if ("2".equals(type)) {
				// 导入商品
				String message = wmsGoodsService.importWmsGoodsList(data, user,storageId);
				if (!"0".equals(message)) {
					return buildJson(1, message);
				} else {
					return buildJson(0, "导入商品成功~");
				}
			} else {
				return buildJson(1, "导入文件类型错误~");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildJson(0, "导入文件异常，" + e.getMessage());
		}
	}

	@RequestMapping("modifyrecord")
	@ResponseBody
	public Object modifyRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) long recordId, @RequestParam(required = false) String price,
			@RequestParam(required = false) long sellerId, @RequestParam(required = false) long count,
			@RequestParam(required = false) String remark) {
		if (count < 0) {
			return buildJson(1, "入库数据不能小于0~");
		}
		if (remark == null || remark.trim().length() < 1) {
			return buildJson(1, "备注不能为空~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		StorageGoodsRecord goodsRecord = storageGoodsRecordService.queryById(recordId);
		if (goodsRecord == null) {
			return buildJson(1, "被修改的入库记录不存在~");
		}
		if (price != null && !price.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱格式错误~");
		}
		/*
		 * GoodsRecordModifyLog modify = new GoodsRecordModifyLog();
		 * modify.recordid=goodsRecord.id;
		 * modify.storageId=goodsRecord.storageId;
		 * modify.goodsId=goodsRecord.goodsId;
		 * modify.sellerId=goodsRecord.sellerId;
		 * modify.num=goodsRecord.num; modify.price=goodsRecord.price;
		 * modify.isDel=goodsRecord.isDel; modify.remark=remark; try {
		 * modify.insert(loginInfo.user.id); } catch (SQLException e) {
		 * // TODO Auto-generated catch block e.printStackTrace();
		 * log.error
		 * ("添加修改记录失败，【"+recordId+","+price+","+sellerId+","+count
		 * +","+remark+"】~", e); return buildJson(1, "添加修改记录失败~"); }
		 */

		RecordType type = null;
		type = recordTypeService.getById(goodsRecord.recordtype); // 获取调整类型
		// 更新实际库存
		int modifyNum = (int) count - goodsRecord.num;
		modifyNum *= type.flag;
		if (modifyNum != 0) {
			StorageGoodsStock storageGoods = storageGoodsStockService.getByGoodsId(goodsRecord.goodsId,
					goodsRecord.storageId);
			storageGoods.stock += modifyNum;
			storageGoodsStockService.update(storageGoods);
		}
		// 更新库存记录
		goodsRecord.price = MoneyUtils.yuan2Fen(Double.parseDouble(price));
		goodsRecord.sellerId = sellerId;
		goodsRecord.num = (int) count;
		storageGoodsRecordService.update(goodsRecord);
		return buildJson(0, "修改成功~");

	}

}
