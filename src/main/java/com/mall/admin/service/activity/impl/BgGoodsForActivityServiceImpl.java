package com.mall.admin.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.bean.activity.ActivityGoodsApplyBean;
import com.mall.admin.model.bean.activity.ActivitySkuApplyBean;
import com.mall.admin.model.dao.activity.ActivityGoodsDao;
import com.mall.admin.model.dao.activity.ActivitySkuDao;
import com.mall.admin.model.dao.activity.BgGoodsForActivityDao;
import com.mall.admin.model.dao.activity.BgSkuForActivityDao;
import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityGoods;
import com.mall.admin.vo.activity.ActivitySku;
import com.mall.admin.vo.activity.BgGoodsForActivity;
import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;

@Service
public class BgGoodsForActivityServiceImpl implements BgGoodsForActivityService {

	@Autowired
	BgSkuGbmService bgSkuGbmService;

	@Autowired
	StorageGoodsStockService stockService;
	@Autowired
	ActivityService activityService;

	@Autowired
	StorageService storageService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	BgGoodsService goodsService;
	@Autowired
	BgSkuService bgSkuService;
	@Autowired
	BgGoodsForActivityDao bgGoodsForActivityDao;
	@Autowired
	BgSkuForActivityDao bgSkuForActivityDao;
	@Autowired
	ActivityGoodsDao activityGoodsDao;
	@Autowired
	ActivitySkuDao activitySkuDao;
	@Autowired
	BgSkuDao bgSkuDao;
	@Autowired
	private StorageCollegeService storageCollegeService;

	@Override
	public BgGoodsForActivity getById(long activityBgGoodsId) {
		return bgGoodsForActivityDao.getById(activityBgGoodsId);
	}


	/**
	 * 获得后台商品可售卖的学校列表
	 */

	@Override
	public List<Long> getCollegeIds(long bgGoodsId, int storageType) {
		
	/*	// 查询所以的rdc仓或ldc仓
		List<Storage> storageList = null;
		if (storageType == Storage.RDC_STORAGE) {
			storageList = storageService.getAllRdcStorage();
		} else if (storageType == Storage.LDC_STORAGE) {
			storageList = storageService.getAllLdcStorage();
		} else if(storageType == Storage.VM_STORAGE){
			storageList = storageService.getVMStorage();
		}
		if (storageList == null || storageList.size() == 0) {
			return null;
		}
		// 将仓放到map中
		Map<Long, Storage> storageMap = new HashMap<Long, Storage>();
		for (Storage storage : storageList) {
			storageMap.put(storage.getStorageId(), storage);
		}*/
		BgGoodsDto bgGoodsDto = goodsService.getBgGoodsDtoById(bgGoodsId);
		BgGoods bgGoods = bgGoodsDto.getBgGoods();
		List<Long> wmsGoodsIdList = new ArrayList<Long>();
		if (bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_SINGLE || bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_GROUP) {
			wmsGoodsIdList = bgSkuGbmService.getWmsGoodsIdByBgGoodsId(bgGoodsId);
		} else {
			wmsGoodsIdList = bgSkuGbmService.getWmsGoodsIdByBgPolyGoodsId(bgGoodsId);
		}

		List<List<Long>> allCollegeList = new ArrayList<List<Long>>();
		for (Long wmsGoodsId : wmsGoodsIdList) {
			List<Long> collegeIdList = new ArrayList<Long>();
			List<Long> collegeIdList_temp = new ArrayList<Long>();
			List<Long> storageIdList = stockService.getStorageIdByWmsGoodsId(wmsGoodsId, storageType);
			for (Long storageId : storageIdList) {
				List<College> collegeList = new ArrayList<College>();
				Storage storage = StroageConstant.getStorageById(storageId);
				if (storage == null) {
					continue;
				}
				if (storageType == Storage.RDC_STORAGE || storageType == Storage.LDC_STORAGE || storageType == Storage.VM_STORAGE) {
					// 查询到的仓必须和选择的类型一致
					if (storage.getStorageType() == storageType) {
						if (storage.getStorageType() == Storage.RDC_STORAGE) {
							collegeList = collegeService.getListByRdcStorageId(storage
									.getStorageId());
						} else if(storage.getStorageType() == Storage.LDC_STORAGE){
							collegeList = collegeService.getListByLdcStorageId(storage
									.getStorageId());
						}else if(storage.getStorageType() == Storage.VM_STORAGE){
							collegeIdList_temp = storageCollegeService.getCollegeIdListByStorageId(storage
									.getStorageId());
						}
					}
				} else {
					if (storage.getStorageType() == Storage.RDC_STORAGE) {
						collegeList = collegeService.getListByRdcStorageId(storage
								.getStorageId());
					} else if(storage.getStorageType() == Storage.LDC_STORAGE) {
						collegeList = collegeService.getListByLdcStorageId(storage
								.getStorageId());
					}else if(storage.getStorageType() == Storage.VM_STORAGE){
						collegeIdList_temp = storageCollegeService.getCollegeIdListByStorageId(storage
								.getStorageId());
					}
				}
				if (collegeList != null && collegeList.size() > 0) {
					for (College college : collegeList) {
						if (!collegeIdList.contains(college.getCollegeId())) {
							collegeIdList.add(college.getCollegeId());
						}
					}
				}
				if (collegeIdList_temp != null && collegeIdList_temp.size() > 0) {
					for (Long collegeId : collegeIdList_temp) {
						if (!collegeIdList.contains(collegeId)) {
							collegeIdList.add(collegeId);
						}
					}
				}
			}
			allCollegeList.add(collegeIdList);
		}
		if (allCollegeList != null && allCollegeList.size() > 0) {
			if (allCollegeList.size() == 1) {
				return allCollegeList.get(0);
			} else {
				List<Long> collegeList = allCollegeList.get(0);
				for (List<Long> collegeList_temp : allCollegeList) {
					collegeList.retainAll(collegeList_temp);
				}
				return collegeList;
			}
		} else {
			return new ArrayList<Long>();
		}
	}
	
	
	/**
	 * 将根据wmsGoodsId查询storageId再循环遍历tb_college
	 * 改为关联查询tb_college和tb_storage_goods_stock
	 * 在storageId数量多的情况下，可以通过减少查询db次数，缩短时间
	 * @param bgGoodsId
	 * @param storageType
	 * @return
	 */
	public List<Long> getCollegeIdsV2(long bgGoodsId, int storageType) {
		
		
		BgGoodsDto bgGoodsDto = goodsService.getBgGoodsDtoById(bgGoodsId);
		BgGoods bgGoods = bgGoodsDto.getBgGoods();
		List<Long> wmsGoodsIdList = new ArrayList<Long>();
		if (bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_SINGLE || bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_GROUP) {
			wmsGoodsIdList = bgSkuGbmService.getWmsGoodsIdByBgGoodsId(bgGoodsId);
		} else {
			wmsGoodsIdList = bgSkuGbmService.getWmsGoodsIdByBgPolyGoodsId(bgGoodsId);
		}

		List<List<Long>> allCollegeList = new ArrayList<List<Long>>();
		for (Long wmsGoodsId : wmsGoodsIdList) {
			List<Long> collegeIdList = new ArrayList<Long>();
			//List<Long> collegeIdList_temp = new ArrayList<Long>();
			//List<Long> storageIdList = stockService.getStorageIdByWmsGoodsId(wmsGoodsId, storageType);
			//System.out.println("storages size=" + storageIdList.size());
			if(storageType == Storage.RDC_STORAGE) {
				collegeIdList = stockService.getCollegeIdsBybgGoodsIdWithRdc(wmsGoodsId);
			} else if(storageType == Storage.LDC_STORAGE) {
				collegeIdList = stockService.getCollegeIdsBybgGoodsIdWithLdc(wmsGoodsId);
			} else if(storageType == Storage.VM_STORAGE) {
				collegeIdList = stockService.getCollegeIdsBybgGoodsIdWithThirddc(wmsGoodsId);
			} else {
				LogConstant.mallLog.error("bgGoodsId {},storageType {} ,storageType 不识别", bgGoodsId, storageType);
			}
			/*
			for (Long storageId : storageIdList) {
				List<College> collegeList = new ArrayList<College>();
				Storage storage = StroageConstant.getStorageById(storageId);
				if (storage == null) {
					continue;
				}
				if (storageType == Storage.RDC_STORAGE || storageType == Storage.LDC_STORAGE || storageType == Storage.VM_STORAGE) {
					// 查询到的仓必须和选择的类型一致
					if (storage.getStorageType() == storageType) {
						long startTime3 = System.currentTimeMillis();
						if (storage.getStorageType() == Storage.RDC_STORAGE) {
							List<College> cacheResult = rdcCacheMap.get(storage.getStorageId());
							if(cacheResult != null) {
								collegeList = cacheResult;
								System.out.println("hit...");
							} else {
								collegeList = collegeService.getListByRdcStorageId(storage
									.getStorageId());
								rdcCacheMap.put(storage.getStorageId(), collegeList);
							}
							//System.out.println("======r" + (storage.getStorageId() + "  ") + (System.currentTimeMillis() - startTime3));
						} else if(storage.getStorageType() == Storage.LDC_STORAGE){
							List<College> cacheResult = ldcCacheMap.get(storage.getStorageId());
							if(cacheResult != null) {
								collegeList = cacheResult;
								System.out.println("hit...");
							} else {
							collegeList = collegeService.getListByLdcStorageId(storage
									.getStorageId());
							ldcCacheMap.put(storage.getStorageId(), collegeList);
							}
							//System.out.println("======L" + (storage.getStorageId() + "  ") + (System.currentTimeMillis() - startTime3));
						}else if(storage.getStorageType() == Storage.VM_STORAGE){
							List<Long> cacheResult = thirdCacheMap.get(storage.getStorageId());
							if(cacheResult != null) {
								collegeIdList_temp = cacheResult;
								System.out.println("hit...");
							} else {
							collegeIdList_temp = storageCollegeService.getCollegeIdListByStorageId(storage
									.getStorageId());
							thirdCacheMap.put(storage.getStorageId(), collegeIdList_temp);
							}
							//System.out.println("======q" + (storage.getStorageId() + "  ") + (System.currentTimeMillis() - startTime3));
						}
					}
				} else {
					if (storage.getStorageType() == Storage.RDC_STORAGE) {
						collegeList = collegeService.getListByRdcStorageId(storage
								.getStorageId());
					} else if(storage.getStorageType() == Storage.LDC_STORAGE) {
						collegeList = collegeService.getListByLdcStorageId(storage
								.getStorageId());
					}else if(storage.getStorageType() == Storage.VM_STORAGE){
						collegeIdList_temp = storageCollegeService.getCollegeIdListByStorageId(storage
								.getStorageId());
					}
				}
				if (collegeList != null && collegeList.size() > 0) {
					for (College college : collegeList) {
						if (!collegeIdList.contains(college.getCollegeId())) {
							collegeIdList.add(college.getCollegeId());
						}
					}
				}
				if (collegeIdList_temp != null && collegeIdList_temp.size() > 0) {
					for (Long collegeId : collegeIdList_temp) {
						if (!collegeIdList.contains(collegeId)) {
							collegeIdList.add(collegeId);
						}
					}
				}
			}
			*/
			allCollegeList.add(collegeIdList);
		}
		if (allCollegeList != null && allCollegeList.size() > 0) {
			if (allCollegeList.size() == 1) {
				//System.out.println("===" + (System.currentTimeMillis() - startTime));
				return allCollegeList.get(0);
			} else {
				List<Long> collegeList = allCollegeList.get(0);
				for (List<Long> collegeList_temp : allCollegeList) {
					collegeList.retainAll(collegeList_temp);
				}
				//System.out.println("===" + (System.currentTimeMillis() - startTime));
				return collegeList;
			}
		} else {
			//System.out.println("===" + (System.currentTimeMillis() - startTime));
			return new ArrayList<Long>();
		}
		
	}
	
	
	/**
	 * 判断给定的商品所能销售的学校
	 * 是否包含
	 * 给定的活动的学校的全集
	 * @return 所有不在商品所属学校范围，但在活动所属学校范围的学校
	 */
	@Override
	public List<Long> containsAllActivityCollege(Long bgGoodsId,int storageType,Long activityId) {
		BgGoodsDto bgGoodsDto = goodsService.getBgGoodsDtoById(bgGoodsId);
		BgGoods bgGoods = bgGoodsDto.getBgGoods();
		/** 获得商品所属仓库，再根据仓库获得仓库下的学校，得到商品可售卖的学校列表 */
		List<Long> bgGoodsCollegeList = getCollegeIds(bgGoods.getBgGoodsId(), storageType);

		/** 获得活动下的学校 */
		List<College> activityCollegeList = activityService.getActivityCollegeList(activityId);
		if (activityCollegeList == null || activityCollegeList.size() == 0) {
			throw new RuntimeException("该活动所设置的学校范围为空！");
		}		
		
		List<Long> activityCollegeIdList = new ArrayList<Long>();
		for (College college : activityCollegeList) {
			activityCollegeIdList.add(college.getCollegeId());
		}
		return (List<Long>)CollectionUtils.removeAll(activityCollegeIdList, bgGoodsCollegeList);
	}
	
	@Override
	public void delBgGoodsForActivity(long activityId) {
		List<BgGoodsForActivity> bgGoodsForActivitys = getBgGoodsForActivityByActivityIdAndIsDel(activityId,0);
		if(CollectionUtils.isNotEmpty(bgGoodsForActivitys)) {
			for(BgGoodsForActivity bgGoodsForActivity : bgGoodsForActivitys) {
				updateIsDelStatus(bgGoodsForActivity.getActivityBgGoodsId(),1);
			}
		}
	}
	

	@Override
	public int insertBgGoodsForActivity(ActivityGoodsApplyBean activityGoodsApplyBean, long activityId,
			int storageType, long userId, Date beginTime, Date endTime, Date showTime, int activityType) {
		
		if(activityType != Activity.ACTIVITY_TYPE_BRAND && activityType != Activity.ACTIVITY_TYPE_POPULAR) {
		 
			// 检查活动activityid下是否存在商品bgGoodsId(并且属于同一个仓库类型)，如果存在，则不再添加。
			BgGoodsForActivity bgGoodsForActivity_temp = queryByActivityIdAndBgGoodsId(activityId,
					activityGoodsApplyBean.getBgGoodsId(), storageType);
			if (bgGoodsForActivity_temp != null) {
				// 不为空，则说明该活动下有该商品，如果isdel!=0说明该商品被删除了，需要更新为可用
				if (bgGoodsForActivity_temp.getIsDel() != 0) {
					int result = bgGoodsForActivityDao.updateIsDelStatus(
							bgGoodsForActivity_temp.getActivityBgGoodsId(), 0);
					result = activityGoodsDao.updateIsDelStatus(
							bgGoodsForActivity_temp.getActivityBgGoodsId(), 0);
	
				}
				List<ActivityGoods> activityGoods = activityGoodsDao
						.getActivitGoodsList(bgGoodsForActivity_temp.getActivityBgGoodsId());
				return activityGoods.size();

			}
		}

		long activityPrice = MoneyUtils.yuan2Fen(Double.parseDouble(activityGoodsApplyBean.getActivityPrice()));
		long originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(activityGoodsApplyBean.getOriginPrice()));

		BgGoodsDto bgGoodsDto = goodsService.getBgGoodsDtoById(activityGoodsApplyBean.getBgGoodsId());
		BgGoods bgGoods = bgGoodsDto.getBgGoods();
		/** 获得商品所属仓库，再根据仓库获得仓库下的学校，得到商品可售卖的学校列表 */
		List<Long> bgGoodsCollegeList = null;
		if(activityType == Activity.ACTIVITY_TYPE_BRAND || activityType == Activity.ACTIVITY_TYPE_POPULAR) {
			bgGoodsCollegeList = getCollegeIdsV2(bgGoods.getBgGoodsId(), storageType);
		} else {
			bgGoodsCollegeList = getCollegeIds(bgGoods.getBgGoodsId(), storageType);
		}

		/** 获得活动下的学校 */
		List<College> activityCollegeList = activityService.getActivityCollegeList(activityId);
		if (activityCollegeList == null || activityCollegeList.size() == 0) {
			return -1;
		}
		List<Long> activityCollegeIdList = new ArrayList<Long>();
		for (College college : activityCollegeList) {
			activityCollegeIdList.add(college.getCollegeId());
		}
		/** 取商品和活动中学校集合的交集 */
		bgGoodsCollegeList.retainAll(activityCollegeIdList);
		if (bgGoodsCollegeList == null || bgGoodsCollegeList.size() == 0) {
			return -2;
		}

		int goodsStatus = Constants.GOOD_STATUS_ONSALE;
		if(activityType == Activity.ACTIVITY_TYPE_POPULAR ) { //爆品活动，状态默认为在售
			goodsStatus = Constants.GOOD_STATUS_SALING;
		} else if(activityType == Activity.ACTIVITY_TYPE_BRAND) {  //品牌活动，goodsStatus从页面中读取
			goodsStatus = Integer.parseInt(activityGoodsApplyBean.getGoodsStatus());
		}
		BgGoodsForActivity bgGoodsForActivity = BgGoodsForActivity.initByBgGoods(bgGoods, activityId,
				activityGoodsApplyBean.getMaxNum(), activityGoodsApplyBean.getWeight(), userId,
				beginTime, endTime, showTime, activityType, storageType,goodsStatus);
		bgGoodsForActivityDao.insert(bgGoodsForActivity);
		
		
		long activityBgGoodsId = bgGoodsForActivity.getActivityBgGoodsId();
		List<BgSku> bgskuList = bgSkuService.getByGoodsId(bgGoods.getBgGoodsId(), bgGoods.getGoodsType());
		/**
		 * 由于bg_goods对应多个bg_sku，需要把添加成功的活动模板sku添加到list中，待后面添加学校对应的sku时使用
		 */
		List<BgSkuForActivity> bgSkuForActivityList = new ArrayList<BgSkuForActivity>();
		if (bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_SINGLE || bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_GROUP) {
			BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(bgskuList.get(0),
					bgGoodsForActivity.getActivityBgGoodsId(), activityPrice, originPrice,
					activityGoodsApplyBean.getStock(), userId, storageType);
			bgSkuForActivityDao.insert(bgSkuForActivity);
			bgSkuForActivityList.add(bgSkuForActivity);
		} else {
			if (activityGoodsApplyBean.getSkuListBean() == null
					|| activityGoodsApplyBean.getSkuListBean().size() == 0) {
				//商品管理中添加聚合品，统一设置的价格
				for (BgSku bgsku : bgskuList) {
					BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(
							bgsku, bgGoodsForActivity.getActivityBgGoodsId(),
							activityPrice, originPrice, activityGoodsApplyBean.getStock(),
							userId, storageType);
					bgSkuForActivityDao.insert(bgSkuForActivity);
					bgSkuForActivityList.add(bgSkuForActivity);
				}
			} else {
				List<ActivitySkuApplyBean> skuApplyBeanList = activityGoodsApplyBean.getSkuListBean();
				Map<Long, ActivitySkuApplyBean> skuApplyMap = new HashMap<Long, ActivitySkuApplyBean>();
				for (ActivitySkuApplyBean bean : skuApplyBeanList) {
					skuApplyMap.put(bean.getSkuId(), bean);
				}
				for (BgSku bgsku : bgskuList) {
					ActivitySkuApplyBean skuApplyBean = skuApplyMap.get(bgsku.getBgSkuId());
					activityPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuApplyBean
							.getActivityPrice()));
					originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuApplyBean
							.getOriginPrice()));
					BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(
							bgsku, bgGoodsForActivity.getActivityBgGoodsId(),
							activityPrice, originPrice, skuApplyBean.getStock(), userId,
							storageType);
					bgSkuForActivityDao.insert(bgSkuForActivity);
					bgSkuForActivityList.add(bgSkuForActivity);
				}
			}
		}
		for (long collegeId : bgGoodsCollegeList) {
			ActivityGoods activityGoods = ActivityGoods.initActivityGoods(bgGoodsForActivity, collegeId);
			activityGoodsDao.insert(activityGoods);
			for (BgSkuForActivity bgSkuForActivity : bgSkuForActivityList) {
				ActivitySku activitySku = ActivitySku.initActivitySKu(bgSkuForActivity,
						activityGoods.getActivityGoodsId(), collegeId);
				activitySkuDao.insert(activitySku);
			}
		}
		return bgGoodsCollegeList.size();
	}

	@Override
	public int insertThirdGoods2Activity(ActivityGoodsApplyBean activityGoodsApplyBean, long activityId,
			long userId, int activityType,int storageType,int goodsStatus) {
		//检查活动activityid下是否存在商品bgGoodsId(并且属于同一个仓库类型)，如果存在，则不再添加。
		BgGoodsForActivity bgGoodsForActivity_temp = queryByActivityIdAndBgGoodsId(activityId,
				activityGoodsApplyBean.getBgGoodsId(), storageType);
		if (bgGoodsForActivity_temp != null) {
			// 不为空，则说明该活动下有该商品，如果isdel!=0说明该商品被删除了，需要更新为可用
			if (bgGoodsForActivity_temp.getIsDel() != 0) {
				int result = bgGoodsForActivityDao.updateIsDelStatus(
						bgGoodsForActivity_temp.getActivityBgGoodsId(), 0);
				result = activityGoodsDao.updateIsDelStatus(
						bgGoodsForActivity_temp.getActivityBgGoodsId(), 0);

			}
			List<ActivityGoods> activityGoods = activityGoodsDao
					.getActivitGoodsList(bgGoodsForActivity_temp.getActivityBgGoodsId());
			return activityGoods.size();
		}
		long activityPrice = MoneyUtils.yuan2Fen(Double.parseDouble(activityGoodsApplyBean.getActivityPrice()));
		long originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(activityGoodsApplyBean.getOriginPrice()));
		BgGoods bgGoods = goodsService.getBgGoodsById(activityGoodsApplyBean.getBgGoodsId());
		BgGoodsForActivity bgGoodsForActivity = BgGoodsForActivity.initByBgGoods(bgGoods, activityId,
				activityGoodsApplyBean.getMaxNum(), activityGoodsApplyBean.getWeight(), userId,
				null, null, null, activityType, storageType,goodsStatus);
		bgGoodsForActivityDao.insert(bgGoodsForActivity);
		
		//由于bg_goods对应多个bg_sku，需要把添加成功的活动模板sku添加到list中，待后面添加学校对应的sku时使用
		List<BgSku> bgskuList = bgSkuService.getByGoodsId(bgGoods.getBgGoodsId(), BgGoods.GOODS_TYPE_MULTI);
		List<BgSkuForActivity> bgSkuForActivityList = new ArrayList<BgSkuForActivity>();
		if (bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_SINGLE || bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_GROUP) {
			BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(bgskuList.get(0),
					bgGoodsForActivity.getActivityBgGoodsId(), activityPrice, originPrice,
					activityGoodsApplyBean.getStock(), userId, storageType);
			bgSkuForActivityDao.insert(bgSkuForActivity);
			bgSkuForActivityList.add(bgSkuForActivity);
		} else {
			if (activityGoodsApplyBean.getSkuListBean() == null
					|| activityGoodsApplyBean.getSkuListBean().size() == 0) {
				//商品管理中添加聚合品，统一设置的价格
				for (BgSku bgsku : bgskuList) {
					BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(
							bgsku, bgGoodsForActivity.getActivityBgGoodsId(),
							activityPrice, originPrice, activityGoodsApplyBean.getStock(),
							userId, storageType);
					bgSkuForActivityDao.insert(bgSkuForActivity);
					bgSkuForActivityList.add(bgSkuForActivity);
				}
			} else {
				List<ActivitySkuApplyBean> skuApplyBeanList = activityGoodsApplyBean.getSkuListBean();
				Map<Long, ActivitySkuApplyBean> skuApplyMap = new HashMap<Long, ActivitySkuApplyBean>();
				for (ActivitySkuApplyBean bean : skuApplyBeanList) {
					skuApplyMap.put(bean.getSkuId(), bean);
				}
				for (BgSku bgsku : bgskuList) {
					ActivitySkuApplyBean skuApplyBean = skuApplyMap.get(bgsku.getBgSkuId());
					activityPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuApplyBean
							.getActivityPrice()));
					originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuApplyBean
							.getOriginPrice()));
					BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(
							bgsku, bgGoodsForActivity.getActivityBgGoodsId(),
							activityPrice, originPrice, skuApplyBean.getStock(), userId,
							storageType);
					bgSkuForActivityDao.insert(bgSkuForActivity);
					bgSkuForActivityList.add(bgSkuForActivity);
				}
			}
		}
		//获取所有学校,并插入activityGoods/activitySku
		List<College> collegeList = collegeService.getAllCollege();
		for (College college : collegeList) {
			if(college.getIsDel() != 1){
				ActivityGoods activityGoods = ActivityGoods.initActivityGoods(bgGoodsForActivity, college.getCollegeId());
				activityGoodsDao.insert(activityGoods);
				for (BgSkuForActivity bgSkuForActivity : bgSkuForActivityList) {
					ActivitySku activitySku = ActivitySku.initActivitySKu(bgSkuForActivity,
							activityGoods.getActivityGoodsId(), college.getCollegeId());
					activitySkuDao.insert(activitySku);
				}
			}
		}
		return collegeList.size();
	}


	@Override
	public int batchBgGoodsForActivity(Long activityId, Long collegeId,
			Goods goods, Sku sku,Long userId) {
		
		BgGoods bgGoods = goodsService.getBgGoodsById(goods.getBgGoodsId());
		
		// 检查活动activityid下是否存在商品bgGoodsId(并且属于同一个仓库类型)，如果存在，则不再添加。
		BgGoodsForActivity bgGoodsForActivity_temp = queryByActivityIdAndBgGoodsId(activityId,
				goods.getBgGoodsId(), 1);
		long activityBgGoodsId = 0L;
		if (bgGoodsForActivity_temp != null) {
			// 不为空，则说明该活动下有该商品，如果isdel!=0说明该商品被删除了，需要更新为可用
			if (bgGoodsForActivity_temp.getIsDel() != 0) {
				int result = bgGoodsForActivityDao.updateIsDelStatus(
						bgGoodsForActivity_temp.getActivityBgGoodsId(), 0);
			}
			activityBgGoodsId = bgGoodsForActivity_temp.getActivityBgGoodsId();
		}else{
			//插入
			BgGoodsForActivity bgGoodsForActivity = BgGoodsForActivity.initByBgGoods(bgGoods, activityId,
					(int)goods.getMaxNum(),(int)goods.getWeight(), userId,
					null, null, null, 0, 1, Constants.GOOD_STATUS_ONSALE);
			bgGoodsForActivityDao.insert(bgGoodsForActivity);
			bgGoodsForActivity_temp = queryByActivityIdAndBgGoodsId(activityId,
					goods.getBgGoodsId(), 1);
			if(bgGoodsForActivity_temp == null){
				System.out.println("[activity bgGoods add failure]activityId:"+activityId+"|goodsId:"+goods.getGoodsId());
				return -1;
			}
			activityBgGoodsId = bgGoodsForActivity_temp.getActivityBgGoodsId();
		}
		
		
		//bgSku
		BgSku bgSku = bgSkuDao.getById(sku.getBgSkuId());

		BgSkuForActivity bgSkuForActivity_temp = bgSkuForActivityDao.getBgSkuForActivity(sku.getBgSkuId(), activityBgGoodsId);
		if(bgSkuForActivity_temp == null){
			BgSkuForActivity bgSkuForActivity = BgSkuForActivity.initBgSkuForActivity(bgSku,
				    activityBgGoodsId, sku.getWapPrice(), sku.getOriginPrice(),
					(int)sku.getStock(), userId, 1);
			bgSkuForActivityDao.insert(bgSkuForActivity);
			bgSkuForActivity_temp = bgSkuForActivityDao.getBgSkuForActivity(sku.getBgSkuId(), activityBgGoodsId);
			if(bgSkuForActivity_temp == null){
				System.out.println("[activity bgSku add failure]activityId:"+activityId+"|skuId:"+sku.getSkuId());
				return -1;
			}
		}else{
			bgSkuForActivityDao.updateIsDel(bgSkuForActivity_temp.getActivityBgSkuId(), 0);
		}
		
		//添加activity_goods
		ActivityGoods activityGoods_temp = activityGoodsDao.getByUnionKey(activityBgGoodsId, collegeId);
		if(activityGoods_temp == null){
			ActivityGoods activityGoods = ActivityGoods.initActivityGoods(bgGoodsForActivity_temp, collegeId);
			activityGoodsDao.insert(activityGoods);
			activityGoods_temp = activityGoodsDao.getByUnionKey(activityBgGoodsId, collegeId);
			if(activityGoods_temp == null){
				System.out.println("[activity goods add failure]activityId:"+activityId+"|goodsId:"+goods.getGoodsId());
				return -1;
			}
		}
		
		//添加activity_sku
	    ActivitySku activitySku_temp =  activitySkuDao.getActivitySku(bgSkuForActivity_temp.getActivityBgSkuId(), collegeId);
		if(activitySku_temp == null){
			 ActivitySku activitySku = ActivitySku.initActivitySKu(bgSkuForActivity_temp,
						activityGoods_temp.getActivityGoodsId(), collegeId);
			activitySkuDao.insert(activitySku);
			activitySku_temp =  activitySkuDao.getActivitySku(bgSkuForActivity_temp.getActivityBgSkuId(), collegeId);
			if(activitySku_temp == null){
				System.out.println("[activity bgSku add failure]activityId:"+activityId+"|skuId:"+sku.getSkuId());
				return -1;
			}
		}
		return 0;
	}

	@Override
	public int updateBgGoodsForActivity(BgGoodsForActivity bgGoodsForActivity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BgGoodsForActivity queryByActivityIdAndBgGoodsId(long activityId, long bgGoodsId, int storageType) {
		return bgGoodsForActivityDao.queryByActivityIdAndBgGoodsId(activityId, bgGoodsId, storageType);
	}

	@Override
	public int updateBgGoodsForActivity(long weight, long maxNum, int status, long activityBgGoodsId) {
		bgGoodsForActivityDao.update(weight, maxNum, status, activityBgGoodsId);
		activityGoodsDao.update(weight, maxNum, status, activityBgGoodsId);
		return 0;
	}

	@Override
	public int updateStatus4ActivityGoods(int status, long activityBgGoodsId) {
		bgGoodsForActivityDao.updateGoodsStatus(status, activityBgGoodsId);
		activityGoodsDao.updateGoodsStatus(status, activityBgGoodsId);
		return 0;
	}


	@Override
	public int updateBgGoodsForActivityTime(Date beginTime, Date endTime, Date showTime, long activityBgGoodsId) {
		bgGoodsForActivityDao.updateTime(beginTime, endTime, showTime, activityBgGoodsId);
		activityGoodsDao.updateTime(beginTime, endTime, showTime, activityBgGoodsId);
		return 0;
	}

	@Override
	public int updateIsDelStatus(long activityBgGoodsId, int isDel) {
		int result = bgGoodsForActivityDao.updateIsDelStatus(activityBgGoodsId, isDel);
		result = activityGoodsDao.updateIsDelStatus(activityBgGoodsId, isDel);
		return result;
	}

	@Override
	public List<BgGoodsForActivity> getBgGoodsForActivityByQuery(Date beginDate, Date endDate, long activityId,
			String goodsName, int status, int isSeckill, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return bgGoodsForActivityDao.queryBeGoodsForActivityByQuery(beginDate, endDate, activityId, goodsName,
				status, isSeckill, paginationInfo);
	}

	@Override
	public int addCollegeInActivity(long activityId, long[] collegeIds) {
		// 查询活动下的商品
		List<BgGoodsForActivity> bgGoodsList = bgGoodsForActivityDao.getBeanListByActivityId(activityId);
		for (BgGoodsForActivity goodsBean : bgGoodsList) {
			// 查询活动商品下的sku
			List<BgSkuForActivity> bgSkuForActivityList = bgSkuForActivityDao.getBgSkuByBgGoodsId(goodsBean
					.getActivityBgGoodsId());
			if (bgSkuForActivityList != null && bgSkuForActivityList.size() > 0) {
				int distributeType = bgSkuForActivityList.get(0).getDistributeType();
				// 获得后台模板商品可售卖的学校
				List<Long> collegeList = getCollegeIds(goodsBean.getBgGoodsId(), distributeType);
				// 获得活动商品售卖的学校
				List<ActivityGoods> activityGoodsList = activityGoodsDao.getActivitGoodsList(goodsBean
						.getActivityBgGoodsId());
				if (collegeList != null && collegeList.size() > 0) {
					for (long collegeId : collegeIds) {
						// 如果可售卖学校包含新增的学校，则可以把商品添加到该学校下。
						if (collegeList.contains(collegeId)) {
							boolean hasContain = false;
							// 检查活动前台商品中是否包含该学校（以前有这个学校，后来学校被删除，再被添加上，有可能出现这个情况，把商品的状态修改为可用即可）
							for (ActivityGoods activityGoods : activityGoodsList) {
								if (activityGoods.getCollegeId() == collegeId) {
									activityGoodsDao.updateIsDelStatusInCollege(
											goodsBean.getActivityBgGoodsId(),
											0, collegeId);
									hasContain = true;
									break;
								}
							}
							// 如果不包含，需要新增该商品，如果包含，则不需要再处理
							if (!hasContain) {

								ActivityGoods activityGoods = ActivityGoods
										.initActivityGoods(goodsBean, collegeId);
								activityGoodsDao.insert(activityGoods);
								for (BgSkuForActivity bgSkuForActivity : bgSkuForActivityList) {
									ActivitySku activitySku = ActivitySku
											.initActivitySKu(
													bgSkuForActivity,
													activityGoods.getActivityGoodsId(),
													collegeId);
									activitySkuDao.insert(activitySku);
								}
							}
						}
					}
				}

			}
		}
		return 0;
	}

	@Override
	public int deleteCollegeInActivity(long activityId, long[] collegeIds) {
		// 查询活动下的商品
		List<BgGoodsForActivity> bgGoodsList = bgGoodsForActivityDao.getBeanListByActivityId(activityId);
		for (BgGoodsForActivity goodsBean : bgGoodsList) {
			for (Long collegeId : collegeIds) {
				activityGoodsDao.updateIsDelStatusInCollege(goodsBean.getActivityBgGoodsId(), 1,
						collegeId);
			}
		}
		return 0;
	}
	
	
	/**
	 * 根据活动ID查询该活动下的bg_activity_goods
	 */
	@Override
	public List<BgGoodsForActivity> getBgGoodsForActivityByActivityId(long activityId) {
		return bgGoodsForActivityDao.getBeanListByActivityId(activityId);
	}
	
	/**
	 * 根据活动ID查询该活动下的bg_activity_goods
	 */
	@Override
	public List<BgGoodsForActivity> getBgGoodsForActivityByActivityIdAndIsDel(long activityId,int isDel) {
		return bgGoodsForActivityDao.getBeanListByActivityId(activityId,isDel);
	}
	
	
	@Override
	public BgGoodsForActivity queryBgGoodsForActivity(long activityGoodsId) {
		return bgGoodsForActivityDao.queryBgGoodsForActivity(activityGoodsId);
	}
	
	
	@Override
	public ActivityGoods queryActivityGoods(long bgGoodsForActivityId, long activityId, long collegeId) {
		return activityGoodsDao.queryActivityGoods(bgGoodsForActivityId, activityId, collegeId);
	}


	@Override
	public List<BgGoodsForActivity> getThirdBgGoods4Activity(
			Map<String, Object> params, PaginationInfo paginationInfo) {
		return bgGoodsForActivityDao.getThirdBgGoods4Activity(params, paginationInfo);
	}

}
