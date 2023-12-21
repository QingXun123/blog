package com.qxbase.blog.server.essay.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.dto.EssayLikeDto;
import com.qxbase.blog.data.entity.EssayLike;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "点赞接口")
@RequestMapping("/essayLike")
public class EssayLikeController {

    @Resource
    private IEssayLikeService essayLikeService;

    @ApiOperation("根据文章id、用户id点赞")
    @PostMapping("/like")
    @SaCheckLogin
    public Result like(@RequestBody EssayLikeDto essayLikeDto) {
        return Result.assertBool(
                essayLikeService.like(BeanUtils.copyInstance(EssayLike.class, essayLikeDto)),
                "点赞成功", "点赞失败");
    }

    @ApiOperation("根据文章id、用户id取消点赞")
    @PostMapping("/dislike")
    public Result dislike(@RequestBody EssayLikeDto essayLikeDto) {
        return Result.assertBool(
                essayLikeService.dislike(BeanUtils.copyInstance(EssayLike.class, essayLikeDto)),
                "取消点赞成功", "取消点赞失败");
    }

    @ApiOperation("根据文章id、用户id查看用户是否点赞")
    @PostMapping("/isLike")
    public Result isLike(@RequestBody EssayLikeDto essayLikeDto) {
        return Result.assertBool(
                essayLikeService.existsByCommentIdAndUserId(BeanUtils.copyInstance(EssayLike.class, essayLikeDto)),
                "已点赞", "未点赞");
    }
}
