package com.mall.admin.vo.activity;

import java.util.Date;

public class TemplateActivity {
	
	/**
	*  活动ID
	**/
	private Long activityId;
	
	/**
	*  模板ID 关联表tb_activity_template
	**/
	private Long activityTemplateId;
	
	/**
	*  头部图片url
	**/
	private String headImageUrl;
	
	/**
	*  主题url
	**/
	private String imageUrl;
	
	/**
	 * 每栏目商品数
	 */
	private Integer goodsNum;
	
	/**
	*  活动规则
	**/
	private String activityRule;
	
	
	/**
	 * 所有栏目名称，以；分隔
	 */
	private String categoryNames;
	
	/**
	 * 	商品IDs,以；分隔不同category，以，分隔同一category内
	 */
	private String goodsIds;
	
	
	
	public TemplateActivity() {
	
	}
	
	
	public Long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	
	public Long getActivityTemplateId() {
		return activityTemplateId;
	}
	
	public void setActivityTemplateId(Long activityTemplateId) {
		this.activityTemplateId = activityTemplateId;
	}
	
	
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	public String getActivityRule() {
		return activityRule;
	}
	
	public void setActivityRule(String activityRule) {
		this.activityRule = activityRule;
	}




	public String getCategoryNames() {
		return categoryNames;
	}


	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}


	public String getGoodsIds() {
		return goodsIds;
	}


	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}


	public Integer getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}


	
	
	
	
}