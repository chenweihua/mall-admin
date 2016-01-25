package com.mall.admin.model.mybatis.express;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.express.ExpressBillDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.express.ExpressBill;
@Repository
public class ExpressBillDaoImpl extends BaseMallDaoImpl implements ExpressBillDao {

	@Override
	public long insert(ExpressBill expressBill) {
		int ret = this.getSqlSession().insert("ExpressBill.insert", expressBill);
		if(ret > 0){
			return expressBill.getId();
		}
		return -1L;
	}
}
