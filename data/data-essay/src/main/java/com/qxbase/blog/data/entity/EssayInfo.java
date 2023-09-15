package com.qxbase.blog.data.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("t_essay_info")
public class EssayInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private Long essayId;

    private Long authorId;

    private String subject;

    private String content;

    private Integer readingQuantity;

    private Integer type;

    private DateTime releaseTime;

    private DateTime createTime;

    private DateTime updateTime;
}
