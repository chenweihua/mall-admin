package com.mall.admin.web.activity;

import static com.google.common.base.Preconditions.checkState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.activity.ActivityGoodsModifyBean;
import com.mall.admin.model.bean.activity.ActivitySkuModifyBean;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.activity.BgSkuForActivityService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityGoods;
import com.mall.admin.vo.activity.BgGoodsForActivity;
import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.activity.dto.ActivityGoodsManagerView;
import com.mall.admin.vo.activity.dto.ActivitySkuManagerView;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.user.User;

/**
 * 活动商品管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/activitygoods/manager")
public class ActivityGoodsManagerController extends BaseController {
	@Autowired
	ActivityService activityService;

	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	BgGoodsForActivityService bgGoodsForActivityService;
	@Autowired
	BgSkuForActivityService bgSkuForActivityService;
	
	@Autowired
	private ZtreeUtil ztreeUtil;

	/**
	 * 查看秒杀活动商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/seckillview")
	public Object seckillGoodsView(HttpServletRequest request, HttpServletResponse response) {
		List<Activity> activityList = activityService.getActivityList(true);

		long currentTime = System.currentTimeMillis();
		Date beginTime = new Date(currentTime);
		Date endTime = new Date(currentTime + 7 * 24 * 60 * 60 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return new ModelAndView("activity/manager/seckillmanager", ImmutableMap.of("activitylist",
				activityList, "beginTime", format.format(beginTime), "endTime", format.format(endTime)));
	}

	/**
	 * 查看日常活动商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/generalview")
	public Object generalGoodsView(HttpServletRequest request, HttpServletResponse response) {
		List<Activity> activityList = activityService.getActivityList(false);

		long currentTime = System.currentTimeMillis();
		Date beginTime = new Date(currentTime);
		Date endTime = new Date(currentTime + 7 * 24 * 60 * 60 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return new ModelAndView("activity/manager/generalmanager", ImmutableMap.of("activitylist",
				activityList, "beginTime", format.format(beginTime), "endTime", format.format(endTime)));
	}

	/**
	 * 查询秒杀商品的goods
	 * 
	 * @param request
	 * @param response
	 * @param beginTime
	 * @param endTime
	 *                beginTime — endTime时间段内可用的秒杀活动
	 * @param activityId
	 *                活动id
	 * @param status
	 *                商品状态 （1：待售 2：在售 3：售罄 0：全部）
	 * @param activityType
	 *                查询状态 0：日常活动商品; 1：秒杀商品；
	 * @param searchStr
	 *                商品名称（模糊查询）
	 * @return
	 */
	@RequestMapping("/queryseckillgoods")
	@ResponseBody
	public Object querySeckillGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String beginTime, @RequestParam String endTime, @RequestParam long activityId,
			@RequestParam int status, @RequestParam int activityType, @RequestParam String searchStr,
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = format.parse(beginTime);
			endDate = format.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildJson(1, "开始或结束时间格式错误~");
		}
		if (beginDate.after(endDate)) {
			return buildJson(1, "开始时间大于结束时间~");
		}

		List<BgGoodsForActivity> bgGoodsForActivityList = bgGoodsForActivityService
				.getBgGoodsForActivityByQuery(beginDate, endDate, activityId, searchStr, status,
						activityType, paginationInfo);
		List<ActivityGoodsManagerView> viewBeanList = new ArrayList<ActivityGoodsManagerView>();
		for (BgGoodsForActivity bgGoodsForActivity : bgGoodsForActivityList) {
			ActivityGoodsManagerView viewBean = ActivityGoodsManagerView.initViewBean(bgGoodsForActivity);
			Activity activity = activityService.getActivityById(bgGoodsForActivity.getActivityId());
			viewBean.setActivityName(activity.getActivityName());
			viewBeanList.add(viewBean);
		}

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), viewBeanList, start));
	}

	/**
	 * 查询秒杀商品的goods
	 * 
	 * @param request
	 * @param response
	 * @param beginTime
	 * @param endTime
	 *                beginTime — endTime时间段内可用的秒杀活动
	 * @param activityId
	 *                活动id
	 * @param status
	 *                商品状态 （1：待售 2：在售 3：售罄 0：全部）
	 * @param activityType
	 *                查询状态 0：日常活动商品;1：秒杀商品；
	 * @param searchStr
	 *                商品名称（模糊查询）
	 * @return
	 */
	@RequestMapping("/querygeneralgoods")
	@ResponseBody
	public Object queryGeneralGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityId, @RequestParam int status, @RequestParam int activityType,
			@RequestParam String searchStr, @RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<BgGoodsForActivity> bgGoodsForActivityList = bgGoodsForActivityService
				.getBgGoodsForActivityByQuery(null, null, activityId, searchStr, status, activityType,
						paginationInfo);
		List<ActivityGoodsManagerView> viewBeanList = new ArrayList<ActivityGoodsManagerView>();
		for (BgGoodsForActivity bgGoodsForActivity : bgGoodsForActivityList) {
			ActivityGoodsManagerView viewBean = ActivityGoodsManagerView.initViewBean(bgGoodsForActivity);
			Activity activity = activityService.getActivityById(bgGoodsForActivity.getActivityId());
			viewBean.setActivityName(activity.getActivityName());
			viewBeanList.add(viewBean);
		}
		
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), viewBeanList, start));
	}
	
	

	/**
	 * 根据活动商品goodsId查找对应的活动sku
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryactivitybgsku")
	@ResponseBody
	public Object queryActivityBgSku(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityBgGoodsId) {
		List<BgSkuForActivity> bgSkuForActivityList = bgSkuForActivityService
				.getListByActivityBgGoodsId(activityBgGoodsId);
		List<ActivitySkuManagerView> skuViewBeanList = new ArrayList<ActivitySkuManagerView>();
		for (BgSkuForActivity bgSkuForActivity : bgSkuForActivityList) {
			BgGoodsDto dto = bgGoodsService.getBgGoodsDtoByBgSkuId(bgSkuForActivity.getBgSkuId());
			BgGoods bgGoods = dto.getBgGoods();
			ActivitySkuManagerView viewBean = ActivitySkuManagerView.initBean(bgSkuForActivity, bgGoods);
			skuViewBeanList.add(viewBean);
		}

		return buildJson(0, "查询成功~", ImmutableMap.of("bgSkuInfo", skuViewBeanList));
	}

	/**
	 * 修改秒杀商品信息，支持批量修改
	 * 
	 * @param request
	 * @param response
	 * @param modifyStr
	 * @param modifytype
	 *                0：日常修改, 1:秒杀修改
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Object modifySecKillGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String modifyStr, @RequestParam(required = true) int modifytype) {
		// 获得被修改的商品信息
		List<ActivityGoodsModifyBean> modifyBeanList = gson.fromJson(modifyStr,
				new TypeToken<List<ActivityGoodsModifyBean>>() {
				}.getType());
		if (modifyBeanList == null || modifyBeanList.size() < 1) {
			return buildJson(1, "修改失败，被修改的商品为空~");
		}
		for (ActivityGoodsModifyBean bean : modifyBeanList) {
			/** 秒杀商品的限购只能是1 */
			if (modifytype == Constants.ISSECKILL) {
				bean.setMaxNum(1);
			}
			List<ActivitySkuModifyBean> skuModifyBeanList = bean.getModifySkuList();
			bgGoodsForActivityService.updateBgGoodsForActivity(bean.getWeight(), bean.getMaxNum(),
					bean.getStatus(), bean.getActivityBgGoodsId());
			if (skuModifyBeanList != null && skuModifyBeanList.size() > 0) {
				for (ActivitySkuModifyBean skuModifyBean : skuModifyBeanList) {
					long originPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuModifyBean
							.getOriginPrice()));
					long activityPrice = MoneyUtils.yuan2Fen(Double.parseDouble(skuModifyBean
							.getActivityPrice()));
					bgSkuForActivityService.updateActivityBgSku(originPrice, activityPrice,
							skuModifyBean.getStock(), skuModifyBean.getActivityBgSkuId());
				}

			}
		}
		return buildJson(0, "修改成功~");
	}

	/**
	 * 批量修改时间
	 * 
	 * @param request
	 * @param response
	 * @param activityBgGoodsIds
	 *                被修改的活动id,格式是（id1,id2,id3...）
	 * @param beginTime
	 *                开始时间
	 * @param endTime
	 *                结束时间
	 * @param showTime
	 *                显示时间
	 * @return
	 */
	@RequestMapping("/modifytime")
	@ResponseBody
	public Object modifySecKillGoodsTime(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String activityBgGoodsIdsStr, @RequestParam String beginTime,
			@RequestParam String endTime, @RequestParam String showTime) {
		if (Strings.isEmpty(activityBgGoodsIdsStr)) {
			return buildJson(1, "被修改的商品为空~");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;
		Date showDate = null;
		try {
			beginDate = format.parse(beginTime);
			endDate = format.parse(endTime);
			showDate = format.parse(showTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return buildJson(1, "时间格式错误~");
		}
		if (showDate.after(beginDate)) {
			return buildJson(1, "修改失败，显示时间在开始时间之后~");
		}
		if (beginDate.after(endDate)) {
			return buildJson(1, "修改失败，开始时间在结束时间之后~");
		}
		if (beginDate.getTime() < System.currentTimeMillis()) {
			return buildJson(1, "开始时间不能小于当前时间~");
		}
		String[] activityBgGoodsIdArr = activityBgGoodsIdsStr.split(",");
		for (String activityBgGoodsId : activityBgGoodsIdArr) {
			if (activityBgGoodsId.matches("[0-9]{1,}")) {
				bgGoodsForActivityService.updateBgGoodsForActivityTime(beginDate, endDate, showDate,
						Long.parseLong(activityBgGoodsId));
			}
		}
		return buildJson(0, "时间修改成功~");
	}

	/**
	 * 批量修改时间
	 * 
	 * @param request
	 * @param response
	 * @param activityBgGoodsIds
	 *                被修改的活动id,格式是（id1,id2,id3...）
	 * @param beginTime
	 *                开始时间
	 * @param endTime
	 *                结束时间
	 * @param showTime
	 *                显示时间
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object deleteActivityGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityBgGoodsId) {
		int result = bgGoodsForActivityService.updateIsDelStatus(activityBgGoodsId, 1);
		if (result > 0) {
			return buildJson(0, "删除成功~");
		} else {
			return buildJson(0, "删除失败~");
		}

	}
	
	
	
	
	@RequestMapping("/queryActivityGoodsCollege")
	@ResponseBody
	public Object queryActivityGoodsCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String activityId,@RequestParam String activityBgGoodsId) {

		try {
			long activityIdLong = _.toLong(activityId);
			long activityBgGoodsIdLong = _.toLong(activityBgGoodsId);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkState(user != null, "请先登录");

			
			/*
			List<NoticeCollege> noticeColleges = noticeService.getNoticeCollegeByNoticeId(noticeIdLong);
			//List<Long> collegeIds = Lists.transform(noticeColleges, s -> s.getCollegeId());
			List<Long> collegeIds = new ArrayList<Long>();
			for(NoticeCollege noticeCollege : noticeColleges) {
				collegeIds.add(noticeCollege.getCollegeId());
			}
			*/
			
			ZtreeBean ztreeBean = ztreeUtil.getCityAreaCollegeZtree(null);
			return buildJson(0, "queryActivityGoodsCollege查询成功", ztreeBean);
		} catch (Exception e) {
			LogConstant.mallLog.error("queryActivityGoodsCollege查询发生异常",e);
			return buildErrJson(e.getMessage());
		}

	}
	
	
	@RequestMapping("/queryActivityGoodsId")
	@ResponseBody
	public Object queryActivityGoodsId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String activityId,@RequestParam String activityBgGoodsId,@RequestParam String collegeId) {

		try {
			long activityIdLong = _.toLong(activityId);
			long activityBgGoodsIdLong = _.toLong(activityBgGoodsId);
			long collegeIdLong = _.toLong(collegeId);
			
			
			ActivityGoods activityGoods = bgGoodsForActivityService.queryActivityGoods(activityBgGoodsIdLong, activityIdLong, collegeIdLong);
			if(activityGoods == null) {
				return buildJson(1, "无此商品");
			}
			
			return buildJson(0, "查询成功", activityGoods.getActivityGoodsId());
		} catch (Exception e) {
			LogConstant.mallLog.error("queryActivityGoodsId查询发生异常",e);
			return buildErrJson(e.getMessage());
		}

	}
}
