package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgSku;

public interface BgSkuService {

	public long insert(BgSku bgSku);
	
	public BgSku getBgSkuById(Long bgSkuId);

	/**
	 * 单品和组合商品，可以利用该方法获取
	 * @param bgGoodsId
	 * @return
	 */
	public BgSku getByBgGoodsId(long bgGoodsId);
	/**
	 * 支持所有类型商品
	 * @param bgGoodsId
	 * @param type，1单品；2组合品；3聚合品
	 * @return
	 */
	
	public List<BgSku> getByGoodsId(long bgGoodsId,int type);
}
