package com.mall.admin.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.storage.StorageRecordModifylogDao;
import com.mall.admin.service.storage.StorageRecordModifylogService;
import com.mall.admin.vo.storage.StorageRecordModifylog;
@Service
public class StorageRecordModifylogServiceImpl implements StorageRecordModifylogService{

	@Autowired
	StorageRecordModifylogDao storageRecordModifylogDao;
	@Override
	public int addStorageRecordModifylog(
			StorageRecordModifylog storageRecordModifylog) {
		// TODO Auto-generated method stub
		return storageRecordModifylogDao.addStorageRecordModifylog(storageRecordModifylog);
	}

}
