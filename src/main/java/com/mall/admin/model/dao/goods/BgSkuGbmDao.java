package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgSkuGbm;

public interface BgSkuGbmDao {

	public BgSkuGbm getById(long id);
	
	public BgSkuGbm getByBgSkuIdWmsGoodsId(long bgSkuId,long wmsGoodsId);

	public BgSkuGbm getByBgWmsGoodsIdNum(long wmsGoodsId,long num);

	public int deleteById(long skuGbmId);

	public int updateByObject(BgSkuGbm bgSkuGbm);

	public int updateByBgSkuIdWmsGoodsId(BgSkuGbm bgSkuGbm);

	public long insert(BgSkuGbm bgSkuGbm);

	/**
	 * 根据单品（组合品）商品id获得供应链商品id
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getWmsGoodsIdByBgGoodsId(long bgGoodsId);
	
	public List<BgSkuGbm> getBgSkuGbmByBgGoodsId(long bgGoodsId);

	/**
	 * 获得聚合商品对应的供应链商品id
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getWmsGoodsIdByBgPolyGoodsId(long bgGoodsId);

	public List<Long> getWmsGoodsIdByBgSkuId(long bgSkuId);
	
	public List<BgSkuGbm> getByBgSkuId(long bgSkuId);

}
