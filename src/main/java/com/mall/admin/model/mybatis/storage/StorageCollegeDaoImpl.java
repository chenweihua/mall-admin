package com.mall.admin.model.mybatis.storage;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.storage.StorageCollegeDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.storage.StorageCollege;
@Repository
public class StorageCollegeDaoImpl extends BaseMallDaoImpl implements StorageCollegeDao {

	@Override
	public List<Long> getCollegeIdListByStorageId(Long storageId) {
		return this.getSqlSession().selectList("StorageCollege.selectCollegeIdListByStorageId", storageId);
	}

	@Override
	public Long insert(StorageCollege storageCollege) {
		int result = this.getSqlSession().insert("StorageCollege.insert", storageCollege);
		if(result > 0){
			return storageCollege.getStorageCollegeId();
		}
		return -1L;
	}

	@Override
	public int deleteByStorageId(Long storageId) {
		return this.getSqlSession().delete("StorageCollege.deleteByStorageId", storageId);
	}

	@Override
	public int deleteByPrimaryKey(Long storageCollegeId) {
		return this.getSqlSession().delete("StorageCollege.deleteByPrimaryKey", storageCollegeId);
	}

}
