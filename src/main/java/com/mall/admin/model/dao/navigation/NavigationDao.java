package com.mall.admin.model.dao.navigation;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.navigation.NavigationCollege;

public interface NavigationDao {

	int insert(Navigation navigation);

	int update(Navigation navigation);

	List<Navigation> query(Navigation navigation);

	int delete(Long navigationId);

	Navigation queryById(Long navigationId);

	public List<Navigation> getList(Map<String, Object> param, PaginationInfo paginationInfo);
	
	public List<Navigation> getList();

	public List<NavigationCollege> getNavigationCollegeByNavigationId(Long navigationId);

	public int delNavigationCollegeByNavigationId(Long navigationId);

	public int insertNavigationColleges(List<NavigationCollege> navigationColleges);

	public int setStatus(long navigationId, int status);

}