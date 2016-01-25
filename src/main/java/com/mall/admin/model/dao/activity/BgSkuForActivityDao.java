package com.mall.admin.model.dao.activity;

import java.util.List;

import com.mall.admin.vo.activity.BgSkuForActivity;

public interface BgSkuForActivityDao {

	public List<BgSkuForActivity> getBgSkuByBgGoodsId(long activityBgGoodsId);
	
	public List<BgSkuForActivity> getBgSkuAndGoodsInfoByBgGoodsId(long activityBgGoodsId);

	public int insert(BgSkuForActivity bean);

	/**
	 * 更新售卖的活动商品sku的信息
	 * 
	 * @param originPrice
	 *                商品原价
	 * @param activityPrice
	 *                活动价钱
	 * @param stock
	 *                库存
	 * @param activityBgSkuId
	 *                活动商品模板sku的id
	 * @return
	 */
	public int update(long originPrice, long activityPrice, int stock, long activityBgSkuId);

	/***
	 * 根据活动sku的模板id和goodsid查询是否有对应的数据
	 * 
	 * @param bg_sku_id
	 * @param activity_bg_goods_id
	 * @return
	 */
	public BgSkuForActivity getBgSkuForActivity(long bg_sku_id, long activity_bg_goods_id);

	/**
	 * 更新活动商品sku的状态
	 * 
	 * @param activityBgSkuId
	 * @param status
	 *                0:可用，1:不可用
	 */
	public int updateIsDel(long activityBgSkuId, int status);

}
