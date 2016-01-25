package com.mall.admin.service.thirdShop.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.BgGoodsImageDao;
import com.mall.admin.model.dao.goods.BgGoodsRegionDao;
import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.dao.goods.BgSkuGbmDao;
import com.mall.admin.model.dao.goods.GoodsDao;
import com.mall.admin.model.dao.goods.GoodsPropertyDao;
import com.mall.admin.model.dao.goods.PropertyValueDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.model.dao.wms.StorageGoodsStockDao;
import com.mall.admin.model.dao.wms.WmsGoodsDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.base.BaseServiceImpl;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.thirdShop.BgGoods4ThirdService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.BgSkuGbm;
import com.mall.admin.vo.goods.GoodsProperty;
import com.mall.admin.vo.goods.PropertyCategory;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.PropertyValue;
import com.mall.admin.vo.goods.SkuProperty;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

/**
 * 简化操作的商城，应该这样区分
 * 将第三方商品单独拆分出来，主要是要更明确的定义模板
 * 将库存商品添加和模板添加融合在一起
 *@date 2015年12月15日 下午1:31:59  
 *@author zhangshuai
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgGoods4ThirdServiceImpl extends BaseServiceImpl implements BgGoods4ThirdService {
	@Autowired
	private BgGoodsDao bgGoodsDao;
	@Autowired
	private WmsGoodsDao wmsGoodsDao;
	@Autowired
	private BgSkuDao bgSkuDao;
	@Autowired
	private BgSkuGbmDao bgSkuGbmDao;
	@Autowired
	private StorageGoodsStockDao storageGoodsStockDao;
	@Autowired
	private SkuPropertyDao skuPropertyDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private SkuService skuService;
	@Autowired
	private BgGoodsRegionDao bgGoodsRegionDao;
	@Autowired
	private GoodsPropertyDao goodsPropertyDao;
	@Autowired
	private PropertyValueDao propertyValueDao;
	@Autowired
	private BgGoodsImageDao bgGoodsImageDao;
	@Autowired
	private WmsGoodsService wmsGoodsService;
	@Autowired
	private PropertyCategoryService propertyCategoryService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private StorageGoodsStockService storageGoodsStockService;

	@Override
	public Map<String, Object> addBgGoods(BgGoods bgGoods,
			Map<String, Map<String, Object>> param,List<String> valueList,List<BgGoodsImage> imageList) {
		PropertyCategory category = propertyCategoryService.getById(bgGoods.getCategoryId());
		Storage storage = storageService.getStorageById(bgGoods.getStorageId());
		if(category == null || storage == null){
			return buildErrObj("系统错误，请重试！");
		}
		//首先插入bgGoods
		bgGoods.setWeight(Constants.GOOD_WEIGHT_DEFAULT);
		if (param.size() > 1) {
			bgGoods.setGoodsType(BgGoods.GOODS_TYPE_MULTI);// 聚合商品
		} else {
			bgGoods.setGoodsType(BgGoods.GOODS_TYPE_SINGLE);// 单品
		}
		bgGoods.setMaxNum(Constants.GOOD_MAXNUM);
		//默认开启
		bgGoods.setGoodsStatus(Constants.GOOD_STATUS_SALING);
		bgGoods.setIsDel(0);
		BgGoods bgGoodsTemp = bgGoodsDao.getById(bgGoods.getBgGoodsId());
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
			long ret = bgGoodsDao.insert(bgGoods);
			if (ret == -1) {
				logger.info("单品bgGoods插入失败，返回结果：" + ret);
				return buildErrObj("商品添加失败！");
			}
			bgGoods.setBgGoodsId(ret);
		}
		
		//添加商品图片
		bgGoodsImageDao.deleteBgImageByBgGoodsId(bgGoods.getBgGoodsId());
		if(CollectionUtils.isNotEmpty(imageList)){
			for(BgGoodsImage goodsImage : imageList) {
				goodsImage.setBgGoodsId(bgGoods.getBgGoodsId());
			}
			long result =  bgGoodsImageDao.insertBgImage(imageList);
			if (result == -1) {
				logger.info("单品bgGoodsImage插入失败，bgGoodsId：" + bgGoods.getBgGoodsId());
				return buildErrObj("商品添加失败！");
			}
		}
		
		//添加BgGoods属性
		editBgGoodsProperty(bgGoods,valueList);
		// 插入skuProperty,其中包含bgGoods与bgSku的关系
		List<SkuProperty> old_skuPropertyList = skuPropertyDao.getByBgGoodsId(bgGoods.getBgGoodsId());
		//添加wmsGoods和sku;
		for(String key : param.keySet()){
			String code = (String)param.get(key).get("code");
			String imageUrl = (String)param.get(key).get("imageUrl");
			long stock = NumberUtils.toLong((String)param.get(key).get("stock"));
			long price = MoneyUtils.yuan2Fen(NumberUtils.toDouble((String)param.get(key).get("price")));
			long bgSkuId = NumberUtils.toLong((String)param.get(key).get("bgSkuId"),-1);
			long wmsGoodsId = param.get(key).get("wmsGoodsId") == null ? -1 : (Long)param.get(key).get("wmsGoodsId");
			//添加wmsGoods
			WmsGoods wmsGoods = new WmsGoods(bgGoods);
			wmsGoods.setCatg_name(category.getPropertyCategoryName());
			wmsGoods.setWms_goods_gbm(code);
			wmsGoods.setStorageType(storage.getStorageType());
			wmsGoods.setStock(stock);
			wmsGoods.setWms_goods_id(wmsGoodsId);
			//操作库存商品及库存
			wmsGoodsId = wmsGoodsService.insertOrUpdateWithStock(wmsGoods);
			//添加sku
			bgSkuId = (Long)editBgSku(bgGoods,bgSkuId,imageUrl,code, wmsGoodsId,price);
			//添加属性
			String[] pvArray = key.split(":");
			editBgSkuProperty(old_skuPropertyList,bgGoods, bgSkuId, pvArray);
		}	
		//删除过期skuProperty
		if (old_skuPropertyList != null && old_skuPropertyList.size() > 0) {
			for (SkuProperty sp : old_skuPropertyList) {
				if (sp.getSkuPropertyId() > 0) {
					skuPropertyDao.deleteById(sp.getSkuPropertyId());
				}
			}
		}
		return buildObj(0, "添加成功");
	}

	private Object editBgSku(BgGoods bgGoods,long bgSkuId,String imageUrl,String code,long wmsGoodsId,long price){
		BgSku bgSku_old = bgSkuDao.getById(bgSkuId);
		BgSku bgSku = new BgSku();
		//根据bgGoods类型确定是否加入bgGoodsId
		if(bgGoods.getGoodsType() == BgGoods.GOODS_TYPE_MULTI){
			//聚合品关联 管理放在goods_property表中
			bgSku.setBgGoodsId(-1);
		}else{
			bgSku.setBgGoodsId(bgGoods.getBgGoodsId());
		}
		bgSku.setSkuType(bgGoods.getGoodsType());
		bgSku.setOperator(bgGoods.getOperator());
		bgSku.setWmsGoodsGbms(code);
		bgSku.setOriginPrice(price);
		bgSku.setImageUrl(imageUrl);
		bgSku.setStorageId(bgGoods.getStorageId());
		if (bgSku_old != null) {
			bgSku.setBgSkuId(bgSkuId);
			int ui = bgSkuDao.updateByObject(bgSku);
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
		bgSkuGbm.setWmsGoodsId(wmsGoodsId);
		bgSkuGbm.setNum(1);
		bgSkuGbm.setBgSkuId(bgSkuId);
		bgSkuGbm.setOperator(bgGoods.getOperator());
		BgSkuGbm temp = bgSkuGbmDao.getByBgSkuIdWmsGoodsId(bgSkuId, wmsGoodsId);
		if (temp != null) {
			int ui = bgSkuGbmDao.updateByBgSkuIdWmsGoodsId(bgSkuGbm);
			if (ui < 0) {
				return buildObj(-1, "商品编辑失败");
			}
			// 标示已有关系
			for (BgSkuGbm b : old_bgSkuGbms) {
				if (b.getBgSkuId() == bgSkuId && b.getWmsGoodsId() == wmsGoodsId) {
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
		return bgSkuId;
	}
	
	private Map<String, Object> editBgGoodsProperty(BgGoods bgGoods,List<String> valueList){
		List<GoodsProperty> old_goodsPropertyList = goodsPropertyDao.getByBgGoodsId(bgGoods.getBgGoodsId());
		GoodsProperty goodsProperty = new GoodsProperty();
		GoodsProperty temp = null;
		//添加BgGoods属性
		for (String pvStr : valueList) {
			long pvId = NumberUtils.toLong(pvStr);
			goodsProperty.setBgGoodsId(bgGoods.getBgGoodsId());
			goodsProperty.setPropertyValueId(pvId);
			goodsProperty.setImageUrl("");
			goodsProperty.setIsDel(0);
			temp = goodsPropertyDao.getByBgGoodsIdValueId(bgGoods.getBgGoodsId(), pvId);
			if (temp != null) {
				int ui = goodsPropertyDao.updateByBgGoodsIdVauleId(goodsProperty);
				if (ui < 0) {
					return buildObj(-1, "商品编辑失败");
				}
				for (GoodsProperty g : old_goodsPropertyList) {
					if (g.getBgGoodsId() == bgGoods.getBgGoodsId() && g.getPropertyValueId() == pvId) {
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
		return buildObj(0, "success");
	}
	private Map<String, Object> editBgSkuProperty(List<SkuProperty> old_skuPropertyList,BgGoods bgGoods,Long bgSkuId,String[] pvArray){
		SkuProperty skuPropertyPojo = new SkuProperty();
		SkuProperty tempSkuP = null;
		for (String key : pvArray) {
			Long pv = NumberUtils.toLong(key);
			skuPropertyPojo.setBgGoodsId(bgGoods.getBgGoodsId());
			skuPropertyPojo.setBgSkuId(bgSkuId);
			skuPropertyPojo.setPropertyValueId(pv);
			PropertyValue pValue = propertyValueDao.getById(pv);
			if (pValue != null) {
				skuPropertyPojo.setPropertyNameId(pValue.getPropertyNameId());
			}
			tempSkuP = skuPropertyDao.getByBgSkuIdValueId(bgGoods.getBgGoodsId(), bgSkuId, pv);
			if (tempSkuP != null) {
				for (SkuProperty sp : old_skuPropertyList) {
					if (sp.getBgGoodsId() == bgGoods.getBgGoodsId() && sp.getBgSkuId() == bgSkuId
							&& sp.getPropertyValueId() == pv) {
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
		return null;
	}

	@Override
	public BgGoods getBgGoodsById(long bgGoodsId) {
		return bgGoodsDao.getById(bgGoodsId);
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
	public List<BgGoods4Manager> getBgGoodsDtoByPage(Map<String, Object> param,
			PaginationInfo paginationInfo) {
		List<BgGoods4Manager> bgGoodsDtoList = bgGoodsDao.getBgGoodsDtoByPage(param, paginationInfo);
		
		for (BgGoods4Manager bgGoods : bgGoodsDtoList) {
			//类别来源于属性的一级类目
			PropertyCategory category = propertyCategoryService.getById(bgGoods.getCategoryId());
			if (category != null && category.getLevel() == 1) {
				bgGoods.setCategoryName(category.getPropertyCategoryName());
			}
			
			List<SkuProperty> skuPropertyList = skuPropertyDao.getByBgGoodsId(bgGoods.getBgGoodsId());
			long stock = 0;
			for(SkuProperty skuProperty : skuPropertyList){
				List<Long> wmsGoodsIdList = bgSkuGbmDao.getWmsGoodsIdByBgSkuId(skuProperty.getBgSkuId());
				if(wmsGoodsIdList == null || wmsGoodsIdList.size() == 0){
					return null;
				}
				StorageGoodsStock storageGoodsStock = storageGoodsStockDao.getByGoodsIdAndStorageId(wmsGoodsIdList.get(0),bgGoods.getStorageId());
				if(storageGoodsStock == null){
					return null;
				}
				stock += storageGoodsStock.getStock();
			}
			bgGoods.setStock(stock);
		}
		return bgGoodsDtoList;
	}

	@Override
	public int updateUpdateTimeByBgGoodsId(long bgGoodsId) {
		return bgGoodsDao.updateUpdateTimeByBgGoodsId(bgGoodsId);
	}

	@Override
	public Map<Long, Map<Long,SkuProperty>> getBgGoodsProperty(Long bgGoodsId) {
		List<SkuProperty> skuPropertyList = skuPropertyDao.getByBgGoodsId(bgGoodsId);
		if(skuPropertyList == null || skuPropertyList.size() == 0){
			return null;
		}
		Map<Long, Map<Long,SkuProperty>> bgSkuPropertyValues = new HashMap<>();
		for(SkuProperty skuProperty : skuPropertyList){
			if(bgSkuPropertyValues.containsKey(skuProperty.getBgSkuId())){
				bgSkuPropertyValues.get(skuProperty.getBgSkuId()).put(skuProperty.getPropertyValueId(), skuProperty);
			}else{
				Map<Long, SkuProperty> skuPropertyMap = new HashMap<>();
				skuPropertyMap.put(skuProperty.getPropertyValueId(), skuProperty);
				bgSkuPropertyValues.put(skuProperty.getBgSkuId(), skuPropertyMap);
			}
		}
		return bgSkuPropertyValues;
	}

	@Override
	public Map<String, String> producePropertyImageMap(
			List<PropertyName> propertyNameList,List<GoodsProperty> selectedProperty) {
		Map<String, String> propertyImageMap = new HashMap<>();
		Map<Long,PropertyName> pnListMap = new HashMap<>();
		for(PropertyName propertyName : propertyNameList){
			if(propertyName.getNeedPic() == 1){
				pnListMap.put(propertyName.getPropertyNameId(), propertyName);
			}
		}
		if(pnListMap.size() == 1){
			//单一属性有图片
			for(GoodsProperty goodsProperty : selectedProperty){
				PropertyValue pv = propertyValueDao.getById(goodsProperty.getPropertyValueId());
				if(pnListMap.containsKey(pv.getPropertyNameId())){
					propertyImageMap.put(Long.toString(pv.getPropertyValueId()), "");
				}
			}
		}else{
			//目前产品确定不存在多个属性同时有图片
		}
		return propertyImageMap;
	}

	@Override
	public Map<String, BgSku> producePropertyBgSkuMap(
			List<PropertyName> propertyNameList,
			Map<Long, Map<Long, SkuProperty>> bgSkuPropertyValues,Map<String, String> propertyImageMap) {
		Map<String, BgSku> propertyBgSkuMap = new HashMap<>();
		for(Long bgSkuId : bgSkuPropertyValues.keySet()){
			BgSku bgSku = bgSkuDao.getById(bgSkuId);
			List<Long> wmsGoodsIdList = bgSkuGbmDao.getWmsGoodsIdByBgSkuId(bgSkuId);
			if(wmsGoodsIdList == null || wmsGoodsIdList.size() == 0){
				return null;
			}
			StorageGoodsStock storageGoodsStock = storageGoodsStockDao.getByGoodsIdAndStorageId(wmsGoodsIdList.get(0),bgSku.getStorageId());
			if(storageGoodsStock == null){
				return null;
			}
			bgSku.setStock(storageGoodsStock.getStock());
			//按顺序生成key
			StringBuilder keyBuild = new StringBuilder();
			for(PropertyName n : propertyNameList){
				for(Long pvId : bgSkuPropertyValues.get(bgSkuId).keySet()){
					if(bgSkuPropertyValues.get(bgSkuId).get(pvId).getPropertyNameId() == n.getPropertyNameId()){
						keyBuild.append(":").append(pvId);
					}
				}
			}
			String key = keyBuild.toString().substring(1);
			propertyBgSkuMap.put(key, bgSku);
			//为图片赋值
			for(String imageKey : propertyImageMap.keySet()){
				if(key.contains(imageKey) && StringUtils.isEmpty(propertyImageMap.get(imageKey))){
					propertyImageMap.put(imageKey, bgSku.getImageUrl());
				}
			}
		}
		return propertyBgSkuMap;
	}

	@Override
	public List<BgSku> produceBgSkuList(
			Map<Long, Map<Long, SkuProperty>> bgSkuPropertyValues) {
		List<BgSku> bgSkuList = new ArrayList<>();
		for(Long bgSkuId : bgSkuPropertyValues.keySet()){
			BgSku bgSku = bgSkuDao.getById(bgSkuId);
			List<Long> wmsGoodsIdList = bgSkuGbmDao.getWmsGoodsIdByBgSkuId(bgSkuId);
			if(wmsGoodsIdList == null || wmsGoodsIdList.size() == 0){
				return null;
			}
			StorageGoodsStock storageGoodsStock = storageGoodsStockDao.getByGoodsIdAndStorageId(wmsGoodsIdList.get(0),bgSku.getStorageId());
			if(storageGoodsStock == null){
				return null;
			}
			bgSku.setStock(storageGoodsStock.getStock());
			//按顺序生成key
			Map<Long,PropertyValue> pnSkuPropertyMap = new HashMap<>();
			for(Long pvId : bgSkuPropertyValues.get(bgSkuId).keySet()){
				PropertyValue pv = propertyValueDao.getById(pvId);
				pnSkuPropertyMap.put(pv.getPropertyNameId(), pv);
			}
			bgSku.setSkuPropertyMap(pnSkuPropertyMap);
			bgSkuList.add(bgSku);
		}
		return bgSkuList;
	}

	@Override
	public int updateBgGoodsStatus(Long bgGoodsId, int goodsStatus) {
		return bgGoodsDao.updateStatusByBgGoodsId(bgGoodsId, goodsStatus);
	}
	
}
