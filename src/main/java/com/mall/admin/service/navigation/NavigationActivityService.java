package com.mall.admin.service.navigation;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.navigation.NavigationActivity;

public interface NavigationActivityService {
	
	
	public List<NavigationActivity> getNavigationActivityList(Map<String,Object> param,PaginationInfo paginationInfo);
	
	public int addNavigationActivity(NavigationActivity navigationActivity) throws Exception;
	
	public int updateNavigationActivity(NavigationActivity navigationActivity) throws Exception ;

	public NavigationActivity getNavigationActivityById(Long navActivityId);
}
