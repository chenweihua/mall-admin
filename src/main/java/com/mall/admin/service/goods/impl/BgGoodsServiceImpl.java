package com.mall.admin.service.goods.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.CategoryConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.BgGoodsImageDao;
import com.mall.admin.model.dao.goods.BgGoodsRegionDao;
import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.dao.goods.BgSkuGbmDao;
import com.mall.admin.model.dao.goods.GoodsDao;
import com.mall.admin.model.dao.goods.GoodsPropertyDao;
import com.mall.admin.model.dao.goods.PropertyValueDao;
import com.mall.admin.model.dao.goods.SkuDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.model.dao.mallbase.CategoryDao;
import com.mall.admin.model.dao.wms.StorageGoodsStockDao;
import com.mall.admin.model.dao.wms.WmsGoodsDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.base.BaseServiceImpl;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.util._;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.BgGoodsRegion;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.BgSkuGbm;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.GoodsProperty;
import com.mall.admin.vo.goods.PropertyValue;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.goods.SkuProperty;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;

/**
 * @date 2015年7月24日 下午1:40:40
 * @author zhangshuai
 */
@Service
public class BgGoodsServiceImpl extends BaseServiceImpl implements BgGoodsService {
	@Autowired
	BgGoodsDao bgGoodsDao;
	@Autowired
	WmsGoodsDao wmsGoodsDao;
	@Autowired
	BgSkuDao bgSkuDao;
	@Autowired
	BgSkuGbmDao bgSkuGbmDao;
	@Autowired
	StorageGoodsStockDao storageGoodsStockDao;
	@Autowired
	GoodsDao goodsDao;
	@Autowired
	SkuDao skuDao;
	@Autowired
	SkuPropertyDao skuPropertyDao;
	@Autowired
	SkuService skuService;
	@Autowired
	BgGoodsRegionDao bgGoodsRegionDao;
	@Autowired
	GoodsPropertyDao goodsPropertyDao;
	@Autowired
	PropertyValueDao propertyValueDao;
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	BgGoodsImageDao bgGoodsImageDao;
	
	@Override
	public Map<String, Object> addGoods(BgGoods bgGoods, Map<String, Long> skuParam, List<BgGoodsImage> imageList) {
		long bgGoodsId = bgGoods.getBgGoodsId();
		BgSku bgSku_old = bgSkuDao.getByBgGoodsId(bgGoodsId);

		// 判断单品不重复
		if (skuParam.size() == 1) {
			for (Entry<String, Long> entry : skuParam.entrySet()) {
				BgSkuGbm temp = bgSkuGbmDao.getByBgWmsGoodsIdNum(_.toLong(entry.getKey()),
						entry.getValue());
				if (temp != null) {
					if (bgSku_old != null) {
						if (temp.getBgSkuId() != bgSku_old.getBgSkuId())
							return buildObj(-1, "商品编码和数量已存在！");
					} else {
						return buildObj(-1, "商品编码和数量已存在！");
					}
				}
			}
		}

		// 判断gbm是否能聚合出仓库
		Long[] wmsIds = new Long[skuParam.size()];
		int i = 0;
		for (String s : skuParam.keySet()) {
			wmsIds[i] = _.toLong(s);
			i++;
		}
		List<Long> storageIds = getBaseStorageIds(wmsIds);
		if (storageIds.size() == 0) {
			return buildErrObj("您添加的商品无法聚合出相同的仓库，请联系采购在需要售卖的仓库中添加此类商品");
		}
		//判断是否为第三方仓，默认取第一个（原因：第三方商品只存在一个仓中，且只允许同一仓内组合）
		Storage storage = StroageConstant.getStorageMap().get(storageIds.get(0));
		if(storage.getStorageType() == Storage.VM_STORAGE){
			bgGoods.setStorageId(storageIds.get(0));
		}else{
			bgGoods.setStorageId(Constants.XM_WMS_STORAGE_ID);
		}
		
		// 添加后台goods
		bgGoods.setWeight(Constants.GOOD_WEIGHT_DEFAULT);
		if (skuParam.size() > 1) {
			bgGoods.setGoodsType(2);// 组合商品
		} else {
			bgGoods.setGoodsType(1);// 单品
		}
		bgGoods.setMaxNum(Constants.GOOD_MAXNUM);
		bgGoods.setGoodsStatus(Constants.GOOD_STATUS_DEFAULT);
		bgGoods.setIsDel(0);
		BgGoods bgGoodsTemp = bgGoodsDao.getById(bgGoodsId);
		if (bgGoodsTemp != null) {
			int ui = bgGoodsDao.updateByObject(bgGoods);
			if (ui < 0) {
				return buildObj(-1, "商品编辑失败");
			}
			// 同步更新所有前台信息
			int uii = goodsDao.updateByBgGoods(bgGoods);
			if (uii < 0) {
				LogConstant.mallLog.error("根据后台goods更新前台goods是出错");
			}
		} else {
			bgGoodsId = bgGoodsDao.insert(bgGoods);
			if (bgGoodsId == -1) {
				logger.info("单品bgGoods插入失败，返回结果：" + bgGoodsId);
				return buildErrObj("商品添加失败！");
			}
		}
		//添加商品图片
		bgGoodsImageDao.deleteBgImageByBgGoodsId(bgGoodsId);
		if(CollectionUtils.isNotEmpty(imageList)){
			for(BgGoodsImage goodsImage : imageList) {
				goodsImage.setBgGoodsId(bgGoodsId);
			}
			long result =  bgGoodsImageDao.insertBgImage(imageList);
			if (result == -1) {
				logger.info("单品bgGoodsImage插入失败，bgGoodsId：" + bgGoodsId);
				return buildErrObj("商品添加失败！");
			}
		}
		// 添加后台sku
		String wmsGoodsGbms = "";
		// 拼接wmsGoodsGbms
		for (String s : skuParam.keySet()) {
			wmsGoodsGbms += ":" + wmsGoodsDao.getWmsGoodsById(_.toLong(s)).getWms_goods_gbm();
		}
		if (wmsGoodsGbms.length() > 0) {
			wmsGoodsGbms = wmsGoodsGbms.substring(1);
		}
		BgSku bgSku = new BgSku();
		bgSku.setBgGoodsId(bgGoodsId);
		bgSku.setSkuType(bgGoods.getGoodsType());
		bgSku.setOperator(bgGoods.getOperator());
		bgSku.setWmsGoodsGbms(wmsGoodsGbms);
		bgSku.setImageUrl(bgGoods.getImageUrl());
		bgSku.setStorageId(bgGoods.getStorageId());
		long bgSkuId = 0;
		if (bgSku_old != null) {
			int ui = bgSkuDao.updateByObjectBgGoodsId(bgSku);
			if (ui < 0) {
				return buildObj(-1, "商品编辑失败");
			}
			bgSkuId = bgSku_old.getBgSkuId();
		} else {
			bgSkuId = bgSkuDao.insert(bgSku);
			if (bgSkuId == -1) {
				logger.info("单品bgSku插入失败，返回结果：" + bgSkuId);
				return buildErrObj("商品添加失败！");
			}
		}

		// 关联bgSku和Gbm
		List<BgSkuGbm> old_bgSkuGbms = bgSkuGbmDao.getByBgSkuId(bgSkuId);

		BgSkuGbm bgSkuGbm = new BgSkuGbm();
		BgSkuGbm temp = null;
		for (String s : skuParam.keySet()) {
			bgSkuGbm.setWmsGoodsId(_.toLong(s));
			bgSkuGbm.setNum(skuParam.get(s));
			bgSkuGbm.setBgSkuId(bgSkuId);
			bgSkuGbm.setOperator(bgGoods.getOperator());

			temp = bgSkuGbmDao.getByBgSkuIdWmsGoodsId(bgSkuId, _.toLong(s));
			if (temp != null) {
				int ui = bgSkuGbmDao.updateByBgSkuIdWmsGoodsId(bgSkuGbm);
				if (ui < 0) {
					return buildObj(-1, "商品编辑失败");
				}
				// 标示已有关系

				for (BgSkuGbm b : old_bgSkuGbms) {
					if (b.getBgSkuId() == bgSkuId && b.getWmsGoodsId() == _.toLong(s)) {
						b.setSkuGbmId(0);
					}
				}
			} else {
				long r = bgSkuGbmDao.insert(bgSkuGbm);
				if (r == -1) {
					LogConstant.mallLog.info("单品bgSkuGbm插入失败，返回结果：" + r);
					return buildErrObj("商品添加失败！");
				}
			}
		}

		// 删除已有关系
		if (old_bgSkuGbms != null && old_bgSkuGbms.size() > 0) {
			for (BgSkuGbm b : old_bgSkuGbms) {
				if (b.getSkuGbmId() > 0) {
					int di = bgSkuGbmDao.deleteById(b.getSkuGbmId());
					if (di < 0) {
						return buildObj(-1, "商品编辑失败");
					}
				}
			}
		}

		return buildObj(0, "成功", bgGoodsId);
	}

	@Override
	public Map<String, Object> addMultiGoods(BgGoods bgGoods, Map<String, String[]> skuProperty, String[] pvIds, List<BgGoodsImage> imageList) {
		// 检测是否可聚合出范围
		List<Long> wmsIdList = new ArrayList<>();
		for (String key : skuProperty.keySet()) {
			wmsIdList.addAll(bgSkuGbmDao.getWmsGoodsIdByBgSkuId(_.toLong(key)));
		}

		Long[] wmsIds = new Long[wmsIdList.size()];
		int i = 0;
		for (Long s : wmsIdList) {
			wmsIds[i] = s;
			i++;
		}
		List<Long> storageIds = getBaseStorageIds(wmsIds);
		if (storageIds.size() == 0) {
			return buildErrObj("您添加的商品无法聚合出相同的仓库，请添加单品");
		}
		//判断是否为第三方仓，默认取第一个（原因：第三方商品只存在一个仓中，且只允许同一仓内组合）
		Storage storage = StroageConstant.getStorageMap().get(storageIds.get(0));
		if(storage.getStorageType() == Storage.VM_STORAGE){
			bgGoods.setStorageId(storageIds.get(0));
		}else{
			bgGoods.setStorageId(Constants.XM_WMS_STORAGE_ID);
		}
		// 插入后台goods
		bgGoods.setWeight(Constants.GOOD_WEIGHT_DEFAULT);
		bgGoods.setGoodsType(3);
		bgGoods.setMaxNum(Constants.GOOD_MAXNUM);
		bgGoods.setGoodsStatus(Constants.GOOD_STATUS_DEFAULT);
		bgGoods.setIsDel(0);
		long bgGoodsId = bgGoods.getBgGoodsId();
		BgGoods bgGoodsTemp = bgGoodsDao.getById(bgGoodsId);
		if (bgGoodsTemp != null) {
			int ui = bgGoodsDao.updateByObject(bgGoods);
			if (ui < 0) {
				return buildObj(-1, "商品编辑失败");
			}
			// 同步更新所有前台信息
			int uii = goodsDao.updateByBgGoods(bgGoods);
			if (uii < 0) {
				LogConstant.mallLog.error("根据后台goods更新前台goods是出错");
			}
		} else {
			bgGoodsId = bgGoodsDao.insert(bgGoods);
			if (bgGoodsId == -1) {
				logger.info("单品bgGoods插入失败，返回结果：" + bgGoodsId);
				return buildErrObj("商品添加失败！");
			}
		}
		//添加商品图片
		bgGoodsImageDao.deleteBgImageByBgGoodsId(bgGoodsId);
		if(CollectionUtils.isNotEmpty(imageList)){
			for(BgGoodsImage goodsImage : imageList) {
				goodsImage.setBgGoodsId(bgGoodsId);
			}
			long result =  bgGoodsImageDao.insertBgImage(imageList);
			if (result == -1) {
				logger.info("单品bgGoodsImage插入失败，bgGoodsId：" + bgGoodsId);
				return buildErrObj("商品添加失败！");
			}
		}
		// 插入goodsProperty
		List<GoodsProperty> old_goodsPropertyList = goodsPropertyDao.getByBgGoodsId(bgGoodsId);
		GoodsProperty goodsProperty = new GoodsProperty();
		GoodsProperty temp = null;
		for (String pvId : pvIds) {
			goodsProperty.setBgGoodsId(bgGoodsId);
			goodsProperty.setPropertyValueId(_.toLong(pvId));
			goodsProperty.setImageUrl("");
			goodsProperty.setIsDel(0);
			temp = goodsPropertyDao.getByBgGoodsIdValueId(bgGoodsId, _.toLong(pvId));
			if (temp != null) {
				int ui = goodsPropertyDao.updateByBgGoodsIdVauleId(goodsProperty);
				if (ui < 0) {
					return buildObj(-1, "商品编辑失败");
				}
				for (GoodsProperty g : old_goodsPropertyList) {
					if (g.getBgGoodsId() == bgGoodsId && g.getPropertyValueId() == _.toLong(pvId)) {
						g.setGoodsPropertyId(0);
					}
				}
			} else {
				long goodsPropertyId = goodsPropertyDao.insert(goodsProperty);
				if (goodsPropertyId == -1) {
					logger.info("聚合品goodsProperty插入失败，返回结果：" + goodsPropertyId);
					return buildErrObj("商品添加失败！");
				}
			}
		}
		if (old_goodsPropertyList != null && old_goodsPropertyList.size() > 0) {
			for (GoodsProperty g : old_goodsPropertyList) {
				if (g.getGoodsPropertyId() > 0) {
					goodsPropertyDao.deleteById(g.getGoodsPropertyId());
				}
			}
		}
		// 插入skuProperty,其中包含bggoods与bgSku的关系
		List<SkuProperty> old_skuPropertyList = skuPropertyDao.getByBgGoodsId(bgGoodsId);
		SkuProperty skuPropertyPojo = new SkuProperty();
		SkuProperty tempSkuP = null;
		for (String key : skuProperty.keySet()) {
			String[] pvTemp = skuProperty.get(key);
			for (String pv : pvTemp) {

				skuPropertyPojo.setBgGoodsId(bgGoodsId);
				skuPropertyPojo.setBgSkuId(_.toLong(key));
				skuPropertyPojo.setPropertyValueId(_.toLong(pv));
				PropertyValue pValue = propertyValueDao.getById(_.toLong(pv));
				if (pValue != null) {
					skuPropertyPojo.setPropertyNameId(pValue.getPropertyNameId());
				}
				tempSkuP = skuPropertyDao.getByBgSkuIdValueId(bgGoodsId, _.toLong(key), _.toLong(pv));
				if (tempSkuP != null) {
					for (SkuProperty sp : old_skuPropertyList) {
						if (sp.getBgGoodsId() == bgGoodsId && sp.getBgSkuId() == _.toLong(key)
								&& sp.getPropertyValueId() == _.toLong(pv)) {
							sp.setSkuPropertyId(0);
						}
					}
				} else {
					long skuPropertyId = skuPropertyDao.insert(skuPropertyPojo);
					if (skuPropertyId == -1) {
						logger.info("聚合品skuProperty插入失败，返回结果：" + skuPropertyId);
						return buildErrObj("商品添加失败！");
					}
				}
			}
		}
		if (old_skuPropertyList != null && old_skuPropertyList.size() > 0) {
			for (SkuProperty sp : old_skuPropertyList) {
				if (sp.getSkuPropertyId() > 0) {
					skuPropertyDao.deleteById(sp.getSkuPropertyId());
				}
			}
		}

		return buildObj(0, "成功", bgGoodsId);
	}

	
	
	
	@Override
	public Map<String, Object> selectSingleGoodsByPage(int start, int numPerPage, long categoryId, long collegeId,
			String name) {
		return bgGoodsDao.selectSingleByPage(start, numPerPage, categoryId, collegeId, null);
	}

	@Override
	public Map<String, Object> selectSingleGoodsByPage(int start, int numPerPage, String searchStr) {
		Map<String, Object> map = bgGoodsDao.selectSingleByPage(start, numPerPage, searchStr);
		@SuppressWarnings("unchecked")
		List<BgGoods> bgGoodsList = (List<BgGoods>) map.get("bgGoodsList");
		List<BgGoodsDto> bgGoodsDtos = new ArrayList<>();
		for (BgGoods bgGoods : bgGoodsList) {
			if (bgGoods != null) {
				BgGoodsDto bgGoodsDto = new BgGoodsDto();
				BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoods.getBgGoodsId());
				if (bgSku != null) {
					bgGoodsDto.setBgSkuId(bgSku.getBgSkuId());
					bgGoodsDto.setBgGoods(bgGoods);
					bgGoodsDto.setWmsGoodsGbms(bgSku.getWmsGoodsGbms());
					bgGoodsDtos.add(bgGoodsDto);
				}
			}
		}
		map.put("bgGoodsDtos", bgGoodsDtos);
		return map;
	}

	@Override
	public BgGoodsDto getBgGoodsDtoById(long bgGoodsId) {
		BgGoodsDto bgGoodsDto = new BgGoodsDto();
		BgGoods bgGoods = bgGoodsDao.getById(bgGoodsId);
		if (bgGoods != null) {
			bgGoodsDto.setBgGoods(bgGoods);
			BgSku sku = bgSkuDao.getByBgGoodsId(bgGoodsId);
			if (sku != null) {
				bgGoodsDto.setBgSkuId(sku.getBgSkuId());
				bgGoodsDto.setWmsGoodsGbms(sku.getWmsGoodsGbms());
			}
		}
		return bgGoodsDto;
	}

	@Override
	public BgGoodsDto getBgGoodsDtoByBgSkuId(long bgSkuId) {
		BgGoodsDto bgGoodsDto = new BgGoodsDto();
		BgSku bgSku = bgSkuDao.getById(bgSkuId);
		if (bgSku != null) {
			bgGoodsDto.setBgSkuId(bgSku.getBgSkuId());
			bgGoodsDto.setWmsGoodsGbms(bgSku.getWmsGoodsGbms());
			BgGoods bgGoods = bgGoodsDao.getById(bgSku.getBgGoodsId());
			if (bgGoods != null) {
				bgGoodsDto.setBgGoods(bgGoods);
			}
		}
		return bgGoodsDto;
	}

	@Override
	public BgGoods getBgGoodsById(long bgGoodsId) {
		return bgGoodsDao.getById(bgGoodsId);
	}

	@Override
	public List<Long> getBaseStorageIds(Long[] wms_goods_ids) {
		// 通过过滤id，获取id交集
		List<Long> storageIds = new ArrayList<>();
		List<StorageGoodsStock> storageGoodsList = new ArrayList<>();
		long id = 0;
		for (int i = 0; i < wms_goods_ids.length; i++) {
			id = wms_goods_ids[i];
			storageGoodsList = storageGoodsStockDao.getStorageGoodsByGoodsId(id);
			// 判断是为了拿到第一份数据
			if (i == 0) {
				for (StorageGoodsStock sgs : storageGoodsList) {
					storageIds.add(sgs.storage_id);
				}
			} else {
				// 从第二份数据起，就需要聚合storageIds
				List<Long> tempIds = new ArrayList<>();
				for (StorageGoodsStock sgs : storageGoodsList) {
					tempIds.add(sgs.storage_id);
				}
				// 取交集，并存放在storageIds中
				storageIds.retainAll(tempIds);
			}
			// 如果提前为空，则结束
			if (storageIds.size() == 0) {
				return storageIds;
			}
		}
		return storageIds;
	}

	@Override
	public Map<String, Object> setRegion(long bgGoodsId, ZtreeBean ztreeBean, User user) {
		if (ztreeBean == null) {
			return buildSuccObj("");
		}
		// 为插入前台goods做准备
		BgGoods bgGoods = bgGoodsDao.getById(bgGoodsId);
		if (bgGoods == null) {
			return buildObj(-1, "范围设置失败");
		}
		// 获得该商品在当前已有学校，主要是为了单品范围删除时，同步删除聚合品范围
		List<Long> rdc_college_id_old_list = goodsDao.getRdcCollegesByBgGoodsId(bgGoodsId);
		List<Long> ldc_college_id_old_list = goodsDao.getLdcCollegesByBgGoodsId(bgGoodsId);
		List<Long> vm_college_id_old_list = goodsDao.getCollegeIdListByBgGoodsIdAndDistributeType(bgGoodsId, Sku.VM_DISTRIBUTE_TYPE);
		List<Long> rdc_college_id_new_list = new ArrayList<Long>();
		List<Long> ldc_college_id_new_list = new ArrayList<Long>();
		List<Long> vm_college_id_new_list = new ArrayList<Long>();

		//比较傻的方式，不需要利用goodsIds删除数据
		//List<Long> goodsIds = goodsDao.getGoodsIdsByBgGoodsId(bgGoodsId);
		// 软删除之前上单所有记录，is_del=1
		// 删除goods
		//if (goodsIds != null && goodsIds.size() > 0) {
			//skuDao.deleteByGoodsId(goodsIds);
			//goodsDao.deleteByBgGoodsId(bgGoodsId);
		 //}
		//软删除goods和sku
		goodsDao.deleteGoodsAndSkuByBgGoodsId(bgGoodsId);
		// 遍历新数据，插入
		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();
		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return buildSuccObj("");
		}
		// RDC$LDC 两个节点
		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean storageBean : rdcStorageChildrenZtree) {
					List<ZtreeBean> rdcCollegeChildrenZtree = storageBean.getChildren();
					// 学校
					if (rdcCollegeChildrenZtree == null || rdcCollegeChildrenZtree.size() == 0) {
						continue;
					}
					// 实际操作
					for (ZtreeBean collegeBean : rdcCollegeChildrenZtree) {
						// 已被选中
						if (Constants.ZTREECHECKED.equals(collegeBean.checked)) {
							// 记录新数据
							rdc_college_id_new_list.add(_.toLong(collegeBean.getId()));
							boolean isOld = false;
							if(rdc_college_id_old_list.contains(_.toLong(collegeBean.getId()))){
								isOld = true;
							}

							// 针对该商品新增的学校，前台goods插入或更新
							Goods goods = new Goods(bgGoods);
							goods.setStatus(Constants.GOOD_STATUS_ONSALE);
							goods.setCollegeId(_.toLong(collegeBean.getId()));
							long goods_id = goodsDao.insertOrUpdate(goods,true);
							if (goods_id == -1) {
								logger.info("范围设置时，插入goods失败");
								return buildErrObj("范围设置失败~");
							}
							// sku
							if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
								// 插入sku,bg_goods和bg_sku一对一关系
								BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
								long sku_id = skuService.addOrUpdateSku(bgSku,
										_.toLong(collegeBean.getId()),
										goods_id, Storage.RDC_STORAGE,
										bgGoods.getGoodsType(),true);
								if (sku_id == -1) {
									logger.info("范围设置时，插入sku失败");
									return buildErrObj("范围设置失败~");
								}
							} else if (bgGoods.getGoodsType() == 3) {
								// 插入sku,bg_goods和bg_sku一对多关系
								List<Long> bgSkuIds = skuPropertyDao
										.getBgSkuByBgGoodsId(bgGoodsId);
								if (bgSkuIds != null) {
									for (long bgSkuId : bgSkuIds) {
										BgSku bgSku = bgSkuDao.getById(bgSkuId);
										long sku_id = skuService
												.addOrUpdateSku(bgSku,
														_.toLong(collegeBean
																.getId()),
														goods_id,
														Storage.RDC_STORAGE,
														bgGoods.getGoodsType(),true);
										if (sku_id == -1) {
											logger.info("范围设置时，插入sku失败");
											return buildErrObj("范围设置失败~");
										} else if (sku_id == -2) {
											logger.info("范围设置时，由于单品sku变动，提醒用户从新编辑");
											return buildErrObj("您选择的单品sku范围出现了变动，请重新获取可选范围");
										}
									}
								}
							}
						} else {
							storageBean.setAllChecked(false);
							childrenZtreen.setAllChecked(false);
							ztreeBean.setAllChecked(false);
						}
					}
				}
			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// LDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean cityBean : ldcStorageChildrenZtree) {
					List<ZtreeBean> storageChildrenZtree = cityBean.getChildren();
					// 学校-->仓
					if (storageChildrenZtree == null || storageChildrenZtree.size() == 0) {
						continue;
					}
					for (ZtreeBean storageBean : storageChildrenZtree) {
						List<ZtreeBean> ldcCollegeChildrenZtree = storageBean.getChildren();
						// 学校
						if (ldcCollegeChildrenZtree == null
								|| ldcCollegeChildrenZtree.size() == 0) {
							continue;
						}
						// 实际操作
						for (ZtreeBean collegeBean : ldcCollegeChildrenZtree) {
							// 已被选中
							if (Constants.ZTREECHECKED.equals(collegeBean.checked)) {
								// 记录新数据
								ldc_college_id_new_list.add(_.toLong(collegeBean
										.getId()));
								boolean isOld = false;
								if(ldc_college_id_old_list.contains(_.toLong(collegeBean.getId()))){
									isOld = true;
								}
								// 针对该商品新增的学校，前台goods插入
								Goods goods = new Goods(bgGoods);
								goods.setCollegeId(_.toLong(collegeBean.getId()));
								long goods_id = goodsDao.insertOrUpdate(goods,true);
								if (goods_id == -1) {
									logger.info("范围设置时，插入goods失败");
									return buildErrObj("范围设置失败~");
								}
								if (bgGoods.getGoodsType() == 1
										|| bgGoods.getGoodsType() == 2) {
									// 插入sku,bg_goods和bg_sku一对一关系
									BgSku bgSku = bgSkuDao
											.getByBgGoodsId(bgGoodsId);
									long sku_id = skuService.addOrUpdateSku(bgSku,
											_.toLong(collegeBean.getId()),
											goods_id, Storage.LDC_STORAGE,
											bgGoods.getGoodsType(),true);
									if (sku_id == -1) {
										logger.info("范围设置时，插入sku失败");
										return buildErrObj("范围设置失败~");
									}
								} else if (bgGoods.getGoodsType() == 3) {
									// 插入sku,bg_goods和bg_sku一对多关系
									List<Long> bgSkuIds = skuPropertyDao
											.getBgSkuByBgGoodsId(bgGoodsId);
									if (bgSkuIds != null) {
										for (long bgSkuId : bgSkuIds) {
											BgSku bgSku = bgSkuDao
													.getById(bgSkuId);
											long sku_id = skuService
													.addOrUpdateSku(bgSku,
															_.toLong(collegeBean
																	.getId()),
															goods_id,
															Storage.LDC_STORAGE,
															bgGoods.getGoodsType(),true);
											if (sku_id == -1) {
												logger.info("范围设置时，插入sku失败");
												return buildErrObj("范围设置失败~");
											} else if (sku_id == -2) {
												logger.info("范围设置时，由于单品sku变动，提醒用户从新编辑");
												return buildErrObj("您选择的单品sku范围出现了变动，请重新获取可选范围");
											}
										}
									}
								}
							} else {
								cityBean.setAllChecked(false);
								storageBean.setAllChecked(false);
								childrenZtreen.setAllChecked(false);
								ztreeBean.setAllChecked(false);
							}
						}
					}
				}
			}else if(Constants.VM_STORAGE_NAME.equals(childrenZtreen.name)) {
				// 虚拟仓
				List<ZtreeBean> vmStorageChildrenZtree = childrenZtreen.getChildren();

				if (vmStorageChildrenZtree == null || vmStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean storageBean : vmStorageChildrenZtree) {
					List<ZtreeBean> vmCollegeChildrenZtree = storageBean.getChildren();
					// 学校
					if (vmCollegeChildrenZtree == null || vmCollegeChildrenZtree.size() == 0) {
						continue;
					}
					// 实际操作
					for (ZtreeBean collegeBean : vmCollegeChildrenZtree) {
						// 已被选中
						if (Constants.ZTREECHECKED.equals(collegeBean.checked)) {
							// 记录新数据
							vm_college_id_new_list.add(_.toLong(collegeBean.getId()));
							boolean isOld = false;
							if(vm_college_id_old_list.contains(_.toLong(collegeBean.getId()))){
								isOld = true;
							}
							// 针对该商品新增的学校，前台goods插入或更新
							Goods goods = new Goods(bgGoods);
							goods.setStatus(Constants.GOOD_STATUS_ONSALE);
							goods.setCollegeId(_.toLong(collegeBean.getId()));
							long goods_id = goodsDao.insertOrUpdate(goods,true);
							if (goods_id == -1) {
								logger.info("范围设置时，插入goods失败");
								return buildErrObj("范围设置失败~");
							}
							// sku
							if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
								// 插入sku,bg_goods和bg_sku一对一关系
								BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
								long sku_id = skuService.addOrUpdateSku(bgSku,
										_.toLong(collegeBean.getId()),
										goods_id, Storage.VM_STORAGE,
										bgGoods.getGoodsType(),true);
								if (sku_id == -1) {
									logger.info("范围设置时，插入sku失败");
									return buildErrObj("范围设置失败~");
								}
							} else if (bgGoods.getGoodsType() == 3) {
								// 插入sku,bg_goods和bg_sku一对多关系
								List<Long> bgSkuIds = skuPropertyDao
										.getBgSkuByBgGoodsId(bgGoodsId);
								if (bgSkuIds != null) {
									for (long bgSkuId : bgSkuIds) {
										BgSku bgSku = bgSkuDao.getById(bgSkuId);
										long sku_id = skuService
												.addOrUpdateSku(bgSku,
														_.toLong(collegeBean
																.getId()),
														goods_id,
														Storage.VM_STORAGE,
														bgGoods.getGoodsType(),true);
										if (sku_id == -1) {
											logger.info("范围设置时，插入sku失败");
											return buildErrObj("范围设置失败~");
										} else if (sku_id == -2) {
											logger.info("范围设置时，由于单品sku变动，提醒用户从新编辑");
											return buildErrObj("您选择的单品sku范围出现了变动，请重新获取可选范围");
										}
									}
								}
							}
						} else {
							storageBean.setAllChecked(false);
							childrenZtreen.setAllChecked(false);
							ztreeBean.setAllChecked(false);
						}
					}
				}
			}
		}

		// 修改单品范围，同步修改聚合品，只针对减少的范围
		if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
			// rdc
			for (Long rdc_college_id : rdc_college_id_old_list) {
				if (!rdc_college_id_new_list.contains(rdc_college_id)) {
					deleteMultiBySingleBgGoodsId(bgGoodsId, rdc_college_id, Storage.RDC_STORAGE);
				}
			}
			// ldc
			for (Long ldc_college_id : ldc_college_id_old_list) {
				if (!ldc_college_id_new_list.contains(ldc_college_id)) {
					deleteMultiBySingleBgGoodsId(bgGoodsId, ldc_college_id, Storage.LDC_STORAGE);
				}
			}
			// 虚拟仓
			for (Long vm_college_id : vm_college_id_old_list) {
				if (!vm_college_id_new_list.contains(vm_college_id)) {
					deleteMultiBySingleBgGoodsId(bgGoodsId, vm_college_id, Storage.VM_STORAGE);
				}
			}
		}

		// 设置goods_region,找出学校被全选的仓
		// 删除bgGoodsId相关区域信息
		//List<Long> region_storage_id_old_list = bgGoodsRegionDao.getStorageIdsByBgGoodsId(bgGoodsId);
		bgGoodsRegionDao.deleteByBgGoodsId(bgGoodsId);

		// 设置新区域
		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}
				long storageId = 0;
				for (ZtreeBean storageBean : rdcStorageChildrenZtree) {
					if (storageBean.isAllChecked) {
						storageId = _.toLong(storageBean.getId().substring(3));
						BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
						bgGoodsRegion.setBgGoodsId(bgGoodsId);
						bgGoodsRegion.setRegionId(storageId);
						bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
						bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_ALL);
						bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
					}
					// 备选
					/*boolean isOld = false;
					if(region_storage_id_old_list.contains(NumberUtils.toLong(storageBean.getId().substring(3)))){
						isOld = true;
					}*/
					if (storageBean.checked.equals("true")) {
						storageId = _.toLong(storageBean.getId().substring(3));
						BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
						bgGoodsRegion.setBgGoodsId(bgGoodsId);
						bgGoodsRegion.setRegionId(storageId);
						bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
						bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
						bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
					}
				}
			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// LDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean cityBean : ldcStorageChildrenZtree) {
					List<ZtreeBean> storageChildrenZtree = cityBean.getChildren();
					// 学校-->仓
					if (storageChildrenZtree == null || storageChildrenZtree.size() == 0) {
						continue;
					}
					long storageId = 0;
					for (ZtreeBean storageBean : storageChildrenZtree) {
						// 目前仅仅标识了单品和组合品的全仓售卖
						if (bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_SINGLE
								|| bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_GROUP) {
							if (storageBean.isAllChecked) {
								storageId = _.toLong(storageBean.getId().substring(3));
								BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
								bgGoodsRegion.setBgGoodsId(bgGoodsId);
								bgGoodsRegion.setRegionId(storageId);
								bgGoodsRegion.setRegionType(Constants.GOOD_STATUS_ONSALE);
								bgGoodsRegion.setStatus(BgGoodsRegion.REGION_TYPE_ALL);
								bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
							}
						}
						// 备选
						/*boolean isOld = false;
						if(region_storage_id_old_list.contains(NumberUtils.toLong(storageBean.getId().substring(3)))){
							isOld = true;
						}*/
						if (storageBean.checked.equals("true")) {
							storageId = _.toLong(storageBean.getId().substring(3));
							BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
							bgGoodsRegion.setBgGoodsId(bgGoodsId);
							bgGoodsRegion.setRegionId(storageId);
							bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
							bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
							bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
						}
					}
					// 备选
					long cityId = 0;
					if (cityBean.checked.equals("true")) {
						cityId = _.toLong(cityBean.getId().substring(5));
						BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
						bgGoodsRegion.setBgGoodsId(bgGoodsId);
						bgGoodsRegion.setRegionId(cityId);
						bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
						bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_CITY);
						bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
					}
				}
			}else if(Constants.VM_STORAGE_NAME.equals(childrenZtreen.name)) {
				// 虚拟仓
				List<ZtreeBean> vmStorageChildrenZtree = childrenZtreen.getChildren();
				if (vmStorageChildrenZtree == null || vmStorageChildrenZtree.size() == 0) {
					continue;
				}
				long storageId = 0;
				for (ZtreeBean storageBean : vmStorageChildrenZtree) {
					if (storageBean.isAllChecked) {
						storageId = _.toLong(storageBean.getId().substring(3));
						BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
						bgGoodsRegion.setBgGoodsId(bgGoodsId);
						bgGoodsRegion.setRegionId(storageId);
						bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
						bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_ALL);
						bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
					}
					// 备选
					/*boolean isOld = false;
					if(region_storage_id_old_list.contains(NumberUtils.toLong(storageBean.getId().substring(3)))){
						isOld = true;
					}*/
					if (storageBean.checked.equals("true")) {
						storageId = _.toLong(storageBean.getId().substring(3));
						BgGoodsRegion bgGoodsRegion = new BgGoodsRegion();
						bgGoodsRegion.setBgGoodsId(bgGoodsId);
						bgGoodsRegion.setRegionId(storageId);
						bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
						bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
						bgGoodsRegionDao.insertOrUpdate(bgGoodsRegion,true);
					}
				}
			}
		}

		return buildSuccObj("范围设置成功~");
	}

	@Override
	public Map<String, Object> setRegionPrice(long bgGoodsId, long regionId, int flag, int originPrice,
			int wapPrice, int appPrice, int maxNum) {
		return null;
	}
	/**
	 * 通过后台商品ID查询商品图片
	 * @return
	 */
	@Override
	public List<BgGoodsImage> getBgGoodsImageListByBgGoodsId(Long bgGoodsId){
		if(bgGoodsId == null) {
			return new ArrayList<BgGoodsImage>();
		}
		return bgGoodsImageDao.getBgGoodsImageListByBgGoodsId(bgGoodsId);
	}
	@Override
	public List<BgGoods4Manager> getBgGoodsInfoByPage(int storageType, Map<String, Object> param,
			PaginationInfo paginationInfo) {

		List<BgGoods4Manager> bgGoodsList = null;
		
		if (param.size() == 0 || (param.size() == 1 && (param.get("goods_name") != null || param.get("category_id") != null)) || 
			(param.size() == 2 && param.get("goods_name") != null && param.get("category_id") != null)) {
			// 直接查询bg_goods
			bgGoodsList = bgGoodsDao.getBgGoodsInfoByPage(param, paginationInfo);
		} else {
			if(param.containsKey("storage_id")){
				//选择仓库后，仓库类型已仓库为准
				Long storageId = (Long)param.get("storage_id");
				Storage storage = StroageConstant.getStorageById(storageId);
				if(storage != null){
					storageType = storage.getStorageType();
				}
			}
			if(storageType == Storage.VM_STORAGE){
				bgGoodsList = bgGoodsDao.getGoods4VmStorageInfoByPage(param, paginationInfo);
			}else if (storageType == Storage.ALL_STORAGE) {
				bgGoodsList = bgGoodsDao.getGoodsInfoByPage(param, paginationInfo);
			} else {
				param.put("isLdc", storageType);
				bgGoodsList = bgGoodsDao.getGoodsInfoByPage(param, paginationInfo);
			}
		}
		for (BgGoods4Manager bgGoods : bgGoodsList) {
			Category category = CategoryConstant.getCategoryById(bgGoods.getCategoryId());
			if (category == null) {
				category = categoryDao.getById(bgGoods.getCategoryId());
				if (category != null) {
					bgGoods.setCategoryName(category.getCategoryName());
				}
			}else{
				bgGoods.setCategoryName(category.getCategoryName());
			}
		}
		return bgGoodsList;
	}

	@Override
	public List<BgGoods> getBgGoodsList(String searchStr, PaginationInfo paginationInfo) {
		return bgGoodsDao.getBgGoodsList(searchStr, paginationInfo);
	}

	@Override
	public List<BgGoods> selectSingleByPage(String searchStr, PaginationInfo paginationInfo) {
		return bgGoodsDao.selectSingleByPage(searchStr, paginationInfo);
	}

	@Override
	public boolean batchCopy(College college) {
		if (college == null) {
			return false;
		}

		// 处理rdc
		long rdcId = college.getRdcStorageId();
		List<BgGoodsRegion> bgGoodsRegions = bgGoodsRegionDao.getByRegionId(rdcId, 0);
		BgGoods bgGoods = null;
		if (bgGoodsRegions != null && bgGoodsRegions.size() > 0) {
			for (BgGoodsRegion bgGoodsRegion : bgGoodsRegions) {
				if (bgGoodsRegion != null) {
					bgGoods = null;
					bgGoods = bgGoodsDao.getById(bgGoodsRegion.getBgGoodsId());
					if (bgGoods != null && bgGoods.getIsDel() == 0) {
						if (!bgGoods2Goods(bgGoods, college.getCollegeId(),rdcId, 0)) {
							return false;
						}
					}
				}
			}
		}
		// 处理ldc
		long ldcId = college.getLdcStorageId();
		bgGoodsRegions = bgGoodsRegionDao.getByRegionId(ldcId, 0);
		if (bgGoodsRegions != null && bgGoodsRegions.size() > 0) {
			for (BgGoodsRegion bgGoodsRegion : bgGoodsRegions) {
				if (bgGoodsRegion != null) {
					bgGoods = null;
					bgGoods = bgGoodsDao.getById(bgGoodsRegion.getBgGoodsId());
					if (bgGoods != null && bgGoods.getIsDel() == 0) {
						if (!bgGoods2Goods(bgGoods, college.getCollegeId(),ldcId, 1)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean bgGoods2Goods(BgGoods bgGoods, long collegeId,long storageId, int isLdc) {
		//获取模板价格
		BgGoodsRegion temp = bgGoodsRegionDao.getByBgGoodsIdAndRegionId(bgGoods.getBgGoodsId(), storageId, 1);
		
		Goods goods = new Goods(bgGoods);
		goods.setStatus(Constants.GOOD_STATUS_ONSALE);
		goods.setCollegeId(collegeId);
		if(temp != null){
			goods.setMaxNum(temp.getMaxNum());
		}
		long goods_id = goodsDao.insertOrUpdate(goods,true);
		if (goods_id == -1) {
			return false;
		}
		if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
			// 插入sku,bg_goods和bg_sku一对一关系
			BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoods.getBgGoodsId());
			if(bgSku != null && temp != null){
				bgSku.setOriginPrice(temp.getOriginPrice());
				bgSku.setWapPrice(temp.getWapPrice());
				bgSku.setAppPrice(temp.getAppPrice());
				bgSku.setStock(temp.getStock());
			}
			long sku_id = skuService.addOrUpdateSku(bgSku, collegeId, goods_id, isLdc,
					bgGoods.getGoodsType(),true);
			if (sku_id == -1) {
				return false;
			}
		} else if (bgGoods.getGoodsType() == 3) {
			// 插入sku,bg_goods和bg_sku一对多关系
			List<Long> bgSkuIds = skuPropertyDao.getBgSkuByBgGoodsId(bgGoods.getBgGoodsId());
			if (bgSkuIds != null) {
				for (long bgSkuId : bgSkuIds) {
					BgSku bgSku = bgSkuDao.getById(bgSkuId);
					long sku_id = skuService.addOrUpdateSku(bgSku, collegeId, goods_id, isLdc,
							bgGoods.getGoodsType(),true);
					if (sku_id == -1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchCopy(College college, int isLdc) {

		return false;
	}

	// 单品/聚合品删除
	@Override
	public boolean batchDelete(long bgGoodsId, long bgSkuId, long collegeId, int isLdc) {
		int di = skuDao.deleteByBgGoodsIdCollegeIdIsLdc(bgGoodsId, bgSkuId, collegeId, isLdc);

		if (di < 0) {
			return false;
		}
		List<Sku> skuList = skuDao.getListByBgGoodsIdCollegeId(bgGoodsId, collegeId);
		if (skuList == null || skuList.size() == 0) {
			int gdi = goodsDao.deleteByBgGoodsIdAndCollegeId(bgGoodsId, collegeId);
			if (gdi < 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean batchDelete(long collegeId, int isLdc) {
		// 跟进学校id和商品类型，删除sku
		skuDao.deleteByBgGoodsIdCollegeIdIsLdc(-1, -1, collegeId, isLdc);
		// 查询所有不可用的goodsid
		List<Long> goodsIds = goodsDao.getNeedDelGoods();
		// 删除所有不可用的goods
		if (goodsIds != null && goodsIds.size() > 0) {
			goodsDao.deleteGoodsByIds(goodsIds);
		}
		return true;
	}

	/**
	 * 根据单品bgGoodsId级联删除聚合品
	 * 
	 * @param bgGoodsId
	 * @param collegeId
	 * @param isLdc
	 * @return
	 */
	private boolean deleteMultiBySingleBgGoodsId(long bgGoodsId, long collegeId, int isLdc) {
		// 根据单品bgGoodsId获取聚合品
		BgSku bgSkuTemp = bgSkuDao.getByBgGoodsId(bgGoodsId);
		if (bgSkuTemp != null) {
			List<Long> bgGoodsIds = skuPropertyDao.getBgGoodsIdsByBgSkuId(bgSkuTemp.getBgSkuId());
			if (bgGoodsIds != null && bgGoodsIds.size() > 0) {
				for (Long bgGoodsIdTemp : bgGoodsIds) {
					batchDelete(bgGoodsIdTemp, bgSkuTemp.getBgSkuId(), collegeId, isLdc);
				}
			}
		}
		return true;
	}

	@Override
	public List<BgGoods> getSingleBgGoodsListByWmsGoodsId(long wmsGoodsId) {
		// TODO Auto-generated method stub
		return bgGoodsDao.getSingleBgGoodsByWmsGoodsId(wmsGoodsId);
	}

	@Override
	public List<BgGoods> getMultBgGoodsListByWmsGoodsId(long wmsGoodsId) {
		// TODO Auto-generated method stub
		return bgGoodsDao.getMultBgGoodsByWmsGoodsId(wmsGoodsId);
	}

	@Override
	public int updateUpdateTimeByBgGoodsId(long bgGoodsId) {
		return bgGoodsDao.updateUpdateTimeByBgGoodsId(bgGoodsId);
	}

}
