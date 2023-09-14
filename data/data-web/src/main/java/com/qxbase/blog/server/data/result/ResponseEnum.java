package com.qxbase.blog.server.data.result;

/**
 * 计时平台对接通用响应
 *
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-07
 */
public enum ResponseEnum {

	ERROR_TEMP_TABLE_DONT_HAVE_OFFICIAL_TABLE(Response.CODE_FAIL, "该临时数据无对应正式表"),
	ERROR_TEMP_TABLE_NOT_FOUND(Response.CODE_FAIL, "该实体无临时表或未设置临时表"),
	ERROR_TEMP_TABLE_DATA_EXIST(Response.CODE_FAIL, "该数据已存在"),

	ERROR_OFFICIAL_SERVICE_NOT_FOUND(Response.CODE_FAIL, "无法找到正式服务"),
	DELETE_SERVICE_NOT_FOUND(Result.CODE_ERROR, "无法找到删除服务"),

	// ===================----------------------=================
	/* 学时模块 */
	// 驾培机构未备案
	ERROR_LOG_REVIEW_NOT_SUPERVISE(Response.CODE_FAIL, "未备案"),
	ERROR_LOG_REVIEW_COMPARISON_FAIL(Response.CODE_FAIL, "对比失败"),
	ERROR_LOG_REVIEW_SIMULTANEOUS(Response.CODE_FAIL, "存在同时"),

	// 计时终端
	ERROR_TERMINAL_BOUND(Result.CODE_FAIL, "终端已绑定"),
	ERROR_TERMINAL_NOT_BOUND(Result.CODE_FAIL, "终端未绑定"),
	ERROR_ABSENT(Result.CODE_FAIL, "终端不存在"),

	// 电子教学日志
	ERROR_ALREADY_EXISTS(Result.CODE_FAIL, "该条学时已存在"),

	// 删除功能
	ERROR_ALL_ALREADY_EXISTS(Result.CODE_FAIL, "该条数据不存在"),

	// 文件上传
	ERROR_GET_PATH(Result.CODE_FAIL, "获取文件上传oss路径失败"),
	ERROR_FILE_TYPE(Result.CODE_FAIL, "上传文件失败，文件业务类型错误"),
	ERROR_UPLOAD_SERVER(Result.CODE_FAIL, "上传文件失败，未能成功上传oss服务器"),
	ERROR_SAVE_DATABASE(Result.CODE_FAIL, "上传文件失败，未成功保存到数据库"),
	ERROR_UNKNOWN(Result.CODE_FAIL, "上传文件失败，系统未知异常"),
	ERROR_EXCEEDS_SIZE(Result.CODE_FAIL, "压缩文件大小超过最大大小(2Mb)"),
	ERROR_NO_EXTENSION(Result.CODE_FAIL, "文件没有扩展名");

	private final Integer code;
	private final String msg;

	ResponseEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
