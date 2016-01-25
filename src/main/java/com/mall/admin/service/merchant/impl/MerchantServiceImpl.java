package com.mall.admin.service.merchant.impl;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.IniBean;
import com.mall.admin.model.dao.merchant.MerchantDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.merchant.MerchantService;
import com.mall.admin.util.EscapeUtil;
import com.mall.admin.vo.merchant.MerChantSeq;
import com.mall.admin.vo.merchant.Merchant;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	MerchantDao merchantDao;

	@Override
	public List<Merchant> getMerchantList(String merchantName, String shopOwner, int status,
			PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return merchantDao.getMerchantList(merchantName, shopOwner, status, paginationInfo);
	}

	@Override
	public int insert(Merchant merchant) {
		// TODO Auto-generated method stub
		return merchantDao.insert(merchant);
	}

	@Override
	public int update(Merchant merchant) {
		// TODO Auto-generated method stub
		return merchantDao.update(merchant);
	}

	@Override
	public int setStatus(int status, long merchantId) {
		// TODO Auto-generated method stub
		return merchantDao.setStatus(status, merchantId);
	}

	@Override
	public Merchant getMerchantById(long merchantId) {
		// TODO Auto-generated method stub
		return merchantDao.getMerchantById(merchantId);
	}

	@Override
	public Merchant getMerchantByUserId(long userId) {
		// TODO Auto-generated method stub
		return merchantDao.getMerchantByUserId(userId);
	}

	@Override
	public MerChantSeq getMaxSeq(long seqId) {
		// TODO Auto-generated method stub
		return merchantDao.getMaxSeq(seqId);
	}

	@Override
	public int updateMaxSeq(long seqId) {
		// TODO Auto-generated method stub
		return merchantDao.updateMaxSeq(seqId);
	}

	@Override
	public Merchant coverToMerchant(ServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getMerchantByName(String merchantName) {
		// TODO Auto-generated method stub
		return merchantDao.getMerchantByName(merchantName);
	}

	@Override
	public String getMerchantUrl(String merchantNo) {
		// 获得跳转地址
		StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize")
				.append("?appid=")
				.append(IniBean.getIniValue("weixinAppId", "wx35a8b3f8507f4ea6"))
				.append("&redirect_uri=")
				.append(EscapeUtil.encUtf8(IniBean.getIniValue("weixinOauth",
						"http://weixin.imxiaomai.com/oauth/redirect")))
				.append("&response_type=code&scope=snsapi_userinfo&state=")
				// .append(EscapeUtil.encUtf8("{\"url\":\"" +
				// IniBean.getIniValue("MerchantUrl")
				// + "\",\"p\":\"merchantNo=" + merchantNo +
				// "\",\"type\":\"base\"}"))
				.append(EscapeUtil.encUtf8("{url:" + IniBean.getIniValue("MerchantUrl")
						+ "?merchantNo=" + merchantNo + ",type:base}"))
				.append("#wechat_redirect");
		return url.toString();
	}
}
