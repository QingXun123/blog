package com.qxbase.blog.server.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.data.dto.UserLoginDto;
import com.qxbase.blog.data.dto.UserRegisterDto;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("用户详情")
    @GetMapping("/info")
    @SaCheckLogin
    public Result info() {
        return Result.rSuccess(userService.info());
    }

    @ApiOperation("登录")
    @PostMapping("/doLogin")
    public SaResult doLogin(@RequestBody UserLoginDto userLoginInPutVo) {
        User user = userService.login(
                userLoginInPutVo.getEmail(), userLoginInPutVo.getPassword());
        StpUtil.login(user.getUserId());
        return SaResult.ok("登录成功").setData(user);
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
    public Result register(@RequestBody UserRegisterDto userRegisterInPutVo) {
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
