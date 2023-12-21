package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EssayCommentDeleteDto {

    private Long commentId;

    private Long userId;
}
