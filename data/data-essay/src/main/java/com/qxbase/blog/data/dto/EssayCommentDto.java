package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EssayCommentDto {

    private Long essayId;

    private String content;

    private Long userId;
}
