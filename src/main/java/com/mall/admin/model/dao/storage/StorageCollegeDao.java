package com.mall.admin.model.dao.storage;

import java.util.List;

import com.mall.admin.vo.storage.StorageCollege;

public interface StorageCollegeDao {
	public List<Long> getCollegeIdListByStorageId(Long storageId);
	
	public Long insert(StorageCollege storageCollege);
	
	public int deleteByStorageId(Long storageId);
	
	public int deleteByPrimaryKey(Long storageCollegeId);
}
