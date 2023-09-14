package com.qxbase.blog.server.data.result;


/**
 * 通用响应枚举
 *
 * @author ALsW
 * @version 1.0.0
 * @since 2023-05-31
 */
public enum ResultEnum {

	MARKET_PLATFORM_NOT_FOUND(Result.CODE_FAIL, "该营销平台不存在"),
	MARKET_PLATFORM_EXIST(Result.CODE_FAIL, "已存在该营销平台"),

	MARKET_USER_PHONE_NOT_NULL(Result.CODE_FAIL, "线索手机号不能为空"),

	COUPON_BATCH_NOT_EXIST(Result.CODE_FAIL, "不存在该优惠券批次"),
	COUPON_BATCH_NUM_EXIST(Result.CODE_FAIL, "已存在该优惠券批次编号"),
	COUPON_BATCH_COUPON_ENABLE_FAIL(Result.CODE_FAIL, "该批次下优惠券启用失败"),
	COUPON_BATCH_COUPON_FORBIDDEN_FAIL(Result.CODE_FAIL, "该批次下优惠券禁用失败"),
	COUPON_USER_NOT_OWNED(Result.CODE_FAIL, "用户没有该优惠券"),

	COUPON_IS_USED(Result.CODE_FAIL, "已使用"),
	COUPON_IS_FORBIDDEN(Result.CODE_FAIL, "无法使用"),
	COUPON_NOT_VALID_TIME(Result.CODE_FAIL, "未到使用期"),
	COUPON_IS_EXPIRE(Result.CODE_FAIL, "已过期"),

	COUPON_BATCH_STOCK_EMPTY(Result.CODE_FAIL, "该优惠券批次已全部领完"),
	COUPON_BATCH_IS_FORBIDDEN(Result.CODE_FAIL, "无法领取该优惠券批次下的优惠券"),
	COUPON_BATCH_IN_USER_EXIST(Result.CODE_FAIL, "用户已领取过该优惠券批次下的优惠券"),
	COUPON_DRAW_FAIL(Result.CODE_FAIL, "优惠券领取失败"),

	USER_NOT_FOUND(Result.CODE_FAIL, "用户不存在"),
	USER_EXIST(Result.CODE_FAIL, "用户已存在"),
	USER_PHONE_EXIST(Result.CODE_FAIL, "手机号已存在");


	private final Integer code;
	private final String msg;

	ResultEnum(Integer code, String msg) {
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
