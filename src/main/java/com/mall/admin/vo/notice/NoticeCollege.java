package com.mall.admin.vo.notice;

import java.util.Date;

public class NoticeCollege {
	
	/**
	 * 主键
	 */
	private Long noticeCollegeId;
	
	/**
	 * 通知ID
	 */
	private Long noticeId;
	
	
	/**
	 * 学校ID
	 */
	private Long collegeId;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;


	public Long getNoticeCollegeId() {
		return noticeCollegeId;
	}


	public void setNoticeCollegeId(Long noticeCollegeId) {
		this.noticeCollegeId = noticeCollegeId;
	}


	public Long getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
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
