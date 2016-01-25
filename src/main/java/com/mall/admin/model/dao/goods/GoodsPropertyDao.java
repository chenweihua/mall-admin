package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.GoodsProperty;

public interface GoodsPropertyDao {

	public GoodsProperty getById(long goodsPropertyId);
	
	public GoodsProperty getByBgGoodsIdValueId(long bgGoodsId,long propertyValueId);

	public int deleteById(long goodsPropertyId);

	public int updateByObject(GoodsProperty goodsProperty);
	
	public int updateByBgGoodsIdVauleId(GoodsProperty goodsProperty);

	public long insert(GoodsProperty goodsProperty);

	public List<GoodsProperty> getByBgGoodsId(long bgGoodsId);
	
}
