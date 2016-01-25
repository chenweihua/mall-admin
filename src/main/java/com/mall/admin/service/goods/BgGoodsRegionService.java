package com.mall.admin.service.goods;

import com.mall.admin.vo.goods.BgGoodsRegion;

public interface BgGoodsRegionService {
	/**
	 * 查bgGoodsRegion
	 * 
	 * @param bgGoodsId
	 * @param regionId
	 * @param regionType
	 *                ：0全部仓；1仓；2城市
	 * @return
	 */
	public BgGoodsRegion getByBgGoodsIdAndRegionId(long bgGoodsId, long regionId, int regionType);

	public long insertOrUpdatePrice(BgGoodsRegion bgGoodsRegion);

	/**
	 * 更新价格
	 * 
	 * @param regionId
	 * @param bgGoodsId
	 * @param originPrice
	 * @param wapPrice
	 * @param appPrice
	 * @param maxNum
	 * @return
	 */
	public int update(long regionId, long bgGoodsId, int regionType, long originPrice, long wapPrice,
			long appPrice, long maxNum);

	/**
	 * 更新价格
	 * 
	 * @param regionId
	 * @param bgGoodsId
	 * @param originPrice
	 * @param wapPrice
	 * @param appPrice
	 * @param maxNum
	 * @return
	 */
	public int batchUpdate(long bgGoodsId, long originPrice, long wapPrice, long appPrice, long maxNum);

	/**
	 * 更新范围值
	 * 
	 * @param regionId
	 * @param bgGoodsId
	 * @param weight
	 * @param stock
	 * @param status
	 * @return -1：失败；1：成功；2：失败，表示需要先设置价格
	 */
	public int update(long regionId, long bgGoodsId, int regionType, long weight, long stock, int status);

	/**
	 * 
	 * @param regionId
	 * @param bgGoodsId
	 * @param weight
	 * @param stock
	 * @param status
	 * @return-1：失败；1：成功；2：失败，表示需要先设置价格
	 */
	public int update(long regionId, long bgGoodsId, int regionType, int status);

	public boolean addPrice2Dto(Object o, long bgGoodsId, int regionType);

}
