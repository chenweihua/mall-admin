package com.mall.admin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.delivery.DeliverCompanyService;
import com.mall.admin.vo.delivery.DeliverCompany;

/**
 * 物流公司的应用缓存
 *
 */
public class DeliverCompanyConstant {
	
	/**
	 * Key是DeliverCompany的code
	 */
	private volatile static Map<String, DeliverCompany> deliverCompanyMap = new HashMap<String, DeliverCompany>();
	
	
	/**
	 * Key是DeliverCompany的name
	 */
	private volatile static Map<String, DeliverCompany> deliverCompanyNameMap = new HashMap<String, DeliverCompany>();
	
	
	private volatile static List<DeliverCompany> deliveryCompanyList = new ArrayList<DeliverCompany>();
	

	@Autowired
	private DeliverCompanyService deliverCompanyService;

	private DeliverCompanyConstant() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.info("init deliverCompany");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<String, DeliverCompany> tempDeliverCompanyMap = new HashMap<String, DeliverCompany>();
		Map<String, DeliverCompany> tempDeliverCompanyNameMap = new HashMap<String, DeliverCompany>();
		List<DeliverCompany> deliverCompanyList = deliverCompanyService.queryAllDeliverCompanys();
		if (!deliverCompanyList.isEmpty()) {
			for (DeliverCompany deliverCompany : deliverCompanyList) {
				tempDeliverCompanyMap.put(deliverCompany.getDeliverCompanyCode(), deliverCompany);
				tempDeliverCompanyNameMap.put(deliverCompany.getDeliverCompanyName(), deliverCompany);
			}
		}
		deliverCompanyMap = tempDeliverCompanyMap;
		deliverCompanyNameMap = tempDeliverCompanyNameMap;
		deliveryCompanyList = deliverCompanyList;
		
		
		LogConstant.mallLog.info("refresh " + (deliverCompanyList.isEmpty() ? 0 : deliverCompanyList.size()) + " deliverCompany");
	}

	/**
	 * @param key
	 * @return
	 */
	public static DeliverCompany getDeliverCompanyByCode(String code) {
		DeliverCompany deliverCompany = deliverCompanyMap.get(code);
		/*
		if(deliverCompany == null) {
			DeliverCompany noDeliverCompany = new DeliverCompany();
			noDeliverCompany.setDeliverCompanyCode("空");
			noDeliverCompany.setDeliverCompanyName("无");
			return noDeliverCompany;
		}
		*/
		return deliverCompany;
	}

	
	public static Map<String, DeliverCompany> getDeliverCompanysMap() {
		return deliverCompanyMap;
	}
	
	public static String getDeliverCompanyByName(String name) {
		DeliverCompany deliverCompany = deliverCompanyNameMap.get(name);
		if(deliverCompany == null) {
			return null;
		} else {
			return deliverCompany.getDeliverCompanyCode();
		}
	}
	
	public static List<DeliverCompany> getDeliverCompanyList() {
		return deliveryCompanyList;
	}

	
}
