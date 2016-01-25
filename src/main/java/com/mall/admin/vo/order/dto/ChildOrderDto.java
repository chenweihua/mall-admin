package com.mall.admin.vo.order.dto;

import java.util.List;

import com.mall.admin.util.EscapeUtil;
import com.mall.admin.vo.order.ChildOrder;

public class ChildOrderDto {

	// 支付类型-微信
	public static final long PAY_WEIXIN = 0;
	// 支付类型-支付宝
	public static final long PAY_ZHIFUBAO = 1;

	// 订单状态-未支付
	public static final int PAY_STATUS_UNPAY = 1;
	// 订单状态-秒杀失效||缺少库存
	public static final int CREATE_ORDER_FAIL = 2;
	// 订单状态-订单超时
	public static final int CREATE_ORDER_TIMEOUT = 3;
	// 订单状态-支付中
	public static final int PAY_STATUS_ING = 4;
	// 订单状态-支付成功
	public static final int PAY_STATUS_DONE = 5;
	// 订单状态-支付超时
	public static final int PAY_STATUS_TIMEOUT = 6;

	// 订单超时时间(30分钟)
	public static final int TIMEOUT = 30 * 60 * 1000;

	public static final String ORDER_SUFFIX = "001XM";

	// FDC status,未打印
	public static final int FDC_NOT_PRINTED = 1;

	// FDC status,待打包
	public static final int FDC_READY_PACKAGE = 2;

	// FDC status,已打包
	public static final int FDC_PACKAGED = 3;

	private long id;

	private long userId;

	private long collegeId;

	private String collegeName;

	private String receiverName;

	private String receiverPhone;

	private String receiverAddress;

	private long totalPay;

	private long onlinePay;

	private long fillPay;

	private long orderCoupon;

	private long sellerCoupon;

	private long payTypeCoupon;

	private String onlinePayId;

	private long onlinePayType;

	private long balancePay;

	private long scorePay;

	private long score;

	private String remark;

	private String orderCode;

	private int deliveryType;

	private int distributeType;

	private long p2pDeliveryStartTime;

	private long p2pDeliveryEndTime;

	private int status;

	private int fdcStatus;

	private long createTime;

	private long updateTime;

	private long supermarketId;

	private String courierName;

	private String courierPhone;

	private List<ChildOrderDetailDto> details;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(long onlinePay) {
		this.onlinePay = onlinePay;
	}

	public long getFillPay() {
		return fillPay;
	}

	public void setFillPay(long fillPay) {
		this.fillPay = fillPay;
	}

	public long getOrderCoupon() {
		return orderCoupon;
	}

	public void setOrderCoupon(long orderCoupon) {
		this.orderCoupon = orderCoupon;
	}

	public long getSellerCoupon() {
		return sellerCoupon;
	}

	public void setSellerCoupon(long sellerCoupon) {
		this.sellerCoupon = sellerCoupon;
	}

	public long getPayTypeCoupon() {
		return payTypeCoupon;
	}

	public void setPayTypeCoupon(long payTypeCoupon) {
		this.payTypeCoupon = payTypeCoupon;
	}

	public String getOnlinePayId() {
		return onlinePayId;
	}

	public void setOnlinePayId(String onlinePayId) {
		this.onlinePayId = onlinePayId;
	}

	public long getOnlinePayType() {
		return onlinePayType;
	}

	public void setOnlinePayType(long onlinePayType) {
		this.onlinePayType = onlinePayType;
	}

	public long getBalancePay() {
		return balancePay;
	}

	public void setBalancePay(long balancePay) {
		this.balancePay = balancePay;
	}

	public long getScorePay() {
		return scorePay;
	}

	public void setScorePay(long scorePay) {
		this.scorePay = scorePay;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	public long getP2pDeliveryStartTime() {
		return p2pDeliveryStartTime;
	}

	public void setP2pDeliveryStartTime(long p2pDeliveryStartTime) {
		this.p2pDeliveryStartTime = p2pDeliveryStartTime;
	}

	public long getP2pDeliveryEndTime() {
		return p2pDeliveryEndTime;
	}

	public void setP2pDeliveryEndTime(long p2pDeliveryEndTime) {
		this.p2pDeliveryEndTime = p2pDeliveryEndTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFdcStatus() {
		return fdcStatus;
	}

	public void setFdcStatus(int fdcStatus) {
		this.fdcStatus = fdcStatus;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getSupermarketId() {
		return supermarketId;
	}

	public void setSupermarketId(long supermarketId) {
		this.supermarketId = supermarketId;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierPhone() {
		return courierPhone;
	}

	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}

	public List<ChildOrderDetailDto> getDetails() {
		return details;
	}

	public void setDetails(List<ChildOrderDetailDto> details) {
		this.details = details;
	}

	public int getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(int distributeType) {
		this.distributeType = distributeType;
	}

	public static ChildOrderDto init(ChildOrder childOrder) {
		ChildOrderDto dto = new ChildOrderDto();
		dto.id = childOrder.getChildOrderId();
		dto.orderCode = childOrder.getChildOrderCode();
		dto.receiverName = EscapeUtil.filter(childOrder.getReceiverName());
		dto.receiverPhone = childOrder.getReceiverPhone();
		dto.createTime = childOrder.getCreateTime().getTime();
		if (childOrder.getDeliveryBeginTime() != null) {
			dto.p2pDeliveryStartTime = childOrder.getDeliveryBeginTime().getTime();
		}
		if (childOrder.getDeliveryEndTime() != null) {
			dto.p2pDeliveryEndTime = childOrder.getDeliveryEndTime().getTime();
		}
		dto.receiverAddress = childOrder.getDeliveryAddress();
		dto.deliveryType = childOrder.getDeliveryType();
		dto.distributeType = childOrder.getDistributeType();
		dto.fdcStatus = childOrder.getFdcStatus();
		dto.collegeId = childOrder.getCollegeId();
		dto.remark = childOrder.getRemark();
		return dto;
	}
}
