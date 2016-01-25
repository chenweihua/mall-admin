package com.mall.admin.service.supplier;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.supplier.Suppiler;

public interface SupplierService {

	public List<Suppiler> getPageSupplier(PaginationInfo paginationInfo);
	
	public Pair<Long,PaginationList<Suppiler>> getPageSupplier(PaginationInfo paginationInfo, Map<String, Object> map);
	
	public boolean addSupplier(Suppiler supplier) throws Exception;
	
	public boolean updateSupplier(Suppiler supplier) throws Exception;
	
	public boolean updateSuppilerStatus(Suppiler suppiler);
	
	public long getCount();
	
	public Suppiler getSupplierById(long id);
	
	public Suppiler getSupplierByStorageId(long storageId);

}
