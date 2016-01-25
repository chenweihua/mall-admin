package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.GoodsProperty;

public interface GoodsPropertyService {
	public GoodsProperty getById(long goodsPropertyId);

	public int deleteById(long goodsPropertyId);

	public int updateByObject(GoodsProperty goodsProperty);

	public long insert(GoodsProperty goodsProperty);

	public List<GoodsProperty> getByBgGoodsId(long bgGoodsId);
	
}
