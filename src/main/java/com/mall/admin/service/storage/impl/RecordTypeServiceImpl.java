package com.mall.admin.service.storage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.storage.RecordTypeDao;
import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.vo.storage.RecordType;

@Service
public class RecordTypeServiceImpl implements RecordTypeService {

	@Autowired
	RecordTypeDao recordTypeDao;
	
	@Override
	public RecordType getById(long id) {
		// TODO Auto-generated method stub
		return recordTypeDao.getById(id);
	}

	@Override
	public RecordType getByName(String name) {
		// TODO Auto-generated method stub
		return recordTypeDao.getByName(name);
	}

	@Override
	public List<RecordType> getAll() {
		// TODO Auto-generated method stub
		return recordTypeDao.getAll();
	}
	

}
