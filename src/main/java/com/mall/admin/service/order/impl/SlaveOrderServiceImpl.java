package com.mall.admin.service.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.constant.DeliverCompanyConstant;
import com.mall.admin.constant.IniBean;
import com.mall.admin.model.dao.order.ChildOrderDetialDao;
import com.mall.admin.model.dao.order.OrderDao;
import com.mall.admin.model.dao.order.SlaveOrderDao;
import com.mall.admin.model.dao.order.WithdrawDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.order.SlaveOrderService;
import com.mall.admin.vo.delivery.DeliverCompany;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
@Service
public class SlaveOrderServiceImpl implements SlaveOrderService {
	@Autowired
	private SlaveOrderDao slaveOrderDao;
	@Autowired
	private WithdrawDao withdrawDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ChildOrderDetialDao childOrderDetialDao;
	
	@Override
	public List<ChildOrder> queryThirdChildOrderListByPage(
			PaginationInfo paginationInfo, Map<String, Object> params) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		if(masterSlaveFlag == Constants.MASTER_SLAVE_FAIL){
			return null;
		}
		List<ChildOrder> childOrderList = slaveOrderDao.queryThirdChildOrderListByPage(paginationInfo, params,masterSlaveFlag);
		if(childOrderList == null || childOrderList.size() == 0){
			return childOrderList;
		}
		//完善信息
		for(ChildOrder childOrder : childOrderList){
			//物流名称
			DeliverCompany deliverCompany = DeliverCompanyConstant.getDeliverCompanyByCode(childOrder.getDeliverCompanyCode());
			childOrder.setDeliverCompanyName(deliverCompany == null ? "" : deliverCompany.getDeliverCompanyName());
			//是否有退款
			if(hasWithdraw(childOrder.getChildOrderId())){
				childOrder.setIsWithdraw(1);
			}else{
				childOrder.setIsWithdraw(0);
			}
			//母订单信息
			Order order = getOrderById(childOrder.getOrderId());
			if(order != null){
				childOrder.setOnlinePayId(order.getOnlinePayId());
				childOrder.setOnlinePay(order.getOnlinePay());
				childOrder.setFullSub(order.getFullSub());
				childOrder.setFirstSub(order.getFirstSub());
				childOrder.setCouponPay(order.getCouponPay());
				childOrder.setOrderStatus((byte)order.getStatus());
				childOrder.setOnlinePayType((byte)order.getOnlinePayType());
			}
		}
		return childOrderList;
	}

	@Override
	public List<ChildOrder> queryThirdChildOrderList(Map<String, Object> params) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		if(masterSlaveFlag == Constants.MASTER_SLAVE_FAIL){
			return null;
		}
		List<ChildOrder> childOrderList = slaveOrderDao.queryThirdChildOrderList(params,masterSlaveFlag);
		if(childOrderList == null || childOrderList.size() == 0){
			return childOrderList;
		}
		//完善信息
		for(ChildOrder childOrder : childOrderList){
			//物流名称
			DeliverCompany deliverCompany = DeliverCompanyConstant.getDeliverCompanyByCode(childOrder.getDeliverCompanyCode());
			childOrder.setDeliverCompanyName(deliverCompany == null ? "" : deliverCompany.getDeliverCompanyName());
			//是否有退款
			if(hasWithdraw(childOrder.getChildOrderId())){
				childOrder.setIsWithdraw(1);
			}else{
				childOrder.setIsWithdraw(0);
			}
			/*//母订单信息
			Order order = getOrderById(childOrder.getOrderId());
			if(order != null){
				childOrder.setOnlinePay(order.getOnlinePay());
				childOrder.setFullSub(order.getFullSub());
				childOrder.setFirstSub(order.getFirstSub());
				childOrder.setCouponPay(order.getCouponPay());
				childOrder.setOrderStatus((byte)order.getStatus());
				childOrder.setOnlinePayType((byte)order.getOnlinePayType());
			}*/
		}
		return childOrderList;
	}

	@Override
	public boolean hasWithdraw(Long childOrderId) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		Long num = withdrawDao.countByChildOrderId(childOrderId, masterSlaveFlag);
		if(num <= 0){
			return false;
		}
		return true;
	}

	@Override
	public Order getOrderById(Long orderId) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		if(masterSlaveFlag == Constants.MASTER_SLAVE_FAIL){
			return null;
		}
		return orderDao.getOrderById(orderId, masterSlaveFlag);
	}

	@Override
	public List<ChildOrderDetail> getChildOrderDetialByChildOrderId(
			Long childOrderId) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		return childOrderDetialDao.getChildOrderDetialByChildOrderId(childOrderId, masterSlaveFlag);
	}

	@Override
	public double queryThirdChildOrderTotalAmount(Map<String, Object> params) {
		//默认走从库
		Integer masterSlaveFlag = IniBean.getIniIntValue(Constants.MASTER_SLAVE_FLAG, Constants.SLAVE_FLAG_VALUE);
		return slaveOrderDao.queryThirdChildOrderTotalAmount(params, masterSlaveFlag);
	}
}
