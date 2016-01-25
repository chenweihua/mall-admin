package com.mall.admin.vo.navigation;

import java.util.Date;

public class NavigationCollege {
	
	/**
	 * 主键
	 */
	private Long navigationCollegeId;
	
	/**
	 * 导航ID
	 */
	private Long navigationId;
	
	
	/**
	 * 学校ID
	 */
	private Long collegeId;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;


	public Long getNavigationCollegeId() {
		return navigationCollegeId;
	}


	public void setNavigationCollegeId(Long navigationCollegeId) {
		this.navigationCollegeId = navigationCollegeId;
	}


	public Long getNavigationId() {
		return navigationId;
	}


	public void setNavigationId(Long navigationId) {
		this.navigationId = navigationId;
	}


	public Long getCollegeId() {
		return collegeId;
	}


	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	

}
