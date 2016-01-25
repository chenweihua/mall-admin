package com.mall.admin.vo.ump;

import java.util.Date;



/**
 * 优惠卷赠送表
 *
 */
public class CouponGive extends  Feature{
    
	private static final long serialVersionUID = -3847561445384080914L;
	
	
	/**
	 * status状态值
	 */
	public static final String WAITING_HANDLE = "0";
	public static final String IN_HANDLE = "1";
	public static final String FAIL_VALID = "2";
	public static final String FAIL_HANDLE = "3";
	public static final String SUCCESS_HANDLE = "4";
	public static final String SAVED = "8";
	public static final String DEL = "9";
	
	
	/**
	 * 发送方式状态值
	 */
	public static final String GIVE_WAY_PHONENO = "1";
	public static final String GIVE_WAY_AREA = "2";
	public static final String GIVE_WAY_ALL = "3";
	
	/**
	 * 用户类型
	 */
	public static final String USER_TYPE_ALL = "0";
	public static final String USER_TYPE_NEW = "1";
	public static final String USER_TYPE_OLD = "2";

	/**
	 * 主键ID
	 */
    private Long id;

    /**
     * 优惠卷批次ID
     */
    private Long couponBatchId;

    /**
     * 每个用户每个批次限制几张
     */
    private Integer receiveLimit;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 发送方式
     */
    private String giveWay;

    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 区域ID，用逗号分隔开
     */
    private String areaId;

    /**
     * 状态
     */
    private String status;
    private String orginStatus;
    
    /**
     * 校验处理结果信息
     */
    private String msg;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新者
     */
    private Long updater;


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


	public Integer getReceiveLimit() {
		return receiveLimit;
	}


	public void setReceiveLimit(Integer receiveLimit) {
		this.receiveLimit = receiveLimit;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getGiveWay() {
		return giveWay;
	}


	public void setGiveWay(String giveWay) {
		this.giveWay = giveWay;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getCreator() {
		return creator;
	}


	public void setCreator(Long creator) {
		this.creator = creator;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getUpdater() {
		return updater;
	}


	public void setUpdater(Long updater) {
		this.updater = updater;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getOrginStatus() {
		return orginStatus;
	}


	public void setOrginStatus(String orginStatus) {
		this.orginStatus = orginStatus;
	}

	
   

   
}