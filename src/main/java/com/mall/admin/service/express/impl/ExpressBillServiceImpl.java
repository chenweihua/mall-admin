package com.mall.admin.service.express.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.express.ExpressBillDao;
import com.mall.admin.service.express.ExpressBillService;
import com.mall.admin.vo.express.ExpressBill;
@Service
public class ExpressBillServiceImpl implements ExpressBillService {
	@Autowired
	private ExpressBillDao expressBillDao;
	@Override
	public long insert(ExpressBill expressBill) {
		return expressBillDao.insert(expressBill);
	}

}
