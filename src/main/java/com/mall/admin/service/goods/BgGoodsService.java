package com.mall.admin.service.goods;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsImage;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.user.User;

public interface BgGoodsService {
	//商城上单
	public Map<String, Object> addGoods(BgGoods bgGoods, Map<String, Long> skuParam, List<BgGoodsImage> imageList);

	public Map<String, Object> addMultiGoods(BgGoods bgGoods, Map<String, String[]> skuProperty, String[] pvIds, List<BgGoodsImage> imageList);

	/**
	 * code,0成功,1失败，
	 * 
	 * msg:提示信息 返回结果data中包括：查询到的总页数total，当前页记录集合bgGoodsDtos
	 * 
	 * @param start
	 *                分页从第几条开始
	 * @param numPerPage
	 *                每页个数
	 * @param categoryId
	 *                类目
	 * @param collegeId
	 *                学校
	 * @param name
	 *                查询字段
	 * @return
	 */
	public Map<String, Object> selectSingleGoodsByPage(int start, int numPerPage, long categoryId, long collegeId,
			String name);

	/**
	 * map中放有：total总条数，bgGoodsDtos
	 * 
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	public Map<String, Object> selectSingleGoodsByPage(int start, int numPerPage, String searchStr);

	public List<BgGoods> selectSingleByPage(String searchStr, PaginationInfo paginationInfo);

	public BgGoodsDto getBgGoodsDtoById(long bgGoodsId);

	public BgGoodsDto getBgGoodsDtoByBgSkuId(long bgSkuId);

	public BgGoods getBgGoodsById(long bgGoodsId);
	
	/**
	 * 通过后台商品ID查询商品图片
	 * @return
	 */
	public List<BgGoodsImage> getBgGoodsImageListByBgGoodsId(Long bgGoodsId);

	/**
	 * 获取多个wms_goods_ids聚合的仓库ids
	 * 
	 * @param wms_goods_ids
	 * @return
	 */
	public List<Long> getBaseStorageIds(Long[] wms_goods_ids);

	/**
	 * 获取多个wms_goods_ids聚合的仓库ids
	 * 
	 * @param wms_goods_ids
	 * @return
	 */
	public Map<String, Object> setRegion(long bgGoodsId, ZtreeBean ztreeBean, User user);

	/**
	 * 设置价格
	 * 
	 * @param bgGoodsId
	 * @param id
	 * @param flag
	 *                1、ldc城市;2、ldc仓;3、rdc仓
	 * @return
	 */
	public Map<String, Object> setRegionPrice(long bgGoodsId, long regionId, int flag, int originPrice,
			int wapPrice, int appPrice, int maxNum);

	public List<BgGoods4Manager> getBgGoodsInfoByPage(int storageType, Map<String, Object> param,
			PaginationInfo paginationInfo);

	/**
	 * 根据编码或名称，分页查找
	 * 
	 * @param searchStr
	 * @param paginationInfo
	 * @return
	 */
	public List<BgGoods> getBgGoodsList(String searchStr, PaginationInfo paginationInfo);

	/**
	 * 新建学校，复制所在仓的全部商品
	 * 
	 * @param college
	 * @return
	 */
	public boolean batchCopy(College college);

	/**
	 * 依据是否LDC，批量添加，
	 * 
	 * @param college
	 * @param isLdc
	 *                0：rdc；1：ldc
	 * @return
	 */
	public boolean batchCopy(College college, int isLdc);

	/**
	 * 批量删除,所有数据为-1时，表示该字段不可用
	 * 
	 * @param bgGoodsId
	 * 
	 * @param college
	 * @param isLdc
	 * @return
	 */
	public boolean batchDelete(long bgGoodsId,long bgSkuid, long collegeId, int isLdc);
	
	public boolean batchDelete(long collegeId,int isLdc);

	/**
	 * 获得单品和组合品的BgGoods信息
	 * 
	 * @param wmsGoodsId
	 * @return
	 */
	public List<BgGoods> getSingleBgGoodsListByWmsGoodsId(long wmsGoodsId);

	/**
	 * 获得聚合商品的BgGoods信息
	 * 
	 * @param wmsGoodsId
	 * @return
	 */
	public List<BgGoods> getMultBgGoodsListByWmsGoodsId(long wmsGoodsId);
	
	public int updateUpdateTimeByBgGoodsId(long bgGoodsId);

}
