package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EssayLikeDto {

    private Long userId;

    private Long commentId;
}
