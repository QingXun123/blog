//package com.qxbase.blog.common.aspect;
//
//import com.qxbase.blog.common.utils.RedisUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//@Aspect
//@Component
//public class FlowAspect {
//
//    @Resource
//    private RedisUtils redisUtils;
//
//    @Around("execution(* com.qxbase.blog.server..controller..*.*(..))")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        Object proceed = pjp.proceed();
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String localAddr = request.getLocalAddr();
//        if (localAddr.equals("127.0.0.1") || localAddr.equals("0:0:0:0:0:0:0:1")) {
//            return proceed;
//        }
//        String method = request.getMethod();
//        String requestURI = request.getRequestURI();
//        String redisKey = "flow." + method + "." + requestURI + "." + localAddr;
//        if (redisUtils.get(redisKey) == null) {
//            redisUtils.set(redisKey, 1);
//        } else {
//            redisUtils.set(redisKey, redisUtils.get(redisKey, Integer.class) + 1);
//        }
//        return proceed;
//    }
//}
