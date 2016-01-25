package com.mall.admin.service.goods.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.dao.goods.GoodsDao;
import com.mall.admin.model.dao.goods.SkuDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.service.goods.BgGoodsRegionService;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.dto.CollegeDto;

@Service
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	GoodsDao goodsDao;
	@Autowired
	CollegeService collegeService;
	@Autowired
	BgSkuDao bgSkuDao;
	@Autowired
	SkuDao skuDao;
	@Autowired
	BgGoodsRegionService bgGoodsRegionService;
	@Autowired
	BgGoodsDao bgGoodsDao;
	@Autowired
	SkuPropertyDao skuPropertyDao;

	@Override
	public Goods getById(long goodsId) {
		return goodsDao.getById(goodsId);
	}

	@Override
	public Map<String, Object> addGoods(Goods goods, Map<String, Integer> skuParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> selectSingleGoods(long categoryId, long collegeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> getCollegeIds(long bgGoodsId) {
		return goodsDao.getCollegesByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<Long> getRdcCollegeIds(long bgGoodsId) {
		return goodsDao.getRdcCollegesByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<Long> getLdcCollegeIds(long bgGoodsId) {
		return goodsDao.getLdcCollegesByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<Long> getVmCollegeIds(long bgGoodsId) {
		return goodsDao.getCollegeIdListByBgGoodsIdAndDistributeType(bgGoodsId, Sku.VM_DISTRIBUTE_TYPE);
	}

	@Override
	public String updateWeightStockStatus(long bgGoodsId, long storageId, long weight, long stock, int status,
			int isLdc) {
		StringBuffer resultinfo = new StringBuffer("");
		BgGoods bgGoods = bgGoodsDao.getById(bgGoodsId);
		if (bgGoods == null) {
			return "没有找到后台商品";
		}

		int sr = bgGoodsRegionService.update(storageId, bgGoodsId, 1, weight, stock, status);
		if (sr != 1)
			return "更新模板信息失败";
		// 获取所有学校
		List<Long> collegeIds = getCollegeIds(bgGoodsId);

		// 过滤学校
		List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds, storageId, isLdc);

		// 根据商品类型判断后台bgsku
		List<Long> bgSkuIds = new ArrayList<>();
		if (bgGoods.getGoodsType() == 3) {
			bgSkuIds = skuPropertyDao.getBgSkuByBgGoodsId(bgGoodsId);
		} else {
			BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
			if (bgSku == null) {
				return "查询模板sku失败";
			}
			bgSkuIds.add(bgSku.getBgSkuId());
		}

		College college = null;
		Sku sku = null;
		// 可以修改为批量更新，将学校维度和是否RDC、LDC拿出来
		for (CollegeDto collegeDto : collegeDtos) {
			college = collegeDto.getCollege();
			if (college == null) {
				// return -1;
				continue;
			}
			Goods goods = goodsDao.getByBgGoodsIdAndCollegeId(bgGoodsId, college.getCollegeId());
			// 做了双层验证,可以屏蔽商品类型
			if (goods == null) {
				// return -1;
				continue;
			}
			boolean checkResult = true;
			for (Long bgSkuId : bgSkuIds) {
				sku = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSkuId, college.getCollegeId(),
						goods.getGoodsId(), isLdc);
				if (sku == null) {
					// return -1;
					continue;
				}
				// 修改售卖状态时，如果价钱为0，则不再修改商品的状态。
				if (sku.getAppPrice() <= 0 || sku.getWapPrice() <= 0) {
					LogConstant.mallLog.info("商品{}的wap价格和app价格小于等于0.禁止更新商品的售卖状态~", sku.getSkuId());
					resultinfo.append("商品【" + goods.getBgGoodsName()
							+ "】的wap价格和app价格小于等于0.禁止更新商品的售卖状态~\r\n");
					checkResult = false;
					continue;
				}
				int result = skuDao.updateStock(sku.getSkuId(), stock, status);
				if (result < 0) {
					// return -1;
					continue;
				}
			}
			if (!checkResult) {
				continue;
			}
			//goods状态优先级：在售->售罄->代售
			List<Sku> skuList = skuDao.getListByGoodsIdSkuStatus(goods.getGoodsId(), Constants.GOOD_STATUS_SALING);
			if(skuList != null && skuList.size() > 0){
				goodsDao.updateWeightStatus(goods.getGoodsId(), weight, Constants.GOOD_STATUS_SALING);
			}else{
				skuList = skuDao.getListByGoodsIdSkuStatus(goods.getGoodsId(), Constants.GOOD_STATUS_STOPSALE);
				if(skuList != null && skuList.size() > 0){
					goodsDao.updateWeightStatus(goods.getGoodsId(), weight, Constants.GOOD_STATUS_STOPSALE);
				}else{
					goodsDao.updateWeightStatus(goods.getGoodsId(), weight, Constants.GOOD_STATUS_ONSALE);
				}
			}
			/*int r2 = goodsDao.updateWeightStatus(goods.getGoodsId(), weight, status);
			if (r2 < 0) {
				// return -1;
				LogConstant.mallLog.info("更新商品({})信息失败，权重{}，状态{}~", goods.getGoodsId(), weight, status);
				resultinfo.append("学校【" + college.getCollegeName() + "】更新商品【" + goods.getBgGoodsName()
						+ "】失败\r\n");
				continue;
			}*/
			resultinfo.append("学校【" + college.getCollegeName() + "】更新商品【" + goods.getBgGoodsName()
					+ "】成功\r\n");
		}
		if (resultinfo.length() == 0) {
			resultinfo.append("更新数据0条");
		}
		return resultinfo.toString();
	}

	@Override
	public String updateStatus(long bgGoodsId, long storageId, int status, int isLdc) {
		StringBuffer resultinfo = new StringBuffer("");
		BgGoods bgGoods = bgGoodsDao.getById(bgGoodsId);
		if (bgGoods == null) {
			return "没有找到后台商品";
		}

		int sr = bgGoodsRegionService.update(storageId, bgGoodsId, 1, status);
		if (sr != 1)
			return "更新模板信息失败";
		// 获取所有学校
		List<Long> collegeIds = getCollegeIds(bgGoodsId);

		// 过滤学校
		List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds, storageId, isLdc);

		// 根据商品类型判断后台bgsku
		List<Long> bgSkuIds = new ArrayList<>();
		if (bgGoods.getGoodsType() == 3) {
			bgSkuIds = skuPropertyDao.getBgSkuByBgGoodsId(bgGoodsId);
		} else {
			BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
			if (bgSku == null) {
				return "查询模板sku失败";
			}
			bgSkuIds.add(bgSku.getBgSkuId());
		}

		College college = null;
		Sku sku = null;
		// 可以修改为批量更新，将学校维度和是否RDC、LDC拿出来
		for (CollegeDto collegeDto : collegeDtos) {
			college = collegeDto.getCollege();
			if (college == null) {
				// return -1;
				continue;
			}

			Goods goods = goodsDao.getByBgGoodsIdAndCollegeId(bgGoodsId, college.getCollegeId());
			// 做了双层验证,可以屏蔽商品类型
			if (goods == null) {
				// return -1;
				continue;
			}
			boolean checkResult = true;
			// 更新sku状态
			for (Long bgSkuId : bgSkuIds) {
				sku = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSkuId, college.getCollegeId(),
						goods.getGoodsId(), isLdc);
				if (sku == null) {
					// return -1;
					checkResult = false;
					continue;
				}
				if (sku.getWapPrice() <= 0 || sku.getAppPrice() <= 0) {
					LogConstant.mallLog.info("学校{}下的商品商品{}({})的wap价格和app价格小于等于0.禁止更新商品的售卖状态~",
							college.getCollegeName(), goods.getBgGoodsName(),
							sku.getSkuId());
					resultinfo.append("学校【" + college.getCollegeName() + "】下的商品【"
							+ goods.getBgGoodsName()
							+ "】的wap价格或app价格小于等于0.禁止更新商品的售卖状态~\r\n");
					checkResult = false;
					continue;
				}
				int result = skuDao.updateStock(sku.getSkuId(), -1, status);
				if (result < 0) {
					// return -1;
					checkResult = false;
					continue;
				}
			}

			if (!checkResult) {
				continue;
			}
			//goods状态优先级：在售->售罄->代售
			List<Sku> skuList = skuDao.getListByGoodsIdSkuStatus(goods.getGoodsId(), Constants.GOOD_STATUS_SALING);
			if(skuList != null && skuList.size() > 0){
				goodsDao.updateStatus(bgGoodsId, college.getCollegeId(), Constants.GOOD_STATUS_SALING);
			}else{
				skuList = skuDao.getListByGoodsIdSkuStatus(goods.getGoodsId(), Constants.GOOD_STATUS_STOPSALE);
				if(skuList != null && skuList.size() > 0){
					goodsDao.updateStatus(bgGoodsId, college.getCollegeId(), Constants.GOOD_STATUS_STOPSALE);
				}else{
					goodsDao.updateStatus(bgGoodsId, college.getCollegeId(), Constants.GOOD_STATUS_ONSALE);
				}
			}
			/*int r2 = goodsDao.updateStatus(bgGoodsId, college.getCollegeId(), status);
			if (r2 < 0) {
				// return -1;
				continue;
			}*/
			resultinfo.append("学校【" + college.getCollegeName() + "】更新商品【" + goods.getBgGoodsName()
					+ "】成功\r\n");
		}
		if (resultinfo.length() == 0) {
			resultinfo.append("更新数据0条");
		}
		return resultinfo.toString();
	}

	@Override
	public boolean updateWeightStockStatus(long goodsId, long weight, long stock, int status, int isLdc) {
		int r1 = skuDao.updateStockByGoodsId(goodsId, stock, status,isLdc);
		if (r1 < 0) {
			return false;
		}
		//goods状态优先级：在售->售罄->代售
		List<Sku> skuList = skuDao.getListByGoodsIdSkuStatus(goodsId, Constants.GOOD_STATUS_SALING);
		if(skuList != null && skuList.size() > 0){
			goodsDao.updateWeightStatus(goodsId, weight, Constants.GOOD_STATUS_SALING);
		}else{
			skuList = skuDao.getListByGoodsIdSkuStatus(goodsId, Constants.GOOD_STATUS_STOPSALE);
			if(skuList != null && skuList.size() > 0){
				goodsDao.updateWeightStatus(goodsId, weight, Constants.GOOD_STATUS_STOPSALE);
			}else{
				goodsDao.updateWeightStatus(goodsId, weight, Constants.GOOD_STATUS_ONSALE);
			}
		}
		return true;
	}

	@Override
	public int updateMaxNum(long bgGoodsId, long collegeId, long maxNum) {
		return goodsDao.updateMaxNum(bgGoodsId, collegeId, maxNum);
	}

	@Override
	public int deleteUnusedGoods() {
		// TODO Auto-generated method stub
		List<Long> goodsIds = goodsDao.getNeedDelGoods();
		return goodsDao.deleteGoodsByIds(goodsIds);
	}

}
