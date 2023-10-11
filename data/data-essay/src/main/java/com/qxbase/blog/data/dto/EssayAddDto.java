package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EssayAddDto {

    private String img;

    private String subject;

    private String content;
}
