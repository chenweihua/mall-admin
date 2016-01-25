package com.mall.admin.vo.ump;

import java.util.Date;

/**
 * 通过用户查询优惠券关联订单使用情况
 * @author liqiang
 *
 */
public class Coupon extends  Feature{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 3044444398298003446L;
	
	/**
	 * 优惠卷id
	 */
	private Long couponDetailId;
	/**
	 * 券类型：0 : 全部 1、代金券
	 */
	private int couponType;
	
	/**
	 * 优惠卷名称
	 */
	private String batchName;
	/**
	 * 优惠卷额度
	 */
	private int money;
	
	//来源
	private int source;
	/**
	 * 业务id（领取红包的用户id)
	 */
	private Long businessId;
	/**
	 * 领取红包的用户名称
	 */
	private String realName;
	/**
	 * 关联id（发放红包的用户编号）
	 */
	private Long releaseBusinessId;

	/**
	 * 代金券状态：0、未领取，1、已领取，2、已使用, 3、已过期
	 */
	private int status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 有效开始时间
	 */
	private Date startTime;
	/**
	 * 有效结束时间
	 */
	private Date endTime;
	/**
	 * 批次编号
	 */
	private Long batchId;
	
	/**
	 * 使用该优惠券的订单号
	 */
	private Long orderId;
	/**
	 * 优惠券来源订单号
	 */
	private Long releaseOrderId;
	

    /**
     * 平台类型，0 :全部  1:app，2:wap
     */
    private Integer platformType;
    
    
    //已使用的金额
    private Long usedMoney;

    /**
     * 总金额
     */
    private Long totalMoney;
    
    //已使用的张数
    private Integer usedNumber;
    
    private Integer originStatus;//原状态

    /**
     * 优惠卷总张数
     */
    private Integer totalNumber;

    /**
     * 扩展磐石id
     */
    private Long psId;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改人
     */
    private Long editor;

    /**
     * 描述
     */
    private String batchDesc;
    
    //发放类型 以后可能多种，需要逗号分隔
    private String deliverType;
    
    /**
     * 关联活动ID
     */
    private Long activityId;
    
    //优惠券类型
    private Integer batchType;
	
	/**
	 * 学校
	 */
	private Integer collegeId;
	
	/**
	 * 领取物品用户名
	 */
	private String receiverName;
	//电话
	private String receiverPhone;
	//总付款
	private Long totalPay;
	//在线支付的总额
	private Long onlinePay;
	//支付类型 1 微信，2 支付宝
	private Integer onlinePayType;
	//支付后id
	private String onlinePayId;
	//优惠券金额
	private Long couponPay;
	//实际付款金额，对冲金额
	private Long fillPay;
	//支付状态
	private Integer payStatus;
	//订单创建时间
	private Date payCreateTime;
	//订单更新时间
	private Date payUpdateTime;
	//支付时间
	private Date payTime;
	//商品
	private Integer skuId;
	//类目id
	private Integer categoryId;
	//商品名称
	private String skuName;
	//商品数量
	private Integer skuNum;
	/**
	 * @return the couponDetailId
	 */
	public Long getCouponDetailId() {
		return couponDetailId;
	}
	/**
	 * @param couponDetailId the couponDetailId to set
	 */
	public void setCouponDetailId(Long couponDetailId) {
		this.couponDetailId = couponDetailId;
	}
	/**
	 * @return the couponType
	 */
	public int getCouponType() {
		return couponType;
	}
	/**
	 * @param couponType the couponType to set
	 */
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}
	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}
	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	/**
	 * @return the source
	 */
	public int getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(int source) {
		this.source = source;
	}
	/**
	 * @return the businessId
	 */
	public Long getBusinessId() {
		return businessId;
	}
	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * @return the releaseBusinessId
	 */
	public Long getReleaseBusinessId() {
		return releaseBusinessId;
	}
	/**
	 * @param releaseBusinessId the releaseBusinessId to set
	 */
	public void setReleaseBusinessId(Long releaseBusinessId) {
		this.releaseBusinessId = releaseBusinessId;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the batchId
	 */
	public Long getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the releaseOrderId
	 */
	public Long getReleaseOrderId() {
		return releaseOrderId;
	}
	/**
	 * @param releaseOrderId the releaseOrderId to set
	 */
	public void setReleaseOrderId(Long releaseOrderId) {
		this.releaseOrderId = releaseOrderId;
	}
	/**
	 * @return the platformType
	 */
	public Integer getPlatformType() {
		return platformType;
	}
	/**
	 * @param platformType the platformType to set
	 */
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	/**
	 * @return the usedMoney
	 */
	public Long getUsedMoney() {
		return usedMoney;
	}
	/**
	 * @param usedMoney the usedMoney to set
	 */
	public void setUsedMoney(Long usedMoney) {
		this.usedMoney = usedMoney;
	}
	/**
	 * @return the totalMoney
	 */
	public Long getTotalMoney() {
		return totalMoney;
	}
	/**
	 * @param totalMoney the totalMoney to set
	 */
	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}
	/**
	 * @return the usedNumber
	 */
	public Integer getUsedNumber() {
		return usedNumber;
	}
	/**
	 * @param usedNumber the usedNumber to set
	 */
	public void setUsedNumber(Integer usedNumber) {
		this.usedNumber = usedNumber;
	}
	/**
	 * @return the originStatus
	 */
	public Integer getOriginStatus() {
		return originStatus;
	}
	/**
	 * @param originStatus the originStatus to set
	 */
	public void setOriginStatus(Integer originStatus) {
		this.originStatus = originStatus;
	}
	/**
	 * @return the totalNumber
	 */
	public Integer getTotalNumber() {
		return totalNumber;
	}
	/**
	 * @param totalNumber the totalNumber to set
	 */
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	/**
	 * @return the psId
	 */
	public Long getPsId() {
		return psId;
	}
	/**
	 * @param psId the psId to set
	 */
	public void setPsId(Long psId) {
		this.psId = psId;
	}
	/**
	 * @return the creator
	 */
	public Long getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * @return the editor
	 */
	public Long getEditor() {
		return editor;
	}
	/**
	 * @param editor the editor to set
	 */
	public void setEditor(Long editor) {
		this.editor = editor;
	}
	/**
	 * @return the batchDesc
	 */
	public String getBatchDesc() {
		return batchDesc;
	}
	/**
	 * @param batchDesc the batchDesc to set
	 */
	public void setBatchDesc(String batchDesc) {
		this.batchDesc = batchDesc;
	}
	/**
	 * @return the deliverType
	 */
	public String getDeliverType() {
		return deliverType;
	}
	/**
	 * @param deliverType the deliverType to set
	 */
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}
	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	/**
	 * @return the batchType
	 */
	public Integer getBatchType() {
		return batchType;
	}
	/**
	 * @param batchType the batchType to set
	 */
	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}
	/**
	 * @return the collegeId
	 */
	public Integer getCollegeId() {
		return collegeId;
	}
	/**
	 * @param collegeId the collegeId to set
	 */
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**
	 * @return the receiverPhone
	 */
	public String getReceiverPhone() {
		return receiverPhone;
	}
	/**
	 * @param receiverPhone the receiverPhone to set
	 */
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	/**
	 * @return the totalPay
	 */
	public Long getTotalPay() {
		return totalPay;
	}
	/**
	 * @param totalPay the totalPay to set
	 */
	public void setTotalPay(Long totalPay) {
		this.totalPay = totalPay;
	}
	/**
	 * @return the onlinePay
	 */
	public Long getOnlinePay() {
		return onlinePay;
	}
	/**
	 * @param onlinePay the onlinePay to set
	 */
	public void setOnlinePay(Long onlinePay) {
		this.onlinePay = onlinePay;
	}
	/**
	 * @return the onlinePayType
	 */
	public Integer getOnlinePayType() {
		return onlinePayType;
	}
	/**
	 * @param onlinePayType the onlinePayType to set
	 */
	public void setOnlinePayType(Integer onlinePayType) {
		this.onlinePayType = onlinePayType;
	}
	/**
	 * @return the onlinePayId
	 */
	public String getOnlinePayId() {
		return onlinePayId;
	}
	/**
	 * @param onlinePayId the onlinePayId to set
	 */
	public void setOnlinePayId(String onlinePayId) {
		this.onlinePayId = onlinePayId;
	}
	/**
	 * @return the couponPay
	 */
	public Long getCouponPay() {
		return couponPay;
	}
	/**
	 * @param couponPay the couponPay to set
	 */
	public void setCouponPay(Long couponPay) {
		this.couponPay = couponPay;
	}
	/**
	 * @return the fillPay
	 */
	public Long getFillPay() {
		return fillPay;
	}
	/**
	 * @param fillPay the fillPay to set
	 */
	public void setFillPay(Long fillPay) {
		this.fillPay = fillPay;
	}
	/**
	 * @return the payStatus
	 */
	public Integer getPayStatus() {
		return payStatus;
	}
	/**
	 * @param payStatus the payStatus to set
	 */
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * @return the payCreateTime
	 */
	public Date getPayCreateTime() {
		return payCreateTime;
	}
	/**
	 * @param payCreateTime the payCreateTime to set
	 */
	public void setPayCreateTime(Date payCreateTime) {
		this.payCreateTime = payCreateTime;
	}
	/**
	 * @return the payUpdateTime
	 */
	public Date getPayUpdateTime() {
		return payUpdateTime;
	}
	/**
	 * @param payUpdateTime the payUpdateTime to set
	 */
	public void setPayUpdateTime(Date payUpdateTime) {
		this.payUpdateTime = payUpdateTime;
	}
	/**
	 * @return the payTime
	 */
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * @return the skuId
	 */
	public Integer getSkuId() {
		return skuId;
	}
	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the skuName
	 */
	public String getSkuName() {
		return skuName;
	}
	/**
	 * @param skuName the skuName to set
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	/**
	 * @return the skuNum
	 */
	public Integer getSkuNum() {
		return skuNum;
	}
	/**
	 * @param skuNum the skuNum to set
	 */
	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}
	
}
