package com.mall.admin.model.mybatis.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.order.SlaveOrderDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.order.ChildOrder;
@Repository
public class SlaveOrderDaoImpl extends BaseMallDaoImpl implements SlaveOrderDao{
	
	
	@Override
	public List<ChildOrder> queryThirdChildOrderListByPage(
			PaginationInfo paginationInfo, Map<String, Object> params,Integer masterSlaveFlag) {
		
		return this.getBaseDaoImplByFlag(masterSlaveFlag).selectPaginationList("ChildOrder.queryThirdChildOrderListByPage", params, paginationInfo);
	}

	@Override
	public List<ChildOrder> queryThirdChildOrderList(
			Map<String, Object> params, Integer masterSlaveFlag) {
		return this.getSqlSessionByFlag(masterSlaveFlag).selectList("ChildOrder.queryThirdChildOrderList", params);
	}
	
	@Override
	public double queryThirdChildOrderTotalAmount(
			Map<String, Object> params, Integer masterSlaveFlag) {
		return this.getSqlSessionByFlag(masterSlaveFlag).selectOne("ChildOrder.queryThirdChildOrderTotalAmount", params);
	}
	
}
