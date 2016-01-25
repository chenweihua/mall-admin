package com.mall.admin.service.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.merchant.ThirdPartyOrderDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.merchant.ThirdPartyOrderService;
import com.mall.admin.vo.merchant.MerchantSummaryOrder;
import com.mall.admin.vo.merchant.ThirdPartyOrder;

@Service
public class ThirdPartyOrderServiceImpl implements ThirdPartyOrderService {

	@Autowired
	ThirdPartyOrderDao thirdPartyOrderDao;

	@Override
	public List<ThirdPartyOrder> getThirdParyOrderList(String orderCode, List<String> merchantNoList,
			String beginTime, String endTime, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.getOrderList(orderCode, merchantNoList, beginTime, endTime, paginationInfo);
	}

	@Override
	public List<ThirdPartyOrder> getThirdParyOrderList(String orderCode, List<String> merchantNoList,
			String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.getOrderList(orderCode, merchantNoList, beginTime, endTime);
	}

	@Override
	public List<MerchantSummaryOrder> summaryOrderInfo(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.summaryOrderInfo(beginDate, endDate);
	}

	@Override
	public MerchantSummaryOrder querySummaryOrder(String merchantNo, Date summaryDate) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.querySummaryOrder(merchantNo, summaryDate);
	}

	@Override
	public int insertSummaryOrder(MerchantSummaryOrder summaryOrder) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.insertSummaryOrder(summaryOrder);
	}

	@Override
	public int updateSummaryOrder(MerchantSummaryOrder summaryOrder) {

		return thirdPartyOrderDao.updateSummaryOrder(summaryOrder);
	}

	@Override
	public List<MerchantSummaryOrder> querySummaryOrderByPage(List<String> merchantNoList, Date beginDate,
			Date endDate, PaginationInfo pageinationInfo) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.querySummaryOrderByPage(merchantNoList, beginDate, endDate, pageinationInfo);
	}

	@Override
	public List<MerchantSummaryOrder> querySummaryOrder(List<String> merchantNoList, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.querySummaryOrder(merchantNoList, beginDate, endDate);
	}

	@Override
	public List<MerchantSummaryOrder> querySumSummaryOrderByPage(Date beginDate, Date endDate,
			PaginationInfo pageinationInfo) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.querySumSummaryOrderByPage(beginDate, endDate, pageinationInfo);
	}

	@Override
	public List<MerchantSummaryOrder> querySumSummaryOrder(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return thirdPartyOrderDao.querySumSummaryOrder(beginDate, endDate);
	}

}
