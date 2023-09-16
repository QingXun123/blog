package com.qxbase.blog.server.essay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.data.entity.EssayBanner;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.service.IEssayBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "轮播图接口")
@RequestMapping("/essayBanner")
public class EssayBannerController {

    @Resource
    private IEssayBannerService essayBannerService;

    @ApiOperation("轮播图查询")
    @GetMapping("/getBanner")
    public Result getBanner() {
        return Result.rSuccess(essayBannerService.page(new Page<>(1, 5),
                new LambdaQueryWrapper<EssayBanner>().eq(EssayBanner::getStatus, 1)));
    }

}
