package com.qxbase.blog.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationUtils.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByName(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBeanByClass(Class<T> serviceClazz) {
		return (T) applicationContext.getBean(serviceClazz);
	}

}
