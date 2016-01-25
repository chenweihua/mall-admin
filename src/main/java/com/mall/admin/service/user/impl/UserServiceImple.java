package com.mall.admin.service.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.user.UserDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.service.util.impl.ZtreeUtilImpl;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.Menu;
import com.mall.admin.vo.user.Role;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.user.UserAndCategory;

@Service
public class UserServiceImple implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private StorageService storageService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ZtreeUtil ztreeUtil;

	@Override
	public User getUserByAccount(String account) {
		User user = userDao.getUserByAccount(account);
		if (user != null) {
			initUserRole(user);
			initUserMenu(user);
			initUserStorage(user);
			initUserCategory(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		User user = userDao.getUserById(userId);
		if (user != null) {
			initUserRole(user);
			initUserMenu(user);
			initUserStorage(user);
			initUserCategory(user);
		}
		return user;
	}

	@Override
	public void initUserCategory(User user) {
		Role userRole = user.role;
		if (userRole == null) {
			initUserRole(user);
			userRole = user.role;
		}
		// 非管理员
		if (user.is_all_category == 1 || userRole.admin_flag == 1) {
			List<Category> categoryList = categoryService.getAllCategories();
			user.categoryList = categoryList;
		} else if (user.is_all_category == 0) {
			List<Category> categoryList = categoryService.getCategoryByUserId(user.user_id);
			user.categoryList = categoryList;
		} else {
			user.categoryList = new ArrayList<Category>();
		}
	}

	@Override
	public void initUserRole(User user) {
		user.role = userDao.getUserRole(user.user_id);
	}

	@Override
	public void initUserMenu(User user) {
		Role userRole = user.role;
		if (userRole == null) {
			initUserRole(user);
			userRole = user.role;
		}
		List<Menu> menuList_temp = null;
		if (userRole.admin_flag == 1) {
			// 管理员用户
			menuList_temp = userDao.getAdminMenu();
		} else {
			menuList_temp = userDao.getMenuByRoleId(userRole.role_id);
		}
		List<Menu> menuList = new ArrayList<Menu>();
		if (menuList_temp != null) {
			/** 查找根节点 */
			for (Menu menu : menuList_temp) {
				if (menu.pid == 0) {
					menuList.add(menu);
				}
			}
			/** 查找根节点下的子节点 */
			for (Menu menu : menuList) {
				for (Menu menu_temp : menuList_temp) {
					if (menu_temp.pid == menu.menu_id) {
						menu.addChildMenu(menu_temp);
					}
				}
			}
		}
		user.menuList = menuList;
	}

	@Override
	public boolean checkUser(Long userId, String token) {
		if (Strings.isEmpty(token)) {
			return false;
		}
		if (userId == null) {
			return false;
		}
		User user = userDao.getUserById(userId);
		if (user != null && token.equals(user.token)) {
			return true;
		}
		return false;
	}

	@Override
	public List<User> getUserList(Map paramMap, PaginationInfo paginationInfo) {
		List<User> userList = userDao.getUserList(paramMap, paginationInfo);
		if (userList != null) {
			for (User user : userList) {
				Role role = userDao.getUserRole(user.user_id);
				user.setRole(role);
			}
		}
		// TODO Auto-generated method stub
		return userList;
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insert(user);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	@Override
	public int updateUserPassword(User user) {
		return userDao.updateUserPassword(user);
	}

	@Override
	public ZtreeBean getUserStorageRegion(User user) {
		// ZtreeBean rootZtreeBean = null;
		// ZtreeBean rdcZtreeBean = null;
		// ZtreeBean ldcZtreeBean = null;
		//
		// if (user.is_all_storage == 1) {
		// // 负责所有仓
		// List<Storage> allRdcStorage =
		// storageService.getAllRdcStorage();
		// if (allRdcStorage != null && allRdcStorage.size() > 0) {
		// rdcZtreeBean = new ZtreeBean("rdc_1", "root",
		// Constants.RDC_STORAGE_NAME, 10, Constants.ICON_RDC);
		// List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
		// for (Storage storage : allRdcStorage) {
		// ZtreeBean storageZtreeBean = new ZtreeBean(
		// storage.getStorageId() + "", "rdc_1",
		// storage.getStorageName(), 12,
		// Constants.ICON_STORAGE);
		// childBeanList.add(storageZtreeBean);
		// }
		// rdcZtreeBean.setChildren(childBeanList);
		// }
		// // 获得所有的城市
		// List<City> cityList = cityService.getCityListByPid(0);
		// List<ZtreeBean> ldcChildList = null;
		// if (cityList != null && cityList.size() > 0) {
		// for (City city : cityList) {
		// List<Storage> ldcStorageInCity = storageService
		// .getLdcStorageByCityId(city.city_id);
		// if (ldcStorageInCity != null && ldcStorageInCity.size() > 0)
		// {
		// if (ldcZtreeBean == null) {
		// ldcZtreeBean = new ZtreeBean("ldc_1",
		// "root",
		// Constants.LDC_STORAGE_NAME,
		// 20, Constants.ICON_LDC);
		// ldcChildList = new ArrayList<ZtreeBean>();
		// }
		// ZtreeBean cityBean = new ZtreeBean("city_"
		// + city.getCity_id(), "ldc_1",
		// city.getCity_name(), 21,
		// Constants.ICON_STORAGE);
		// List<ZtreeBean> cityChildList = new ArrayList<ZtreeBean>();
		// for (Storage storage : ldcStorageInCity) {
		// ZtreeBean storageBean = new ZtreeBean(
		// storage.getStorageId() + "",
		// "city_" + city.getCity_id(),
		// storage.getStorageName(),
		// 21, Constants.ICON_STORAGE);
		// cityChildList.add(storageBean);
		// }
		// cityBean.setChildren(cityChildList);
		// ldcChildList.add(cityBean);
		// }
		// }
		// if (ldcZtreeBean != null) {
		// ldcZtreeBean.setChildren(ldcChildList);
		// }
		// }
		// } else {
		// // 负责部分仓
		// List<Storage> allRdcStorage = storageService
		// .getRdcStorageByUserId(user.user_id);
		// if (allRdcStorage != null && allRdcStorage.size() > 0) {
		// rdcZtreeBean = new ZtreeBean("rdc_1", "root",
		// Constants.RDC_STORAGE_NAME, 10, Constants.ICON_RDC);
		// List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
		// for (Storage storage : allRdcStorage) {
		// ZtreeBean storageZtreeBean = new ZtreeBean(
		// storage.getStorageId() + "", "rdc_1",
		// storage.getStorageName(), 12,
		// Constants.ICON_STORAGE);
		// childBeanList.add(storageZtreeBean);
		// }
		// rdcZtreeBean.setChildren(childBeanList);
		// }
		// // 获得所有的城市
		// List<City> cityList = cityService.getCityListByPid(0);
		// List<ZtreeBean> rdcChildList = null;
		// if (cityList != null && cityList.size() > 0) {
		// for (City city : cityList) {
		// List<Storage> ldcStorageInCity = storageService
		// .getLdcStorageByUserIdAndCityId(
		// user.user_id, city.city_id);
		// if (ldcStorageInCity != null && ldcStorageInCity.size() > 0)
		// {
		// if (ldcZtreeBean == null) {
		// ldcZtreeBean = new ZtreeBean("ldc_1",
		// "root",
		// Constants.LDC_STORAGE_NAME,
		// 20, Constants.ICON_LDC);
		// rdcChildList = new ArrayList<ZtreeBean>();
		// }
		// ZtreeBean cityBean = new ZtreeBean("city_"
		// + city.getCity_id(), "ldc_1",
		// city.getCity_name(), 21,
		// Constants.ICON_STORAGE);
		// List<ZtreeBean> cityChildList = new ArrayList<ZtreeBean>();
		// for (Storage storage : ldcStorageInCity) {
		// ZtreeBean storageBean = new ZtreeBean(
		// storage.getStorageId() + "",
		// "city_" + city.getCity_id(),
		// storage.getStorageName(),
		// 22, Constants.ICON_STORAGE);
		// cityChildList.add(storageBean);
		// }
		// cityBean.setChildren(cityChildList);
		// rdcChildList.add(cityBean);
		// }
		// }
		// if (ldcZtreeBean != null) {
		// ldcZtreeBean.setChildren(rdcChildList);
		// }
		// }
		// }
		// if (rdcZtreeBean != null || ldcZtreeBean != null) {
		// rootZtreeBean = new ZtreeBean("root", "",
		// Constants.All_STORAGE_NAME, 0, "");
		// List<ZtreeBean> rootChildList = new ArrayList<ZtreeBean>();
		// if (rdcZtreeBean != null) {
		// rootChildList.add(rdcZtreeBean);
		// }
		// if (ldcZtreeBean != null) {
		// rootChildList.add(ldcZtreeBean);
		// }
		// rootZtreeBean.setChildren(rootChildList);
		// }
		// return rootZtreeBean;
		// return storageService.getStorageZtree(user.allStorageList);
		return ztreeUtil.getStorageZtree(user.allStorageList,true);
	}

	@Override
	public int insertUserCategory(UserAndCategory userAndCategory) {
		// TODO Auto-generated method stub
		return userDao.insertUserCategory(userAndCategory);
	}

	@Override
	public int deleteUserCategory(long userId) {
		// TODO Auto-generated method stub
		return userDao.deleteUserCategory(userId);
	}

	@Override
	public int deleteUserRoleByUserId(long userId) {
		// TODO Auto-generated method stub
		return userDao.deleteUserRoleByUserId(userId);
	}

	@Override
	public int insertUserRole(long userId, long roleId) {
		// TODO Auto-generated method stub
		return userDao.insertUserRole(userId, roleId);
	}

	@Override
	public Role getUserRole(long userId) {
		// TODO Auto-generated method stub
		return userDao.getUserRole(userId);
	}

	@Override
	public void initUserStorage(User user) {
		List<Storage> storageList = new ArrayList<Storage>();
		List<Storage> rdcstorageList = new ArrayList<Storage>();
		List<Storage> ldcstorageList = new ArrayList<Storage>();
		List<Storage> vmStorageList = new ArrayList<Storage>();
		Role userRole = user.role;
		if (userRole == null) {
			initUserRole(user);
			userRole = user.role;
		}
		/** 负责全部仓，或者是管理员 */
		if (user.is_all_storage == 1 || userRole.admin_flag == 1) {
			// 负责所有仓
			List<Storage> allRdcStorage = storageService.getAllRdcStorage();
			if (allRdcStorage != null && allRdcStorage.size() > 0) {
				for (Storage storage : allRdcStorage) {
					storageList.add(storage);
					rdcstorageList.add(storage);
				}
			}
			List<Storage> ldcStorageInCity = storageService.getAllLdcStorage();
			if (ldcStorageInCity != null && ldcStorageInCity.size() > 0) {
				for (Storage storage : ldcStorageInCity) {
					storageList.add(storage);
					ldcstorageList.add(storage);
				}
			}
			List<Storage> vmStorageInCity = storageService.getVMStorage();
			if (vmStorageInCity != null && vmStorageInCity.size() > 0) {
				for (Storage storage : vmStorageInCity) {
					storageList.add(storage);
					vmStorageList.add(storage);
				}
			}
		} else {
			// 负责部分仓
			// 获得ldc仓
			List<Storage> allRdcStorage = storageService.getRdcStorageByUserId(user.user_id);
			if (allRdcStorage != null && allRdcStorage.size() > 0) {
				for (Storage storage : allRdcStorage) {
					storageList.add(storage);
					rdcstorageList.add(storage);
				}
			}
			List<Storage> ldcStorageInCity = storageService.getLdcStorageByUserId(user.user_id);
			if (ldcStorageInCity != null && ldcStorageInCity.size() > 0) {
				for (Storage storage : ldcStorageInCity) {
					storageList.add(storage);
					ldcstorageList.add(storage);
				}
			}
			List<Storage> vmStorageInCity = storageService.getVMStorageByUserId(user.user_id);
			if (vmStorageInCity != null && vmStorageInCity.size() > 0) {
				for (Storage storage : vmStorageInCity) {
					storageList.add(storage);
					vmStorageList.add(storage);
				}
			}
		}
		user.allStorageList = storageList;
		user.ldcStorageList = ldcstorageList;
		user.rdcStorageList = rdcstorageList;
		user.setVmStorageList(vmStorageList);
	}

	@Override
	public int insertUserStorage(long userId, long storageId, long creator) {
		int count = userDao.selectUserStorageCount(userId, storageId);
		int result = 0;
		if (count == 0) {
			result = userDao.insertUserStorage(userId, storageId, creator);
		} else {
			result = userDao.updateUserStorage(userId, storageId, 0, creator);
		}
		return result;
	}

	@Override
	public int updateUserStorage(long userId, long storageId, long operator, int is_del) {
		// TODO Auto-generated method stub
		return userDao.updateUserStorage(userId, storageId, is_del, operator);
	}

	@Override
	public void setZtreeBeanStatus(ZtreeBean ztreeBean, User set_user) {
		if (ztreeBean == null) {
			return;
		}
		List<Storage> userStorageList = set_user.getAllStorageList();
		if (userStorageList == null || userStorageList.size() < 1) {
			return;
		}
		List<Long> user_id_list = new ArrayList<Long>();
		for (Storage bean : userStorageList) {
			// 所在仓是可用的。
			if (bean.getIsDel() == 0) {
				user_id_list.add(bean.getStorageId());
			}
		}
		if (user_id_list.size() == 0) {
			return;
		}
		new ZtreeUtilImpl().setZtreeStatus(ztreeBean, user_id_list);
	}

	@Override
	public void setUserRegion(ZtreeBean ztreeBean, User set_user, User user) {
		if (ztreeBean == null) {
			return;
		}
		// 获得该用户当前负责的仓库
		List<Storage> storageGoodsList = set_user.getAllStorageList();
		List<Long> user_storage_id_old_list = new ArrayList<Long>();
		List<Long> user_storage_id_new_list = new ArrayList<Long>();
		if (storageGoodsList != null && storageGoodsList.size() > 0) {
			for (Storage bean : storageGoodsList) {
				// 所在仓是可用的。
				if (bean.getIsDel() == 0) {
					user_storage_id_old_list.add(bean.getStorageId());
				}
			}
		}

		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();
		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return;
		}
		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean bean : rdcStorageChildrenZtree) {
					// 已被选中
					if (Constants.ZTREECHECKED.equals(bean.checked)) {
						user_storage_id_new_list.add(Long.parseLong(bean.id));
						if (!user_storage_id_old_list.contains(Long.parseLong(bean.id))) {

							// 新增的仓库，添加user_id和storage_id的关系
							insertUserStorage(set_user.getUser_id(),
									Long.parseLong(bean.id), user.getUser_id());
						}
					} else {
						childrenZtreen.isAllChecked = false;
						ztreeBean.isAllChecked = false;
					}
				}
			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// LDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean citybean : ldcStorageChildrenZtree) {
					List<ZtreeBean> cityChildrenZtree = citybean.getChildren();
					if (cityChildrenZtree == null || cityChildrenZtree.size() == 0) {
						continue;
					}
					for (ZtreeBean storagebean : cityChildrenZtree) {

						if (Constants.ZTREECHECKED.equals(storagebean.checked)) {
							user_storage_id_new_list.add(Long.parseLong(storagebean.id));

							if (!user_storage_id_old_list.contains(Long
									.parseLong(storagebean.id))) {
								// 新增的仓库，添加user_id和storage_id的关系
								insertUserStorage(set_user.getUser_id(),
										Long.parseLong(storagebean.id),
										user.getUser_id());
							}
						} else {
							// 子节点没有被全选，
							citybean.isAllChecked = false;
							childrenZtreen.isAllChecked = false;
							ztreeBean.isAllChecked = false;
						}
					}
				}
			}else if (Constants.VM_STORAGE_NAME.equals(childrenZtreen.name)) {
				// 虚拟仓
				List<ZtreeBean> vmStorageChildrenZtree = childrenZtreen.getChildren();
				if (vmStorageChildrenZtree == null || vmStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean bean : vmStorageChildrenZtree) {
					// 已被选中
					if (Constants.ZTREECHECKED.equals(bean.checked)) {
						user_storage_id_new_list.add(Long.parseLong(bean.id));
						if (!user_storage_id_old_list.contains(Long.parseLong(bean.id))) {

							// 新增的仓库，添加user_id和storage_id的关系
							insertUserStorage(set_user.getUser_id(),
									Long.parseLong(bean.id), user.getUser_id());
						}
					} else {
						childrenZtreen.isAllChecked = false;
						ztreeBean.isAllChecked = false;
					}
				}
			}
		}
		for (Long user_storage_id_temp : user_storage_id_old_list) {
			if (!user_storage_id_new_list.contains(user_storage_id_temp)) {
				// 说明是被删除的id 修改状态为禁用
				updateUserStorage(set_user.getUser_id(), user_storage_id_temp, user.getUser_id(), 1);
			}
		}
		// 用户负责全部仓库，或者是管理员，给其它用户设置全部仓库的权限
		if (user.is_all_storage == 1 || user.getRole().getAdmin_flag() == 1) {
			if (ztreeBean.isAllChecked) {
				userDao.udpateUserIsAllStorage(1, set_user.getUser_id());
			} else {
				userDao.udpateUserIsAllStorage(0, set_user.getUser_id());
			}
		} else {
			userDao.udpateUserIsAllStorage(0, set_user.getUser_id());
		}

	}

	@Override
	public boolean checkPermission(String url, List<Menu> userMenuList) {
		List<Menu> allMenuList = userDao.getAdminMenu();
		boolean needCheck = false;
		for (Menu menu : allMenuList) {
			if (url.equals(menu.getMenu_url())) {
				needCheck = true;
			}
		}
		if (needCheck) {
			// 检查用户是否具有访问的权限
			boolean hasPermission = false;
			for (Menu menu : userMenuList) {
				if (hasPermission) {
					break;
				}
				if (url.equals(menu.getMenu_url())) {
					hasPermission = true;
				}
				if (menu.getChildMenu() != null && menu.getChildMenu().size() > 0) {
					for (Menu childMenu : menu.getChildMenu()) {
						if (url.equals(childMenu.getMenu_url())) {
							hasPermission = true;
							break;
						}
					}
				}
			}
			if (hasPermission) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
