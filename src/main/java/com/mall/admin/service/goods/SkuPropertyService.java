package com.mall.admin.service.goods;

import java.util.List;

public interface SkuPropertyService {
	/**
	 * 获取bgGoodsId对应的BgSkuIds
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getBgSkuByBgGoodsId(long bgGoodsId);
}
