package com.mall.admin.vo.notice;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NoticeOperationLog {
	
	/**
	*  
	**/
	private Long noticeOperationLogId;
	
	/**
	*  
	**/
	private Long noticeId;
	
	/**
	*  
	**/
	private String noticeName;
	
	/**
	*  
	**/
	private String content;
	
	/**
	*  
	**/
	private String platform;
	
	/**
	*  
	**/
	private Integer position;
	
	/**
	*  
	**/
	private String htmlUrl;
	
	/**
	*  
	**/
	private Integer openType;
	
	/**
	*  
	**/
	private Date startTime;
	
	/**
	*  
	**/
	private Date endTime;
	
	/**
	*  
	**/
	private Integer status;
	
	/**
	*  
	**/
	private Date operationTime;
	
	private String operator;
	
	/**
	*  
	**/
	private String collegeId;
	
	
	
	public NoticeOperationLog() {
	
	}
	
	
	public Long getNoticeOperationLogId() {
		return noticeOperationLogId;
	}
	
	public void setNoticeOperationLogId(Long noticeOperationLogId) {
		this.noticeOperationLogId = noticeOperationLogId;
	}
	
	
	public Long getNoticeId() {
		return noticeId;
	}
	
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	
	
	public String getNoticeName() {
		return noticeName;
	}
	
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getPlatform() {
		return platform;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
	public String getHtmlUrl() {
		return htmlUrl;
	}
	
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	
	
	public Integer getOpenType() {
		return openType;
	}
	
	public void setOpenType(Integer openType) {
		this.openType = openType;
	}
	
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public Date getOperationTime() {
		return operationTime;
	}
	
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	
	public String getCollegeId() {
		return collegeId;
	}
	
	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}
	
	
	
	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}