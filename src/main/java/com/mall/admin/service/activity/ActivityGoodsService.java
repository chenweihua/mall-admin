package com.mall.admin.service.activity;

public interface ActivityGoodsService {

	/**
	 * 更新某个活动下的某个商品删除状态
	 * 
	 * @param bgGoodsId
	 *                商品id
	 * @param collegeId
	 *                学校id
	 * @param isDel
	 *                删除状态 0：可用 1：不可用
	 * @param distributeType
	 *                商品类型 0：rdc；1：ldc
	 * @return
	 */
	public int updateStatusByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId, int isDel, int distributeType);

	/**
	 * 所有活动下，某学校的商品删除状态
	 * 
	 * @param collegeId
	 *                学校di
	 * @param distributeType
	 *                商品类型 0：rdc；1：ldc
	 * @return
	 */
	public int deletActivityGoodsByCollegeId(long collegeId, int distributeType);

}
