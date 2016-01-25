package com.mall.admin.vo.ump;

import java.util.Date;




/**
 * 优惠卷批次表
 * @author 温德亮
 *
 */
public class CouponBatch extends  Feature{
    
	private static final long serialVersionUID = -3847561445384080914L;
	
	
	    
	public final static int STATUS_ALL = 0; //0 全部
	public final static int STATUS_NEW = 1; //1 ：新建 
	public final static int STATUS_IN_RECEIVE = 2; //2：领取中
	public final static int STATUS_DONE_RECEIVE = 3; //3：领取完
	public final static int STATUS_OVER_RECEIVE = 4; //4：领取结束
	public final static int STATUS_LOCKED = 5; //5：后台主动发送锁定该批次
	
	
	
	/**
	 * 批次ID
	 */
    private Long batchId;

    /**
     * 优惠卷名称
     */
    private String batchName;

    /**
     * 平台类型，0 :全部  1:app，2:wap
     */
    private Integer platformType;
    
    /**
     * 该批次面额
     */
    private Long money;
    
    //已使用的金额
    private Long usedMoney;

    /**
     * 总金额
     */
    private Long totalMoney;
    
    //已使用的张数
    private Integer usedNumber;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 优惠卷状态 0 ：领取中 1：领取完 2：领取结束
     */
    private Integer status;
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

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Long getActivityId() {
		return activityId;
	}

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

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOriginStatus() {
		return originStatus;
	}

	public void setOriginStatus(Integer originStatus) {
		this.originStatus = originStatus;
	}
	
	
	
	
}