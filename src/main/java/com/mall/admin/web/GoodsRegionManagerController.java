package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.goods.SkuPropertyService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.User;

/**
 * 范围
 *@date 2015年8月16日 下午6:32:40  
 *@author zhangshuai
 */
@Controller
@RequestMapping("/goods/region/")
public class GoodsRegionManagerController extends BaseController {

	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ZtreeUtil ztreeutil;
	@Autowired
	StorageService storageService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	BgSkuGbmService bgSkuGbmService;
	@Autowired
	SkuService skuService;
	@Autowired
	SkuPropertyService skuPropertyService;
	@Autowired
	private BgSkuService bgSkuService;

	@ResponseBody
	@RequestMapping("/get/allSingleGoods")
	public Object getSingleGoods(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) throws SQLException, IOException {
		LogConstant.mallLog.info("/goods/extend/allSingleGoods---->获取全部的单品...");

		Map<String, Object> result = bgGoodsService.selectSingleGoodsByPage(start, numPerPage, 0, 0, searchStr);

		if (result.get("code").equals("0")) {
			return result.remove("data");
		}
		return "goods/manager";
	}

	@ResponseBody
	@RequestMapping("/getRegion/byWmsGoodsIds")
	public Object getRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		boolean isAllChecked = false;
		if (_.isEmpty(bgGoodsIdStr) || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			isAllChecked = true;
		}
		String wmsGoodsIdsStr = request.getParameter("wmsGoodsIds");
		if (wmsGoodsIdsStr == null) {
			return buildErrJson("请先添加商品！");
		}
		wmsGoodsIdsStr = wmsGoodsIdsStr.substring(1);
		String[] wmsGoodsIds = wmsGoodsIdsStr.split(":");
		Long[] wmsIds = new Long[wmsGoodsIds.length];
		for (int i = 0; i < wmsGoodsIds.length; i++) {
			wmsIds[i] = _.toLong(wmsGoodsIds[i]);
		}
		ZtreeBean ztreeBean = getSingleZtreeBean(wmsIds, user,isAllChecked,bgGoodsId);
		if (ztreeBean == null) {
			return buildErrJson("您添加的商品，不在相同的仓，请联系采购添加商品！");
		}
		// 设置已被选中的节点
		/*if (!isAllChecked) {
			long bgGoodsId = _.toLong(bgGoodsIdStr);
			List<Long> rdcCollegeIds = goodsService.getRdcCollegeIds(bgGoodsId);
			List<Long> ldcCollegeIds = goodsService.getLdcCollegeIds(bgGoodsId);
			ztreeutil.setZtreeStatusByCollege(ztreeBean, rdcCollegeIds, ldcCollegeIds);
		}*/
		return buildSuccJson(ztreeBean);
	}

	/**
	 * skuIds是已：分割的id
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getRegion/multi/byBgSkuIds")
	public Object getRegionBySkuIds(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgSkuIdsStr = request.getParameter("bgSkuIds");
		if (bgSkuIdsStr == null) {
			return buildJson(-1,"请先添加商品！");
		}
		bgSkuIdsStr = bgSkuIdsStr.substring(1);
		String[] bgSkuIdStrs = bgSkuIdsStr.split(":");
		Long[] bgSkuIds = new Long[bgSkuIdStrs.length];
		for (int i = 0; i < bgSkuIdStrs.length; i++) {
			bgSkuIds[i] = _.toLong(bgSkuIdStrs[i]);
		}
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		long bgGoodsId = NumberUtils.toLong(bgGoodsIdStr,-1L);
		ZtreeBean ztreeBean = getMultiZtreeBean(bgGoodsId,bgSkuIds, user);
		if(ztreeBean == null){
			return buildJson(-1,"选中的单品无法聚合出可选范围");
		}
		return buildSuccJson(ztreeBean);
	}

	/**
	 * 仅仅针对单品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getRegion/byBgGoogdsId")
	public Object getRegionByBgGoodsId(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("查看信息失败");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		BgGoods bgGoods = bgGoodsService.getBgGoodsById(bgGoodsId);
		if (bgGoods == null) {
			return buildErrJson("查看信息失败");
		}
		ZtreeBean ztreeBean = null;
		List<Long> wmsIdList = new ArrayList<>();
		if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
			wmsIdList = bgSkuGbmService.getWmsGoodsIdByBgGoodsId(bgGoodsId);
			ztreeBean = getSingleZtreeBean(wmsIdList.toArray(new Long[wmsIdList.size()]), user,false,bgGoodsId);
			if (ztreeBean == null) {
				return buildErrJson("对不起，范围空，请到库存管理为商品添加范围！");
			}
		} else if (bgGoods.getGoodsType() == 3) {
			List<Long> bgSkuIds = skuPropertyService.getBgSkuByBgGoodsId(bgGoodsId);
			ztreeBean = getMultiZtreeBean(bgGoodsId,bgSkuIds.toArray(new Long[bgSkuIds.size()]), user);
			if(ztreeBean == null){
				return buildJson(-1,"您好，您选中的单品无法聚合出可选范围");
			}
		}
		return buildSuccJson(ztreeBean);
	}

	@ResponseBody
	@RequestMapping("/setRegion/byBgGoodsId")
	public Object setRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		String treeInfo = request.getParameter("treeInfo");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			LogConstant.mallLog.info("商品ID错误，id是：" + bgGoodsIdStr);
			return buildJson(1, "商品ID错误，请联系管理员~");
		}
		List<ZtreeBean> bean = gson.fromJson(treeInfo, new TypeToken<List<ZtreeBean>>() {
		}.getType());
		if (bean == null || bean.size() == 0) {
			return buildJson(1, "获取范围信息为空~");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		ZtreeBean ztreeBean = bean.get(0);
		Map<String, Object> result = bgGoodsService.setRegion(bgGoodsId, ztreeBean, user);
		return result;
	}
	
	private ZtreeBean getSingleZtreeBean(Long[] wmsIds,User user,boolean isAllChecked,long bgGoodsId){
		// 取范围
		List<Long> storageIds = bgGoodsService.getBaseStorageIds(wmsIds);
		if(storageIds == null || storageIds.size() == 0){
			return null;
		}
		List<Storage> allStorage = user.getAllStorageList();
		String ids = "";
		for (Long id : storageIds) {
			ids += "," + id;
		}
		if (ids.length() > 0) {
			ids = ids.substring(1);
		}
		List<Storage> filterStorage = storageService.getAllStorageByIds(ids);
		filterStorage.retainAll(allStorage);
		if (filterStorage == null || filterStorage.size() < 1) {
			return null;
		}
		return ztreeutil.getCollegeZtree(filterStorage,isAllChecked,bgGoodsId);
	}

	
	private ZtreeBean getMultiZtreeBean(Long bgGoodsId,Long[] bgSkuIds,User user){
		List<College> collegeList = CollegeConstant.getCollegeList();
		// 根据sku获得售卖的rdc学校
		List<Long> skuRdcCollege = skuService.getCollegeIdsByBgSkuId(
				bgSkuIds[0],Sku.RDC_DISTRIBUTE_TYPE);
		for (int i = 1;i<bgSkuIds.length;i++) {
			List<Long> skuCollegeIdList = skuService.getCollegeIdsByBgSkuId(
					bgSkuIds[i], Sku.RDC_DISTRIBUTE_TYPE);
			skuRdcCollege.retainAll(skuCollegeIdList);
		}
		// 根据用户获得可管理的rdc学校
		List<Long> userRdcCollege = new ArrayList<Long>();
		List<Storage> userRdcStorage = user.getRdcStorageList();
		for (Storage storage : userRdcStorage) {
			for (College college : collegeList) {
				if (college.getIsDel() == 0 && college.getRdcStorageId() == storage.getStorageId()) {
					userRdcCollege.add(college.getCollegeId());
				}
			}
		}
		// 聚合rdc学校
		skuRdcCollege.retainAll(userRdcCollege);
		// 根据sku获得售卖的ldc学校
		List<Long> skuLdcCollege = skuService.getCollegeIdsByBgSkuId(
				bgSkuIds[0], Sku.LDC_DISTRIBUTE_TYPE);
		for (int i = 1;i<bgSkuIds.length;i++) {
			List<Long> skuCollegeIdList = skuService.getCollegeIdsByBgSkuId(
					bgSkuIds[i], Sku.LDC_DISTRIBUTE_TYPE);
			skuLdcCollege.retainAll(skuCollegeIdList);
		}
		// 根据用户获得可管理的ldc学校
		List<Long> userLdcCollege = new ArrayList<Long>();
		List<Storage> userLdcStorage = user.getLdcStorageList();
		for (Storage storage : userLdcStorage) {
			for (College college : collegeList) {
				if (college.getIsDel() == 0 && college.getLdcStorageId() == storage.getStorageId()) {
					userLdcCollege.add(college.getCollegeId());
				}
			}
		}
		// 聚合ldc学校
		skuLdcCollege.retainAll(userLdcCollege);
		// 根据sku获得售卖的虚拟仓学校
		BgSku bgSkuTemp = bgSkuService.getBgSkuById(bgSkuIds[0]);
		Long vmStorageId = bgSkuTemp != null ? bgSkuTemp.getStorageId() : 0;
		List<Long> skuVmCollege = skuService.getCollegeIdsByBgSkuId(
				bgSkuIds[0], Sku.VM_DISTRIBUTE_TYPE);
		for (int i = 1;i<bgSkuIds.length;i++) {
			List<Long> skuCollegeIdList = skuService.getCollegeIdsByBgSkuId(
					bgSkuIds[i], Sku.VM_DISTRIBUTE_TYPE);
			bgSkuTemp = bgSkuService.getBgSkuById(bgSkuIds[0]);
			if(bgSkuTemp == null || bgSkuTemp.getStorageId() != vmStorageId){
				LogConstant.mallLog.info("[bgSku can not produce region]bgGoodsId:"+bgGoodsId+"|bgSkuId:"+bgSkuIds[i]);
				return null;
			}
			skuVmCollege.retainAll(skuCollegeIdList);
		}
		// 根据用户获得可管理的ldc学校
		List<Storage> userVmStorage = user.getVmStorageList();
		boolean inUserVmStorage = false;
		for(Storage storage : userVmStorage){
			if(storage.getStorageId() == vmStorageId){
				inUserVmStorage = true;
			}
		}
		// 聚合虚拟仓学校(根据虚拟仓决定)
		if(!inUserVmStorage){
			skuVmCollege = null;
		}
		
		if((skuRdcCollege == null || skuRdcCollege.size() == 0)
				&& (skuLdcCollege == null || skuLdcCollege.size() == 0)
				&& (skuVmCollege == null || skuVmCollege.size() == 0)){
			return null;
		}
		List<Long> rdcCollegeIds = goodsService.getRdcCollegeIds(bgGoodsId);
		List<Long> ldcCollegeIds = goodsService.getLdcCollegeIds(bgGoodsId);
		List<Long> vmCollegeIds = goodsService.getVmCollegeIds(bgGoodsId);
		return ztreeutil.getCollegeZtree(skuRdcCollege, skuLdcCollege,vmStorageId,skuVmCollege,
				rdcCollegeIds,ldcCollegeIds,vmCollegeIds);
	}
}
