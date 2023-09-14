package com.qxbase.blog.common.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-01
 */
@TableName("t_error_log")
@Data
public class ErrorLog {

	@TableId
	private Integer id;
	private Integer userId;
	private String action;
	private Integer type;
	private String message;
	private Integer errorCode;
	private Timestamp time;

	public ErrorLog() {
	}

	public ErrorLog(Integer userId, String action, Integer type, String message, Integer errorCode, Timestamp time) {
		this.userId = userId;
		this.action = action;
		this.type = type;
		this.message = message;
		this.errorCode = errorCode;
		this.time = time;
	}
}
