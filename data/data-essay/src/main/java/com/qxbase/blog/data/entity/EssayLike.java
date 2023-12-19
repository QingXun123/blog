package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxbase.blog.server.data.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@TableName("t_essay_comment_like")
@Accessors(chain = true)
public class EssayLike extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long likeId;

    private Long userId;

    private Long commentId;
}
