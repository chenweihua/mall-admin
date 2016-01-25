package com.mall.admin.model.mybatis.subsribe;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.subsribe.SubscribeDetailDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.subscribe.SubscribeDetail;
import com.mall.admin.vo.subscribe.SubscribeDetailExample;

@Repository
public class SubscribeDetailDaoImpl extends BaseMallDaoImpl implements SubscribeDetailDao {

	@Override
	public int countByExample(SubscribeDetailExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(SubscribeDetailExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Long subscribeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(SubscribeDetail record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(SubscribeDetail record) {
		return this.getSqlSession().insert("SubscribeDetail.insertSelective",record);
	}

	@Override
	public List<SubscribeDetail> selectByExample(SubscribeDetailExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscribeDetail selectByPrimaryKey(Long subscribeId) {
		return this.getSqlSession().selectOne("SubscribeDetail.selectByPrimaryKey",subscribeId);
	}

	@Override
	public int updateByExampleSelective(SubscribeDetail record,
			SubscribeDetailExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(SubscribeDetail record,
			SubscribeDetailExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(SubscribeDetail record) {
		return this.getSqlSession().update("SubscribeDetail.updateByPrimaryKeySelective",record);
	}

	@Override
	public int updateByPrimaryKey(SubscribeDetail record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public List<SubscribeDetail> getList(Map<String,Object> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<SubscribeDetail> subscribeDetailList = selectPaginationList("SubscribeDetail.getPageSubscribeDetailByPage", param, paginationInfo);
		return subscribeDetailList;
	}

}
