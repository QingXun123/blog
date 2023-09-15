package com.qxbase.blog.server.reception.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试接口")
@RequestMapping("/user")
public class UserController {

    @ApiOperation("测试")
    @GetMapping("/test")
    public String test() {
        return "123";
    }
}
