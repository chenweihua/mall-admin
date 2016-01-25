package com.mall.admin.web.subscribe;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.subscribe.ISubscribeService;
import com.mall.admin.util._;
import com.mall.admin.vo.subscribe.SubscribeDetail;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController extends BaseController {

	private static final Logger log = LogConstant.mallLog;
	@Autowired
	private ISubscribeService subscribeService;
	
	/**
	 * 广告平台入口
	 * 
	 * @param request
	 * @param response
	 * @param subscribeId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/advertise/list")
	public ModelAndView advertiseList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		return new ModelAndView("subscribe/list");
	}
	
	/**
	 * 活动、信息平台入口
	 * 
	 * @param request
	 * @param response
	 * @param subscribeId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/activity/list")
	public ModelAndView activityList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		return new ModelAndView("subscribe/activityInfolist");
	}
	
	/**
	 * 广告平台查询列表
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @param titleOrHtmlUrl
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/advertise/query")
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage,@RequestParam(required=false) String titleOrHtmlUrl)
			throws SQLException, IOException, ParseException {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("platformType", SubscribeDetail.SUBSCRIBE_ADVERTISE_PLATFORM_TYPE);
		param.put("status", request.getParameter("status"));
		param.put("contentType", request.getParameter("contentType"));
		param.put("titleOrHtmlUrl", titleOrHtmlUrl);
		List<SubscribeDetail> advertiseDetailList = subscribeService.getSubscribeDetailList(param,paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), advertiseDetailList));
	}
	
	
	/**
	 * 平台广告保存
	 * 
	 * @param request
	 * @param response
	 * @param title
	 * @param htmlUrl
	 * @param status
	 * @param weight
	 * @param imgUrl
	 * @param subscribeId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/advertise/save")
	@ResponseBody
	public Object insertEditAdvertise(HttpServletRequest request, HttpServletResponse response, @RequestParam String title,@RequestParam(required=false) String subTitle,
			@RequestParam(required=false) String htmlUrl, @RequestParam String statusEdit,@RequestParam String contentTypeEdit,
			@RequestParam String weight, @RequestParam String imgUrl, @RequestParam(required=false) String subscribeId)
			throws SQLException {

		User loginInfo = (User) request.getAttribute("user");
		try {
			String type = request.getParameter("subscribeType");
			GroupSequence groupSequence = new GroupSequence();
			checkArgument(!_.isEmpty(title) && title.length() < 512, "标题不能为空或者超过512字符");
			if(!"isAdvertise".equals(type)){//广告没有副标题
				checkArgument(!_.isEmpty(subTitle) && subTitle.length() < 512, "副标题不能为空或者超过512字符");
			}
			checkArgument(!_.isEmpty(htmlUrl), "链接不能为空或者超过128字符");
			checkArgument(!_.isEmpty(imgUrl), "图片链接不能为空或者超过128字符");
			int status = _.toInt(statusEdit, 0);
			int weightInt = _.toInt(weight, 0);
			int contentType = _.toInt(contentTypeEdit, 0);
			Date now = new Date();
			SubscribeDetail subscribeDetail = new SubscribeDetail();
			subscribeDetail.setTitle(title);
			subscribeDetail.setHtmlUrl(htmlUrl);
			subscribeDetail.setSubTitle(subTitle);
			subscribeDetail.setStatus(status);
			subscribeDetail.setWeight(weightInt);
			if("isAdvertise".equals(type)) {//广告
				subscribeDetail.setPlatformType(SubscribeDetail.SUBSCRIBE_ADVERTISE_PLATFORM_TYPE);
			}
			else {//活动和信息
				subscribeDetail.setPlatformType(SubscribeDetail.SUBSCRIBE_ACTIVITY_PLATFORM_TYPE);
			}
			subscribeDetail.setContentType(contentType);
			subscribeDetail.setImgUrl(imgUrl);
			subscribeDetail.setOperator(loginInfo.getUser_id());
			subscribeDetail.setUpdateTime(now);
			int affectNum = 0;
			if(StringUtils.isNotBlank(subscribeId)) {// 编辑
				subscribeDetail.setSubscribeId(Long.parseLong(subscribeId));
				affectNum = subscribeService.updateSubscribeDetailById(subscribeDetail);
			}
			else {//新增
				subscribeDetail.setCreateTime(now);
				subscribeDetail.setSubscribeId(groupSequence.nextValue());
				affectNum = subscribeService.insertSubscribeDetail(subscribeDetail);
			}
			if(affectNum == 0) {
				return buildJson(1, "操作失败~");
			}
			return buildJson(0, "操作成功~");
		} catch (Exception e) {
			log.error("insertEditAdvertise.subscribeDetail：", e);
			return buildJson(1, "操作失败：" + e.getMessage());
		}
	}
	
	/**
	 * 点击编辑，查询订阅信息通过ID
	 * 
	 * @param request
	 * @param response
	 * @param subscribeId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/subscribe/edit")
	public Object getAdvertiseDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String subscribeId)
			throws SQLException {
		SubscribeDetail subscribeDetail = null;
		log.info("SubscribeController.getAdvertiseDetail.subscribeId:" + subscribeId);
		try {
			subscribeDetail = subscribeService.selectSubscribeDetailById(Long.parseLong(subscribeId));
			log.info("SubscribeController.getAdvertiseDetail.subscribeDetail:" + subscribeDetail.toString());
		} catch (Exception e) {
			log.error("getAdvertiseDetail：", e);
		}
		return _.asMap("subscribeDetail", subscribeDetail);
	}
	
	/**
	 * 活动，信息查询列表
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @param titleOrHtmlUrl
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/activity/query")
	@ResponseBody
	public Object activitylist(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage,@RequestParam(required=false) String titleOrHtmlUrl)
			throws SQLException, IOException, ParseException {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("platformType", SubscribeDetail.SUBSCRIBE_ACTIVITY_PLATFORM_TYPE);
		param.put("status", request.getParameter("status"));
		param.put("contentType", request.getParameter("contentType"));
		param.put("titleOrHtmlUrl", titleOrHtmlUrl);
		List<SubscribeDetail> advertiseDetailList = subscribeService.getSubscribeDetailList(param,paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), advertiseDetailList));
	}
	
}
