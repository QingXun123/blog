package com.qxbase.blog.common.exception.handler;

import com.qxbase.blog.common.exception.BindExceptionResult;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.server.data.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-05-31
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// @Resource
	// private IErrorLogService errorLogService;

	@ExceptionHandler(ServiceException.class)
	public Result serviceExceptionHandler(ServiceException e) {
		log.error("[qxBase博客] 发生服务异常 result =>[{}], code =>[{}], msg => [{}], data => [{}]",
				e.getResultEnum(), e.getCode(), e.getMsg(), e.getData());
		log.error("[qxBase博客] 发生服务异常", e);
		return returnResult(e);
	}

	@ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
	public Result bindExceptionHandler(Exception e) {
		List<ObjectError> errors = new ArrayList<>();
		if (e instanceof BindException) {
			errors = ((BindException) e).getAllErrors();
			log.error("参数绑定异常 [{}]", errors);
		} else if (e instanceof MethodArgumentNotValidException) {
			errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
			log.error("参数绑定异常 [{}]", errors);
		}

		List<BindExceptionResult> resultList = new ArrayList<>(errors.size());

		for (ObjectError error : errors) {
			BindExceptionResult result = new BindExceptionResult();
			if (error instanceof FieldError) {
				FieldError fieldError = (FieldError) error;
				result.setFiled(fieldError.getField());
				result.setValue(fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue());
				result.setResult(fieldError.getDefaultMessage());
			}

			resultList.add(result);
		}
		saveErrorLog(e);
		return Result.rError(Result.CODE_PARAM_BIND_FAIL, "参数绑定错误", resultList);
	}

	// @ExceptionHandler(SQLSyntaxErrorException.class)
	// public Result sqlSyntaxErrorExceptionHandler(SQLSyntaxErrorException e){
	// 	// e.
	// }

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return Result.rError(600, "不支持的请求方法 [" + e.getMethod() + "]");
	}

	@ExceptionHandler(Exception.class)
	public Result unknownExceptionHandler(Exception e) {
		log.error("发生未知异常", e);
		saveErrorLog(e);
		return Result.rUnknown(e);
	}

	private Result returnResult(ServiceException e) {
		saveErrorLog(e);

		if (e.getResultEnum() == null) {
			return Result.rErrorNull(e.getCode(), e.getMsg(), e.getData());
		}

		return Result.rErrorNull(e.getResultEnum(), e.getData());
	}

	private void saveErrorLog(Exception e) {
		log.debug("保存错误日志");
		// ErrorLog errorLog = new ErrorLog();
		// errorLog.setAction(JSON.toJSONString(e.getStackTrace()));
		//
		// errorLog.setUserId(1);
		// if (e instanceof ServiceException) {
		// 	ServiceException serviceException = (ServiceException) e;
		// 	errorLog.setErrorCode(serviceException.getCode());
		// 	errorLog.setMessage(serviceException.getMsg() + " data:[ " + serviceException.getData() + "]");
		// 	errorLog.setType(1);
		// } else {
		// 	errorLog.setErrorCode(Result.CODE_UNKNOWN);
		// 	errorLog.setMessage(e.getMessage());
		// 	errorLog.setType(0);
		// }
		//
		// errorLog.setTime(Timestamp.valueOf(LocalDateTime.now()));
		// errorLogService.save(errorLog);
	}

}
