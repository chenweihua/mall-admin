package com.mall.admin.model.mybatis.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mall.admin.model.dao.order.OrderDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.util._;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.ChildOrderExample;
import com.mall.admin.vo.order.ChildOrderExample.Criteria;
import com.mall.admin.vo.order.ChildOrderMapper;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.PrintInfo;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.WithdrawExample;
import com.mall.admin.vo.order.WithdrawMapper;

@Repository
public class OrderDaoImpl extends BaseMallDaoImpl implements OrderDao {
	
	@Override
	public Order getOrderById(Long orderId, Integer masterSlaveFlag) {
		return this.getSqlSessionByFlag(masterSlaveFlag).selectOne("Order.getByOrderId", orderId);
	}

	private ChildOrderMapper getChildOrderMapper() {
		return this.getSqlSession().getMapper(ChildOrderMapper.class);
	}

	private WithdrawMapper getWithdrawMapper() {
		return this.getSqlSession().getMapper(WithdrawMapper.class);
	}

	@Override
	public Order getOrderById(long id) {
		return this.getSqlSession().selectOne("Order.getOrderById", id);
	}

	@Override
	public ChildOrder getChildOrderById(long id) {
		// return
		// this.getSqlSession().selectOne("ChildOrder.getChildOrderById",
		// id);
		return getChildOrderMapper().selectByPrimaryKey(id);
	}

	@Override
	public ChildOrder getChildOrderByCode(String childOrderCode) {
		// TODO Auto-generated method stub
		ChildOrderExample example = new ChildOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildOrderCodeEqualTo(childOrderCode);
		List<ChildOrder> childOrderList = getChildOrderMapper().selectByExample(example);
		if (childOrderList == null || childOrderList.size() == 0) {
			return null;
		}
		return childOrderList.get(0);
	}

	@Override
	public List<ChildOrder> getChildOrderList(int limit) {
		ChildOrderExample example = new ChildOrderExample().setStart(0).setLimit(limit);
		return getChildOrderMapper().selectByExample(example);
	}

	@Override
	public Pair<Integer, List<ChildOrder>> getChildOrderList(List<Long> collegeIds, String phone, int status,
			int orderType, Date startDate, Date endDate, String orderCode, String porderCode, String deliverSheetCode, List<Long> storageIds, String deliverStatus, int start,
			int numPerPage) {

		ChildOrderExample example = new ChildOrderExample().setStart(start).setLimit(numPerPage)
				.setOrderByClause(" order_id DESC, child_order_id DESC");
		Criteria criteria = example.createCriteria().andCollegeIdIn(collegeIds);
		if (!_.isEmpty(orderCode)) {
			criteria.andChildOrderCodeEqualTo(orderCode);
			if (orderType != -1) {
				criteria.andTypeEqualTo(_.toByte(orderType));
			}
		} else if (!_.isEmpty(porderCode)) {
			criteria.andOrderCodeEqualTo(porderCode);
		} else {
			
			if (!_.isEmpty(phone)) {
				criteria.andReceiverPhoneEqualTo(phone);
			}
			
			if (status != 0) {
				criteria.andStatusEqualTo(_.toByte(status));
			}
			if (startDate != null) {
				criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
			}
			if (endDate != null) {
				criteria.andCreateTimeLessThanOrEqualTo(endDate);
			}
			if (orderType != -1) {
				criteria.andTypeEqualTo(_.toByte(orderType));
			}
			if(deliverSheetCode != null) {
				criteria.andDeliverSheetCodeEqualTo(deliverSheetCode);
			}
			if(storageIds != null) {
				criteria.andStorageIdEqualTo(storageIds);
			}
			
			
			if("0".equals(deliverStatus)) { //待发货
				criteria.andDeliverNotSended();
			} else if("1".equals(deliverStatus)) { //已发货
				criteria.andDeliverSended();
			}
			
			
		}
		List<ChildOrder> datas = getChildOrderMapper().selectByExample(example);
		int count = getChildOrderMapper().countByExample(example);
		return Pair.of(count, datas);
	}
	
	
	@Override
	public Pair<Integer, List<ChildOrder>> getThirdSellerChildOrderList(String phone, int status,int orderType, 
			Date startDate, Date endDate, String orderCode, String deliverSheetCode, List<Long> storageIds, String deliverStatus,  Integer deliveryStatus,
			int start,int numPerPage) {

		ChildOrderExample example = new ChildOrderExample().setStart(start).setLimit(numPerPage)
				.setOrderByClause(" order_id DESC, child_order_id DESC");
		Criteria criteria = example.createCriteria();
		if (!_.isEmpty(orderCode)) {
			criteria.andChildOrderCodeEqualTo(orderCode);
			if (orderType != -1) {
				criteria.andTypeEqualTo(_.toByte(orderType));
			}
		} else {
			
			if (!_.isEmpty(phone)) {
				criteria.andReceiverPhoneEqualTo(phone);
			}
			
			if (status != 0) {
				criteria.andStatusEqualTo(_.toByte(status));
			}
			if (startDate != null) {
				criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
			}
			if (endDate != null) {
				criteria.andCreateTimeLessThanOrEqualTo(endDate);
			}
			if (orderType != -1) {
				criteria.andTypeEqualTo(_.toByte(orderType));
			}
			if(StringUtils.isNotEmpty(deliverSheetCode)) {
				criteria.andDeliverSheetCodeEqualTo(deliverSheetCode);
			}
			if(storageIds != null) {
				criteria.andStorageIdEqualTo(storageIds);
			}
			
			if(deliveryStatus != null) {
				criteria.andDeliveryStatusEqualTo(deliveryStatus);
			}
			
			if("0".equals(deliverStatus)) { //待发货
				criteria.andDeliverNotSended();
			} else if("1".equals(deliverStatus)) { //已发货
				criteria.andDeliverSended();
			}
			
			
			
			
		}
		List<ChildOrder> datas = getChildOrderMapper().selectByExample(example);
		int count = getChildOrderMapper().countByExample(example);
		return Pair.of(count, datas);
	}
	

	@Override
	public Pair<Integer, List<ChildOrder>> getChildOrderListForCustomer(String phone, String orderCode, int start,
			int numPerPage) {

		ChildOrderExample example = new ChildOrderExample().setStart(start).setLimit(numPerPage)
				.setOrderByClause(" order_id, child_order_id");
		Criteria criteria = example.createCriteria();
		if (!_.isEmpty(orderCode)) {
			criteria.andChildOrderCodeEqualTo(orderCode);
		} else if (!_.isEmpty(phone)) {
			criteria.andReceiverPhoneEqualTo(phone);
		}
		List<ChildOrder> datas = getChildOrderMapper().selectByExample(example);
		int count = getChildOrderMapper().countByExample(example);
		return Pair.of(count, datas);
	}

	@Override
	public ChildOrderDetail getChildOrderDetailById(long id) {
		return this.getSqlSession().selectOne("ChildOrderDetail.getChildOrderDetailById", id);
	}

	@Override
	public List<ChildOrderDetail> getChildOrderDetailListByChildOrderId(long childOrderId) {
		return this.getSqlSession().selectList("ChildOrderDetail.getChildOrderDetailListByChildOrderId",
				childOrderId);
	}

	@Override
	public List<Withdraw> getWithdrawByOrderId(long orderId) {
		WithdrawExample example = new WithdrawExample();
		example.createCriteria().andOrderIdEqualTo(orderId);
		return getWithdrawMapper().selectByExample(example);
	}

	@Override
	public void updateWithdraw(Withdraw withdrawOld) {
		getWithdrawMapper().updateByPrimaryKey(withdrawOld);
	}

	@Override
	public void insertWithdraw(Withdraw withdraw) {
		getWithdrawMapper().insertSelective(withdraw);
	}

	@Override
	public List<Withdraw> getAfterListByIdAndDate(long childOrderDetailId) {
		Date today = new DateTime().millisOfDay().setCopy(0).toDate();
		WithdrawExample example = new WithdrawExample();
		example.createCriteria().andCreatetimeGreaterThanOrEqualTo(today).andOrderDetailIdEqualTo(childOrderDetailId);
		return getWithdrawMapper().selectByExample(example);
	}

	@Override
	public List<Withdraw> getBeforeListByIdAndDate(long childOrderDetailId) {
		Date today = new DateTime().millisOfDay().setCopy(0).toDate();
		WithdrawExample example = new WithdrawExample();
		example.createCriteria().andCreatetimeLessThan(today).andOrderDetailIdEqualTo(childOrderDetailId);
		return getWithdrawMapper().selectByExample(example);
	}

	@Override
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate) {
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria cirteri = example.createCriteria();
		if (startDate != null) {
			cirteri.andCreatetimeGreaterThanOrEqualTo(startDate);
		}
		if (endDate != null) {
			cirteri.andCreatetimeLessThanOrEqualTo(endDate);
		}
		return getWithdrawMapper().selectByExample(example);
	}
	
	
	@Override
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate, List<Long> storageIds) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("startTime", startDate);
		paramMap.put("endTime", endDate);
		paramMap.put("storageIds", storageIds);
		
		return this.getSqlSession().selectList("com.mall.admin.vo.order.WithdrawMapper.selectByStorageId",paramMap );
	}

	@Override
	public List<ChildOrder> getLDCChildOrderList(long storageId, int fdcStatus, String startDate, String endDate,
			String searchStr, PaginationInfo paginationInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("storageId", storageId);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		if (fdcStatus != 0) {
			paramMap.put("fdcStatus", fdcStatus);
		}
		if (!Strings.isEmpty(searchStr)) {
			paramMap.put("searchStr", searchStr);
		}
		// TODO Auto-generated method stub
		PaginationList<ChildOrder> childOrderList = selectPaginationList(
				"com.mall.admin.vo.order.ChildOrderMapper.selectLdcChildOrderByPage",
				paramMap, paginationInfo);
		return childOrderList;
	}

	@Override
	public int updateChildOrderFdcStatus(int fdcStatus, String childOrderCode) {
		Map paramMap = new HashMap();
		paramMap.put("fdcStatus", fdcStatus);
		paramMap.put("childOrderCode", childOrderCode);
		return this.getSqlSession().update(
				"com.mall.admin.vo.order.ChildOrderMapper.updateFdcStatus", paramMap);
	}

	@Override
	public List<PrintInfo> getPrintInfoList(long childOrderId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("ChildOrderDetail.getPrintInfo", childOrderId);
	}

	@Override
	public long userSuccessOrderCount(long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getSqlSession().selectOne("Order.selectUesrSuccessOrderCount", params);
	}

	@Override
	public long getLdcOrderCountByQuery(int fdcStatus, long storageId, String startDate, String endDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (startDate != null) {
			paramMap.put("startDate", startDate);
		}
		if (endDate != null) {
			paramMap.put("endDate", endDate);
		}
		paramMap.put("storageId", storageId);

		paramMap.put("fdcStatus", fdcStatus);
		return this.getSqlSession()
				.selectOne("com.mall.admin.vo.order.ChildOrderMapper.selectLdcChildOrderCount",
						paramMap);
	}
	
	
	/**
	 * 更新子订单表deliver_company_code字段和deliver_sheet_code字段
	 */
	@Override
	public boolean updateDeliverInfo(String childOrderCode, String deliverCompanyCode, String deliverSheetCode) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("childOrderCode", childOrderCode);
		paramMap.put("deliverCompanyCode", deliverCompanyCode);
		paramMap.put("deliverSheetCode", deliverSheetCode);
		
		ChildOrder childOrder = getChildOrderByCode(childOrderCode);
		
		paramMap.put("userId", childOrder.getUserId());
		
		int affectNumZong = this.getSqlSession().update("Order.updateDeliverInfo", paramMap);
		int affectNumFen = this.getMySqlSession(childOrder.getUserId()).update("Order.updateDeliverInfo", paramMap);
		
		return affectNumZong == 1 && affectNumFen == 1;
	}
	
	
	@Override
	public boolean updateDeliveryStatus(String childOrderCode,Long userId,Integer deliveryStatus) {
		
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("childOrderCode", childOrderCode);
		paramMap.put("userId", userId);
		paramMap.put("deliveryStatus", deliveryStatus);
		
		int affectNumZong = this.getSqlSession().update("Order.updateDeliveryStatus",paramMap);
		int affectNumFen = this.getMySqlSession(userId).update("Order.updateDeliveryStatus",paramMap);
		
		return affectNumZong == 1 && affectNumFen == 1;
		
	}

}
