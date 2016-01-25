package com.mall.admin.vo.category;

import java.sql.Timestamp;

/**
 * 类目表
 * 
 * @author nanjing
 *
 */
public class Category {
	private long categoryId;

	private String categoryName;

	private String description;
	
	private int labelType;

	private String iconOn;

	private String iconOff;
	
	private String appIcon;

	private long weight;

	private int showIndex;

	private int indexWeight;

	private int isDel;

	private Timestamp updateTime;

	private Timestamp createTime;

	private long operator;

	public int getLabelType() {
		return labelType;
	}

	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconOn() {
		return iconOn;
	}

	public void setIconOn(String iconOn) {
		this.iconOn = iconOn;
	}

	public String getIconOff() {
		return iconOff;
	}

	public void setIconOff(String iconOff) {
		this.iconOff = iconOff;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public int getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}

	public int getIndexWeight() {
		return indexWeight;
	}

	public void setIndexWeight(int indexWeight) {
		this.indexWeight = indexWeight;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", description=" + description + ", iconOn="
				+ iconOn + ", iconOff=" + iconOff + ", weight=" + weight
				+ ", showIndex=" + showIndex + ", indexWeight=" + indexWeight
				+ ", isDel=" + isDel + ", updateTime=" + updateTime
				+ ", createTime=" + createTime + ", operator=" + operator + "]";
	}
}
