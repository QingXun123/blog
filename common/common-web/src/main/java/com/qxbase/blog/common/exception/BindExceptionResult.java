package com.qxbase.blog.common.exception;


import lombok.Data;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-01
 */
@Data
public class BindExceptionResult {

	private String filed;
	private String result;
	private Object value;

}
