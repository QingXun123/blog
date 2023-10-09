package com.qxbase.blog.data.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EssayCommentInPutVo {

    private Long userId;

    private Long essayId;

    private String content;
}
