package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.BgGoodsImage;

public interface BgGoodsImageDao {

	/**
	 * 插入商品图片
	 * @return
	 */
	public long insertBgImage(List<BgGoodsImage> bgGoodsImageList);
	/**
	 * 根据后台商品ID删除商品图片
	 * @param bgGoodsId
	 * @return
	 */
	public int deleteBgImageByBgGoodsId(Long bgGoodsId);
	/**
	 * 通过后台商品ID查询商品图片
	 * @return
	 */
	public List<BgGoodsImage> getBgGoodsImageListByBgGoodsId(Long bgGoodsId);
	
}
