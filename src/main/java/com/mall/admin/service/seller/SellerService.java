package com.mall.admin.service.seller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.seller.Seller;

public interface SellerService {
	public List<Seller> getAllSeller();

	public List<Seller> getPageSeller(PaginationInfo paginationInfo);
	
	public Pair<Long,PaginationList<Seller>> getPageSeller(PaginationInfo paginationInfo,Map map);
	
	public long addSeller(Seller seller);
	
	public long updateSeller(Seller seller);
	
	public long getCount();
	
	public Seller getSellerById(long id);

	public int deleteSeller(Long sellerId);
}
