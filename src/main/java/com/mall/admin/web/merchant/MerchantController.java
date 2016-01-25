package com.mall.admin.web.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.constant.Constants;
import com.mall.admin.enumdata.MerchantType;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.merchant.MerchantService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.EscapeUtil;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.merchant.MerChantSeq;
import com.mall.admin.vo.merchant.Merchant;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

	@Autowired
	MerchantService merchantService;

	@Autowired
	CityService cityService;

	@Autowired
	UserService userService;

	private static Object obj = new Object();
	Lock lock = new ReentrantLock();// 锁

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("addmerchant")
	@ResponseBody
	public Object addMerchantView(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Merchant merchant) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (merchant.getMerchantId() > 0) {
			merchant.setOperator(user.getUser_id());
			merchantService.update(merchant);
		} else {
			User tempUser = userService.getUserByAccount(merchant.getMerchantAccount());
			if (tempUser != null) {
				return buildErrJson("登陆账户" + merchant.getMerchantAccount() + "已经存在，请更换登陆账户~");
			}
			Merchant merchant_temp = merchantService.getMerchantByName(merchant.getMerchantName());
			if (merchant_temp != null) {
				return buildErrJson("商户名称" + merchant.getMerchantName() + "已存在禁止添加");
			}
			int salt = new Random().nextInt(1000);
			User newUser = new User();
			newUser.setUser_name(merchant.getMerchantAccount());
			newUser.setAccount(merchant.getMerchantAccount());
			newUser.setUser_type(User.MERCHANT_USER);
			String passWord = merchant.getOwnerPhone().substring(merchant.getOwnerPhone().length() - 6,
					merchant.getOwnerPhone().length());
			String tempPwd = passWord + salt;
			HashCode md5PwdHashCode = Hashing.md5().hashString(tempPwd, Charsets.UTF_8);
			String passwordMd5 = md5PwdHashCode.toString().toUpperCase();
			HashCode tokenHashCode = Hashing.md5().hashString(passwordMd5 + salt, Charsets.UTF_8);
			newUser.password = passwordMd5; // 修改为和salt组合的md5加密密码
			newUser.token = tokenHashCode.toString().toUpperCase();
			newUser.salt = salt;
			newUser.is_del = 0;
			newUser.is_all_category = -1;
			newUser.is_all_storage = -1;
			int userId = userService.insertUser(newUser);
			// 添加用户和角色的关系
			userService.insertUserRole(newUser.getUser_id(), User.MERCHANT_ROLE);
			merchant.setCreator(user.getUser_id());
			merchant.setOperator(user.getUser_id());

			merchant.setUserId(newUser.getUser_id());
			merchantService.insert(merchant);
		}

		return buildSuccJson(null);
	}

	/**
	 * 添加商户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getmerchantno")
	@ResponseBody
	public Object getMerchant(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		try {
			lock.lock();
			MerChantSeq maxSeqBean = merchantService.getMaxSeq(1L);
			merchantService.updateMaxSeq(1L);
			long maxSeqNum = maxSeqBean.getSeqNum();
			String maxSeqNumStr = maxSeqNum + "";
			while (maxSeqNumStr.length() < 7) {
				maxSeqNumStr = "0" + maxSeqNumStr;
			}
			String merchantNo = Merchant.ACOUNT_PRE + maxSeqNumStr;
			// // 获得跳转地址
			// StringBuilder url = new
			// StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize")
			// .append("?appid=")
			// .append(IniBean.getIniValue("weixinAppId",
			// "wx35a8b3f8507f4ea6"))
			// .append("&redirect_uri=")
			// .append(EscapeUtil.encUtf8(IniBean.getIniValue("weixinOauth",
			// "http://weixin.imxiaomai.com/oauth/redirect")))
			// .append("&response_type=code&scope=snsapi_userinfo&state=")
			// .append(EscapeUtil.encUtf8("{\"url\":\"" +
			// IniBean.getIniValue("MerchantUrl")
			// + "\",\"p\":\"merchantNo=" + maxSeqNumStr
			// +
			// "\",\"type\":\"user_info\"}")).append("#wechat_redirect");
			String merchantUrl = merchantService.getMerchantUrl(merchantNo);
			map.put("merchantNo", merchantNo);
			map.put("mechantPayAddr", merchantUrl);

		} finally {
			lock.unlock();
		}
		return buildSuccJson(map);
	}

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("querymerchantview")
	public Object queryMerchantView(HttpServletRequest request, HttpServletResponse response) {
		List<City> rootCityList = cityService.getCityListByPid(0L);
		return new ModelAndView("merchant/manager/merchantlist", CommonUtil.asMap("rootCityList", rootCityList));
	}

	/**
	 * 查询商户信息
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @param merchantName
	 *                商户名称
	 * @param shopOwner
	 *                商户店主
	 * @param status
	 *                商户状态 0：全部，1：使用中 2：停止使用
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Object queryMerchantList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, @RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer numPerPage,
			@RequestParam(value = "merchantname", required = false) String merchantName,
			@RequestParam(value = "shopowner", required = false) String shopOwner,
			@RequestParam(value = "status", required = false, defaultValue = "0") int status) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		List<Merchant> merchantList = merchantService.getMerchantList(merchantName, shopOwner, status,
				paginationInfo);
		if (merchantList != null && merchantList.size() > 0) {
			for (Merchant merchant : merchantList) {
				int type = merchant.getMerchantType();
				String merchantTypeStr = MerchantType.getNameByType(type);
				merchant.setMerchantTypeStr(merchantTypeStr);
				long areaId = merchant.getMerchantAreaId();
				City city = cityService.getCityById(areaId);
				long cityId = city.getPid();
				merchant.setMerchantCityId(cityId);
			}
		}
		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), merchantList, start));

	}

	/**
	 * 修改商户状态
	 * 
	 * @param request
	 * @param response
	 * @param status
	 *                1：使用中；2：停止使用
	 * @param merchantId
	 * @return
	 */
	@RequestMapping("/setstatus")
	@ResponseBody
	public Object setMerchantStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) int status, @RequestParam(required = true) long merchantId) {
		int num = merchantService.setStatus(status, merchantId);
		if (num > 0) {
			return buildSuccJson(null);
		} else {
			return buildErrJson("设置状态失败~");
		}
	}

	/**
	 * 查看商户信息
	 */
	@RequestMapping("/view")
	public Object viewMerchant(HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		if (user == null) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户不存在，请重新登录~"));
		}

		Merchant merchant = merchantService.getMerchantByUserId(user.getUser_id());
		if (merchant == null) {
			return new ModelAndView("info", ImmutableMap.of("message", "『对不起您不是商户账户，请到门店管理查看商户信息』"));
		}
		String merchantUrl = merchantService.getMerchantUrl(merchant.getMerchantNo());
		merchant.setMechantPayAddr(merchantUrl);
		return new ModelAndView("merchant/manager/viewmerchant", _.asMap("merchant", merchant));
	}

	/**
	 * 获取商户信息
	 * 
	 * @param request
	 * @param response
	 * @param merchantId
	 * @return
	 */
	@RequestMapping("/getdetail")
	public Object getMerchantDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) long merchantId) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		if (user == null) {
			return new ModelAndView("info", ImmutableMap.of("message", "用户不存在，请重新登录~"));
		}
		if (user.getRole().getAdmin_flag() != Constants.ADMIN_FLAG) {
			return new ModelAndView("info", ImmutableMap.of("message", "『对不起您不是管理员账户，无权操作此模块』"));
		}

		Merchant merchant = merchantService.getMerchantById(merchantId);
		if (merchant == null) {
			return new ModelAndView("info", ImmutableMap.of("message", "对不起，商户不存在~"));
		}
		String merchantUrl = merchantService.getMerchantUrl(merchant.getMerchantNo());
		merchant.setMechantPayAddr(merchantUrl);
		return new ModelAndView("merchant/manager/merchantdetail", _.asMap("merchant", merchant));
	}

	public static void main(String[] args) {

		// 获得跳转地址
		StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize")
				.append("?appid=")
				.append("wxd888b8a9c5763733")
				.append("&redirect_uri=")
				.append(EscapeUtil.encUtf8("http://weixin.test.imxiaomai.com/oauth/redirect"))
				.append("&response_type=code&scope=snsapi_userinfo&state=")
				.append(EscapeUtil
						.encUtf8("{\"url\":\""
								+ "http://wap.tmall.imxiaomai.com/page/qrcodepay/index.html?null#/qrcodepay/?merchantNo=XMS0000016"
								+ "\",\"type\":\"user_info\"}"))
				.append("#wechat_redirect");
		System.out.println(url.toString());

	}
}
