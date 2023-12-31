package com.qxbase.blog.server.essay.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.dto.EssayCommentDeleteDto;
import com.qxbase.blog.data.dto.EssayCommentDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "评论接口")
@RequestMapping("/essayComment")
public class EssayCommentController {

    @Resource
    private IEssayCommentService essayCommentService;

    @ApiOperation("发布评论")
    @PostMapping("/addComment")
    @SaCheckLogin
    public Result addComment(@RequestBody EssayCommentDto essayCommentInPutVo) {
        return Result.rSuccess(
                essayCommentService.addComment(
                        BeanUtils.copyInstance(EssayComment.class, essayCommentInPutVo)),
                "发布成功");
    }

    @ApiOperation("根据文章id获取评论次数")
    @GetMapping("/getCountByEssayId")
    public Result getCountByEssayId(@RequestParam Long essayId) {
        return Result.rSuccess(essayCommentService.countByEssayId(essayId));
    }

    @ApiOperation("根据文章id获取评论分页")
    @PostMapping("/getCommentPage")
    public Result getCommentPage(@RequestBody Page page) {
        return Result.rSuccess(essayCommentService.getCommentPage(page));
    }

    @ApiOperation("根据评论id获取回复分页")
    @PostMapping("/getNextCommentPage")
    public Result getNextCommentPage(@RequestBody Page page) {
        return Result.rSuccess(essayCommentService.getNextCommentPage(page));
    }

    @ApiOperation("根据评论id获取回复数量")
    @GetMapping("/getCommentTotalByReplySuperCommentId")
    public Result getCommentTotalByReplySuperCommentId(@RequestParam Long commentId) {
        return Result.rSuccess(essayCommentService.count(new LambdaQueryWrapper<EssayComment>()
                .eq(EssayComment::getReplySuperCommentId, commentId)));
    }

    @ApiOperation("根据评论id获取回复数量")
    @GetMapping("/getCommentTotalByReplyCommentId")
    public Result getCommentTotalByReplyCommentId(@RequestParam Long commentId) {
        return Result.rSuccess(essayCommentService.count(new LambdaQueryWrapper<EssayComment>()
                .eq(EssayComment::getReplyCommentId, commentId)));
    }

    @ApiOperation("删除评论")
    @PostMapping("/deleteComment")
    @SaCheckLogin
    public Result deleteComment(@RequestBody EssayCommentDeleteDto essayCommentDeleteDto) {
        return Result.assertBool(essayCommentService.deleteComment(
                        BeanUtils.copyInstance(EssayComment.class, essayCommentDeleteDto)),
                "删除成功", "删除失败");
    }
}
