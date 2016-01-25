package com.mall.admin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import scala.collection.mutable.StringBuilder;

import com.mall.admin.enumdata.BannerTypeEnum;
import com.mall.admin.vo.base.SelectDto;

public class BannerHrefUrlConstant {
	public static final String PROTOCOL = "xiaomaiapp://platformapi/startapp/?";
	public static final String TARGET = "target";
	public static final String NAVIGATION_ID = "navigationid";
	public static final String CATEGORY_ID = "categoryid";
	public static final String CATEGORY_NAME = "categoryname";
	public static final String ACTIVITY_TYPE = "activitytype";
	public static final String ACTIVITY_ID = "activityid";
	public static final String ACTIVITY_NAME = "activityname";
	public static final String WEBVIEW_IS_NEED_LOGIN = "isneedlogin";
	public static final String WEBVIEW_ADDRESS = "urladdress";
	public static final String WEBVIEW_NAME = "webviewname";
	public static final String GOODS_ID = "goodsid";
	public static final String GOODS_TYPE = "goodstype";
	public static final String GOODS_SOURCE_TYPE = "sourcetype";
	
	
	public static String generateDiscoveryUrl(String target,Long id){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target)
				.append("&").append(NAVIGATION_ID).append("=").append(id).toString();
	}
	
	public static String generateUsercenterUrl(String target){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target).toString();
	}
	
	public static String generateCategoryUrl(String target,Long id,String categoryName){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target)
				.append("&").append(CATEGORY_ID).append("=").append(id)
				.append("&").append(CATEGORY_NAME).append("=").append(categoryName).toString();
	}
	
	public static String generateActivityUrl(String target,Long id,Integer activityType,String activityName){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target)
				.append("&").append(ACTIVITY_TYPE).append("=").append(activityType)
				.append("&").append(ACTIVITY_ID).append("=").append(id)
				.append("&").append(ACTIVITY_NAME).append("=").append(activityName).toString();
	}
	
	public static String generateGoodsDetailUrl(String target,Long id,Integer goodsType,Integer sourceType){
		return new StringBuilder().append(PROTOCOL).append("targer=").append(target)
				.append("&").append(GOODS_ID).append("=").append(id)
				.append("&").append(GOODS_TYPE).append("=").append(goodsType)
				.append("&").append(GOODS_SOURCE_TYPE).append("=").append(sourceType).toString();
	}
	
	public static String generateScoreUrl(String target){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target).toString();
	}
	
	public static String generateWebViewUrl(String target,Integer isNeedLogin,String urlAddress,String webViewName){
		return new StringBuilder().append(PROTOCOL).append(TARGET).append("=").append(target)
				.append("&").append(WEBVIEW_IS_NEED_LOGIN).append("=").append(isNeedLogin)
				.append("&").append(WEBVIEW_NAME).append("=").append(webViewName)
				.append("&").append(WEBVIEW_ADDRESS).append("=").append(urlAddress).toString();
	}
	
	public static List<SelectDto> generateMallModelList(BannerTypeEnum selected){
		List<SelectDto> mallModelList = new ArrayList<>();
		boolean score = false;
		boolean discovery = false;
		boolean usercenter = false;
		switch (selected) {
		case BANNER_HREF_TYPE_SCORE:
			score = true;
			break;
		case BANNER_HREF_TYPE_DISCOVERY:
			discovery = true;
			break;
		case BANNER_HREF_TYPE_USERCENTER:
			usercenter = true;
			break;
		default:
			break;
		}
		mallModelList.add(new SelectDto(BannerTypeEnum.BANNER_HREF_TYPE_SCORE.getValue(),
				"积分模块",score));
		mallModelList.add(new SelectDto(BannerTypeEnum.BANNER_HREF_TYPE_DISCOVERY.getValue(),
				"发现模块",discovery));
		mallModelList.add(new SelectDto(BannerTypeEnum.BANNER_HREF_TYPE_USERCENTER.getValue(),
				"用户中心",usercenter));
		return mallModelList;
	}
	
	
	
	public static Map<String, String> parseNewHrefUrl(String url){
		if(StringUtils.isEmpty(url)){
			return null;
		}
		Map<String, String> ret = new HashMap<String, String>();
		String paramStr = url.split("\\?",2)[1];
		String[] params = paramStr.split("&");
		for(String param : params){
			String[] keyValue = param.split("=");
			if(keyValue.length == 1){
				ret.put(keyValue[0], "");
			}else{
				ret.put(keyValue[0], keyValue[1]);
			}
		}
		return ret;
	}
	
	public static Map<String, String> parseWebview(String url){
		if(StringUtils.isEmpty(url)){
			return null;
		}
		Map<String, String> ret = new HashMap<String, String>();
		String paramStr = url.split("\\?",2)[1];
		String[] params =paramStr.split("&",4);
		for(String param : params){
			String[] keyValue = param.split("=",2);
			if(keyValue.length == 1){
				ret.put(keyValue[0], "");
			}else{
				ret.put(keyValue[0], keyValue[1]);
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		String url = "xiaomaiapp://platformapi/startapp/?target=webview&isneedlogin=0&webviewname=普通活动页展示 跳转至URL链接&urladdress=http://www.baidu.com?targer=jfsidjfkls&name=jfkdjk";
		parseWebview(url);
	}
}
