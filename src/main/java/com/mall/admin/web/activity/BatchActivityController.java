package com.mall.admin.web.activity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;

@Controller
@RequestMapping("/activity/batch")
public class BatchActivityController extends BaseController {

	@Autowired
	private BgGoodsForActivityService bgGoodsForActivityService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private GoodsService goodsService;

	private static final Logger log = LogConstant.mallLog;

	@ResponseBody
	@RequestMapping("/copy")
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "0") Long activityId,
			@RequestParam(defaultValue = "0") Long collegeId) throws SQLException,
			IOException {
		if(activityId == null || activityId == 0){
			return "activityId必填且不能为0";
		}
		if(collegeId == null || collegeId == 0){
			return "collegeId必填且不能为0";
		}
		
		List<Sku> skuList = skuService.get4copy2activity(collegeId);
		if(skuList == null || skuList.size() == 0){
			System.out.println("[学校对应的ldc商品sku不存在]activityId:"+activityId+"collegeId:"+collegeId);
			return "[学校对应的ldc商品sku不存在]activityId:"+activityId+"collegeId:"+collegeId;
		}
		for(Sku sku : skuList){
			Goods goods = goodsService.getById(sku.getGoodsId());
			if(goods == null){
				System.out.println("[学校对应的ldc商品goods不存在]activityId:"+activityId+"collegeId:"+collegeId);
				return "[学校对应的ldc商品goods不存在]activityId:"+activityId+"collegeId:"+collegeId;
			}
			int result = bgGoodsForActivityService.batchBgGoodsForActivity(activityId, collegeId, goods, sku, 0L);
			if(result == -1){
				return -1;
			}
		}
		return 0;
	}
}
