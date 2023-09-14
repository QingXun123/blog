package com.qxbase.blog.common.utils;

import com.qxbase.blog.server.data.result.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-07-26
 */
@Slf4j
public class PageResultHolder {

	private static final ThreadLocal<Result> RESULT_LOCAL = new ThreadLocal<>();

	public static void setResult(Result result) {
		log.debug("Page util [set] Result...");
		RESULT_LOCAL.set(result);
	}

	public static Result getResult() {
		log.debug("Page util [get] Result...");
		return RESULT_LOCAL.get();
	}

	public static void removeResult() {
		log.debug("Page util [remove] Result...");
		RESULT_LOCAL.remove();
	}

	public static Result getResultAndRemove() {
		Result result = getResult();
		removeResult();
		return result;
	}


}
