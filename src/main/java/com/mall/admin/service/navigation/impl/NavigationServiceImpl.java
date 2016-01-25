package com.mall.admin.service.navigation.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.navigation.NavigationDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.navigation.NavigationService;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.navigation.NavigationCollege;

@Service
public class NavigationServiceImpl implements NavigationService {

	private Logger logger = LogConstant.mallLog;

	@Autowired
	private NavigationDao navigationDao;
	
	@Override
	public List<Navigation> getNavigationList() {
		return navigationDao.getList();
	}

	@Override
	public List<Navigation> getNavigationList(Map<String, Object> param, PaginationInfo paginationInfo) {
		List<Navigation> navigationList = navigationDao.getList(param, paginationInfo);
		return navigationList;
	}

	@Override
	public int addNavigation(Navigation navigation) throws Exception {
		return navigationDao.insert(navigation);
	}

	@Override
	public int updateNavigation(Navigation navigation) throws Exception {
		return navigationDao.update(navigation);
	}

	@Override
	public Navigation getNavigationById(Long navigationId) {
		return navigationDao.queryById(navigationId);
	}

	@Override
	public List<NavigationCollege> getNavigationCollegeByNavigationId(Long navigationId) {
		return navigationDao.getNavigationCollegeByNavigationId(navigationId);
	}

	/**
	 * 更新通知与学校的关联
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateNavigationColleges(Long navigationId, List<Long> collegeIds) throws Exception {

		// 删除旧的关联数据
		navigationDao.delNavigationCollegeByNavigationId(navigationId);

		// 添加新的关联数据
		List<NavigationCollege> navigationColleges = Lists.newArrayList();
		GroupSequence groupSequence = new GroupSequence();
		for (Long collegeId : collegeIds) {
			NavigationCollege navigationCollege = new NavigationCollege();
			navigationCollege.setCollegeId(collegeId);
			navigationCollege.setNavigationId(navigationId);
			navigationCollege.setNavigationCollegeId(groupSequence.nextValue());
			navigationCollege.setCreateTime(new Date());
			navigationColleges.add(navigationCollege);
		}
		navigationDao.insertNavigationColleges(navigationColleges);
	}

	@Override
	public int setStatus(long navigationId, int status) {
		// TODO Auto-generated method stub
		return navigationDao.setStatus(navigationId, status);
	}

}
