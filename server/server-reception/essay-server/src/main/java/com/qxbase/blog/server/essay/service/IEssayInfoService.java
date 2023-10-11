package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.dto.EssayInfoDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface IEssayInfoService extends IService<EssayInfo> {

    List<EssayInfo> getNewEssayList();

    List<EssayInfo> getEssayListByType(Integer type);

    IPage<EssayInfoDto> pageOfAuthor(Page page);

    IPage<EssayInfoDto> pageOfAuthor(Page page, QueryWrapper<EssayInfo> queryWrapper);

    boolean readEssay(Long essayId);

    boolean existsById(Long id);

    boolean addEssay(EssayInfo essayInfo);

    @Override
    default boolean save(EssayInfo entity) {
        entity.setReleaseTime(Timestamp.valueOf(LocalDateTime.now()));
        return IService.super.save(entity);
    }
}
