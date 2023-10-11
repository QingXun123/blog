package com.qxbase.blog.server.essay.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.dto.EssayInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EssayInfoMapper extends BaseMapper<EssayInfo> {

    IPage<EssayInfoDto> pageOfAuthor(Page<EssayInfoDto> page, @Param(Constants.WRAPPER) QueryWrapper<EssayInfo> queryWrapper);

}
