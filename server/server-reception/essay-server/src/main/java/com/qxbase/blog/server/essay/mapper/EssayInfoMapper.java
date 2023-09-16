package com.qxbase.blog.server.essay.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayInfoOutPutVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EssayInfoMapper extends BaseMapper<EssayInfo> {

    List<EssayInfoOutPutVo> selectPage(@Param("current") long current, @Param("size") long size, @Param(Constants.WRAPPER) QueryWrapper<EssayInfo> queryWrapper);

}
