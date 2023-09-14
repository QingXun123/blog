package com.qxbase.blog.common.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-02
 */
@TableName("t_system_log")
@Data
public class SystemLog {

	private Long id;
	private Long userId;
	private String action;
	private String message;
	private Timestamp time;

}
