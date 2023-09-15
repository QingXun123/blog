package com.qxbase.blog.server.essay.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.server.data.entity.PageParam;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "文章接口")
@RequestMapping("/essayInfo")
public class EssayInfoController {

    @Resource
    private IEssayInfoService essayInfoService;

    @ApiOperation("文章列表")
    @PostMapping("/page")
    public Result page(@RequestBody Page page) {
        return Result.rSuccess(essayInfoService.page(page));
    }

    @ApiOperation("文章详情")
    @GetMapping("/info")
    public Result info(@RequestParam Long essayId) {
        return Result.rSuccess(essayInfoService.getById(essayId));
    }

    @ApiOperation("获取精选文章列表")
    @GetMapping("/getFeaturedEssayList")
    public Result getFeaturedEssayList() {
        return Result.rSuccess(essayInfoService.getEssayListByType(1));
    }

    @ApiOperation("获取置顶文章列表")
    @GetMapping("/getTopEssayList")
    public Result getTopEssayList() {
        return Result.rSuccess(essayInfoService.getEssayListByType(2));
    }

}
