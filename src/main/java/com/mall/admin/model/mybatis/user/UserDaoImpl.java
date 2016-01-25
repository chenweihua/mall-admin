package com.mall.admin.model.mybatis.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.user.UserDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.user.Menu;
import com.mall.admin.vo.user.Role;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.user.UserAndCategory;

@Repository
public class UserDaoImpl extends BaseMallDaoImpl implements UserDao {

	@Override
	public User getUserByAccount(String account) {
		User user = new User();
		user.account = account;
		return this.getSqlSession().selectOne("User.getUserByAccount", user);
	}

	@Override
	public User getUserById(long userId) {
		User user = new User();
		user.user_id = userId;
		return this.getSqlSession().selectOne("User.getUserById", user);
	}

	@Override
	public Role getUserRole(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("User.getUserRole", userId);
	}

	@Override
	public List<Menu> getAdminMenu() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("User.getAllMenu");
	}

	@Override
	public List<Menu> getMenuByRoleId(long roleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("User.getMenuByRoleId", roleId);
	}

	@Override
	public List<Menu> getCommonMenu() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("User.getCommonMenu");
	}

	@Override
	public List<User> getUserList(Map paramMap, PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<User> userList = selectPaginationList("User.getPageUserByPage",
				paramMap, paginationInfo);
		return userList;
	}

	@Override
	public int insert(User user) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("User.insertUser", user);
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("User.updateUser", user);
	}

	@Override
	public int updateUserPassword(User user) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("User.updateUserPassword", user);
	}

	@Override
	public int deleteUserRoleByUserId(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("User.deleteUserRoleByUserId", userId);
	}

	@Override
	public int insertUserRole(long userId, long roleId) {
		Map map = new HashMap();
		map.put("user_id", userId);
		map.put("role_id", roleId);
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("User.insertUserRole", map);
	}

	@Override
	public int deleteUserCategory(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("User.deleteUserCategoryByUserId", userId);
	}

	@Override
	public int insertUserCategory(UserAndCategory userAndCategory) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("User.insertUserCategory", userAndCategory);
	}

	@Override
	public int selectUserStorageCount(long userId, long storageId) {
		// TODO Auto-generated method stub
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("user_id", userId);
		map.put("storage_id", storageId);
		return this.getSqlSession().selectOne("User.selectUserStorageCount", map);

	}

	@Override
	public int insertUserStorage(long userId, long storageId, long creator) {
		// TODO Auto-generated method stub
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("user_id", userId);
		map.put("storage_id", storageId);
		map.put("creator", creator);
		return this.getSqlSession().insert("User.insertUserStorage", map);
	}

	@Override
	public int updateUserStorage(long userId, long storageId, int is_del, long operator) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("user_id", userId);
		map.put("storage_id", storageId);
		map.put("is_del", is_del);
		map.put("operator", operator);
		return this.getSqlSession().update("User.updateUserStorage", map);
	}

	@Override
	public int udpateUserIsAllStorage(int is_all_storage, long user_id) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("is_all_storage", is_all_storage);
		map.put("user_id", user_id);
		return this.getSqlSession().update("User.updateUserIsAllStorage", map);
	}

}
