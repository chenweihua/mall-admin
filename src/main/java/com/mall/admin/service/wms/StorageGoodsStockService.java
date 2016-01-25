package com.mall.admin.service.wms;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

public interface StorageGoodsStockService {

	/**
	 * 根据条件查询分页
	 * 
	 * @param begin
	 *                开始页
	 * @param pagenum
	 *                每页个数
	 * @param wmsGoods
	 *                查询条件
	 * @return
	 */
	public List<StorageGoodsStock> getStorageGoodsStock(StorageGoodsStock storageGoods,
			PaginationInfo paginationInfo);

	/**
	 * 
	 * @param wmsgoods
	 *                待插入的对象
	 * @return
	 */
	public int insertOrUpdateWithStock(WmsGoods wmsGoods);
	
	public int insert(long wms_goods_id, long storage_id, int storage_type, long user_id);

	/**
	 * 
	 * @param wmsgoods
	 *                待更新的对象
	 * @return
	 */
	public int update(StorageGoodsStock storageGoods);

	/**
	 * 根据参数查询
	 * 
	 * @param gmb
	 * @return
	 */
	public List<StorageGoodsStock> getByQuery(long wms_goods_id, long storage_Id, int storagetype,
			PaginationInfo paginationInfo);

	/**
	 * 根据id查询库存
	 * 
	 * @param id
	 * @return
	 */
	public StorageGoodsStock queryById(Long id);

	/**
	 * 获得该商品所在仓库id
	 * 
	 * @param wms_goods_id
	 * @return
	 */
	public List<StorageGoodsStock> getStorageGoodsStockByGoodsId(long wms_goods_id);

	/**
	 * 修改商品在仓库中的状态
	 * 
	 * @param wms_goods_id
	 * @param storage_id
	 * @param is_del
	 * @return
	 */
	public int updateGoodsInStorageStatus(long wms_goods_id, long storage_id, int is_del, long user_id);

	public String importGoods(List<String[]> data, long operstorid, long storageId);

	public int insert(StorageGoodsStock storageGoodsStock);

	public StorageGoodsStock getByGoodsId(long wms_goods_id, long storageId);

	/**
	 * 根据供应链商品id和仓库类型，查询所在仓库的id
	 * 
	 * @param wmsGoodsId
	 *                供应链商品id
	 * @param storageType
	 *                0：rdc仓； 1：ldc仓； -1：所有仓
	 * @return
	 */
	public List<Long> getStorageIdByWmsGoodsId(long wmsGoodsId, int storageType);

	public Pair<Long, PaginationList<StorageGoodsStock>> getPageStorageGoodsStock(PaginationInfo paginationInfo,
			Map paramMap);

	public StorageGoodsStock getStorageGoodsStockByStorageGoodsId(long goodsid);

	public int updateStock(long storageGoodsStockId, long stock);

	/**
	 * 修改某仓库下的商品不可用
	 * 
	 * @param storageId
	 * @return
	 */
	public int setStorageStockIsDel(long storageId);

	public StorageGoodsStock getByGoodsIdAndStorageId(long wms_goods_id,
			long storageId);
	
	
	public List<Long> getCollegeIdsBybgGoodsIdWithRdc(long wmsGoodsId);
	
	public List<Long> getCollegeIdsBybgGoodsIdWithLdc(long wmsGoodsId);
	
	public List<Long> getCollegeIdsBybgGoodsIdWithThirddc(long wmsGoodsId) ;
}
