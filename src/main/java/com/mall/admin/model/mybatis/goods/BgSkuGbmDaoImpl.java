package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.BgSkuGbmDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.BgSkuGbm;

@Repository
public class BgSkuGbmDaoImpl extends BaseMallDaoImpl implements BgSkuGbmDao {

	@Override
	public BgSkuGbm getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public BgSkuGbm getByBgSkuIdWmsGoodsId(long bgSkuId, long wmsGoodsId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgSkuId", bgSkuId);
		param.put("wmsGoodsId", wmsGoodsId);
		return this.getSqlSession().selectOne("BgSkuGbm.selectByBgSkuIdWmsGoodsId", param);
	}

	@Override
	public BgSkuGbm getByBgWmsGoodsIdNum(long wmsGoodsId, long num) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("num", num);
		param.put("wmsGoodsId", wmsGoodsId);
		List<BgSkuGbm> list = this.getSqlSession().selectList("BgSkuGbm.selectByBgWmsGoodsIdNum", param);
		if(list == null || list.size() < 1){
			return null;
		}
		return list.get(0);
	}

	@Override
	public int deleteById(long skuGbmId) {
		return this.getSqlSession().update("BgSkuGbm.deleteByPrimaryKey", skuGbmId);
	}

	@Override
	public int updateByObject(BgSkuGbm bgSkuGbm) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByBgSkuIdWmsGoodsId(BgSkuGbm bgSkuGbm) {
		return this.getSqlSession().update("BgSkuGbm.updateByBgSkuIdWmsGoodsId", bgSkuGbm);
	}


	@Override
	public long insert(BgSkuGbm bgSkuGbm) {
		int result = getSqlSession().insert("BgSkuGbm.insert", bgSkuGbm);
		if (result < 0) {
			return -1L;
		}
		return bgSkuGbm.getSkuGbmId();
	}

	@Override
	public List<Long> getWmsGoodsIdByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("BgSkuGbm.getWmsGoodsIdByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getWmsGoodsIdByBgPolyGoodsId(long bgGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BgSkuGbm.getWmsGoodsIdByBgPolyGoodsId", bgGoodsId);
	}
	
	@Override
	public List<BgSkuGbm> getBgSkuGbmByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("BgSkuGbm.getBgSkuGbmByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getWmsGoodsIdByBgSkuId(long bgSkuId) {
		return this.getSqlSession().selectList("BgSkuGbm.getWmsGoodsIdByBgSkuId", bgSkuId);
	}

	@Override
	public List<BgSkuGbm> getByBgSkuId(long bgSkuId) {
		return this.getSqlSession().selectList("BgSkuGbm.selectByBgSkuId", bgSkuId);
	}
	
}
