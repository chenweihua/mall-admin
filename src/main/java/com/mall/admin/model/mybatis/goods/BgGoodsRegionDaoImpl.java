package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.constant.Constants;
import com.mall.admin.model.dao.goods.BgGoodsRegionDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.BgGoodsRegion;

@Repository
public class BgGoodsRegionDaoImpl extends BaseMallDaoImpl implements BgGoodsRegionDao {

	@Override
	public BgGoodsRegion getById(long bgGoodsRegionId) {
		return this.getSqlSession().selectOne("BgGoodsRegion.selectByPrimaryKey", bgGoodsRegionId);
	}

	@Override
	public List<Long> getStorageIdsByBgGoodsId(long bgGoodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("regionType", 1);
		return this.getSqlSession().selectList("BgGoodsRegion.getRegionIdsByBgGoodsIdAndRegionType", map);
	}

	@Override
	public int deleteById(long bgGoodsRegionId) {
		return this.getSqlSession().update("BgGoodsRegion.deleteByPrimaryKey", bgGoodsRegionId);
	}

	@Override
	public int deleteByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().update("BgGoodsRegion.deleteByBgGoodsId", bgGoodsId);
	}

	@Override
	public int updateByObject(BgGoodsRegion bgGoodsRegion) {
		return this.getSqlSession().update("BgGoodsRegion.updateByPrimaryKeySelective", bgGoodsRegion);
	}

	@Override
	public long insert(BgGoodsRegion bgGoodsRegion) {
		int result = this.getSqlSession().insert("BgGoodsRegion.insert", bgGoodsRegion);
		if (result < 0) {
			return -1L;
		}
		return bgGoodsRegion.getBgGoodsRegionId();
	}

	@Override
	public long insertOrUpdate(BgGoodsRegion bgGoodsRegion, boolean isOld) {
		BgGoodsRegion temp = getByBgGoodsIdAndRegionId(bgGoodsRegion.getBgGoodsId(),
				bgGoodsRegion.getRegionId(), bgGoodsRegion.getRegionType());
		if (temp != null) {
			if (!isOld) {
				temp.setStatus(Constants.GOOD_STATUS_ONSALE);
			}
			temp.setIsDel(0);
			int i = updateByObject(temp);
			if (i > 0) {
				return temp.getBgGoodsRegionId();
			}
			return -1L;
		} else {
			bgGoodsRegion.setStock(Constants.BGGOODSREGION_STOCK_NAN);
			return insert(bgGoodsRegion);
		}
	}

	@Override
	public boolean isExist(long bgGoodsId, long regionId, int regionType) {
		BgGoodsRegion bgGoodsRegion = getByBgGoodsIdAndRegionId(bgGoodsId, regionId, regionType);
		if (bgGoodsRegion != null) {
			return true;
		}
		return false;
	}

	@Override
	public BgGoodsRegion getByBgGoodsIdAndRegionId(long bgGoodsId, long regionId, int regionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("regionId", regionId);
		map.put("regionType", regionType);
		return this.getSqlSession().selectOne("BgGoodsRegion.getByBgGoodsIdAndRegionId", map);
	}

	@Override
	public List<BgGoodsRegion> getByRegionId(long regionId, int regionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionId", regionId);
		map.put("regionType", regionType);
		return this.getSqlSession().selectList("BgGoodsRegion.getByRegionIdType", map);
	}

	@Override
	public int updateWeightStockStatus(long bgGoodsId, long regionId, int regionType, long weight, long stock,
			int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("regionId", regionId);
		map.put("regionType", regionType);
		map.put("weight", weight);
		map.put("stock", stock);
		map.put("status", status);
		return this.getSqlSession().update("BgGoodsRegion.updateWeightStockStatus", map);
	}

	@Override
	public int updateStatus(long bgGoodsId, long regionId, int regionType, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("regionId", regionId);
		map.put("regionType", regionType);
		map.put("status", status);
		return this.getSqlSession().update("BgGoodsRegion.updateStatus", map);
	}

	@Override
	public int update(long regionId, long bgGoodsId, int regionType, long originPrice, long wapPrice,
			long appPrice, long maxNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("regionId", regionId);
		map.put("regionType", regionType);
		map.put("originPrice", originPrice);
		map.put("wapPrice", wapPrice);
		map.put("appPrice", appPrice);
		map.put("maxNum", maxNum);
		return this.getSqlSession().update("BgGoodsRegion.updatePrice", map);
	}

	@Override
	public int batchUpdate(long bgGoodsId, long originPrice, long wapPrice, long appPrice, long maxNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("originPrice", originPrice);
		map.put("wapPrice", wapPrice);
		map.put("appPrice", appPrice);
		map.put("maxNum", maxNum);
		return this.getSqlSession().update("BgGoodsRegion.batchUpdatePrice", map);
	}

}
