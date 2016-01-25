package com.mall.admin.vo.navigation;

import java.util.Date;

public class Navigation {

	/**
	 * 主键ID
	 **/
	private Long navigationId;

	/**
	 * 导航名称
	 **/
	private String navigationName;

	/**
	 * 导航描述
	 **/
	private String navigationDesc;

	/**
	 * 链接地址
	 **/
	private String navigationUrl;

	/**
	 * 内部链接
	 **/
	private String insideUrl;

	/**
	 * 权重
	 **/
	private Integer weight;

	/**
	 * 状态
	 **/
	private Integer status;

	/**
	 * 创建时间
	 **/
	private Date createTime;

	/**
	 * 更新时间
	 **/
	private Date updateTime;
	/**
	 * 导航类型 1：超级返； 2：淘精品
	 */
	private int navigationType;
	/** 创建人 */
	private long creator;
	/** 操作人 */
	private long operator;

	private int isShow;
	private int isDel;

	public Navigation()
	{

	}

	public Long getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(Long navigationId) {
		this.navigationId = navigationId;
	}

	public String getNavigationName() {
		return navigationName;
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationDesc() {
		return navigationDesc;
	}

	public void setNavigationDesc(String navigationDesc) {
		this.navigationDesc = navigationDesc;
	}

	public String getNavigationUrl() {
		return navigationUrl;
	}

	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}

	public String getInsideUrl() {
		return insideUrl;
	}

	public void setInsideUrl(String insideUrl) {
		this.insideUrl = insideUrl;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
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

	public int getNavigationType() {
		return navigationType;
	}

	public void setNavigationType(int navigationType) {
		this.navigationType = navigationType;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

}