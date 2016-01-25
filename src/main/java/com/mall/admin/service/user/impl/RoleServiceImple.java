package com.mall.admin.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.user.RoleDao;
import com.mall.admin.service.user.RoleService;
import com.mall.admin.vo.user.Role;

@Service
public class RoleServiceImple implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public Role getRoleByUserId(long userId) {
		return roleDao.getRoleByUserId(userId);
	}

	@Override
	public List<Role> getAllRole() {
		return roleDao.getRole();
	}

	@Override
	public List<Role> getEnableRole() {
		return roleDao.getEnableRole();
	}

}
