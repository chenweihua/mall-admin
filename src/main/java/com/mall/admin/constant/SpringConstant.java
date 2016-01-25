package com.mall.admin.constant;

import org.springframework.web.context.WebApplicationContext;

public class SpringConstant {
	
	private static WebApplicationContext context;
	
	
	public static void setWebApplicationContext(WebApplicationContext context) {
		SpringConstant.context = context;
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

}
