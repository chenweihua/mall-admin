package com.mall.admin.service.user;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.user.Menu;
import com.mall.admin.vo.user.Role;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.user.UserAndCategory;

public interface UserService {

	/**
	 * 获取用户列表
	 * 
	 * @param paramMap
	 * @param paginationInfo
	 * @return
	 */
	public List<User> getUserList(Map paramMap, PaginationInfo paginationInfo);

	public int insertUser(User user);

	public int updateUser(User user);

	/**
	 * 更新用户密码
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserPassword(User user);

	/**
	 * 根据用户账号获得用户信息
	 * 
	 * @return
	 */
	public User getUserByAccount(String account);

	/**
	 * 根据用户账号获得用户信息
	 * 
	 * @return
	 */
	public User getUserById(Long userId);

	/**
	 * 设置用户负责的类目
	 * 
	 * @param user
	 */
	public void initUserCategory(User user);

	/**
	 * 设置用户的角色信息
	 */
	public void initUserRole(User user);

	/**
	 * 获得用户可访问的菜单
	 * 
	 * @param userId
	 *                用户id
	 * @return
	 */
	public void initUserMenu(User user);

	/**
	 * 检查用户登录是否正确
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean checkUser(Long userId, String token);

	/**
	 * 获得用户的负责的范围
	 * 
	 * @return
	 */
	public ZtreeBean getUserStorageRegion(User user);

	/**
	 * 添加用户和类目的关系
	 * 
	 * @param userAndCategory
	 * @return
	 */
	public int insertUserCategory(UserAndCategory userAndCategory);

	/**
	 * 删除用户对应的类目
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserCategory(long userId);

	/**
	 * 删除用户对应的角色
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserRoleByUserId(long userId);

	/**
	 * 添加用户对应的角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int insertUserRole(long userId, long roleId);

	/**
	 * 活动用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	public Role getUserRole(long userId);

	/**
	 * 初始化用户负责的仓库
	 * 
	 * @param userId
	 * @return
	 */
	public void initUserStorage(User user);

	/**
	 * 添加用户的仓库的关系
	 * 
	 * @param userId
	 * @param storageId
	 * @param creator
	 * @return
	 */
	public int insertUserStorage(long userId, long storageId, long creator);

	/**
	 * 更新用户和仓库是否可用
	 * 
	 * @param userId
	 * @param storageId
	 * @param operator
	 * @param is_del
	 *                0：可用 1：不可用
	 * @return
	 */
	public int updateUserStorage(long userId, long storageId, long operator, int is_del);

	/**
	 * 设置用户Ztree对象的选中状态
	 * 
	 * @param ztreeBean
	 * @param set_user
	 */
	public void setZtreeBeanStatus(ZtreeBean ztreeBean, User set_user);

	/**
	 * 这是用户负责的范围
	 * 
	 * @param ztreeBean
	 * @param set_user
	 * @param user
	 */
	public void setUserRegion(ZtreeBean ztreeBean, User set_user, User user);

	public boolean checkPermission(String url, List<Menu> userMenuList);
	
}
