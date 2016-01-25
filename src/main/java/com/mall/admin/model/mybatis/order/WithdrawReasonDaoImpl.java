package com.mall.admin.model.mybatis.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.order.WithdrawReasonDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.order.WithdrawReason;

@Repository
public class WithdrawReasonDaoImpl extends BaseMallDaoImpl implements WithdrawReasonDao {

	@Override
	public List<WithdrawReason> getByLevel(Integer level) {
		return this.getSqlSession().selectList("WithdrawReason.selectByLevel", level);
	}

	@Override
	public List<WithdrawReason> getByPid(Long pid) {
		return this.getSqlSession().selectList("WithdrawReason.selectByPid", pid);
	}

	@Override
	public WithdrawReason getById(Long id) {
		return this.getSqlSession().selectOne("WithdrawReason.selectByPrimaryKey", id);
	}

}
