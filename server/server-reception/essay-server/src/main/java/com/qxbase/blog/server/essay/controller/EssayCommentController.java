package com.qxbase.blog.server.essay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "评论接口")
@RequestMapping("/essayComment")
public class EssayCommentController {

    @Resource
    private IEssayCommentService essayCommentService;

    @ApiOperation("根据文章id获取评论列表")
    @GetMapping("/getCommentByEssayId")
    public Result getCommentByEssayId(@RequestParam Long essayId) {
        return Result.rSuccess(essayCommentService.getCommentListByEssayId(essayId));
    }

    @ApiOperation("根据文章id获取评论次数")
    @GetMapping("/getCountByEssayId")
    public Result getCountByEssayId(@RequestParam Long essayId) {
        return Result.rSuccess(essayCommentService.countByEssayId(essayId));
    }

}
