package com.mall.admin.web.order;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.DeliverCompanyConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.order.ExpressCodeInfo;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.category.ThirdCategoryService;
import com.mall.admin.service.delivery.DeliverCompanyService;
import com.mall.admin.service.delivery.DeliveryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.service.order.SlaveOrderService;
import com.mall.admin.service.order.WithdrawReasonService;
import com.mall.admin.service.outside.PayService;
import com.mall.admin.service.outside.dto.PayItem;
import com.mall.admin.service.supplier.SupplierService;
import com.mall.admin.service.ump.uc.UcUserService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.FileUtils;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.category.ThirdCategory;
import com.mall.admin.vo.delivery.DeliverCompany;
import com.mall.admin.vo.delivery.Delivery;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.WithdrawReason;
import com.mall.admin.vo.order.pay.RefundInfoBean;
import com.mall.admin.vo.supplier.Suppiler;
import com.mall.admin.vo.ump.UcUser;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

@Controller
@RequestMapping("/thirdSeller/order")
public class ThirdSellerOrderManagerController extends BaseController {

	// 单次导出限制条数，多于该数则提醒，防止用户一次导出太多数据
	private static final int QUERY_MAX_NUM_PER_TIME = 10000;

	@Autowired
	private OrderService orderService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private WithdrawReasonService withdrawReasonService;

	@Autowired
	private DeliverCompanyService deliverCompanyService;
	@Autowired
	private UcUserService ucUserService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private SlaveOrderService slaveOrderService;
	@Autowired
	WmsGoodsService wmsGoodsService;
	@Autowired
	private PayService payService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ThirdCategoryService thirdCategoryService;

	@RequestMapping("/list")
	public Object toList(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		User loginInfo = (User) request.getAttribute("user");
		List<Storage> vmStorageList = loginInfo.getVmStorageList();

		List<DeliverCompany> deliverCompanyList = DeliverCompanyConstant.getDeliverCompanyList();
		return new ModelAndView("order/listThirdSellerOrder", _.asMap("have_nav", true, "status", 0, "payType",
				0, "storageList", vmStorageList, "deliverCompanyList", deliverCompanyList));
	}

	@RequestMapping("/ajaxListData")
	@ResponseBody
	public Object ajaxListData(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false, defaultValue = "-1") Integer status,
			@RequestParam(required = false) String orderCode,
			@RequestParam(required = false) String deliverSheetCode, // 物流单号
			@RequestParam(required = false, defaultValue = "-1") Long storageId, // 仓库ID
			@RequestParam(required = false, defaultValue = "") Integer onlinePayType, // 支付类型
													// 1微信支付
													// 2支付宝
			@RequestParam(required = false, defaultValue = "-1") Integer deliverStatus // 物流状态
													// 0待发货
													// 1已发货
	) throws SQLException, IOException, ParseException {

		User loginInfo = (User) request.getAttribute("user");
		// 查询当前登录用户有权限的第三方storage
		List<Storage> storageList = loginInfo.getVmStorageList();
		if (storageList == null || storageList.size() == 0) {
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
		Map<String, Object> params = new HashMap<>();
		List<Long> storageIdList = Lists.newArrayList();
		StringBuilder storageIdBuilder = new StringBuilder();
		for (Storage storage : storageList) {
			storageIdList.add(storage.getStorageId());
			storageIdBuilder.append(",").append(storage.getStorageId());
		}
		if (storageIdList == null || storageIdList.size() == 0
				|| (storageId != -1 && !storageIdList.contains(storageId))) {
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
		// 页面传递过来的为空，则查询当前登录用户有权限的所有第三方storage，否则查询页面传递过来的storageId
		if (storageId == -1 && storageIdList.size() > 0) {
			params.put("storageIdStr", storageIdBuilder.toString().substring(1));
			params.put("isOneStorage", 0);
		} else {
			params.put("storageIdStr", storageId);
			params.put("isOneStorage", 1);
		}
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		params.put("childOrderCode", orderCode);
		params.put("deliverSheetCode", deliverSheetCode);
		params.put("status", status);
		if (status == 0) {
			params.put("status", -1);
		}
		params.put("sendGoodsStatus", deliverStatus);
		params.put("startTime", startDate);
		params.put("endTime", endDate);
		params.put("onlinePayType", onlinePayType);

		List<ChildOrder> childOrderList = slaveOrderService.queryThirdChildOrderListByPage(paginationInfo,
				params);
		if (childOrderList == null) {
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
		long filterRecord = paginationInfo.getTotalRecord();
		
		return ImmutableMap.of("draw", draw, "recordsTotal", filterRecord, "recordsFiltered", filterRecord,
				"data", childOrderList,"orderTotalAmount", getOrderTotalAmount(params));
	}
	
	private double getOrderTotalAmount(Map<String, Object> params) {
		double orderTotalAmount = 0.00d;
		try {
			orderTotalAmount = slaveOrderService.queryThirdChildOrderTotalAmount(params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderTotalAmount;
	}
	
	@RequestMapping("/getChildOrderDetail")
	public Object getChildOrderDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int childOrderId) throws Exception {

		User loginInfo = (User) request.getAttribute("user");

		// 判断权限
		List<Storage> storages = loginInfo.getVmStorageList();
		ChildOrder childOrder = orderService.getChildOrderById(childOrderId);
		//物流名称
		DeliverCompany deliverCompany = DeliverCompanyConstant.getDeliverCompanyByCode(childOrder.getDeliverCompanyCode());
		childOrder.setDeliverCompanyName(deliverCompany == null ? "" : deliverCompany.getDeliverCompanyName());
		
		//母订单信息
		Order order = orderService.getOrderById(childOrder.getOrderId());
		boolean allow = false;
		if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(storages)) {
			for (Storage storage : storages) {
				if (childOrder.getStorageId().equals(storage.getStorageId())) {
					allow = true;
					break;
				}
			}
		}
		if (!allow) {
			return buildJson(1, "当前登录用户无权限查询订单" + childOrderId);
		}

		List<WithdrawReason> withdrawReasonFirstList = withdrawReasonService.getByLevel(1);
		List<WithdrawReason> withdrawReasonSecondList = new ArrayList<>();
		if (withdrawReasonFirstList != null && withdrawReasonFirstList.size() > 0) {
			withdrawReasonSecondList = withdrawReasonService.getByPid(withdrawReasonFirstList.get(0)
					.getWithdrawReasonId());
		}
		Suppiler suppiler = new Suppiler();
		UcUser ucUser = new UcUser();
		try {
			checkState(loginInfo != null, "请先登录");
			// 1、获取订单详情列表
			List<ChildOrderDetail> orderDetails = orderService
					.getChildOrderDetailListByChildOrderId(childOrderId);// .getOrderDetailList(orderId);
			if (!_.isEmpty(orderDetails)) {
				for (ChildOrderDetail detail : orderDetails) {
					List<Withdraw> withdraweds = orderService.getBeforeListByIdAndDate(detail
							.getChildOrderDetailId());
					int withdrawedNums = 0;
					if (!_.isEmpty(withdraweds)) {
						for (Withdraw w : withdraweds) {
							withdrawedNums += w.getSkuWithdrawNum();
						}
					}
					List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuList = wmsGoodsService
							.queryWmsGoods4BgSku(detail.getBgSkuId());
					detail.setSkuWithdrawedNum(withdrawedNums);
					detail.setWmsGoods4BgSkuList(wmsGoods4BgSkuList);
					
					ThirdCategory thirdCategory = thirdCategoryService.getCategoryBySkuId(detail.getBgSkuId());
					if(thirdCategory != null && thirdCategory.getLevel() == 1) {//获取一级类目
						detail.setThirdCategory(thirdCategory);
					}
					List<String> skuPropertyList = thirdCategoryService.getSkuPropertyBySkuIdAndBgGoodsId(detail.getBgSkuId());
					if(!CollectionUtils.isEmpty(skuPropertyList)) {
						detail.setSkuPropertySpecList(skuPropertyList);
					}
				}
			}
			//获取供应商信息
			suppiler = supplierService.getSupplierByStorageId(childOrder.getStorageId());
			//获取用户信息
			ucUser = getUcUserById(childOrder.getUserId());
			//获取快递公司信息
			List<DeliverCompany> deliverCompanyList = DeliverCompanyConstant.getDeliverCompanyList();
			
			// 2、获取订单提现信息
			List<Withdraw> withdraws = orderService.getWithdrawByOrderlId(childOrderId);
			List<RefundInfoBean> refundInfos = Lists.newArrayList();
			if (withdraws != null && withdraws.size() > 0) {
				// 3、获取用户退款信息
				refundInfos = RefundInfoBean.getRefundInfo((long) childOrderId);
			} 
			return new ModelAndView("order/thirdSellerOrderDetail",_.asMap("reasonFirst", withdrawReasonFirstList, "reasonSecond",
					withdrawReasonSecondList, "orderDetails", orderDetails, "withdraws",
					withdraws, "refundinfos", refundInfos, "childOrder", childOrder, "suppiler", suppiler, "order", order, "ucUser", ucUser,
					"deliverCompanyList",deliverCompanyList));

		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error(_.f("获取订单详情失败orderId:%s", childOrderId), e);
			return new ModelAndView("order/thirdSellerOrderDetail",_.asMap("reasonFirst", withdrawReasonFirstList, "reasonSecond",
					withdrawReasonSecondList, "orderDetails", new ArrayList<ChildOrderDetail>(),
					"withdraws", new ArrayList<Withdraw>(), "refundinfos",
					new ArrayList<RefundInfoBean>(), "childOrder", childOrder, "suppiler", suppiler, "order", order, "ucUser", ucUser));
		}
	}
	
	private UcUser getUcUserById(long userId) {
		UcUser ucUser = null;
		try {
			ucUser = ucUserService.getUcUserByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ucUser;
	}

	@RequestMapping("/ajaxOrderDetail")
	@ResponseBody
	public Object orderDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam int orderId) {
		User loginInfo = (User) request.getAttribute("user");

		// 判断权限
		List<Storage> storages = loginInfo.getVmStorageList();
		ChildOrder childOrder = orderService.getChildOrderById(orderId);
		boolean allow = false;
		if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(storages)) {
			for (Storage storage : storages) {
				if (childOrder.getStorageId().equals(storage.getStorageId())) {
					allow = true;
					break;
				}
			}
		}
		if (!allow) {
			return buildJson(1, "当前登录用户无权限查询订单" + orderId);
		}

		List<WithdrawReason> withdrawReasonFirstList = withdrawReasonService.getByLevel(1);
		List<WithdrawReason> withdrawReasonSecondList = new ArrayList<>();
		if (withdrawReasonFirstList != null && withdrawReasonFirstList.size() > 0) {
			withdrawReasonSecondList = withdrawReasonService.getByPid(withdrawReasonFirstList.get(0)
					.getWithdrawReasonId());
		}
		try {
			checkState(loginInfo != null, "请先登录");
			// 1、获取订单详情列表
			List<ChildOrderDetail> orderDetails = orderService
					.getChildOrderDetailListByChildOrderId(orderId);// .getOrderDetailList(orderId);
			if (!_.isEmpty(orderDetails)) {
				for (ChildOrderDetail detail : orderDetails) {
					List<Withdraw> withdraweds = orderService.getBeforeListByIdAndDate(detail
							.getChildOrderDetailId());
					int withdrawedNums = 0;
					if (!_.isEmpty(withdraweds)) {
						for (Withdraw w : withdraweds) {
							withdrawedNums += w.getSkuWithdrawNum();
						}
					}
					List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuList = wmsGoodsService
							.queryWmsGoods4BgSku(detail.getBgSkuId());
					detail.setSkuWithdrawedNum(withdrawedNums);
					detail.setWmsGoods4BgSkuList(wmsGoods4BgSkuList);
				}
			}

			// 2、获取订单提现信息
			List<Withdraw> withdraws = orderService.getWithdrawByOrderlId(orderId);
			if (withdraws != null && withdraws.size() > 0) {
				// 3、获取用户退款信息
				List<RefundInfoBean> refundInfos = RefundInfoBean.getRefundInfo((long) orderId);
				return _.asMap("reasonFirst", withdrawReasonFirstList, "reasonSecond",
						withdrawReasonSecondList, "orderDetails", orderDetails, "withdraws",
						withdraws, "refundinfos", refundInfos);
			} else {
				return _.asMap("reasonFirst", withdrawReasonFirstList, "reasonSecond",
						withdrawReasonSecondList, "orderDetails", orderDetails, "withdraws",
						withdraws, "refundinfos", new ArrayList<RefundInfoBean>());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error(_.f("获取订单详情失败orderId:%s", orderId), e);
			return _.asMap("reasonFirst", withdrawReasonFirstList, "reasonSecond",
					withdrawReasonSecondList, "orderDetails", new ArrayList<ChildOrderDetail>(),
					"withdraws", new ArrayList<Withdraw>(), "refundinfos",
					new ArrayList<RefundInfoBean>());
		}

	}

	@RequestMapping("/withdraw")
	@ResponseBody
	public Object withdraw(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {

		User loginInfo = (User) request.getAttribute("user");

		List<Pair<Long, Integer>> detailList = new ArrayList<>();
		String reason = "";
		String data = request.getParameter("data");
		try {
			checkState(loginInfo != null, "请先登录");
			JsonNode jsonNode = JsonUtil.parse(data);
			reason = jsonNode.findValue("reason").textValue();
			Iterator<JsonNode> iterator = jsonNode.findValue("detailList").elements();
			while (iterator.hasNext()) {
				JsonNode elem = iterator.next();
				long detailId = elem.findValue("orderDetailId").asLong();
				int withdrawNum = elem.findValue("withdrawNum").asInt();
				detailList.add(Pair.of(detailId, withdrawNum));
			}
			// 判断权限
			List<Storage> storages = loginInfo.getVmStorageList();
			if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(storages)) {
				for (Pair<Long, Integer> pair : detailList) {
					boolean allow = false;
					Long detailId = pair.getLeft();
					ChildOrderDetail childOrderDetail = orderService
							.getChildOrderDetailById(detailId);
					ChildOrder childOrder = orderService.getChildOrderById(childOrderDetail
							.getChildOrderId());
					for (Storage storage : storages) {
						if (childOrder.getStorageId().equals(storage.getStorageId())) {
							allow = true;
							break;
						}
					}
					if (!allow) {
						return buildJson(1, "当前登录用户无权限查询订单详单" + detailId);
					}
				}
			} else {
				return buildJson(1, "当前登录用户无权限查看任何订单");
			}

			Pair<Integer, String> result = orderService.withdraw(detailList, reason,
					loginInfo.getUser_id(), loginInfo.getUser_name());
			if (result.getLeft() == 0) {
				return _.asMap("status", "success");
			} else {
				LogConstant.mallLog.error(_.f("提现失败,userId:%s,data[%s],reason:%s,msg:%s",
						loginInfo.getUser_id(), data, reason, result.getRight()));
				return _.asMap("status", "error", "msg", result.getRight());
			}
		} catch (Exception e) {
			e.printStackTrace();
			long userId = -1;
			if (loginInfo != null) {
				userId = loginInfo.getUser_id();
			}
			LogConstant.mallLog.error(_.f("提现失败,userId:%s,data[%s],reason:%s", userId, data, reason), e);
			return _.asMap("status", "error");
		}
	}

	private static String filter(String raw) {
		String filted = raw.replaceAll("\\s*", "");
		return filted;
	}

	//退款订单导出
	@RequestMapping("/export")
	@ResponseBody
	public Object export(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String strStartDate,
			@RequestParam(required = false) String strEndDate) throws SQLException, IOException,
			ParseException {
		User loginInfo = (User) request.getAttribute("user");
		// 判断权限
		List<Storage> storages = loginInfo.getVmStorageList();
		if (CollectionUtils.isEmpty(storages)) {
			return buildJson(1, "您无权限查看任何订单");
		}
		List<Long> storageIds = Lists.newArrayList();
		for (Storage storage : storages) {
			storageIds.add(storage.getStorageId());
		}
		Date startDate = _.toDate(strStartDate);
		Date endDate = _.toDate(strEndDate);
		List<Withdraw> withdraws = orderService.getWithdrawList(startDate, endDate, storageIds);
		if ((startDate == null && endDate == null)
				|| (startDate != null && endDate != null && startDate.after(endDate))) {
			startDate = new DateTime().millisOfDay().setCopy(0).toDate();
			endDate = new DateTime().plusDays(1).millisOfDay().setCopy(0).toDate();
		}
		String filename = "withdraw_" + _.formatDate(startDate, "yyyy-MM-dd") + "_"
				+ _.formatDate(endDate, "yyyy-MM-dd") + ".csv";
		response.setContentType("text/csv;charset=GB2312");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		// Write the header line
		OutputStream out = response.getOutputStream();
		String header = "订单日期,退款日期,订单号,母订单,子订单,用户名,商品名称,单价(元),数量,退款原因,应退金额(元),订单总额(元),首减金额(元),满减金额(元),优惠券(元),在线支付总额(元),在线支付类型,学校,联系电话,支付ID,商户退款流水号,支付交易号\n";
		out.write(header.getBytes(Charset.forName("GB2312")));
		// Write the content
		int i = 1;
		for (Withdraw withdraw : withdraws) {
			String onlinePayType = "未知";
			if (withdraw.getOnlinepaytype() == 1) {
				onlinePayType = "微信";
			} else if (withdraw.getOnlinepaytype() == 2) {
				onlinePayType = "支付宝";
			}
			ChildOrder childOrder = orderService.getChildOrderById(withdraw.getOrderId());
			Order order = orderService.getOrderById(childOrder.getOrderId());
			String line = _.formatDate(withdraw.getOrderCreateTime(), "yyyyMMdd") + ","
					+ _.formatDate(withdraw.getCreatetime(), "yyyyMMdd") + ","
					+ childOrder.getOrderId() + "," + childOrder.getOrderCode() + ","
					+ childOrder.getChildOrderCode() + "," + filter(withdraw.getReceivername())
					+ "," + filter(withdraw.getSkuName()) + ","
					+ withdraw.getSkuUnitPrice() / 100.0 + "," + withdraw.getSkuWithdrawNum() + ","
					+ filter(withdraw.getReason()) + ","
					+ withdraw.getSkuUnitPrice() * withdraw.getSkuWithdrawNum() / 100.0 + ","
					+ withdraw.getOrdertotalpay() / 100.0 + "," + withdraw.getFirstsub() / 100.0
					+ "," + withdraw.getFullsub() / 100.0 + "," + withdraw.getCoupon() / 100.0
					+ "," + withdraw.getOnlinepay() / 100.0 + "," + onlinePayType + ","
					+ withdraw.getCollegename() + "," + withdraw.getReceiverphone() + "\t,"
					+ withdraw.getTransactionId() + "," + generateCode(i) + "\t," + getPayId(order)
					+ "\t\n";
			i++;
			// 退款流水号ID，四位
			if (i > 9999) {
				i = 1;
			}
			out.write(line.getBytes(Charset.forName("GB2312")));
		}
		out.flush();

		return null;
	}

	/**
	 * 根据查询条件导出文件 单次导出数量限制1w条
	 */
	@RequestMapping("/exportThirdSellerOrders")
	@ResponseBody
	public Object exportThirdSellerOrders(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String phone,
			@RequestParam(required = false, defaultValue = "-1") Integer status,
			@RequestParam(required = false) String orderCode,
			@RequestParam(required = false) String deliverSheetCode, // 物流单号
			@RequestParam(required = false, defaultValue = "-1") Long storageId, // 仓库ID
			@RequestParam(required = false, defaultValue = "") Integer onlinePayType, // 支付类型 1微信支付,2支付宝
			@RequestParam(required = false, defaultValue = "-1") Integer deliverStatus // 物流状态
															// 0待发货
															// 1已发货
	) throws SQLException, IOException, ParseException {
		User loginInfo = (User) request.getAttribute("user");
		// 查询当前登录用户有权限的第三方storage
		List<Storage> storageList = loginInfo.getVmStorageList();
		if (storageList == null || storageList.size() == 0) {
			return buildJson(1, "您无权限查看第三方卖家订单！");
		}
		Map<String, Object> params = new HashMap<>();
		List<Long> storageIdList = Lists.newArrayList();
		for (Storage storage : storageList) {
			storageIdList.add(storage.getStorageId());
		}
		if (storageIdList == null || storageIdList.size() == 0
				|| (storageId != -1 && !storageIdList.contains(storageId))) {
			return buildJson(1, "您无权限查看第三方卖家订单！");
		}
		// 页面传递过来的为空，则查询当前登录用户有权限的所有第三方storage，否则查询页面传递过来的storageId
		if (storageId == -1 && storageIdList.size() > 0) {
			params.put("storageIdList", storageIdList);
		} else {
			storageIdList = Lists.newArrayList();
			storageIdList.add(storageId);
			params.put("storageIdList", storageIdList);
		}
		params.put("childOrderCode", orderCode);
		params.put("deliverSheetCode", deliverSheetCode);
		params.put("status", status);
		if (status == 0) {
			params.put("status", -1);
		}
		params.put("sendGoodsStatus", deliverStatus);
		params.put("receiverPhone", phone);
		params.put("startTime", startDate);
		params.put("endTime", endDate);
		params.put("onlinePayType", onlinePayType);
		try {
			List<ChildOrder> childOrderList = slaveOrderService.queryThirdChildOrderList(params);
			// 加入限制
			if (childOrderList.size() > QUERY_MAX_NUM_PER_TIME) {
				return buildJson(1, "您单次导出的数据量过大，请查询输入详细的查询条件，缩减单次导出的数据量！");
			}
			String filename = "exportThirdSellerOrders.csv";
			response.setContentType("text/csv;charset=GB2312");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			// Write the header line
			OutputStream out = response.getOutputStream();
			String header = "订单号,	SKU名称,SKU条码,数量,物流商名称,物流单号,收货人姓名,联系方式,收货地址,当前状态,发货状态,生成时间,有无退款\n";
			out.write(header.getBytes(Charset.forName("GB2312")));
			Map<Byte, String> statusMap = new HashMap<Byte, String>();
			statusMap.put(_.toByte(1), "未支付");
			statusMap.put(_.toByte(2), "订单无效");
			statusMap.put(_.toByte(3), "订单超时");
			statusMap.put(_.toByte(4), "支付中");
			statusMap.put(_.toByte(5), "支付成功");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取bgSkuID和wmsgoods的对应关系
			Map<Long, List<WmsGoods4BgSkuInfo>> wmsGoods4BgSkuListMap = wmsGoodsService.getWmsGoods4BgSkuMap();
			for (ChildOrder order : childOrderList) {
				List<ChildOrderDetail> childOrderDetailList = slaveOrderService
						.getChildOrderDetialByChildOrderId(order.getChildOrderId());
				StringBuffer lineBuffer = new StringBuffer();
				if (childOrderDetailList == null || childOrderDetailList.size() == 0) {
					continue;
				}
				for (ChildOrderDetail childOrderDetail : childOrderDetailList) {
					List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuList = wmsGoods4BgSkuListMap.get(childOrderDetail.getBgSkuId());
					if(wmsGoods4BgSkuList == null || wmsGoods4BgSkuList.size() == 0){
						continue;
					}
					for(WmsGoods4BgSkuInfo wmsGoods : wmsGoods4BgSkuList){
						lineBuffer.append(order.getChildOrderCode())
						.append(",")
						.append(wmsGoods.getWmsGoodsName())
						.append(",")
						.append(wmsGoods.getWmsGoodsGbm())
						.append(",")
						.append(childOrderDetail.getSkuNum()*wmsGoods.getSkuNum())
						.append(",")
						.append(order.getDeliverCompanyName())
						.append(",")
						.append(order.getDeliverSheetCode())
						.append(",")
						.append(order.getReceiverName())
						.append(",")
						.append(order.getReceiverPhone())
						.append("\t,")
						.append(order.getDeliveryAddress())
						.append(",")
						//.append(String.format("%.2f", order.getTotalPay() / 100.0d))
						//.append(",")
						.append(statusMap.get(order.getStatus()))
						.append(",")
						.append(StringUtils.isEmpty(order.getDeliverSheetCode()) ? "待发货"
								: "已发货").append(",")
						.append(format.format(order.getCreateTime())).append("\t,")
						.append(order.getIsWithdraw() == 1 ? "有退款" : "无退款")
						.append("\n");
					}
				}
				out.write(lineBuffer.toString().getBytes(Charset.forName("GB2312")));
			}
			out.flush();
		} catch (Exception e) {
			LogConstant.mallLog.error("导出第三方订单时发生异常", e);
		}

		/*
		 * try {
		 * 
		 * Date startDated = _.toDate(startDate); Date endDated =
		 * _.toDate(endDate);
		 * 
		 * checkState(loginInfo != null);
		 * 
		 * //查询当前登录用户有权限的第三方storage List<Storage> storages =
		 * loginInfo.getVmStorageList(); List<Long> allowStorageIds =
		 * Lists.newArrayList(); if(storages != null) { for(Storage
		 * storage : storages) {
		 * allowStorageIds.add(storage.getStorageId()); } }
		 * //当前登录用户无权限查看任何第三方storage
		 * if(CollectionUtils.isEmpty(allowStorageIds)) { return
		 * buildJson(1,"您无权限查看第三方卖家订单！"); }
		 * 
		 * List<Long> storageIds = Lists.newArrayList();
		 * //页面传递过来的为空，则查询当前登录用户有权限的所有第三方storage，否则查询页面传递过来的storageId
		 * if(StringUtils.isEmpty(storageId)) {
		 * storageIds.addAll(allowStorageIds); } else {
		 * storageIds.add(Long.parseLong(storageId)); }
		 * 
		 * int statusInt = _.toInt(status, 0);
		 * 
		 * LogConstant.mallLog.debug("status=" + status + " statusInt="
		 * + statusInt); Pair<Integer, List<ChildOrder>> datas =
		 * orderService.getThirdSellerChildOrderList( phone, statusInt,
		 * DeliveryConstant.THIRD_SELLER, startDated, endDated,
		 * orderCode, deliverSheetCode,
		 * storageIds,deliverStatus,deliveryStatus, 0,
		 * QUERY_MAX_NUM_PER_TIME);
		 * 
		 * Integer queryNum = datas.getLeft(); if(queryNum != null &&
		 * queryNum > QUERY_MAX_NUM_PER_TIME) { return buildJson(1,
		 * "您单次导出的数据量过大，请查询输入详细的查询条件，缩减单次导出的数据量！"); }
		 * 
		 * for(ChildOrder order : datas.getRight()) { DeliverCompany
		 * deliverCompany =
		 * DeliverCompanyConstant.getDeliverCompanyByCode
		 * (order.getDeliverCompanyCode());
		 * order.setDeliverCompanyName(deliverCompany == null ? "" :
		 * deliverCompany.getDeliverCompanyName()); }
		 * 
		 * for (ChildOrder order : datas.getRight()) { List<Withdraw>
		 * withdraws =
		 * orderService.getWithdrawByOrderlId(order.getChildOrderId());
		 * if (withdraws == null || withdraws.size() == 0) {
		 * order.setIsWithdraw(0); } else { order.setIsWithdraw(1); } }
		 * 
		 * String filename = "exportThirdSellerOrders.csv";
		 * response.setContentType("text/csv;charset=GB2312");
		 * response.setHeader("Content-Disposition",
		 * "attachment; filename=" + filename);
		 * 
		 * // Write the header line OutputStream out =
		 * response.getOutputStream(); String header =
		 * "订单号,	母订单号,物流商名称,物流单号,	收货人姓名,联系方式,收货地址,	" +
		 * "订单金额【子】,在线支付金额【总】,促销免减金额【总】,首单免减金额【总】,优惠金额【总】,支付方式,当前状态," +
		 * "发货状态,	生成时间,有无退款\n";
		 * out.write(header.getBytes(Charset.forName("GB2312")));
		 * 
		 * Map<Byte,String> payTypeMap = new HashMap<Byte,String>();
		 * payTypeMap.put(_.toByte(1), "微信支付");
		 * payTypeMap.put(_.toByte(2), "支付宝");
		 * 
		 * 
		 * 
		 * Map<Byte,String> statusMap = new HashMap<Byte,String>();
		 * statusMap.put(_.toByte(1), "未支付"); statusMap.put(_.toByte(2),
		 * "订单无效"); statusMap.put(_.toByte(3), "订单超时");
		 * statusMap.put(_.toByte(4), "支付中"); statusMap.put(_.toByte(5),
		 * "支付成功");
		 * 
		 * SimpleDateFormat format = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for (ChildOrder
		 * order : datas.getRight()) {
		 * 
		 * StringBuffer lineBuffer = new StringBuffer();
		 * lineBuffer.append(order.getChildOrderCode()).append(",")
		 * .append(order.getOrderCode()).append(",")
		 * .append(order.getDeliverCompanyName()).append(",")
		 * .append(order.getDeliverSheetCode()).append(",")
		 * .append(order.getReceiverName()).append(",")
		 * .append(order.getReceiverPhone()).append(",")
		 * .append(order.getDeliveryAddress()).append(",")
		 * .append(String.format("%.2f",order.getTotalPay() /
		 * 100.0d)).append(",")
		 * .append(String.format("%.2f",order.getOnlinePay() /
		 * 100.0d)).append(",")
		 * .append(String.format("%.2f",order.getFullSub() /
		 * 100.0d)).append(",")
		 * .append(String.format("%.2f",order.getFirstSub() /
		 * 100.0d)).append(",")
		 * .append(String.format("%.2f",order.getCouponPay() /
		 * 100.0d)).append(",")
		 * .append(payTypeMap.get(order.getOnlinePayType())).append(",")
		 * .append(statusMap.get(order.getStatus())).append(",")
		 * .append(StringUtils.isEmpty(order.getDeliverSheetCode()) ?
		 * "待发货" : "已发货").append(",")
		 * .append(format.format(order.getCreateTime())).append(",")
		 * .append(order.getIsWithdraw() == 1 ? "有退款" :
		 * "无退款").append("\n");
		 * out.write(lineBuffer.toString().getBytes(
		 * Charset.forName("GB2312")));
		 * 
		 * }
		 * 
		 * out.flush(); } catch(Exception ex) {
		 * LogConstant.mallLog.error("导出第三方订单时发生异常",ex); }
		 */

		return null;
	}

	/**
	 * 为p2pAccess提供的订单查询，在门店入库时调用
	 *
	 * @param request
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping("/getByCollegeIdAndExpressCode")
	@ResponseBody
	public Object getByExpressCode(HttpServletRequest request) throws SQLException, IOException {
		long start = System.currentTimeMillis();
		String data = getDataFromRequest(request);

		if (data == null || data.trim().length() == 0) {
			return buildErrJson("订单查询失败");
		}
		JsonNode jsonNode = JsonUtil.parse(data);
		long collegeId = 0;
		String expressCode = null;
		try {
			collegeId = jsonNode.get("collegeId") == null ? 0 : jsonNode.get("collegeId").asLong();
			expressCode = jsonNode.get("expressCode").asText();
			ChildOrder order = orderService.getChildOrderByCode(expressCode);
			if (order == null) {
				LogConstant.mallLog.warn(
						"p2pAccess查询订单，已打包的订单（fdcStatus=3）未查到，collegeId={}, expressCode={}",
						collegeId, expressCode);
			} else {
				ExpressCodeInfo info = new ExpressCodeInfo();
				info.setReceiverName(order.getReceiverName());
				info.setReceiverPhone(order.getReceiverPhone());
				return buildSuccJson(_.asMap("order", info));
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error(
					_.f("p2pAccess查询订单异常，collegeId=%d, expressCode=%s", collegeId, expressCode), e);
		}
		return buildErrJson("订单查询失败");
	}

	@ResponseBody
	@RequestMapping("/getWithdrawReasonByPid")
	public Object getWithdrawReasonByPid(HttpServletRequest request, @RequestParam(defaultValue = "-1") Long pid)
			throws SQLException, IOException {
		List<WithdrawReason> withdrawReasonSecondList = withdrawReasonService.getByPid(pid);
		return buildSuccJson(withdrawReasonSecondList);
	}

	@RequestMapping("/manageDelivery")
	@ResponseBody
	public Object manageDelivery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String childOrderCode, @RequestParam String deliverCompanyCode,
			@RequestParam String deliverSheetCode) {

		checkArgument(StringUtils.isNotEmpty(childOrderCode), "childOrderCode不能为空");
		checkArgument(StringUtils.isNotEmpty(deliverCompanyCode), "deliverCompanyCode不能为空");
		checkArgument(StringUtils.isNotEmpty(deliverSheetCode), "deliverSheetCode不能为空");

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (user == null) {
			return buildJson(1, "用户未登录");
		}
		List<Storage> storages = user.getVmStorageList();

		boolean allow = false;
		ChildOrder childOrder = orderService.getChildOrderByCode(childOrderCode);
		Long storageId = childOrder.getStorageId();
		if (storageId != null && storages != null) {
			for (Storage storage : storages) {
				if (storageId.equals(storage.getStorageId())) {
					allow = true;
					break;
				}
			}
		}

		if (!allow) {
			return buildJson(1, "当前用户无权限管理订单" + childOrderCode + "的配送信息");
		}

		boolean success = orderService.updateDeliveryInfo(childOrderCode, deliverCompanyCode, deliverSheetCode);

		if (success) {
			return buildJson(0, "更新成功");
		} else {
			return buildJson(0, "更新失败");
		}

	}

	@RequestMapping("/ignoreDeliveryStatus")
	@ResponseBody
	public Object ignoreDeliveryStatus(HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (user == null) {
			return buildJson(1, "用户未登录");
		}
		List<Storage> storages = user.getVmStorageList();

		String childOrderCode = request.getParameter("childOrderCode");
		childOrderCode = StringUtils.trim(childOrderCode);
		if (StringUtils.isEmpty(childOrderCode)) {
			return buildJson(1, "参数缺少！");
		}

		boolean allow = false;
		ChildOrder childOrder = orderService.getChildOrderByCode(childOrderCode);
		Long storageId = childOrder.getStorageId();
		if (storageId != null && storages != null) {
			for (Storage storage : storages) {
				if (storageId.equals(storage.getStorageId())) {
					allow = true;
					break;
				}
			}
		}

		if (!allow) {
			return buildJson(1, "当前用户无权限管理订单" + childOrderCode + "的配送信息");
		}

		boolean success = orderService.ignoreDeliveryStatus(childOrderCode, childOrder.getUserId());

		if (success) {
			return buildJson(0, "更新成功");
		} else {
			return buildJson(0, "更新失败");
		}

	}

	@RequestMapping(value = "/queryDelivery", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object queryDelivery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String deliverCompanyCode, @RequestParam String deliverSheetCode) {

		checkArgument(StringUtils.isNotEmpty(deliverCompanyCode), "deliverCompanyCode不能为空");
		checkArgument(StringUtils.isNotEmpty(deliverSheetCode), "deliverSheetCode不能为空");

		// List<DeliverDetailInfo> deliverDetailInfo =
		// deliverInfoService.queryDeliverInfo(deliverCompanyCode,
		// deliverSheetCode);
		Delivery delivery = deliveryService.getDeliveryById(deliverCompanyCode, deliverSheetCode);
		if (delivery == null || delivery.getDeliveryInfo() == null) {
			return buildJson(-1, "暂时没有该物流单号信息！");
		}
		if (delivery == null) {
			return "";
		}

		return delivery.getDeliveryInfo();
	}

	public static void main(String[] args) {
		System.out.println(123456829 % 1024);
	}

	/**
	 * 批量导入物流信息 CSV文件，第一列为子订单号,第二列为物流商名称，第三列为物流单号
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Object importFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile myfiles) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (user == null) {
			return buildJson(1, "用户未登录");
		}
		// response.setContentType("application/json");
		response.setContentType("text/html");
		String type = request.getParameter("type");
		String fileName = myfiles.getOriginalFilename();
		if (fileName == null) {
			return buildJson(1, "导入文件名为空~");
		}
		if (!fileName.endsWith("csv")) {
			return buildJson(1, "仅支持导入csv文件~");
		}
		try {
			// String[][] data =
			// ExcelImportUtil.readExcel(myfiles.getInputStream());
			List<String[]> data = FileUtils.readCsv(myfiles.getInputStream());
			if (data == null || data.size() == 0) {
				return buildJson(1, "导入失败，数据为空~");
			}

			List<Storage> storages = user.getVmStorageList();
			List<Long> storageIds = Lists.newArrayList();
			if (storages != null) {
				for (Storage storage : storages) {
					storageIds.add(storage.getStorageId());
				}
			}
			if (CollectionUtils.isEmpty(storages)) {
				return buildJson(1, "该用户无权限导入任何物流信息！");
			}

			// 导入物流信息
			String message = orderService.importDeliveryInfo(data, storageIds);
			return buildJson(0, message);

		} catch (Exception e) {
			LogConstant.mallLog.error("批量导入物流信息异常", e);
			return buildJson(1, "批量导入物流信息异常，" + e.getMessage());
		}
	}

	private String generateCode(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = sdf.format(new Date()) + String.format("%04d", i);
		return result;
	}

	private String getPayId(Order order) {
		Map<Long, PayItem> params = new HashMap<Long, PayItem>();
		int type = order.getDevice() == 1 ? PayItem.TYPE_H5 : PayItem.TYPE_APP;
		int appType = order.getOnlinePayType() == 1 ? PayItem.APP_TYPE_WEIXIN : PayItem.APP_TYPE_BAO;
		params.put(order.getOrderId(), new PayItem(order.getOrderId(), type, appType, null, -1L));
		Map<Long, PayItem> ret = payService.getPayId(params);
		if (ret == null) {
			return "";
		}
		return ret.get(order.getOrderId()).getPayId();
	}
}
