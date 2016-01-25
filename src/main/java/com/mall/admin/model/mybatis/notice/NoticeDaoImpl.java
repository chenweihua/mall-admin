package com.mall.admin.model.mybatis.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.notice.NoticeDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.util.CollectionUtil;
import com.mall.admin.vo.notice.Notice;
import com.mall.admin.vo.notice.NoticeCollege;
import com.mall.admin.vo.notice.NoticeOperationLog;

@Repository
public class NoticeDaoImpl extends BaseMallDaoImpl implements NoticeDao {

	public int insert(Notice notice,String account) {
		int affectNum = this.getSqlSession().insert("Notice.insert",notice);
		
		insertNoticeLog(notice,account);
		
		return affectNum;
	}
	
	public int update(Notice notice,String account) {
		int affectNum = this.getSqlSession().update("Notice.updateByPrimaryKey",notice);
		
		insertNoticeLog(notice,account);
		
		return affectNum;
		
	}
	
	public List<Notice> query(Notice notice) {
		return this.getSqlSession().selectList("Notice.select", notice);
	}
	
	public Notice queryByKey(Long noticeId) {
		Notice notice = new Notice();
		notice.setNoticeId(noticeId);
		List<Notice> notices = query(notice);
		if(notices != null && notices.size() > 0) {
			return notices.get(0);
		}
		return null;
	}
	
	public Notice queryByName(String noticeName) {
		Notice notice = new Notice();
		notice.setNoticeName(noticeName);
		List<Notice> notices = query(notice);
		if(notices != null && notices.size() > 0) {
			return notices.get(0);
		}
		return null;
	}
	
	public int delete(Long noticeId) {
		return this.getSqlSession().delete("Notice.deleteByPrimaryKey", noticeId);
	}
	
	public List<Notice> getList(Map<String,Object> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Notice> noticeList = selectPaginationList("Notice.getPageNoticeByPage", param, paginationInfo);
		return noticeList;
	}
	
	public List<NoticeCollege> getNoticeCollegeByNoticeId(Long noticeId) {
		return this.getSqlSession().selectList("Notice.getNoticeCollegeByNoticeId", noticeId);
	}
	
	public int insertNoticeColleges(List<NoticeCollege> noticeColleges,String account) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("noticeColleges", noticeColleges);
		int affectNum = this.getSqlSession().insert("Notice.insertNoticeColleges",paraMap);
		
		//List<Long> collegeIds = Lists.transform(noticeColleges, (s) -> s.getCollegeId());
		List<Long> collegeIds = new ArrayList<Long>();
		for(NoticeCollege noticeCollege : noticeColleges) {
			collegeIds.add(noticeCollege.getCollegeId());
		}
		insertNoticeCollegeLog(noticeColleges.get(0).getNoticeId(),collegeIds, account);
		
		return affectNum;
	}
	
	public int delNoticeCollegeByNoticeId(Long noticeId) {
		return this.getSqlSession().delete("Notice.delNoticeCollegeByNoticeId", noticeId);
	}
	
	public List<Notice> getNoticeByCollege(Long collegeId) {
		return this.getSqlSession().selectList("Notice.getNoticeByCollege", collegeId);
	}
	
	public int insert(NoticeOperationLog noticeOperationLog) {
		return this.getSqlSession().insert("NoticeOperationLog.insert",noticeOperationLog);
	}
	
	private void insertNoticeLog(Notice notice,String account) {
		GroupSequence groupSequence = new GroupSequence();
		NoticeOperationLog log = new NoticeOperationLog();
		try {
			log.setNoticeOperationLogId(groupSequence.nextValue());
			log.setNoticeId(notice.getNoticeId());
			log.setNoticeName(notice.getNoticeName());
			log.setContent(notice.getContent());
			log.setPlatform(notice.getPlatform());
			log.setPosition(notice.getPosition());
			log.setHtmlUrl(notice.getHtmlUrl());
			log.setOpenType(notice.getOpenType());
			log.setStartTime(notice.getStartTime());
			log.setEndTime(notice.getEndTime());
			log.setStatus(notice.getStatus());
			log.setOperationTime(new Date());
			log.setOperator(account);
			this.getSqlSession().insert("Notice.insertNoticeOperationLog",log);
		} catch(Exception ex) {
			LogConstant.mallLog.error("插入NoticeOperationLog异常：" + log,ex);
		}
	}
	
	private void insertNoticeCollegeLog(Long noticeId,List<Long> collegeIds,String account) {
		GroupSequence groupSequence = new GroupSequence();
		NoticeOperationLog log = new NoticeOperationLog();
		try {
			log.setNoticeOperationLogId(groupSequence.nextValue());
			log.setNoticeId(noticeId);
			log.setCollegeId(CollectionUtil.join(collegeIds, ","));
			log.setOperationTime(new Date());
			log.setOperator(account);
			this.getSqlSession().insert("Notice.insertNoticeOperationLog",log);
		} catch(Exception ex) {
			LogConstant.mallLog.error("插入NoticeOperationLog异常：" + log,ex);
		}
	}
	
	
	

}