package com.mall.admin.model.mybatis.goods;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.BgGoodsImageDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.BgGoodsImage;

@Repository
public class BgGoodsImageDaoImpl extends BaseMallDaoImpl implements BgGoodsImageDao {

	/**
	 * 插入商品图片
	 * @return
	 */
	@Override
	public long insertBgImage(List<BgGoodsImage> bgGoodsImageList){
		int result = this.getSqlSession().insert("BgGoodsImage.insertBgGoodsImage", bgGoodsImageList);
		if (result < 0) {
			return -1L;
		}
		return result;
		
	}
	
	/**
	 * 根据后台商品ID删除商品图片，逻辑删除
	 * @param bgGoodsId
	 * @return
	 */
	@Override
	public int deleteBgImageByBgGoodsId(Long bgGoodsId){
		return this.getSqlSession().update("BgGoodsImage.deleteByBgGoodsId", bgGoodsId);
	}
	
	/**
	 * 通过后台商品ID查询商品图片
	 * @return
	 */
	@Override
	public List<BgGoodsImage> getBgGoodsImageListByBgGoodsId(Long bgGoodsId){
		return this.getSqlSession().selectList("BgGoodsImage.selectByBgGoodsId", bgGoodsId);
	}

}
