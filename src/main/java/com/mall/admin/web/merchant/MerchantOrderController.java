package com.mall.admin.web.merchant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.FileDownLoadUtil;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.enumdata.MerchantType;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.merchant.MerchantService;
import com.mall.admin.service.merchant.ThirdPartyOrderService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.merchant.Merchant;
import com.mall.admin.vo.merchant.MerchantSummaryOrder;
import com.mall.admin.vo.merchant.ThirdPartyOrder;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/merchant/order")
public class MerchantOrderController extends BaseController {
	@Autowired
	MerchantService merchantService;
	@Autowired
	ThirdPartyOrderService thirdpartyOrderService;
	@Autowired
	CityService cityService;

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("view")
	public Object queryMerchantView(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant != null) {
			return new ModelAndView("merchant/order/orderlist", CommonUtil.asMap("merchant", merchant,
					"userflag", 0));
		}
		if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
			return new ModelAndView("info", ImmutableMap.of("message", "您没有权限查看商户订单信息~"));
		}
		return new ModelAndView("merchant/order/orderlist", CommonUtil.asMap("userflag", 1));
	}

	@RequestMapping("/queryorder")
	@ResponseBody
	public Object queryOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer numPerPage,
			@RequestParam(value = "merchantNoList", required = false) String merchantNos,
			@RequestParam(value = "beginTime", required = false) String beginTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "orderCode", required = false) String orderCode) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant != null) {
			if (merchantNos == null || !merchantNos.equals(merchant.getMerchantNo())) {
				return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
			}
		} else {
			if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
				return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
			}
		}

		if (beginTime == null) {
			beginTime = DateUtil.dateFormat(new Date());
			endTime = DateUtil.dateFormat(new Date());
		}

		if (!DateUtil.checkStrIsDateTime(beginTime)) {
			return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
		}
		if (!DateUtil.checkStrIsDateTime(endTime)) {
			return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<String> merchantNoList = null;
		if (merchantNos != null && merchantNos.trim().length() > 0 && !"-1".equals(merchantNos)) {
			merchantNoList = new ArrayList<String>();
			String[] merchantNoArry = merchantNos.trim().split(",");
			for (String merchantNo : merchantNoArry) {
				merchantNoList.add(merchantNo);
			}
		}
		List<ThirdPartyOrder> thirdpartyOrderList = thirdpartyOrderService.getThirdParyOrderList(orderCode,
				merchantNoList, beginTime, endTime, paginationInfo);
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), thirdpartyOrderList,
				start));
	}

	@RequestMapping("/querysummaryorder")
	@ResponseBody
	public Object querySummaryOrderInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer numPerPage,
			@RequestParam(value = "merchantNoList", required = false) String merchantNos,
			@RequestParam(value = "beginTime", required = false) String beginTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "orderCode", required = false) String orderCode) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant != null) {
			if (merchantNos == null || !merchantNos.equals(merchant.getMerchantNo())) {
				return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
			}
		} else {
			if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
				return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
			}
		}

		if (beginTime == null) {
			beginTime = DateUtil.dateFormat(new Date());
			endTime = DateUtil.dateFormat(new Date());
		}

		beginTime += " 00:00:00";
		endTime += " 23:59:59";
		if (!DateUtil.checkStrIsDateTime(beginTime)) {
			return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
		}
		if (!DateUtil.checkStrIsDateTime(endTime)) {
			return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList(), start));
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<String> merchantNoList = null;
		List<MerchantSummaryOrder> summaryOrderInfoList = null;
		if (merchantNos != null && merchantNos.trim().length() > 0 && !"-1".equals(merchantNos)) {
			merchantNoList = new ArrayList<String>();
			String[] merchantNoArry = merchantNos.trim().split(",");
			for (String merchantNo : merchantNoArry) {
				merchantNoList.add(merchantNo);
			}
			summaryOrderInfoList = thirdpartyOrderService.querySummaryOrderByPage(merchantNoList,
					DateUtil.formatDateToDate(beginTime), DateUtil.formatDateToDate(endTime),
					paginationInfo);

		} else {
			// 查询汇总信息
			summaryOrderInfoList = thirdpartyOrderService.querySumSummaryOrderByPage(
					DateUtil.formatDateToDate(beginTime), DateUtil.formatDateToDate(endTime),
					paginationInfo);

		}
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), summaryOrderInfoList,
				start));

	}

	@RequestMapping("/exportorder")
	@ResponseBody
	public void exprotOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "merchantNoList", required = false) String merchantNos,
			@RequestParam(value = "beginTime", required = false) String beginTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "orderCode", required = false) String orderCode) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant != null) {
			if (merchantNos == null || !merchantNos.equals(merchant.getMerchantNo())) {
				LogConstant.mallLog.info("下载失败，您没有权限下载账单信息~");
				return;
			}
		} else {
			if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
				LogConstant.mallLog.info("下载失败，您没有权限下载账单信息~");
				return;
			}
		}

		if (beginTime == null) {
			beginTime = DateUtil.dateFormat(new Date());
			endTime = DateUtil.dateFormat(new Date());
		}

		if (!DateUtil.checkStrIsDateTime(beginTime)) {
			LogConstant.mallLog.info("下载失败，开始时间错误~");
			return;
		}
		if (!DateUtil.checkStrIsDateTime(endTime)) {
			LogConstant.mallLog.info("下载失败，结束时间错误~");
			return;
		}

		List<String> merchantNoList = null;
		if (merchantNos != null && merchantNos.trim().length() > 0 && !"-1".equals(merchantNos)) {
			merchantNoList = new ArrayList<String>();
			String[] merchantNoArry = merchantNos.trim().split(",");
			for (String merchantNo : merchantNoArry) {
				merchantNoList.add(merchantNo);
			}
		}
		List<ThirdPartyOrder> thirdpartyOrderList = thirdpartyOrderService.getThirdParyOrderList(orderCode,
				merchantNoList, beginTime, endTime);
		StringBuffer content = new StringBuffer();
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		List<City> cityList = cityService.getCityList();
		Map<Long, City> cityMap = new HashMap<Long, City>();
		for (City city : cityList) {
			cityMap.put(city.getCityId(), city);
		}
		content.append("订单号\t入账时间\t业务类型\t商户订单号\t支付时间\t门店名称\t门店ID\t所属城市\t支付方式\t支付平台\t支付状态\t订单总金额\t优惠金额\t实付金额\n");
		for (ThirdPartyOrder order : thirdpartyOrderList) {
			String payType = "未知";
			if (order.getOnlinePayType() == 1) {
				payType = "微信";
			} else if (order.getOnlinePayType() == 2) {
				payType = "支付宝";
			} else if (order.getOnlinePayType() == 3) {
				payType = "余额支付";
			}
			String device = "未知";
			if (order.getDevice() == 0) {
				device = "APP";
			} else if (order.getDevice() == 1) {
				device = "微信商城";
			} else if (order.getDevice() == 2) {
				device = "IOS客户端";
			} else if (order.getDevice() == 3) {
				device = "android客户端";
			}
			String status = "未知";
			if (order.getStatus() == 5) {
				status = "支付完成";
			} else if (order.getStatus() == 6) {
				status = "退款中";
			} else if (order.getStatus() == 7) {
				status = "退款完成";
			}

			String cityName = "未知";
			City merchantArea = cityMap.get(order.getMerchantAreaId());
			if (merchantArea != null) {
				City merchantCity = cityMap.get(merchantArea.getCityId());
				if (merchantCity != null) {
					cityName = merchantCity.getCityName();
				}
			}

			String merchantType = MerchantType.getNameByType(order.getMerchantType());

			content.append(order.getOrderCode() + "\t");
			content.append(sdf.format(order.getCreateTime()) + "\t");
			content.append(merchantType + "\t");
			content.append(order.getOnlinePayId() + "\t");
			content.append(sdf.format(order.getPayTime()) + "\t");
			content.append(order.getMerchantName() + "\t");
			content.append(order.getMerchantNo() + "\t");
			content.append(cityName + "\t");
			content.append(payType + "\t");
			content.append(device + "\t");
			content.append(status + "\t");
			content.append(order.getTotalPay() / 100.0 + "\t");
			content.append(order.getCouponPay() / 100.0 + "\t");
			content.append(order.getOnlinePay() / 100.0 + "\t");
			content.append("\n");
		}
		try {
			FileDownLoadUtil.downloadCSV(response, "订单记录", content.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping("/exportsummaryorder")
	@ResponseBody
	public void exprotSummaryOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "merchantNoList", required = false) String merchantNos,
			@RequestParam(value = "beginTime", required = false) String beginTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "orderCode", required = false) String orderCode) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant != null) {
			if (merchantNos == null || !merchantNos.equals(merchant.getMerchantNo())) {
				LogConstant.mallLog.info("下载失败，您没有权限下载账单信息~");
				return;
			}
		} else {
			if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
				LogConstant.mallLog.info("下载失败，您没有权限下载账单信息~");
				return;
			}
		}

		if (beginTime == null) {
			beginTime = DateUtil.dateFormat(new Date());
			endTime = DateUtil.dateFormat(new Date());
		}

		beginTime += " 00:00:00";
		endTime += " 23:59:59";
		if (!DateUtil.checkStrIsDateTime(beginTime)) {
			LogConstant.mallLog.info("下载失败，开始时间不正确~");
			return;
		}
		if (!DateUtil.checkStrIsDateTime(endTime)) {
			LogConstant.mallLog.info("下载失败，结束时间不正确~");
			return;
		}

		List<String> merchantNoList = null;
		List<MerchantSummaryOrder> summaryOrderInfoList = null;
		if (merchantNos != null && merchantNos.trim().length() > 0 && !"-1".equals(merchantNos)) {
			// 查询对应的商户信息
			merchantNoList = new ArrayList<String>();
			String[] merchantNoArry = merchantNos.trim().split(",");
			for (String merchantNo : merchantNoArry) {
				merchantNoList.add(merchantNo);
			}
			summaryOrderInfoList = thirdpartyOrderService.querySummaryOrder(merchantNoList,
					DateUtil.formatDateToDate(beginTime), DateUtil.formatDateToDate(endTime));
		} else {
			// 查询所有商户的汇总信息
			summaryOrderInfoList = thirdpartyOrderService.querySumSummaryOrder(
					DateUtil.formatDateToDate(beginTime), DateUtil.formatDateToDate(endTime));
		}

		StringBuffer content = new StringBuffer();
		content.append("商户名称\t日期\t应收总金额\t实付总金额\t交易笔数\n");
		if (summaryOrderInfoList != null) {
			for (MerchantSummaryOrder summaryOrder : summaryOrderInfoList) {
				content.append(summaryOrder.getMerchantName() + "\t");
				content.append(summaryOrder.getRecordDate() + "\t");
				content.append(summaryOrder.getSumTotalPay() / 100.0 + "\t");
				content.append(summaryOrder.getSumOnlinePay() / 100.0 + "\t");
				content.append(summaryOrder.getSumCount() + "\t");
				content.append("\n");
			}
		}

		try {
			FileDownLoadUtil.downloadCSV(response, "财务汇总", content.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}
}
