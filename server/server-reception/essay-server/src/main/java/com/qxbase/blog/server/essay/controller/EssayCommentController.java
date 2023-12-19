package com.qxbase.blog.server.essay.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.dto.EssayCommentDto;
import com.qxbase.blog.data.vo.EssayCommentVo;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

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
    @PostMapping("/getcommentPage")
    public Result getcommentPage(@RequestBody Page page) {
        return Result.rSuccess(essayCommentService.getCommentPage(page,
                new QueryWrapper<EssayComment>()
                        .eq("tec.essay_id", page.getRecords().get(0))
                        .isNull("tec.reply_super_comment_id")));
    }

    @ApiOperation("根据评论id获取回复分页")
    @PostMapping("/getNextCommentPage")
    public Result getNextCommentPage(@RequestBody Page page) {
        IPage<EssayCommentVo> commentPage = essayCommentService.getCommentPage(page,
                new QueryWrapper<EssayComment>()
                        .eq("tec.reply_super_comment_id", page.getRecords().get(0)));
        return Result.rSuccess(commentPage);
    }

    @ApiOperation("根据评论id获取回复数量")
    @GetMapping("/getDoubleNextCommentDoubleTotal")
    public Result getDoubleNextCommentDoubleTotal(@RequestParam Long commentId) {
        return Result.rSuccess(essayCommentService.count(new LambdaQueryWrapper<EssayComment>()
                .eq(EssayComment::getReplySuperCommentId, commentId)));
    }

}
