package com.mall.admin.service.notice;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.notice.Notice;
import com.mall.admin.vo.notice.NoticeCollege;

public interface NoticeService {
	
	
	public List<Notice> getNoticeList(Map<String,Object> param,PaginationInfo paginationInfo);
	
	public int addNotice(Notice notice,String account) throws Exception;
	
	public int updateNotice(Notice notice,String account) throws Exception ;

	public void updateNoticeColleges(Long noticeId, List<Long> collegeIds,String account) throws Exception;
	
	public List<NoticeCollege> getNoticeCollegeByNoticeId(Long noticeId);
	
	public void exists(Long collegeId,Notice noticeCompare) throws Exception;
	
}
