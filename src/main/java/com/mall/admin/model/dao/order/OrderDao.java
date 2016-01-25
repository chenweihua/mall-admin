package com.mall.admin.model.dao.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.PrintInfo;
import com.mall.admin.vo.order.Withdraw;

public interface OrderDao {
	/**
	 * 分住从库读取，目前用于订单查询 --zhangshuai
	 * @param id
	 * @param masterSlaveFlag
	 * @return
	 */
	public Order getOrderById(Long orderId,Integer masterSlaveFlag);

	public Order getOrderById(long id);

	public ChildOrder getChildOrderById(long id);

	public ChildOrder getChildOrderByCode(String childOrderCode);

	public List<ChildOrder> getChildOrderList(int limit);

	public Pair<Integer, List<ChildOrder>> getChildOrderList(List<Long> collegeIds, String phone, int status,
			int orderType, Date startDate, Date endDate, String orderCode, String porderCode,String deliverSheetCode, List<Long> storageId, String deliverStatus, int start,
			int numPerPage);
	
	public Pair<Integer, List<ChildOrder>> getThirdSellerChildOrderList(String phone, int status,int orderType, 
			Date startDate, Date endDate, String orderCode, String deliverSheetCode, List<Long> storageIds, String deliverStatus, Integer deliveryStatus,
			int start,int numPerPage);

	public ChildOrderDetail getChildOrderDetailById(long id);

	public List<ChildOrderDetail> getChildOrderDetailListByChildOrderId(long childOrderId);

	public List<Withdraw> getWithdrawByOrderId(long orderId);

	public void updateWithdraw(Withdraw withdrawOld);

	public Pair<Integer, List<ChildOrder>> getChildOrderListForCustomer(String phone, String orderCode, int start,
			int numPerPage);

	public void insertWithdraw(Withdraw withdraw);

	public List<Withdraw> getBeforeListByIdAndDate(long childOrderDetailId);

	public List<Withdraw> getAfterListByIdAndDate(long childOrderDetailId);

	public List<Withdraw> getWithdrawList(Date startDate, Date endDate);
	
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate, List<Long> storageIds);

	public long userSuccessOrderCount(long userId);

	/**
	 *
	 * @param storageId
	 *                仓库id
	 * @param fdcStatus
	 *                fdc状态 0：全部;1:未打印；2：未出库；3：已出库
	 * @param startDate
	 *                开始时间
	 * @param endDate
	 *                结束时间
	 * @param searchStr
	 *                模糊查询条件
	 * @param start
	 *                起始值
	 * @param numPerPage
	 *                结束值
	 * @return
	 */
	public List<ChildOrder> getLDCChildOrderList(long storageId, int fdcStatus, String startDate, String endDate,
			String searchStr, PaginationInfo paginationInfo);

	/**
	 * 更新子订单的fdc状态 1：待打印；2：待出库；3：已出库
	 * 
	 * @param fdcStatus
	 * @param childOrderCode
	 * @return
	 */
	public int updateChildOrderFdcStatus(int fdcStatus, String childOrderCode);

	public List<PrintInfo> getPrintInfoList(long childOrderId);

	/**
	 * 查询满足条件的ldc订单数量
	 * 
	 * @param fdcStatus
	 *                1：未打印 2：未出库 3：已出库
	 * @param storageId
	 *                仓库id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long getLdcOrderCountByQuery(int fdcStatus, long storageId, String startDate, String endDate);
	
	/**
	 * 更新子订单表deliver_company_code字段和deliver_sheet_code字段
	 */
	public boolean updateDeliverInfo(String childOrderCode, String deliverCompanyCode, String deliverSheetCode);
	
	
	public boolean updateDeliveryStatus(String childOrderCode,Long userId,Integer deliveryStatus);

}
