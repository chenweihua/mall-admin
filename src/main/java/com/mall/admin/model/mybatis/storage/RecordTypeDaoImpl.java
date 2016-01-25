package com.mall.admin.model.mybatis.storage;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.storage.RecordTypeDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.wms.StorageGoodsStock;

@Repository
public class RecordTypeDaoImpl extends BaseMallDaoImpl implements RecordTypeDao {

	@Override
	public RecordType getByName(String name) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("RecordType.getByName",name);
	}

	@Override
	public RecordType getById(long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("RecordType.getById",id);
	}

	@Override
	public List<RecordType> getAll() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("RecordType.getAll");
	}

	

}
