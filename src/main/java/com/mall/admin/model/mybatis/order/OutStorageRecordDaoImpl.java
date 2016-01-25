package com.mall.admin.model.mybatis.order;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.order.OutStorageRecordDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.order.OutStorageRecord;

@Repository
public class OutStorageRecordDaoImpl extends BaseMallDaoImpl implements OutStorageRecordDao {

	@Override
	public int insertRecord(OutStorageRecord record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("OutStorageRecord.addRecord", record);
	}

}
