package com.mall.admin.model.mybatis.seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.seller.SellerDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.seller.Seller;

@Repository
public class SellerDaoImpl extends BaseMallDaoImpl implements SellerDao {

	@Override
	public List<Seller> getAllSeller() {
		return this.getSqlSession().selectList("Seller.getAllSeller");
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationList<Seller> getPageSeller(PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Seller> sellerList = selectPaginationList("Seller.getPageSellerByPage", paginationInfo);
		return sellerList;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<Long, PaginationList<Seller>> getPageSeller(PaginationInfo paginationInfo, Map paramMap) {
		PaginationList<Seller> sellerList = selectPaginationList("Seller.getPageSellerByPage", paramMap,
				paginationInfo);
		long totalCount = paginationInfo.getTotalRecord();
		return Pair.of(totalCount, sellerList);
	}

	@Override
	public long addSeller(Seller seller) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("Seller.addSeller", seller);
	}

	@Override
	public long updateSeller(Seller seller) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Seller.updateSeller", seller);

	}

	@Override
	public long getCount() {
		return this.getSqlSession().selectOne("Seller.getCount");
	}

	@Override
	public Seller getSellerById(long sellerId) {
		return this.getSqlSession().selectOne("Seller.getSellerById", sellerId);
	}

	@Override
	public int deleteSeller(Long sellerId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Seller.deleteSeller", sellerId);
	}

}
