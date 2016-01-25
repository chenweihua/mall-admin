package com.mall.admin.model.dao.wms;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.wms.WmsGoods;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

public interface WmsGoodsDao {

	public List<WmsGoods> getWmsGoodsList(Map paramMap, PaginationInfo paginationInfo);

	public long insert(WmsGoods wmsGoods);

	public int update(WmsGoods wmsGoods);

	public List<WmsGoods> getWmsGoodsByGbm(String gbm);

	/**
	 * 根据id获得商品信息
	 * 
	 * @param wms_goods_id
	 * @return
	 */
	public WmsGoods getWmsGoodsById(Long wms_goods_id);

	/**
	 * 删除商品的范围
	 * 
	 * @param wms_goods_id
	 * @return
	 */
	public int deleteWmsGoodsRegion(long wms_goods_id);

	/**
	 * 添加商品的范围
	 * 
	 * @param wms_goods_id
	 * @param region_id
	 * @param type
	 * @return
	 */
	public int insertWmsGoodsRegion(long wms_goods_id, long region_id, int region_type);

	/**
	 * 根据范围id和范围类型，查找对应的wms_goods_id列表
	 * 
	 * @param regionId
	 * @param region_type
	 * @return
	 */
	public List<Long> getWmsGoodsIdByRegionId(long regionId, long region_type);

	/**
	 * @author gaozhou
	 */
	public WmsGoods queryById(long wms_goods_id);

	/**
	 * 根据商品编码和商品序号查询对应的数据
	 * 
	 * @param gbm
	 * @param No
	 * @return
	 */
	public WmsGoods queryByGbmAndNo(String gbm, String No);

	public List<WmsGoods> getByGbmAndStorageId(String gbm, Long storageId);

	public List<WmsGoods4BgSkuInfo> queryWmsGoods4BgSku(long bgSkuId);
	
	public List<WmsGoods4BgSkuInfo> getWmsGoods4BgSkuList();
}
