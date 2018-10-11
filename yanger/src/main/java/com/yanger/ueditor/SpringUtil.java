package com.yanger.ueditor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	//配置ApplicationContext,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
    	}
		System.out.println("SpringUtils:applicationContext init success");
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
