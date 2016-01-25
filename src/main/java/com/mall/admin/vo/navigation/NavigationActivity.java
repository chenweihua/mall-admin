package com.mall.admin.vo.navigation;

import java.util.Date;

public class NavigationActivity {
	
	/**
	*  淘精品下活动id
	**/
	private Long navActivityId;
	
	/**
	*  淘精品下菜单id
	**/
	private Integer navMenuId;
	
	/**
	*  活动类型
	**/
	private Integer activityType;
	
	/**
	*  活动名称
	**/
	private String activityName;
	
	/**
	*  活动关联地址
	**/
	private String activityUrl;
	
	/**
	*  权重
	**/
	private Integer weight;
	
	/**
	*  备注
	**/
	private String remark;
	
	/**
	*  图片url
	**/
	private String imageUrl;
	
	/**
	*  控制方式
	**/
	private Integer openType;
	
	/**
	*  状态
	**/
	private Integer status;
	
	/**
	*  创建时间
	**/
	private Date createTime;
	
	/**
	*  更新时间
	**/
	private Date updateTime;
	
	/**
	*  创建人
	**/
	private Long creator;
	
	/**
	*  操作人
	**/
	private Long operator;
	
	/**
	*  是否删除，0：未删除；1：已删除
	**/
	private Integer isDel;
	
	/**
	*  活动开始时间
	**/
	private Date beginTime;
	
	/**
	*  活动结束时间
	**/
	private Date endTime;
	
	
	
	public NavigationActivity() {
	
	}
	
	
	public Long getNavActivityId() {
		return navActivityId;
	}
	
	public void setNavActivityId(Long navActivityId) {
		this.navActivityId = navActivityId;
	}
	
	
	public Integer getNavMenuId() {
		return navMenuId;
	}
	
	public void setNavMenuId(Integer navMenuId) {
		this.navMenuId = navMenuId;
	}
	
	
	public Integer getActivityType() {
		return activityType;
	}
	
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	
	
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
	public String getActivityUrl() {
		return activityUrl;
	}
	
	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}
	
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	public Integer getOpenType() {
		return openType;
	}
	
	public void setOpenType(Integer openType) {
		this.openType = openType;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
	
	
	public Long getCreator() {
		return creator;
	}
	
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	
	public Long getOperator() {
		return operator;
	}
	
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	
	
	public Integer getIsDel() {
		return isDel;
	}
	
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	
	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}