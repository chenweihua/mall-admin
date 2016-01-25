package com.mall.admin.model.dao.sale;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.sale.SaleInCollege;
import com.mall.admin.vo.sale.SaleInStorage;
import com.mall.admin.vo.sale.SaleSkuInfo;

public interface SaleDao {

	/**
	 * 获得rdc仓下商品的售卖信息
	 * 
	 * @return
	 */
	public List<SaleInStorage> getRdcStorageGoodsInfo(Map paramMap, PaginationInfo paginationInfo);

	/**
	 * 获得LDC仓下商品的售卖信息
	 * 
	 * @param storage_id
	 * @param goods_name
	 * @param status
	 * @param category_id
	 * @return
	 */
	public List<SaleInStorage> getLdcStorageGoodsInfo(Map paramMap, PaginationInfo paginationInfo);
	
	public List<SaleInStorage> getVmStorageGoodsInfo(Map paramMap, PaginationInfo paginationInfo);

	/**
	 * 获得商品在某RDC仓下学校的售卖情况
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SaleInCollege> getRdcStorageGoodsInCollege(long storageId, long collegeId, long bg_goods_id);

	/**
	 * 获得商品在某LDC仓下学校的售卖情况
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SaleInCollege> getLdcStorageGoodsInCollege(long storageId, long collegeId, long bg_goods_id);
	
	public List<SaleInCollege> getVmStorageGoodsInCollege(long storageId, long collegeId, long bg_goods_id);

	/**
	 * 获得单品和组合品商品的信息
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<SaleSkuInfo> getSkuInfoByGoodsId(long bggoodsId);

	/**
	 * 获得组合品商品的信息
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<SaleSkuInfo> getMoreSkuInfoByGoodsId(long bggoodsId);

	/**
	 * 查询商品库存
	 * 
	 * @param bg_goods_id
	 * @param storageId
	 * @return
	 */
	public List<Integer> queryStorageStock(long bg_goods_id, long storageId);

}
