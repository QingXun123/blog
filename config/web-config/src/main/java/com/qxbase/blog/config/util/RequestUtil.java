package com.qxbase.blog.config.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 获取客户端IP
	 *
	 * @param request 请求
	 * @return IP
	 */
	public static String getClientIp(HttpServletRequest request) {

		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");

		//logger.info("|X-Real-IP:{}|X-Forwarded-For:{}|", Xip, XFor);

		boolean skip = false;

		if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				XFor = XFor.substring(0, index);
				skip = true;
			} else {
				skip = true;
			}
		}

		String header = null;

		if (!skip) {

			XFor = Xip;

			if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
				return XFor;
			}
			if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
				XFor = request.getHeader("Proxy-Client-IP");
				header = "Proxy-Client-IP";
			}
			if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
				XFor = request.getHeader("WL-Proxy-Client-IP");
				header = "WL-Proxy-Client-IP";
			}
			if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
				XFor = request.getHeader("HTTP_CLIENT_IP");
				header = "HTTP_CLIENT_IP";
			}
			if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
				XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
				header = "HTTP_X_FORWARDED_FOR";
			}
			if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
				XFor = request.getRemoteAddr();
			}
		}

		return XFor;
	}

}
