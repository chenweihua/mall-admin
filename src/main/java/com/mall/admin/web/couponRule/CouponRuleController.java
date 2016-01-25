package com.mall.admin.web.couponRule;

import static com.google.common.base.Preconditions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.base.MoneyUtils;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.service.couponRule.CouponRuleService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util._;
import com.mall.admin.vo.couponRule.CouponRule;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/couponRule")
public class CouponRuleController extends BaseController {

    @Autowired
    private CouponRuleService couponRuleService;
    @Autowired
	private ZtreeUtil ztreeUtil;
    @Autowired
	private WmsGoodsService wmsGoodsService;
    
    private static final Logger log = LogConstant.mallLog;
    
    @RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response, 
            @RequestParam(required=false, defaultValue="0") String type) throws SQLException,
			IOException {
		return new ModelAndView("couponRule/listCouponRule", _.asMap("have_nav", true, "type", type));

	}
    @RequestMapping("/ajaxListData")
    @ResponseBody
    public Object ajaxListData(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam int draw, @RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(required=false, defaultValue="0") String type
            ) throws SQLException, IOException, ParseException {
        int typeInt = _.toInt(type, 0);
        long total = 0;
        Pair<Integer, List<CouponRule>> datas = couponRuleService.getCouponRules(typeInt, start, numPerPage); //CouponRule.getListBySearchUserNameAndLimit(collegeId, typeInt, start, numPerPage);
        log.info("to query CouponRule type:{},start:{},numPerPage:{}",type, start, numPerPage);
        return buildDataTableResult(draw, total, datas.getLeft(), datas.getRight());
    }
    
    
   
    @RequestMapping("/edit")
    @ResponseBody
    public Object doEdit(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String couponRuleId,
    		@RequestParam String type,
    		@RequestParam String title,
    		@RequestParam String desc,
    		@RequestParam String money,
    		@RequestParam String subMoney
    		) throws SQLException {

    	User loginInfo = (User)request.getAttribute("user");
    	
    	try {
    		
    		checkArgument(money!=null&&money.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$"), "价钱格式错误~");
			checkArgument(subMoney!=null&&subMoney.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$"), "价钱格式错误~");
    		
    		int couponRuleIdInt = _.toInt(couponRuleId);
    		int typeInt = _.toInt(type);
    		int totalMoney = (int)MoneyUtils.yuan2Fen(Double.parseDouble(money));//_.toInt(money);
			int sub_money = (int)MoneyUtils.yuan2Fen(Double.parseDouble(subMoney));//_.toInt(subMoney);
			
			checkArgument((couponRuleIdInt>0), "couponRuleId非法");
			checkArgument((typeInt == 1 || typeInt == 2), "优惠规则错误类型");
			checkArgument((title.length()>=2 && title.length()<=100), "标题不得为空，且长度2~100");
			checkArgument((desc.length()>=2 && desc.length()<=100), "描述不得为空，且长度2~100");
			checkArgument(totalMoney>=1 && sub_money>=1, "总金额、满减金额为大于0");
			checkArgument(totalMoney>sub_money, "总额必须大于减免金额");
			
			checkState(loginInfo != null, "请先登录");
	    	
			CouponRule couponRule = couponRuleService.getCouponRuleById(couponRuleIdInt);
			
			checkState(couponRule!=null, "[%s]对应CouponRule不存在", couponRuleIdInt);
			
			couponRule.setCouponType(Byte.parseByte(type));// = Integer.parseInt(type);
			couponRule.setCouponName(title);
			couponRule.setDescription(desc);
			couponRule.setTotalMoney((int)(Double.parseDouble(money)*100));
			couponRule.setSubMoney((int)(Double.parseDouble(subMoney)*100));// = (int)(Double.parseDouble(subMoney)*100);
			couponRule.setOperator((int)loginInfo.getUser_id());//operatorId = loginInfo.user.id;
			couponRule.setUpdateTime(new Date());
			
			int count = couponRuleService.update(couponRule);
			
			checkState(count == 1, "系统错误,修改失败");
			
    		return _.asMap("status", "success");
    	} catch(Exception e) {
    		e.printStackTrace();
    		log.error(_.f("插入CouponRule异常,[userId:%s,couponRuleId:%s,type:%s,title:%s,desc:%s,money:%s,subMoney:%s]，error：", 
					loginInfo.getUser_id(),couponRuleId,type, title, desc, money, subMoney),e);
    		return _.asMap("status", e.getMessage());
    	}
    }

   
    @RequestMapping("/add")
    @ResponseBody
    public Object doAdd(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String type,
    		@RequestParam String title,
    		@RequestParam String desc,
    		@RequestParam String money,
    		@RequestParam String subMoney
    		) throws SQLException {
    	
    	User loginInfo = (User)request.getAttribute("user");
    	
		try {
			
			checkArgument(money!=null&&money.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$"), "价钱格式错误~");
			checkArgument(subMoney!=null&&subMoney.matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$"), "价钱格式错误~");
			
			int typeInt = _.toInt(type);
			int totalMoney = (int)MoneyUtils.yuan2Fen(Double.parseDouble(money));//_.toInt(money);
			int sub_money = (int)MoneyUtils.yuan2Fen(Double.parseDouble(subMoney));//_.toInt(subMoney);
			
			checkArgument((typeInt == 1 || typeInt == 2), "优惠规则错误类型");
			checkArgument((title.length()>=2 && title.length()<=100), "标题不得为空，且长度2~100");
			checkArgument((desc.length()>=2 && desc.length()<=100), "描述不得为空，且长度2~100");
			checkArgument(totalMoney>=1 && sub_money>=1, "总金额、满减金额为大于0");
			checkArgument(totalMoney>sub_money, "总额必须大于减免金额");
			
	    	checkState(loginInfo != null, "请先登录");
	    	
			CouponRule couponRule = new CouponRule();
			couponRule.setCouponType(Byte.parseByte(type));// = Integer.parseInt(type);
			couponRule.setCouponName(title);
			couponRule.setDescription(desc);
			couponRule.setTotalMoney((int)(Double.parseDouble(money)*100));
			couponRule.setSubMoney((int)(Double.parseDouble(subMoney)*100));// = (int)(Double.parseDouble(subMoney)*100);
			couponRule.setOperator((int)loginInfo.getUser_id());//operatorId = loginInfo.user.id;
			
			couponRuleService.insert(couponRule);
			
			LogConstant.mallLog.info("插入CouponRule[{}]", JSON.toJSONString(couponRule));
			
			return _.asMap("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(_.f("插入CouponRule异常,[userId:%s,type:%s,title:%s,desc:%s,money:%s,subMoney:%s]，error：", 
											loginInfo.getUser_id(),type, title, desc, money, subMoney),e);
			return _.asMap("status", e.getMessage());
		}
		
    }
    
    @RequestMapping("/del")
	@ResponseBody
	public Object delAction(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String couponRuleId) throws SQLException {
    	User loginInfo = (User)request.getAttribute("user");
    	try {
    		int couponRuleIdInt = _.toInt(couponRuleId);
    		checkArgument(couponRuleIdInt>0, "错误操作，没有couponRuleId[%s]", couponRuleId);
    		int count = couponRuleService.delete(couponRuleIdInt);
    		checkState(count == 1, "删除失败");
    		log.info("用户[{}],成功删除CouponRule[{}]", loginInfo.getUser_id(), couponRuleId);
    		return _.asMap("status", "success");
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.error(_.f("删除CouponRule失败，用户[%s],couponRule[%s]", loginInfo.getUser_id(), couponRuleId), e);
			return _.asMap("status", e.getMessage());
    	}
	}
    
    @RequestMapping("/getregion")
	@ResponseBody
	public Object couponRuleRegion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String couponRuleId) {
    	
    	try {
    		int couponRuleIdInt = _.toInt(couponRuleId);
    		checkArgument(couponRuleIdInt>0, "错误操作，没有couponRuleId[%s]", couponRuleId);
    		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
    		checkState(user!=null,"请先登录");
    		ZtreeBean ztreeBean = ztreeUtil.getCollegeZtree(couponRuleService.getCouponReginsByCouponRuleId(couponRuleIdInt),
    														couponRuleService.getCouponCollegesByCouponRuleId(couponRuleIdInt));
    		return buildJson(0, "商品范围查询成功~", ztreeBean);
    	} catch (Exception e) {
    		e.printStackTrace();
			return buildErrJson(e.getMessage());// _.asMap("status", e.getMessage());
    	}
		
	}
    
    @RequestMapping("/setregion")
	@ResponseBody
	public Object couponRuleSetRegion(HttpServletRequest request, HttpServletResponse response) {
    	User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
    	String region = request.getParameter("region");
		String couponRuleId = request.getParameter("couponRuleId");
		
		try {
			List<ZtreeBean> bean = gson.fromJson(region, new TypeToken<List<ZtreeBean>>() {
			}.getType());
			checkArgument(bean!=null&&bean.size()>0, "获取范围信息为空~");
			int couponRuleIdInt = Integer.parseInt(couponRuleId);
			ZtreeBean ztreeBean = bean.get(0);
			couponRuleService.setCouponRuleRegion(ztreeBean, couponRuleIdInt, user);
			return buildJson(0, "设置优惠规则区域成功~");
		} catch (Exception e) {
			e.printStackTrace();
			long userId = -1;
			if(user != null) {
				userId = user.getUser_id();
			}
			LogConstant.mallLog.error(_.f("设置优惠规则区域失败，用户[%s],couponRule[%s]", userId, couponRuleId), e);
			return buildErrJson("设置优惠规则区域失败,"+e.getMessage());// _.asMap("status", e.getMessage());
		}
	}

}
