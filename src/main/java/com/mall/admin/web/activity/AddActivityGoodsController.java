package com.mall.admin.web.activity;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.activity.ActivityGoodsApplyBean;
import com.mall.admin.model.bean.activity.ActivitySkuApplyBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.activity.BgGoodsForActivityService;
import com.mall.admin.service.activity.BgSkuForActivityService;
import com.mall.admin.service.activitytemplate.ActivityTemplateService;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.service.sale.SaleService;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.BgGoodsForActivity;
import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.activity.TemplateActivity;
import com.mall.admin.vo.activity.dto.BgGoodsInActivityView;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.sale.SaleSkuInfo;
import com.mall.admin.vo.user.User;

/**
 * 活动商品上单管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/activitygoods")
public class AddActivityGoodsController extends BaseController {
	private static final Logger log = LogConstant.mallLog;

	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	BgSkuService bgSkuService;
	@Autowired
	ActivityService activityService;
	@Autowired
	SaleService saleService;

	@Autowired
	BgGoodsForActivityService bgGoodsForActivityService;
	
	@Autowired
	BgSkuForActivityService bgSkuForActivityService;
	
	@Autowired
	private ActivityTemplateService activityTemplateService;

	@RequestMapping("/addseckillgoodsview")
	public Object addSeckillGoodsView(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		List<Activity> activityList = activityService.getActivityList(true);
		// Activity defaultActivity = null;
		// if (activityList != null && activityList.size() > 0) {
		// defaultActivity = activityList.get(0);
		// }
		if (activityList == null || activityList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建秒杀活动~"));
		}
		return new ModelAndView("activity/SeckillActivityGoodsList", ImmutableMap.of("activitylist",
				activityList));
	}

	/**
	 * 添加日常活动的商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping("/addgeneralgoodsview")
	public Object addGeneralGoodsView(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		List<Activity> activityList = activityService.getActivityList(false);
		// Activity defaultActivity = null;
		// if (activityList != null && activityList.size() > 0) {
		// defaultActivity = activityList.get(0);
		// }
		if (activityList == null || activityList.size() == 0) {
			return new ModelAndView("info", ImmutableMap.of("message", "请先创建日常活动~"));
		}
		return new ModelAndView("activity/generalActivityGoodsList", ImmutableMap.of("activitylist",
				activityList));
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 *                开始值
	 * @param numPerPage
	 *                每页个数
	 * @param searchStr
	 *                查询条件
	 * @param activityType
	 *                0：普通活动 1：秒杀活动
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/querybggoods")
	@ResponseBody
	public Object querySeckillBgGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr, @RequestParam int activityType)
			throws SQLException, IOException, ParseException {
		User loginInfo = (User) request.getAttribute("user");

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<BgGoods> bgGoodsList = new ArrayList<BgGoods>();

		if (Strings.isEmpty(searchStr)) {
			if (Constants.ISGENERAL == activityType || activityType == Activity.ACTIVITY_TYPE_POPULAR || activityType == Activity.ACTIVITY_TYPE_BRAND) {
				bgGoodsList = bgGoodsService.getBgGoodsList(null, paginationInfo);
			} else if (Constants.ISSECKILL == activityType) {
				bgGoodsList = bgGoodsService.selectSingleByPage(null, paginationInfo);
			}
		} else {
			if (Constants.ISGENERAL == activityType || activityType == Activity.ACTIVITY_TYPE_POPULAR || activityType == Activity.ACTIVITY_TYPE_BRAND) {
				bgGoodsList = bgGoodsService.getBgGoodsList(searchStr, paginationInfo);
			} else if (Constants.ISSECKILL == activityType) {
				bgGoodsList = bgGoodsService.selectSingleByPage(searchStr, paginationInfo);
			}
		}

		List<BgGoodsInActivityView> viewList = new ArrayList<BgGoodsInActivityView>();
		for (BgGoods bgGoods : bgGoodsList) {
			BgGoodsInActivityView bean = BgGoodsInActivityView.initBgGoodsInActivityView(bgGoods);
			if (bgGoods.getGoodsType() == 1 || bgGoods.getGoodsType() == 2) {
				BgSku bgSku = bgSkuService.getByBgGoodsId(bgGoods.getBgGoodsId());
				bean.setOriginPrice(bgSku.getOriginPrice());
			}
			viewList.add(bean);
		}
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), viewList, start));
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
	@RequestMapping("/addbggoods")
	@ResponseBody
	public Object addSeckillBgGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String bgGoodsInfo, @RequestParam int storageType,
			@RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String showTime, @RequestParam long activityId,
			@RequestParam int activityType) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<ActivityGoodsApplyBean> beanList = gson.fromJson(bgGoodsInfo,
				new TypeToken<List<ActivityGoodsApplyBean>>() {
				}.getType());
		Date beginDate = null;
		Date endDate = null;
		Date showDate = null;
		if (activityType == Constants.ISSECKILL) {

			try {
				beginDate = format.parse(beginTime);
				endDate = format.parse(endTime);
				showDate = format.parse(showTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		for (ActivityGoodsApplyBean bean : beanList) {
			if (activityType == Constants.ISSECKILL) {
				bean.setMaxNum(1);
			}
			int addNum = bgGoodsForActivityService.insertBgGoodsForActivity(bean, activityId, storageType,
					user.getUser_id(), beginDate, endDate, showDate, activityType);
			log.info("活动{}添加商品{}成功，添加学校{}个", activityId, bean.getBgGoodsId(), addNum);
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
		}
		return buildJson(0, message.toString());
	}

	/**
	 * 后台商品模板对应的sku模板
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/getskuinfo")
	@ResponseBody
	public Object queryBgSkuByBgGoodsId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long bggoodsId, @RequestParam int goodsType) {
		List<SaleSkuInfo> saleSkuInfoList = saleService.getSaleSkuInfoByGoodsId(bggoodsId, goodsType);
		return buildJson(0, "查询成功~", ImmutableMap.of("bgSkuInfo", saleSkuInfoList));
	}
	
	
	
	/**
	 * 添加爆品活动
	 */
	@RequestMapping("/addActivityPopularGoods")
	@ResponseBody
	public Object addPopularGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long popularActivityId, //活动ID
			@RequestParam long popularActivityGoodsId,  //商品ID
			@RequestParam String bgGoodsInfo,
			@RequestParam Integer storageType,  //RDC\LDC\第三方卖家商品
			@RequestParam String headerImageUrl, //头部图片 
			@RequestParam String imageUrl,   //描述图片
			@RequestParam String activityRule,  //活动规则
			@RequestParam String beginTime, //开始时间
			@RequestParam String endTime,  //结束时间
			@RequestParam(required=false) String showTime  //显示时间
			) {
		
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		
		Date beginDate = null;
		Date endDate = null;
		//Date showDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			beginDate = format.parse(beginTime);
			endDate = format.parse(endTime);
			//showDate = format.parse(showTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildJson(1, "提交的时间格式（yyyy-mm-dd HH:mm:ss）错误~");
		}
		
		TemplateActivity templateActivity = new TemplateActivity();
		templateActivity.setActivityId(popularActivityId);
		templateActivity.setActivityRule(activityRule);
		templateActivity.setHeadImageUrl(headerImageUrl);
		templateActivity.setImageUrl(imageUrl);
		activityService.saveOrUpdateTemplateActivity(templateActivity);
		
		ActivityGoodsApplyBean activityGoodsApplyBean = gson.fromJson(bgGoodsInfo,
				new TypeToken<ActivityGoodsApplyBean>() {
				}.getType());
		
		//ActivityGoodsApplyBean bean = new ActivityGoodsApplyBean();
		//bean.setActivityPrice(activityGoodsApplyBean.getActivityPrice());
		//bean.setOriginPrice(activityGoodsApplyBean.getOriginPrice());
		//bean.setBgGoodsId(popularActivityGoodsId);
		//bean.setMaxNum(maxNum);
		//bean.setStock(stock);
		//bean.setWeight(0);
		
		//品牌活动无限量的逻辑，这里设置int最大值
		activityGoodsApplyBean.setMaxNum(Integer.MAX_VALUE); 
		activityGoodsApplyBean.setStock(Integer.MAX_VALUE);
		if(activityGoodsApplyBean.getSkuListBean() != null) {
			for(ActivitySkuApplyBean bean : activityGoodsApplyBean.getSkuListBean()) {
				bean.setStock(Integer.MAX_VALUE);
			}
		}
		
		bgGoodsForActivityService.delBgGoodsForActivity(popularActivityId);
		
		int addNum = bgGoodsForActivityService.insertBgGoodsForActivity(activityGoodsApplyBean, popularActivityId, storageType,
				user.getUser_id(), beginDate, endDate, null, Activity.ACTIVITY_TYPE_POPULAR);
		BgGoods bggoods = bgGoodsService.getBgGoodsById(activityGoodsApplyBean.getBgGoodsId());
		String message = "";
		if (addNum == -1) {
			message = "添加失败，请先设置活动的范围~";
			return buildJson(1, message);
		} else if (addNum == -2) {
			message = "添加失败，商品【" + bggoods.getBgGoodsName() + "】和活动的范围不存在交集。\n";
			return buildJson(1, message);
		} else if (addNum == -3) {
			message = "添加成功，商品【" + bggoods.getBgGoodsName() + "】在该活动下已存在。\n";
			return buildJson(1, message);
		} else {
			message = "添加成功，商品【" + bggoods.getBgGoodsName() + "】添加成功，添加到" + addNum + "个学校内。\n";
			return buildJson(0, message);
		}
		
	}
	
	
	/**
	 * 查询爆品活动信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryPopularActivity")
	@ResponseBody
	public Object queryPopularActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam long popularActivityId ) {
		
		TemplateActivity templateActivity = activityService.queryTemplateActivityByActivityId(popularActivityId);
		
		List<BgGoodsForActivity> bgGoodsForActivitys = bgGoodsForActivityService.getBgGoodsForActivityByActivityIdAndIsDel(popularActivityId,0);
		BgGoodsForActivity bgGoodsForActivity = null;
		List<BgSkuForActivity> bgSkuForActivitys = null;
		if(bgGoodsForActivitys != null && bgGoodsForActivitys.size() > 0) {
			bgGoodsForActivity = bgGoodsForActivitys.get(0);
			
			//bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsId(bgGoodsForActivity.getActivityBgGoodsId());
			
			bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsIdAndGoodsType(bgGoodsForActivity.getActivityBgGoodsId(),bgGoodsForActivity.getGoodsType());
		}
		
		Map<String,Object> dataMap = Maps.newHashMap();
		dataMap.put("templateActivity", templateActivity);
		dataMap.put("bgGoodsForActivity" ,bgGoodsForActivity);
		dataMap.put("bgSkuForActivitys", bgSkuForActivitys);
		
		return buildJson(0, "", dataMap);
		
	}
	
	/**
	 * 进入展示品牌活动页面
	 * @param request
	 * @param response
	 * @param brandActivityId
	 * @return
	 */
	@RequestMapping("/showBrandActivity")
	@ResponseBody
	public Object showBrandActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam long brandActivityId ) {
		
		List<College> activityCollegeList = activityService.getActivityCollegeList(brandActivityId);
		if(CollectionUtils.isEmpty(activityCollegeList)) {
			return createErrorView("您尚未给该活动设置范围！");
		}
		
		List<ActivityTemplate> activityTemplates = activityTemplateService.getAllActivityTemplate();
		
		TemplateActivity templateActivity = activityService.queryTemplateActivityByActivityId(brandActivityId);
		
		List<Long> bgGoodsIds = changeBgGoodsId(templateActivity);
		
		List<BgGoodsForActivity> bgGoodsForActivitys = bgGoodsForActivityService.getBgGoodsForActivityByActivityIdAndIsDel(brandActivityId,0);
		
		if(CollectionUtils.isNotEmpty(bgGoodsForActivitys)) {
			for(Iterator<BgGoodsForActivity> it = bgGoodsForActivitys.iterator();it.hasNext();) {
				BgGoodsForActivity bgGoodsForActivity = it.next();
				if(!bgGoodsIds.contains(bgGoodsForActivity.getBgGoodsId())) {
					it.remove();
				}
				List<BgSkuForActivity> bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsIdAndGoodsType(bgGoodsForActivity.getActivityBgGoodsId(),bgGoodsForActivity.getGoodsType());
				bgGoodsForActivity.setBgSkuForActivitys(bgSkuForActivitys);
			}
			/*
			for(BgGoodsForActivity bgGoodsForActivity : bgGoodsForActivitys) {
				List<BgSkuForActivity> bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsIdAndGoodsType(bgGoodsForActivity.getActivityBgGoodsId(),bgGoodsForActivity.getGoodsType());
				bgGoodsForActivity.setBgSkuForActivitys(bgSkuForActivitys);
			}
			*/
		}
		
		
		Map<Long,BgGoodsForActivity> bgGoodsMap = Maps.newHashMap();
		if(bgGoodsForActivitys != null) {
			for(BgGoodsForActivity bgGoodsForActivity : bgGoodsForActivitys) {
				bgGoodsMap.put(bgGoodsForActivity.getActivityBgGoodsId(), bgGoodsForActivity);
			}
		}
		
		
		/*
		if(bgGoodsForActivitys != null && templateActivity != null && StringUtils.isNotEmpty(templateActivity.getGoodsIds())) {
			String goodsIds = templateActivity.getGoodsIds();
			for(String eachCategoryGoodsIds : goodsIds.split(";")) {
				for(String eachGoods : eachCategoryGoodsIds.split(",")) {
					bgGoodsMap.put(key, value)
				}
			}
		}
		*/
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("activityTemplates", activityTemplates);
		map.put("activityId", brandActivityId);
		map.put("templateActivity", templateActivity);
		map.put("bgGoodsForActivitys", bgGoodsForActivitys);
		
		return new ModelAndView("activity/addBrandActivityGoods",map);
	}
	
	
	/**
	 * 将品牌活动中的bgGoodsID转换为
	 * @param templateActivity
	 * @return
	 */
	private List<Long> changeBgGoodsId(TemplateActivity templateActivity ) {
		List<Long> bgGoodsIds = Lists.newArrayList();
		if(templateActivity == null || StringUtils.isEmpty(templateActivity.getGoodsIds())) {
			return bgGoodsIds;
		}
		
		String goodsIds = templateActivity.getGoodsIds();
		for(String goodsIdsEachCateogry : goodsIds.split(";")) {
			for(String goodsIdEach : goodsIdsEachCateogry.split(",")) {
				bgGoodsIds.add(Long.parseLong(goodsIdEach.trim()));
			}
		}
		
		return bgGoodsIds;
	}
	
	
	/**
	 * 添加品牌活动商品
	 */
	@RequestMapping("/addActivityBrandGoods")
	@ResponseBody
	public Object addBrandGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityId, //活动ID
			@RequestParam long activityGoodsId,  //商品ID
			@RequestParam String originPrice,  //原价
			@RequestParam String nowPrice,  //活动价
			@RequestParam Integer storageType  //RDC\LDC\第三方卖家商品
			) {
		
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		
		ActivityGoodsApplyBean bean = new ActivityGoodsApplyBean();
		bean.setActivityPrice(nowPrice);
		bean.setOriginPrice(originPrice);
		bean.setBgGoodsId(activityGoodsId);
		//bean.setMaxNum(maxNum);
		//bean.setStock(stock);
		bean.setWeight(0);
		
		
		int addNum = bgGoodsForActivityService.insertBgGoodsForActivity(bean, activityId, storageType,
				user.getUser_id(), null, null, null, Activity.ACTIVITY_TYPE_BRAND);
		BgGoods bggoods = bgGoodsService.getBgGoodsById(bean.getBgGoodsId());
		StringBuffer message = new StringBuffer("");
		if (addNum == -1) {
			return buildJson(1, "添加失败，请先设置活动的范围~");
		} else if (addNum == -2) {
			message.append("添加失败，商品【" + bggoods.getBgGoodsName() + "】和活动的范围不存在交集。\n");
		} else if (addNum == -3) {
			message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】在该活动下已存在。\n");
		} else { //成功
			BgGoodsForActivity bgGoodsForActivity = bgGoodsForActivityService.queryByActivityIdAndBgGoodsId(activityId,activityGoodsId,storageType);
			
			message.append("添加成功，商品【" + bggoods.getBgGoodsName() + "】添加成功，添加到" + addNum + "个学校内。\n");
			return buildJson(0, message.toString(),bgGoodsForActivity.getActivityBgGoodsId());
		}
		
		return buildJson(1, message.toString());
		
	}
	
	
	/**
	 * 保存品牌上单信息，包括商品信息和templateActivity信息
	 */
	@RequestMapping("/saveTemplateActivity")
	public Object saveTemplateActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityId, //活动ID
			@RequestParam String goodsIds,  //商品ID
			@RequestParam int goodsNum,  //每栏目商品数
			@RequestParam(required=false) String activityRule,  //活动规则
			@RequestParam String categoryName,  //栏目名
			@RequestParam long activityTemplateId, //模板ID
			@RequestParam String goodsInfo //商品信息
			
			) {
		
		
		List<ActivityGoodsApplyBean> beanList = gson.fromJson(goodsInfo,
				new TypeToken<List<ActivityGoodsApplyBean>>() {
				}.getType());
		
		bgGoodsForActivityService.delBgGoodsForActivity(activityId);
		
		StringBuffer sb = new StringBuffer("");
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		//long startTime = System.currentTimeMillis();
		for (ActivityGoodsApplyBean bean : beanList) {
			//long startTime1 = System.currentTimeMillis();
			bean.setMaxNum(Integer.MAX_VALUE);
			bean.setStock(Integer.MAX_VALUE);
			if(bean.getSkuListBean() != null) {
				for(ActivitySkuApplyBean skuBean : bean.getSkuListBean()) {
					skuBean.setStock(Integer.MAX_VALUE);
				}
			}
			int addNum = bgGoodsForActivityService.insertBgGoodsForActivity(bean, activityId, Integer.parseInt(bean.getStorageType()),
					user.getUser_id(), null, null, null, Activity.ACTIVITY_TYPE_BRAND);
			if(addNum <= 0) {
				BgGoods bggoods = bgGoodsService.getBgGoodsById(bean.getBgGoodsId());
				sb.append(bggoods.getBgGoodsName()).append(",");
			}
			//System.out.println(System.currentTimeMillis() - startTime1);
		}
		//System.out.println(System.currentTimeMillis() - startTime);
		
		
		TemplateActivity templateActivity = new TemplateActivity();
		templateActivity.setActivityId(activityId);
		templateActivity.setActivityRule(activityRule);
		templateActivity.setActivityTemplateId(activityTemplateId);
		templateActivity.setCategoryNames(categoryName);
		templateActivity.setGoodsIds(goodsIds);
		templateActivity.setGoodsNum(goodsNum);
		activityService.saveOrUpdateTemplateActivity(templateActivity);
		
		
		//把失败的商品信息传递至前台页面
		if(sb.length() > 0) {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		String message = sb.toString();
		String code = (StringUtils.isEmpty(message) ? "0" : "1");  //0表示操作成功 1表示操作失败
		
		/**
		 * 文进行了编码的时候用的是UTF-8的编码方式，而在servlet中调用request.getParameter();
		 * 方法的时候使用服务器指定的编码格式自动解码一次，所以前台编码一次后台解码一次而解码和编码的方式不用所以造成了乱码的出现
		 */
		if(StringUtils.isNotEmpty(message)) {
			try {
				message = URLEncoder.encode(message, "UTF-8");
				message = URLEncoder.encode(message, "UTF-8");
			} catch(Exception ex) {
				log.error("编码参数时发生异常",ex);
				message = "";
			}
		}
		
		return new RedirectView("/activitygoods/showBrandActivity?brandActivityId=" + activityId + "&code=" + code + "&message=" + message ,false);
		
	}
	
	
	/**
	 * 查询品牌活动信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryBrandActivity")
	@ResponseBody
	public Object queryBrandActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam long activityGoodsId ) {
		//TemplateActivity templateActivity = activityService.queryTemplateActivityByActivityId(popularActivityId);
		
		BgGoodsForActivity bgGoodsForActivity = bgGoodsForActivityService.queryBgGoodsForActivity(activityGoodsId);
		
		BgSkuForActivity bgSkuForActivity = null;
		if(bgGoodsForActivity != null) {
			
			List<BgSkuForActivity> bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsId(bgGoodsForActivity.getActivityBgGoodsId());
			if(bgSkuForActivitys != null && bgSkuForActivitys.size() > 0) {
				bgSkuForActivity = bgSkuForActivitys.get(0);
			}
		}
		
		
		Map<String,Object> dataMap = Maps.newHashMap();
		//dataMap.put("templateActivity", templateActivity);
		dataMap.put("bgGoodsForActivity" ,bgGoodsForActivity);
		dataMap.put("bgSkuForActivity", bgSkuForActivity);
		
		return buildJson(0, "", dataMap);
	}
	
	/**
	 * 更新品牌活动信息
	 * 原价、活动价、商品类型
	 * @param request
	 * @param response
	 * @param activityGoodsId
	 * @return
	 *  TODO storageType 不能修改
	 */
	@RequestMapping("/updateBrandActivity")
	@ResponseBody
	public Object updateBrandActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long activityGoodsId,
			@RequestParam String originPrice,  //原价
			@RequestParam String nowPrice,  //活动价
			@RequestParam Integer storageType  //RDC\LDC\第三方卖家商品
			
			) {
		
		List<BgSkuForActivity> bgSkuForActivitys = bgSkuForActivityService.getListByActivityBgGoodsId(activityGoodsId);
		if(bgSkuForActivitys != null && bgSkuForActivitys.size() > 0) {
			BgSkuForActivity bgSkuForActivity = bgSkuForActivitys.get(0);
			long originPriceFen = MoneyUtils.yuan2Fen(Double.parseDouble(originPrice));
			long nowPriceFen = MoneyUtils.yuan2Fen(Double.parseDouble(nowPrice));
			bgSkuForActivityService.updateActivityBgSku(originPriceFen, nowPriceFen,
					bgSkuForActivity.getStock(), bgSkuForActivity.getActivityBgSkuId());
		}
		
		
		return buildJson(0, "修改成功");
		
	}
	
	
	@RequestMapping("/validateGoodsCollegeContainsActivityColleges")
	@ResponseBody
	public Object validateGoodsCollegeContainsActivityColleges(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long bgGoodsId,  //商品ID
			@RequestParam long activityId,  //活动ID
			@RequestParam int storageType  //RDC\LDC\第三方卖家商品
			
			) {
		
		try {
			List<Long> notContainCollegeId = bgGoodsForActivityService.containsAllActivityCollege(bgGoodsId, storageType, activityId);
			if(CollectionUtils.isEmpty(notContainCollegeId)) {
				return buildJson(0, "");
			} else {
				StringBuffer sb = new StringBuffer();
				for(Long collegeId : notContainCollegeId) {
					College college = CollegeConstant.getCollegeById(collegeId);
					sb.append(college == null ? "" : college.getCollegeName()).append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				return buildJson(1, "以下几个学校不在商品售卖之列：" + sb.toString());
			}
		} catch(Exception ex) {
			log.error("校验商品所属学校和活动所属学校集合关系，bgGoodsId " + bgGoodsId + ",activityId " + activityId + ",storageType " + storageType,ex);
			return buildJson(1, ex.getMessage());
		}
		
	}
	
	

}
