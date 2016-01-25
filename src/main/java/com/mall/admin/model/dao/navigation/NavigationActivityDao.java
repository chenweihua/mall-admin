package com.mall.admin.model.dao.navigation;

import java.util.List;
import java.util.Map;
import com.mall.admin.vo.navigation.NavigationActivity;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;


public interface NavigationActivityDao {

	int insert(NavigationActivity navigationActivity);
	
	int update(NavigationActivity navigationActivity);
	
	List<NavigationActivity> query(NavigationActivity navigationActivity);
	
	int delete(Long navActivityId);
	
	NavigationActivity queryById(Long navActivityId);
	
	public List<NavigationActivity> getList(Map<String,Object> param,PaginationInfo paginationInfo);

}