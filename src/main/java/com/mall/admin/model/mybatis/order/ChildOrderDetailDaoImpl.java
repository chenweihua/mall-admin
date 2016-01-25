package com.mall.admin.model.mybatis.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.order.ChildOrderDetialDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.order.ChildOrderDetail;
@Repository
public class ChildOrderDetailDaoImpl extends BaseMallDaoImpl implements ChildOrderDetialDao {

	@Override
	public List<ChildOrderDetail> getChildOrderDetialByChildOrderId(
			Long childOrderId,Integer masterSlaveFlag) {
		return this.getSqlSessionByFlag(masterSlaveFlag).selectList("ChildOrderDetail.getChildOrderDetialByChildOrderId", childOrderId);
	}

}
