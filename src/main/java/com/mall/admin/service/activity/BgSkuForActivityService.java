package com.mall.admin.service.activity;

import java.util.List;

import com.mall.admin.vo.activity.BgSkuForActivity;

public interface BgSkuForActivityService {

	/**
	 * 根据活动模板goods 的id查找对应的sku
	 * 
	 * @param activityBgGoodsId
	 * @return
	 */
	public List<BgSkuForActivity> getListByActivityBgGoodsId(long activityBgGoodsId);
	
	public List<BgSkuForActivity> getListByActivityBgGoodsIdAndGoodsType(long activityBgGoodsId,int goodsType);

	/**
	 * 添加活动后台模板sku
	 * 
	 * @param bean
	 * @return
	 */
	public int insert(BgSkuForActivity bean);

	/**
	 * 更新活动后台模板sku
	 * 
	 * @param bean
	 * @return
	 */
	public int update(BgSkuForActivity bean);

	/**
	 * 更新商品的原价，活动价和限售数量
	 * 
	 * @param orginPrice
	 * @param activityPrice
	 * @param stock
	 * @param activityBgSkuId
	 * @return
	 */
	public int updateActivityBgSku(long originPrice, long activityPrice, int stock, long activityBgSkuId);
}
