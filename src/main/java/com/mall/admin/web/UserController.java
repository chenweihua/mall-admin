package com.mall.admin.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.gson.reflect.TypeToken;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.user.RoleService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.user.Role;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.user.UserAndCategory;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ZtreeUtil ztreeUtil;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private StorageCollegeService storageCollegeService;

	@RequestMapping("/list")
	public Object userList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user/list");
		return mav;
	}

	@RequestMapping("/query")
	@ResponseBody
	public Object userListQuery(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage,
			@RequestParam(value = "search[value]") String searchStr) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		Map param = new HashMap();
		param.put("user_name", searchStr);

		List<User> userList = userService.getUserList(param, paginationInfo);
		for (User user : userList) {
			Role role = roleService.getRoleByUserId(user.user_id);
			user.role = role;
		}

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), userList));
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Object deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String userIdStr = request.getParameter("user_id");
		if (Strings.isEmpty(userIdStr)) {
			return buildJson(1, "删除失败，用户账号为空~");
		}
		if (!userIdStr.matches("[0-9]{1,}")) {
			return buildJson(1, "删除失败，用户账号不正确~");
		}
		long userId = Long.parseLong(userIdStr);
		User user = userService.getUserById(userId);
		if (user == null) {
			return buildJson(1, "删除失败，用户不存在~");
		}
		if (user.is_del != 0) {
			return buildJson(1, "删除失败，用户已被删除~");
		}
		user.is_del = 1;
		userService.updateUser(user);
		return buildJson(0, "删除成功~");
	}

	/**
	 * 开启用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/open")
	@ResponseBody
	public Object openUser(HttpServletRequest request, HttpServletResponse response) {
		String userIdStr = request.getParameter("user_id");
		if (Strings.isEmpty(userIdStr)) {
			return buildJson(1, "打开打开失败，用户账号为空~");
		}
		if (!userIdStr.matches("[0-9]{1,}")) {
			return buildJson(1, "打开失败，用户账号不正确~");
		}
		long userId = Long.parseLong(userIdStr);
		User user = userService.getUserById(userId);
		if (user == null) {
			return buildJson(1, "打开失败，用户不存在~");
		}
		if (user.is_del == 0) {
			return buildJson(1, "打开失败，用户可用~");
		}
		user.is_del = 0;
		userService.updateUser(user);
		return buildJson(0, "打开成功~");
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	public Object addUser(HttpServletRequest request, HttpServletResponse response) {
		List<Category> categoryList = categoryService.getAllCategories();
		User user = new User();
		//不包括管理员
		List<Role> roleList = roleService.getAllRole();
		List<Long> categoryIdList = new ArrayList<Long>();
		ModelAndView mav = new ModelAndView("user/add", ImmutableMap.of("categoryList", categoryList,
				"roleList", roleList, "userroleid", "0", "categoryIdList", categoryIdList, "newuser",
				user));
		return mav;
	}

	/**
	 * 编辑用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/edit")
	public Object editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userIdStr = request.getParameter("user_id");
		if (Strings.isEmpty(userIdStr) || !userIdStr.matches("[0-9]{1,}")) {
			gotoPage(request, response, "/home/index");
			return null;
		}
		Long userId = Long.parseLong(userIdStr);
		User user = userService.getUserById(userId);
		if (user == null) {
			gotoPage(request, response, "/home/index");
			return null;
		}

		List<Category> categoryList = categoryService.getAllCategories();
		List<Role> roleList = roleService.getAllRole();
		// Role role = userService.getUserRole(userId);
		List<Long> categoryIdList = new ArrayList<Long>();
		if (user.is_all_category == 0) {
			List<Category> userCategoryList = categoryService.getCategoryByUserId(userId);
			for (Category category : userCategoryList) {
				categoryIdList.add(category.getCategoryId());
			}
		}
		ModelAndView mav = new ModelAndView("user/edit", ImmutableMap.of("categoryList", categoryList,
				"roleList", roleList, "userroleid", user.role.role_id, "categoryIdList",
				categoryIdList, "newuser", user));
		return mav;
	}

	/**
	 * 开启用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adduser")
	@ResponseBody
	public Object addUserAction(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String userId, @RequestParam String userName, @RequestParam String account,
			@RequestParam String passWord,
			@RequestParam Integer userType,
			@RequestParam(required = false, defaultValue = "") Long[] categoryIdList,
			@RequestParam Long roleId) {
		if (Strings.isEmpty(account)) {
			return buildJson(1, "添加失败，账号为空~");
		}
		if (Strings.isEmpty(userName)) {
			return buildJson(1, "添加失败，用户名为空~");
		}
		if (Strings.isEmpty(passWord)) {
			return buildJson(1, "添加失败，密码为空~");
		}
		if (userType == null) {
			return buildJson(1, "添加失败，用户类型为空~");
		}
		if (roleId < 1) {
			return buildJson(1, "添加失败，用户角色为空~");
		}
		if (categoryIdList == null || categoryIdList.length < 1) {
			return buildJson(1, "添加失败，请选择类目~");
		}
		User oldUser = userService.getUserByAccount(account);
		if (oldUser != null) {
			return buildJson(1, "添加失败，账号已存在~");
		}
		int is_all_category = 0;
		// 不负责类目，所有类目和具体类目的优先级是 "不负责类目">"所有类目">"具体类目"
		for (Long categoryId : categoryIdList) {
			if (categoryId == -1) {
				is_all_category = -1;
				break;
			}
			if (categoryId == 0) {
				is_all_category = 1;
			}
		}

		User user = new User();
		/** 把密码和随机数salt组合，并通过 md5加密 */
		Random random = new Random();
		int salt = random.nextInt(998) + 1;
		user.salt = salt;
		String tempPwd = passWord + salt;
		HashCode md5PwdHashCode = Hashing.md5().hashString(tempPwd, Charsets.UTF_8);
		String passwordMd5 = md5PwdHashCode.toString().toUpperCase();
		HashCode tokenHashCode = Hashing.md5().hashString(passwordMd5 + salt, Charsets.UTF_8);
		user.password = passwordMd5; // 修改为和salt组合的md5加密密码
		user.token = tokenHashCode.toString().toUpperCase();
		user.user_name = userName;
		user.account = account;
		user.user_type = userType;
		user.is_del = 0;
		user.is_all_category = is_all_category;
		int insertFlag = userService.insertUser(user);
		if (insertFlag != 1) {
			return buildJson(1, "添加失败，添加用户信息失败~");
		}

		if (is_all_category == 0) {
			// 添加用户和类目的关系
			for (Long categoryId : categoryIdList) {
				UserAndCategory userAndCategory = new UserAndCategory();
				userAndCategory.category_id = categoryId;
				userAndCategory.user_id = user.user_id;
				userAndCategory.createor = user.user_id;
				userService.insertUserCategory(userAndCategory);
			}
		}
		// 添加用户和角色的关系
		userService.insertUserRole(user.user_id, roleId);
		return buildJson(0, "添加成功~");
	}

	/**
	 * 编辑用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/edituser")
	@ResponseBody
	public Object editUserAction(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String userId, @RequestParam String userName, @RequestParam String account,
			@RequestParam Integer userType,
			@RequestParam(required = false, defaultValue = "") Long[] categoryIdList,
			@RequestParam Long roleId) {

		if (Strings.isEmpty(userId) || !userId.matches("[0-9]{1,}")) {
			return buildJson(1, "修改失败，待修改的用户id错误~");
		}
		long user_id = Long.parseLong(userId);
		if (Strings.isEmpty(userName)) {
			return buildJson(1, "修改失败，用户名为空~");
		}
		if (roleId < 1) {
			return buildJson(1, "修改失败，用户角色为空~");
		}
		if (categoryIdList == null || categoryIdList.length < 1) {
			return buildJson(1, "修改失败，请选择类目~");
		}
		if (userType == null) {
			return buildJson(1, "修改失败，请选择用户类型~");
		}
		// User oldUser = userService.getUserByAccount(account);
		User user = userService.getUserById(Long.parseLong(userId));
		if (user == null) {
			return buildJson(1, "修改失败，用户不存在，禁止修改~");
		}
		if (!user.account.equals(account)) {
			return buildJson(1, "修改失败，用户账号信息不正确~");
		}
		int is_all_category = 0;
		// 不负责类目，所有类目和具体类目的优先级是 "不负责类目">"所有类目">"具体类目"
		for (Long categoryId : categoryIdList) {
			if (categoryId == -1) {
				is_all_category = -1;
				break;
			}
			if (categoryId == 0) {
				is_all_category = 1;
			}
		}

		user.user_name = userName;
		user.user_type = userType;
		user.is_del = 0;
		user.is_all_category = is_all_category;
		int insertFlag = userService.updateUser(user);
		if (insertFlag != 1) {
			return buildJson(1, "添加失败，添加用户信息失败~");
		}

		// userService.deleteUserRoleByUserId(user_id);
		userService.deleteUserCategory(user_id);
		if (is_all_category == 0) {
			// 添加用户和类目的关系
			for (Long categoryId : categoryIdList) {
				UserAndCategory userAndCategory = new UserAndCategory();
				userAndCategory.category_id = categoryId;
				userAndCategory.user_id = user.user_id;
				userAndCategory.createor = user.user_id;
				userService.insertUserCategory(userAndCategory);
			}
		}
		// 添加用户和角色的关系
		Role role = userService.getUserRole(user_id);
		if (role.role_id != roleId) {
			userService.deleteUserRoleByUserId(user_id);
			userService.insertUserRole(user.user_id, roleId);
		}
		return buildJson(0, "添加成功~");
	}

	@RequestMapping("/modifypassword")
	@ResponseBody
	public Object modifyPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String modifyuserId, @RequestParam String password,
			@RequestParam String repassword) {
		if (Strings.isEmpty(modifyuserId) || !modifyuserId.matches("[0-9]{1,}")) {
			return buildJson(1, "修改失败，待修改的用户id错误~");
		}
		long user_id = Long.parseLong(modifyuserId);
		if (Strings.isEmpty(password)) {
			return buildJson(1, "修改失败，新密码错误~");
		}
		if (Strings.isEmpty(repassword)) {
			return buildJson(1, "修改失败，重复密码错误~");
		}
		if (!password.equals(repassword)) {
			return buildJson(1, "修改失败，密码不一致~");
		}
		// User oldUser = userService.getUserByAccount(account);
		User set_user = userService.getUserById(Long.parseLong(modifyuserId));
		if (set_user == null) {
			return buildJson(1, "修改失败，用户不存在，禁止修改~");
		}
		// User user =
		// (User)request.getAttribute(Constants.MALLADMIN_USER);
		// if(set_user.user_id==user.user_id){
		// return buildJson(1, "修改失败，禁止修改自己的密码~");
		// }
		int salt = set_user.getSalt();
		HashCode md5PwdHashCode = Hashing.md5().hashString(repassword + salt, Charsets.UTF_8);
		String passwordMd5 = md5PwdHashCode.toString().toUpperCase();
		HashCode tokenHashCode = Hashing.md5().hashString(passwordMd5 + salt, Charsets.UTF_8);
		set_user.password = passwordMd5; // 修改为和salt组合的md5加密密码
		set_user.token = tokenHashCode.toString().toUpperCase();
		userService.updateUserPassword(set_user);
		return buildJson(0, "密码修改成功~");
	}

	@RequestMapping("/getregion")
	@ResponseBody
	public Object getUserRegion(HttpServletRequest request, HttpServletResponse response) {
		// 首先获得该商品所在仓的范围
		String user_id_Str = request.getParameter("user_id");
		if (user_id_Str == null || !user_id_Str.matches("[0-9]{1,}")) {
			return buildJson(1, "待设置的用户id错误~");
		}
		long user_id = Long.parseLong(user_id_Str);
		User set_user = userService.getUserById(user_id);
		if (set_user == null) {
			return buildJson(1, "待设置的用户不存在~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		ZtreeBean ztreeBean = ztreeUtil.getStorageZtree(user.allStorageList,true);
		userService.setZtreeBeanStatus(ztreeBean, set_user);
		return buildJson(0, "商品范围查询成功~", ztreeBean);
	}

	/**
	 * 添加商品或编辑商品
	 * 
	 * @param request
	 * @param response
	 * @param draw
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/setregion")
	@ResponseBody
	public Object setUserRegion(HttpServletRequest request, HttpServletResponse response) {
		String region = request.getParameter("region");
		String user_id_str = request.getParameter("user_id");
		if (user_id_str == null || !user_id_str.matches("[0-9]{1,}")) {
			return buildJson(1, "待设置的用户id错误~");
		}

		List<ZtreeBean> bean = gson.fromJson(region, new TypeToken<List<ZtreeBean>>() {
		}.getType());

		if (bean == null || bean.size() == 0) {
			return buildJson(1, "获取范围信息为空~");
		}
		long user_id = Long.parseLong(user_id_str);
		User set_user = userService.getUserById(user_id);
		if (set_user == null) {
			return buildJson(1, "待设置的用户不存在~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		ZtreeBean ztreeBean = bean.get(0);
		userService.setUserRegion(ztreeBean, set_user, user);
		return buildJson(0, "商品修改成功~");
	}

	/**
	 * 获得用户负责的类目和仓库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCategoryAndStorage")
	@ResponseBody
	public Object getUserCategoryAndStorage(HttpServletRequest request, HttpServletResponse response) {

		String storagetypeFlag = request.getParameter("stoagetypeflag");
		if (!(Constants.VM_STORAGE.equals(storagetypeFlag) || Constants.RDC_STORAGE.equals(storagetypeFlag) ||
				Constants.LDC_STORAGE.equals(storagetypeFlag)||Constants.ALL_STORAGE.equals(storagetypeFlag))) {
			return buildJson(1, "仓库类型错误~");
		}
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
		List<Category> categoryList = new ArrayList<Category>();
		List<Storage> storageList = new ArrayList<Storage>();
		List<College> collegeList = new ArrayList<College>();
		if (Constants.RDC_STORAGE.equals(storagetypeFlag)) {
			categoryList = user.getCategoryList();
			storageList = user.getRdcStorageList();
		}
		if (Constants.VM_STORAGE.equals(storagetypeFlag)) {
			categoryList = user.getCategoryList();
			storageList = user.getVmStorageList();
		}
		if (Constants.LDC_STORAGE.equals(storagetypeFlag)) {
			categoryList = categoryService.getAllCategories();
			storageList = user.getLdcStorageList();
		}
		if (Constants.ALL_STORAGE.equals(storagetypeFlag)) {
			if (user.getRole().admin_flag == Constants.ADMIN_FLAG
					|| (user.getIs_all_category() == 1 && user.getIs_all_storage() == 1)) {
				categoryList = categoryService.getAllCategories();
			} else {
				categoryList = user.getCategoryList();
			}
			storageList = user.getAllStorageList();
		}
		if (storageList != null && storageList.size() > 0) {
			Storage temp = storageList.get(0);
			switch (temp.getStorageType()) {
			case Storage.RDC_STORAGE:
				collegeList = collegeService.getListByRdcStorageId(temp.getStorageId());
				break;
			case Storage.LDC_STORAGE:
				collegeList = collegeService.getListByLdcStorageId(temp.getStorageId());
				break;
			case Storage.VM_STORAGE:
				List<Long> collegeIdList = storageCollegeService.getCollegeIdListByStorageId(temp.getStorageId());
				for(Long collegeId : collegeIdList){
					College tempCollege = CollegeConstant.getCollegeById(collegeId);
					if(temp != null){
						collegeList.add(tempCollege);
					}
				}
				break;
			}
		}
		if (user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG || user.is_all_category == 1) {
			Category category = new Category();
			category.setCategoryId(0);
			category.setCategoryName("全部");
			categoryList.add(0,category);
		}
		if (user.getRole().getAdmin_flag() == Constants.ADMIN_FLAG || user.is_all_storage == 1) {
			Storage storage = new Storage();
			storage.setStorageId(0);
			storage.setStorageName("全部");
			storageList.add(0,storage);
		}
		return buildJson(0, "查询成功~", ImmutableMap.of("category", categoryList, "storage", storageList,
				"college", collegeList));

	}

	/**
	 * 修改自己的密码
	 * 
	 * @param request
	 * @param response
	 * @param modifyuserId
	 * @param password
	 * @param repassword
	 * @return
	 */
	@RequestMapping("/modifymypassword")
	@ResponseBody
	public Object modifyMyPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String oldpassword, @RequestParam String password, @RequestParam String repassword) {
		User user = (User) request.getAttribute(Constants.MALLADMIN_USER);

		HashCode oldPwHashCode = Hashing.md5().hashString(oldpassword + user.salt, Charsets.UTF_8);
		String oldPasswordMd5 = oldPwHashCode.toString().toUpperCase();
		if (!oldPasswordMd5.equalsIgnoreCase(user.password)) {
			return buildJson(1, "旧密码不正确~");
		}

		if (Strings.isEmpty(password)) {
			return buildJson(1, "修改失败，新密码错误~");
		}
		if (Strings.isEmpty(repassword)) {
			return buildJson(1, "修改失败，重复密码错误~");
		}
		if (!password.equals(repassword)) {
			return buildJson(1, "修改失败，密码不一致~");
		}

		int salt = user.getSalt();
		HashCode md5PwdHashCode = Hashing.md5().hashString(repassword + salt, Charsets.UTF_8);
		String passwordMd5 = md5PwdHashCode.toString().toUpperCase();
		HashCode tokenHashCode = Hashing.md5().hashString(passwordMd5 + salt, Charsets.UTF_8);
		user.password = passwordMd5; // 修改为和salt组合的md5加密密码
		user.token = tokenHashCode.toString().toUpperCase();
		userService.updateUserPassword(user);

		Cookie tokenCookie = new Cookie(Constants.COOKIE_USER_TOKEN, user.token);
		tokenCookie.setPath("/");
		tokenCookie.setMaxAge(86400);
		Cookie userIdCookie = new Cookie(Constants.COOKIE_USER_ID, String.valueOf(user.user_id));
		userIdCookie.setPath("/");
		userIdCookie.setMaxAge(86400);

		response.addCookie(tokenCookie);
		response.addCookie(userIdCookie);

		return buildJson(0, "密码修改成功~");
	}
}
