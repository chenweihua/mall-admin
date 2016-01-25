package com.mall.admin.service.wms;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.WmsGoods;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

public interface WmsGoodsService {

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
	public List<WmsGoods> getWmsGoods(Map paramMap, PaginationInfo paginationInfo);

	public long insertOrUpdateWithStock(WmsGoods wmsGoods);
	/**
	 * 
	 * @param wmsgoods
	 *                待插入的对象
	 * @return
	 */
	public long insert(WmsGoods wmsgoods);

	/**
	 * 
	 * @param wmsgoods
	 *                待更新的对象
	 * @return
	 */
	public int update(WmsGoods wmsgoods);

	/**
	 * 根据商品id查询商品
	 * 
	 * @param id
	 * @return
	 */
	public WmsGoods queryById(Long id);

	/**
	 * 根据商品的售卖范围，设置它是否选择和是否展开
	 * 
	 * @param ztreeBean
	 */
	public void setZtreeBeanStatus(ZtreeBean ztreeBean, long wms_goods_id);

	/**
	 * 设置商品的销售范围，并把商品添加到对应的仓中
	 * 
	 * @param ztreeBean
	 * @param wms_goods_id
	 * @param user
	 */
	public void setWmsGoodsRegion(ZtreeBean ztreeBean, long wms_goods_id, User user);

	/**
	 * 复制范围在所有rdc仓的商品到新增rdc仓中
	 * 
	 * @param storageId
	 */
	public void copyRdcWmsGoodsToStorage(long storageId, long userId);

	/**
	 * 复制范围在所有Ldc仓的商品到新增ldc仓中 同时把所属城市也复制到该仓中
	 * 
	 * @param storageId
	 * @throws Exception
	 */
	public void copyLdcWmsGoodsToStorage(Storage storage, long cityId, long userId) throws Exception;

	/**
	 * @author gaozhou
	 */
	public WmsGoods queryById(long wms_goods_id);

	/**
	 * 批量导入商品
	 * 
	 * @param data
	 *                导入数据
	 * @param user
	 * @return
	 */
	public String importWmsGoodsList(List<String[]> data, User user, Long storageId);

	public WmsGoods getWmsGoodsByStorageGoodId(long goodsId);

	/**
	 * 和wms系统同步商品信息
	 * 
	 * @param wmsGoods
	 */
	public void synchWmsGoods(WmsGoods wmsGoods);

	public List<WmsGoods> getByGbmAndStorageId(String gbm, Long storageId);

	/**
	 * 根据skuId查询
	 * 
	 * @param bgSkuId
	 * @return
	 */
	public List<WmsGoods4BgSkuInfo> queryWmsGoods4BgSku(long bgSkuId);
	/**
	 * 获取bgSkuId,List<WmsGoods4BgSkuInfo>的map集合
	 * @return
	 */
	public Map<Long,List<WmsGoods4BgSkuInfo>> getWmsGoods4BgSkuMap();
}
