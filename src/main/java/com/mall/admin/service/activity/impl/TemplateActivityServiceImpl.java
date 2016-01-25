package com.mall.admin.service.activity.impl;

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
import com.mall.admin.model.dao.activity.TemplateActivityDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.TemplateActivityService;
import com.mall.admin.vo.activity.TemplateActivity;


@Service
public class TemplateActivityServiceImpl implements TemplateActivityService {
	
	private Logger logger = LogConstant.mallLog;

	@Autowired
	private TemplateActivityDao templateActivityDao;
	
	public List<TemplateActivity> getTemplateActivityList(Map<String,Object> param,PaginationInfo paginationInfo) {
		List<TemplateActivity> templateActivityList = templateActivityDao.getList(param, paginationInfo);
		return templateActivityList;
	}
	
	
	public int addTemplateActivity(TemplateActivity templateActivity) throws Exception {
		return templateActivityDao.insert(templateActivity);
	}
	
	public int updateTemplateActivity(TemplateActivity templateActivity) throws Exception {
		return templateActivityDao.update(templateActivity);
	}
	
	public TemplateActivity getTemplateActivityById(Long activityId) {
		return templateActivityDao.queryById(activityId);
	}
	
}
