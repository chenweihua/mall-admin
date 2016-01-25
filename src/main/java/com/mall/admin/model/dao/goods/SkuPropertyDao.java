package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.SkuProperty;

public interface SkuPropertyDao {

	public SkuProperty getById(long skuPropertyId);
	/**
	 * 主要查询聚合品
	 * @param bgSkuId
	 * @return
	 */
	public Long getBgGoodsIdBybgSkuId(long bgSkuId);
	
	public SkuProperty getByBgSkuIdValueId(long bgGoodsId,long bgSkuId,long propertyValueId);

	public int deleteById(long skuPropertyId);

	public int updateByObject(SkuProperty skuProperty);

	public long insert(SkuProperty skuProperty);

	public List<Long> getBgSkuByBgGoodsId(long bgGoodsId);
	
	public List<Long> getBgGoodsIdsByBgSkuId(long bgSkuId);

	public List<SkuProperty> getByBgGoodsId(long bgGoodsId);

}
