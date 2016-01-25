package com.mall.admin.vo.order.customer;

import java.util.List;

/**
 * 客服系统传输对象
 * @author Administrator
 *
 */
public class OrderListBean {
	
	public String code;
	public String message;

	public List<CustomerOrder> orderList;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CustomerOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<CustomerOrder> orderList) {
		this.orderList = orderList;
	}
}
