package com.mall.admin.model.dao.goods;

import com.mall.admin.vo.goods.BgSku;

public interface BgSkuDao {

	public BgSku getById(long bgSkuId);
	
	public boolean isExist(long bgGoodsId);

	public BgSku getByBgGoodsId(long bgGoodsId);

	public int deleteById(long id);

	public int updateByObject(BgSku bgSku);

	public int updateByObjectBgGoodsId(BgSku bgSku);
	
	public long insert(BgSku bgSku);
}
