package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.Sku;

public interface SkuDao {

	public Sku getById(long skuId);

	public List<Sku> get4copy2activity(Long collegeId);

	public List<Sku> getListByGoodsId(long goodsId);

	public List<Sku> getListByGoodsIdSkuStatus(long goodsId, int skuStatus);

	public List<Long> getCollegeIdsByBgSkuId(long bgSkuId, int isLdc);

	/**
	 * 每个字段值为-1是，该字段无效
	 * 
	 * @param bgGoodsId
	 * @param collegeId
	 * @return
	 */
	public List<Sku> getListByBgGoodsIdCollegeId(long bgGoodsId, long collegeId);

	public Sku getBybgSkuIdAndCollegeIdAndGoodsId(long bgSkuId, long collegeId, long goodsId, int distributeType);

	public int deleteById(long skuId);

	public int deleteByGoodsId(List<Long> goodsIds);

	/**
	 * 删除，每个字段值为-1是，该字段无效
	 * 
	 * @param bgGoodsId
	 * @param bgSkuId
	 * @param collegeId
	 * @param isLdc
	 * @return
	 */
	public int deleteByBgGoodsIdCollegeIdIsLdc(long bgGoodsId, long bgSkuId, long collegeId, int isLdc);

	public int updateByObject(Sku sku);

	public long insert(Sku sku);

	public int updatePrice(long skuId, long originPrice, long wapPrice, long appPrice);

	public int updatePriceByBgSkuId(long skuId, long originPrice, long wapPrice, long appPrice);

	public int updateStock(long skuId, long stock, int status);

	public int updateStockByGoodsId(long goodsId, long stock, int status, int isLdc);

	// public int updateRdcStatusByBgGoodsIdCollegeId(long bgGoodsId, long
	// collegeId, int status);

	// public int updateLdcStatusByBgGoodsIdCollegeId(long bgGoodsId, long
	// collegeId, int status);
}
