package com.mall.admin.model.dao.supplier;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.supplier.Suppiler;


public interface SupplierDao {
	public List<Suppiler> getAllSuppiler();

	public List<Suppiler> getPageSuppiler(PaginationInfo paginationInfo);
	
	public long addSuppiler(Suppiler suppiler);
	
	public long updateSuppiler(Suppiler suppiler);
	
	public long updateSuppilerStatus(Suppiler suppiler);

	public Pair<Long,PaginationList<Suppiler>> getPageSuppiler(PaginationInfo paginationInfo,
			Map<String, Object> paramMap);
	
	public long getCount();
	
	public Suppiler getSuppilerById(long id);
	
	public Suppiler getSupplierByStorageId(long storageId);

}
