package com.mall.admin.service.sale;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.sale.SaleInCollege;
import com.mall.admin.vo.sale.SaleInStorage;
import com.mall.admin.vo.sale.SaleSkuInfo;

public interface SaleService {

	/**
	 * 获得RDC仓库下销售商品的信息
	 * 
	 * @param storage_id
	 *                仓库id
	 * @param goods_name
	 *                商品名称
	 * @param status
	 *                状态 0：全部；1：待售；2：在售；3：售罄
	 * @param category_id
	 *                类目id
	 * @return
	 */
	public List<SaleInStorage> getSaleInRDCStorageInfo(Map paramMap, PaginationInfo paginationInfo);

	/**
	 * 获得LDC仓库下销售商品的信息
	 * 
	 * @param storage_id
	 *                仓库id
	 * @param goods_name
	 *                商品名称
	 * @param status
	 *                状态 0：全部；1：待售；2：在售；3：售罄
	 * @param category_id
	 *                类目id
	 * @return
	 */
	public List<SaleInStorage> getSaleInLDCStorageInfo(Map paramMap, PaginationInfo paginationInfo);
	
	public List<SaleInStorage> getSaleInVMStorageInfo(Map paramMap, PaginationInfo paginationInfo);

	/**
	 * 获得RDC仓下该商品在学校下的售卖情况。如果collegeId=0，则表示查询的是所有的学校
	 * 
	 * @param storageId
	 * @param collegeId
	 * @param bg_goods_id
	 * @return
	 */
	public List<SaleInCollege> getSaleInRdcStorageInCollege(long storageId, long collegeId, long bg_goods_id);

	/**
	 * 获得LDC仓下该商品在学校下的售卖情况。如果collegeId=0，则表示查询的是所有的学校
	 * 
	 * @param storageId
	 * @param collegeId
	 * @param bg_goods_id
	 * @return
	 */
	public List<SaleInCollege> getSaleInLdcStorageInCollege(long storageId, long collegeId, long bg_goods_id);
	
	public List<SaleInCollege> getSaleInVmStorageInCollege(long storageId, long collegeId, long bg_goods_id);

	/**
	 * 获得售卖商品的sku信息，包括单品、组合品、聚合品
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<SaleSkuInfo> getSaleSkuInfoByGoodsId(long bggoodsId, int goodsType);

	/**
	 * 如果是单品，则查询该单品所在的库存
	 * 
	 * @param bg_goods_id
	 * @param storageId
	 * @return
	 */
	public int queryStorageStock(long bg_goods_id, long storageId);

}
