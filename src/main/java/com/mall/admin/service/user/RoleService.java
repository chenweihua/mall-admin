package com.mall.admin.service.user;

import java.util.List;

import com.mall.admin.vo.user.Role;

public interface RoleService {

	public Role getRoleByUserId(long userId);

	public List<Role> getAllRole();

	public List<Role> getEnableRole();

}
