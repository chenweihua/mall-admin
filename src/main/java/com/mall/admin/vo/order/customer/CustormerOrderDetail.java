package com.mall.admin.vo.order.customer;

import java.util.ArrayList;
import java.util.List;

import com.mall.admin.util._;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Withdraw;

public class CustormerOrderDetail {
	public long id;
	public String skuName;
	public long unitPrice;
	public String skuSubName;
	public long skuNum;
	public long totalPay;
	public String createTime;
	public String withdrawNum="0";
	public long withdrawTotalMoney=0;
	public String withDrawDate="";
	public String reason="";
	
	public static List<CustormerOrderDetail> init(List<ChildOrderDetail> orderDetails,
			List<Withdraw> withdraws) {
		List<CustormerOrderDetail> cdetailList = new ArrayList<CustormerOrderDetail>();
		if(orderDetails!=null&&orderDetails.size()>0){
			for(ChildOrderDetail detail : orderDetails){
				CustormerOrderDetail cdetail=new CustormerOrderDetail();
				cdetail.id= detail.getChildOrderDetailId();// detail.id;
				cdetail.skuName= detail.getSkuName();// detail.skuName;
				cdetail.unitPrice = detail.getSkuPrice();// detail.unitPrice;
				cdetail.skuSubName= detail.getSkuSubName();// detail.skuSubName;
				cdetail.totalPay= detail.getSkuNum()*detail.getSkuPrice();// detail.totalPay;
				cdetail.createTime= _.formatDate(detail.getCreateTime());// format.format(new Date(detail.createTime));
				cdetail.skuNum= detail.getSkuNum();// detail.skuNum;
				long withdrawNum=0;
				long withdrawTotalMoney=0;
				if(withdraws!=null&&withdraws.size()>0){
					for(Withdraw withdraw:withdraws){
						if(detail.getChildOrderDetailId()==withdraw.getOrderDetailId()){
							withdrawNum+=withdraw.getSkuWithdrawNum();//skuWithdrawNum;
							withdrawTotalMoney+= withdraw.getSkuWithdrawNum()*withdraw.getSkuUnitPrice();
							cdetail.withDrawDate = _.formatDate(withdraw.getCreatetime());// format.format(new Date(withdraw.createTime));
							cdetail.reason=withdraw.getReason();
						}
					}
				}
				cdetail.withdrawNum=withdrawNum+"";
				cdetail.withdrawTotalMoney=withdrawTotalMoney;
				cdetailList.add(cdetail);
			}
		}
		return cdetailList;
	}
}
