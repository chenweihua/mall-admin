package com.mall.admin.vo.activitytemplate;

import java.util.Date;

public class ActivityTemplate {
	
	/**
	*  活动模板ID
	**/
	private Long activityTemplateId;
	
	/**
	*  模板名称
	**/
	private String templateName;
	
	/**
	*  模板类型
	**/
	private Integer templateType;
	
	/**
	*  背景颜色
	**/
	private String colorValue;
	
	/**
	*  头图片，限定一个
	**/
	private String headerImageUrl;
	
	/**
	*  描述图片，多个,用逗号隔开
	**/
	private String imageUrl;
	
	/**
	*  创建时间
	**/
	private Date createTime;
	
	/**
	*  最近一次更新时间
	**/
	private Date updateTime;
	
	
	
	public ActivityTemplate() {
	
	}
	
	
	public Long getActivityTemplateId() {
		return activityTemplateId;
	}
	
	public void setActivityTemplateId(Long activityTemplateId) {
		this.activityTemplateId = activityTemplateId;
	}
	
	
	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	
	public Integer getTemplateType() {
		return templateType;
	}
	
	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}
	
	
	public String getColorValue() {
		return colorValue;
	}
	
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	
	
	public String getHeaderImageUrl() {
		return headerImageUrl;
	}
	
	public void setHeaderImageUrl(String headerImageUrl) {
		this.headerImageUrl = headerImageUrl;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	
}