package com.mall.admin.service.seller.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.seller.SellerDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.seller.SellerService;
import com.mall.admin.vo.seller.Seller;


@Service
public class SellerServiceImpl implements SellerService{

	@Autowired
	private SellerDao sellerDao;

	@Override
	public List<Seller> getAllSeller() {
		return sellerDao.getAllSeller();
	}

	@Override
	public List<Seller> getPageSeller(PaginationInfo paginationInfo) {
		return sellerDao.getPageSeller(paginationInfo);
	}

	@Override
	public long addSeller(Seller seller) {
		// TODO Auto-generated method stub;
		return sellerDao.addSeller(seller);
	}

	@Override
	public long updateSeller(Seller seller) {
		// TODO Auto-generated method stub
		return sellerDao.updateSeller(seller);
	}
	
	public long getCount(){
		return sellerDao.getCount();
	}

	@Override
	public Pair<Long,PaginationList<Seller>> getPageSeller(PaginationInfo paginationInfo,
			Map map) {
		// TODO Auto-generated method stub
		return sellerDao.getPageSeller(paginationInfo,map);
	}
	
	@Override
	public Seller getSellerById(long id) {
		return sellerDao.getSellerById(id);
	}

	@Override
	public int deleteSeller(Long sellerId) {
		// TODO Auto-generated method stub
		return sellerDao.deleteSeller(sellerId);
	}

}
