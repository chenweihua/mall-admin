package com.mall.admin.vo;

import org.springframework.beans.factory.annotation.Value;

/**
 * 小麦配置文件
 * 
 * @author Administrator
 *
 */
public class ConfigureBean {

	@Value("${taobao.goods.url}")
	private String taobaoGoodsURL;
	@Value("${taobao.goods.appkey}")
	private String taobaoGoodsAppkey;
	@Value("${taobao.goods.secret}")
	private String taobaoGoodsSecret;

	public String getTaobaoGoodsURL() {
		return taobaoGoodsURL;
	}

	public void setTaobaoGoodsURL(String taobaoGoodsURL) {
		this.taobaoGoodsURL = taobaoGoodsURL;
	}

	public String getTaobaoGoodsAppkey() {
		return taobaoGoodsAppkey;
	}

	public void setTaobaoGoodsAppkey(String taobaoGoodsAppkey) {
		this.taobaoGoodsAppkey = taobaoGoodsAppkey;
	}

	public String getTaobaoGoodsSecret() {
		return taobaoGoodsSecret;
	}

	public void setTaobaoGoodsSecret(String taobaoGoodsSecret) {
		this.taobaoGoodsSecret = taobaoGoodsSecret;
	}

}
