package com.mall.admin.vo.ump;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 邀请奖励
 */
public class CouponRewardsInfo implements Serializable{

	private static final long serialVersionUID = -1593753730981618470L;
	
    private Date createTime;
	/**
	 * 优惠券ID
	 */
	private Long couponDetailId;
	
	/**
	 * 优惠券批次ID
	 */
	private Long batchId;
	/**
	 * 用户ID
	 */
	private Long businessId;
	
	/**
	 * 被邀请者ID
	 */
	private Long releaseBusinessId;
	/**
	 * 优惠券面值
	 */
	private Long amount;
	/**
	 * 优惠券使用状态 1：未使用，2已使用
	 */
	private int status;
	/**
	 * 被邀请者手机号
	 */
	private String bussinessMobile;
	/**
	 * 邀请者手机号
	 */
	private String releaseBussinessMobile;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 头像
	 */
	private String headUrl;
	
	private Integer source;
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
	 * @return the couponBatchId
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
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
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
	 * @return the bussinessMobile
	 */
	public String getBussinessMobile() {
		return bussinessMobile;
	}
	/**
	 * @param bussinessMobile the bussinessMobile to set
	 */
	public void setBussinessMobile(String bussinessMobile) {
		this.bussinessMobile = bussinessMobile;
	}
	/**
	 * @return the releaseBussinessMobile
	 */
	public String getReleaseBussinessMobile() {
		return releaseBussinessMobile;
	}
	/**
	 * @param releaseBussinessMobile the releaseBussinessMobile to set
	 */
	public void setReleaseBussinessMobile(String releaseBussinessMobile) {
		this.releaseBussinessMobile = releaseBussinessMobile;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the headUrl
	 */
	public String getHeadUrl() {
		return headUrl;
	}
	/**
	 * @param headUrl the headUrl to set
	 */
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	/**
	 * @return the source
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(Integer source) {
		this.source = source;
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
