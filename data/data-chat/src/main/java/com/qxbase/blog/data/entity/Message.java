package com.qxbase.blog.data.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Message {

    private String msg;

    private String to;

    private String userId;

    private String type;
}
