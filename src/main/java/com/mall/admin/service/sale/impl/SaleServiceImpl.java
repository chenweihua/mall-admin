package com.mall.admin.service.sale.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.sale.SaleDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.sale.SaleService;
import com.mall.admin.vo.sale.SaleInCollege;
import com.mall.admin.vo.sale.SaleInStorage;
import com.mall.admin.vo.sale.SaleSkuInfo;

@Service
public class SaleServiceImpl implements SaleService {
	@Autowired
	SaleDao saleDao;

	@Autowired
	BgGoodsService bggoodService;

	@Override
	public List<SaleInStorage> getSaleInRDCStorageInfo(Map paramMap, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return saleDao.getRdcStorageGoodsInfo(paramMap, paginationInfo);
	}

	@Override
	public List<SaleInStorage> getSaleInLDCStorageInfo(Map paramMap, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return saleDao.getLdcStorageGoodsInfo(paramMap, paginationInfo);
	}

	@Override
	public List<SaleInStorage> getSaleInVMStorageInfo(Map paramMap,
			PaginationInfo paginationInfo) {
		return saleDao.getVmStorageGoodsInfo(paramMap, paginationInfo);
	}

	@Override
	public List<SaleInCollege> getSaleInRdcStorageInCollege(long storageId, long collegeId, long bg_goods_id) {
		List<SaleInCollege> saleInCollegeList = saleDao.getRdcStorageGoodsInCollege(storageId, collegeId,
				bg_goods_id);
		if (saleInCollegeList != null && saleInCollegeList.size() > 0) {
			for (SaleInCollege saleInCollege : saleInCollegeList) {
				if (saleInCollege.getStatus() == 1) {
					saleInCollege.setStatusStr("待售");
				} else if (saleInCollege.getStatus() == 2) {
					saleInCollege.setStatusStr("在售");
				} else if (saleInCollege.getStatus() == 3) {
					saleInCollege.setStatusStr("售罄");
				} else {
					saleInCollege.setStatusStr("未知");
				}
				if (saleInCollege.getStock() > 999999) {
					saleInCollege.setStockStr("不限制");
				} else {
					saleInCollege.setStockStr(saleInCollege.getStock() + "");
				}
			}
		}

		return saleInCollegeList;
	}

	@Override
	public List<SaleInCollege> getSaleInLdcStorageInCollege(long storageId, long collegeId, long bg_goods_id) {
		// TODO Auto-generated method stub
		List<SaleInCollege> saleInCollegeList = saleDao.getLdcStorageGoodsInCollege(storageId, collegeId,
				bg_goods_id);
		if (saleInCollegeList != null && saleInCollegeList.size() > 0) {
			for (SaleInCollege saleInCollege : saleInCollegeList) {
				if (saleInCollege.getStatus() == 1) {
					saleInCollege.setStatusStr("待售");
				}
				if (saleInCollege.getStatus() == 2) {
					saleInCollege.setStatusStr("在售");
				}
				if (saleInCollege.getStatus() == 3) {
					saleInCollege.setStatusStr("售罄");
				}
				if (saleInCollege.getStock() > 999999) {
					saleInCollege.setStockStr("不限制");
				} else {
					saleInCollege.setStockStr(saleInCollege.getStock() + "");
				}
			}
		}
		return saleInCollegeList;
	}

	@Override
	public List<SaleInCollege> getSaleInVmStorageInCollege(long storageId,
			long collegeId, long bg_goods_id) {
		List<SaleInCollege> saleInCollegeList = saleDao.getVmStorageGoodsInCollege(storageId, collegeId,
				bg_goods_id);
		if (saleInCollegeList != null && saleInCollegeList.size() > 0) {
			for (SaleInCollege saleInCollege : saleInCollegeList) {
				if (saleInCollege.getStatus() == 1) {
					saleInCollege.setStatusStr("待售");
				} else if (saleInCollege.getStatus() == 2) {
					saleInCollege.setStatusStr("在售");
				} else if (saleInCollege.getStatus() == 3) {
					saleInCollege.setStatusStr("售罄");
				} else {
					saleInCollege.setStatusStr("未知");
				}
				if (saleInCollege.getStock() > 999999) {
					saleInCollege.setStockStr("不限制");
				} else {
					saleInCollege.setStockStr(saleInCollege.getStock() + "");
				}
			}
		}
		return saleInCollegeList;
	}

	@Override
	public List<SaleSkuInfo> getSaleSkuInfoByGoodsId(long bggoodsId, int goodsType) {
		if (goodsType == 1 || goodsType == 2) {
			return saleDao.getSkuInfoByGoodsId(bggoodsId);
		} else if (goodsType == 3) {
			return saleDao.getMoreSkuInfoByGoodsId(bggoodsId);
		}
		return null;
	}

	@Override
	public int queryStorageStock(long bg_goods_id, long storageId) {
		List<Integer> storageStockList = saleDao.queryStorageStock(bg_goods_id, storageId);
		if (storageStockList == null || storageStockList.size() != 1) {
			if(storageStockList != null && storageStockList.size() > 1 && isEqual(storageStockList)){
				//同一条码，上单时绑定的数量不同；或不同条码，数量都相同
				return storageStockList.get(0);
			}
			return 0;
		} else {
			return storageStockList.get(0);
		}
	}
	
	private boolean isEqual(List<Integer> list){
		boolean result = true;
		Integer defaultValue = list.get(0);
		for(Integer item : list){
			if(defaultValue != item){
				result = false;
			}
		}
		return result;
	}

}
