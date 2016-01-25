package com.mall.admin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;
import com.mall.admin.vo.order.Withdraw;
import com.mall.admin.vo.order.customer.RefundResult;

public class CustomerUtil {
	
	public static RefundResult checkRequestInfo(HttpServletRequest request){
		RefundResult result = new RefundResult();
		String refundstr = request.getParameter("refundstr");
		refundstr=URLDecoder.decode(refundstr);
		    String refunduser = request.getParameter("refunduser");
		    String requesttime=request.getParameter("requesttime");
		    String sign=request.getParameter("sign");
		    try {
				String mysign = TokenUtils.getCustomerSign("refundstr="+refundstr+"&refunduser="+refunduser+"&requesttime="+requesttime);
				if(!mysign.equalsIgnoreCase(sign)){
					result.code="-1";
					result.message="签名错误~";
					return result;
				}
			} catch (UnsupportedEncodingException e1) {
				result.code="-1";
				result.message="计算签名异常~";
				return result;
			}
		    
		    if(refunduser==null||refunduser.trim().length()<1){
			    result.code="1";
			    result.message="客服名称为空~";
			    return result;
		    }
		    if(refundstr==null||refundstr.trim().length()<1){
			    result.code="1";
			    result.message="退款信息为空~";
			    return result;
		    }
		    result.code="0";
		    result.message="检查成功~";
		    return result;
	}

	public static RefundResult checkRefundInfo(List<Pair<Long,Long>> detailList,String withDrawDateStr) throws SQLException{
//		RefundResult result = new RefundResult();
//    		Order order = null;
//    		long orderId=0;
//    		//订单检查
//    		for (int i = 0; i < detailList.size(); i++) {
//    			long orderDetailId = detailList.get(i).getLeft();
//    			long withdrawNum = detailList.get(i).getRight();
//    			//Withdraw withdraw = new Withdraw();
//    			ChildOrderDetail orderDetail = OrderDetail.getById(orderDetailId);
//    			if(orderDetail==null){
//    				result.code="1";
//    				result.message="订单明细"+orderDetailId+"不存在~";
//    				return result;
//    			}
//    			if(order==null){
//    				order = Order.getById(orderDetail.orderId);
//    				if(order==null){
//    					result.code="1";
//    	    				result.message="订单"+orderDetail.orderId+"不存在~";
//    	    				return result;
//    				}
//    				if (order.onlinePayId.isEmpty() || order.onlinePayId.length() == 0) {
////    					return asMap("status", "error", "msg", "订单还木有交易id，再等等等吧骚年");
//    					result.code="1";
//    	    				result.message="订单"+orderDetail.skuName+"还木有交易id，再等等等吧~";
//    	    				return result;
//    				}
//    				orderId=order.id;
//    			}else{
//    				if(orderId!=orderDetail.orderId){
//    					result.code="1";
//    	    				result.message="退款记录中的明细不在同一个订单中~";
//    	    				return result;
//    				}
//    			}
//    			List<Withdraw> withdrawList = Withdraw.getListByOrderDetailId(orderDetailId);
//    			long sukNum=0;
//    			if(withdrawList!=null&&withdrawList.size()>0){
//    				for(Withdraw withdraw:withdrawList){
//    					sukNum+=withdraw.skuWithdrawNum;
//    				}
//    			}
////    			/**已退个数大于购买个数，无法继续退款*/
////    			if(orderDetail.skuNum<=sukNum){
////    				result.code="1";
////	    			result.message=orderDetail.skuName+"已退款个数"+sukNum+"，实际购买个数"+orderDetail.skuNum+"，无法继续退款~";
////	    			return result;
////    			}
//    			/**要退的个数大于购买个数*/
//    			if(orderDetail.skuNum<withdrawNum){
//    				result.code="1";
//	    			result.message=orderDetail.skuName+"请求退款个数"+withdrawNum+"大于购买个数"+orderDetail.skuNum+",无法继续退款~";
//	    			return result;
//    			}
////    			if(withdrawNum<=sukNum){
////    				result.code="1";
////	    			result.message=orderDetail.skuName+"请求退款个数"+withdrawNum+"不大于已退个数"+sukNum+"，不需要继续退款~";
////	    			return result;
////    			}
//    		}
//		result.code="0";
//		result.message="检查退款信息成功~";
//		return result;
		return null;//
	}
}
