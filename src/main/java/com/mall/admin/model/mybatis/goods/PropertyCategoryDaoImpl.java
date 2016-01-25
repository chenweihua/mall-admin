package com.mall.admin.model.mybatis.goods;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.PropertyCategoryDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.PropertyCategory;
@Repository
public class PropertyCategoryDaoImpl extends BaseMallDaoImpl implements PropertyCategoryDao {

	@Override
	public PropertyCategory getById(long propertyCategoryId) {
		return this.getSqlSession().selectOne("PropertyCategory.selectByPrimaryKey", propertyCategoryId);
	}

	@Override
	public int deleteById(long propertyCategoryId) {
		return this.getSqlSession().update("PropertyCategory.deleteByPrimaryKey",
				propertyCategoryId);
	}

	@Override
	public int updateByObject(PropertyCategory propertyCategory) {
		return this.getSqlSession().update(
				"PropertyCategory.updateByPrimaryKeySelective", propertyCategory);
	}

	@Override
	public long insert(PropertyCategory propertyCategory) {
		int result = this.getSqlSession().insert("PropertyCategory.insert",
				propertyCategory);
		if (result < 0) {
			return -1L;
		}
		return propertyCategory.getPropertyCategoryId();
	}

	@Override
	public List<PropertyCategory> getByPid(long pid) {
		return this.getSqlSession().selectList("PropertyCategory.selectByPid", pid);
	}
}
