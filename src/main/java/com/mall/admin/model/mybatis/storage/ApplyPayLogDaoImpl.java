package com.mall.admin.model.mybatis.storage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.base._;
import com.mall.admin.model.dao.storage.ApplyPayLogDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.storage.ApplyPayLog;
import com.mall.admin.vo.storage.ApplyPayLogExample;
import com.mall.admin.vo.storage.ApplyPayLogExample.Criteria;
import com.mall.admin.vo.storage.ApplyPayLogMapper;

@Repository
public class ApplyPayLogDaoImpl extends BaseMallDaoImpl implements ApplyPayLogDao{

	public ApplyPayLogMapper getApplyPayLogMapper() {
		return this.getSqlSession().getMapper(ApplyPayLogMapper.class);
	}
	
	@Override
	public int insert(ApplyPayLog applyPayLog) {
		return getApplyPayLogMapper().insertSelective(applyPayLog);
	}

	@Override
	public Pair<Integer, List<ApplyPayLog>> getApplyPayLogList(int status, String applyCode,
			Date begin, Date end, String applyUser, int start, int limit) {
		ApplyPayLogExample example = new ApplyPayLogExample();
		if(start > -1 && limit > 0) {
			example.setStart(start).setLimit(limit);
		}
		Criteria criteria = example.createCriteria();
		if(status != -1) {
			criteria.andStatusEqualTo((byte)status);
		}
		
		if(!_.isEmpty(applyCode)) {
			criteria.andApplyCodeEqualTo(applyCode);
		}
		
		if(!_.isEmpty(applyUser)) {
			criteria.andApplyUserNameLike("%" + applyUser + "%");
		}
		
		if(begin != null && end != null && begin.before(end)) {
			criteria.andCreateTimeBetween(begin, end);
		}
		
		List<ApplyPayLog> applyPayLogs = getApplyPayLogMapper().selectByExample(example);
		int count = getApplyPayLogMapper().countByExample(example);
		
		return Pair.of(count, applyPayLogs);
	}
	
	@Override
	public ApplyPayLog getApplyPayLogByCode(String applyCode) {
		ApplyPayLog result = null;
		List<ApplyPayLog> applyPayLogs = null;
		ApplyPayLogExample example = new ApplyPayLogExample();
		example.createCriteria().andApplyCodeEqualTo(applyCode);
		applyPayLogs = getApplyPayLogMapper().selectByExample(example);
		if(!_.isEmpty(applyPayLogs) && applyPayLogs.size()==1) {
			result = applyPayLogs.get(0);
		}
		return result;
	}
	
	@Override
	public int update2ConfirmPay(ApplyPayLog payInfo) {
		return getApplyPayLogMapper().update2ConfirmPay(payInfo);
	}

}
