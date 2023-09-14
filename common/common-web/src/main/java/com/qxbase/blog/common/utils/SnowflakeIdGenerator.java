package com.qxbase.blog.common.utils;

import java.util.Calendar;

/**
 * @ClassName SnowflakeIdGenerator
 * @Description 雪花算法 用于生成自定义前缀和位数的订单号 通过机器id全局唯一
 * @Author Qsh
 * @Date 2023/7/6 11:24
 * @Version 1.0
 */
public class SnowflakeIdGenerator {
	private final static long START_TIMESTAMP = 1609430400000L; // 起始时间戳，根据需求可以自定义

	private final long workerIdBits = 5L; // 机器ID所占位数
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits); // 最大机器ID
	private final long sequenceBits = 12L; // 序列号所占位数

	private final long workerIdShift = sequenceBits; // 机器ID左移位数
	private final long timestampShift = sequenceBits + workerIdBits; // 时间戳左移位数

	private long workerId; // 机器ID
	private long sequence = 0L; // 序列号
	private long lastTimestamp = -1L; // 上一次生成订单号的时间戳

	/**
	 * 构造函数，用于初始化机器ID
	 *
	 * @param workerId 机器ID
	 */
	public SnowflakeIdGenerator(long workerId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("Worker ID can't be greater than %d or less than 0", maxWorkerId));
		}
		this.workerId = workerId;
	}

	/**
	 * 生成下一个唯一的订单号
	 *
	 * @param prefix 自定义的年份前缀
	 * @return 订单号 默认返回17位的唯一单号 具体请加上前缀
	 */
	public synchronized String nextId(String prefix) {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate ID for %d milliseconds", lastTimestamp - timestamp));
		}

		if (timestamp == lastTimestamp) {
			sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		long orderId = ((timestamp - START_TIMESTAMP) << timestampShift) | (workerId << workerIdShift) | sequence;

		String orderIdStr = String.valueOf(orderId);

		return prefix + orderIdStr;
	}

	public synchronized String nextYearId() {
		return nextId(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	}


	/**
	 * 获取当前的时间戳
	 *
	 * @return 时间戳
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取下一毫秒的时间戳
	 *
	 * @param lastTimestamp 上一次生成订单号的时间戳
	 * @return 下一毫秒的时间戳
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	public static void main(String[] args) {
		SnowflakeIdGenerator snowflakeUtil = new SnowflakeIdGenerator(1); // 传入机器ID
		String orderId = snowflakeUtil.nextId("2023"); // 传入自定义的年份前缀和位数

		System.out.println("生成的订单号是：" + orderId);
		System.out.println("年份：" + Calendar.getInstance().get(Calendar.YEAR));
	}
}






