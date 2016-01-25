package com.mall.admin.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.base.BaseController;
import com.mall.admin.util.HttpService;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.PropertyUtils;
import com.mall.admin.util._;

@Controller
@RequestMapping("/tool")
public class ToolController extends BaseController {
	@Autowired
	HttpService httpService;

	@RequestMapping("image/upload")
	@ResponseBody
	public Object uploadAction(HttpServletRequest request,
			HttpServletResponse response) {
		String content = request.getParameter("content");

		String dataUrlPre = content.substring(0, content.indexOf(","));

		String uploadImgUrl = PropertyUtils.getProperty("upload.img");
		if (Strings.isEmpty(uploadImgUrl)
				|| Strings.isEmpty(uploadImgUrl.trim())) {
			return buildJson(1, "图片服务地址错误");
		}
		String uploadResultString = httpService.sendPostRequest(uploadImgUrl,
				gson.toJson(_.asMap("data", content)), "UTF-8");
		// String uploadResultString =
		// config.crawler.post(config.selectUrl(),
		// gson.toJson(_.asMap("data", content)), "UTF-8").getRight();

		JsonNode uploadResultJsonNode;
		try {
			uploadResultJsonNode = JsonUtil.parse(uploadResultString);
		} catch (IOException e) {
			return buildJson(1, "error");
		}
		int code = uploadResultJsonNode.findValue("code").asInt();
		String url = uploadResultJsonNode.findValue("url").asText();
		String msg = uploadResultJsonNode.findValue("msg").asText();

		if (code != 0) {
			return buildJson(1, "error");
		}

		return buildJson(0, "success", url);
	}
}
