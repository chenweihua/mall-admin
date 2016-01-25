package com.mall.admin.model.mybatis.base;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseOrder2DaoImpl extends BaseDaoImpl {

	@Autowired
	public void setMySqlSessionFactory(@Qualifier("sqlSessionOrder2Factory") SqlSessionFactory sqlSessionFactory) {
		this.setSqlSessionFactory(sqlSessionFactory);
	}
	
	
	
	
}
