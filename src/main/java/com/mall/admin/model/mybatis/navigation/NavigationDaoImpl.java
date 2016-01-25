package com.mall.admin.model.mybatis.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.navigation.NavigationDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.navigation.NavigationCollege;

@Repository
public class NavigationDaoImpl extends BaseMallDaoImpl implements NavigationDao {

	@Override
	public int insert(Navigation navigation) {
		return this.getSqlSession().insert("Navigation.insertNavigation", navigation);
	}

	@Override
	public int update(Navigation navigation) {
		return this.getSqlSession().update("Navigation.updateNavigationByPrimaryKey", navigation);
	}

	@Override
	public List<Navigation> query(Navigation navigation) {
		return this.getSqlSession().selectList("Navigation.selectNavigation", navigation);
	}

	@Override
	public int delete(Long navigationId) {
		return this.getSqlSession().delete("Navigation.deleteNavigationByPrimaryKey", navigationId);
	}

	@Override
	public Navigation queryById(Long navigationId) {
		return this.getSqlSession().selectOne("Navigation.selectNavigationByPrimaryKey", navigationId);
	}

	@Override
	public List<Navigation> getList() {
		return this.getSqlSession().selectList("Navigation.getNavigationList");
	}

	@Override
	public List<Navigation> getList(Map<String, Object> param, PaginationInfo paginationInfo) {
		PaginationList<Navigation> navigationList = selectPaginationList("Navigation.getPageNavigationByPage",
				param, paginationInfo);
		return navigationList;
	}

	@Override
	public List<NavigationCollege> getNavigationCollegeByNavigationId(Long navigationId) {
		return this.getSqlSession().selectList("Navigation.getNavigationCollegeByNavigationId", navigationId);
	}

	@Override
	public int delNavigationCollegeByNavigationId(Long navigationId) {
		return this.getSqlSession().delete("Navigation.delNavigationCollegeByNavigationId", navigationId);
	}

	@Override
	public int insertNavigationColleges(List<NavigationCollege> navigationColleges) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("navigationColleges", navigationColleges);
		int affectNum = this.getSqlSession().insert("Navigation.insertNavigationColleges", paraMap);
		return affectNum;
	}

	@Override
	public int setStatus(long navigationId, int status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("navigationId", navigationId);
		paramMap.put("status", status);
		int affectNum = this.getSqlSession().update("Navigation.setNavigationStatus", paramMap);
		return affectNum;
	}

}