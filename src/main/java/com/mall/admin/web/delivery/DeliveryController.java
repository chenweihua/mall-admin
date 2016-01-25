package com.mall.admin.web.delivery;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.delivery.DeliverCompanyService;
import com.mall.admin.service.delivery.DeliverInfoService;
import com.mall.admin.service.delivery.DeliveryService;
import com.mall.admin.vo.delivery.Delivery;

@Controller
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {
	
	private static final String LOGGER_PREFIX = "DELIVERY_INFO_INSIDE_CALL:";
	
	private static final String SIGN_PWD_PREFIX = "imxiaomaiInside"; //验签字段为该固定前缀+deliverySheetCode
	
	@Autowired
	private DeliverCompanyService deliverCompanyService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private DeliverInfoService deliveryInfoService;
	
	private static final Logger logger = LogConstant.mallLog;

	
	
	
	@RequestMapping("/query")
	@ResponseBody
	public Object queryDelivery(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=false) String deliveryCompanyCode,@RequestParam(required=false) String deliverySheetCode,
			@RequestParam(required=false) String source,@RequestParam(required=false) String sign)  {
		
		logger.info(LOGGER_PREFIX + "receive a call, deliveryCompanCode {} , deliverySheetCode {} , source {} ,sign {}",
						deliveryCompanyCode, deliverySheetCode,source, sign);
		
		try {
			checkArgument(StringUtils.isNotEmpty(deliveryCompanyCode), "deliveryCompanyCode不能为空");
			checkArgument(StringUtils.isNotEmpty(deliverySheetCode), "deliverySheetCode不能为空");
			checkArgument(StringUtils.isNotEmpty(source), "source不能为空");
			checkArgument(StringUtils.isNotEmpty(sign), "sign不能为空");
			
			//验签
			String signComputed = DigestUtils.md5DigestAsHex((deliveryCompanyCode + deliverySheetCode + SIGN_PWD_PREFIX + deliverySheetCode).getBytes("UTF-8"));
			
			if(!signComputed.equalsIgnoreCase(sign)) {
				logger.error(LOGGER_PREFIX + "验签失败，我们计算的是：" + signComputed + ",对方传递的是：" + sign + ",报文：" + (deliveryCompanyCode + deliverySheetCode + SIGN_PWD_PREFIX + deliverySheetCode));
				return buildJson(1,"验签失败");
			}
			
			
			Delivery queryDelivery = deliveryService.getDeliveryById(deliveryCompanyCode, deliverySheetCode);
			
			if(queryDelivery == null) {  //分库中无记录，说明没有注册过
				boolean registerSuccess = deliveryInfoService.registerDeliverInfo(deliveryCompanyCode, deliverySheetCode);
				
				//添加物流信息记录
				if(registerSuccess) {
					try {
						Delivery delivery = new Delivery();
						delivery.setDeliveryCompanyCode(deliveryCompanyCode);
						delivery.setDeliverySheetCode(deliverySheetCode);
						delivery.setSource(Delivery.SOURCE_KUAIDI100);
						delivery.setStatus(Delivery.STATUS_NO_INFO);
						delivery.setCreateTime(new Date());
						deliveryService.addDelivery(delivery);
					} catch(DuplicateKeyException ex) {
						//重复主键，说明有多个输入源输入了相同的物流公司和物流编码，或是用户重复保存,系统忽略
						//do nothing
					}
				}
			}
			
			String deliveryInfo = queryDelivery == null ? "" : (queryDelivery.getDeliveryInfo() == null ? "" : queryDelivery.getDeliveryInfo());
			
			return buildJson(0, "", deliveryInfo);
		
		} catch(Exception ex) {
			logger.error(LOGGER_PREFIX + "在处理queryDelivery中发生异常",ex);
			return buildJson(1,ex.getMessage());
		}
	}
	
	
}
