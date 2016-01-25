package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.service.goods.BgGoodsService;
import com.mall.admin.service.goods.PropertyNameService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.util._;
import com.mall.admin.vo.goods.dto.BgGoodsDto;
import com.mall.admin.vo.user.User;

/**
 * 用户登录控制对象
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods/extend/")
public class GoodsManagerExtendController extends BaseController {

	@Autowired
	BgGoodsService bgGoodsService;
	@Autowired
	CategoryService categoryService;	
	@Autowired
	PropertyNameService propertyNameService;
	
	@ResponseBody
	@RequestMapping("bgGoodsDto/get")
	public Object getBgGoodsDto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		
		BgGoodsDto bgGoodsDto = bgGoodsService.getBgGoodsDtoById(bgGoodsId);
		
		return buildSuccJson(bgGoodsDto);
	}
	
	
	@ResponseBody
	@RequestMapping("bgGoodsDtos/get")
	public Object getBgGoodsDtos(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) throws SQLException, IOException {
		User user = (User) request.getAttribute("user");
		Map<String, Object> map = bgGoodsService.selectSingleGoodsByPage(start, numPerPage,searchStr);
		
		int total = _.toInt(map.get("total").toString());
		if(total < 0){
			return buildErrJson("获取信息失败");
		}
		return buildDataTableResult(draw, 0, total, (List)map.get("bgGoodsDtos"));
	}
	
	@ResponseBody
	@RequestMapping("propertyNameDto/get")
	public Object getPropertyNameDto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String bgGoodsIdStr = request.getParameter("bgGoodsId");
		if (bgGoodsIdStr == null || !bgGoodsIdStr.matches("[0-9]{1,}")) {
			return buildErrJson("请先填写商品信息，并保存或下一步");
		}
		long bgGoodsId = _.toLong(bgGoodsIdStr);
		
		Object o = propertyNameService.buildPropertyNameDtos(bgGoodsId);
		return o.toString();
	}
	
	
}
