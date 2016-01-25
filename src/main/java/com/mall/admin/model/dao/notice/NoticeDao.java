package com.mall.admin.model.dao.notice;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.notice.Notice;
import com.mall.admin.vo.notice.NoticeCollege;
import com.mall.admin.vo.notice.NoticeOperationLog;

public interface NoticeDao {

	int insert(Notice notice,String account);
	
	int update(Notice notice,String account);
	
	List<Notice> query(Notice notice);
	
	Notice queryByKey(Long noticeId);
	
	Notice queryByName(String noticeName);
	
	int delete(Long noticeId);
	
	public List<Notice> getList(Map<String,Object> param,PaginationInfo paginationInfo) ;
	
	public List<NoticeCollege> getNoticeCollegeByNoticeId(Long noticeId);
	
	public int insertNoticeColleges(List<NoticeCollege> noticeColleges,String account);
	
	public int delNoticeCollegeByNoticeId(Long noticeId);
	
	public List<Notice> getNoticeByCollege(Long collegeId);
	
	public int insert(NoticeOperationLog noticeOperationLog);

}