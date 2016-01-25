package com.mall.admin.model.dao.user;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.user.Menu;
import com.mall.admin.vo.user.Role;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.user.UserAndCategory;

public interface UserDao {

	/**
	 * 获取用户信息
	 * 
	 * @param paramMap
	 * @param paginationInfo
	 * @return
	 */
	public List<User> getUserList(Map paramMap, PaginationInfo paginationInfo);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public int insert(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public int update(User user);

	/**
	 * 更新用户的密码和token
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
	 * 根据用户id获得用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(long userId);

	/**
	 * 获得用户的角色
	 * */
	public Role getUserRole(long userId);

	/**
	 * 删除用户的角色
	 * */
	public int deleteUserRoleByUserId(long userId);

	/**
	 * 添加用户角色
	 * */
	public int insertUserRole(long userId, long roleId);

	/**
	 * 获得管理员的菜单
	 * */
	public List<Menu> getAdminMenu();

	/**
	 * 根据权限获得用户可范围的菜单
	 * */
	public List<Menu> getMenuByRoleId(long roleId);

	/**
	 * 获得普通用户可访问的菜单
	 * */
	public List<Menu> getCommonMenu();

	/**
	 * 删除用户所有类目
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserCategory(long userId);

	/***
	 * 添加用户关联的类目
	 * 
	 * @param userAndCategory
	 * @return
	 */
	public int insertUserCategory(UserAndCategory userAndCategory);

	/**
	 * 查询用户对应的仓库是否存在
	 * 
	 * @param userId
	 * @param storageId
	 * @return
	 */
	public int selectUserStorageCount(long userId, long storageId);

	/**
	 * 添加用户和仓库的关系
	 * 
	 * @param userId
	 * @param storageId
	 * @param creator
	 * @return
	 */
	public int insertUserStorage(long userId, long storageId, long creator);

	/**
	 * 更新用户和仓库的关系
	 * 
	 * @param userId
	 * @param storageId
	 * @param is_del
	 * @param operator
	 * @return
	 */
	public int updateUserStorage(long userId, long storageId, int is_del, long operator);

	/**
	 * 更新用户是否负责全部仓库
	 * 
	 * @param is_all_storage
	 * @param user_id
	 * @return
	 */
	public int udpateUserIsAllStorage(int is_all_storage, long user_id);

}
