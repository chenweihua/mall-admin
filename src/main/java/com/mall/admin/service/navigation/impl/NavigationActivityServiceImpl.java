package com.mall.admin.service.navigation.impl;

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
import com.mall.admin.model.dao.navigation.NavigationActivityDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.navigation.NavigationActivityService;
import com.mall.admin.vo.navigation.NavigationActivity;


@Service
public class NavigationActivityServiceImpl implements NavigationActivityService {
	
	private Logger logger = LogConstant.mallLog;

	@Autowired
	private NavigationActivityDao navigationActivityDao;
	
	public List<NavigationActivity> getNavigationActivityList(Map<String,Object> param,PaginationInfo paginationInfo) {
		List<NavigationActivity> navigationActivityList = navigationActivityDao.getList(param, paginationInfo);
		return navigationActivityList;
	}
	
	
	public int addNavigationActivity(NavigationActivity navigationActivity) throws Exception {
		return navigationActivityDao.insert(navigationActivity);
	}
	
	public int updateNavigationActivity(NavigationActivity navigationActivity) throws Exception {
		return navigationActivityDao.update(navigationActivity);
	}
	
	public NavigationActivity getNavigationActivityById(Long navActivityId) {
		return navigationActivityDao.queryById(navActivityId);
	}
	
}
