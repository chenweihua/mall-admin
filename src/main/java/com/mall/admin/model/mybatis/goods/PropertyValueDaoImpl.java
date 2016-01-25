package com.mall.admin.model.mybatis.goods;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.PropertyValueDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.PropertyValue;

@Repository
public class PropertyValueDaoImpl extends BaseMallDaoImpl implements
		PropertyValueDao {

	@Override
	public PropertyValue getById(long propertyValueId) {
		return this.getSqlSession().selectOne(
				"PropertyValue.selectByPrimaryKey", propertyValueId);
	}
	

	@Override
	public List<PropertyValue> getByNameId(long propertyNameId) {
		return this.getSqlSession().selectList("PropertyValue.selectListByNameId", propertyNameId);
	}



	@Override
	public int deleteById(long propertyValueId) {
		return this.getSqlSession().update("PropertyValue.deleteByPrimaryKey",
				propertyValueId);
	}

	@Override
	public int updateByObject(PropertyValue propertyValue) {
		return this.getSqlSession().update(
				"PropertyValue.updateByPrimaryKeySelective", propertyValue);
	}

	@Override
	public long insert(PropertyValue propertyValue) {
		int result = this.getSqlSession().insert("PropertyValue.insert",
				propertyValue);
		if (result < 0) {
			return -1L;
		}
		return propertyValue.getPropertyValueId();
	}

}
