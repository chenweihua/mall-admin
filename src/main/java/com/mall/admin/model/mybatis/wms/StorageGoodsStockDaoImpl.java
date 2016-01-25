package com.mall.admin.model.mybatis.wms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.wms.StorageGoodsStockDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.wms.StorageGoodsStock;

@Repository
public class StorageGoodsStockDaoImpl extends BaseMallDaoImpl implements StorageGoodsStockDao {

	@Override
	public List<StorageGoodsStock> getStorageGoodsStackList(PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertWithStock(StorageGoodsStock storageGoodsStock) {
		return this.getSqlSession().insert("StorageGoodsStock.insertWithStock", storageGoodsStock);
	}

	@Override
	public int insert(StorageGoodsStock storageGoods) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("StorageGoodsStock.insertwmsgoodsstock", storageGoods);
	}

	@Override
	public int update(StorageGoodsStock storageGoods) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("StorageGoodsStock.updategoodsstock", storageGoods);
	}

	@Override
	public List<StorageGoodsStock> getStorageGoodsByQuery(long storage_Id, long wms_goods_id, int storagetype) {
		StorageGoodsStock stock = new StorageGoodsStock();
		stock.wms_goods_id = wms_goods_id;
		stock.storage_id = storage_Id;
		stock.storage_type = storagetype;
		return this.getSqlSession().selectList("StorageGoodsStock.getByQuery", stock);
	}

	@Override
	public Pair<Long, PaginationList<StorageGoodsStock>> getPageStorageGoodsStock(PaginationInfo paginationInfo,
			Map paramMap) {
		long totalCount = this.getSqlSession().selectOne("StorageGoodsStock.getCountByParams", paramMap);
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<StorageGoodsStock> storageList = selectPaginationList(
				"StorageGoodsStock.getPageStorageGoodsStockByPage", paramMap, paginationInfo);

		return Pair.of(totalCount, storageList);
	}

	@Override
	public StorageGoodsStock getWmsGoodsById(Long storage_goods_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据商品id 获得商品所在仓库的售卖情况
	 */
	@Override
	public List<StorageGoodsStock> getStorageGoodsByGoodsId(long wms_goods_id) {
		return this.getSqlSession().selectList("StorageGoodsStock.getStorageGoodsByGoodsId", wms_goods_id);
	}

	@Override
	public StorageGoodsStock getStorageGoodsByGoodsIdAndStorageId(long wms_goods_id, long storage_Id) {
		Map map = new HashMap();
		map.put("wms_goods_id", wms_goods_id);
		map.put("storage_id", storage_Id);
		return this.getSqlSession().selectOne("StorageGoodsStock.getByQuery", map);
	}

	@Override
	public int updateWmsGoodsInStorageStatus(long wms_goods_id, long storage_Id, int is_del, long user_id) {
		Map map = new HashMap();
		map.put("wms_goods_id", wms_goods_id);
		map.put("storage_id", storage_Id);
		map.put("is_del", is_del);
		map.put("operator", user_id);
		return this.getSqlSession().update("StorageGoodsStock.updatewmsgoodsinstorgestatus", map);
	}

	@Override
	public List<Long> getStorageByWmsGoodsId(long wmsGoodsId, int storageType) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("wmsGoodsId", wmsGoodsId);
		paramMap.put("storageType", storageType);
		return this.getSqlSession().selectList("StorageGoodsStock.getStorageIdByWmsGoodsId", paramMap);
	}
	
	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithRdc(long wmsGoodsId) {
		return this.getSqlSession().selectList("StorageGoodsStock.getCollegeIdsBybgGoodsIdWithRdc", wmsGoodsId);
	}
	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithLdc(long wmsGoodsId) {
		return this.getSqlSession().selectList("StorageGoodsStock.getCollegeIdsBybgGoodsIdWithLdc", wmsGoodsId);
	}
	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithThirddc(long wmsGoodsId) {
		return this.getSqlSession().selectList("StorageGoodsStock.getCollegeIdsBybgGoodsIdWithThirddc", wmsGoodsId);
	}
	

	@Override
	public StorageGoodsStock getStorageGoodsStockByStorageGoodsId(long storagegoodsid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("StorageGoodsStock.getStorageGoodsStockByStorageGoodsId",
				storagegoodsid);
	}

	@Override
	public int updateStock(long storageGoodsStockId, long stock) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("storage_goods_id", storageGoodsStockId);
		paramMap.put("stock", stock);
		return this.getSqlSession().update("StorageGoodsStock.updategoodsstock", paramMap);
	}

	@Override
	public int setStorageStockIsDel(long storageId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("StorageGoodsStock.setStorageStcokIsDel", storageId);
	}

	@Override
	public StorageGoodsStock getByGoodsIdAndStorageId(long wms_goods_id,
			long storageId) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("wmsGoodsId", wms_goods_id);
		paramMap.put("storageId",storageId);
		return this.getSqlSession().selectOne("StorageGoodsStock.getByGoodsIdAndStorageId",paramMap);
	}

}
