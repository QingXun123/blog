package com.qxbase.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-01
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	public static <T> T copyInstance(Class<T> clazz, Object source, String... ignoreProperties) {
		T instance;
		try {
			instance = clazz.newInstance();
			copyProperties(source, instance, ignoreProperties);
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("clazz 实例化异常 [{}], e => [{}]", clazz, e);
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T copyInstance(Class<T> clazz, Object source) {
		T instance;
		try {
			instance = clazz.newInstance();
			copyProperties(source, instance);
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("clazz 实例化异常 [{}], e => [{}]", clazz, e);
			e.printStackTrace();
		}
		return null;
	}

	public static <T, S> List<T> copyInstanceList(Class<T> clazz, List<S> sourceList) {
		List<T> newList = new ArrayList<>(sourceList.size());

		for (Object source : sourceList) {
			newList.add(copyInstance(clazz, source));
		}

		return newList;
	}

}
