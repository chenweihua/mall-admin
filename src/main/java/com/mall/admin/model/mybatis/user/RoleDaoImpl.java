package com.mall.admin.model.mybatis.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.user.RoleDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.user.Role;

@Repository
public class RoleDaoImpl extends BaseMallDaoImpl implements RoleDao {

	@Override
	public Role getRoleByUserId(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Role.getUserRole", userId);
	}

	@Override
	public List<Role> getRole() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Role.getAllRole");
	}

	@Override
	public List<Role> getEnableRole() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Role.getEnableRole");
	}

}
