package com.mall.admin.web.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.UrlConstants;
import com.mall.admin.service.user.UserService;
import com.mall.admin.vo.user.User;

/**
 * 用户登录控制对象
 * 
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public void root(HttpServletRequest request, HttpServletResponse response) throws IOException {
		gotoPage(request, response, UrlConstants.LOGIN);
	}

	@RequestMapping("/admin/login")
	public Object login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Cookie tokenCookie = new Cookie(Constants.COOKIE_USER_TOKEN, "");
		tokenCookie.setPath("/");
		tokenCookie.setMaxAge(1);
		Cookie userIdCookie = new Cookie(Constants.COOKIE_USER_ID, "");
		userIdCookie.setPath("/");
		userIdCookie.setMaxAge(1);

		response.addCookie(tokenCookie);
		response.addCookie(userIdCookie);

		return new ModelAndView("login");
	}

	@RequestMapping("/admin/logout")
	public Object logout(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		return new ModelAndView("login");
	}

	@RequestMapping(value = "/admin/login.do", method = { RequestMethod.POST })
	@ResponseBody
	public Object loginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException,
			IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		if (Strings.isNullOrEmpty(account)) {
			return buildJson(1, "账号为空~");
		}
		if (Strings.isNullOrEmpty(password)) {
			return buildJson(1, "密码为空");
		}
		User user = userService.getUserByAccount(account);
		if (user == null) {
			return buildJson(1, "用户不存在~");
		}
		if (user.is_del == 1) {
			return buildJson(1, "用户被禁用~");
		}
		// HashCode pwHashCode = Hashing.md5()
		// .hashString(password + user.salt, Charsets.UTF_8);
		HashCode pwHashCode = Hashing.md5().hashString(password + user.salt, Charsets.UTF_8);
		String passwordMd5 = pwHashCode.toString().toUpperCase();
		if (!passwordMd5.equalsIgnoreCase(user.password)) {
			return buildJson(1, "密码不正确~");
		}

		Cookie tokenCookie = new Cookie(Constants.COOKIE_USER_TOKEN, user.token);
		tokenCookie.setPath("/");
		tokenCookie.setMaxAge(86400);
		Cookie userIdCookie = new Cookie(Constants.COOKIE_USER_ID, String.valueOf(user.user_id));
		userIdCookie.setPath("/");
		userIdCookie.setMaxAge(86400);

		response.addCookie(tokenCookie);
		response.addCookie(userIdCookie);

		return buildJson(0, "login success");
	}

	@RequestMapping("/home/index")
	public Object index(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		return new ModelAndView("index");
	}

}
