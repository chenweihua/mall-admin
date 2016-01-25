package com.mall.admin.service.order.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.mall.admin.constant.DeliverCompanyConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.delivery.DeliverCompanyDao;
import com.mall.admin.model.dao.delivery.DeliveryDao;
import com.mall.admin.model.dao.order.OrderDao;
import com.mall.admin.model.dao.order.OutStorageRecordDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.delivery.DeliverInfoService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.util._;
import com.mall.admin.vo.delivery.Delivery;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.OutStorageRecord;
import com.mall.admin.vo.order.PrintInfo;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.customer.RefundResult;
import com.mall.admin.vo.user.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CollegeService collegeService;

	@Autowired
	private OutStorageRecordDao outStorageRcordDao;
	
	@Autowired
	private DeliverCompanyDao deliverCompanyDao;
	
	@Autowired
	private DeliverInfoService deliverInfoService;
	
	@Autowired
	private DeliveryDao deliveryDao;

	@Override
	public Order getOrderById(long id) {
		return orderDao.getOrderById(id);
	}

	@Override
	public ChildOrder getChildOrderById(long id) {
		return orderDao.getChildOrderById(id);
	}

	@Override
	public ChildOrder getChildOrderByCode(String childOrderCode) {
		return orderDao.getChildOrderByCode(childOrderCode);
	}

	@Override
	public Pair<Integer, List<ChildOrder>> getChildOrderList(User user, String phone, int status, int orderType,
			Date startDate, Date endDate, String orderCode, String porderCode, String deliverSheetCode, List<Long> storageId, String deliverStatus, int start, int numPerPage) {

		List<Long> rdcStorateIds = Lists.newArrayList();
		List<Storage> rdcStorages = user.getRdcStorageList();
		if (!_.isEmpty(rdcStorages)) {
			for (Storage rstorage : rdcStorages) {
				rdcStorateIds.add(rstorage.getStorageId());
			}
		}

		List<Long> ldcStorateIds = Lists.newArrayList();
		List<Storage> ldcStorages = user.getLdcStorageList();
		if (!_.isEmpty(ldcStorages)) {
			for (Storage lstorage : ldcStorages) {
				ldcStorateIds.add(lstorage.getStorageId());
			}
		}
		List<College> colleges = collegeService.getListByStorateids(rdcStorateIds, ldcStorateIds);

		List<Long> collegeIds = Lists.newArrayList();

		if (!_.isEmpty(colleges)) {
			for (College college : colleges) {
				collegeIds.add(college.getCollegeId());
			}
		}

		return orderDao.getChildOrderList(collegeIds, phone, status, orderType, startDate, endDate, orderCode,
				porderCode, deliverSheetCode,storageId,deliverStatus, start, numPerPage);
	}
	
	//查询第三方卖家订单
	@Override
	public Pair<Integer, List<ChildOrder>> getThirdSellerChildOrderList(String phone, int status, int orderType,
			Date startDate, Date endDate, String orderCode, String deliverSheetCode, List<Long> storageId, String deliverStatus, Integer deliveryStatus, int start, int numPerPage) {

		return orderDao.getThirdSellerChildOrderList(phone, status, orderType, startDate, endDate, orderCode,
				 deliverSheetCode,storageId,deliverStatus, deliveryStatus, start, numPerPage);
	}
	

	@Override
	public Pair<Integer, List<ChildOrder>> getChildOrderListForCustomer(String phone, String orderCode, int start,
			int numPerPage) {
		return orderDao.getChildOrderListForCustomer(phone, orderCode, start, numPerPage);
	}

	@Override
	public ChildOrderDetail getChildOrderDetailById(long id) {
		return orderDao.getChildOrderDetailById(id);
	}

	@Override
	public List<ChildOrderDetail> getChildOrderDetailListByChildOrderId(long childOrderId) {
		return orderDao.getChildOrderDetailListByChildOrderId(childOrderId);
	}

	@Override
	public List<Withdraw> getWithdrawByOrderlId(long orderId) {
		return orderDao.getWithdrawByOrderId(orderId);
	}

	@Override
	public void insertWithdraw(Withdraw withdraw) {
		orderDao.insertWithdraw(withdraw);
	}

	@Override
	public void updateWithdraw(Withdraw withdrawOld) {
		orderDao.updateWithdraw(withdrawOld);
	}

	@Override
	public Pair<Integer, String> withdraw(List<Pair<Long, Integer>> detailList, String reason, long userId,
			String name) {
		String message = null;
		ChildOrder order = null;

		RefundResult refundResult = checkRefundInfo(detailList);

		if (!"0".equals(refundResult.code)) {
			// customerLog.setCheckResultAndMessage(1,
			// result.message);
			LogConstant.mallLog.error("code:{},msg:{}", refundResult.code, refundResult.message);
			return Pair.of(_.toInt(refundResult.code, 1), refundResult.message);
		}

		for (int i = 0; i < detailList.size(); i++) {
			long orderDetailId = detailList.get(i).getLeft();
			int withdrawNum = detailList.get(i).getRight();
			// Withdraw withdraw = new Withdraw();
			ChildOrderDetail orderDetail = this.getChildOrderDetailById(orderDetailId);// ChildOrderDetail.getById(orderDetailId);
			if (order == null) {
				order = this.getChildOrderById(orderDetail.getChildOrderId()); // Order.getById(orderDetail.orderId);
			}
			/**
			 * 在今天以前已退款个数
			 */
			List<Withdraw> withdrawList_old = this.getBeforeListByIdAndDate(orderDetail
					.getChildOrderDetailId());
			int sukNum_old = 0;
			if (withdrawList_old != null && withdrawList_old.size() > 0) {
				for (Withdraw withdraw : withdrawList_old) {
					sukNum_old += withdraw.getSkuWithdrawNum();// withdraw.skuWithdrawNum;
				}
			}

			/**
			 * 今天已退款个数
			 */
			List<Withdraw> withdrawList_new = this.getAfterListByIdAndDate(orderDetail
					.getChildOrderDetailId());
			Withdraw withdrawOld = null;
			if (withdrawList_new != null && withdrawList_new.size() > 0) {
				if (withdrawList_new.size() != 1) {
					message = orderDetail.getSkuName() + "今天("
							+ _.formatDate(new Date(), "yyyy-MM-dd") + ")的退款记录个数错误,num:("
							+ withdrawList_new.size() + ")~";
					LogConstant.mallLog.error("code:{},msg:{}", 1, message);
					return Pair.of(1, message);
				}
				withdrawOld = withdrawList_new.get(0);
			}
			int realNum = withdrawNum - sukNum_old;

			if (realNum < 0) {
				message = orderDetail.getSkuName() + "申请退款错误，已完成退款" + sukNum_old + "，大于申请退款"
						+ withdrawNum + "，无法继续退款~";
				LogConstant.mallLog.error("code:{},msg:{}", 1, message);
				return Pair.of(1, message);
			}

			if (withdrawOld == null) {
				Withdraw withdraw = new Withdraw();
				withdraw.setOrderId(orderDetail.getChildOrderId());
				withdraw.setUserId(_.toLong(order.getUserId() + ""));
				withdraw.setOrderDetailId(orderDetail.getChildOrderDetailId());
				withdraw.setSkuId(orderDetail.getSkuId());
				withdraw.setSkuName(orderDetail.getSkuName());
				withdraw.setSkuUnitPrice(orderDetail.getSkuPrice());
				withdraw.setSkuBuyNum(orderDetail.getSkuNum());
				withdraw.setSkuWithdrawNum(realNum);
				withdraw.setOrderCreateTime(order.getCreateTime());
				withdraw.setReason(reason);
				withdraw.setOrdertotalpay(order.getTotalPay());
				withdraw.setCollegename(collegeService.getCollege(order.getCollegeId())
						.getCollegeName());
				withdraw.setReceivername(order.getReceiverName());
				withdraw.setReceiverphone(order.getReceiverPhone());
				withdraw.setTransactionId(order.getOnlinePayId());
				withdraw.setFirstsub(order.getFirstSub());
				withdraw.setFullsub(order.getFullSub());
				withdraw.setOnlinepay(order.getOnlinePay());
				withdraw.setOnlinepaytype(order.getOnlinePayType().intValue());
				Date now = new Date();
				withdraw.setCreatetime(now);
				withdraw.setUpdatetime(now);

				// 今天没有退款记录
				// Withdraw withdraw = new Withdraw();
				// withdraw.orderId = orderDetail.orderId;
				// withdraw.userId = order.userId;
				// withdraw.orderDetailId = orderDetail.id;
				// withdraw.skuId = orderDetail.skuId;
				// withdraw.skuName = orderDetail.skuName;
				// withdraw.skuUnitPrice =
				// orderDetail.unitPrice;
				// withdraw.skuBuyNum = orderDetail.skuNum;
				// withdraw.skuWithdrawNum = realNum;
				// withdraw.orderCreateTime = order.createTime;
				// withdraw.reason = reason;
				// withdraw.orderTotalPay = order.totalPay;
				// withdraw.collegeName =
				// College.getById(order.collegeId).name;
				// withdraw.receiverPhone = order.receiverPhone;
				// withdraw.receiverName = order.receiverName;
				// withdraw.transactionId = order.onlinePayId;
				// withdraw.firstSub = order.orderCoupon;
				// withdraw.fullSub = order.sellerCoupon;
				// withdraw.onlinePay = order.onlinePay;
				// withdraw.onlinePayType = order.onlinePayType;

				// TODO
				// CashCoupon cashCoupon =
				// CashCoupon.getByOrderId(order.id);
				// if (cashCoupon != null) {
				// withdraw.coupon = cashCoupon.discount;
				// } else {
				// withdraw.coupon = 0;
				// }
				withdraw.setOperator(userId);
				withdraw.setOperatoruser(name);
				this.insertWithdraw(withdraw);
			} else {
				// withdrawOld.skuWithdrawNum = realNum;
				// withdrawOld.reason = reason;
				// withdrawOld.update();
				withdrawOld.setSkuWithdrawNum(withdrawNum);
				withdrawOld.setReason(reason);
				this.updateWithdraw(withdrawOld);
			}
		}
		return Pair.of(0, "success");
	}

	@Override
	public List<Withdraw> getBeforeListByIdAndDate(long childOrderDetailId) {
		return orderDao.getBeforeListByIdAndDate(childOrderDetailId);
	}

	@Override
	public List<Withdraw> getAfterListByIdAndDate(long childOrderDetailId) {
		return orderDao.getAfterListByIdAndDate(childOrderDetailId);
	}

	@Override
	public RefundResult checkRefundInfo(List<Pair<Long, Integer>> detailList) {
		RefundResult result = new RefundResult();
		ChildOrder childOrder = null;
		long orderId = 0;
		// 订单检查
		for (int i = 0; i < detailList.size(); i++) {
			long orderDetailId = detailList.get(i).getLeft();
			long withdrawNum = detailList.get(i).getRight();
			// Withdraw withdraw = new Withdraw();
			ChildOrderDetail orderDetail = this.getChildOrderDetailById(orderDetailId);
			if (orderDetail == null) {
				result.code = "1";
				result.message = "订单明细" + orderDetailId + "不存在~";
				return result;
			}
			if (childOrder == null) {
				childOrder = this.getChildOrderById(orderDetail.getChildOrderId());
				if (childOrder == null) {
					result.code = "1";
					result.message = "订单" + orderDetail.getChildOrderId() + "不存在~";
					return result;
				}
				if (childOrder.getOnlinePayId() == null || childOrder.getOnlinePayId().length() == 0) {
					// return asMap("status", "error",
					// "msg", "订单还木有交易id，再等等等吧骚年");
					result.code = "1";
					result.message = "订单" + orderDetail.getSkuName() + "还木有交易id，再等等吧~";
					return result;
				}
				orderId = childOrder.getChildOrderId();
			} else {
				if (orderId != orderDetail.getChildOrderId()) {
					result.code = "1";
					result.message = "退款记录中的明细不在同一个订单中~";
					return result;
				}
			}
			/* 获取今天之前已退的个数 */
			List<Withdraw> withdrawList = this.getBeforeListByIdAndDate(orderDetailId);// .getListByOrderDetailId(orderDetailId);
			long sukNum = 0;
			if (withdrawList != null && withdrawList.size() > 0) {
				for (Withdraw withdraw : withdrawList) {
					sukNum += withdraw.getSkuWithdrawNum();// skuWithdrawNum;
				}
			}
			// // /**已退个数大于购买个数，无法继续退款*/
			if (orderDetail.getSkuNum() <= sukNum) {
				result.code = "1";
				result.message = orderDetail.getSkuName() + "已退款个数" + sukNum + "，实际购买个数"
						+ orderDetail.getSkuNum() + "，无法继续退款~";
				return result;
			}
			/** 要退的个数大于购买个数 */
			if (orderDetail.getSkuNum() < withdrawNum) {
				result.code = "1";
				result.message = orderDetail.getSkuName() + "请求退款个数" + withdrawNum + "大于购买个数"
						+ orderDetail.getSkuNum() + ",无法继续退款~";
				return result;
			}
			if (withdrawNum <= sukNum) {
				result.code = "1";
				result.message = orderDetail.getSkuName() + "请求退款个数" + withdrawNum + "不大于已退个数" + sukNum
						+ "，不需要继续退款~";
				return result;
			}
		}
		result.code = "0";
		result.message = "检查退款信息成功~";
		return result;
	}

	@Override
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate) {
		return orderDao.getWithdrawList(startDate, endDate);
	}
	
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate, List<Long> storageIds) {
		return orderDao.getWithdrawList(startDate, endDate, storageIds);
	}

	@Override
	public boolean isFirstOrderUser(long userId) {
		// TODO Auto-generated method stub
		return orderDao.userSuccessOrderCount(userId) > 0 ? false : true;
	}

	@Override
	public List<ChildOrder> getLdcChildOrder(long storageId, int fdcStatus, String startDate, String endDate,
			String searchStr, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return orderDao.getLDCChildOrderList(storageId, fdcStatus, startDate, endDate, searchStr,
				paginationInfo);
	}

	@Override
	public ChildOrder getLdcChildOrderByOrderCode(String orderCode) {
		// TODO Auto-generated method stub
		return orderDao.getChildOrderByCode(orderCode);
	}

	@Override
	public int updateChildOrderFdcStatus(int fdcStatus, String childOrderCode) {
		return orderDao.updateChildOrderFdcStatus(fdcStatus, childOrderCode);
	}

	@Override
	public List<PrintInfo> getPrintList(long childOrderId) {
		// TODO Auto-generated method stub
		return orderDao.getPrintInfoList(childOrderId);
	}

	@Override
	public long getUnOutStorageOrderCount(long storageId, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return orderDao.getLdcOrderCountByQuery(2, storageId, beginDate, endDate);
	}

	@Override
	public long getUnPrintStorageOrderCount(long storageId) {
		// TODO Auto-generated method stub
		return orderDao.getLdcOrderCountByQuery(1, storageId, null, null);
	}

	@Override
	public int addOutStorageRecord(OutStorageRecord record) {
		// TODO Auto-generated method stub
		return outStorageRcordDao.insertRecord(record);
	}
	
	/**
	 * 批量导入物流信息
	 * datas为导入的数据
	 * storageIds为当前登录用户所允许操作的仓库ID，做验证使用，防止用户填写无权限的订单号
	 */
	public String importDeliveryInfo(List<String[]> datas,List<Long> storageIds) {
		
		if(CollectionUtils.isEmpty(datas)) {
			return "数据为空";
		}
		
		if(CollectionUtils.isEmpty(storageIds)) {
			return "该用户无权限导入任何物流信息";
		}
		
		int totalNum = datas.size();
		int successNum = 0;
		int currentNum = 1;  //读入文件第一行固定是标题，读取的内容从第二行开始
		StringBuffer errorMsg = new StringBuffer("[");
		
		for(String[] data : datas) {
			currentNum ++;
			String childOrderCode = data[0];//子订单编号
			String deliverCompanyName = data[1]; //物流公司名
			String deliverSheetCode = data[2]; //物流编号
			
			//各个字段不允许为空
			if(StringUtils.isEmpty(childOrderCode) || StringUtils.isEmpty(deliverCompanyName) || StringUtils.isEmpty(deliverSheetCode)) {
				errorMsg.append("第" + currentNum + "行信息不全,");
				LogConstant.mallLog.error("childOrderCode:{},deliverCompanyName:{},deliverSheetCode:{}不能为空",childOrderCode,deliverCompanyName,deliverSheetCode);
				continue;
			}
			
			//校验子订单号是否在权限内
			ChildOrder childOrder = orderDao.getChildOrderByCode(childOrderCode);
			
			if(childOrder == null) {
				errorMsg.append("第" + currentNum + "行无此订单号,");
				LogConstant.mallLog.error("根据childOrderCode:" + childOrderCode + "查询不到对应的childorder");
				continue;
			}
			
			if(!storageIds.contains(childOrder.getStorageId())) {
				errorMsg.append("第" + currentNum + "行无此订单号,");
				LogConstant.mallLog.error("根据childOrderCode:" + childOrderCode + "所对应的storageId:" + childOrder.getStorageId() + "不在当前用户权限范围内:" + storageIds);
				continue;
			}
			
			//校验物流公司名是否合法
			String deliverCompanyCode = DeliverCompanyConstant.getDeliverCompanyByName(deliverCompanyName);
			if(deliverCompanyCode == null) {
				errorMsg.append("第" + currentNum + "行无此物流公司,");
				LogConstant.mallLog.error("根据deliverCompanyName:" + deliverCompanyName + "查询不到deliverCompanyCode");
				continue;
			}
			
			boolean success = updateDeliveryInfo(childOrderCode,deliverCompanyCode,deliverSheetCode);
			if(success) {
				successNum ++;
			} else {
				errorMsg.append("第" + currentNum + "行保存物流信息失败,");
			}
		}
		
		String tip = "一共读取" + totalNum + "条记录，记录成功" + successNum + "条";
		if(totalNum != successNum) {
			tip += ("，" + StringUtils.removeEnd(errorMsg.toString(),",") + "]");
		}
		
		return tip;
	}
	
	
	/**
	 * 记录指定子订单更新物流公司编号和物流单号，并向快递100注册信息
	 */
	public boolean updateDeliveryInfo(String childOrderCode, String deliverCompanyCode, String deliverSheetCode) {		
		//更新child_order记录（主库和分库）
		boolean updateSuccess = orderDao.updateDeliverInfo(childOrderCode, deliverCompanyCode, deliverSheetCode);
		
		//注册（如有需要）
		//如果重复注册，快递100会拦住
		boolean registerSuccess = deliverInfoService.registerDeliverInfo(deliverCompanyCode, deliverSheetCode);
		
		//添加物流信息记录
		if(registerSuccess) {
			try {
				Delivery delivery = new Delivery();
				delivery.setDeliveryCompanyCode(deliverCompanyCode);
				delivery.setDeliverySheetCode(deliverSheetCode);
				delivery.setSource(Delivery.SOURCE_KUAIDI100);
				delivery.setStatus(Delivery.STATUS_NO_INFO);
				delivery.setCreateTime(new Date());
				deliveryDao.insert(delivery);
			} catch(DuplicateKeyException ex) {
				//重复主键，说明有用户或多个用户输入了相同的物流公司和物流编码，或是用户重复保存,系统忽略
				//do nothing
			}
		}
	
		return updateSuccess && registerSuccess;
	}
	
	
	public boolean ignoreDeliveryStatus(String childOrderCode, Long userId) {
		
		return orderDao.updateDeliveryStatus(childOrderCode, userId, Delivery.NORMAL_STATUS_NORMAL);
		
	}

}
