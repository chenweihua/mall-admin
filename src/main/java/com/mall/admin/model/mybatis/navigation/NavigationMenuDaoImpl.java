package com.mall.admin.model.mybatis.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.navigation.NavigationMenuDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.navigation.NavigationMenu;

@Repository
public class NavigationMenuDaoImpl extends BaseMallDaoImpl implements NavigationMenuDao {

	@Override
	public List<NavigationMenu> queryAllMenu() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("NavigationMenu.selectAllNavMenuList");
	}

	@Override
	public int insert(NavigationMenu menu) {
		return this.getSqlSession().insert("NavigationMenu.insertNavigationMenu", menu);
	}

	@Override
	public int update(NavigationMenu menu) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationMenu.updateNavigationMenu", menu);
	}

	@Override
	public List<NavigationMenu> queryMenuList(long navigationId) {
		return this.getSqlSession().selectList("NavigationMenu.selectNavMenuListByNavId", navigationId);
	}

	@Override
	public int setMenuShowStatus(long menuId, int isShow) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("navMenuId", menuId);
		paramMap.put("isShow", isShow);

		return this.getSqlSession().update("NavigationMenu.setNavigationShowStatus", paramMap);
	}
	
	/**
	 * 批量设置某个菜单项的所有子菜单是否可见
	 * @param pid
	 * @param isShow
	 * @return
	 */
	public int setShowStatusByPid(long pid, int isShow) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pid", pid);
		paramMap.put("isShow", isShow);

		return this.getSqlSession().update("NavigationMenu.setNavigationMenuShowStatusByPid", paramMap);
	}

	@Override
	public int deleteMenuById(long menuId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationMenu.delNavigationMenuById", menuId);
	}

	@Override
	public int deleteMenuByPid(long pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationMenu.delNavigationMenuByPId", pid);
	}

	@Override
	public List<NavigationMenu> getRootMenu(Long menuType) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("NavigationMenu.selectRootMenu", menuType);
	}

	@Override
	public List<NavigationMenu> getChildMenuByPid(long pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("NavigationMenu.selectChildMenuByPid", pid);
	}
	
	/**
	 * 根据navMenuId查询菜单
	 * @param navMenuId
	 * @return
	 */
	public NavigationMenu queryNavigationMenuByNavMenuId(Long navMenuId) {
		return this.getSqlSession().selectOne("NavigationMenu.queryNavigationMenuByNavMenuId", navMenuId);
	}

}
