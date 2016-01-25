package com.mall.admin.vo.ump;

import java.util.Date;



/**
 * 优惠卷赠送log表
 *
 */
public class CouponGiveLog{
    
	private static final long serialVersionUID = -3847561445384080914L;
	
	
	public static final String STATUS_NO_SEND = "0";
	public static final String STATUS_SENDED = "1";
	

	/**
	 * 主键ID
	 */
    private Long id;

    /**
     * 优惠卷批次ID
     */
    private Long couponBatchId;

    /**
     * couponGive ID
     */
    private Long couponGiveId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 金额,以分为单位
     */
    private int money;
    
    
    /**
     * 创建时间 
     */
    private Date createTime ;

    /**
     * 状态
     */
    private String status;
    private String orginStatus;
    
    

    /**
     * 最后更新时间
     */
    private Date updateTime;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getCouponBatchId() {
		return couponBatchId;
	}



	public void setCouponBatchId(Long couponBatchId) {
		this.couponBatchId = couponBatchId;
	}



	public Long getCouponGiveId() {
		return couponGiveId;
	}



	public void setCouponGiveId(Long couponGiveId) {
		this.couponGiveId = couponGiveId;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getPhoneNo() {
		return phoneNo;
	}



	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	public int getMoney() {
		return money;
	}



	public void setMoney(int money) {
		this.money = money;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getOrginStatus() {
		return orginStatus;
	}



	public void setOrginStatus(String orginStatus) {
		this.orginStatus = orginStatus;
	}



	public Date getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	

   
}