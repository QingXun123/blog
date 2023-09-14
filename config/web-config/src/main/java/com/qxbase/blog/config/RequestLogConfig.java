package com.qxbase.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-08-01
 */
@Configuration
public class RequestLogConfig {

	@Bean
	public AbstractRequestLoggingFilter commonsRequestLoggingFilter() {
		RequestLoggingFilter filter = new RequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(false);
		return filter;
	}

}
