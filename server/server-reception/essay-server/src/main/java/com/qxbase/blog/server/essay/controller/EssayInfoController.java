package com.qxbase.blog.server.essay.controller;

import com.qxbase.blog.server.data.entity.PageParam;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "文章接口")
@RequestMapping("/essayInfo")
public class EssayInfoController {

    @Resource
    private IEssayInfoService essayInfoService;

    @ApiOperation("文章列表")
    @PostMapping("/page")
    public Result page(String s) {
        return Result.rSuccess(s);
    }
}
