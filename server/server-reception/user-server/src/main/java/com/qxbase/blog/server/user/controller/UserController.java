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
import com.qxbase.blog.server.user.utils.IdentifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

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
    public Result register(@RequestBody UserRegisterDto userRegisterInPutVo, HttpSession session) {
        //从session中取出验证码
        String sessionCode = (String)session.getAttribute("identifyCode");
        if (!userRegisterInPutVo.getIdentifyCode().equalsIgnoreCase(sessionCode)){
            return Result.rError("验证码错误");
        }
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

    /**
     * 给前端返回一个验证码图片
     * @return
     */
    @GetMapping("/identifyImage")
    @ApiOperation("图形验证码")
    public void identifyImage(HttpServletResponse response, HttpSession session){
        //创建随机验证码
        String identifyCode = IdentifyCodeUtils.getIdentifyCode();
        //session存入验证码
        session.setAttribute("identifyCode", identifyCode);
        //根据验证码创建图片
        BufferedImage identifyImage = IdentifyCodeUtils.getIdentifyImage(identifyCode);
        //回传给前端
        IdentifyCodeUtils.responseIdentifyImg(identifyImage,response);

    }
}
