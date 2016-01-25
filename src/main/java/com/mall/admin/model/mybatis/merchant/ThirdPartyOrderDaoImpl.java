package com.mall.admin.model.mybatis.merchant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.merchant.ThirdPartyOrderDao;
import com.mall.admin.model.mybatis.base.BaseDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.util.DateUtil;
import com.mall.admin.vo.merchant.MerchantSummaryOrder;
import com.mall.admin.vo.merchant.ThirdPartyOrder;

@Repository
public class ThirdPartyOrderDaoImpl extends BaseDaoImpl implements ThirdPartyOrderDao {

	@Override
	public List<ThirdPartyOrder> getOrderList(String orderCode, List<String> merchantNoList, String beginTime,
			String endTime, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		// if (merchantNoList != null && merchantNoList.size() > 0) {
		map.put("merchantNoList", merchantNoList);
		// }
		map.put("beginTime", DateUtil.formatTimestampToDate(beginTime));
		map.put("endTime", DateUtil.formatTimestampToDate(endTime));
		if (orderCode != null && orderCode.trim().length() > 0) {
			map.put("orderCode", orderCode);
		}
		List<ThirdPartyOrder> orderList = selectPaginationList("ThirdPartyOrder.selectListByPage", map,
				paginationInfo);
		return orderList;
	}

	@Override
	public List<ThirdPartyOrder> getOrderList(String orderCode, List<String> merchantNoList, String beginTime,
			String endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if (merchantNoList != null && merchantNoList.size() > 0) {
			map.put("merchantNoList", merchantNoList);
		}
		map.put("beginTime", DateUtil.formatTimestampToDate(beginTime));
		map.put("endTime", DateUtil.formatTimestampToDate(endTime));
		if (orderCode != null && orderCode.trim().length() > 0) {
			map.put("orderCode", orderCode);
		}
		return this.getSqlSession().selectList("ThirdPartyOrder.selectOrderList", map);
	}

	@Override
	public List<MerchantSummaryOrder> summaryOrderInfo(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		Map<String, Date> map = new HashMap<String, Date>();
		map.put("beginTime", beginDate);
		map.put("endTime", endDate);
		return this.getSqlSession().selectList("ThirdPartyOrder.summeryOrder", map);
	}

	@Override
	public MerchantSummaryOrder querySummaryOrder(String merchantNo, Date summaryDate) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("recordDate", summaryDate);
		return this.getSqlSession().selectOne("ThirdPartyOrder.selectSummaryOrder", map);
	}

	@Override
	public int insertSummaryOrder(MerchantSummaryOrder summaryOrder) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("ThirdPartyOrder.insertSummaryOrder", summaryOrder);
	}

	@Override
	public int updateSummaryOrder(MerchantSummaryOrder summaryOrder) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("ThirdPartyOrder.updateSummaryOrder", summaryOrder);
	}

	@Override
	public List<MerchantSummaryOrder> querySummaryOrderByPage(List<String> merchantNoList, Date beginDate,
			Date endDate, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNoList", merchantNoList);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		List<MerchantSummaryOrder> summaryOrderList = selectPaginationList(
				"ThirdPartyOrder.selectSummaryOrderByPage", map, paginationInfo);
		return summaryOrderList;
	}

	@Override
	public List<MerchantSummaryOrder> querySummaryOrder(List<String> merchantNoList, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNoList", merchantNoList);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return this.getSqlSession().selectList("ThirdPartyOrder.selectAllSummaryOrder", map);
	}

	@Override
	public List<MerchantSummaryOrder> querySumSummaryOrderByPage(Date beginDate, Date endDate,
			PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		List<MerchantSummaryOrder> summaryOrderList = selectPaginationList(
				"ThirdPartyOrder.selectSumSummaryOrderByPage", map, paginationInfo);
		return summaryOrderList;
	}

	@Override
	public List<MerchantSummaryOrder> querySumSummaryOrder(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return this.getSqlSession().selectList("ThirdPartyOrder.selectSumSummaryOrder", map);
	}

}
