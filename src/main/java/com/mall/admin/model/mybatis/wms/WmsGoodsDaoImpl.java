package com.mall.admin.model.mybatis.wms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.wms.WmsGoodsDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.wms.WmsGoods;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

@Repository
public class WmsGoodsDaoImpl extends BaseMallDaoImpl implements WmsGoodsDao {

	@Override
	public List<WmsGoods> getWmsGoodsList(Map paramMap, PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<WmsGoods> wmsGoodsList = selectPaginationList("WmsGoods.getPageWmsGoodsByPage",
				paramMap, paginationInfo);
		return wmsGoodsList;
	}

	@Override
	public long insert(WmsGoods wmsGoods) {
		int result = this.getSqlSession().insert("WmsGoods.insertIntoWmsGoods", wmsGoods);
		if (result > 0) {
			return wmsGoods.getWms_goods_id();
		}
		return -1L;
	}

	@Override
	public int update(WmsGoods wmsGoods) {
		return this.getSqlSession().update("WmsGoods.updateWmsGoods", wmsGoods);
	}

	@Override
	public List<WmsGoods> getWmsGoodsByGbm(String gbm) {
		WmsGoods wmsGoods = new WmsGoods();
		wmsGoods.wms_goods_gbm = gbm;
		return this.getSqlSession().selectList("WmsGoods.queryWmsGoods", wmsGoods);
	}

	@Override
	public WmsGoods getWmsGoodsById(Long wms_goods_id) {
		WmsGoods wmsGoods = new WmsGoods();
		wmsGoods.wms_goods_id = wms_goods_id;
		return this.getSqlSession().selectOne("WmsGoods.queryWmsGoods", wmsGoods);
	}

	@Override
	public int deleteWmsGoodsRegion(long wms_goods_id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete("WmsGoods.deletwmsgoodsregion", wms_goods_id);
	}

	@Override
	public int insertWmsGoodsRegion(long wms_goods_id, long region_id, int region_type) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("wms_goods_id", wms_goods_id);
		map.put("region_id", region_id);
		map.put("region_type", region_type);
		return this.getSqlSession().insert("WmsGoods.insertwmsggoodsregion", map);
	}

	@Override
	public List<Long> getWmsGoodsIdByRegionId(long regionId, long region_type) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("region_id", regionId);
		map.put("region_type", region_type);

		return this.getSqlSession().selectList("WmsGoods.getStorageGoodsByRegionId", map);
	}

	/**
	 * @author gaozhou
	 */
	@Override
	public WmsGoods queryById(long wms_goods_id) {
		return this.getSqlSession().selectOne("WmsGoods.queryById", wms_goods_id);
	}

	@Override
	public WmsGoods queryByGbmAndNo(String gbm, String No) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("wms_goods_gbm", gbm);
		map.put("wmsGoodsNo", No);
		return this.getSqlSession().selectOne("WmsGoods.queryById", map);
	}

	@Override
	public List<WmsGoods> getByGbmAndStorageId(String gbm, Long storageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wms_goods_gbm", gbm);
		map.put("storageId", storageId);
		return this.getSqlSession().selectList("WmsGoods.queryByGbm", map);
	}

	@Override
	public List<WmsGoods4BgSkuInfo> queryWmsGoods4BgSku(long bgSkuId) {
		return this.getSqlSession().selectList("WmsGoods.queryWmsGoods4BgSku", bgSkuId);
	}

	@Override
	public List<WmsGoods4BgSkuInfo> getWmsGoods4BgSkuList() {
		return this.getSqlSession().selectList("WmsGoods.getWmsGoods4BgSkuList");
	}

}
