package com.mall.admin.tool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imxiaomai.cb.kafka.consumer.ConsumerLogic;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.synchwmsgoods.SynchWmsGoodsBean;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.vo.wms.WmsGoods;

/**
 * 同步供应链商品信息
 * 
 * @author Administrator
 *
 */
public class SynchWmsGoodsInfoLogic implements ConsumerLogic {
	public static final Gson gson = new Gson();

	@Autowired
	private WmsGoodsService wmsGoodsService;


	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.debug("init SynchWmsGoodsInfoLogic");
	}

		public boolean consume(String message) throws Exception {

		LogConstant.mallLog.error("Demo1ConsumerLogic consume succ,message[" + message + "]");

		List<SynchWmsGoodsBean> synchBeanList = gson.fromJson(message,
				new TypeToken<List<SynchWmsGoodsBean>>() {
				}.getType());

		for (SynchWmsGoodsBean synchBean : synchBeanList) {
			List<WmsGoods> wmsGoodsList = WmsGoods.initWmsGoodsBySynchBean(synchBean);
			for (WmsGoods wmsGoods : wmsGoodsList) {
				wmsGoodsService.synchWmsGoods(wmsGoods);
			}
		}

		return true;
	}

}
