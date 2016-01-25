package com.mall.admin.web.notice;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.notice.NoticeService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.notice.Notice;
import com.mall.admin.vo.notice.NoticeCollege;
import com.mall.admin.vo.user.User;


@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	private static final Logger log = LogConstant.mallLog;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ZtreeUtil ztreeUtil;
	
	
	
	
	@RequestMapping("/list")
	public Object listCouponGive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("notice/list");
		return mav;
		
	}
	
	
	
	@RequestMapping("/query")
	@ResponseBody
	public Object queryCouponGive(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,@RequestParam(value = "length") int numPerPage,
			@RequestParam(required=false) String noticeName,@RequestParam(required=false) String platform,
			@RequestParam(required=false) Integer status,@RequestParam(required=false) Integer state,
			@RequestParam(required=false) Integer cityId,@RequestParam(required=false) Integer collegeId) {
		
		if(state == null) {  //不提供查询所有的数据，只能查询生效或失效状态的，默认是生效的
			state = 1;
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String,Object> param = Maps.newHashMap();
		param.put("noticeName", noticeName);
		param.put("platform", platform);
		param.put("status", status);
		param.put("state", state);
		if(cityId != null && cityId != -1) {
			param.put("cityId", cityId);
		}
		if(collegeId != null && collegeId != -1) {
			param.put("collegeId", collegeId);
		}
		List<Notice> noticeList = noticeService.getNoticeList(param, paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), noticeList));
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam String platform,@RequestParam String noticeName,@RequestParam String content,@RequestParam(required=false) String htmlUrl,
			@RequestParam Integer position, @RequestParam Integer openType,@RequestParam(required=false) Integer status,
			@RequestParam(required=false) String startDate, @RequestParam(required=false) String endDate, 
			@RequestParam(required=false) Long noticeId) {

		try {
			
			User loginInfo = (User) request.getAttribute("user");
			String account = loginInfo.getAccount();
			
			checkArgument(!_.isEmpty(noticeName) && noticeName.length() <= 100, "通告名称不能为空或者超过100字符");
			checkArgument(!_.isEmpty(content) && content.length() <= 256, "通告内容不能为空或者超过256字符");
			checkArgument(!_.isEmpty(htmlUrl) && htmlUrl.length() <= 256, "链接不能为空或者超过256字符");
			checkArgument(!_.isEmpty(platform), "平台不能为空");
			checkArgument(position != null, "展示页面不能为空");
			if(openType == Notice.OPEN_TYPE_MANUAL) {  //手动
				
				checkArgument(status != null,"手动控制时状态不能为空");
				
			} else if(openType == Notice.OPEN_TYPE_AUTO) {  //自动
				
				checkArgument(!_.isEmpty(startDate) && !_.isEmpty(endDate),"自动控制时开始结束时间不能为空");
				
			}
			Date startTime = _.toDate(startDate);
			Date endTime = _.toDate(endDate);
			
			Notice notice = new Notice();
			notice.setNoticeName(noticeName);
			notice.setContent(content);
			notice.setHtmlUrl(htmlUrl);
			notice.setPlatform(platform);
			notice.setPosition(position);
			notice.setOpenType(openType);
			notice.setStartTime(startTime);
			notice.setEndTime(endTime);
			notice.setStatus(status);
			
			int affectNum = 0;
			if(noticeId == null) {  // 添加
				GroupSequence groupSequence = new GroupSequence();
				notice.setNoticeId(groupSequence.nextValue());
				notice.setCreateTime(new Date());
				affectNum = noticeService.addNotice(notice,account);
			} else {  //修改
				notice.setNoticeId(noticeId);
				notice.setUpdateTime(new Date());
				affectNum = noticeService.updateNotice(notice,account);
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
	
	@RequestMapping("/getCityCollege")
	@ResponseBody
	public Object getCityCollege(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		
		List<City> cityList = Lists.newArrayList(); 
		List<District> districtList = cityService.getDistrictList();
		for(District district: districtList) {
			cityList.add(district.getCity());
		}
		Map<Long, List<College>> collegeMap = Maps.newHashMap();
		for (City city : cityList) {
			collegeMap.put(city.getCityId(), CollegeConstant.getCollegesByCityId(city.getCityId()));
		}
		result.put("citiList", cityList);
		result.put("collegeMap", collegeMap);
		return result;
	}
	
	
	@RequestMapping("/getNoticeCollege")
	@ResponseBody
	public Object getNoticeCollege(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String noticeId) {

		try {
			long noticeIdLong = _.toLong(noticeId);
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkState(user != null, "请先登录");

			
			List<NoticeCollege> noticeColleges = noticeService.getNoticeCollegeByNoticeId(noticeIdLong);
			//List<Long> collegeIds = Lists.transform(noticeColleges, s -> s.getCollegeId());
			List<Long> collegeIds = new ArrayList<Long>();
			for(NoticeCollege noticeCollege : noticeColleges) {
				collegeIds.add(noticeCollege.getCollegeId());
			}
			
			ZtreeBean ztreeBean = ztreeUtil.getCityAreaCollegeZtreeWithoutEmptyCity(collegeIds);
			return buildJson(0, "通知与学校关联查询成功", ztreeBean);
		} catch (Exception e) {
			log.error("通知与学校关联查询发生异常",e);
			return buildErrJson(e.getMessage());
		}

	}

	@RequestMapping("/setNoticeCollege")
	@ResponseBody
	public Object setNoticeCollege(HttpServletRequest request, HttpServletResponse response) {
		String collegeIdStr = request.getParameter("collegeIds");
		String noticeIdStr = request.getParameter("noticeId");

		checkArgument(StringUtils.isNotEmpty(collegeIdStr), "没有选中学校节点");
		checkArgument(StringUtils.isNotEmpty(noticeIdStr), "没有通知ID");
		
		try {
			User loginInfo = (User) request.getAttribute("user");
			String account = loginInfo.getAccount();
			Long noticeId = Long.parseLong(noticeIdStr);
			String[] collegeIdArr = collegeIdStr.split(",");
			//List<Long> collegeIds = Lists.transform(Arrays.asList(collegeIdArr), s -> Long.parseLong(s));
			List<Long> collegeIds = new ArrayList<Long>();
			for(String collegeIdStrEach : collegeIdArr) {
				collegeIds.add(Long.parseLong(collegeIdStrEach));
			}
			noticeService.updateNoticeColleges(noticeId, collegeIds,account);
			return buildJson(0, "设置通知学校关联操作成功~");
		} catch (Exception e) {
			log.error("设置通知学校关联操作发生异常",e);
			LogConstant.mallLog.error(_.f("设置通知学校关联操作失败，通知[%s],学校[%s]", noticeIdStr, collegeIdStr), e);
			return buildErrJson("设置通知学校关联操作失败," + e.getMessage());
		}
	}
	

}
