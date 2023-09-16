package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@TableName("t_essay_banner")
public class EssayBanner {

    @TableId(type = IdType.AUTO)
    private Long bannerId;

    private String url;

    private String img;

    private Integer status;

    private Timestamp createTime;

    private Timestamp updateTime;
}
