package com.mall.admin.service.wms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.wms.WmsGoodsDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityGoodsService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.util.impl.ZtreeUtilImpl;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

@Service
public class WmsGoodsServiceImpl implements WmsGoodsService {
	private static final Logger logger = LoggerFactory.getLogger(WmsGoodsServiceImpl.class);
	@Autowired
	WmsGoodsDao wmsGoodsDao;
	@Autowired
	StorageGoodsStockService storageGoodsStockService;
	@Autowired
	StorageService storageService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	ActivityGoodsService activityGoodsService;

	@Override
	public long insertOrUpdateWithStock(WmsGoods wmsGoods) {
		WmsGoods wmsGoodsTemp = wmsGoodsDao.getWmsGoodsById(wmsGoods.getWms_goods_id());
		if(wmsGoodsTemp == null){
			long ret = insert(wmsGoods);
			if(ret == -1L){
				return -1L;
			}
			wmsGoods.setWms_goods_id(ret);
		}else{
			update(wmsGoods);
		}
		//操作库存
		storageGoodsStockService.insertOrUpdateWithStock(wmsGoods);
		return wmsGoods.getWms_goods_id();
	}

	@Override
	public long insert(WmsGoods wmsGoods) {
		return wmsGoodsDao.insert(wmsGoods);
	}

	@Override
	public int update(WmsGoods wmsGoods) {
		// TODO Auto-generated method stub
		return wmsGoodsDao.update(wmsGoods);
	}

	@Override
	public List<WmsGoods> getWmsGoods(Map paramMap, PaginationInfo paginationInfo) {
		return wmsGoodsDao.getWmsGoodsList(paramMap, paginationInfo);
	}

	@Override
	public WmsGoods queryById(Long id) {
		// TODO Auto-generated method stub
		return wmsGoodsDao.getWmsGoodsById(id);
	}

	@Override
	public void setZtreeBeanStatus(ZtreeBean ztreeBean, long wms_goods_id) {
		if (ztreeBean == null) {
			return;
		}
		// 获得该商品在那些仓有数据
		List<StorageGoodsStock> storageGoodsList = storageGoodsStockService
				.getStorageGoodsStockByGoodsId(wms_goods_id);
		if (storageGoodsList == null || storageGoodsList.size() == 0) {
			return;
		}
		List<Long> wms_goods_id_list = new ArrayList<Long>();
		for (StorageGoodsStock bean : storageGoodsList) {
			// 所在仓是可用的。
			if (bean.is_del == 0) {
				wms_goods_id_list.add(bean.storage_id);
			}
		}
		if (wms_goods_id_list.size() == 0) {
			return;
		}
		new ZtreeUtilImpl().setZtreeStatus(ztreeBean, wms_goods_id_list);
		// List<ZtreeBean> allChildrenZtreeList =
		// ztreeBean.getChildren();
		// if (allChildrenZtreeList == null ||
		// allChildrenZtreeList.size() == 0) {
		// return;
		// }
		// for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
		// boolean childChecked = false;
		// if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
		// // RDC仓
		// List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen
		// .getChildren();
		// if (rdcStorageChildrenZtree == null
		// || rdcStorageChildrenZtree.size() == 0) {
		// continue;
		// }
		// for (ZtreeBean bean : rdcStorageChildrenZtree) {
		// if (wms_goods_id_list.contains(Long.parseLong(bean.id))) {
		// bean.checked = "true";
		// childrenZtreen.checked = "true";
		// ztreeBean.checked = "true";
		// }
		// }
		//
		// } else if
		// (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
		// // RDC仓
		// List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen
		// .getChildren();
		// if (ldcStorageChildrenZtree == null
		// || ldcStorageChildrenZtree.size() == 0) {
		// continue;
		// }
		// for (ZtreeBean citybean : ldcStorageChildrenZtree) {
		// List<ZtreeBean> cityChildrenZtree = citybean.getChildren();
		// if (cityChildrenZtree == null
		// || cityChildrenZtree.size() == 0) {
		// continue;
		// }
		// for (ZtreeBean storagebean : cityChildrenZtree) {
		// if (wms_goods_id_list.contains(Long
		// .parseLong(storagebean.id))) {
		// storagebean.checked = "true";
		// citybean.checked = "true";
		// childrenZtreen.checked = "true";
		// ztreeBean.checked = "true";
		// }
		// }
		// }
		// }
		// }
	}

	@Override
	public void setWmsGoodsRegion(ZtreeBean ztreeBean, long wms_goods_id, User user) {
		if (ztreeBean == null) {
			return;
		}
		// 获得该商品在当前那些仓有数据
		List<StorageGoodsStock> storageGoodsList = storageGoodsStockService
				.getStorageGoodsStockByGoodsId(wms_goods_id);
		List<Long> storage_id_old_list = new ArrayList<Long>();
		List<Long> storage_id_new_list = new ArrayList<Long>();
		if (storageGoodsList != null && storageGoodsList.size() > 0) {
			for (StorageGoodsStock bean : storageGoodsList) {
				// 所在仓是可用的。
				if (bean.is_del == 0) {
					storage_id_old_list.add(bean.storage_id);
				}
			}
		}

		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();
		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return;
		}
		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean bean : rdcStorageChildrenZtree) {
					// 已被选中
					if (Constants.ZTREECHECKED.equals(bean.checked)) {
						storage_id_new_list.add(Long.parseLong(bean.id));
						if (!storage_id_old_list.contains(Long.parseLong(bean.id))) {
							// 新增的仓库，添加wms_goods_id和storage_id的关系
							storageGoodsStockService.insert(wms_goods_id,
									Long.parseLong(bean.id), Storage.RDC_STORAGE,
									user.user_id);
						}
					} else {
						childrenZtreen.isAllChecked = false;
						ztreeBean.isAllChecked = false;
					}
				}
			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean citybean : ldcStorageChildrenZtree) {
					List<ZtreeBean> cityChildrenZtree = citybean.getChildren();
					if (cityChildrenZtree == null || cityChildrenZtree.size() == 0) {
						continue;
					}
					for (ZtreeBean storagebean : cityChildrenZtree) {

						if (Constants.ZTREECHECKED.equals(storagebean.checked)) {
							storage_id_new_list.add(Long.parseLong(storagebean.id));
							if (!storage_id_old_list.contains(Long
									.parseLong(storagebean.id))) {
								// 新增的仓库，添加wms_goods_id和storage_id的关系
								// 新增的仓库，添加wms_goods_id和storage_id的关系
								storageGoodsStockService.insert(wms_goods_id,
										Long.parseLong(storagebean.id),
										Storage.LDC_STORAGE, user.user_id);
							}
						} else {
							// 子节点没有被全选，
							citybean.isAllChecked = false;
							childrenZtreen.isAllChecked = false;
							ztreeBean.isAllChecked = false;
						}
					}
				}
			}
		}
		for (Long storage_id_temp : storage_id_old_list) {
			if (!storage_id_new_list.contains(storage_id_temp)) {
				// 说明是被删除的id 修改状态为禁用
				storageGoodsStockService.updateGoodsInStorageStatus(wms_goods_id, storage_id_temp, 1,
						user.user_id);
				// 更新该商品售卖的活动
				Storage storage = storageService.getStorageById(storage_id_temp);
				int storageType = storage.getStorageType();
				List<College> collegeList = new ArrayList<College>();
				// 获得被删除仓库对应的学校，
				if (storageType == 0) {
					// rdc
					collegeList = collegeService.getListByRdcStorageId(storage.getStorageId());
				} else if (storageType == 1) {
					// ldc
					collegeList = collegeService.getListByLdcStorageId(storage.getStorageId());
				}

				// 获得供应链商品对应的商城单品（组合品）模板商品
				List<BgGoods> singleBgGoodsList = bgGoodsService
						.getSingleBgGoodsListByWmsGoodsId(wms_goods_id);
				for (College college : collegeList) {
					for (BgGoods bgGoods : singleBgGoodsList) {
						// 更新活动上商品的状态为不可用
						activityGoodsService.updateStatusByBgGoodsIdAndCollegeId(
								bgGoods.getBgGoodsId(), college.getCollegeId(), 1,
								storageType);
						// 更新前台商品不可用
						bgGoodsService.batchDelete(bgGoods.getBgGoodsId(), -1,
								college.getCollegeId(), storageType);
					}
				}
				// 获得供应链商品对应的商城聚合品模板商品
				List<BgGoods> bgGoodsList = bgGoodsService.getMultBgGoodsListByWmsGoodsId(wms_goods_id);
				for (College college : collegeList) {
					for (BgGoods bgGoods : bgGoodsList) {
						// 更新前台商品不可用
						// 更新活动上商品的状态为不可用
						activityGoodsService.updateStatusByBgGoodsIdAndCollegeId(
								bgGoods.getBgGoodsId(), college.getCollegeId(), 1,
								storageType);
						bgGoodsService.batchDelete(bgGoods.getBgGoodsId(), -1,
								college.getCollegeId(), storageType);
					}
				}

			}
		}
		if (user.is_all_storage == 1 || user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG) {
			// 把原来的范围信息删除。重新插入新的范围信息
			wmsGoodsDao.deleteWmsGoodsRegion(wms_goods_id);
			// 负责所有仓 根据用户设置的范围重新设置当前范围
			for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
				if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
					if (childrenZtreen.isAllChecked) {
						// 负责全部的rdc仓
						wmsGoodsDao.insertWmsGoodsRegion(wms_goods_id,
								Constants.ALLRDCSTORAGEID,
								Constants.ALLSTORAGE_REGION_TYPE);
					}
				} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
					if (childrenZtreen.isAllChecked) {
						// 负责全部的ldc仓
						wmsGoodsDao.insertWmsGoodsRegion(wms_goods_id,
								Constants.ALLLDCSTORAGEID,
								Constants.ALLSTORAGE_REGION_TYPE);
					} else {
						List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
						if (ldcStorageChildrenZtree == null
								|| ldcStorageChildrenZtree.size() == 0) {
							continue;
						}
						for (ZtreeBean citybean : ldcStorageChildrenZtree) {
							if (citybean.isAllChecked) {
								// 负责某个城市
								String cityZTreeId = citybean.getId();
								cityZTreeId = cityZTreeId.substring(5);
								if (cityZTreeId.matches("[0-9]{1,}")) {
									long cityId = Long.parseLong(cityZTreeId);
									wmsGoodsDao.insertWmsGoodsRegion(wms_goods_id,
											cityId,
											Constants.CITY_REGION_TYPE);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void copyRdcWmsGoodsToStorage(long storageId, long userId) {
		List<Long> wms_goods_id_list = wmsGoodsDao.getWmsGoodsIdByRegionId(Constants.ALLRDCSTORAGEID,
				Constants.ALLSTORAGE_REGION_TYPE);
		if (wms_goods_id_list != null && wms_goods_id_list.size() > 0) {
			for (Long wms_goods_id : wms_goods_id_list) {
				storageGoodsStockService.insert(wms_goods_id, storageId, 0, userId);
			}
		}

	}

	@Override
	public void copyLdcWmsGoodsToStorage(Storage storage, long cityId, long userId) throws Exception {
		if (storage == null) {
			logger.info("WmsGoodsServiceImpl.copyLdcWmsGoodsToStorage.storage is null,cityId{},userId{}",
					cityId, userId);
			throw new Exception("对应仓库是空");
		}
		if (storage.getLdcType() == Storage.HIDE_LDC_TIME) {// 如果是商超，隐藏29分钟达
			return;
		}
		// 复制所有LDC仓的信息
		List<Long> wms_goods_id_list = wmsGoodsDao.getWmsGoodsIdByRegionId(Constants.ALLLDCSTORAGEID,
				Constants.ALLSTORAGE_REGION_TYPE);
		if (wms_goods_id_list != null && wms_goods_id_list.size() > 0) {
			for (Long wms_goods_id : wms_goods_id_list) {
				storageGoodsStockService.insert(wms_goods_id, storage.getStorageId(), 1, userId);
			}
		}
		// 复制归属城市的信息
		wms_goods_id_list = wmsGoodsDao.getWmsGoodsIdByRegionId(cityId, Constants.CITY_REGION_TYPE);
		if (wms_goods_id_list != null && wms_goods_id_list.size() > 0) {
			for (Long wms_goods_id : wms_goods_id_list) {
				storageGoodsStockService.insert(wms_goods_id, storage.getStorageId(), 1, userId);
			}
		}
	}

	@Override
	public WmsGoods queryById(long wms_goods_id) {
		// TODO Auto-generated method stub
		return wmsGoodsDao.queryById(wms_goods_id);
	}

	@Override
	public String importWmsGoodsList(List<String[]> data, User user, Long storageId) {
		List<WmsGoods> goodsList = new ArrayList<WmsGoods>();
		List<String> gbmList = new ArrayList<String>();

		StringBuffer message = new StringBuffer("");
		for (int i = 0; i < data.size(); i++) {
			String[] rowData = data.get(i);
			if (rowData.length != 10) {
				message.append("第" + (i + 2) + "行记录不正确~\n");
			}
			if (Strings.isEmpty(rowData[0])) {
				message.append("第" + (i + 2) + "行记录商品名称为空~\n");
			}
			if (Strings.isEmpty(rowData[1]) || !rowData[1].matches("^[0-9a-zA-Z]{1,20}$")) {
				message.append("第" + (i + 2) + "行记录商品条码为空或格式错误~\n");
			}
			// 商品单位名称不能为空 0522
			if (Strings.isEmpty(rowData[6])) {
				message.append("第" + (i + 2) + "行记录商品单位为空~\n");
			}

			if (storageId == 0) {
				// 小麦商品验证条码
				List<WmsGoods> wmsGoodList = getByGbmAndStorageId(rowData[1].trim(), storageId);
				if (wmsGoodList != null && wmsGoodList.size() > 0) {
					message.append("第" + (i + 2) + "行记录错误，商品条码" + rowData[1] + "已存在~\n");
				}
			}

			if (gbmList.contains(rowData[1])) {
				message.append("第" + (i + 2) + "行记录错误，文件中商品条码出现重复~\n");
			} else {
				gbmList.add(rowData[1]);
			}
			WmsGoods goods = new WmsGoods();
			goods.setStorageId(storageId);
			goods.wms_goods_name = rowData[0];
			goods.wms_goods_gbm = rowData[1];
			goods.short_name = rowData[2] == null ? "" : rowData[2];
			goods.brand = rowData[3] == null ? "" : rowData[3];
			goods.catg_name = rowData[4] == null ? "" : rowData[4];
			goods.sale_spec = rowData[5] == null ? "" : rowData[5];
			goods.unit = rowData[6] == null ? "" : rowData[6];
			goods.package_spec = rowData[7] == null ? "" : rowData[7];
			goods.origin_place = rowData[8] == null ? "" : rowData[8];
			goods.shelf_life = rowData[9] == null ? "" : rowData[9];
			goods.operator_id = user.getUser_id();
			goods.status = 0;
			goodsList.add(goods);
		}
		if (message.toString().trim().length() > 0) {
			return message.toString();
		}
		if (storageId == 0) {
			for (WmsGoods goods : goodsList) {
				insert(goods);
			}
		} else {
			for (WmsGoods goods : goodsList) {
				goods.setWms_goods_gbm(Constants.SF_WMS_PREFIX + goods.getWms_goods_gbm());
				long wmsGoodsId = insert(goods);
				storageGoodsStockService
						.insert(wmsGoodsId, storageId, Storage.VM_STORAGE, user.user_id);
			}
		}
		return "0";
	}

	@Override
	public WmsGoods getWmsGoodsByStorageGoodId(long goodsId) {
		WmsGoods wmsGoods = wmsGoodsDao.queryById(goodsId);
		return wmsGoods;
	}

	@Override
	public void synchWmsGoods(WmsGoods wmsGoods) {
		WmsGoods wmsGoods_temp = wmsGoodsDao.queryByGbmAndNo(wmsGoods.getWms_goods_gbm(),
				wmsGoods.getWmsGoodsCode());
		if (wmsGoods_temp == null) {
			List<WmsGoods> wmsGoodsList = getByGbmAndStorageId(wmsGoods.getWms_goods_gbm(),
					Constants.XM_WMS_STORAGE_ID);
			if (wmsGoodsList == null || wmsGoodsList.size() == 0) {
				// 如果不存在，则添加
				wmsGoodsDao.insert(wmsGoods_temp);
			} else {
				// 如果条码存在，则更新
				wmsGoods_temp = wmsGoodsList.get(0);
				wmsGoods.setWms_goods_id(wmsGoods_temp.getWms_goods_id());
				wmsGoodsDao.update(wmsGoods);
			}
		} else {
			// 如果条码和编码都存在，则更新
			wmsGoods.setWms_goods_id(wmsGoods_temp.getWms_goods_id());
			wmsGoodsDao.update(wmsGoods);
		}

	}

	@Override
	public List<WmsGoods> getByGbmAndStorageId(String gbm, Long storageId) {
		return wmsGoodsDao.getByGbmAndStorageId(gbm, storageId);
	}

	/**
	 * 根据skuId查询
	 * 
	 * @param bgSkuId
	 * @return
	 */
	@Override
	public List<WmsGoods4BgSkuInfo> queryWmsGoods4BgSku(long bgSkuId) {
		return wmsGoodsDao.queryWmsGoods4BgSku(bgSkuId);
	}

	@Override
	public Map<Long, List<WmsGoods4BgSkuInfo>> getWmsGoods4BgSkuMap() {
		Map<Long, List<WmsGoods4BgSkuInfo>> wmsGoods4BgSkuMap = new HashMap<Long, List<WmsGoods4BgSkuInfo>>();
		List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuInfosList = wmsGoodsDao.getWmsGoods4BgSkuList();
		if(wmsGoods4BgSkuInfosList != null && wmsGoods4BgSkuInfosList.size() > 0){
			for(WmsGoods4BgSkuInfo wmsGoods4BgSkuInfo: wmsGoods4BgSkuInfosList){
				if(wmsGoods4BgSkuMap.containsKey(wmsGoods4BgSkuInfo.getBgSkuId())){
					wmsGoods4BgSkuMap.get(wmsGoods4BgSkuInfo.getBgSkuId()).add(wmsGoods4BgSkuInfo);
				}else{
					List<WmsGoods4BgSkuInfo> wmsBgSkuInfos = new ArrayList<>();
					wmsBgSkuInfos.add(wmsGoods4BgSkuInfo);
					wmsGoods4BgSkuMap.put(wmsGoods4BgSkuInfo.getBgSkuId(), wmsBgSkuInfos);
				}
			}
		}
		return wmsGoods4BgSkuMap;
	}
}
