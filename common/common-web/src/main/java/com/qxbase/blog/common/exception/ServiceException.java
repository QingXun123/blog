package com.qxbase.blog.common.exception;


import com.qxbase.blog.server.data.result.ResultEnum;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-05-31
 */
public class ServiceException extends RuntimeException {

	private ResultEnum resultEnum;
	private Integer code;
	private String msg;
	private Object data;

	public ServiceException() {
	}

	public ServiceException(ResultEnum resultEnum) {
		this(resultEnum.getCode(), resultEnum.getMsg());
		this.resultEnum = resultEnum;
	}

	public ServiceException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}


	public static ServiceException create(ResultEnum resultEnum) {
		return new ServiceException(resultEnum);
	}

	public static ServiceException create(Integer code, String msg) {
		return new ServiceException(code, msg);
	}

	public ResultEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public ServiceException setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "ServiceException{" +
				"resultEnum=" + resultEnum +
				", code=" + code +
				", msg='" + msg + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
