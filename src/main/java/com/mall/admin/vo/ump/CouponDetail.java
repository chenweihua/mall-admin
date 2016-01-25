package com.mall.admin.vo.ump;

import java.util.Date;



/**
 * 优惠卷模型对象
 * @author 温德亮
 *
 */
public class CouponDetail extends  Feature{
	
	
	public final static int SOURCE_INVITE_COUPON = 1;  //邀请红包
	public final static int SOURCE_DIVISION_COUPON = 2; //裂变红包
	public final static int SOURCE_ACTIVE_SEND = 3; //后台主动发送
	
	
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
	private String couponName;
	/**
	 * 优惠卷额度
	 */
	private int money;
	/**
	 * 满金额(使用条件)
	 */
	private int amount;
	
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
	 * @return the couponName
	 */
	public String getCouponName() {
		return couponName;
	}
	/**
	 * @param couponName the couponName to set
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
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
}
