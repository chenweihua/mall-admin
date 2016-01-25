package com.mall.admin.service.storage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.storage.StorageCollegeDao;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.vo.storage.StorageCollege;
@Service
public class StorageCollegeServiceImpl implements StorageCollegeService {
	@Autowired
	private StorageCollegeDao storageCollegeDao;
	
	@Override
	public List<Long> getCollegeIdListByStorageId(Long storageId) {
		return storageCollegeDao.getCollegeIdListByStorageId(storageId);
	}

	@Override
	public Long insert(StorageCollege storageCollege) {
		return storageCollegeDao.insert(storageCollege);
	}

	@Override
	public int deleteByStorageId(Long storageId) {
		return storageCollegeDao.deleteByStorageId(storageId);
	}

	@Override
	public int deleteByPrimaryKey(Long storageCollegeId) {
		return storageCollegeDao.deleteByPrimaryKey(storageCollegeId);
	}

}
