package com.mall.admin.vo.notice;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Notice {
	
	public static final int OPEN_TYPE_MANUAL = 1; //手动控制
	public static final int OPEN_TYPE_AUTO = 2; //自动控制
	
	
	public static final int STATUS_OPEN = 1;  //开始状态
	public static final int STATUS_CLOSED = 0; //关闭状态
	
	
	/**
	*  主键ID
	**/
	private Long noticeId;
	
	/**
	*  名称
	**/
	private String noticeName;
	
	/**
	*  内容
	**/
	private String content;
	
	/**
	*  平台，0,全部 App：1，H5:2
	**/
	private String platform;
	
	/**
	*  展示页面 1:首页；2：购物车页
	**/
	private Integer position;
	
	/**
	*  
	**/
	private String htmlUrl;
	
	/**
	*  控制类型 1：手动 2：自动
	**/
	private Integer openType;
	
	/**
	*  开始时间
	**/
	private Date startTime;
	
	/**
	*  结束时间
	**/
	private Date endTime;
	
	/**
	*  状态 1：开 0：关，该字段仅在control为手动时有效
	**/
	private Integer status;
	
	/**
	*  创建记录时间
	**/
	private Date createTime;
	
	/**
	*  更新记录时间
	**/
	private Date updateTime;
	
	
	
	public Notice() {
	
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}