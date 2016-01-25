package com.mall.admin.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.JsonObject;

/**
 * 申请支付的操作类
 *
 */
public class ApplyPayUtil {
	
	private static AtomicInteger seq = new AtomicInteger(0);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	public synchronized static String applyCode(){
		
		String dateStr = format.format(new Date());
		int seq_temp=seq.getAndIncrement();
		if(seq_temp==9999){
			seq.set(0);
		}
		String seqStr = seq_temp+"";
		while(seqStr.length()<4){
			seqStr="0"+seqStr;
		}
		return dateStr+seqStr;
	}

	
}
