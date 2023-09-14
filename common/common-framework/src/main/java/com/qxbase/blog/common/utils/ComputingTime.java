package com.qxbase.blog.common.utils;

import java.sql.Timestamp;

/**
 * @author Fkh
 * @version 1.0.0
 * @since 2023-06-08
 */
public class ComputingTime {

    /**
     * 截取两段日期 - 拼接成范围日期
     */
    public static String computingTime(Timestamp t1, Timestamp t2) {
        return t1.toString().substring(0, 10) + " ~ " +t2.toString().substring(0, 10);
    }

}
