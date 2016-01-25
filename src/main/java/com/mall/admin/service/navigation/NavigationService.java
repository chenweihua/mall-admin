package com.mall.admin.service.navigation;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.navigation.NavigationCollege;

public interface NavigationService {

	public List<Navigation> getNavigationList();
	
	public List<Navigation> getNavigationList(Map<String, Object> param, PaginationInfo paginationInfo);

	public int addNavigation(Navigation navigation) throws Exception;

	public int updateNavigation(Navigation navigation) throws Exception;

	public Navigation getNavigationById(Long navigationId);

	public List<NavigationCollege> getNavigationCollegeByNavigationId(Long navigationId);

	public void updateNavigationColleges(Long navigationId, List<Long> collegeIds) throws Exception;

	public int setStatus(long navigationId, int status);
}
