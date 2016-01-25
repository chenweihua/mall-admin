package com.mall.admin.vo.order;

import java.util.Date;

public class ChildOrder {

	// FDC status,未打印
	public static final int FDC_NOT_PRINTED = 1;

	// FDC status,待打包
	public static final int FDC_READY_PACKAGE = 2;

	// FDC status,已打包
	public static final int FDC_PACKAGED = 3;
	// 配送
	public static final int P2P_DELIVERY = 1;
	// 自提
	public static final int SELF_DELIVERY = 0;

	private Long childOrderId;

	private Long orderId;

	private Long userId;

	private Long collegeId;

	private String receiverName;

	private String receiverPhone;

	private Integer totalPay;

	private Integer freight;

	private String orderCode;

	private String childOrderCode;

	private String remark;

	private Byte distributeType;

	private Byte status;

	private Date deliveryBeginTime;

	private Date deliveryEndTime;

	private Byte deliveryType;

	private Date createTime;

	private Date updateTime;

	private Date selfPickUpTime;
	private String selfPickUpAddress;
	private String deliveryAddress;
	private int freightSub;
	private int fdcStatus;

	private int isWithdraw;
	
	
	/**
	 * 物流公司编码
	 */
	private String deliverCompanyCode;
	
	/**
	 * 物流单号
	 */
	private String deliverSheetCode;
	
	/**
	 * 物流公司名称
	 */
	private String deliverCompanyName;
	
	
	/**
	 * 该子订单如为第三方卖家订单，其关联的tb_storage主键
	 */
	private Long storageId;
	
	
	private Integer deliveryStatus;
	

	public int getIsWithdraw() {
		return isWithdraw;
	}

	public void setIsWithdraw(int isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public Long getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(Long childOrderId) {
		this.childOrderId = childOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
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

	public Integer getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Integer totalPay) {
		this.totalPay = totalPay;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getChildOrderCode() {
		return childOrderCode;
	}

	public void setChildOrderCode(String childOrderCode) {
		this.childOrderCode = childOrderCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getDeliveryBeginTime() {
		return deliveryBeginTime;
	}

	public void setDeliveryBeginTime(Date deliveryBeginTime) {
		this.deliveryBeginTime = deliveryBeginTime;
	}

	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(Byte distributeType) {
		this.distributeType = distributeType;
	}

	public Date getSelfPickUpTime() {
		return selfPickUpTime;
	}

	public void setSelfPickUpTime(Date selfPickUpTime) {
		this.selfPickUpTime = selfPickUpTime;
	}

	public String getSelfPickUpAddress() {
		return selfPickUpAddress;
	}

	public void setSelfPickUpAddress(String selfPickUpAddress) {
		this.selfPickUpAddress = selfPickUpAddress;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public int getFreightSub() {
		return freightSub;
	}

	public void setFreightSub(int freightSub) {
		this.freightSub = freightSub;
	}

	public int getFdcStatus() {
		return fdcStatus;
	}

	public void setFdcStatus(int fdcStatus) {
		this.fdcStatus = fdcStatus;
	}

	// =====================================================

	private Integer onlinePay;

	private String onlinePayId;

	private Byte onlinePayType;

	private Long couponId;

	private Integer couponPay;

	private Integer firstSub;

	private Integer fullSub;

	private Integer fillPay;

	private Byte orderStatus;

	public Integer getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Integer onlinePay) {
		this.onlinePay = onlinePay;
	}

	public String getOnlinePayId() {
		return onlinePayId;
	}

	public void setOnlinePayId(String onlinePayId) {
		this.onlinePayId = onlinePayId;
	}

	public Byte getOnlinePayType() {
		return onlinePayType;
	}

	public void setOnlinePayType(Byte onlinePayType) {
		this.onlinePayType = onlinePayType;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponPay() {
		return couponPay;
	}

	public void setCouponPay(Integer couponPay) {
		this.couponPay = couponPay;
	}

	public Integer getFirstSub() {
		return firstSub;
	}

	public void setFirstSub(Integer firstSub) {
		this.firstSub = firstSub;
	}

	public Integer getFullSub() {
		return fullSub;
	}

	public void setFullSub(Integer fullSub) {
		this.fullSub = fullSub;
	}

	public Integer getFillPay() {
		return fillPay;
	}

	public void setFillPay(Integer fillPay) {
		this.fillPay = fillPay;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public Byte getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Byte deliveryType) {
		this.deliveryType = deliveryType;
	}

	public void setOrderStatus(Byte status) {
		this.orderStatus = status;
	}

	public String getDeliverCompanyCode() {
		return deliverCompanyCode;
	}

	public void setDeliverCompanyCode(String deliverCompanyCode) {
		this.deliverCompanyCode = deliverCompanyCode;
	}

	public String getDeliverSheetCode() {
		return deliverSheetCode;
	}

	public void setDeliverSheetCode(String deliverSheetCode) {
		this.deliverSheetCode = deliverSheetCode;
	}

	public String getDeliverCompanyName() {
		return deliverCompanyName;
	}

	public void setDeliverCompanyName(String deliverCompanyName) {
		this.deliverCompanyName = deliverCompanyName;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	

}