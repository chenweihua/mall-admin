package com.mall.admin.model.mybatis.storage;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.storage.StorageRecordModifylogDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.mallbase.CollegeMapper;
import com.mall.admin.vo.storage.StorageRecordModifylog;
import com.mall.admin.vo.storage.StorageRecordModifylogMapper;

@Repository
public class StorageRecordModifylogDaoImpl extends BaseMallDaoImpl implements StorageRecordModifylogDao{

	@Override
	public int addStorageRecordModifylog(StorageRecordModifylog storageRecordModifylog) {
		// TODO Auto-generated method stub
		return getStorageRecordModifylogMapper().insertSelective(storageRecordModifylog);
	}

	private StorageRecordModifylogMapper getStorageRecordModifylogMapper() {
		// TODO Auto-generated method stub
		return this.getSqlSession().getMapper(StorageRecordModifylogMapper.class);
	}
}
