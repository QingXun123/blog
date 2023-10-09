package com.qxbase.blog.server.user.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.data.vo.UserLoginInPutVo;
import com.qxbase.blog.data.vo.UserRegisterInPutVo;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("登录")
    @PostMapping("/doLogin")
    public Result doLogin(@RequestBody UserLoginInPutVo userLoginInPutVo) {
        User user = userService.login(
                userLoginInPutVo.getEmail(), userLoginInPutVo.getPassword());
        StpUtil.login(user.getUserId());
        return Result.rSuccess(user);
    }

    @ApiOperation("是否登录")
    @GetMapping("isLogin")
    public Result isLogin() {
        SaSession anonTokenSession = StpUtil.getAnonTokenSession();
        log.info("用户token: " + anonTokenSession.getToken());
        return Result.rSuccess(StpUtil.isLogin());
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterInPutVo userRegisterInPutVo) {
        return Result.rSuccess(userService.register(
                BeanUtils.copyInstance(
                        User.class,
                        userRegisterInPutVo)));
    }

    @ApiOperation("注销")
    @GetMapping("/logout")
    public Result logout() {
        StpUtil.logout(StpUtil.getLoginId());
        return this.isLogin();
    }
}
