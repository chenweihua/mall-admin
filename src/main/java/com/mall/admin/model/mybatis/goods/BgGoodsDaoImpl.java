package com.mall.admin.model.mybatis.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;

/**
 * bgGoods实现
 *
 * @date 2015年7月14日 下午3:42:49
 * @author zhangshuai
 */

@Repository
public class BgGoodsDaoImpl extends BaseMallDaoImpl implements BgGoodsDao {

	@Override
	public BgGoods getById(long bgGoodsId) {
		return this.getSqlSession().selectOne("BgGoods.selectByPrimaryKey", bgGoodsId);
	}

	@Override
	public BgGoods getSingByBgSkuId(long bgSkuId) {
		return this.getSqlSession().selectOne("BgGoods.selectSingByBgSkuId", bgSkuId);
	}

	@Override
	public boolean isExist(long bgGoodsId) {
		if (getById(bgGoodsId) == null) {
			return false;
		}
		return true;
	}

	@Override
	public int deleteById(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByObject(BgGoods bgGoods) {
		return this.getSqlSession().update("BgGoods.updateByPrimaryKey", bgGoods);
	}

	@Override
	public int updateStatusByBgGoodsId(Long bgGoodsId, int goodsStatus) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsId", bgGoodsId);
		param.put("goodsStatus", goodsStatus);
		return this.getSqlSession().update("BgGoods.updateStatusByBgGoodsId", param);
	}

	@Override
	public long insert(BgGoods bgGoods) {
		int result = this.getSqlSession().insert("BgGoods.insert", bgGoods);
		if (result < 0) {
			return -1L;
		}
		return bgGoods.getBgGoodsId();
	}

	@Override
	public Map<String, Object> selectSingleByPage(int start, int numPerPage, long categoryId, long collegeId,
			String name) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		params.put("collegeId", collegeId);
		params.put("name", name);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		@SuppressWarnings("unchecked")
		PaginationList<BgGoods> bgGoodsList = selectPaginationList("BgGoods.selectSingleByPage", params,
				paginationInfo);
		result.put("bgGoodsList", bgGoodsList);
		result.put("total", paginationInfo.getTotalRecord());
		return result;
	}

	@Override
	public Map<String, Object> selectSingleByPage(int start, int numPerPage, String name) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsName", name);
		param.put("wmsGoodsGbm", name);
		Map<String, Object> result = new HashMap<>();
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		@SuppressWarnings("unchecked")
		PaginationList<BgGoods> bgGoodsList = selectPaginationList("BgGoods.selectSingleByPage", param,
				paginationInfo);
		result.put("bgGoodsList", bgGoodsList);
		result.put("total", paginationInfo.getTotalRecord());
		return result;
	}

	@Override
	public List<BgGoods> selectSingleByPage(String searchStr, PaginationInfo paginationInfo) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsName", searchStr);
		param.put("wmsGoodsGbm", searchStr);
		return selectPaginationList("BgGoods.selectSingleByPage", param, paginationInfo);
	}

	@Override
	public List<BgGoods4Manager> getGoodsInfoByPage(Map<String, Object> map, PaginationInfo paginationInfo) {
		return this.selectPaginationList("BgGoods.getStorageGoodsInfoByPage", map, paginationInfo);
	}
	@Override
	public List<BgGoods4Manager> getGoods4VmStorageInfoByPage(
			Map<String, Object> map, PaginationInfo paginationInfo) {
		return this.selectPaginationList("BgGoods.getGoodsInfo4VmStorageByPage", map, paginationInfo);
	}

	@Override
	public List<BgGoods4Manager> getBgGoodsInfoByPage(Map<String, Object> map,
			PaginationInfo paginationInfo) {
		return this.selectPaginationList("BgGoods.getBgGoodsInfoByPage", map, paginationInfo);
	}

	@Override
	public List<BgGoods> getBgGoodsList(String searchStr, PaginationInfo paginationInfo) {
		Map<String, Object> param = new HashMap<>();
		param.put("bgGoodsName1", searchStr);
		param.put("wmsGoodsGbm1", searchStr);
		param.put("bgGoodsName2", searchStr);
		param.put("wmsGoodsGbm2", searchStr);
		return this.selectPaginationList("BgGoods.selectByCodeOrNameByPage", param, paginationInfo);
	}

	@Override
	public List<BgGoods> getSingleBgGoodsByWmsGoodsId(long wmsGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BgGoods.selectSingleGoodsByWmsGoodsId", wmsGoodsId);
	}

	@Override
	public List<BgGoods> getMultBgGoodsByWmsGoodsId(long wmsGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BgGoods.selectMultGoodsByWmsGoodsId", wmsGoodsId);
	}

	@Override
	public int updateUpdateTimeByBgGoodsId(long bgGoodsId) {
		return this.getSqlSession().update("BgGoods.updateUpdateTimeByBgGoodsId", bgGoodsId);
	}

	@Override
	public List<BgGoods4Manager> getBgGoodsDtoByPage(Map<String, Object> map,
			PaginationInfo paginationInfo) {
		return this.selectPaginationList("BgGoods.selectBgGoodsDtoListByPage", map, paginationInfo);
	}
}
