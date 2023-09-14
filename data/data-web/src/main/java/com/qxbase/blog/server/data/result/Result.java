package com.qxbase.blog.server.data.result;

import cn.hutool.core.convert.Convert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 通用响应
 *
 * @author ALsW
 * @version 1.0.0
 * @since 2023-05-30
 */
public class Result extends HashMap<String, Object> implements Serializable {


	/**
	 * 成功
	 */
	public final static Integer CODE_SUCCESS = 200;
	/**
	 * 自定义错误
	 */
	public final static Integer CODE_FAIL = 300;
	/**
	 * 参数绑定错误
	 */
	public final static Integer CODE_PARAM_BIND_FAIL = 302;
	/**
	 * 服务器错误
	 */
	public final static Integer CODE_ERROR = 500;
	/**
	 * 未知错误
	 */
	public final static Integer CODE_UNKNOWN = 600;


	private static final long serialVersionUID = -5730703174755243880L;

	public Result() {
		this.setCode(200);
		this.setMessage("ok");
	}

	public Result(Integer code, String msg) {
		this.setCode(code);
		this.setMessage(msg);
	}

	public Result(Integer code, String msg, Object data) {
		this.setCode(code);
		this.setMessage(msg);
		this.setData(data);
	}

	public Result putIn(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public Integer getCode() {
		return (Integer) this.get("code");
	}

	public void setCode(Integer code) {
		this.put("code", code);
	}

	public Object getData() {
		return this.get("data");
	}

	public <T> T getData(Class<T> dataType) {
		return get("data", dataType);
	}

	public <T> T get(String key, Class<T> dataType) {
		return Convert.convert(dataType, this.get(key));
	}

	public <T> List<T> getDataList(Class<T> dataType) {
		List<Object> resultList = Convert.convertByClassName(List.class.getName(), this.get("data"));
		if (resultList == null)
			return new ArrayList<>();
		List<T> dataList = new ArrayList<>(resultList.size());
		for (Object result : resultList) {
			dataList.add(Convert.convert(dataType, result));
		}
		return dataList;
	}

	public void setData(Object data) {
		this.put("data", data);
	}

	public String getMessage() {
		return (String) this.get("message");
	}

	public void setMessage(String msg) {
		this.put("message", msg);
	}

	public void success() {
		this.setCode(CODE_SUCCESS);
	}

	public void fail() {
		this.setCode(CODE_FAIL);
	}

	// assert ------------------------------------------------------

	public static Result assertNull(Object data, String error) {
		return data == null ? rError(error) : rSuccess(data);
	}

	public static Result assertBool(Boolean bool, String success, String error) {
		return !bool ? rError(error) : rSuccess(Boolean.TRUE, success);
	}

	public static Result assertBool(Boolean bool, ResultEnum success, ResultEnum error) {
		return !bool ? rError(error) : rSuccess(Boolean.TRUE, success);
	}

	public static Result assertBool(Boolean bool, ResultEnum success, ResultEnum error, Object data) {
		return !bool ? rError(error) : rSuccess(data, success);
	}


	// success ------------------------------------------------------

	public static Result rSuccess(Object data) {
		return new Result(CODE_SUCCESS, "ok", data);
	}

	public static Result rSuccess(Object data, String success) {
		return new Result(CODE_SUCCESS, success, data);
	}

	public static Result rSuccess(Object data, ResultEnum success) {
		return new Result(success.getCode(), success.getMsg(), data);
	}


	// error ------------------------------------------------------

	public static Result rError(String msg) {
		return new Result(CODE_FAIL, msg);
	}

	public static Result rError(ResultEnum error) {
		return rError(error.getCode(), error.getMsg());
	}

	public static Result rError(ResultEnum error, Object data) {
		return new Result(error.getCode(), error.getMsg(), data);
	}

	public static Result rError(Integer code, String msg) {
		return new Result(code, msg);
	}

	public static Result rErrorNull(ResultEnum error, Object data) {
		return rErrorNull(error.getCode(), error.getMsg(), data);
	}

	public static Result rErrorNull(Integer code, String msg, Object data) {
		return data != null ? Result.rError(code, msg, data)
				: Result.rError(code, msg);
	}

	public static Result rError(Integer code, String msg, Object data) {
		return new Result(code, msg, data);
	}

	// unknown ------------------------------------------------------

	public static Result rUnknown(Exception e) {
		return rError(CODE_UNKNOWN, "发生未知错误")
				.putIn("err", e.getMessage());
	}


	/**
	 * 是否操作成功，即code != 200
	 */
	public boolean judgeFailed() {
		return !judgeSuccess();
	}

	/**
	 * 是否操作成功，即code = 200
	 */
	public boolean judgeSuccess() {
		return CODE_SUCCESS.equals(getCode());
	}
}
