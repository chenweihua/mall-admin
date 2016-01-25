package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.GoodsPropertyDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.GoodsProperty;

@Repository
public class GoodsPropertyDaoImpl extends BaseMallDaoImpl implements
		GoodsPropertyDao {

	@Override
	public GoodsProperty getById(long goodsPropertyId) {
		return this.getSqlSession().selectOne(
				"GoodsProperty.selectByPrimaryKey", goodsPropertyId);
	}

	@Override
	public GoodsProperty getByBgGoodsIdValueId(long bgGoodsId,
			long propertyValueId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("propertyValueId", propertyValueId);
		return this.getSqlSession().selectOne("GoodsProperty.selectByBgGoodsIdValueId", param);
	}

	@Override
	public int deleteById(long goodsPropertyId) {
		return this.getSqlSession().update("GoodsProperty.deleteByPrimaryKey",
				goodsPropertyId);
	}

	@Override
	public int updateByObject(GoodsProperty goodsProperty) {
		return this.getSqlSession().update("GoodsProperty.updateByPrimaryKey",
				goodsProperty);
	}

	@Override
	public int updateByBgGoodsIdVauleId(GoodsProperty goodsProperty) {
		return this.getSqlSession().update("GoodsProperty.updateByBgGoodsIdValueId",
				goodsProperty);
	}

	@Override
	public long insert(GoodsProperty goodsProperty) {
		int result = this.getSqlSession().insert("GoodsProperty.insert",
				goodsProperty);
		if (result < 0) {
			return -1L;
		}
		return goodsProperty.getGoodsPropertyId();
	}

	@Override
	public List<GoodsProperty> getByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList(
				"GoodsProperty.selectByBgGoodsId", bgGoodsId);
	}

}
