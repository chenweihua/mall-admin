package com.mall.admin.service.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.OutStorageRecord;
import com.mall.admin.vo.order.PrintInfo;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.customer.RefundResult;
import com.mall.admin.vo.user.User;

public interface OrderService {

	public Order getOrderById(long id);

	public ChildOrder getChildOrderById(long id);

	public ChildOrder getChildOrderByCode(String childOrderCode);

	/**
	 * phone/orderCode/porderCode 相等查询
	 * 
	 * @param phone
	 * @param status
	 * @param orderType
	 * @param startDate
	 * @param endDate
	 * @param orderCode
	 * @param porderCode
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	public Pair<Integer, List<ChildOrder>> getChildOrderList(User user, String phone, int status, int orderType,
			Date startDate, Date endDate, String orderCode, String porderCode, String deliverSheetCode, List<Long> storageIds, String deliverStatus,int start, int numPerPage);

	
	/**
	 * 查询第三方卖家订单
	 */
	public Pair<Integer, List<ChildOrder>> getThirdSellerChildOrderList(String phone, int status, int orderType,
			Date startDate, Date endDate, String orderCode, String deliverSheetCode, List<Long> storageId, String deliverStatus, Integer deliveryStatus, int start, int numPerPage);


	
	
	/**
	 * 为客服提供的查询接口
	 * 
	 * @param phone
	 * @param orderCode
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	public Pair<Integer, List<ChildOrder>> getChildOrderListForCustomer(String phone, String orderCode, int start,
			int numPerPage);

	public ChildOrderDetail getChildOrderDetailById(long id);

	public List<ChildOrderDetail> getChildOrderDetailListByChildOrderId(long childOrderId);

	public void insertWithdraw(Withdraw withdraw);

	public List<Withdraw> getWithdrawByOrderlId(long orderId);

	public void updateWithdraw(Withdraw withdrawOld);

	public Pair<Integer, String> withdraw(List<Pair<Long, Integer>> detailList, String reason, long userId,
			String name);

	/**
	 * 获取某订单详情今天之前的退款记录
	 * 
	 * @param id
	 * @return
	 */
	public List<Withdraw> getBeforeListByIdAndDate(long childOrderDetailId);

	/**
	 * 获取某订单详情今天的退款记录
	 * 
	 * @param id
	 * @return
	 */
	public List<Withdraw> getAfterListByIdAndDate(long childOrderDetailId);

	public RefundResult checkRefundInfo(List<Pair<Long, Integer>> detailList);

	public List<Withdraw> getWithdrawList(Date startDate, Date endDate);
	
	public List<Withdraw> getWithdrawList(Date startDate, Date endDate, List<Long> storageIds);

	public boolean isFirstOrderUser(long userId);

	/**
	 * 查询ldc仓未打印的订单
	 * 
	 * @param storageId
	 * @param fdcStatus
	 * @param startDate
	 * @param endDate
	 * @param searchStr
	 * @param paginationInfo
	 * @return
	 */
	public List<ChildOrder> getLdcChildOrder(long storageId, int fdcStatus, String startDate, String endDate,
			String searchStr, PaginationInfo paginationInfo);

	/**
	 * 根据订单编号获得订单信息
	 * 
	 * @param orderCode
	 * @return
	 */
	public ChildOrder getLdcChildOrderByOrderCode(String orderCode);

	/**
	 * 更新子订单的fdc状态
	 * 
	 * @param fdcStatus
	 *                订单状态 1：待打印 2：待出库 3：已出库
	 * @param childOrderCode
	 *                子订单编号
	 * @return
	 */
	public int updateChildOrderFdcStatus(int fdcStatus, String childOrderCode);

	/**
	 * 查询打印信息
	 * 
	 * @param childOrderId
	 * @return
	 */
	public List<PrintInfo> getPrintList(long childOrderId);

	/**
	 * 获得未出库的订单数量
	 * 
	 * @param storageId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public long getUnOutStorageOrderCount(long storageId, String beginDate, String endDate);

	/**
	 * 查询未打印的订单
	 * 
	 * @param storageId
	 * @return
	 */
	public long getUnPrintStorageOrderCount(long storageId);

	/**
	 * 添加入库记录
	 * 
	 * @param record
	 * @return
	 */
	public int addOutStorageRecord(OutStorageRecord record);
	
	
	
	public String importDeliveryInfo(List<String[]> datas,List<Long> storageIds);
	
	public boolean updateDeliveryInfo(String childOrderCode, String deliverCompanyCode, String deliverSheetCode);
	
	public boolean ignoreDeliveryStatus(String childOrderCode, Long userId);
}
