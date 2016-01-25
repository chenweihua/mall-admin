package com.mall.admin.model.mybatis.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.merchant.MerchantDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.merchant.MerChantSeq;
import com.mall.admin.vo.merchant.Merchant;

@Repository
public class MerchantDaoImpl extends BaseMallDaoImpl implements MerchantDao {

	@Override
	public List<Merchant> getMerchantList(String merchantName, String shopOwner, int status,
			PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		if (merchantName != null) {
			map.put("merchantName", merchantName);
		}
		if (shopOwner != null) {
			map.put("shopOwner", shopOwner);
		}
		if (status > 0) {
			map.put("status", status);
		}
		return selectPaginationList("Merchant.selectListByPage", map, paginationInfo);
	}

	@Override
	public int insert(Merchant merchant) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("Merchant.insertMerchant", merchant);
	}

	@Override
	public int update(Merchant merchant) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Merchant.updateMerchant", merchant);
	}

	@Override
	public int setStatus(int status, long merchantId) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("merchantId", merchantId);
		map.put("status", status);
		return this.getSqlSession().update("Merchant.updateStatus", map);
	}

	@Override
	public Merchant getMerchantById(long merchantId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Merchant.selectMerchantById", merchantId);
	}

	@Override
	public MerChantSeq getMaxSeq(long seqId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Merchant.selectMaxSeq", seqId);
	}

	@Override
	public int updateMaxSeq(long seqId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Merchant.updateMaxSeq", seqId);
	}

	@Override
	public Merchant getMerchantByUserId(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Merchant.selectMerchantByUserId", userId);
	}

	@Override
	public Merchant getMerchantByName(String merchantName) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Merchant.selectMerchantByName", merchantName);
	}

}
