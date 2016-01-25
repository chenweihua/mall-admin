package com.mall.admin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.navigation.NavigationMenuService;
import com.mall.admin.vo.navigation.NavigationMenu;

public class NavigationMenuConstant {

	private static Map<Long, NavigationMenu> navMenuMap = new HashMap<Long, NavigationMenu>();

	@Autowired
	NavigationMenuService navMenuService;
	Lock lock = new ReentrantLock();// 锁

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.info("init NavigationMenu");
		refresh();
	}

	/**
	 * 由于后台是多个服务器，所以在每次执行该方法时，需要执行一下该方法，刷新一下缓存。保证数据的正确性
	 */
	public void refresh() {
		try {
			lock.lock();
			Map<Long, NavigationMenu> navMenuMap_temp = new HashMap<Long, NavigationMenu>();
			List<NavigationMenu> navMenuList = navMenuService.queryAllMenu();
			for (NavigationMenu menu : navMenuList) {
				navMenuMap_temp.put(menu.getNavMenuId(), menu);
			}
			navMenuMap = navMenuMap_temp;

		} catch (Exception e) {
			LogConstant.mallLog.error("刷新NavigationMenuConstant缓存数据时发生异常", e);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获得根节点
	 * 
	 * @param type
	 * @return
	 */
	public List<NavigationMenu> getRootMenu(long type) {
		refresh();
		List<NavigationMenu> navMenuList = new ArrayList<NavigationMenu>();
		try {
			lock.lock();
			Set<Entry<Long, NavigationMenu>> set = navMenuMap.entrySet();
			for (Entry<Long, NavigationMenu> entry : set) {
				NavigationMenu menu = entry.getValue();
				if (menu.getPid() == 0 && menu.getMenuType() == type) {
					navMenuList.add(menu);
				}
			}
		} catch (Exception e) {
			LogConstant.mallLog.error("根据type查询NavigationMenu发生异常", e);
		} finally {
			lock.unlock();
		}
		return navMenuList;
	}

	/**
	 * 获得子结点
	 * 
	 * @param type
	 * @return
	 */
	public List<NavigationMenu> getChildMenuByPid(long pid) {
		refresh();
		List<NavigationMenu> navMenuList = new ArrayList<NavigationMenu>();
		try {
			lock.lock();
			Set<Entry<Long, NavigationMenu>> set = navMenuMap.entrySet();
			for (Entry<Long, NavigationMenu> entry : set) {
				NavigationMenu menu = entry.getValue();
				if (menu.getPid() == pid) {
					navMenuList.add(menu);
				}
			}
		} catch (Exception e) {
			LogConstant.mallLog.error("根据pid查询NavigationMenu发生异常", e);
		} finally {
			lock.unlock();
		}
		return navMenuList;
	}

	/**
	 * 根据id获取菜单对象
	 * 
	 * @param navMenuId
	 * @return
	 */
	public NavigationMenu getNavMenuById(Long navMenuId) {
		refresh();
		try {
			lock.lock();
			NavigationMenu menu = navMenuMap.get(navMenuId);
			return menu;
		} catch (Exception e) {
			LogConstant.mallLog.error("根据navMenuId查询NavigationMenu发生异常", e);
		} finally {
			lock.unlock();
		}
		return null;
	}

	/**
	 * 根据类型获得所有的菜单
	 * 
	 * @param menuType
	 *                1：活动 2：商品
	 * @return
	 */
	public List<NavigationMenu> getNavMenuByMenuType(int menuType) {
		refresh();
		List<NavigationMenu> navMenuList = new ArrayList<NavigationMenu>();
		try {
			lock.lock();
			Set<Entry<Long, NavigationMenu>> set = navMenuMap.entrySet();
			for (Entry<Long, NavigationMenu> entry : set) {
				NavigationMenu menu = entry.getValue();
				if (menu.getMenuType() == menuType) {
					navMenuList.add(menu);
				}
			}
		} catch (Exception e) {
			LogConstant.mallLog.error("根据menuType查询NavigationMenu发生异常", e);
		} finally {
			lock.unlock();
		}
		return navMenuList;
	}

	/**
	 * 获得所有的子菜单
	 * 
	 * @return
	 */
	public List<NavigationMenu> getAllChildMenu() {
		refresh();
		List<NavigationMenu> navMenuList = new ArrayList<NavigationMenu>();
		try {
			lock.lock();
			Set<Entry<Long, NavigationMenu>> set = navMenuMap.entrySet();
			for (Entry<Long, NavigationMenu> entry : set) {
				NavigationMenu menu = entry.getValue();
				if (menu.getPid() != 0) {
					navMenuList.add(menu);
				}
			}
		} catch (Exception e) {
			LogConstant.mallLog.error("根据menuType查询NavigationMenu发生异常", e);
		} finally {
			lock.unlock();
		}
		return navMenuList;
	}
}
