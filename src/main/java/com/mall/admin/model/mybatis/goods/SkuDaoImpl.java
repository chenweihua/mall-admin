package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.SkuDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.Sku;

@Repository
public class SkuDaoImpl extends BaseMallDaoImpl implements SkuDao {

	@Override
	public Sku getById(long skuId) {
		return this.getSqlSession().selectOne("Sku.selectByPrimaryKey", skuId);
	}

	@Override
	public List<Sku> get4copy2activity(Long collegeId) {
		return this.getSqlSession().selectList("Sku.select4copy2activity", collegeId);
	}

	@Override
	public List<Long> getCollegeIdsByBgSkuId(long bgSkuId, int isLdc) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgSkuId", bgSkuId);
		param.put("isLdc", isLdc);
		return this.getSqlSession().selectList("Sku.selectCollegeIdsByBgSkuId", param);
	}

	@Override
	public List<Sku> getListByGoodsId(long goodsId) {
		return this.getSqlSession().selectList("Sku.selectListByGoodsId", goodsId);
	}

	@Override
	public List<Sku> getListByGoodsIdSkuStatus(long goodsId, int skuStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("skuStatus", skuStatus);
		return this.getSqlSession().selectList("Sku.selectListByGoodsIdSkuStatus", param);
	}

	@Override
	public List<Sku> getListByBgGoodsIdCollegeId(long bgGoodsId, long collegeId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("collegeId", collegeId);
		return this.getSqlSession().selectList("Sku.selectListByBgGoodsIdCollegeId", param);
	}

	@Override
	public Sku getBybgSkuIdAndCollegeIdAndGoodsId(long bgSkuId, long collegeId, long goodsId, int distributeType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgSkuId", bgSkuId);
		param.put("collegeId", collegeId);
		param.put("goodsId", goodsId);
		param.put("distributeType", distributeType);
		return this.getSqlSession().selectOne("Sku.getBybgSkuIdAndCollegeIdAndGoodsId", param);
	}

	@Override
	public int deleteById(long skuId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByGoodsId(List<Long> goodsIds) {
		Map<String, List<Long>> param = new HashMap<>();
		param.put("goodsIds", goodsIds);
		return this.getSqlSession().update("Sku.deleteByGoodsId", param);
	}

	@Override
	public int deleteByBgGoodsIdCollegeIdIsLdc(long bgGoodsId, long bgSkuId, long collegeId, int isLdc) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bgGoodsId", bgGoodsId);
		params.put("bgSkuId", bgSkuId);
		params.put("isLdc", isLdc);
		params.put("collegeId", collegeId);
		return this.getSqlSession().update("Sku.deleteByCollegeIdIsLdc", params);
	}

	@Override
	public int updateByObject(Sku sku) {
		return this.getSqlSession().update("Sku.updateByPrimaryKeySelective", sku);
	}

	@Override
	public long insert(Sku sku) {
		int result = this.getSqlSession().insert("Sku.insert", sku);
		if (result < 0) {
			return -1L;
		}
		return sku.getSkuId();
	}

	@Override
	public int updatePrice(long skuId, long originPrice, long wapPrice, long appPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("skuId", skuId);
		param.put("originPrice", originPrice);
		param.put("wapPrice", wapPrice);
		param.put("appPrice", appPrice);
		return this.getSqlSession().update("Sku.updatePrice", param);
	}

	@Override
	public int updatePriceByBgSkuId(long bgskuId, long originPrice, long wapPrice, long appPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgSkuId", bgskuId);
		param.put("originPrice", originPrice);
		param.put("wapPrice", wapPrice);
		param.put("appPrice", appPrice);
		return this.getSqlSession().update("Sku.batchUpdatePrice", param);
	}

	@Override
	public int updateStock(long skuId, long stock, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("skuId", skuId);
		param.put("stock", stock);
		param.put("status", status);
		return this.getSqlSession().update("Sku.updateStock", param);
	}

	@Override
	public int updateStockByGoodsId(long goodsId, long stock, int status, int isLdc) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("stock", stock);
		param.put("status", status);
		param.put("isLdc", isLdc);
		return this.getSqlSession().update("Sku.updateStockByGoodsId", param);
	}

	// @Override
	// public int updateRdcStatusByBgGoodsIdCollegeId(long bgGoodsId, long
	// collegeId, int status) {
	// Map<String, Object> param = new HashMap<>();
	// param.put("bgGoodsId", bgGoodsId);
	// param.put("collegeId", collegeId);
	// param.put("status", status);
	// return
	// this.getSqlSession().update("Sku.updateRdcStatusByBgGoodsIdCollegeId",
	// param);
	// }

	// @Override
	// public int updateLdcStatusByBgGoodsIdCollegeId(long bgGoodsId, long
	// collegeId, int status) {
	// Map<String, Object> param = new HashMap<>();
	// param.put("bgGoodsId", bgGoodsId);
	// param.put("collegeId", collegeId);
	// param.put("status", status);
	// return
	// this.getSqlSession().update("Sku.updateLdcStatusByBgGoodsIdCollegeId",
	// param);
	// }

}
