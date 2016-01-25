package com.mall.admin.model.mybatis.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseSlaveMallDaoImpl extends BaseDaoImpl {

	@Autowired
	public void init(
			@Qualifier("sqlSessionSlaveFactory") SqlSessionFactory sqlSessionFactory) {
		this.setSqlSessionFactory(sqlSessionFactory);
	}
}
