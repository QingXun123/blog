package com.qxbase.blog.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qxbase.blog.server.data.entity.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatDataInfoVo extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long chatDataId;

    private Long userId;

    private String roomId;

    private String chatContent;

    private Integer type;

    private String userName;
}
