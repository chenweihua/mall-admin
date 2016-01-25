package com.mall.admin.model.mybatis.order;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.order.WithdrawDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
@Repository
public class WithdrawDaoImpl extends BaseMallDaoImpl implements WithdrawDao {

	@Override
	public Long countByChildOrderId(Long childOrderId,Integer masterSlaveFlag) {
		return this.getSqlSessionByFlag(masterSlaveFlag).selectOne("Withdraw.countByChildOrderId", childOrderId);
	}

}
