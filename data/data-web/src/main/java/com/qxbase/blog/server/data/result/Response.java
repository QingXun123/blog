package com.qxbase.blog.server.data.result;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 计时平台对接通用响应
 *
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-07
 */
public class Response extends HashMap<String, Object> implements Serializable {

    /**
     * 成功
     */
    public final static Integer CODE_SUCCESS = 0;
    /**
     * 失败
     */
    public final static Integer CODE_FAIL = 1;
    /**
     * 请求的服务/资源不存在
     */
    public final static Integer CODE_NOT_FOUND = 100;
    /**
     * 数据格式错误，无法正确解析
     */
    public final static Integer CODE_PARAM_ERROR = 200;
    /**
     * 时间戳重复或错误
     * 时间戳重复或与真实时间误差大于3min
     */
    public final static Integer CODE_TIMESTAMP_REPEAT = 201;
    /**
     * 证书无法通过验证
     */
    public final static Integer CODE_USER_CERT_ERROR = 202;

    /**
     * 服务连接异常
     */
    public final static Integer CODE_SERVER_CONNECTION_ERROR = 210;
    /**
     * 学员未在该驾校报名
     */
    public final static Integer CODE_STUDENT_NOT_REGISTERED = 211;
    /**
     * 学员已打卡，请勿重复打卡
     */
    public final static Integer CODE_STUDENT_ALREADY_CLOCK_IN = 222;
    /**
     * 未找到该设备
     */
    public final static Integer CODE_DEVICE_NOT_FOUND = 223;
    /**
     * 该设备未注册
     */
    public final static Integer CODE_DEVICE_NOT_REGISTERED = 224;
    /**
     * 签名错误
     */
    public final static Integer CODE_SIGNATURE_ERROR = 225;
    /**
     * 该设备已注册
     */
    public final static Integer CODE_DEVICE_HAS_REGISTERED = 226;


    private static final long serialVersionUID = -5730703174755243880L;

    public Response() {
        this.setCode(200);
        this.setMessage("ok");
    }

    public Response(Integer code, String msg) {
        this.setCode(code);
        this.setMessage(msg);
    }

    public Response(Integer code, String msg, Object data) {
        this.setCode(code);
        this.setMessage(msg);
        this.setData(data);
    }

    public static Response rUnknown(Exception e) {
        return rError(CODE_FAIL, "系统异常");
    }

    public Response putIn(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Integer getCode() {
        return (Integer) this.get("errorcode");
    }

    public void setCode(Integer code) {
        this.put("errorcode", code);
    }

    public Object getData() {
        return this.get("data");
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
        this.setCode(200);
    }


    // assert ------------------------------------------------------

    public static Response assertNull(Object data, String error) {
        return data == null ? rError(error) : rSuccess(data);
    }

    public static Response assertBool(Boolean bool, ResponseEnum success, ResponseEnum error) {
        return !bool ? rError(error) : rSuccess(Boolean.TRUE, success);
    }

    public static Response assertBool(Boolean bool, String success, String error) {
        return !bool ? rError(error) : rSuccess(Boolean.TRUE, success);
    }

    public static Response assertBool(Boolean bool, ResponseEnum success, ResponseEnum error, Object data) {
        return !bool ? rError(error) : rSuccess(data, success);
    }


    // success ------------------------------------------------------

    public static Response rSuccess() {
        return new Response(CODE_SUCCESS, "ok", null);
    }

    public static Response rSuccess(Object data) {
        return new Response(CODE_SUCCESS, "ok", data);
    }

    public static Response rSuccess(Object data, String msg) {
        return new Response(CODE_SUCCESS, msg, data);
    }

    private static Response rSuccess(Object data, ResponseEnum success) {
        return new Response(success.getCode(), success.getMsg(), data);
    }

    // error ------------------------------------------------------

    public static Response rError(String msg) {
        return new Response(CODE_FAIL, msg);
    }

    public static Response rError(ResponseEnum error) {
        return rError(error.getCode(), error.getMsg());
    }

    public static Response rError(ResponseEnum error, Object data) {
        return new Response(error.getCode(), error.getMsg(), data);
    }

    public static Response rError(Integer code, String msg) {
        return new Response(code, msg);
    }

	public static Response rErrorNull(ResponseEnum error, Object data) {
		return rErrorNull(error.getCode(), error.getMsg(), data);
	}

	public static Response rErrorNull(Integer code, String msg, Object data) {
		return data != null ? Response.rError(code, msg, data)
				: Response.rError(code, msg);
	}

    public static Response rError(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }


    /**
     * 是否操作成功，即code != 0
     */
    public boolean judgeFailed() {
        return !judgeSuccess();
    }

    /**
     * 是否操作成功，即code = 0
     */
    public boolean judgeSuccess() {
        return CODE_SUCCESS.equals(getCode());
    }


}
