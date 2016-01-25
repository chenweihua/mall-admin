package com.mall.admin.model.dao.navigation;

import java.util.List;

import com.mall.admin.vo.navigation.NavigationMenu;

public interface NavigationMenuDao {

	public List<NavigationMenu> queryAllMenu();

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int insert(NavigationMenu menu);

	/**
	 * 更新菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	public int update(NavigationMenu menu);

	/**
	 * 查询导航下所有的菜单
	 * 
	 * @param navigationId
	 * @return
	 */
	public List<NavigationMenu> queryMenuList(long navigationId);

	/**
	 * 设置菜单是否可见
	 * 
	 * @param menuId
	 * @param isShow
	 * @return
	 */
	public int setMenuShowStatus(long menuId, int isShow);
	
	
	/**
	 * 批量设置某个菜单项的所有子菜单是否可见
	 * @param pid
	 * @param isShow
	 * @return
	 */
	public int setShowStatusByPid(long pid, int isShow);

	/**
	 * 根据菜单的id删除菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public int deleteMenuById(long menuId);

	/**
	 * 删除菜单下所有的子菜单
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteMenuByPid(long pid);

	/**
	 * 获得所有根节点的菜单
	 * 
	 * @param menuType
	 * @return
	 */
	public List<NavigationMenu> getRootMenu(Long menuType);

	/**
	 * 获得所有子菜单
	 * 
	 * @param pid
	 * @return
	 */
	public List<NavigationMenu> getChildMenuByPid(long pid);
	
	/**
	 * 根据navMenuId查询菜单
	 * @param navMenuId
	 * @return
	 */
	public NavigationMenu queryNavigationMenuByNavMenuId(Long navMenuId);
}
