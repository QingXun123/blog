package com.qxbase.blog.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.common.aspect.annotation.PageResponse;
import com.qxbase.blog.common.utils.ApplicationUtils;
import com.qxbase.blog.common.utils.PageResultHolder;
import com.qxbase.blog.server.data.entity.BaseEntity;
import com.qxbase.blog.server.data.entity.PageParam;
import com.qxbase.blog.server.data.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分页 AOP
 *
 * @author ALsW
 * @since 2023-05-25
 */
@Aspect
@Component
public class PageAspect {

	private static final Logger logger = LoggerFactory.getLogger(PageAspect.class);

	@SuppressWarnings("unchecked")
	@Around("@annotation(com.qxbase.blog.common.aspect.annotation.PageResponse)")
	public Object pageAround(ProceedingJoinPoint joinPoint) {
		Method pageMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
		PageParam pageParam = (PageParam) joinPoint.getArgs()[0];

		PageResponse pageResponse = pageMethod.getDeclaredAnnotation(PageResponse.class);
		IService<BaseEntity> service = null;

		try {
			Class<?> serviceClazz = pageResponse.serviceClazz();
			String serviceName = pageResponse.service();
			if (serviceClazz != Void.class) {
				service = (IService<BaseEntity>) ApplicationUtils.getBeanByClass(serviceClazz);
			} else if (StringUtils.isNotBlank(serviceName)) {
				service = ApplicationUtils.getBeanByName(serviceName);
			}

			if (service == null) {
				return null;
			}

			IPage<BaseEntity> page;
			QueryWrapper<BaseEntity> searchWrapper;

			if (pageResponse.aliaClass() != Void.class) {
				searchWrapper = PageParam.buildSearchWrapper(pageParam, pageResponse.aliaClass());
			} else {
				searchWrapper = PageParam.buildSearchWrapper(pageParam);
			}

			logger.info("\n------ 开始分页 ------");

			page = service.page(pageParam.getIPage(),
					searchWrapper);

			Result result = PageParam.wrapperPage2Result(page);
			// .putIn("module", serviceName);

			PageResultHolder.setResult(result);
			Object proceed = joinPoint.proceed();
			logger.info("service -> [{}], pageParam -> [{}], " +
							"total -> [{}], recordsCount -> [{}]", serviceName, pageParam,
					page.getTotal(), page.getRecords().size());

			return proceed != null ? proceed : result;
		} catch (BadSqlGrammarException e) {
			logger.error(service + " SQL 语法错误\n [{}]", e.getMessage());
			return Result.rError("分页查询异常").putIn("err", e.getCause().getMessage());
		} catch (Throwable e) {
			logger.error(service + " 发生分页异常", e);
			return Result.rError("分页查询异常");
		} finally {
			PageResultHolder.removeResult();
			logger.info("\n------ 分页结束 ------\n");

		}

	}


}
