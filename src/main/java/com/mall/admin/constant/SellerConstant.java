package com.mall.admin.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.seller.SellerService;
import com.mall.admin.vo.seller.Seller;



public class SellerConstant {
	private static Map<Long, Seller> sellerMap = new HashMap<Long, Seller>();
	private static Map<String, Seller> sellerNameMap = new HashMap<String, Seller>();

	@Autowired
	private SellerService sellerService;

	private SellerConstant() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.debug("init seller");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Long, Seller> tempSellerMap = new HashMap<Long, Seller>();
		Map<String, Seller> tempNameSellerMap = new HashMap<String, Seller>();
		List<Seller> sellerList = sellerService.getAllSeller();
		if (!sellerList.isEmpty()) {
			for (Seller seller : sellerList) {
				tempSellerMap.put(seller.getSellerId(), seller);
				tempNameSellerMap.put(seller.getSellerName(), seller);
			}
		}
		sellerMap = tempSellerMap;
		sellerNameMap = tempNameSellerMap;
		LogConstant.mallLog
				.info("refresh "
						+ (sellerList.isEmpty() ? 0 : sellerList.size())
						+ " seller");
	}

	/**
	 * 根据id获得整个Seller对象
	 * 
	 * @param key
	 * @return
	 */
	public static Seller getSellerById(Long id) {
		return sellerMap.get(id);
	}

	/**
	 * 根据name获得整个Seller对象
	 * 
	 * @param key
	 * @return
	 */
	public static Seller getSellerByName(String name) {
		return sellerNameMap.get(name);
	}

	public static Map<Long, Seller> getSellerMap() {
		return sellerMap;
	}

	public static Map<String, Seller> getSellerNameMap() {
		return sellerNameMap;
	}
}
