package com.mall.admin.model.mybatis.delivery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.delivery.DeliveryDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.delivery.Delivery;

@Repository
public class DeliveryDaoImpl extends BaseMallDaoImpl implements DeliveryDao {

	public int insert(Delivery delivery) {
		return this.getMySqlSession(delivery.getDeliveryCompanyCode(),delivery.getDeliverySheetCode()).insert("Delivery.insertDelivery",delivery);
	}
	
	public int update(Delivery delivery) {
		return this.getMySqlSession(delivery.getDeliveryCompanyCode(),delivery.getDeliverySheetCode()).update("Delivery.updateDeliveryByPrimaryKey",delivery);
	}
	
	public List<Delivery> query(Delivery delivery) {
		return this.getMySqlSession(delivery.getDeliveryCompanyCode(),delivery.getDeliverySheetCode()).selectList("Delivery.selectDelivery", delivery);
	}
	
	
	public Delivery queryById(String deliveryCompanyCode, String deliverySheetCode) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("deliveryCompanyCode", deliveryCompanyCode);
		param.put("deliverySheetCode", deliverySheetCode);
		return this.getMySqlSession(deliveryCompanyCode,deliverySheetCode).selectOne("Delivery.selectDeliveryByPrimaryKey", param);
	}
	

}