package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgGoodsRegion;

public interface BgGoodsRegionDao {

	public BgGoodsRegion getById(long bgGoodsRegionId);

	public List<Long> getStorageIdsByBgGoodsId(long bgGoodsId);

	public int deleteById(long bgGoodsRegionId);

	public int deleteByBgGoodsId(long bgGoodsId);

	public int updateByObject(BgGoodsRegion bgGoodsRegion);

	public long insert(BgGoodsRegion bgGoodsRegion);

	public long insertOrUpdate(BgGoodsRegion bgGoodsRegion, boolean isOld);

	public boolean isExist(long bgGoodsId, long regionId, int regionType);

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

	public List<BgGoodsRegion> getByRegionId(long regionId, int regionType);

	public int update(long regionId, long bgGoodsId, int regionType, long originPrice, long wapPrice,
			long appPrice, long maxNum);

	public int batchUpdate(long bgGoodsId, long originPrice, long wapPrice, long appPrice, long maxNum);

	public int updateWeightStockStatus(long bgGoodsId, long regionId, int regionType, long weight, long stock,
			int status);

	public int updateStatus(long bgGoodsId, long regionId, int regionType, int status);
}
