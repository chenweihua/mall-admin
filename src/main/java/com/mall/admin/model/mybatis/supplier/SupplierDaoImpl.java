package com.mall.admin.model.mybatis.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.supplier.SupplierDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.supplier.Suppiler;

@Repository
public class SupplierDaoImpl extends BaseMallDaoImpl implements SupplierDao {

	@Override
	public List<Suppiler> getAllSuppiler() {
		return this.getSqlSession().selectList("Suppiler.getAllSuppiler");
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationList<Suppiler> getPageSuppiler(PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Suppiler> suppilerList = selectPaginationList("Suppiler.getPageSuppilerByPage", paginationInfo);
		return suppilerList;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<Long, PaginationList<Suppiler>> getPageSuppiler(PaginationInfo paginationInfo, Map paramMap) {
		PaginationList<Suppiler> suppilerList = selectPaginationList("Suppiler.getPageSuppilerByPage", paramMap,
				paginationInfo);
		long totalCount = paginationInfo.getTotalRecord();
		return Pair.of(totalCount, suppilerList);
	}

	@Override
	public long addSuppiler(Suppiler suppiler) {
		return this.getSqlSession().insert("Suppiler.addSuppiler", suppiler);
	}

	@Override
	public long updateSuppiler(Suppiler suppiler) {
		return this.getSqlSession().update("Suppiler.updateSuppiler", suppiler);

	}
	
	@Override
	public long updateSuppilerStatus(Suppiler suppiler) {
		return this.getSqlSession().update("Suppiler.updateSuppilerStatus", suppiler);

	}

	@Override
	public long getCount() {
		return this.getSqlSession().selectOne("Suppiler.getCount");
	}

	@Override
	public Suppiler getSuppilerById(long suppilerId) {
		return this.getSqlSession().selectOne("Suppiler.getSuppilerById", suppilerId);
	}
	
	public Suppiler getSupplierByStorageId(long storageId) {
		return this.getSqlSession().selectOne("Suppiler.getSupplierByStorageId", storageId);
	}

}
