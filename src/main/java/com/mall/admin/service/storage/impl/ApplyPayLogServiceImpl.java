package com.mall.admin.service.storage.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.storage.ApplyPayLogDao;
import com.mall.admin.service.storage.ApplyPayLogService;
import com.mall.admin.vo.storage.ApplyPayLog;

@Service
public class ApplyPayLogServiceImpl implements ApplyPayLogService {

	@Autowired
	private ApplyPayLogDao applyPayLogDao;
	
	@Override
	public long insert(ApplyPayLog applyPayLog) {
		return applyPayLogDao.insert(applyPayLog);
	}

	@Override
	public Pair<Integer, List<ApplyPayLog>> getApplyPayLogList(int status,
			String applyCode, Date begin, Date end, String applyUser,
			int start, int limit) {
		return applyPayLogDao.getApplyPayLogList(status, applyCode, begin, end, applyUser, start, limit);
	}
	
	@Override
	public ApplyPayLog getApplyPayLogByCode(String applyCode) {
		return applyPayLogDao.getApplyPayLogByCode(applyCode);
	}
	
	@Override
	public int update2ConfirmPay(ApplyPayLog payInfo) {
		return applyPayLogDao.update2ConfirmPay(payInfo);
	}

}
