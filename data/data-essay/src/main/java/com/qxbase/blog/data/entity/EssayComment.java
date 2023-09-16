package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@TableName("t_essay_comment")
public class EssayComment {

    @TableId(type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private Long essayId;

    private String content;

    private Integer like;

    private Timestamp createTime;

    private Timestamp updateTime;
}
