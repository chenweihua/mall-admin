package com.mall.admin.vo.order.customer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.pay.RefundInfoBean;

public class CustomerOrder{
	/**订单号*/
	public String orderCode;
	/**用户名*/
	public String userName;
	/**联系电话*/
	public String phone;
	/**收货地址*/
	public String address;
	/**订单总金额*/
	public long totlePay;
	/**在线支付金额*/
	public long onlinePay;
	/**促销免减*/
	public long sellerCoupon;
	/**首单免减*/
	public long orderCoupon;
	/**优惠金额*/
	public long cashCoupon;
	/**支付方式*/
	public String payType;
	/**配送类型*/
	public String deliveryType;
	/**P2P配送开始时间*/
	public String deliveryStartTime;
	/**P2P配送结束时间*/
	public String deliveryEndTime;
	/**订单状态*/
	public String status;
	/**订单创建时间*/
	public String createTime;
	/**是否有退款*/
	public String withDraw;
	
	public List<CustormerOrderDetail> orderDetails;
        public List<RefundInfoBean> refundInfos;
	public static CustomerOrder init(ChildOrder order) {
		
		String payTypeStr= order.getOnlinePayType() + "";// order.onlinePayType+"";
		String deliveryTypeStr= order.getDeliveryType() + "";// order.deliveryType+"";
		String statusStr = order.getOrderStatus() + "";//order.status+"";
		String payType="未知";
		if("1".equals(payTypeStr)){
			payType = "微信";
		}else if("2".equals(payTypeStr)){
			payType="支付宝";
		}
		String deliveryType="未知";
		if("1".equals(deliveryTypeStr)){
			deliveryType="P2P";
		}else if("2".equals(deliveryTypeStr)){
			deliveryType="自提";
		}

//		订单状态，1:未支付，2:因sku没有库存或sku超过秒杀时间导致的订单创建失败，3:订单超时，4:支付中，5:支付成功',
		String status="未知";
		if("1".equals(statusStr)){
			status="未支付";
		}else if("2".equals(statusStr)){
			status="订单无效";
		}else if("3".equals(statusStr)){
			status="订单超时";
		}else if("4".equals(statusStr)){
			status="支付中";
		}else if("5".equals(statusStr)){
			status="支付成功";
		}/*else if("6".equals(statusStr)){
			status="支付超时";
		}*/
		
		CustomerOrder corder = new CustomerOrder();
		corder.orderCode= order.getChildOrderCode();//order.orderCode;
		corder.userName= order.getReceiverName();// order.receiverName;
		/**联系电话*/
		corder.phone= order.getReceiverPhone();// order.receiverPhone;
		/**收货地址*/
		corder.address= order.getDeliveryAddress(); //getReceiverAddress();// order.receiverAddress;
		/**订单总金额*/
		corder.totlePay= order.getTotalPay();// order.totalPay;
		/**在线支付金额*/
		corder.onlinePay= order.getOnlinePay();// order.onlinePay;
		/**促销免减*/
		corder.sellerCoupon= order.getFullSub();// order.sellerCoupon;
		/**首单免减*/
		corder.orderCoupon= order.getFirstSub();// order.orderCoupon;
		/**优惠金额*/
//		corder.cashCoupon=order.cashcoupon;
		/**支付方式*/
		corder.payType=payType;
		/**配送类型*/
		corder.deliveryType=deliveryType;
		/**P2P配送开始时间*/
		corder.deliveryStartTime= _.formatDate(order.getDeliveryBeginTime());// format.format(new Date(order.p2pDeliveryStartTime));
		/**P2P配送结束时间*/
		corder.deliveryEndTime= _.formatDate(order.getDeliveryEndTime());// format.format(new Date(order.p2pDeliveryEndTime));
		/**订单状态*/
		corder.status=status;
		/**订单创建时间*/
		corder.createTime= _.formatDate(order.getCreateTime());// format.format(new Date(order.createTime));

		return corder;
	}
        
	public static void main(String[] args) {
		String data ="{\"detailList\":[{\"orderDetailId\":\"6188716\",\"withdrawNum\":\"1\"},{\"orderDetailId\":\"6188717\",\"withdrawNum\":\"1\"},{\"orderDetailId\":\"6188718\",\"withdrawNum\":\"1\"}],\"reason\":\"提客服退款\"}:";
		JsonNode jsonNode;
		try {
			jsonNode = JsonUtil.parse(data);
		        String reason = jsonNode.findValue("reason").textValue();
		        Iterator<JsonNode> iterator = jsonNode.findValue("detailList").elements();
		        while (iterator.hasNext()) {
		        	JsonNode elem = iterator.next();
		        	long detailId = elem.findValue("orderDetailId").asLong();
		        	long withdrawNum = elem.findValue("withdrawNum").asLong();
		        	System.out.println("detailId:"+detailId);
		        	System.out.println("withdrawNum:"+withdrawNum);
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
