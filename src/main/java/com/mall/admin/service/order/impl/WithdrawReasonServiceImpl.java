package com.mall.admin.service.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.order.WithdrawReasonDao;
import com.mall.admin.service.order.WithdrawReasonService;
import com.mall.admin.vo.order.WithdrawReason;
@Service
public class WithdrawReasonServiceImpl implements WithdrawReasonService {
	@Autowired
	private WithdrawReasonDao withdrawReasonDao;
	
	@Override
	public List<WithdrawReason> getByLevel(Integer level) {
		return withdrawReasonDao.getByLevel(level);
	}

	@Override
	public List<WithdrawReason> getByPid(Long pid) {
		return withdrawReasonDao.getByPid(pid);
	}

	@Override
	public WithdrawReason getById(Long id) {
		return withdrawReasonDao.getById(id);
	}

}
