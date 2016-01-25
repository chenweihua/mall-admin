package com.mall.admin.model.dao.storage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.vo.storage.ApplyPayLog;


public interface ApplyPayLogDao {
	public int insert(ApplyPayLog applyPayLog);
	public Pair<Integer,List<ApplyPayLog>> getApplyPayLogList(int status, String applyCode, Date begin, Date end, String applyUser, int start, int limit);
	
	public ApplyPayLog getApplyPayLogByCode(String applyCode);

	public int update2ConfirmPay(ApplyPayLog payInfo);
}
