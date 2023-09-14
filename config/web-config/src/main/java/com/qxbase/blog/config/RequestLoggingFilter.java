package com.qxbase.blog.config;


import com.qxbase.blog.config.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-08-01
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	/**
	 * 日志跟踪标识
	 */
	private static final String TRACE_ID = "TRACE_ID";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		String traceId = UUID.randomUUID().toString();
		if (StringUtils.isEmpty(MDC.get(TRACE_ID))) {
			MDC.put(TRACE_ID, traceId);
		}
		// 打印请求相关参数
		log.info("\n\n========================================== Start =========================================="
				// 打印请求 url
				+ "\nURL            : " + request.getRequestURL().toString()
				// 打印 Http method
				+ "\nHTTP Method    : " + request.getMethod()
				// 打印请求的 IP
				+ "\nFROM IP        : " + RequestUtil.getClientIp(request));
		// 打印请求入参
		// logger.info("Request Args   : {}", JSON.toJSON(requestWrapper.getBody()));

	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		if (message.contains("payload")) {
			message = message.replaceAll("payload=", "\npayload=\n");
		}

		log.info("\n" + message +
				"\n=========================================== End ===========================================\n\n");
		// 每个请求之间空一行
		MDC.remove(TRACE_ID);
	}

}
