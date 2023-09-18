package com.qxbase.blog.server.user.controller;

import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.data.vo.UserLoginInPutVo;
import com.qxbase.blog.data.vo.UserRegisterInPutVo;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginInPutVo userLoginInPutVo) {
        return Result.rSuccess(userService.login(
                userLoginInPutVo.getEmail(), userLoginInPutVo.getPassword()));
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterInPutVo userRegisterInPutVo) {
        return Result.rSuccess(userService.register(
                BeanUtils.copyInstance(
                        User.class,
                        userRegisterInPutVo)));
    }
}
