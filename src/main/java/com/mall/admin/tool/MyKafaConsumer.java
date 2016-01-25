package com.mall.admin.tool;

import com.imxiaomai.cb.kafka.consumer.KafkaConsumer;
import com.mall.admin.constant.LogConstant;

public class MyKafaConsumer extends KafkaConsumer{

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		LogConstant.mallLog.debug("init Kafka");
		super.init();
		
//		this.getConsumerLogic().consume("123123");
	}

}
