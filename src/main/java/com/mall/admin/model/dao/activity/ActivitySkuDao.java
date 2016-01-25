package com.mall.admin.model.dao.activity;

import java.util.List;

import com.mall.admin.vo.activity.ActivitySku;

public interface ActivitySkuDao {

	public int insert(ActivitySku bean);

	public int update(ActivitySku bean);

	/**
	 * 更新商品sku的售卖信息
	 * 
	 * @param originPrice
	 *                商品原价
	 * @param activityPrice
	 *                活动价
	 * @param stock
	 *                限售库存
	 * @param activityBgSkuId
	 *                活动模板sku的id
	 * @return
	 */
	public int update(long originPrice, long activityPrice, int stock, long activityBgSkuId);

	/**
	 * 查询学校下的模板sku
	 * 
	 * @param activityBgSkuId
	 * @param collegeId
	 * @return
	 */
	public ActivitySku getActivitySku(long activityBgSkuId, long collegeId);

	/**
	 * 查询满足条件的ActivitySku列表如果collegeId=0
	 * 则表示查询所有活动sku模板id是activityBgSkuId的ActivitySku
	 * 
	 * @param activityBgSkuId
	 * @param collegeId
	 * @return
	 */
	public List<ActivitySku> getActivitiSkuList(long activityBgSkuId, long collegeId);

}
