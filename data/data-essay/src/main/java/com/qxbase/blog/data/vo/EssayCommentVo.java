package com.qxbase.blog.data.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class EssayCommentVo {

    @TableId(type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private String userName;

    private String img;

    private Long replyCommentId;

    private Long replySuperCommentId;

    private Long essayId;

    private String content;

    private Integer type = 0;

    private Integer like = 0;

    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;
}
