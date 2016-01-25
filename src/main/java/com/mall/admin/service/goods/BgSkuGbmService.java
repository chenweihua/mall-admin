package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgSkuGbm;

public interface BgSkuGbmService {

	public long insert(BgSkuGbm bgSkuGbm);

	/**
	 * 获得单品（组合品）商品对应的供应链商品id
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getWmsGoodsIdByBgGoodsId(long bgGoodsId);
	
	public List<BgSkuGbm> getBgSkuGbmByBgGoodsId(long bgGoodsId);
	
	public List<Long> getWmsGoodsIdByBgSkuId(long bgSkuId);

	/**
	 * 获得聚合品商品对应的供应链商品id
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getWmsGoodsIdByBgPolyGoodsId(long bgGoodsId);

}
