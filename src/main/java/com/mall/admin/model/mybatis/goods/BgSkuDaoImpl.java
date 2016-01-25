package com.mall.admin.model.mybatis.goods;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.BgSku;

@Repository
public class BgSkuDaoImpl extends BaseMallDaoImpl implements BgSkuDao {

	@Override
	public BgSku getById(long bgSkuId) {
		return this.getSqlSession().selectOne("BgSku.selectByPrimaryKey",
				bgSkuId);
	}
	

	@Override
	public boolean isExist(long bgGoodsId) {
		if(getByBgGoodsId(bgGoodsId) == null){
			return false;
		}
		return true;
	}


	@Override
	public BgSku getByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectOne("BgSku.getByBgGoodsId", bgGoodsId);
	}

	@Override
	public int deleteById(long id) {
		return 0;
	}

	@Override
	public int updateByObject(BgSku bgSku) {
		return this.getSqlSession().update("BgSku.updateByPrimaryKey", bgSku);
	}
	
	@Override
	public int updateByObjectBgGoodsId(BgSku bgSku) {
		return this.getSqlSession().update("BgSku.updateByBgGoodsId", bgSku);
	}
	
	@Override
	public long insert(BgSku bgSku) {
		int result = getSqlSession().insert("BgSku.insert", bgSku);
		if (result < 0) {
			return -1L;
		}
		return bgSku.getBgSkuId();
	}
}
