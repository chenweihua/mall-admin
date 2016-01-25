package com.mall.admin.web.order;

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

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.order.ExpressCodeInfo;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.service.order.WithdrawReasonService;
import com.mall.admin.service.outside.PayService;
import com.mall.admin.service.outside.dto.PayItem;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.WithdrawReason;
import com.mall.admin.vo.order.pay.RefundInfoBean;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

@Controller
@RequestMapping("/order")
public class OrderManagerController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private WithdrawReasonService withdrawReasonService;
	@Autowired
	WmsGoodsService wmsGoodsService;
	@Autowired
	private PayService payService;

	@RequestMapping("/list")
	public Object toList(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		return new ModelAndView("order/listOrder", _.asMap("have_nav", true, "status", 0, "payType", 0));
	}

	@RequestMapping("/ajaxListData")
	@ResponseBody
	public Object ajaxListData(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String orderType,
			@RequestParam(required = false) String orderCode,
			@RequestParam(required = false) String porderCode) throws SQLException, IOException,
			ParseException {

		User loginInfo = (User) request.getAttribute("user");
		try {

			Date startDated = _.toDate(startDate);// DateTime.parse(startDate,
								// DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
			Date endDated = _.toDate(endDate);// DateTime.parse(endDate,
								// DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

			checkState(loginInfo != null);

			int statusInt = _.toInt(status, 0);
			int orderTypeInt = _.toInt(orderType);

			LogConstant.mallLog.debug("status=" + status + " statusInt=" + statusInt);
			LogConstant.mallLog.debug("payType=" + orderType + " payTypeInt=" + orderTypeInt);
			long total = 0;
			Pair<Integer, List<ChildOrder>> datas = orderService.getChildOrderList(loginInfo, phone,
					statusInt, orderTypeInt, startDated, endDated, orderCode, porderCode, null,
					null, null, start, numPerPage);

			for (ChildOrder order : datas.getRight()) {
				List<Withdraw> withdraws = orderService.getWithdrawByOrderlId(order.getChildOrderId());
				if (withdraws == null || withdraws.size() == 0) {
					order.setIsWithdraw(0);
				} else {
					order.setIsWithdraw(1);
				}
			}
			return buildDataTableResult(draw, total, datas.getLeft(), datas.getRight());
		} catch (Exception e) {
			e.printStackTrace();
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
	}

	@RequestMapping("/ajaxOrderDetail")
	@ResponseBody
	public Object orderDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam int orderId) {
		User loginInfo = (User) request.getAttribute("user");
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
			long parentOrderId = 0;
			if (!_.isEmpty(orderDetails)) {
				for (ChildOrderDetail detail : orderDetails) {
					parentOrderId = detail.getOrderId();
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
				List<RefundInfoBean> refundInfos = RefundInfoBean.getRefundInfo(parentOrderId);

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

	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String strStartDate,
			@RequestParam(required = false) String strEndDate) throws SQLException, IOException,
			ParseException {
		Date startDate = _.toDate(strStartDate);
		Date endDate = _.toDate(strEndDate);

		if ((startDate == null && endDate == null)
				|| (startDate != null && endDate != null && startDate.after(endDate))) {
			startDate = new DateTime().millisOfDay().setCopy(0).toDate();
			endDate = new DateTime().plusDays(1).millisOfDay().setCopy(0).toDate();
		}
		List<Withdraw> withdraws = orderService.getWithdrawList(startDate, endDate);

		String filename = "withdraw_" + _.formatDate(startDate, "yyyy-MM-dd") + "_"
				+ _.formatDate(endDate, "yyyy-MM-dd") + ".csv";
		response.setContentType("text/csv;charset=GB2312");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);

		// Write the header line
		OutputStream out = response.getOutputStream();
		String header = "订单日期,退款日期,订单号,母订单,子订单,用户名,商品名称,单价(元),数量,退款原因,应退金额(元),订单总额(元),首减金额(元),满减金额(元),优惠券(元),在线支付总额(元),在线支付类型,学校,联系电话,支付ID,商户退款流水号,支付交易号\n";
		out.write(header.getBytes(Charset.forName("GB2312")));
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

	private String generateCode(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = sdf.format(new Date()) + String.format("%04d", i);
		return result;
	}

	private String getPayId(Order order) {
		if (order == null) {
			return "";
		}
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
