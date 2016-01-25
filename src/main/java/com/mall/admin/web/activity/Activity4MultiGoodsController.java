package com.mall.admin.web.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.activity.ActivityGoodsApplyBean;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/activity4MultiGoods")
public class Activity4MultiGoodsController extends BaseController {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private BgGoodsForActivityService bgGoodsForActivityService;
	@Autowired
	private BgGoodsService bgGoodsService;
	
	@ResponseBody
	@RequestMapping("/getActivities/byActivityType")
	public Object getActivities(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Integer activityType) throws Exception {
		List<Activity> activities = activityService.getActivityByActivityType(activityType);
		return buildSuccJson(activities);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param bgGoodsInfo
	 * @param storageType
	 *                商品类型 0：rdc商品 1：ldc商品
	 * @param beginTime
	 * @param endTime
	 * @param showTime
	 * @param activityId
	 *                活动id
	 * @param activityType
	 *                0：普通活动，1:秒杀活动
	 * @return
	 */
	@RequestMapping("/addMultiBgGoods")
	@ResponseBody
	public Object addSeckillBgGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "-1") Long bgGoodsId,
			@RequestParam Integer activityType,
			@RequestParam Long activityId,
			@RequestParam Integer storageType,
			@RequestParam(required = false) String showTime,
			@RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(defaultValue = "-1") String originPrice,
			@RequestParam(defaultValue = "-1") String price,
			@RequestParam(defaultValue = "-1") Integer weight,
			@RequestParam(defaultValue = "-1") Integer skuStock,
			@RequestParam(defaultValue = "-1") Integer maxNum
			) {
		//目前只支持一次添加一个，之后会考虑以“,”分割，一次添加多个bgGoods
		
		//数据校验
		
		ActivityGoodsApplyBean bean = new ActivityGoodsApplyBean();
		bean.setBgGoodsId(bgGoodsId);
		bean.setOriginPrice(originPrice);
		bean.setActivityPrice(price);
		bean.setStock(skuStock);
		bean.setWeight(weight);
		bean.setMaxNum(maxNum);
		//log
		LogConstant.mallLog.info(new Date() + "[add multiBgGoods]bgGoodsId"+bgGoodsId+"|info:"+JSONObject.toJSONString(bean)+
				"|showTime:"+showTime+"|beginTime:"+beginTime+"|endTime:"+endTime);
		Date beginDate = null;
		Date endDate = null;
		Date showDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int isSecKill = 0;
		if (activityType == Activity.ACTIVITY_TYPE_SECKILL) {
			isSecKill = 1;
			//秒杀限购为什么设置1
			bean.setMaxNum(1);
			try {
				beginDate = format.parse(beginTime);
				endDate = format.parse(endTime);
				showDate = format.parse(showTime);
			} catch (ParseException e) {
				LogConstant.mallLog.info("[add multiBgGoods]activityId:"+activityId+"|bgGoodsId"+bgGoodsId+"|info:"+JSONObject.toJSONString(bean)+"error:"+e);
				return buildJson(1, "提交的时间格式（yyyy-mm-dd HH:mm:ss）错误~");
			}
			if (beginDate.getTime() < System.currentTimeMillis()) {
				return buildJson(1, "开始时间应大于当前时间，禁止添加~");
			}
			if (showDate.after(beginDate)) {
				return buildJson(1, "显示时间应小于开始时间，禁止添加~");
			}
			if (beginDate.after(endDate)) {
				return buildJson(1, "开始时间应小于结束时间，禁止添加~");
			}
		}
		StringBuffer message = new StringBuffer("");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		int addNum = bgGoodsForActivityService.insertBgGoodsForActivity(bean, activityId, storageType,
				user.getUser_id(), beginDate, endDate, showDate, isSecKill);
		LogConstant.mallLog.info("活动{}添加商品{}成功，添加学校{}个", activityId, bean.getBgGoodsId(), addNum);
		BgGoods bggoods = bgGoodsService.getBgGoodsById(bean.getBgGoodsId());
		if (addNum == -1) {
			return buildJson(1, "添加失败，请先设置活动的范围~");
		} else if (addNum == -2) {
			message.append("添加失败，商品【" + bggoods.getBgGoodsName() + "】和活动的范围不存在交集。\n");
		} else if (addNum == -3) {
			message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】在该活动下已存在。\n");
		} else {
			message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】添加成功，添加到" + addNum + "个学校内。\n");
		}
	return buildJson(0, message.toString());
	}
}
