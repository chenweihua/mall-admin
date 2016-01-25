package com.mall.admin.service.mallbase.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.mallbase.MallIniDao;
import com.mall.admin.service.mallbase.MallIniService;
import com.mall.admin.vo.mallbase.MallIni;


@Service
public class MallIniServiceImpl implements MallIniService {

	@Autowired
	private MallIniDao mallIniDao;

	@Override
	public List<MallIni> getAllMallIni() {
		return mallIniDao.getAllMallIni();
	}

}
