package com.mall.admin.service.navigation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.NavigationMenuConstant;
import com.mall.admin.model.dao.navigation.NavigationMenuDao;
import com.mall.admin.service.navigation.NavigationMenuService;
import com.mall.admin.vo.navigation.NavigationMenu;

@Service
public class NavigationMenuServiceImpl implements NavigationMenuService {

	@Autowired
	NavigationMenuDao navigationMenuDao;
	@Autowired
	NavigationMenuConstant navMenuConstant;

	@Override
	public List<NavigationMenu> queryAllMenu() {
		// TODO Auto-generated method stub
		return navigationMenuDao.queryAllMenu();
	}

	@Override
	public List<NavigationMenu> queryMenuList(long navigationId) {
		// TODO Auto-generated method stub
		List<NavigationMenu> oldNavMenuList = navigationMenuDao.queryMenuList(navigationId);
		List<NavigationMenu> newNavMenuList = new ArrayList<NavigationMenu>();
		for (NavigationMenu navigationMenu : oldNavMenuList) {
			if (navigationMenu.getPid() == 0) {
				newNavMenuList.add(navigationMenu);
				for (NavigationMenu childMenu : oldNavMenuList) {
					if (childMenu.getPid() == navigationMenu.getNavMenuId()) {
						newNavMenuList.add(childMenu);
					}
				}
			}
		}
		return newNavMenuList;
	}

	@Override
	public int insert(NavigationMenu menu) {
		// TODO Auto-generated method stub
		int i = navigationMenuDao.insert(menu);
		navMenuConstant.refresh();
		return i;
	}

	@Override
	public int update(NavigationMenu menu) {
		// TODO Auto-generated method stub
		int i = navigationMenuDao.update(menu);
		navMenuConstant.refresh();
		return i;
	}

	@Override
	public int setShowStatus(long navMenuId, int isShow) {
		// TODO Auto-generated method stub
		int i = navigationMenuDao.setMenuShowStatus(navMenuId, isShow);
		navMenuConstant.refresh();
		return i;
	}

	/**
	 * 批量设置一个菜单项的子菜单项显示状态
	 * 
	 * @param pid
	 * @param isShow
	 * @return
	 */
	@Override
	public int setShowStatusByPid(long pid, int isShow) {
		int i = navigationMenuDao.setShowStatusByPid(pid, isShow);
		navMenuConstant.refresh();
		return i;
	}

	@Override
	public int deleteMenuById(long menuId) {
		// TODO Auto-generated method stub
		int i = navigationMenuDao.deleteMenuById(menuId);
		navMenuConstant.refresh();
		return i;
	}

	@Override
	public int deleteMenuByPid(long pid) {
		// TODO Auto-generated method stub
		int i = navigationMenuDao.deleteMenuByPid(pid);
		navMenuConstant.refresh();
		return i;
	}

	@Override
	public List<NavigationMenu> getRootMenu(Long menuType) {
		// TODO Auto-generated method stub
		return navigationMenuDao.getRootMenu(menuType);
	}

	@Override
	public List<NavigationMenu> getChildMenuByPid(long parentId) {
		// TODO Auto-generated method stub
		return navigationMenuDao.getChildMenuByPid(parentId);
	}

	/**
	 * 根据navMeuId查询菜单信息
	 * 
	 * @param navMeuId
	 * @return
	 */
	@Override
	public NavigationMenu queryNavigationMenu(Long navMenuId) {
		return navigationMenuDao.queryNavigationMenuByNavMenuId(navMenuId);
	}

}
