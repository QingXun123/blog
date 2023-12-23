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
@TableName("t_chat_data")
@Accessors(chain = true)
public class ChatData extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long chatDataId;

    private Long userId;

    private String roomId;

    private String chatContent;

    private Integer type;

}
