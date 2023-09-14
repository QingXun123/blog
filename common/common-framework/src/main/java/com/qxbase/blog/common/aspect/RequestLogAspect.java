package com.qxbase.blog.common.aspect;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-07
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

	@Around("execution(* com.qxbase.blog..server.controller..*.*(..))")
	public Object requestLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String mappingPath = request.getRequestURI();
		ApiOperation apiOperation = methodSignature.getMethod().getAnnotation(ApiOperation.class);
		String requestComment = "无";
		if (apiOperation != null) {
			requestComment = apiOperation.value();
		}

		Object[] args = joinPoint.getArgs();
		long start = System.currentTimeMillis();
		log.info("请求接收日志 - 接口信息: [method = [{}], comment = [{}], path = [{}], args = [{}] ] ",
				// joinPoint.getSignature()
				getLogLocality(joinPoint)
				, requestComment, mappingPath, args);
		Object proceed = joinPoint.proceed();
		String jsonProceed = JSON.toJSONString(proceed);
		if (jsonProceed.length() >= 60) {
			jsonProceed = jsonProceed.substring(0, 50) + "...}";
		}
		log.info("请求接收日志 - 接口响应信息: [ time = [{}], method = [{}], response = [{}] ] ",
				System.currentTimeMillis() - start, getLogLocality(joinPoint), jsonProceed);
		return proceed;
	}

	public String getLogLocality(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String clazzName = joinPoint.getTarget().getClass().getName();
		String clazzSimpleName = joinPoint.getTarget().getClass().getSimpleName();
		return clazzName +
				"." +
				methodSignature.getMethod().getName() +
				"(" + clazzSimpleName + ":0)";
	}

	public static void main(String[] args) {
		System.out.println("com.ygxc.dodobird.server.controller.SettlementController.pagePlatform(SettlementController:0)");
		System.out.println("com.ygxc.dodobird.server.controller.SettlementController.pagePlatform(SettlementController:)");
		System.out.println("com.ygxc.dodobird.server.controller.SettlementController.pagePlatform(SettlementController:50)");
	}

}
