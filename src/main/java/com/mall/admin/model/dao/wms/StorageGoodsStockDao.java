package com.mall.admin.model.dao.wms;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.wms.StorageGoodsStock;

public interface StorageGoodsStockDao {

	public List<StorageGoodsStock> getStorageGoodsStackList(PaginationInfo paginationInfo);

	public int insertWithStock(StorageGoodsStock storageGoodsStock);
	
	public int insert(StorageGoodsStock storageGoodsStock);

	public int update(StorageGoodsStock storageGoodsStock);

	public List<StorageGoodsStock> getStorageGoodsByQuery(long storage_Id, long wms_goods_id, int storagetype);

	/**
	 * 根据id查找是否存在商品
	 * 
	 * @param storage_goods_id
	 * @return
	 */
	public StorageGoodsStock getWmsGoodsById(Long storage_goods_id);

	/**
	 * 根据商品id获得该商品所在仓库的信息
	 * 
	 * @param wms_goods_id
	 * @return
	 */
	public List<StorageGoodsStock> getStorageGoodsByGoodsId(long wms_goods_id);

	/**
	 * 根据商品id获得该商品所在仓库的信息
	 * 
	 * @param wms_goods_id
	 * @return
	 */
	public StorageGoodsStock getStorageGoodsByGoodsIdAndStorageId(long wms_goods_id, long storageId);

	/**
	 * 修改商品状态
	 * 
	 * @param wms_goods_id
	 * @param storage_id
	 * @return
	 */
	public int updateWmsGoodsInStorageStatus(long wms_goods_id, long storage_id, int is_del, long user_id);

	/**
	 * 根据供应链端的商品id和仓库类型，查询该商品所在仓的信息
	 * 
	 * @param wmsGoodsId
	 *                供应链端的商品id
	 * @param storageType
	 *                0：RDC仓； 1：LDC仓 -1查询所有
	 * @return
	 */
	public List<Long> getStorageByWmsGoodsId(long wmsGoodsId, int storageType);
	
	public List<Long> getCollegeIdsBybgGoodsIdWithRdc(long wmsGoodsId);
	public List<Long> getCollegeIdsBybgGoodsIdWithLdc(long wmsGoodsId);
	public List<Long> getCollegeIdsBybgGoodsIdWithThirddc(long wmsGoodsId);

	public Pair<Long, PaginationList<StorageGoodsStock>> getPageStorageGoodsStock(PaginationInfo paginationInfo,
			Map paramMap);

	public StorageGoodsStock getStorageGoodsStockByStorageGoodsId(long storagegoodsid);

	public int updateStock(long storageGoodsStockId, long stock);

	/**
	 * 修改某仓库下的商品都不可用
	 * 
	 * @param storageId
	 * @return
	 */
	public int setStorageStockIsDel(long storageId);

	public StorageGoodsStock getByGoodsIdAndStorageId(long wms_goods_id,
			long storageId);

}
