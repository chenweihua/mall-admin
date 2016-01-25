package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.constant.Constants;
import com.mall.admin.model.dao.goods.GoodsDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.Goods;

@Repository
public class GoodsDaoImpl extends BaseMallDaoImpl implements GoodsDao {

	@Override
	public Goods getById(long goodsId) {
		return this.getSqlSession().selectOne("Goods.selectByPrimaryKey", goodsId);
	}

	@Override
	public List<Long> getBgGoodsIdByCollegeId(long collegeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Goods getByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId) {
		Map<String, Object> map = new HashMap<>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("collegeId", collegeId);
		return this.getSqlSession().selectOne("Goods.getByBgGoodsIdAndCollegeId", map);
	}

	@Override
	public List<Long> getGoodsIdsByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("Goods.selectGoodsByBgGoodsId", bgGoodsId);
	}

	@Override
	public int deleteGoodsAndSkuByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().update("Goods.deleteGoodsAndSkuByBgGoodsId", bgGoodsId);
	}

	@Override
	public int deleteByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgGoodsId", bgGoodsId);
		map.put("collegeId", collegeId);
		return this.getSqlSession().update("Goods.deleteByPrimaryKey", map);
	}

	@Override
	public int deleteByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().update("Goods.deleteByBgGoodsId", bgGoodsId);
	}

	@Override
	public int updateByObject(Goods goods) {
		return this.getSqlSession().update("Goods.updateByPrimaryKey", goods);
	}

	@Override
	public int updateByBgGoods(BgGoods bgGoods) {
		return this.getSqlSession().update("Goods.updateByBgGoods", bgGoods);
	}

	@Override
	public int updateStatus(long bgGoodsId, long collegeId, int status) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("collegeId", collegeId);
		param.put("status", status);
		return this.getSqlSession().update("Goods.updateStatus", param);
	}

	@Override
	public int updateWeightStatus(long goodsId, long weight, int status) {
		Map<String, Object> param = new HashMap<>();
		param.put("goodsId", goodsId);
		param.put("weight", weight);
		param.put("status", status);
		return this.getSqlSession().update("Goods.updateWeightStatus", param);
	}

	@Override
	public int updateMaxNum(long goodsId, long maxNum) {
		Map<String, Object> param = new HashMap<>();
		param.put("goodsId", goodsId);
		param.put("maxNum", maxNum);
		return this.getSqlSession().update("Goods.updateMaxNumByGoodsId", param);
	}

	@Override
	public int batchUpdateMaxNum(long bgGoodsId, long maxNum) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("maxNum", maxNum);
		return this.getSqlSession().update("Goods.updateMaxNumByBgGoodsId", param);
	}

	@Override
	public int updateMaxNum(long bgGoodsId, long collegeId, long maxNum) {
		Map<String, Object> param = new HashMap<>();
		param.put("collegeId", collegeId);
		param.put("bgGoodsId", bgGoodsId);
		param.put("maxNum", maxNum);
		return this.getSqlSession().update("Goods.updateMaxNumByUnionKey", param);
	}

	@Override
	public long insert(Goods goods) {
		int result = this.getSqlSession().insert("Goods.insert", goods);
		if (result < 0) {
			return -1L;
		}
		return goods.getBgGoodsId();
	}

	@Override
	public long insertOrUpdate(Goods goods, boolean isOld) {
		Goods temp = getByBgGoodsIdAndCollegeId(goods.getBgGoodsId(), goods.getCollegeId());
		if (temp != null) {
			if (!isOld) {
				temp.setStatus(Constants.GOOD_STATUS_ONSALE);
			}
			temp.setIsDel(0);
			int i = updateByObject(temp);
			if (i > 0) {
				return temp.getGoodsId();
			}
			return -1L;
		} else {
			int result = this.getSqlSession().insert("Goods.insert", goods);
			if (result < 0) {
				return -1L;
			}
			return goods.getGoodsId();
		}
	}

	@Override
	public Map<String, Object> selectSingleByPage(int start, int numPerPage, long categoryId, long collegeId,
			String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> getCollegesByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("Goods.selectCollegesByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getRdcCollegesByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("Goods.selectRdcCollegesByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getLdcCollegesByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().selectList("Goods.selectLdcCollegesByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<Long> getCollegeIdListByBgGoodsIdAndDistributeType(Long bgGoodsId, Integer distributeType) {
		Map<String, Object> params = new HashMap<>();
		params.put("bgGoodsId", bgGoodsId);
		params.put("distributeType", distributeType);
		return this.getSqlSession().selectList("Goods.selectCollegeIdListByBgGoodsIdAndDistributeType", params);
	}

	@Override
	public List<Long> getNeedDelGoods() {
		return this.getSqlSession().selectList("Goods.selectNeedDelGoods");
	}

	@Override
	public int deleteGoodsByIds(List<Long> goodsIds) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("goodsIds", goodsIds);
		return this.getSqlSession().update("Goods.deleteGoodsByIds", map);
	}

}
