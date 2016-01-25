package com.mall.admin.model.dao.user;

import java.util.List;

import com.mall.admin.vo.user.Role;

public interface RoleDao {

	public Role getRoleByUserId(long userId);

	public List<Role> getRole();

	public List<Role> getEnableRole();

}
