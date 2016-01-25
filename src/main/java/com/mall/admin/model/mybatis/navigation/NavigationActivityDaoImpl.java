package com.mall.admin.model.mybatis.navigation;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.mall.admin.vo.navigation.NavigationActivity;
import com.mall.admin.model.dao.navigation.NavigationActivityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import org.springframework.stereotype.Repository;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;

@Repository
public class NavigationActivityDaoImpl extends BaseMallDaoImpl implements NavigationActivityDao {

	public int insert(NavigationActivity navigationActivity) {
		return this.getSqlSession().insert("NavigationActivity.insertNavigationActivity",navigationActivity);
	}
	
	public int update(NavigationActivity navigationActivity) {
		return this.getSqlSession().update("NavigationActivity.updateNavigationActivityByPrimaryKey",navigationActivity);
	}
	
	public List<NavigationActivity> query(NavigationActivity navigationActivity) {
		return this.getSqlSession().selectList("NavigationActivity.selectNavigationActivity", navigationActivity);
	}
	
	public int delete(Long navActivityId) {
		return this.getSqlSession().delete("NavigationActivity.deleteNavigationActivityByPrimaryKey", navActivityId);
	}
	
	public NavigationActivity queryById(Long navActivityId) {
		return this.getSqlSession().selectOne("NavigationActivity.selectNavigationActivityByPrimaryKey", navActivityId);
	}
	
	public List<NavigationActivity> getList(Map<String,Object> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<NavigationActivity> navigationActivityList = selectPaginationList("NavigationActivity.getPageNavigationActivityByPage", param, paginationInfo);
		return navigationActivityList;
	}

}