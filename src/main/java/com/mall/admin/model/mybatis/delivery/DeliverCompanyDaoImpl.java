package com.mall.admin.model.mybatis.delivery;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.delivery.DeliverCompanyDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.delivery.DeliverCompany;

@Repository
public class DeliverCompanyDaoImpl extends BaseMallDaoImpl implements DeliverCompanyDao {

	@Override
	public List<DeliverCompany> queryAllDeliverCompanys() {
		return this.getSqlSession().selectList("DeliverCompany.getAllDeliverCompanys");
	}
	
	public DeliverCompany queryDeliverCompany(String deliverCompanyCode) {
		return this.getSqlSession().selectOne("DeliverCompany.getDeliverCompanyByCode",deliverCompanyCode);
	}

}
