package com.mall.admin.service.delivery.impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mall.admin.constant.DictionaryConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.delivery.DeliverInfoService;
import com.mall.admin.service.ump.uc.DependExternalService;
import com.mall.admin.service.ump.uc.impl.DependExternalServiceImpl;
import com.mall.admin.util.DictionaryUtil;
import com.mall.admin.util.GsonUtil;
import com.mall.admin.vo.delivery.DeliverDetailInfo;

/**
 * 快递100收费接口
 */
@Service
public class Kuaidi100NewDeliverInfoServiceImpl implements DeliverInfoService {
	
	private final String KUAIDI100_REGISTER_URL = "http://www.kuaidi100.com/poll";
	
	private final String KUAIDI100_KEY = "AaZDIXGH40"; //快递100授权给小麦公社的KEY
	
	//private final String KUAIDI100_CALLBACK_URL = "http://h5.imxiaomai.com/delivery/kuaidi100/notify"; //小麦提供的被动通知接口
	//private final String KUAIDI100_CALLBACK_URL = "http://wap.tmall.imxiaomai.com/delivery/kuaidi100/notify"; //小麦提供的被动通知接口
	private String KUAIDI100_CALLBACK_URL = null;
	
	private final String KUAIDI100_SALT_PREFIX = "imxiaomai";  //salt为该前缀+物流单号
	
	private final String KUAIDI100_REGISTER_LOG_PREFIX = "KUAIDI100_REGISTER:"; //注册接口log前缀，加此前缀方便后续提取log进行分析
	
	@Autowired
	DependExternalService dependExternalService;

	@Override
	public List<DeliverDetailInfo> queryDeliverInfo(String deliverCompanyCode,
			String deliverSheetCode) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	/**
	 * 入参格式：
	 * <form method="post" name="" action="http://www.kuaidi100.com/poll">
		<input type="text" name="schema" value="json" />
		<input type="text" name="param" value="此处为json文本，参考如下param格式" >
		</form>
		
		{
			"company":"yuantong",//订阅的快递公司的编码，一律用小写字母，见章五《快递公司编码》
			"number":"12345678", //订阅的快递单号，单号的最大长度是32个字符
			"from":"广东省深圳市南山区",//出发地城市
			"to":"北京市朝阳区",//目的地城市，到达目的地后会加大监控频率
			"key":"*********",//授权码，签订合同后发放
			"parameters":{
				"callbackurl":"http://www.您的域名.com/kuaidi?callbackid=...",//回调地址
				"salt":"any string",		//签名用随机字符串（可选）
		"resultv2":"1"				//添加此字段表示开通行政区域解析功能（仅对开通签收状态服务用户有效），见章3.1《推送请求》
			}
		}

	 * 
	 * 出参格式：
	 * {
			"result":"true",
			"returnCode":"200",
			"message":"提交成功"
		}

	 */
	@Override
	public boolean registerDeliverInfo(String deliverCompanyCode,
			String deliverSheetCode) {
		
		if(KUAIDI100_CALLBACK_URL == null) {
			KUAIDI100_CALLBACK_URL = DictionaryUtil.getValueByTypeIdAndKey(DictionaryConstant.KUAIDI100_NOTIFY_URL_TYPE_ID, "KUAIDI100_NOTIFY_URL");
		}
		
		try {
			RegisterInput input = new RegisterInput(deliverCompanyCode,deliverSheetCode,
					KUAIDI100_KEY,KUAIDI100_CALLBACK_URL,KUAIDI100_SALT_PREFIX + deliverSheetCode);
			
			String paramStr = input.toJsonStr();
			
			LogConstant.mallLog.info(KUAIDI100_REGISTER_LOG_PREFIX + paramStr);
			
			JsonNode jsonNode = dependExternalService.sendPostRequestNameValue(KUAIDI100_REGISTER_URL, "schema", "json", "param", paramStr);
			
			LogConstant.mallLog.info(KUAIDI100_REGISTER_LOG_PREFIX + jsonNode.toString());
			
			String result = jsonNode.get("result").asText();
			String returnCode = jsonNode.get("returnCode").asText();
	
			return ("true".equals(result)) || ("false".equals(result) && "501".equals(returnCode));  //成功提交或重复提交都被视为返回成功
		} catch(Exception ex) {
			LogConstant.mallLog.error("调用快递100注册接口发生异常,deliverCompanyCode:" + deliverCompanyCode + ",deliverSheetCode:" + deliverSheetCode, ex);
			return false;
		}
	}
	
	private static class RegisterInput {
		
		String company; //快递公司编码
		
		String number; //快递单号
		
		String key; //授权码
		
		Parameters parameters; //
		
		RegisterInput () {
			
		}
		
		RegisterInput(String company, String number, String key, String callbackurl, String salt) {
			this.company = company;
			this.number = number;
			this.key = key;
			this.parameters = new Parameters();
			this.parameters.callbackurl = callbackurl;
			this.parameters.salt = salt;
		}
		
		private static class Parameters {
			
			String callbackurl; //通知url
			
			String salt; //签名用随机字符串
		}
		
		String toJsonStr() throws Exception {
			 return GsonUtil.toJsonString(this);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		/*
		String deliverySheetCode = "1201815431112";
		String s = "{\"status\":\"abort\",\"message\":\"测试信息\",\"lastResult\":{\"state\":\"0\",\"ischeck\":\"0\",\"com\":\"yunda\",\"nu\":\"" + deliverySheetCode  + "\",\"data\":[{\"context\":\"上海分拨中心/装件入车扫描\",\"ftime\":\"2012-08-28 16:33:19\"},{\"context\":\"上海分拨中心/下车扫描 \",\"ftime\":\"2012-08-27 23:22:42\"}]}}}";
		//System.out.println(DigestUtils.md5DigestAsHex((s + "imxiaomai" + "V030344422").getBytes("UTF-8")));//1b63c8804dc8f66ac2f9fadbac160478
		String sign = DigestUtils.md5DigestAsHex((s + "imxiaomai" + deliverySheetCode).getBytes());
		DependExternalService dependExternalService = new DependExternalServiceImpl();
		//http://wap.tmall.imxiaomai.com/delivery/kuaidi100/notify
		dependExternalService.sendPostRequestNameValue("http://localhost:8080/xiaomaimall-web/delivery/kuaidi100/notify", "param", s, "sign", sign);
		*/
		String s = IOUtils.toString(new FileInputStream(new File("e:/abc.txt")),Charset.forName("GBK"));
		String sign = "DBAEE217E1518F9B12E55003AD58721E";
		DependExternalService dependExternalService = new DependExternalServiceImpl();
		
		dependExternalService.sendPostRequestNameValue("http://localhost:8080/delivery/kuaidi100/notify", "param", s, "sign", sign);
	}
}
