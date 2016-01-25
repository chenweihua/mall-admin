package com.mall.admin.web.ump.coupon;

import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.DictionaryConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.enumdata.CouponRestrictEnum;
import com.mall.admin.enumdata.CouponStatus;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.dao.ump.base.SequenceException;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.order.OrderService;
import com.mall.admin.service.ump.coupon.CouponService;
import com.mall.admin.service.ump.uc.UcUserService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util.CollectionUtil;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.util.DictionaryUtil;
import com.mall.admin.util.FileUtils;
import com.mall.admin.util.OssUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.ump.Coupon;
import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.ump.CouponGive;
import com.mall.admin.vo.ump.CouponGiveLog;
import com.mall.admin.vo.ump.CouponRewardsInfo;
import com.mall.admin.vo.ump.UcUser;
import com.mall.admin.vo.user.User;


@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
	
	private static final String FILE_STORE_PATH = DictionaryUtil.getValueByTypeIdAndKey(3L, "COUPON_CSV_FILE_PATH");
	//private static final String FILE_STORE_PATH = "e:/path/";
	
	/**
	 * 默认的一次性导入csv文件的手机号码最大数量
	 */
	private static final int DEFAULT_MAX_PHONENO_NUM_PER_FILE = 50000;
	
	
	/**
	 * 默认的选择区域发送优惠券的一次性选择区域最多数量（以第二层区域数量为准）
	 */
	private static final int DEFAULT_MAX_AREA_NUM = 5;
	
	//23:59:59
	private static final int HOUR=23;
	private static final int MINUTE=59;
	private static final int SECOND=59;
	
	
	/**
	 * 主动发放优惠券上传手机号文件存放在阿里OSS Bucket和路径
	 */
	private static final String COUPON_GIVE_PHONENO_FILE_ALI_OSS_BUCKET = "h5-hongbao";
	private static final String COUPON_GIVE_PHONENO_FILE_ALI_OSS_PATH = "csv/";
	
	
	private static final Logger logger = LogConstant.mallLog;
	
	private static Executor executor = Executors.newSingleThreadExecutor();
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private ZtreeUtil ztreeUtil;
	
	GroupSequence groupSequence;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private UcUserService ucUserService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CityService cityService;
	
	/**
	 * 进入优惠券发放页面
	 * 输入：优惠券批次号  couponBatchId
	 * 输出：1.该批次号的发放页面
	         2.不允许发放提示页面
	 */     
	@RequestMapping("/give")
	public Object give(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Long couponBatchId = Long.parseLong(request.getParameter("couponBatchId"));
		CouponGive couponGive = new CouponGive();
		couponGive.setCouponBatchId(couponBatchId);
		request.setAttribute("couponGive", couponGive);
		
		//允许一次性最多选择多少个二级区域
		int allowAreaNum = getMaxAreaNumPer();
		request.setAttribute("allowAreaNum", allowAreaNum);
		
		CouponBatch couponBatch = couponService.queryCouponBatch(couponBatchId);
		if(couponBatch.getStatus() != CouponBatch.STATUS_NEW) {
			return createErrorView("该批次" + couponBatchId + "状态为" + couponBatch.getStatus() + "不允许发送");
		}
		Date nowDate = new Date();
		if(couponBatch.getEndTime().before(nowDate)) {
			return createErrorView("该批次" + couponBatchId + "当前时间不在有效期中，不允许发送");
		}
		
		
		return new ModelAndView("ump/coupon/give");

	}
	
	
	@RequestMapping("/give/list")
	public Object listCouponGive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ump/coupon/giveList");
		return mav;
		
	}
	
	
	@RequestMapping("/give/viewCouponGiveLog")
	public Object viewCouponGiveLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String couponGiveId =  request.getParameter("couponGiveId");
		request.setAttribute("couponGiveId", couponGiveId);
		ModelAndView mav = new ModelAndView("ump/coupon/giveLogList");
		return mav;
		
	}
	
	
	@RequestMapping("/give/query")
	@ResponseBody
	public Object queryCouponGive(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String,String> param = new HashMap<String,String>();
		param.put("coupon_batch_id", searchStr);
		List<CouponGive> couponGiveList = couponService.getCouponGiveList(param,paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), couponGiveList));
	}
	
	@RequestMapping("/give/queryLog")
	@ResponseBody
	public Object queryCouponGiveLog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String,String> param = new HashMap<String,String>();
		param.put("phoneno", searchStr);
		param.put("couponGiveId", request.getParameter("couponGiveId"));
		List<CouponGiveLog> couponGiveLogList = couponService.getCouponGiveLogList(param,paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), couponGiveLogList));
	}
	
	
	
	@RequestMapping("/give/del")
	@ResponseBody
	public Object deleteCouponGive(HttpServletRequest request, HttpServletResponse response) {
		String couponGiveId = request.getParameter("couponGiveId");
		if (Strings.isEmpty(couponGiveId)) {
			return buildJson(1, "作废操作失败，ID为空~");
		}
		
		CouponGive couponGive = couponService.queryGouponGive(Long.parseLong(couponGiveId));
		if (couponGive == null) {
			return buildJson(1, "作废操作失败，优惠券发放记录不存在~");
		}
		if (!CouponGive.SAVED.equals(couponGive.getStatus()) && !CouponGive.FAIL_VALID.equals(couponGive.getStatus()) && !CouponGive.FAIL_HANDLE.equals(couponGive.getStatus())) {
			return buildJson(1, "作废操作失败，该优惠券发放记录不能作废~");
		}
		try {
			couponService.delCouponGive(Long.parseLong(couponGiveId));
		} catch(Exception ex) {
			logger.error("",ex);
			return buildJson(1, "作废操作失败~");
		}
		return buildJson(0, "作废操作成功~");
	}
	
	
	@RequestMapping("/give/enable")
	@ResponseBody
	public Object enableCouponGive(HttpServletRequest request, HttpServletResponse response) {
		String couponGiveId = request.getParameter("couponGiveId");
		if (Strings.isEmpty(couponGiveId)) {
			return buildJson(1, "激活失败，ID为空~");
		}
		
		CouponGive couponGive = couponService.queryGouponGive(Long.parseLong(couponGiveId));
		if (couponGive == null) {
			return buildJson(1, "激活失败，优惠券发放记录不存在~");
		}
		if (!CouponGive.SAVED.equals(couponGive.getStatus())) {
			return buildJson(1, "激活失败，该优惠券发放记录不能激活~");
		}
		
		boolean enableSuccess = couponService.updateCouponGiveStatus(Long.parseLong(couponGiveId), CouponGive.SAVED, CouponGive.WAITING_HANDLE, null);
		if(enableSuccess) {
			executor.execute(new CouponGiveHandler(couponGive.getId()));
		}
		return buildJson(0, "开始激活中~~~");
	}
	
	
	
	@RequestMapping("/give/view")
	public Object viewCouponGive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String couponGiveId = request.getParameter("couponGiveId");
		if (Strings.isEmpty(couponGiveId)) {
			return createErrorView("操作失败，ID为空~");
		}
		
		CouponGive couponGive = couponService.queryGouponGive(Long.parseLong(couponGiveId));
		if (couponGive == null) {
			return createErrorView("操作失败，优惠券发放记录不存在~");
		}
		request.setAttribute("couponGive", couponGive);
		if(CouponGive.GIVE_WAY_PHONENO.equals(couponGive.getGiveWay())) { 
			request.setAttribute("orginFileName", couponGive.getFileName().substring(couponGive.getFileName().indexOf("_") + 1));
		}
		

		return new ModelAndView("ump/coupon/give");
	}
	
	
	
	/**
	 *  展示省-市（直辖市-区）这两个层级的树状态选择器
	 */
	@ResponseBody
	@RequestMapping("/getCityArea")
	public Object getCityArea(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		List<Long> areaIdList = new ArrayList<Long>();
		String couponGiveId = request.getParameter("couponGiveId");
		if(StringUtils.isNotEmpty(couponGiveId)) {
			
			CouponGive couponGive  = couponService.queryGouponGive(Long.parseLong(couponGiveId));
			if(CouponGive.GIVE_WAY_AREA.equals(couponGive.getGiveWay())) {
				List<String> areaIdStrList = Arrays.asList(couponGive.getAreaId().split(","));
				for(String areaIdStr : areaIdStrList) {
					areaIdList.add(Long.parseLong(areaIdStr));
				}
			}
		}
		
		ZtreeBean ztreeBean = ztreeUtil.getCityAreaZtree(areaIdList,StringUtils.isEmpty(couponGiveId));
		return buildSuccJson(ztreeBean);
	}
	
	
	/**
	 * 异步校验并处理优惠券发送操作，并将结果放入status和msg字段
	 *
	 */
    class CouponGiveHandler implements Runnable {
    	
    	private Long couponGiveId;
		
    	CouponGiveHandler(){}
    	
    	CouponGiveHandler(Long couponGiveId) {
    		this.couponGiveId = couponGiveId;
    	}
		
		@Override
		public void run() {
			logger.error("couponGiveDeamonThread started couponGiveId:" + couponGiveId);
			boolean inHandleSuccess = couponService.updateCouponGiveStatus(couponGiveId, CouponGive.WAITING_HANDLE, CouponGive.IN_HANDLE, null);
			if(!inHandleSuccess) {
				logger.error("couponGiveId:" + couponGiveId + "更新状态为" + CouponGive.IN_HANDLE + "失败");
				return;
			}
			CouponGive couponGive = couponService.queryGouponGive(couponGiveId);
			String userType = couponGive.getUserType();
			Long couponBatchId = couponGive.getCouponBatchId();
			String giveWay = couponGive.getGiveWay();
			
			List<Pair<Long,Long>> phoneNoUserIdList = new ArrayList<Pair<Long,Long>>();
			try {
				
				if(CouponGive.GIVE_WAY_PHONENO.equals(giveWay)) {  //根据手机号发送
				
					//读取csv文件获得手机号  -----稍后需考虑占内存多大
					InputStream input = findCouponGivePhoneNoFileInputStream(couponGive.getFileName());
					List<String> phoneNos = FileUtils.readCsvForSingleLine(input);
					
					
					//根据手机号查询对应的userId
					phoneNoUserIdList = queryUserIdByPhoneNo(phoneNos);
					
					logger.error("couponGiveDeamonThread giveWay phoneNo size " + phoneNoUserIdList.size()  + ",couponGiveId:" +  couponGiveId);
					
				} else if(CouponGive.GIVE_WAY_AREA.equals(giveWay)){ //根据区域发送
					
					phoneNoUserIdList = queryPhoneNoAndUserIdByAreaId(couponGive.getAreaId().split(","));
					
					logger.error("couponGiveDeamonThread giveWay area size " + phoneNoUserIdList.size()  + ",couponGiveId:" +  couponGiveId);
					
				} else if(CouponGive.GIVE_WAY_ALL.equals(giveWay)) {  //全部发送 暂不做
					
					return;
					//phoneNoUserIdList = queryAllPhoneNoAndUserId();
					
				}
				
				
				//过滤掉用户类型不符合的
				if(!CouponGive.USER_TYPE_ALL.equals(userType)) {
					for(Iterator<Pair<Long,Long>> it = phoneNoUserIdList.iterator();it.hasNext();) {
						Pair<Long,Long> phoneNoAndUserId = it.next();
						if((CouponGive.USER_TYPE_NEW.equals(userType) && isOldUserType(phoneNoAndUserId.getRight()))
								|| (CouponGive.USER_TYPE_OLD.equals(userType) && isNewUserType(phoneNoAndUserId.getRight()))) {
							it.remove();
						}
					}
				}
				
				
				//校验该批次里的优惠券的张数是否大于等于人数
//				boolean numValidation = validBatchAndUserNum(couponBatchId,phoneNoUserIdList.size());
				
				//获取优惠券张数
				CouponBatch couponBatch = couponService.queryCouponBatch(couponBatchId);
				if( couponBatch.getTotalNumber() < phoneNoUserIdList.size()) {
					throw new Exception("该批次中优惠券的张数不足以发放" + phoneNoUserIdList.size() + "个用户");
				}
				
			} catch(Exception ex) {
				logger.error("校验CouponGive：" + couponGive.getId() + "失败",ex);
				failCouponGive(couponGive.getId(),ex.getMessage(),CouponGive.FAIL_VALID);
				return;				
			}
			
			logger.error("couponGiveDeamonThread start sending couponGiveId:" + couponGiveId);
			
			//给用户发送优惠券，给这些用户该批次内的各种面额各一张
			try {
				couponService.sendCouponBatch(couponGive,phoneNoUserIdList);
			} catch(Exception ex) {
				logger.error("发送优惠券操作couponGiveId:" + couponGiveId + "发生异常",ex);
				failCouponGive(couponGive.getId(),ex.getMessage(),CouponGive.FAIL_HANDLE);
				return;
			}
			couponService.sendMsgBatchAfterSending(couponGiveId);
			
			logger.error("couponGiveDeamonThread end sending couponGiveId:" + couponGiveId);
			
			boolean handleSuccess = couponService.updateCouponGiveStatus(couponGiveId, CouponGive.IN_HANDLE, CouponGive.SUCCESS_HANDLE,null);
			if(!handleSuccess) {
				logger.error("couponGiveId:" + couponGiveId + "更新状态为" + CouponGive.SUCCESS_HANDLE + "失败");
				return;
			}
			
			logger.error("couponGiveDeamonThread ended couponGiveId:" + couponGiveId);
		}
	}
    
    
    
    @RequestMapping("/give/downloadPhoneNoFile")
	public Object downloadPhoneNoFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String couponGiveId = request.getParameter("couponGiveId");
		if (Strings.isEmpty(couponGiveId)) {
			throw new Exception("ID为空~");
		}
		
		CouponGive couponGive = couponService.queryGouponGive(Long.parseLong(couponGiveId));
		if (couponGive == null) {
			throw new Exception("优惠券发放记录不存在~");
		}
		
		String fileName = couponGive.getFileName().substring(couponGive.getFileName().indexOf("_") + 1);
		InputStream input = null;
		try {
			input = findCouponGivePhoneNoFileInputStream(couponGive.getFileName());
			byte[] buffer = IOUtils.toByteArray(input);
	        //File file=new File(FILE_STORE_PATH + couponGive.getFileName());
	        HttpHeaders headers = new HttpHeaders(); 
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"),"ISO8859-1"));   
	        return new ResponseEntity<byte[]>(buffer,headers, HttpStatus.CREATED);  
		} catch(Exception ex) {
			logger.error("",ex);
			return createErrorView("下载文件失败,失败原因:" + ex.getMessage());
		} finally {
			if(input != null) {
				input.close();
			}
		}
		
    }
    
	
	
	/**
	 * 主动发送优惠券
	 */
	@RequestMapping("/giveSubmit")
	public Object giveSubmit(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "phoneNoFile", required = false) MultipartFile file, Exception ex) throws Exception {
		final Long couponBatchId = Long.parseLong(request.getParameter("couponBatchId"));
		//优惠券批次中后来改为一个批次只有一种面额，该字段失去校验意义,这里随便输入一个最大整形值，暂时无任何作用
		//Integer receiveLimit = Integer.parseInt(request.getParameter("receiveLimit"));
		Integer receiveLimit = 0;
		String userType = request.getParameter("userType");
		if(!CouponGive.USER_TYPE_ALL.equals(userType) && !CouponGive.USER_TYPE_NEW.equals(userType) && !CouponGive.USER_TYPE_OLD.equals(userType)) {
			return createErrorView("用户类型输入项有误" + userType);
		}
		String giveWay = request.getParameter("giveWay");
		if(!CouponGive.GIVE_WAY_PHONENO.equals(giveWay) && !CouponGive.GIVE_WAY_AREA.equals(giveWay) /*&& !CouponGive.GIVE_WAY_ALL.equals(giveWay)*/) {
			return createErrorView("发券方式输入项有误" + giveWay);
		}		
		
		
		//发送记录
		CouponGive couponGive = new CouponGive();
		couponGive.setId(new GroupSequence().nextValue());
		couponGive.setCouponBatchId(couponBatchId);
		couponGive.setReceiveLimit(receiveLimit);
		couponGive.setUserType(userType);
		couponGive.setGiveWay(giveWay);
		couponGive.setStatus(CouponGive.SAVED);
		couponGive.setCreator(getNowLoginedUserId(request));
		couponGive.setCreateTime(new Date());
		couponGive.setUpdater(getNowLoginedUserId(request));
		couponGive.setUpdateTime(new Date());
		
		
		
		if(CouponGive.GIVE_WAY_PHONENO.equals(giveWay)) {  //根据手机号发送
			
			String errorMsg = preValidateInPutCsvPhoneNoFile(file.getInputStream());
			if(StringUtils.isNotEmpty(errorMsg)) {
				return createErrorView("校验上传文件格式不通过，具体原因是：" + errorMsg);
			}
			
			//备份
			String storeFileName = CommonUtil.genUUID()  + "_" + file.getOriginalFilename();
			//file.transferTo(new File(FILE_STORE_PATH + storeFileName));
			try {
				saveCouponGivePhoneNoFile(storeFileName,file.getInputStream());
			} catch(Exception e) {
				logger.error("上传文件时发生异常,storeFileName:" + storeFileName,e);
				return createErrorView("上传文件操作失败，原因：" + e.getMessage());
			}
			
			
			couponGive.setFileName(storeFileName);
				
				
			
		} else if(CouponGive.GIVE_WAY_AREA.equals(giveWay)){ //根据区域发送
			
			String checkAreaId = request.getParameter("checkAreaId");
			if(StringUtils.isEmpty(checkAreaId)) {
				return createErrorView("没有选择区域");
			}
			
			checkAreaId = filterOutProvinceArea(checkAreaId);
			
			String errorMsg = preValidateCheckedAreaId(checkAreaId);
			if(StringUtils.isNotEmpty(errorMsg)) {
				return createErrorView("校验区域不通过，具体原因：" + errorMsg);
			}
			
			
			couponGive.setAreaId(checkAreaId);
			
			
		} else if(CouponGive.GIVE_WAY_ALL.equals(giveWay)) {  //全部发送
			
			//do nothing
			
		}
		
		try {
			couponService.addCouponGive(couponGive);
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			return createErrorView(e.getMessage());
		}
		
		
		/*
		final Long couponGiveId = couponGive.getId();
		final InputStream in = new FileInputStream(new File(FILE_STORE_PATH + couponGive.getFileName()));
		executor.execute(new Runnable(){
			
			@Override
			public void run() {
				
				couponService.addCouponGiveLogFromFile(in, couponBatchId, couponGiveId);
            	
				
			}
			
		});
		*/
		
		return new RedirectView("/coupon/give/list",false);
		//return new ModelAndView("redirect:/coupon/give/list");
		//return new ModelAndView("ump/coupon/giveList");

	}
	
	
	/**
	 * 保存主动发送优惠券时上传的CSV文件
	 */
	private void saveCouponGivePhoneNoFile(String storeFileName, InputStream content) throws Exception {
		OssUtil.putObject(COUPON_GIVE_PHONENO_FILE_ALI_OSS_BUCKET, COUPON_GIVE_PHONENO_FILE_ALI_OSS_PATH + storeFileName, content);
	}
	
	
	private InputStream findCouponGivePhoneNoFileInputStream(String fileName) throws Exception {
		return OssUtil.getObject(COUPON_GIVE_PHONENO_FILE_ALI_OSS_BUCKET, COUPON_GIVE_PHONENO_FILE_ALI_OSS_PATH + fileName);
	}
	
	
	/**
	 * 校验用户上传的手机号csv文件
	 * 校验点：
	 * 1.每行都是11位的数字，且以1开头
	 * 2.行数不大于允许行数
	 * @param in
	 * @return
	 */
	public String preValidateInPutCsvPhoneNoFile(InputStream in){
		
		final int PHONE_NUM_LENGTH = 11; //手机号码位数
		
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in,"GBK"));
			
			String content = null;
			int rowNum = 1;
			int allowNum = getMaxPhoneNoNumPerFile();
			while((content = reader.readLine())!=null){
				
				if(rowNum > allowNum) {
					return "一个号码文件最多允许导入" + allowNum + "个号码！";
				}
				
				content = content.trim();
				if(content.length() != PHONE_NUM_LENGTH || !content.startsWith("1")) {
					return "第" + rowNum + "行非正确号码格式，请核查！";
				}
				
				rowNum++;
			}
			
		} catch (Exception e) {
			logger.error("预校验号码文件时发生异常",e);
			return null;
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (Exception e) {
				logger.error("预校验号码文件关闭文件流时发生异常",e);
			}
		}
		
		return null;
	}
	
	
	/**
	 * 取得导入的号码csv文件最大允许多少个号码
	 * @return
	 */
	private int getMaxPhoneNoNumPerFile() {
		String num = DictionaryUtil.getValueByTypeIdAndKey(DictionaryConstant.COUPON_SEND_TYPE_ID, "MAX_PHONENO_NUM_PER_FILE");
		try {
			return Integer.parseInt(num);
		} catch(Exception ex) {
			return DEFAULT_MAX_PHONENO_NUM_PER_FILE;
		}
		
	}
	
	
	/**
	 * 取得选择区域最多可以一次选中多少个
	 * @return
	 */
	private int getMaxAreaNumPer() {
		
		
		String num = DictionaryUtil.getValueByTypeIdAndKey(DictionaryConstant.COUPON_SEND_TYPE_ID, "MAX_AREA_NUM");
		try {
			return Integer.parseInt(num);
		} catch(Exception ex) {
			return DEFAULT_MAX_AREA_NUM;
		}
		
	}
	
	
	
	/**
	 * 校验
	 * 校验点：
	 * 1.一次性选择区域个数不大于预定义的个数
	 */
	private String preValidateCheckedAreaId(String checkAreaId) {
		
		int allowNum = getMaxAreaNumPer();
		int currentNum = checkAreaId.split(",").length;
		if(currentNum > allowNum) {
			return "一次最多允许选择区级区域" + allowNum + "个，您现在选择了" + currentNum + "个。";
		}
		return "";
	}
	
	
	/**
	 *  过滤去掉市一级的节点，只留下区一级的节点
	 */
	private String filterOutProvinceArea(String checkAreaId) {
		
		
		String[] areaIdArr = checkAreaId.split(",");
		List<String> areaIdList = Arrays.asList(areaIdArr);
		
		CollectionUtils.filter(areaIdList, new Predicate(){
			
			@Override
			public boolean evaluate(Object object) {
				String areaId = (String)object;
				City city = CityConstant.getcityMap().get(Long.parseLong(areaId));
				if(city.getLevel() != 2L) {
					return false;
				}
				return true;
			}
			
		});
		
		return CollectionUtil.join(areaIdList, ",");
		
	}
	
	
	
	private void failCouponGive(Long couponGiveId,String msg,String status) {
		boolean recordCouponGiveFailSuccess = couponService.updateCouponGiveStatus(couponGiveId, CouponGive.IN_HANDLE, status, msg);
		if(!recordCouponGiveFailSuccess) {
			logger.error("couponGiveId为" + couponGiveId + ",msg" + msg + "更改状态为处理失败状态操作失败");
		}
	}
	
	
	/**
	 * 一次性根据手机号查询对应的userId  ---uc接口
	 * @param phoneNos
	 * @return
	 */
	private List<Pair<Long,Long>> queryUserIdByPhoneNo(List<String> phoneNos) throws Exception {
		
		try {
			List<UcUser> ucUsers = ucUserService.getUcUsersByPhoneNos(phoneNos);
			
			List<Pair<Long,Long>> list = new ArrayList<Pair<Long,Long>>(ucUsers == null ? 0 : ucUsers.size());
			if(ucUsers != null) {
				for(UcUser user : ucUsers) {
					if(StringUtils.isNotEmpty(user.getMobilephone()) && user.getId() != null) {
						list.add(new ImmutablePair<Long,Long>(Long.parseLong(user.getMobilephone()),user.getId().longValue()));
					}
				}
			}
			return list;
		} catch(Exception ex) {
			logger.error("",ex);
			throw new Exception("根据手机号码查询对应的USERID操作失败");
		}
		
	}
	
	
	/**
	 * 根据区域ID，查询对应的所有用户的Id和手机号   ---uc接口
	 * @return
	 */
	private List<Pair<Long,Long>> queryPhoneNoAndUserIdByAreaId(String[] areaIds) throws Exception {
		
		List<Long> areaIdList = new ArrayList<Long>();
		if(areaIds != null) {
			for(String areaId : areaIds) {
				City city = cityService.getCityById(Long.parseLong(areaId));
				if(city == null) {
					throw new Exception("根据" + areaId + "没有查找到对应的区域配置，不能进行发送操作！");
				}
				if(city.getLevel() != 2) {
					throw new Exception("根据" + areaId + "，" + city.getCityName() + "查找到的区域数据不合法！");
				}
				if(city.getUcId() == null) {
					throw new Exception(city.getCityName() + "没有对应的UC ID配置，不能进行发送操作！");
				}
				areaIdList.add(city.getUcId());
			}
		}
		
		
		
		List<Pair<Long,Long>> list = new ArrayList<Pair<Long,Long>>();
		try {
			List<UcUser> users = ucUserService.getUcUserByAreaIds(areaIdList);
			if(users != null) {
				for(UcUser user : users) {
					list.add(new ImmutablePair<Long, Long>(Long.parseLong(user.getMobilephone()), user.getId().longValue()));
				}
			}
			return list;
		} catch(Exception ex) {
			logger.error("",ex);
			throw new Exception("根据区域ID查询对应的USERID和手机号操作失败");
		}
		
		/*
		List<Pair<Long,Long>> list = new ArrayList<Pair<Long,Long>>();
		list.add(new ImmutablePair<Long,Long>(18701126352L,345777L));
		list.add(new ImmutablePair<Long,Long>(18810691728L,4535344L));
		return list;
		*/
	}
	
	
	
	/**
	 * 查询所有手机号及其对应的userId   ---废弃
	 * @return
	 */
	private List<Pair<Long,Long>> queryAllPhoneNoAndUserId() {
		
		return null;
		
	}
	
	
	
	/**
	 * 校验批次内的每种面额的数量都必须大于等于用户数，否则不允许发送
	 * @return 如果校验通过返回null，否则返回失败提示信息
	 */
	private boolean validBatchAndUserNum(Long batchId,int userNum) {
		
		/*
		String msg = "";
		
		Map<Long,Integer> moneyNum = couponService.queryMoneyNum(batchId);
		for(Long money : moneyNum.keySet()) {
			if(moneyNum.get(money) < userNum) {
				msg = String.format("批次%s中面额为%s的数量为%s张，少于要发送的人数%s", batchId, money, moneyNum.get(money), userNum);
				break;
			}
		}
		
		return msg;
		*/
		
		
		CouponBatch couponBatch = couponService.queryCouponBatch(batchId);
		return couponBatch.getTotalNumber() >= userNum;
		
	}
	
	
	
	/**
	 * 是新用户
	 * 
	 */
	private boolean isNewUserType(Long userId) {
		return orderService.isFirstOrderUser(userId);
	}
	
	/**
	 * 是老用户
	 */
	private boolean isOldUserType(Long userId) {
		return !isNewUserType(userId);
	}
	
	
	
	/**
	 * 创建优惠页面
	 * @param weddingId
	 * 优惠劵新增、修改保存
	 * @return
	 */
	@RequestMapping(value ="/saveCoupon")
	@ResponseBody
	public Object couponAdd(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam String operation, 
			@RequestParam Long addCouponId, 
			@RequestParam String addCouponName, 
			@RequestParam int addPlatFormType, 
			@RequestParam Long addCouponTotalMoney, 
			@RequestParam Long addCouponMoney, 
			@RequestParam int addCouponCount, 
			@RequestParam String addStartDate,
			@RequestParam String addEndDate,
			@RequestParam String addCouponDesc
			){
		User loginInfo = (User)request.getAttribute("user");
		Map<String,String> map = Maps.newHashMap();
		try {
			checkState(loginInfo != null, "请先登录");
			/*String [] strCouponMoneys = request.getParameterValues("addCouponMoney");
			String [] strCouponCouns=request.getParameterValues("addCouponCount");*/
			if(addCouponId==null||
				addCouponName==null||
				addCouponMoney==null||
				addCouponTotalMoney==null||
				addStartDate==null||
				addEndDate==null||
				addCouponDesc==null||
				addCouponCount==0
			  ){
				return buildErrJson("必填项不能为空");
			}
			if(addCouponDesc.indexOf(",")>0){
				return buildErrJson("预览相不能出现逗号");
			}
			
			//多种面额的情况
			/*Long couponTotalMoney=0L;
			int couponTotalCount=0;
			Map<String,Object> relationMap = Maps.newHashMap();
			List<Map<String,Object>> list = Lists.newArrayList();
			for(int i=0;i<strCouponMoneys.length;i++){
				if(StringUtils.isNotBlank(strCouponMoneys[i])&&StringUtils.isNotBlank(strCouponCouns[i])){
					couponTotalMoney+=Integer.parseInt(strCouponMoneys[i])*Integer.parseInt(strCouponCouns[i]);
					couponTotalCount+=Integer.parseInt(strCouponCouns[i]);
					relationMap.put("money", Integer.parseInt(strCouponMoneys[i]));
					relationMap.put("totalNumber", Integer.parseInt(strCouponCouns[i]));
					list.add(relationMap);
				}
			}*/
			if(!addCouponTotalMoney.equals(addCouponCount*addCouponMoney)){
				return buildErrJson("总金额和面值总额不相等");
			}
			
	        Date startTime = DateUtil.formatDateToDate(addStartDate);
	        Date endTime =  DateUtil.getTimeChangeHMS(addEndDate,HOUR,MINUTE,SECOND);
	        CouponBatch couponBatch = new CouponBatch();
	        couponBatch.setBatchId(addCouponId);
	        couponBatch.setBatchName(addCouponName);
	        couponBatch.setPlatformType(addPlatFormType);
	        couponBatch.setTotalMoney(addCouponTotalMoney*Constants.YUAN_FEN_CHANGE);
	        couponBatch.setTotalNumber(addCouponCount);
	        couponBatch.setStartTime(startTime);
	        couponBatch.setEndTime(endTime);
	        couponBatch.setMoney(addCouponMoney*Constants.YUAN_FEN_CHANGE);
	        Date date = new Date();
	        String []checkItems = request.getParameterValues("checkItem");
	        Map<String,String> featureMap = Maps.newHashMap();
	        Integer batchType=0;
	        if(checkItems!=null&&checkItems.length>0){
	        for(int i=0;i<checkItems.length;i++){
	        	if(Integer.parseInt(checkItems[i])==CouponRestrictEnum.RESTRICT_1.getKey()){
	        		batchType=CouponRestrictEnum.RESTRICT_1.getKey();
	        	}
	        	String checkItemValue =CouponRestrictEnum.getValueByKey(Integer.parseInt(checkItems[i]));
	        	String []values =StringUtils.split(checkItemValue,",");
	        	StringBuffer sb = new StringBuffer();
	        	for(String va:values){
	        		String []parameterValues = request.getParameterValues(va);
	        		StringBuffer sbpara = new StringBuffer();
	        		if(parameterValues!=null){
	        			for(String para:parameterValues){
		        			sbpara.append(para).append(",");
		        		}
	        		}else{
	        			sbpara.append("0");
	        		}
	        		sb.append(StringUtils.stripEnd(sbpara.toString(),","))
	        		.append(",");
	        	}
	        	featureMap.put(checkItems[i],StringUtils.stripEnd(sb.toString(),","));
	        }
	        }

	        couponBatch.setFeatureMap(featureMap);
	        
	        couponBatch.setBatchDesc(addCouponDesc);
	        couponBatch.setBatchType(batchType);
	        long userId =loginInfo.getUser_id();
	        //修改batch只能是新建状态
	        couponBatch.setStatus(CouponStatus.STATUS_1.getKey());
	        if("add".equals(operation)){
	        	couponBatch.setCreateTime(date);
	        	couponBatch.setUpdateTime(date);
	        	couponBatch.setCreator(userId);
	        	couponBatch.setEditor(userId);
		        couponService.insertCoupon(couponBatch);
	        }else{
	        	couponBatch.setUpdateTime(date);
	        	couponBatch.setEditor(userId);
	        	couponService.updateCoupon(couponBatch);
	        }
	        return buildSuccJson("success");
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("优惠劵新增、修改保存失败 user_id,batch_id",loginInfo.getUser_id(), loginInfo.getUser_name());
			 map.put("status", e.getMessage());
			 return buildErrJson(e.getMessage());
		}
	}
	
	
	@RequestMapping("/list")
	public Object couponList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("ump/coupon/list");
		model.addAllObjects( this.getAcctivityCategroy(request, response));
		return model;

	}
	/**
	 * 优惠卷列表页
	 * @param 
	 * @return
	 */
	@RequestMapping(value ="ajaxList")
	@ResponseBody
	public Object ajaxCouponList(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam int draw, 
			@RequestParam int start, 
			@RequestParam(value = "length") int numPerPage,
			@RequestParam String startDate,
			@RequestParam String endDate,
			@RequestParam Long batchId,
			@RequestParam String batchName,
			@RequestParam String money,
			@RequestParam int platformType,
			@RequestParam int deliverType,
			@RequestParam int status
			){
		
		User loginInfo = (User)request.getAttribute("user");
		try {
			long total = 0;
	        Pair<Integer, List<CouponBatch>> datas = couponService.getCouponList(startDate, endDate,batchId, batchName, money, platformType, deliverType,status,start, numPerPage);
	        logger.info("user[{}:{}] to query,start:{},numPerPage:{}",loginInfo.getUser_id(), loginInfo.getUser_name(), start, numPerPage);
	        return buildDataTableResult(draw, total, datas.getLeft(), datas.getRight());
		} catch (Exception e) {
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
		
	}
	
	/**
	 * 获取生成编号id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="getSequenceId")
	@ResponseBody
	public Object getSequenceId(HttpServletRequest request, HttpServletResponse response
			){
		Map<String,Object> map = Maps.newHashMap();
		Long sequenceId=0L;
		groupSequence = new GroupSequence();
		try {
			sequenceId = groupSequence.nextValue();
		} catch (SequenceException e) {
			e.printStackTrace();
		}
		map.put("sequenceId", sequenceId);
		return map;
	}
	
	/**
	 * 获取活动类目，商品类目
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="getActivityCategroy")
	@ResponseBody
	public Map<String,Object> getAcctivityCategroy(HttpServletRequest request, HttpServletResponse response
			){
		List<Category> categoryList = categoryService.getAllCategories();
		List<Activity> activityList = activityService.getActivityList(null);
		Map<String,Object> map = Maps.newHashMap();
		map.put("categoryList", categoryList);
		map.put("activityList", activityList);
		return map;
	}
	
	/**
	 * 结束领取
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="endCouponBatch")
	@ResponseBody
	public Object endCouponBatch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Long couponBatchId ){
			Integer st = couponService.endCouponBatch(couponBatchId,CouponStatus.STATUS_4.getKey());
			return buildJson(st, "");
	}
	
	
	@RequestMapping("/userReferList")
	public Object userReferList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("ump/coupon/userReferList");
		return model;
	}
	
	/**
	 * 通过手机号查询用户所有信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="getUserAllByPhone")
	@ResponseBody
	public Object getUserAllByPhone(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String phone ){
			
			List<String> list = new ArrayList<String>();
			list.add(phone);
			List<UcUser> userList = null;
			try {
				userList = ucUserService.getUcUsersByPhoneNos(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			UcUser user=null;
			List<Coupon> batchList =new ArrayList<Coupon>();
			List<Coupon> rewardsList =new ArrayList<Coupon>();
			if(CollectionUtils.isNotEmpty(userList)){
				user =userList.get(0);
				batchList =couponService.getUserAllByPhone(user.getId());
				rewardsList =couponService.getCouponRewardsAllByPhone(user.getId());
			}
			Map<String,Object> map = Maps.newHashMap();
			map.put("user", user);
			map.put("batchList", batchList);
			map.put("rewardsList", rewardsList);
			return map;
	}
	
	@RequestMapping("/pushRewardList")
	public Object pushRewardList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("ump/coupon/pushRewardList");
		return model;
	}
	/**
	 * 通过手机号查询用户所有信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="getPushRewardList")
	@ResponseBody
	public Object getPushRewardList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, 
			@RequestParam int start, 
			@RequestParam(value = "length") int numPerPage,
			@RequestParam String phone,
			@RequestParam String startDate,
			@RequestParam String endDate){
			
		if(StringUtils.isBlank(startDate)||StringUtils.isBlank(endDate)){
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
		List<UcUser> userList = null;
		if(StringUtils.isNotBlank(phone)){
			List<String> list = new ArrayList<String>();
			list.add(phone);
			try {
				userList = ucUserService.getUcUsersByPhoneNos(list);
			} catch (Exception e) {
				return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
			}
		}
			UcUser user=null;
			Integer userId =0;
			if(CollectionUtils.isNotEmpty(userList)){
				user =userList.get(0);
				userId =user.getId();
			}
			try {
			Pair<Integer, List<CouponRewardsInfo>>	rewardsList = couponService.getPushRewardList(userId,phone,6,startDate, endDate,start, numPerPage);
				 return buildDataTableResult(draw, 0, rewardsList.getLeft(), rewardsList.getRight());
			} catch (Exception e) {
				logger.error("",e);
				return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
			}
	}
	
	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String phone,
			@RequestParam String strStartDate,
			@RequestParam String strEndDate) throws SQLException, IOException,
			ParseException {

		Date startDate=null;
		 Date endDate =null;
		if(StringUtils.isNotBlank(strStartDate)&&StringUtils.isNotBlank(strEndDate)){
			startDate = DateUtil.formatDateToDate(strStartDate);
			endDate =  DateUtil.formatDateToDate(strEndDate);
		}else{
			return;
		}

		if ((startDate == null && endDate == null)
				|| (startDate != null && endDate != null && startDate.after(endDate))) {
			startDate = new DateTime().millisOfDay().setCopy(0).toDate();
			endDate = new DateTime().plusDays(1).millisOfDay().setCopy(0).toDate();
		}

		String filename = "withdraw_" + _.formatDate(startDate, "yyyy-MM-dd") + "_"
				+ _.formatDate(endDate, "yyyy-MM-dd") + ".csv";
		response.setContentType("text/csv;charset=GB2312");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);

		OutputStream out = response.getOutputStream();
		String header = "批次号,邀请者手机,被邀请者手机,状态,拉新日期\n";
		out.write(header.getBytes(Charset.forName("GB2312")));

		// Write the content
		
		//获取uc信息
		List<UcUser> userList = null;
		if(StringUtils.isNotBlank(phone)){
			List<String> list = new ArrayList<String>();
			list.add(phone);
			try {
				userList = ucUserService.getUcUsersByPhoneNos(list);
			} catch (Exception e) {
				logger.error("获取用户信息出错",e);
			}
		}
			UcUser user=null;
			Integer userId =0;
			if(CollectionUtils.isNotEmpty(userList)){
				user =userList.get(0);
				userId =user.getId();
			}
		List<CouponRewardsInfo> rewardsList;
		try {
			rewardsList = couponService.getPushRewardExportList(userId,phone,6,startDate, endDate);
		for (CouponRewardsInfo couponRewardsInfo : rewardsList) {
			String status = "";
			if (couponRewardsInfo.getStatus() == 1) {
				status = "未使用";
			} else if (couponRewardsInfo.getStatus() == 2||couponRewardsInfo.getStatus()==4) {
				status = "成功";
			}
			String line = couponRewardsInfo.getBatchId() + "," + couponRewardsInfo.getReleaseBussinessMobile() + ","
					+ couponRewardsInfo.getBussinessMobile() + "," 
					+ status + "," + _.formatDate(couponRewardsInfo.getCreateTime(), "yyyy-MM-dd hh:mm:ss") + 
					"\n";
			out.write(line.getBytes(Charset.forName("GB2312")));
		}
		out.flush();
	} catch (Exception e) {
		logger.error("导出失败",e);
	}
	}
	
}
