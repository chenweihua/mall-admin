package com.mall.admin.model.mybatis.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;

public class BaseMallDaoImpl extends BaseDaoImpl {

	@Autowired
	public void setMySqlSessionFactory(
			@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		this.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Autowired
	private BaseOrder0DaoImpl baseOrder0DaoImpl;
	@Autowired
	private BaseOrder1DaoImpl baseOrder1DaoImpl;
	@Autowired
	private BaseOrder2DaoImpl baseOrder2DaoImpl;
	@Autowired
	private BaseOrder3DaoImpl baseOrder3DaoImpl;
	@Autowired
	private BaseSlaveMallDaoImpl baseSlaveMallDaoImpl;

	@SuppressWarnings("unused")
	public SqlSession getMySqlSession(Long userId) {
		if (true) {
			int dbIndex = (int) (userId % 4L);
			switch (dbIndex) {
			case 0:
				return baseOrder0DaoImpl.getSqlSession();
			case 1:
				return baseOrder1DaoImpl.getSqlSession();
			case 2:
				return baseOrder2DaoImpl.getSqlSession();
			case 3:
				return baseOrder3DaoImpl.getSqlSession();
			default:
				LogConstant.mallLog.fatal("[getMySqlSession]userId:" + userId + ". dbIndex:" + dbIndex);
				return null;
			}
		} else {
			return this.getSqlSession();
		}
	}
	
	/**
	 * tb_delivery表的分库
	 */
	public SqlSession getMySqlSession(String deliveryCompanyCode, String deliverySheetCode) {
		if(StringUtils.isEmpty(deliveryCompanyCode)) {
			throw new RuntimeException("deliveryCompanyCode不能为空");
		}
		if(StringUtils.isEmpty(deliverySheetCode)) {
			throw new RuntimeException("deliverySheetCode不能为空");
		}
		int hashCode = (deliveryCompanyCode + "," + deliverySheetCode).hashCode();
		int dbIndex = Math.abs(hashCode) % 4;
		switch (dbIndex) {
			case 0:
				return baseOrder0DaoImpl.getSqlSession();
			case 1:
				return baseOrder1DaoImpl.getSqlSession();
			case 2:
				return baseOrder2DaoImpl.getSqlSession();
			case 3:
				return baseOrder3DaoImpl.getSqlSession();
			default:
				LogConstant.mallLog.fatal("[getMySqlSession]deliveryCompanyCode:" + deliveryCompanyCode + ",deliverySheetCode：" + deliverySheetCode + ". dbIndex:" + dbIndex);
				return null;
		}
		
	}
	
	public BaseDaoImpl getShardDbBaseDao(Long userId) {
		int dbIndex = (int) (userId % 4L);
		switch (dbIndex) {
			case 0:
				return baseOrder0DaoImpl;
			case 1:
				return baseOrder1DaoImpl;
			case 2:
				return baseOrder2DaoImpl;
			case 3:
				return baseOrder3DaoImpl;
			default:
				LogConstant.mallLog.fatal("[getMySqlSession]userId:" + userId + ". dbIndex:" + dbIndex);
				return null;
		}
	}
	
	public SqlSession getSqlSessionByFlag(Integer masterSlaveFlag){
		switch (masterSlaveFlag) {
		case Constants.MASTER_FLAG_VALUE:
			return this.getSqlSession();
		case Constants.SLAVE_FLAG_VALUE:
			return baseSlaveMallDaoImpl.getSqlSession();
		default:
			LogConstant.mallLog.fatal("[getSqlSessionByFlag]masterSlaveFlag:" + masterSlaveFlag);
			return null;
		}
	}
	
	public BaseDaoImpl getBaseDaoImplByFlag(Integer masterSlaveFlag){
		switch (masterSlaveFlag) {
		case Constants.MASTER_FLAG_VALUE:
			return this;
		case Constants.SLAVE_FLAG_VALUE:
			return baseSlaveMallDaoImpl;
		default:
			LogConstant.mallLog.fatal("[getBaseDaoImplByFlag]masterSlaveFlag:" + masterSlaveFlag);
			return null;
		}
	}
}
