package com.mall.admin.vo.order.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.mall.admin.util.PropertyUtils;
import com.mall.admin.util.TokenUtils;
import com.mall.admin.util.impl.HttpServiceImpl;

/**
 * 退款信息对象
 * 
 * @author Administrator
 *
 */
public class RefundInfoBean {

	public String applyStatus;
	public String refundStatus;
	public String refundReason;
	public long refundMoney;
	public long notifyMondy;
	public String refundMessage;
	public String applyDate;
	public String notifyDate;
	public String refundResult;

	private static Logger log = Logger.getLogger(RefundInfoBean.class);

	/**
	 * 根据订单id获得订单的退款信息 支付系统返回的格式
	 * 退款申请状态^退款状态^退款原因^申请退款金额^实际退款金额^退款信息^申请时间^退款时间
	 * #退款申请状态^退款状态^退款原因^申请退款金额^实际退款金额^退款信息^申请时间^退款时间
	 * 
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<RefundInfoBean> getRefundInfo(Long orderId) throws UnsupportedEncodingException {
		// Crawler crawler = Crawler.of("pay_refund",10000,10000);
		long date = new Date().getTime();

		JsonObject refundInfoJson = new JsonObject();

		refundInfoJson.addProperty("source", PropertyUtils.getProperty("pay.user"));
		refundInfoJson.addProperty("reqTime", date);
		refundInfoJson.addProperty(
				"nonceStr",
				TokenUtils.getNonceStr(PropertyUtils.getProperty("pay.user"),
						PropertyUtils.getProperty("pay.token"), date));
		refundInfoJson.addProperty("orderid", orderId);

		URL url = null;
		URI uri = null;
		try {
			url = new URL(PropertyUtils.getProperty("pay.refund.info"));
			uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		Header[] headers = { new BasicHeader("Cookie", "JSESSIONID=638q71ps77f91avvgo67r4nrj") };

		// CrawlResponse mallresponse = crawler.postResp(uri,
		// refundInfoJson.toString(), headers,"UTF-8");
		String payresponse = new HttpServiceImpl().sendRequest(PropertyUtils.getProperty("pay.refund.info"),
				refundInfoJson.toString(), headers);

		log.info("get  mall apply pay list:" + PropertyUtils.getProperty("pay.refund.info") + "\t"
				+ payresponse);
		// String returnInfo=payresponse.content;

		List<RefundInfoBean> infoList = new ArrayList<RefundInfoBean>();

		if (payresponse != null && payresponse.trim().length() > 1) {

			JsonNode jsonNode;
			try {
				jsonNode = new ObjectMapper().readTree(payresponse);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			String code = jsonNode.findValue("code") != null ? jsonNode.findValue("code").asText() : "-1";
			String msg = jsonNode.findValue("msg") != null ? jsonNode.findValue("msg").asText() : "-1";

			if (!"0".equals(code)) {
				return null;
			}
			String refundInfo = jsonNode.findValue("refundinfo") != null ? jsonNode.findValue("refundinfo")
					.asText() : "";
			String[] infoArry = refundInfo.split("\\#");
			for (String info : infoArry) {
				if (info.split("\\^").length == 8) {
					String[] param = info.split("\\^");
					RefundInfoBean bean = new RefundInfoBean();
					bean.applyStatus = param[0];
					bean.refundStatus = param[1];
					bean.refundReason = param[2];
					bean.refundMoney = Long.parseLong(param[3]);
					bean.notifyMondy = Long.parseLong(param[4]);
					bean.refundMessage = param[5];
					bean.applyDate = param[6];
					bean.notifyDate = param[7];

					if ("0".equals(bean.applyStatus)) {
						bean.refundResult = "退款中";
					} else if ("1".equals(bean.applyStatus)) {
						if ("0".equals(bean.refundStatus)) {
							bean.refundResult = "退款失败";
						} else if ("1".equals(bean.refundStatus)) {
							bean.refundResult = "退款成功";
						} else {
							bean.refundResult = "未知";
						}
					} else {
						bean.refundResult = "未知";
					}

					infoList.add(bean);
				}
			}
		}
		return infoList;

	}

	public static void main(String[] args) throws Exception {
		System.out.println(RefundInfoBean.getRefundInfo(1L));
	}

}
