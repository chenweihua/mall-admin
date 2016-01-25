package com.mall.admin.model.mybatis.mallbase;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.mallbase.MallIniDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.mallbase.MallIni;


@Repository
public class MallIniDaoImpl extends BaseMallDaoImpl implements MallIniDao {

	@Override
	public List<MallIni> getAllMallIni() {
		return this.getSqlSession().selectList("MallIni.getAllMallIni");
	}
}
