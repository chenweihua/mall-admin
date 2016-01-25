package com.mall.admin.service.thirdShop;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.GoodsProperty;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.SkuProperty;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;

public interface BgGoods4ThirdService {
	/**
	 * 最新版商城后台，添加bgGoods,级联添加库存商品/库存
	 * @param bgGoods
	 * @param param
	 * @return
	 */
	public Map<String, Object> addBgGoods(BgGoods bgGoods, Map<String, Map<String,Object>> param,List<String> valueList,List<BgGoodsImage> imageList);
	
	public BgGoods getBgGoodsById(long bgGoodsId);
	
	/**
	 * 通过后台商品ID查询商品图片
	 * @return
	 */
	public List<BgGoodsImage> getBgGoodsImageListByBgGoodsId(Long bgGoodsId);

	/**
	 * 分页查询bgGoods
	 * @param param
	 * @param paginationInfo
	 * @return
	 */
	public List<BgGoods4Manager> getBgGoodsDtoByPage(Map<String, Object> param,
			PaginationInfo paginationInfo);

	public int updateUpdateTimeByBgGoodsId(long bgGoodsId);
	/**
	 * 获取商品属性
	 * @param bgGoodsId
	 * @return bgSkuId -> Map<pvId,SkuProperty>
	 */
	public Map<Long, Map<Long,SkuProperty>> getBgGoodsProperty(Long bgGoodsId);
	
	public Map<String, String> producePropertyImageMap(List<PropertyName> propertyNameList,List<GoodsProperty> selectedProperty);
	
	public Map<String, BgSku> producePropertyBgSkuMap(List<PropertyName> propertyNameList,Map<Long, Map<Long,SkuProperty>> bgSkuPropertyValues,Map<String, String> propertyImageMap);

	public List<BgSku> produceBgSkuList(Map<Long, Map<Long,SkuProperty>> bgSkuPropertyValues);
	
	public int updateBgGoodsStatus(Long bgGoodsId,int goodsStatus);
}
