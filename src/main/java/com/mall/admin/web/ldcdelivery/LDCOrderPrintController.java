package com.mall.admin.web.ldcdelivery;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.util.HttpUtil;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.MyExecutor;
import com.mall.admin.util.PropertyUtils;
import com.mall.admin.util.impl.HttpServiceImpl;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.OutStorageRecord;
import com.mall.admin.vo.order.PrintInfo;
import com.mall.admin.vo.order.dto.ChildOrderDetailDto;
import com.mall.admin.vo.order.dto.ChildOrderDto;
import com.mall.admin.vo.user.User;

/**
 * ldc订单打印流程
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/ldcorder")
public class LDCOrderPrintController extends BaseController {

	private Logger log = LogConstant.mallLog;

	@Autowired
	OrderService orderService;

	@Autowired
	StorageService storageService;

	@Autowired
	CollegeService collegeService;

	private static final MyExecutor exector = MyExecutor.instance();

	/**
	 * 查看极速达订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view")
	public Object viewLdcOrder(HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		Date beginDate = todayStart.getTime();
		Date endDate = new Date();
		String beginDateStr = format.format(beginDate);
		String endDateStr = format.format(endDate);

		List<Storage> storageList = user.getLdcStorageList();
		if (storageList == null || storageList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "您没有负责的仓库，请先让管理员给你添加仓库~"));
		}
		for (int i = storageList.size() - 1; i > 0; i--) {
			Storage storage = storageList.get(i);
			if (storage.getPushType() == Storage.pushToXMWms) {
				storageList.remove(i);
			}
		}
		if (storageList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "您负责的LDC仓库都不在该系统下打印出库，谢谢~"));
		}
		return new ModelAndView("ldcdelivery/ldcorderlist", ImmutableMap.of("storageList", storageList,
				"startDate", beginDateStr, "endDate", endDateStr));
	}

	/**
	 * 查询极速达订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Object queryLdcOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam String beginDate, @RequestParam String endDate, @RequestParam long storageId,
			@RequestParam(defaultValue = "0") int fdcStatus,
			@RequestParam(required = false) String searchStr) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> ldcStorage = user.getLdcStorageList();
		boolean hashPermission = false;
		for (Storage storage : ldcStorage) {
			if (storage.getStorageId() == storageId) {
				hashPermission = true;
				break;
			}
		}
		if (!hashPermission) {
			LogConstant.mallLog.info("用户{}({})没有操作仓库的权限。", user.getUser_name(), user.getUser_id(),
					storageId);
			return gson.toJson(buildDataTableResult(draw, 0, 0, new ArrayList<ChildOrderDetailDto>(), start));
		}
		Date beginTime = null;
		Date endTime = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			beginTime = format.parse(beginDate);
			endTime = format.parse(endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			endTime = new Date(calendar.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
			LogConstant.mallLog.info("时间格式错误：开始时间{}，结束时间{}", beginDate, endDate);
		}
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<ChildOrder> childOrderList = orderService.getLdcChildOrder(storageId, fdcStatus,
				format.format(beginTime), format.format(endTime), searchStr, paginationInfo);
		List<ChildOrderDto> childOrderDtoList = new ArrayList<ChildOrderDto>();
		for (ChildOrder childOrder : childOrderList) {
			ChildOrderDto childOrderDto = ChildOrderDto.init(childOrder);
			List<ChildOrderDetailDto> childOrderDetailDtoList = new ArrayList<ChildOrderDetailDto>();
			List<PrintInfo> printInfoList = orderService.getPrintList(childOrder.getChildOrderId());
			for (PrintInfo printInfo : printInfoList) {
				ChildOrderDetailDto detailDto = ChildOrderDetailDto.init(printInfo);
				childOrderDetailDtoList.add(detailDto);
			}
			childOrderDto.setDetails(childOrderDetailDtoList);
			childOrderDtoList.add(childOrderDto);
		}
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), childOrderDtoList,
				start));
	}

	/**
	 * 打印服务
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/print" })
	@ResponseBody
	public Object print(HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> ldcStorage = user.getLdcStorageList();
		String storageIdStr = request.getParameter("storageId");
		if (!storageIdStr.matches("[0-9]{1,}")) {
			buildJson(1, "仓库信息错误~");
		}
		long storageId = Long.parseLong(storageIdStr);
		boolean hashPermission = false;
		Storage printStorage = null;
		for (Storage storage : ldcStorage) {
			if (storage.getStorageId() == storageId) {
				hashPermission = true;
				printStorage = storage;
				break;
			}
		}
		if (!hashPermission) {
			LogConstant.mallLog.info("用户{}({})没有操作仓库的权限。", user.getUser_name(), user.getUser_id(),
					storageId);
			buildJson(1, "骚年，你没有该仓的权限~");
		}

		List<String> orderCodes = new ArrayList<String>();
		String printData = null;
		JsonNode jsonNode = null;
		try {
			// String data = getDataFromRequest(request);
			String data = request.getParameter("printData");
			jsonNode = JsonUtil.parse(data);
			printData = jsonNode.findValue("printData").toString();

			Iterator<JsonNode> iterator = jsonNode.findValue("orderCodes").elements();
			while (iterator.hasNext()) {
				JsonNode elem = iterator.next();
				String orderCode = elem.asText();
				System.out.println(orderCode);
				orderCodes.add(orderCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			buildJson(1, "获取打印数据异常~");
		}
		String result = null;
		try {
			String userIdStr = String.format("%05d", printStorage.getVmStoreId());
			String userId = "xm" + userIdStr;
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", userId);
			map.put("type", 0 + "");
			map.put("info", printData);
			String printService = PropertyUtils.getProperty("ldcprint.print");
			result = HttpUtil.post(printService, map);
		} catch (Exception e) {
			LogConstant.mallLog.info("call print server exception\t" + e.getMessage());
			return buildJson(2, "print_server_error");
		}

		try {
			JsonNode retNode = JsonUtil.parse(result);
			int code = retNode.findValue("code").asInt();
			String msg = retNode.findValue("msg").asText();
			if (code < 0) {
				LogConstant.mallLog.info("print server code < 0, code \t" + code);
				return buildJson(1, msg);
			}
		} catch (IOException e) {
			LogConstant.mallLog.info("parser server return data exception\t" + e.getMessage());
			return buildJson(2, "print_server_error");
		}

		try {
			// final InterfaceConfig config =
			// InterfaceManager.get("mall.updateFdcStatus");
			for (String orderCode : orderCodes) {
				// 修复订单状态为已出库
				ChildOrder childOrder = orderService.getChildOrderByCode(orderCode);
				if (ChildOrder.FDC_NOT_PRINTED == childOrder.getFdcStatus()) {
					orderService.updateChildOrderFdcStatus(ChildOrder.FDC_READY_PACKAGE, orderCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "打印异常");
		}
		return buildJson(0, "打印成功");
	}

	@RequestMapping("/package")
	public Object orderPackege(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Storage> storageList = user.getLdcStorageList();
		if (storageList == null || storageList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "您没有负责的仓库，请先让管理员给你添加仓库~"));
		}

		return new ModelAndView("ldcdelivery/package");
	}

	/**
	 * 获得订单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping({ "/getOrderByOrderCode" })
	@ResponseBody
	public Object getOrderByOrderCode(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String orderCode = request.getParameter("orderCode");
		ChildOrder childOrder = orderService.getLdcChildOrderByOrderCode(orderCode);
		if (childOrder == null) {
			return ImmutableMap.of("status", "error", "msg", "此包裹不存在");
		}
		if (childOrder.getFdcStatus() == ChildOrder.FDC_NOT_PRINTED) {
			return ImmutableMap.of("status", "error", "msg", "未打印的订单不能出库");
		}
		if (childOrder.getFdcStatus() == ChildOrder.FDC_PACKAGED) {
			return ImmutableMap.of("status", "error", "msg", "已出库的订单不能再出库");
		}
		College college = CollegeConstant.getCollegeById(childOrder.getCollegeId());
				//collegeService.getCollegeById(childOrder.getCollegeId());
		Storage storage = StroageConstant.getStorageById(college.getLdcStorageId());
				//storageService.getStorageById(college.getLdcStorageId());
		ChildOrderDto childOrderDto = ChildOrderDto.init(childOrder);
		List<ChildOrderDetailDto> childOrderDetailDtoList = new ArrayList<ChildOrderDetailDto>();
		List<PrintInfo> printInfoList = orderService.getPrintList(childOrder.getChildOrderId());
		for (PrintInfo printInfo : printInfoList) {
			ChildOrderDetailDto detailDto = ChildOrderDetailDto.init(printInfo);
			childOrderDetailDtoList.add(detailDto);
		}
		childOrderDto.setDetails(childOrderDetailDtoList);

		// 配送的订单需要检查订单是否被麦客抢单
		if (childOrder.getDeliveryType() == ChildOrder.P2P_DELIVERY) {
			String retStr = "";
			try {
				// JsonObject refundInfoJson = new JsonObject();
				// refundInfoJson.addProperty("expId", "RETA");
				// refundInfoJson.addProperty("expCode",
				// childOrderDto.getOrderCode());
				// refundInfoJson.addProperty("collegeId",
				// storage.getVmCollegeId());

				// URL url = null;
				// URI uri = null;
				// try {
				// url = new URL(Constants.PAY_REFUND_INFO);
				// uri = new URI(url.getProtocol(),
				// url.getHost(), url.getPath(), url.getQuery(),
				// null);
				// } catch (MalformedURLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// return null;
				// } catch (URISyntaxException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// return null;
				// }
				// 从P2P获取订单信息
				String p2pDeliverySearch = PropertyUtils.getProperty("p2p.delivery.search");
				Map<String, String> map = new HashMap<String, String>();
				map.put("expId", "RETA");
				map.put("expCode", childOrderDto.getOrderCode());
				map.put("collegeId", storage.getVmCollegeId() + "");
				retStr = HttpUtil.post(p2pDeliverySearch, map);
				if (retStr == null || retStr.trim().length() == 0) {
					return ImmutableMap.of("stauts", "error", "msg", "没有获得订单信息~");
				}
			} catch (Exception e) {
				LogConstant.mallLog.info("call p2p server exception\t" + e.getMessage());
				ImmutableMap.of("stauts", "error", "msg", "没有获得订单信息~");
			}

			try {
				JsonNode retNode = JsonUtil.parse(retStr);
				int code = retNode.findValue("code").asInt();
				String msg = retNode.findValue("msg").asText();
				if (code < 0) {
					throw new Exception("code\t" + code + "\tmsg\t" + msg);
				}
				String courierName = retNode.findValue("courierName").asText();
				String courierPhone = retNode.findValue("courierPhone").asText();
				if (courierName == "null" || courierPhone == "null") {
					throw new Exception("麦客木有抢单~");
				}
				childOrderDto.setCourierName(courierName);
				childOrderDto.setCourierPhone(courierPhone);
			} catch (Exception e) {
				LogConstant.mallLog.info("parser server return data exception\t" + e.getMessage());
				return ImmutableMap.of("status", "error", "msg", e.getMessage());
			}
		} else {
			childOrderDto.setCourierName("");
			childOrderDto.setCourierPhone("");
		}
		return ImmutableMap.of("status", "success", "order", childOrderDto);
	}

	// 出库
	@RequestMapping({ "/doPackage" })
	@ResponseBody
	public Object doPackage(HttpServletRequest request, HttpServletResponse response) {
		int successNum = 0;
		int errorNum = 0;
		StringBuffer message = new StringBuffer("");

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<String> orderCodes = new ArrayList<String>();
		OutStorageRecord osRecord = new OutStorageRecord();
		List<ChildOrderDto> childOrderDtoList = new ArrayList<ChildOrderDto>();

		try {
			String data = request.getParameter("orderListJson");
			JsonNode jsonNode = JsonUtil.parse(data);
			Iterator<JsonNode> iterator = jsonNode.findValue("orderList").elements();
			while (iterator.hasNext()) {
				JsonNode elem = iterator.next();
				String orderCode = elem.findValue("orderCode").asText();
				String courierName = elem.findValue("courierName").asText();
				String courierPhone = elem.findValue("courierPhone").asText();
				ChildOrder childOrder = orderService.getLdcChildOrderByOrderCode(orderCode);
				//判断订单是否已出库（防止用户多次点击出库按钮）
				if(childOrder.getFdcStatus() != ChildOrder.FDC_PACKAGED){
					ChildOrderDto childOrderDto = ChildOrderDto.init(childOrder);
					childOrderDtoList.add(childOrderDto);
					// 如果是配送，记录下配送人员信息
					if (childOrderDto.getDeliveryType() == ChildOrder.P2P_DELIVERY) {
						LogConstant.mallLog.info("courierInfo from FE", courierName + "\t"
								+ courierPhone);
						osRecord.setOrderCode(orderCode);
						osRecord.setCourierName(courierName);
						osRecord.setCourierPhone(courierPhone);
						orderService.addOutStorageRecord(osRecord);
						orderCodes.add(orderCode);
					} else {
						successNum++;
						orderService.updateChildOrderFdcStatus(3, orderCode);
						message.append("订单" + orderCode + "出库成功\n");
					}
				}else{
					message.append("订单" + orderCode + "已出库，禁止重复出库！\n");
				}
			}
		} catch (IOException e) {
			LogConstant.mallLog.error("[dopackage]"+e);
			return ImmutableMap.of("status", "error");
		}
		try {
			final String pushDelivery = PropertyUtils.getProperty("openapi.api.bs.pushDelivery");
			if (childOrderDtoList != null && childOrderDtoList.size() > 0) {
				for (ChildOrderDto order : childOrderDtoList) {
					// 如果是自提，则不发送消息，如果是配送，则调用p2p向用户发送消息
					if (order.getDeliveryType() == ChildOrder.SELF_DELIVERY) {
						continue;
					}
					College college = CollegeConstant.getCollegeById(order.getCollegeId());
							//collegeService.getCollegeById(order.getCollegeId());
					Storage storage = StroageConstant.getStorageById(college.getLdcStorageId());
							//storageService.getStorageById(college.getLdcStorageId());
					final JsonObject data = new JsonObject();
					final String orderCode = order.getOrderCode();
					JsonArray items = new JsonArray();
					JsonObject item = new JsonObject();
					item.addProperty("code", order.getOrderCode());
					item.addProperty("delivery_staff", user.getUser_id());
					//配送状态
					JsonObject deliveryStatus = new JsonObject();
					deliveryStatus.addProperty("delivery_id", 0);
					deliveryStatus.addProperty("delivery_staff", "");
					deliveryStatus.addProperty("id", order.getId());
					deliveryStatus.addProperty("is_settlement", 0);
					deliveryStatus.addProperty("pre_status", 2);
					deliveryStatus.addProperty("process_staff", "");
					deliveryStatus.addProperty("remark", "29分钟限时达");
					deliveryStatus.addProperty("source", 11);
					deliveryStatus.addProperty("update_time", System.currentTimeMillis());
					item.add("delivery_status", deliveryStatus);
					item.addProperty("express_id", 260);
					item.addProperty("id", order.getId());
					item.addProperty("is_settlement", 0);
					item.addProperty("isdel", 0);
					item.addProperty("merchant", 0);
					item.addProperty("merchant_type", 0);
					item.addProperty("mobilephone", order.getReceiverPhone());
					item.addProperty("out_type", 189);
					item.addProperty("packageCode", "");
					item.addProperty("package_num", 1);
					item.addProperty("pay_type", 129);
					item.addProperty("send_type", 226);
					item.addProperty("shelf", "");
					item.addProperty("source", 11);
					//修改为125，配送中--zhsh--20151021
					item.addProperty("status", 125);
					//item.addProperty("status", 126);
					item.addProperty("status_update_time", System.currentTimeMillis());
					item.addProperty("store_id", storage.getVmStoreId());
					item.addProperty("type", 181);
					item.addProperty("update_time", System.currentTimeMillis());
					item.addProperty("user_id", order.getUserId());
					items.add(item);
					data.add("data", items);

					long begintime = System.currentTimeMillis();
					log.info("【push ldc order to p2p】:【order:{}】【date:{}】", orderCode,
							data.toString());
					String result = new HttpServiceImpl().sendPostRequest(pushDelivery,
							data.toString(), "UTF-8");
					log.info("【push ldc order to p2p】:【order:{}】【push time:{}ms】", orderCode,
							System.currentTimeMillis() - begintime);
					log.info("【push ldc order to p2p】:【order:{}】【push result:{}】", orderCode,
							result);
					String resultCode = JsonUtil.parse(result, String.class, "code");
					String msg = JsonUtil.parse(result, String.class, "msg");
					if ("0".equals(resultCode)) {
						message.append("订单" + orderCode + "出库成功\n");
						successNum++;
						orderService.updateChildOrderFdcStatus(3, orderCode);
					} else {
						message.append("订单" + orderCode + "出库失败,失败原因：" + msg + "\n");
						errorNum++;
					}
				}
			}
		} catch (Exception e) {
			LogConstant.mallLog.error("[dopackage]"+e);
			return ImmutableMap.of("status", "error", "msg", e.getMessage());
		}
		return ImmutableMap.of("status", "success", "msg", message, "successNum", successNum, "errorNum",
				errorNum);

	}

	@RequestMapping({ "/getOrdersCount" })
	@ResponseBody
	public Object getOrdersCount(HttpServletRequest request, HttpServletResponse response) {
		long ordersCount = 0;
		long newOrdersCount = 0;
		Long storageId = Long.parseLong(request.getParameter("storageId"));
		DateTime today = DateTime.now();
		DateTime beginDate = today.minusDays(10);
		ordersCount = orderService.getUnOutStorageOrderCount(storageId,
				beginDate.toString("yyyy-MM-dd HH:mm:ss"), today.toString("yyyy-MM-dd HH:mm:ss"));
		newOrdersCount = orderService.getUnPrintStorageOrderCount(storageId);
		return ImmutableMap.of("status", "success", "ordersCount", ordersCount, "newOrdersCount",
				newOrdersCount);
	}
}
