package com.qxbase.blog.server.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
public class UserController {

    @ApiOperation("测试")
    @GetMapping("/test")
    public String test(MultipartFile multipartFile) {
        return "123";
    }
}
