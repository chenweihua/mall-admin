package com.mall.admin.service.order;

import java.util.List;

import com.mall.admin.vo.order.WithdrawReason;

public interface WithdrawReasonService {
	
	public List<WithdrawReason> getByLevel(Integer level);
	
	public List<WithdrawReason> getByPid(Long pid);
	
	public WithdrawReason getById(Long id);
}
