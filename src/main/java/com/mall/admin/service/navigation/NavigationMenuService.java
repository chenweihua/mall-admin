package com.mall.admin.service.navigation;

import java.util.List;

import com.mall.admin.vo.navigation.NavigationMenu;

public interface NavigationMenuService {

	/**
	 * 查询所有可用的菜单
	 * 
	 * @return
	 */
	public List<NavigationMenu> queryAllMenu();

	/**
	 * 查询导航下所有的菜单
	 * 
	 * @param navigationId
	 * @return
	 */
	public List<NavigationMenu> queryMenuList(long navigationId);

	/**
	 * 向导航下添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int insert(NavigationMenu menu);

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int update(NavigationMenu menu);

	/**
	 * 设置导航的显示状态
	 * 
	 * @param navMenuId
	 * @param isShow
	 * @return
	 */
	public int setShowStatus(long navMenuId, int isShow);
	
	
	/**
	 * 批量设置一个菜单项的子菜单项显示状态
	 * @param pid
	 * @param isShow
	 * @return
	 */
	public int setShowStatusByPid(long pid, int isShow);

	/**
	 * 根据id删除菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public int deleteMenuById(long menuId);

	/**
	 * 根据pid删除菜单
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteMenuByPid(long pid);

	/**
	 * 获得根菜单。如果menutype=1是获得活动的菜单，menuType=2是获得商品的菜单。如果为null则获得全部的根菜单。
	 * 
	 * @param menuType
	 * @return
	 */
	public List<NavigationMenu> getRootMenu(Long menuType);

	/**
	 * 根据父节点获得对应的子结点菜单
	 * 
	 * @param parentId
	 * @return
	 */
	public List<NavigationMenu> getChildMenuByPid(long parentId);
	
	/**
	 * 根据navMeuId查询菜单信息
	 * @param navMeuId
	 * @return
	 */
	public NavigationMenu queryNavigationMenu(Long navMeuId);
	
	
}
