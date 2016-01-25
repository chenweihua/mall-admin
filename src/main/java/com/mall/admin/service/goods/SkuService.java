package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.dto.CollegeDto;

public interface SkuService {

	public long addOrUpdateSku(BgSku bgSku, long collegeId, long goodsId, int distributeType, int skuType,
			boolean isOld);

	public List<Sku> get4copy2activity(Long collegeId);
	/**
	 * 
	 * @param bgSkuId
	 * @param collegeId
	 * @param goodsId
	 * @param isLdc
	 *                ,0RDC,1LDC
	 * @return
	 */
	public Sku getBybgSkuIdAndCollegeIdAndGoodsId(long bgSkuId, long collegeId, long goodsId, int isLdc);

	/**
	 * 单品设置价格
	 * 
	 * @param collegeDto
	 * @param bgGoodsId
	 * @return
	 */
	public boolean addRdcPrice2CollegeDto(CollegeDto collegeDto, long bgGoodsId, int isLdc);

	public boolean batchModifyRdcPriceMaxNum(List<CollegeDto> collegeDtos, long bgGoodsId, long originPrice,
			long wapPrice, long appPrice, long maxNum, int isLdc);

	public boolean modifyPriceMaxnum(long skuId, long originPrice, long wapPrice, long appPrice, long maxNum);

	public boolean modifyMaxnum(long skuId, long maxNum);

	/**
	 * 根据goodsId查询对应的sku信息
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<Sku> getSkuListByGoodsId(long goodsId);

	public List<Long> getCollegeIdsByBgSkuId(long bgSkuId, int isLdc);

	/**
	 * 修改全国仓下的价格和限购数量
	 * 
	 * @param bgGoodsId
	 * @param bgSkuId
	 * @param originPrice
	 * @param wapPrice
	 * @param appPrice
	 * @param maxNum
	 * @return
	 */
	public int batchModifyPrice(long bgGoodsId, long bgSkuId, long originPrice, long wapPrice, long appPrice,
			long maxNum);
}
