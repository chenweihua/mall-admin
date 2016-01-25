package com.mall.admin.service.activitytemplate.impl;

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
import com.mall.admin.model.dao.activitytemplate.ActivityTemplateDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activitytemplate.ActivityTemplateService;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;


@Service
public class ActivityTemplateServiceImpl implements ActivityTemplateService {
	
	private Logger logger = LogConstant.mallLog;

	@Autowired
	private ActivityTemplateDao activityTemplateDao;
	
	public List<ActivityTemplate> getActivityTemplateList(Map<String,Object> param,PaginationInfo paginationInfo) {
		List<ActivityTemplate> activityTemplateList = activityTemplateDao.getList(param, paginationInfo);
		return activityTemplateList;
	}
	
	
	public int addActivityTemplate(ActivityTemplate activityTemplate) throws Exception {
		Date nowDate = new Date();
		activityTemplate.setCreateTime(nowDate);
		return activityTemplateDao.insert(activityTemplate);
	}
	
	public int updateActivityTemplate(ActivityTemplate activityTemplate) throws Exception {
		Date nowDate = new Date();
		activityTemplate.setUpdateTime(nowDate);
		return activityTemplateDao.update(activityTemplate);
	}
	
	public ActivityTemplate getActivityTemplateById(Long activityTemplateId) {
		return activityTemplateDao.queryById(activityTemplateId);
	}
	
	public List<ActivityTemplate> getAllActivityTemplate() {
		return activityTemplateDao.getAllActivityTemplate();
	}
	
}
