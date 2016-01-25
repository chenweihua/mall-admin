package com.mall.admin.web.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.util.CustomerUtil;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.TokenUtils;
import com.mall.admin.util._;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.customer.CustomerOrder;
import com.mall.admin.vo.order.customer.CustormerOrderDetail;
import com.mall.admin.vo.order.customer.OrderListBean;
import com.mall.admin.vo.order.customer.RefundResult;
import com.mall.admin.vo.order.pay.RefundInfoBean;

/***
 * 客服的话务系统接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping({ "/selleradmin/customer" })
public class CustomerAction extends BaseController {

	private static int warningNum = 1000;
	private static Date flagdate = new Date();
	
	@Autowired
	private OrderService orderService;
	
	static {
		String warningStr = Constants.CUSTOMER_WARNING;
		if (warningStr != null && warningStr.trim().matches("[0-9]{1,}")) {
			warningNum = Integer.parseInt(Constants.CUSTOMER_WARNING.trim());
		}
	}

	@RequestMapping("/getorderinfo")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response) {
		
		OrderListBean result = new OrderListBean();

		String phoneNum = request.getParameter("phone");
		String orderCode = request.getParameter("orderCode");
		String pageNumStr = request.getParameter("pagenum");
		String pageSizeStr = request.getParameter("pagesize");
		String username = request.getParameter("username");
		String requesttime = request.getParameter("requesttime");
		String sign = request.getParameter("sign");
		if (orderCode == null) {
			orderCode = "";
		}
		if (phoneNum == null) {
			phoneNum = "";
		}
		String paramsInfo = "phone=" + phoneNum + "&ordercode=" + orderCode
				+ "&pagenum=" + pageNumStr + "&pagesize=" + pageSizeStr
				+ "&username=" + username + "&requesttime=" + requesttime;
		try {
			String mysign = TokenUtils.getCustomerSign("phone=" + phoneNum
					+ "&ordercode=" + orderCode + "&pagenum=" + pageNumStr
					+ "&pagesize=" + pageSizeStr + "&username=" + username
					+ "&requesttime=" + requesttime);
			System.out.println("计算的sign:" + mysign);
			if (!mysign.equalsIgnoreCase(sign)) {
				LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"签名错误",paramsInfo);
				result.code = "-1";
				result.message = "签名错误~";
				return result;//gson.toJson(result);
			}
		} catch (UnsupportedEncodingException e1) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"计算签名异常~",paramsInfo);
			result.code = "-1";
			result.message = "计算签名异常~";
			return result;//gson.toJson(result);
		}

		if (phoneNum == null || !phoneNum.matches("[0-9]{11}")) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"号码为空或长度错误~",paramsInfo);
			result.code = "1";
			result.message = "号码为空或长度错误~";
			return result;//gson.toJson(result);
		}
		if (pageNumStr == null || !pageNumStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"页数错误~",paramsInfo);
			result.code = "1";
			result.message = "页数错误~";
			return result;//gson.toJson(result);
		}
		if (pageSizeStr == null || !pageSizeStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"每页记录数错误~",paramsInfo);
			result.code = "1";
			result.message = "每页记录数错误~";
			return result;//gson.toJson(result);
		}
		if (_.isEmpty(username)) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"客服名称为空~",paramsInfo);
			result.code = "1";
			result.message = "客服名称为空~";
			return result;//gson.toJson(result);
		}
		/**
		 * 检查24小时内查询次数是否超过设置的查询次数
		 */
		if (warningNum < 1) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"系统繁忙，请明天再试~",paramsInfo);
			result.code = "-1";
			result.message = "系统繁忙，请明天再试~";
			return result;//gson.toJson(result);
		}
		Date nowDate = new Date();
		long now = nowDate.getTime();
		long flag = flagdate.getTime();
		if (now > flag && now - flag > 24 * 60 * 60 * 1000) {
			flagdate = nowDate;
			String warningStr = Constants.CUSTOMER_WARNING;
			if (warningStr != null && warningStr.trim().matches("[0-9]{1,}")) {
				warningNum = Integer
						.parseInt(Constants.CUSTOMER_WARNING.trim());
			} else {
				warningNum = 1000;
			}
		}
		warningNum--;

		int numPerPage = Integer.parseInt(pageSizeStr);
		int pageNum = Integer.parseInt(pageNumStr);
		pageNum = pageNum < 1 ? 1 : pageNum;
		int start = (pageNum - 1) * numPerPage;
		try {
			Pair<Integer, List<ChildOrder>> ret = orderService.getChildOrderListForCustomer(phoneNum, orderCode, start, numPerPage);
			List<ChildOrder> userOrderList = ret.getRight();
			List<CustomerOrder> customerList = new ArrayList<CustomerOrder>();
			for (ChildOrder order : userOrderList) {
				CustomerOrder customerOrder = CustomerOrder.init(order);
				
				customerOrder.cashCoupon = order.getCouponPay();
				List<ChildOrderDetail> orderDetails = orderService.getChildOrderDetailListByChildOrderId(order.getChildOrderId());// OrderDetail.getOrderDetailList(order.id);
				List<Withdraw> withdraws = orderService.getWithdrawByOrderlId(order.getChildOrderId());// Withdraw.getByOrderlId(order.id);
				if (withdraws == null || withdraws.size() == 0) {
					order.setIsWithdraw(0);
					customerOrder.withDraw = "没有退款";
				} else {
					order.setIsWithdraw(1);
					customerOrder.withDraw = "有退款";
					List<RefundInfoBean> refundInfos = RefundInfoBean.getRefundInfo(order.getChildOrderId());
					customerOrder.refundInfos = refundInfos;
				}
				List<CustormerOrderDetail> cdetail = CustormerOrderDetail.init(orderDetails, withdraws);
				customerOrder.orderDetails = cdetail;

				customerList.add(customerOrder);
			}
			result.orderList = customerList;
			result.code = "0";
			result.message = "查询成功~";
			LogConstant.mallLog.error("code:{},msg:{},params:{}",0,"查询成功~",paramsInfo);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"查询退款记录异常~",paramsInfo);
			result.code = "1";
			result.message = "查询退款记录异常~";
			return result;
		}
	}

	@RequestMapping("/refund")
	@ResponseBody
	public Object refund(HttpServletRequest request,
			HttpServletResponse response) {
		String refundstr = request.getParameter("refundstr");
		if (refundstr == null || refundstr.trim().length() == 0) {
			RefundResult result = new RefundResult();
			result.code = "0";
			result.message = "申请退款记录为空~";
			return result;
		}
		refundstr = URLDecoder.decode(refundstr);
		String refunduser = request.getParameter("refunduser");
		String requesttime = request.getParameter("requesttime");
		String sign = request.getParameter("sign");
		String paramsInfo = "refundstr=" + refundstr + "&refunduser="
				+ refunduser + "&requesttime=" + requesttime;

		RefundResult result = CustomerUtil.checkRequestInfo(request);
		if (!"0".equals(result.code)) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,result.message,paramsInfo);
			return result;
		}

		List<Pair<Long, Integer>> detailList = new ArrayList<>();
		String reason = "";
		try {
			JsonNode jsonNode = JsonUtil.parse(refundstr);
			reason = jsonNode.findValue("reason").textValue();
			Iterator<JsonNode> iterator = jsonNode.findValue("detailList")
					.elements();
			while (iterator.hasNext()) {
				JsonNode elem = iterator.next();
				long detailId = elem.findValue("orderDetailId").asLong();
				int withdrawNum = elem.findValue("withdrawNum").asInt();
				detailList.add(Pair.of(detailId, withdrawNum));
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"申请退款异常~",paramsInfo);
			result.code = "1";
			result.message = "申请退款异常：" + e.getMessage();
			return result;
		}
		if (detailList.size() == 0) {
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"申请退款记录为空~",paramsInfo);
			result.code = "1";
			result.message = "申请的退款记录为空~";
			return result;
		}

		try {
			
			Pair<Integer, String> info = orderService.withdraw(detailList, reason, 0L, refunduser);
			
			if(info.getLeft() == 0) {
				result.code = "0";
				result.message = "申请退款成功~";
				return result;
			} else {
				result.code = info.getLeft()+"";
				result.message = info.getRight();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"申请退款异常~",paramsInfo);
			result.code = "1";
			result.message = "申请退款异常：" + e.getMessage();
			return result;
		}
	}
	
}
