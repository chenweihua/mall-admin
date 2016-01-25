package com.mall.admin.service.notice.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.notice.NoticeDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.notice.NoticeService;
import com.mall.admin.util.DateRange;
import com.mall.admin.vo.notice.Notice;
import com.mall.admin.vo.notice.NoticeCollege;


@Service
public class NoticeServiceImpl implements NoticeService {
	
	private Logger logger = LogConstant.mallLog;

	@Autowired
	private NoticeDao noticeDao;
	
	public List<Notice> getNoticeList(Map<String,Object> param,PaginationInfo paginationInfo) {
		List<Notice> noticeList = noticeDao.getList(param, paginationInfo);
		return noticeList;
	}
	
	
	public int addNotice(Notice notice,String account) throws Exception {
		Notice noticeInDb = noticeDao.queryByName(notice.getNoticeName());
		if(noticeInDb != null) {
			throw new Exception("已有同名的通告，请检查！");
		}
		return noticeDao.insert(notice,account);
	}
	
	public int updateNotice(Notice notice,String account) throws Exception {
		
		Notice noticeInDb = noticeDao.queryByName(notice.getNoticeName());
		if(noticeInDb != null && !noticeInDb.getNoticeId().equals(notice.getNoticeId())) {
			throw new Exception("已有同名的通告，请检查！");
		}
		
		List<NoticeCollege> noticeColleges = getNoticeCollegeByNoticeId(notice.getNoticeId());
		if(!CollectionUtils.isEmpty(noticeColleges)) {
			for(NoticeCollege noticeCollege : noticeColleges) {
				exists(noticeCollege.getCollegeId(),notice);
			}
			
		}
		
		return noticeDao.update(notice,account);
	}
	
	public List<NoticeCollege> getNoticeCollegeByNoticeId(Long noticeId) {
		return noticeDao.getNoticeCollegeByNoticeId(noticeId);
	}
	
	
	
	/**
	 * 更新通知与学校的关联
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateNoticeColleges(Long noticeId, List<Long> collegeIds,String account) throws Exception {
		
		//删除旧的关联数据
		noticeDao.delNoticeCollegeByNoticeId(noticeId);
		
		
		//删除旧数据后添加新数据前验证
		Notice noticeCompare = noticeDao.queryByKey(noticeId);
		for(Long collegeId : collegeIds) {
			exists(collegeId, noticeCompare);
		}
		
		//添加新的关联数据
		List<NoticeCollege> noticeColleges = Lists.newArrayList();
		GroupSequence groupSequence = new GroupSequence();
		for(Long collegeId : collegeIds) {
			NoticeCollege noticeCollege = new NoticeCollege();
			noticeCollege.setCollegeId(collegeId);
			noticeCollege.setNoticeId(noticeId);
			noticeCollege.setNoticeCollegeId(groupSequence.nextValue());
			noticeColleges.add(noticeCollege);
		}
		noticeDao.insertNoticeColleges(noticeColleges,account);
	}
	
	
	/**
	 * 即将入库的noticeCompare是否在库中已有重合生效的(即不能共存的)，有则抛异常
	 */
	public void exists(Long collegeId,Notice noticeCompare) throws Exception {
		
		List<Notice> notices = noticeDao.getNoticeByCollege(collegeId);
		if(CollectionUtils.isEmpty(notices)) {
			return;
		}
		
		
		if(noticeCompare.getOpenType() == Notice.OPEN_TYPE_MANUAL) {
			
			//即将要入库的是手动关闭状态，不与任何已有冲突
			if(noticeCompare.getStatus() == Notice.STATUS_CLOSED) {  
				return;
			}
			
			//即将要入库的是手动打开状态，需判断库中已有的或是都是手动关闭状态，或是自动但结束时间都为过去时间
			Date nowDate = new Date();
			if(noticeCompare.getStatus() == Notice.STATUS_OPEN) {
				for(Notice notice : notices) {
					if(notice.getNoticeId().equals(noticeCompare.getNoticeId())) {  //如果修改的是同一个，则过滤
						continue;
					}
					if((notice.getOpenType() == Notice.OPEN_TYPE_MANUAL && notice.getStatus() == Notice.STATUS_OPEN  && notice.getPosition().equals(noticeCompare.getPosition()))
						|| (notice.getOpenType() == Notice.OPEN_TYPE_AUTO && notice.getPosition().equals(noticeCompare.getPosition()) && notice.getEndTime().after(nowDate)) ) {
						logger.info(noticeCompare + "与库中已有的" + notice + "不能同时存在！");
						throw new Exception("您要添加的与库中已有的\"" + notice.getNoticeName() + "\"冲突，请查看！");
					}
					
				}
				return;
			}
		}
		
		
		//即将要入库的是自动的，需判断库中已有的或是手动关闭状态的，或是自动的但时间段与此无交集的
		if(noticeCompare.getOpenType() == Notice.OPEN_TYPE_AUTO) {
			
			for(Notice notice : notices) {
				if(notice.getNoticeId().equals(noticeCompare.getNoticeId())) {  //如果修改的是同一个，则过滤
					continue;
				}
				if(notice.getOpenType() == Notice.OPEN_TYPE_MANUAL && notice.getStatus() == Notice.STATUS_OPEN && notice.getPosition().equals(noticeCompare.getPosition())) {
					logger.info(noticeCompare + "与库中已有的" + notice + "不能同时存在！");
					throw new Exception("您要添加的与库中已有的\"" + notice.getNoticeName() + "\"冲突，请查看！");
				}
				
				if(notice.getOpenType() == Notice.OPEN_TYPE_AUTO  && notice.getPosition().equals(noticeCompare.getPosition())
						&& (new DateRange(notice.getStartTime(),notice.getEndTime()).overlaps(new DateRange(noticeCompare.getStartTime(),noticeCompare.getEndTime())))) {
					logger.info(noticeCompare + "与库中已有的" + notice + "不能同时存在！");
					throw new Exception("您要添加的与库中已有的\"" + notice.getNoticeName() + "\"冲突，请查看！");
				}
				
			}
			return;
		}
		
	}
	
	
}
