package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayInfoOutPutVo;

import java.util.List;

public interface IEssayInfoService extends IService<EssayInfo> {

    List<EssayInfo> getEssayListByType(Integer type);

    IPage<EssayInfoOutPutVo> pageOfAuthor(Page page);
    IPage<EssayInfoOutPutVo> pageOfAuthor(Page page, QueryWrapper<EssayInfo> queryWrapper);

    boolean readEssay(Long essayId);
}
