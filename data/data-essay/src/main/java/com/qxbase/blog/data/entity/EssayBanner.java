package com.qxbase.blog.data.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
@TableName("t_essay_banner")
public class EssayBanner extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long bannerId;

    private String url;

    private String img;

    private Integer status;

}
