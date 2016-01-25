package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.SkuProperty;

@Repository
public class SkuPropertyDaoImpl extends BaseMallDaoImpl implements
		SkuPropertyDao {

	@Override
	public SkuProperty getById(long skuPropertyId) {
		return this.getSqlSession().selectOne("SkuProperty.selectByPrimaryKey",
				skuPropertyId);
	}

	@Override
	public Long getBgGoodsIdBybgSkuId(long bgSkuId) {
		List<Long> bgGoodsIdList = this.getSqlSession().selectList("SkuProperty.getBgGoodsIdBybgSkuId", bgSkuId);
		if(bgGoodsIdList == null || bgGoodsIdList.size() == 0){
			return -1L;
		}
		return bgGoodsIdList.get(0);
	}

	@Override
	public SkuProperty getByBgSkuIdValueId(long bgGoodsId,long bgSkuId, long propertyValueId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("bgSkuId", bgSkuId);
		param.put("propertyValueId", propertyValueId);
		return this.getSqlSession().selectOne("SkuProperty.selectByBgSkuIdValueId",
				param);
	}

	@Override
	public int deleteById(long skuPropertyId) {
		return this.getSqlSession().delete("SkuProperty.deleteRealByPrimaryKey", skuPropertyId);
	}

	@Override
	public int updateByObject(SkuProperty skuProperty) {
		return this.getSqlSession().update("SkuProperty.updateByPrimaryKey",
				skuProperty);
	}

	@Override
	public long insert(SkuProperty skuProperty) {
		int result = this.getSqlSession().insert("SkuProperty.insert",
				skuProperty);
		if (result < 0) {
			return -1L;
		}
		return skuProperty.getSkuPropertyId();
	}

	@Override
	public List<Long> getBgSkuByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList(
				"SkuProperty.selectListByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getBgGoodsIdsByBgSkuId(long bgSkuId) {
		return this.getSqlSession().selectList(
				"SkuProperty.selectBgGoodsIdsByBgSkuId", bgSkuId);
	}

	@Override
	public List<SkuProperty> getByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("SkuProperty.selectByBgGoodsId",
				bgGoodsId);
	}

}
