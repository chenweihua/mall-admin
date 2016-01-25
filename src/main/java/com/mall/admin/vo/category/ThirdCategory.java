package com.mall.admin.vo.category;

import java.io.Serializable;
import java.sql.Timestamp;

public class ThirdCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3685319187579568851L;

	private long propertyCategoryId;
	
	private String propertyCategoryName;
	
	private int level;
	
	private int pid;
	
	private int weight;
	
	private int isDel;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int operator;
	
	private String creator;
	
	private String modifyUser;

	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}

	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}

	public String getPropertyCategoryName() {
		return propertyCategoryName;
	}

	public void setPropertyCategoryName(String propertyCategoryName) {
		this.propertyCategoryName = propertyCategoryName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
}
