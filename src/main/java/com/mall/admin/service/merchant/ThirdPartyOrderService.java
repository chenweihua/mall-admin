package com.mall.admin.service.merchant;

import java.util.Date;
import java.util.List;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.merchant.MerchantSummaryOrder;
import com.mall.admin.vo.merchant.ThirdPartyOrder;

public interface ThirdPartyOrderService {

	public List<ThirdPartyOrder> getThirdParyOrderList(String orderCode, List<String> merchantNoList,
			String beginTime, String endTime, PaginationInfo paginationInfo);

	public List<ThirdPartyOrder> getThirdParyOrderList(String orderCode, List<String> merchantNoList,
			String beginTime, String endTime);

	/**
	 * 通过订单表计算汇总信息
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<MerchantSummaryOrder> summaryOrderInfo(Date beginDate, Date endDate);

	/**
	 * 根据商户编码和时间，查询当天的汇总订单 如果存在，则执行更新操作，如果不存在，则执行插入操作
	 * 
	 * @return
	 */
	public MerchantSummaryOrder querySummaryOrder(String merchantNo, Date summaryDate);

	/**
	 * 添加汇总的订单
	 * 
	 * @param summaryOrder
	 * @return
	 */
	public int insertSummaryOrder(MerchantSummaryOrder summaryOrder);

	/**
	 * 更新汇总的订单信息
	 * 
	 * @param summaryOrder
	 * @return
	 */
	public int updateSummaryOrder(MerchantSummaryOrder summaryOrder);

	/**
	 * 查询分页后的订单信息
	 * 
	 * @param merchantNoList
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<MerchantSummaryOrder> querySummaryOrderByPage(List<String> merchantNoList, Date beginDate,
			Date endDate, PaginationInfo pageinationInfo);

	public List<MerchantSummaryOrder> querySummaryOrder(List<String> merchantNoList, Date beginDate, Date endDate);

	public List<MerchantSummaryOrder> querySumSummaryOrderByPage(Date beginDate, Date endDate,
			PaginationInfo pageinationInfo);

	public List<MerchantSummaryOrder> querySumSummaryOrder(Date beginDate, Date endDate);
}
