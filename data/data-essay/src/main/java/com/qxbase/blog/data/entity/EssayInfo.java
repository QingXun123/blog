package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableId(type = IdType.AUTO)
    private Long essayId;

    private Long userId;

    private String img;

    private String subject;

    private String content;

    private Integer readingQuantity;

    private Integer type;

    private Timestamp releaseTime;

    private Timestamp createTime;

    private Timestamp updateTime;
}
