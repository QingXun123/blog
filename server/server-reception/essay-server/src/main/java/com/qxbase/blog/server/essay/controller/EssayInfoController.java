package com.qxbase.blog.server.essay.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.dto.EssayAddDto;
import com.qxbase.blog.data.entity.EssayInfo;
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

    @ApiOperation("文章分页")
    @PostMapping("/page")
    public Result page(@RequestBody Page page) {
        return Result.rSuccess(essayInfoService.pageOfAuthor(page));
    }

    @ApiOperation("文章详情")
    @GetMapping("/info")
    public Result info(@RequestParam Long essayId) {
        return Result.rSuccess(essayInfoService.getById(essayId));
    }

    @ApiOperation("添加文章")
    @PostMapping("/addEssay")
    @SaCheckLogin
    public Result addEssay(@RequestBody EssayAddDto essayAddDto) {
        return Result.assertBool(
                essayInfoService.addEssay(
                        BeanUtils.copyInstance(EssayInfo.class, essayAddDto)),
                "添加成功", "添加失败");
    }

    @ApiOperation("获取最新文章列表")
    @GetMapping("/getNewEssayList")
    public Result getNewEssayList() {
        return Result.rSuccess(essayInfoService.getNewEssayList());
    }

    @ApiOperation("获取置顶文章列表")
    @GetMapping("/getTopEssayList")
    public Result getTopEssayList() {
        return Result.rSuccess(essayInfoService.getEssayListByType(2));
    }

    @ApiOperation("阅读文章")
    @GetMapping("/readEssay")
    public Result readEssay(@RequestParam Long essayId) {
        return Result.assertBool(essayInfoService.readEssay(essayId), "阅读成功", "阅读失败");
    }

    @ApiOperation("模糊查询文章")
    @PostMapping("/getEssayListLikeSubject")
    public Result getEssayListLikeSubject(@RequestBody Page page) {
        return Result.rSuccess(essayInfoService.pageOfAuthor(page, new QueryWrapper<EssayInfo>()
                .like("subject", page.getRecords().get(0))));
    }

}
