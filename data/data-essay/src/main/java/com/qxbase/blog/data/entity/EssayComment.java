package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxbase.blog.server.data.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@ToString
@TableName("t_essay_comment")
@Accessors(chain = true)
public class EssayComment extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private Long essayId;

    private String content;

    private Integer like = 0;
}
