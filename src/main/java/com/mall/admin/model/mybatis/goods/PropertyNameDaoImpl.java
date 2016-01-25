package com.mall.admin.model.mybatis.goods;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.PropertyNameDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.dto.PropertyDto;

@Repository
public class PropertyNameDaoImpl extends BaseMallDaoImpl implements
		PropertyNameDao {

	@Override
	public PropertyName getById(long propertyNameId) {
		return this.getSqlSession().selectOne(
				"PropertyName.selectByPrimaryKey", propertyNameId);
	}

	@Override
	public List<PropertyName> getByCategoryId(long propertyCategoryId) {
		return this.getSqlSession().selectList("PropertyName.selectListByCategoryId", propertyCategoryId);
	}



	@Override
	public int deleteById(long propertyNameId) {
		return this.getSqlSession().update("PropertyName.deleteByPrimaryKey",
				propertyNameId);
	}

	@Override
	public int updateByObject(PropertyName propertyName) {
		return this.getSqlSession().update(
				"PropertyName.updateByPrimaryKeySelective", propertyName);
	}

	@Override
	public long insert(PropertyName propertyName) {
		int result = this.getSqlSession().insert("PropertyName.insert",
				propertyName);
		if (result < 0) {
			return -1L;
		}
		return propertyName.getPropertyNameId();
	}

	@Override
	public List<PropertyDto> selectPropertyDto(long bgGoodsId) {
		return this.getSqlSession().selectList("PropertyName.selectPropertyDtosByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<PropertyName> getPnListByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("PropertyName.selectPnListByBgGoodsId", bgGoodsId);
	}

}
