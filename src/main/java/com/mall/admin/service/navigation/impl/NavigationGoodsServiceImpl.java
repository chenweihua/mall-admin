package com.mall.admin.service.navigation.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.navigation.NavigationGoodsDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.navigation.NavigationGoodsService;
import com.mall.admin.service.taobao.api.ApiException;
import com.mall.admin.service.taobao.api.DefaultTaobaoClient;
import com.mall.admin.service.taobao.api.TaobaoClient;
import com.mall.admin.service.taobao.api.domain.NTbkItem;
import com.mall.admin.service.taobao.api.request.TbkItemInfoGetRequest;
import com.mall.admin.service.taobao.api.response.TbkItemInfoGetResponse;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.vo.ConfigureBean;
import com.mall.admin.vo.navigation.NavigationGoods;

@Service
public class NavigationGoodsServiceImpl implements NavigationGoodsService {

	@Autowired
	NavigationGoodsDao navGoodsDao;

	@Autowired
	ConfigureBean configure;

	@Override
	public List<NavigationGoods> queryNavGoods(Integer goodsStatus, String navGoodsName, long navMenuId,
			Integer isOpen, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return navGoodsDao.queryNavGoods(goodsStatus, navGoodsName, navMenuId, isOpen, paginationInfo);
	}

	@Override
	public int insert(NavigationGoods navGoods) {
		// TODO Auto-generated method stub
		return navGoodsDao.insert(navGoods);
	}

	@Override
	public int update(NavigationGoods navGoods) {
		// TODO Auto-generated method stub
		return navGoodsDao.update(navGoods);
	}

	@Override
	public int deleteNavGoods(long navGoodsId) {
		// TODO Auto-generated method stub
		return navGoodsDao.deleteNavGoods(navGoodsId);
	}

	@Override
	public int setOpenStatus(long navGoodsId, int isOpen) {
		// TODO Auto-generated method stub
		return navGoodsDao.setOpenStatus(navGoodsId, isOpen);
	}

	@Override
	public int putNavGoodsToSalePool(long navGoodsId) {
		// TODO Auto-generated method stub
		return navGoodsDao.putNavGoodsToSalePool(navGoodsId);
	}

	/**
	 * 将选品池中的商品添加到选品池中
	 * 
	 * @param navGoodsId
	 * @return
	 */
	@Override
	public int putNavGoodsToSelectPool(long navGoodsId) {

		return navGoodsDao.putNavGoodsToSelectPool(navGoodsId);

	}

	@Override
	public boolean initNavGoods(NavigationGoods navGoods) {
		String url = configure.getTaobaoGoodsURL();
		String appkey = configure.getTaobaoGoodsAppkey();
		String secret = configure.getTaobaoGoodsSecret();

		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		TbkItemInfoGetRequest request = new TbkItemInfoGetRequest();
		request.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,click_url");
		request.setPlatform(2L);
		request.setNumIids(navGoods.getGoodsId());
		TbkItemInfoGetResponse response;
		try {
			response = client.execute(request);
			List<NTbkItem> list = response.getResults();
			if (list == null || list.size() != 1) {
				return false;
			}
			NTbkItem item = list.get(0);
			navGoods.setGoodsName(item.getTitle());
			// navGoods.setClickUrl(item.getClickUrl());
			navGoods.setReservePrice(MoneyUtils.yuan2Fen(Double.parseDouble(item.getReservePrice())));
			navGoods.setZkFinalPrice(MoneyUtils.yuan2Fen(Double.parseDouble(item.getZkFinalPrice())));
			navGoods.setOriginPlace(item.getProvcity());
			navGoods.setImageUrl(item.getPictUrl());
			return true;
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<NavigationGoods> getGoodsByMenuId(long menuId) {
		// TODO Auto-generated method stub
		return navGoodsDao.getGoodsByMenuId(menuId);
	}

	@Override
	public int deleteGoodsByMenuId(long menuId) {
		return navGoodsDao.deleteGoodsByMenuId(menuId);
	}

}
