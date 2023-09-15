package com.qxbase.blog.data.entity;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@ToString
@TableName("t_essay_info")
public class EssayInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(value = "essay_id", type = IdType.AUTO)
    private Long essayId;

    private Long userId;

    private String subject;

    private String content;

    private Integer readingQuantity;

    private Integer type;

    private Timestamp releaseTime;

    private Timestamp createTime;

    private Timestamp updateTime;
}
