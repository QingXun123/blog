package com.qxbase.blog.server.chat.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.data.entity.ChatData;
import com.qxbase.blog.data.vo.ChatDataInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatDataMapper extends BaseMapper<ChatData> {
    List<ChatDataInfoVo> chatList(@Param(Constants.WRAPPER) QueryWrapper<ChatData> queryWrapper);
}
