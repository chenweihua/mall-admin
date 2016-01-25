package com.mall.admin.task;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.merchant.ThirdPartyOrderService;
import com.mall.admin.util.DateUtil;
import com.mall.admin.vo.merchant.MerchantSummaryOrder;

@Component
public class SummaryMerchantOrder {

	private static final Logger log = LogConstant.mallLog;

	@Autowired
	ThirdPartyOrderService thirdPartyOrderService;

	@Scheduled(cron = "0 30 * * * ?")
	public void summeray() {
		log.info("汇总商超订单开始===========");

		try {
			List<MerchantSummaryOrder> summayOrderInfoList = thirdPartyOrderService.summaryOrderInfo(
					DateUtil.getDayOfFirst(), DateUtil.getDayOfEnd());
			for (MerchantSummaryOrder summaryOrderInfo : summayOrderInfoList) {
				System.out.println("summaryOrderInfo:" + summaryOrderInfo);
				MerchantSummaryOrder summaryOrderInfoTemp = thirdPartyOrderService.querySummaryOrder(
						summaryOrderInfo.getMerchantNo(), DateUtil.getDayOfFirst());
				if (summaryOrderInfoTemp == null) {
					log.info("添加汇总订单信息");
					summaryOrderInfo.setRecordDate(new Timestamp(DateUtil.getDayOfFirst().getTime()));
					thirdPartyOrderService.insertSummaryOrder(summaryOrderInfo);
				} else {
					log.info("更新汇总订单信息");
					summaryOrderInfo.setRecordDate(new Timestamp(DateUtil.getDayOfFirst().getTime()));
					thirdPartyOrderService.updateSummaryOrder(summaryOrderInfo);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("汇总商超订单结束===========");
	}
}
