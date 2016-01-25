package com.mall.admin.web;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.goods.BgGoodsRegionService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgGoodsRegion;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.dto.CityDto;
import com.mall.admin.vo.mallbase.dto.CollegeDto;
import com.mall.admin.vo.mallbase.dto.StorageDto;
import com.mall.admin.vo.user.User;

/**
 * 用户登录控制对象
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods/price/")
public class GoodsPriceManagerController extends BaseController {
	@Autowired
	StorageService storageService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	CityService cityService;
	@Autowired
	BgGoodsRegionService bgGoodsRegionService;
	@Autowired
	SkuService skuService;
	@Autowired
	private BgGoodsService bgGoodsService;
	@Autowired
	BgSkuService bgSkuService;

	private static final Logger log = LogConstant.mallLog;

	@ResponseBody
	@RequestMapping("/getRdcStorage4Price/byBgGoodsId")
	public Object getRdcStorage4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
					throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		// 获取所有学校
		List<Long> collegeIds = goodsService.getRdcCollegeIds(bgGoodsId);
		// 范围信息，以goods表中对应的学校为准
		Map<String, List<Long>> map = collegeService.parseBaseInfo(collegeIds);
		List<Long> rdcStorage = map.get("rdcStorage");
		// 需要加入用户权限
		List<StorageDto> rdcStorages = storageService.getRdcStorages(user, rdcStorage);
		// 分页
		List<StorageDto> data = new ArrayList<>();
		for (int i = start; i < rdcStorages.size(); i++) {
			data.add(rdcStorages.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}
		// 加入已有数据
		for (StorageDto temp : data) {
			bgGoodsRegionService.addPrice2Dto(temp, bgGoodsId, BgGoodsRegion.REGION_TYPE_STORAGE);
		}
		return buildDataTableResult(draw, 0, rdcStorages.size(), data);
	}
	
	@ResponseBody
	@RequestMapping("/getVmStorage4Price/byBgGoodsId")
	public Object getVmStorage4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		// 获取所有学校
		List<Long> collegeIds = goodsService.getVmCollegeIds(bgGoodsId);
		// 范围信息，以goods表中对应的学校为准
		List<Long> vmStorageIds = new ArrayList<>();
		BgGoods bgGoods = bgGoodsService.getBgGoodsById(bgGoodsId);
		vmStorageIds.add(bgGoods.getStorageId());
		// 需要加入用户权限
		List<StorageDto> rdcStorages = storageService.getVmStorages(user, vmStorageIds);
		// 分页
		List<StorageDto> data = new ArrayList<>();
		for (int i = start; i < rdcStorages.size(); i++) {
			data.add(rdcStorages.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}
		// 加入已有数据
		for (StorageDto temp : data) {
			bgGoodsRegionService.addPrice2Dto(temp, bgGoodsId, BgGoodsRegion.REGION_TYPE_STORAGE);
		}
		return buildDataTableResult(draw, 0, rdcStorages.size(), data);
	}

	@ResponseBody
	@RequestMapping("/getLdcCity4Price/byBgGoodsId")
	public Object getLdcCity4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);

		// 获取所有学校
		List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsId);

		// 范围信息，以goods表中对应的学校为准
		Map<String, List<Long>> map = collegeService.parseBaseInfo(collegeIds);
		List<Long> ldcCity = map.get("ldcCity");

		List<CityDto> ldcCitys = cityService.getCitys(ldcCity);

		// 分页
		List<CityDto> data = new ArrayList<>();
		for (int i = start; i < ldcCitys.size(); i++) {
			data.add(ldcCitys.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}

		// 加入已有数据
		for (CityDto temp : data) {
			bgGoodsRegionService.addPrice2Dto(temp, bgGoodsId, BgGoodsRegion.REGION_TYPE_CITY);
		}
		return buildDataTableResult(draw, 0, ldcCitys.size(), data);
	}

	@ResponseBody
	@RequestMapping("/getLdcStorage4Price/byBgGoodsIdCityId")
	public Object getLdcStorage4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);

		String cityIdStr = request.getParameter("cityId");
		if (cityIdStr == null || !cityIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long cityId = _.toLong(cityIdStr);

		// 获取所有学校
		List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsId);

		// 范围信息，以goods表中对应的学校为准
		List<Long> ldcStorageIds = collegeService.parseStorageInfo(collegeIds, cityId, Sku.LDC_DISTRIBUTE_TYPE);
		// 需要加入用户权限
		List<StorageDto> ldcStorages = storageService.getLdcStorages(user, ldcStorageIds);

		// 分页
		List<StorageDto> data = new ArrayList<>();
		for (int i = start; i < ldcStorages.size(); i++) {
			data.add(ldcStorages.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}

		// 加入已有数据
		for (StorageDto temp : data) {
			bgGoodsRegionService.addPrice2Dto(temp, bgGoodsId, BgGoodsRegion.REGION_TYPE_STORAGE);
		}
		return buildDataTableResult(draw, 0, ldcStorages.size(), data);
	}

	@ResponseBody
	@RequestMapping("/getLdcCollege4Price/byBgGoodsIdStorageId")
	public Object getLdcCollege4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);

		String storageIdStr = request.getParameter("storageId");
		if (bgGoodsIdStr == null || !storageIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long storageId = _.toLong(storageIdStr);

		// 获取所有学校
		List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsId);

		// 过滤学校
		List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds, storageId, Sku.LDC_DISTRIBUTE_TYPE);

		// 分页
		List<CollegeDto> data = new ArrayList<>();
		for (int i = start; i < collegeDtos.size(); i++) {
			data.add(collegeDtos.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}

		// 加入已有数据
		for (CollegeDto temp : data) {
			skuService.addRdcPrice2CollegeDto(temp, bgGoodsId, Sku.LDC_DISTRIBUTE_TYPE);
		}

		return buildDataTableResult(draw, 0, collegeDtos.size(), data);
	}

	@ResponseBody
	@RequestMapping("/getRdcCollege4Price/byBgGoodsIdStorageId")
	public Object getRdcCollege4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
					throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		
		String storageIdStr = request.getParameter("storageId");
		if (bgGoodsIdStr == null || !storageIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long storageId = _.toLong(storageIdStr);
		
		// 获取所有学校
		List<Long> collegeIds = goodsService.getRdcCollegeIds(bgGoodsId);
		
		// 过滤学校
		List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds, storageId, Sku.RDC_DISTRIBUTE_TYPE);
		
		// 分页
		List<CollegeDto> data = new ArrayList<>();
		for (int i = start; i < collegeDtos.size(); i++) {
			data.add(collegeDtos.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}
		
		// 加入已有数据
		for (CollegeDto temp : data) {
			skuService.addRdcPrice2CollegeDto(temp, bgGoodsId, Sku.RDC_DISTRIBUTE_TYPE);
		}
		
		return buildDataTableResult(draw, 0, collegeDtos.size(), data);
	}
	
	@ResponseBody
	@RequestMapping("/getVmCollege4Price/byBgGoodsIdStorageId")
	public Object getVmCollege4Price(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);

		String storageIdStr = request.getParameter("storageId");
		if (bgGoodsIdStr == null || !storageIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long storageId = _.toLong(storageIdStr);

		// 获取所有学校
		List<Long> collegeIds = goodsService.getVmCollegeIds(bgGoodsId);

		// 过滤学校
		List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds, storageId, Sku.VM_DISTRIBUTE_TYPE);

		// 分页
		List<CollegeDto> data = new ArrayList<>();
		for (int i = start; i < collegeDtos.size(); i++) {
			data.add(collegeDtos.get(i));
			if (i > start + numPerPage - 1) {
				break;
			}
		}

		// 加入已有数据
		for (CollegeDto temp : data) {
			skuService.addRdcPrice2CollegeDto(temp, bgGoodsId, Sku.VM_DISTRIBUTE_TYPE);
		}

		return buildDataTableResult(draw, 0, collegeDtos.size(), data);
	}

	@ResponseBody
	@RequestMapping("/editPrice")
	public Object editRdcStoragePrice(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		BgGoodsRegion bgGoodsRegion = RequestUtil.toBean(request, BgGoodsRegion.class);

		if (bgGoodsRegion.getBgGoodsId() == -1) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}

		if (bgGoodsRegion.getRegionId() == -1) {
			LogConstant.mallLog.info("价格信息修改失败，regionId:" + bgGoodsRegion.getRegionId() + "bgGoodsId:"
					+ bgGoodsRegion.getBgGoodsId());
			return buildErrJson("价格修改失败~");
		}

		String originPriceStr = request.getParameter("originPrice");
		if (!originPriceStr.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱必须为数字，且精确小数点后两位！");
		}
		long originPrice = MoneyUtils.yuan2Fen(_.toDouble(originPriceStr));
		if (originPrice < 1) {
			return buildJson(-1, "价钱必须大于零");
		}
		bgGoodsRegion.setOriginPrice(originPrice);

		String wapPriceStr = request.getParameter("wapPrice");
		if (!wapPriceStr.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱必须为数字，且精确小数点后两位！");
		}
		long wapPrice = MoneyUtils.yuan2Fen(_.toDouble(wapPriceStr));
		if (wapPrice < 1) {
			return buildJson(-1, "价钱必须大于零");
		}
		bgGoodsRegion.setWapPrice(wapPrice);

		String appPriceStr = request.getParameter("appPrice");
		if (!appPriceStr.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
			return buildJson(1, "价钱必须为数字，且精确小数点后两位！");
		}
		long appPrice = MoneyUtils.yuan2Fen(_.toDouble(appPriceStr));
		if (appPrice < 1) {
			return buildJson(-1, "价钱必须大于零");
		}
		bgGoodsRegion.setAppPrice(appPrice);

		String maxNumStr = request.getParameter("maxNum");
		if (!maxNumStr.matches("[0-9]{1,}")) {
			return buildJson(1, "购买数量格式错误！");
		}
		long maxNum = _.toLong(maxNumStr);
		bgGoodsRegion.setMaxNum(maxNum);

		bgGoodsRegion.setStock(0);
		bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
		bgGoodsRegion.setWeight(0);

		if (bgGoodsRegion.getRegionType() == 3) {
			// 修改学校价格
			long skuId = bgGoodsRegion.getRegionId();
			boolean r = skuService.modifyPriceMaxnum(skuId, bgGoodsRegion.getOriginPrice(),
					bgGoodsRegion.getWapPrice(), bgGoodsRegion.getAppPrice(),
					bgGoodsRegion.getMaxNum());
			if (!r) {
				return buildErrJson("价格修改失败");
			}
		} else if (bgGoodsRegion.getRegionType() == 1) {
			// rdc仓
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改rdc仓对应所有学校该商品的价格
			List<Long> collegeIds = goodsService.getRdcCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.RDC_DISTRIBUTE_TYPE);

			// 批量修改rdc仓下所有学校
			boolean r = skuService.batchModifyRdcPriceMaxNum(collegeDtos, bgGoodsRegion.getBgGoodsId(),
					originPrice, wapPrice, appPrice, maxNum, Sku.RDC_DISTRIBUTE_TYPE);
			if (!r)
				return buildErrJson("价格修改失败");

		} else if (bgGoodsRegion.getRegionType() == 2) {
			// ldc城市
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改ldc城市对应所有学校该商品的价格

			List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			Map<String, List> map = collegeService.filterByCityId(collegeIds, bgGoodsRegion.getRegionId(),
					Sku.LDC_DISTRIBUTE_TYPE);
			// 批量修改仓价格
			List<Long> storages = map.get("storages");
			for (Long sId : storages) {
				bgGoodsRegionService.update(sId, bgGoodsRegion.getBgGoodsId(), BgGoodsRegion.REGION_TYPE_STORAGE, originPrice,
						wapPrice, appPrice, maxNum);
			}
			// 批量修改ldc仓下所有学校
			List<CollegeDto> collegeDtos = map.get("collegeDtos");
			boolean r = skuService.batchModifyRdcPriceMaxNum(collegeDtos, bgGoodsRegion.getBgGoodsId(),
					originPrice, wapPrice, appPrice, maxNum, Sku.LDC_DISTRIBUTE_TYPE);
			if (!r)
				return buildErrJson("价格修改失败");
		} else if (bgGoodsRegion.getRegionType() == 4) {
			// ldc仓
			bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改ldc仓对应所有学校该商品的价格
			List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsRegion.getBgGoodsId());

			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.LDC_DISTRIBUTE_TYPE);

			// 批量修改ldc仓下所有学校
			boolean r = skuService.batchModifyRdcPriceMaxNum(collegeDtos, bgGoodsRegion.getBgGoodsId(),
					originPrice, wapPrice, appPrice, maxNum, Sku.LDC_DISTRIBUTE_TYPE);
			if (!r)
				return buildErrJson("价格修改失败");
		}else if (bgGoodsRegion.getRegionType() == 5) {
			// 虚拟仓
			bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改rdc仓对应所有学校该商品的价格
			List<Long> collegeIds = goodsService.getVmCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.VM_DISTRIBUTE_TYPE);

			// 批量修改rdc仓下所有学校
			boolean r = skuService.batchModifyRdcPriceMaxNum(collegeDtos, bgGoodsRegion.getBgGoodsId(),
					originPrice, wapPrice, appPrice, maxNum, Sku.VM_DISTRIBUTE_TYPE);
			if (!r)
				return buildErrJson("价格修改失败");

		}
		return buildJson(0, "修改成功");
	}

	@ResponseBody
	@RequestMapping("/editMaxNum")
	public Object editMaxNum(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		BgGoodsRegion bgGoodsRegion = RequestUtil.toBean(request, BgGoodsRegion.class);

		if (bgGoodsRegion.getBgGoodsId() == -1) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}

		if (bgGoodsRegion.getRegionId() == -1) {
			LogConstant.mallLog.info("限购信息修改失败，regionId:" + bgGoodsRegion.getRegionId() + "bgGoodsId:"
					+ bgGoodsRegion.getBgGoodsId());
			return buildErrJson("限购修改失败~");
		}

		String maxNumStr = request.getParameter("maxNum");
		if (!maxNumStr.matches("[0-9]{1,}")) {
			return buildJson(1, "购买数量格式错误！");
		}
		long maxNum = _.toLong(maxNumStr);
		bgGoodsRegion.setMaxNum(maxNum);

		bgGoodsRegion.setStock(0);
		bgGoodsRegion.setStatus(Constants.GOOD_STATUS_ONSALE);
		bgGoodsRegion.setWeight(0);

		if (bgGoodsRegion.getRegionType() == 3) {
			// 修改学校价格
			long goodsId = bgGoodsRegion.getRegionId();
			boolean r = skuService.modifyMaxnum(goodsId, bgGoodsRegion.getMaxNum());
			if (!r) {
				return buildErrJson("限购修改失败");
			}
		} else if (bgGoodsRegion.getRegionType() == 1) {
			// rdc仓
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("限购修改失败");
			}
			// 修改rdc仓对应所有学校该商品的价格
			List<Long> collegeIds = goodsService.getRdcCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.RDC_DISTRIBUTE_TYPE);

			// 批量修改rdc仓下所有学校
			for (CollegeDto collegeDto : collegeDtos) {
				int ui = goodsService.updateMaxNum(bgGoodsRegion.getBgGoodsId(), collegeDto
						.getCollege().getCollegeId(), maxNum);
				if (ui < 0) {
					LogConstant.mallLog.error("修改限购数量时，bgGoodsID:" + bgGoodsRegion.getBgGoodsId()
							+ "，学校ID：" + collegeDto.getCollege().getCollegeId() + "失败！");
				}
			}
		} else if (bgGoodsRegion.getRegionType() == 2) {
			// ldc城市
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改ldc城市对应所有学校该商品的价格

			List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			Map<String, List> map = collegeService.filterByCityId(collegeIds, bgGoodsRegion.getRegionId(),
					Sku.LDC_DISTRIBUTE_TYPE);
			// 批量修改仓价格
			List<Long> storages = map.get("storages");
			for (Long sId : storages) {
				bgGoodsRegionService.update(sId, bgGoodsRegion.getBgGoodsId(), 1, 0, 0, 0, maxNum);
			}

			// 批量修改ldc仓下所有学校
			List<CollegeDto> collegeDtos = map.get("collegeDtos");
			for (CollegeDto collegeDto : collegeDtos) {
				int ui = goodsService.updateMaxNum(bgGoodsRegion.getBgGoodsId(), collegeDto
						.getCollege().getCollegeId(), maxNum);
				if (ui < 0) {
					LogConstant.mallLog.error("修改限购数量时，bgGoodsID:" + bgGoodsRegion.getBgGoodsId()
							+ "，学校ID：" + collegeDto.getCollege().getCollegeId() + "失败！");
				}
			}
		} else if (bgGoodsRegion.getRegionType() == 4) {
			// ldc仓
			bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("价格修改失败");
			}
			// 修改ldc仓对应所有学校该商品的价格
			List<Long> collegeIds = goodsService.getLdcCollegeIds(bgGoodsRegion.getBgGoodsId());

			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.LDC_DISTRIBUTE_TYPE);

			// 批量修改ldc仓下所有学校
			for (CollegeDto collegeDto : collegeDtos) {
				int ui = goodsService.updateMaxNum(bgGoodsRegion.getBgGoodsId(), collegeDto
						.getCollege().getCollegeId(), maxNum);
				if (ui < 0) {
					LogConstant.mallLog.error("修改限购数量时，bgGoodsID:" + bgGoodsRegion.getBgGoodsId()
							+ "，学校ID：" + collegeDto.getCollege().getCollegeId() + "失败！");
				}
			}
		}else if (bgGoodsRegion.getRegionType() == 5) {
			// 虚拟仓
			bgGoodsRegion.setRegionType(BgGoodsRegion.REGION_TYPE_STORAGE);
			bgGoodsRegion.setIsDel(0);
			long bg_goods_region_id = bgGoodsRegionService.insertOrUpdatePrice(bgGoodsRegion);
			if (bg_goods_region_id == -1) {
				return buildErrJson("限购修改失败");
			}
			// 修改虚拟仓对应所有学校该商品的限购
			List<Long> collegeIds = goodsService.getVmCollegeIds(bgGoodsRegion.getBgGoodsId());
			// 过滤学校，这样获取的学校有可能在sku表中存在相同学校id的ldc
			List<CollegeDto> collegeDtos = collegeService.filterByStorageId(collegeIds,
					bgGoodsRegion.getRegionId(), Sku.VM_DISTRIBUTE_TYPE);

			// 批量修改rdc仓下所有学校
			for (CollegeDto collegeDto : collegeDtos) {
				int ui = goodsService.updateMaxNum(bgGoodsRegion.getBgGoodsId(), collegeDto
						.getCollege().getCollegeId(), maxNum);
				if (ui < 0) {
					LogConstant.mallLog.error("修改限购数量时，bgGoodsID:" + bgGoodsRegion.getBgGoodsId()
							+ "，学校ID：" + collegeDto.getCollege().getCollegeId() + "失败！");
				}
			}
		}
		return buildJson(0, "修改成功");
	}

	/**
	 * 批量修改全国仓的价格和最大购买数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/batchEditPrice")
	public Object batchEditRdcStoragePrice(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		String originPriceStr = request.getParameter("originPrice");
		String wapPriceStr = request.getParameter("wapPrice");
		String appPriceStr = request.getParameter("appPrice");
		String maxNumStr = request.getParameter("maxNum");
		try {
			checkArgument(bgGoodsIdStr != null && bgGoodsIdStr.matches("[0-9]{1,}"), "待修改的商品错误~");
			checkArgument(originPriceStr != null && originPriceStr.matches("\\d{1,10}(\\.\\d{1,2})?$")
					&& Double.parseDouble(originPriceStr) > 0, "待修改的商品原价错误~");
			checkArgument(wapPriceStr != null && wapPriceStr.matches("\\d{1,10}(\\.\\d{1,2})?$")
					&& Double.parseDouble(wapPriceStr) > 0, "待修改的商品wap价格错误~");
			checkArgument(appPriceStr != null && appPriceStr.matches("\\d{1,10}(\\.\\d{1,2})?$")
					&& Double.parseDouble(appPriceStr) > 0, "待修改的商品app价格错误~");
			checkArgument(maxNumStr != null && maxNumStr.matches("[0-9]{1,}"), "待修改的商品错误~");

			long bgGoodsId = Long.parseLong(bgGoodsIdStr);
			long originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(originPriceStr));
			long wapPrice = MoneyUtils.yuan2Fen(Double.parseDouble(wapPriceStr));
			long appPrice = MoneyUtils.yuan2Fen(Double.parseDouble(appPriceStr));
			int maxNum = Integer.parseInt(maxNumStr);

			BgSku bgSku = bgSkuService.getByBgGoodsId(bgGoodsId);

			// 批量更新sku的价格
			skuService.batchModifyPrice(bgGoodsId, bgSku.getBgSkuId(), originPrice, wapPrice, appPrice,
					maxNum);
			// 修改商品范围内的价格
			bgGoodsRegionService.batchUpdate(bgGoodsId, originPrice, wapPrice, appPrice, maxNum);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("批量全仓修改商品信息错误,[bgGoodsId:{},originPrice:{},wapPrice:{},appPrice:{},maxNum:{}]，error：{}",
					bgGoodsIdStr, originPriceStr, wapPriceStr, appPriceStr, maxNumStr, e);
			return buildJson(1, e.getMessage());
		}

		return buildJson(0, "修改成功");
	}
}
