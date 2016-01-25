package com.mall.admin.model.mybatis.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.sale.SaleDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.sale.SaleInCollege;
import com.mall.admin.vo.sale.SaleInStorage;
import com.mall.admin.vo.sale.SaleSkuInfo;

@Repository
public class SaleDaoImpl extends BaseMallDaoImpl implements SaleDao {

	@Override
	public List<SaleInStorage> getRdcStorageGoodsInfo(Map paramMap, PaginationInfo paginationInfo) {
		return selectPaginationList("Sale.getRdcStorageGoodsInfoByPage", paramMap, paginationInfo);
		//return saleInStorageList;
	}

	@Override
	public List<SaleInStorage> getLdcStorageGoodsInfo(Map paramMap, PaginationInfo paginationInfo) {
		return selectPaginationList("Sale.getLdcStorageGoodsInfoByPage", paramMap, paginationInfo);
	}

	@Override
	public List<SaleInStorage> getVmStorageGoodsInfo(Map paramMap,
			PaginationInfo paginationInfo) {
		return selectPaginationList("Sale.getVmStorageGoodsInfoByPage", paramMap, paginationInfo);
	}

	@Override
	public List<SaleInCollege> getRdcStorageGoodsInCollege(long storageId, long collegeId, long bg_goods_id) {
		// TODO Auto-generated method stub
		Map<String, Long> map = new HashMap<String, Long>();
		if (collegeId != 0) {
			map.put("collegeId", collegeId);
		}
		map.put("bggoodsId", bg_goods_id);
		map.put("storageId", storageId);

		return this.getSqlSession().selectList("Sale.getRdcStorageGoodsInCollege", map);
	}

	@Override
	public List<SaleInCollege> getLdcStorageGoodsInCollege(long storageId, long collegeId, long bg_goods_id) {
		Map<String, Long> map = new HashMap<String, Long>();
		if (collegeId != 0) {
			// 如果学校为空，则查询的是该仓下所有学校的信息
			map.put("collegeId", collegeId);
		}
		map.put("bggoodsId", bg_goods_id);
		map.put("storageId", storageId);
		return this.getSqlSession().selectList("Sale.getLdcStorageGoodsInCollege", map);
	}
	
	@Override
	public List<SaleInCollege> getVmStorageGoodsInCollege(long storageId,
			long collegeId, long bg_goods_id) {
		Map<String, Long> map = new HashMap<String, Long>();
		if (collegeId != 0) {
			// 如果学校为空，则查询的是该仓下所有学校的信息
			map.put("collegeId", collegeId);
		}
		map.put("bggoodsId", bg_goods_id);
		map.put("storageId", storageId);
		return this.getSqlSession().selectList("Sale.getVmStorageGoodsInCollege", map);
	}

	@Override
	public List<SaleSkuInfo> getSkuInfoByGoodsId(long bggoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Sale.getSkuMap", bggoodsId);
	}

	@Override
	public List<SaleSkuInfo> getMoreSkuInfoByGoodsId(long bggoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Sale.getMoreSkuMap", bggoodsId);
	}

	@Override
	public List<Integer> queryStorageStock(long bg_goods_id, long storageId) {
		Map param = new HashMap();
		param.put("bgGoodsId", bg_goods_id);
		param.put("storageId", storageId);
		return this.getSqlSession().selectList("Sale.queryStorageStock", param);
	}

}
