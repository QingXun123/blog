package com.qxbase.blog.server.essay.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.data.dto.EssayInfoDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EssayCommentMapper extends BaseMapper<EssayComment> {

    IPage<EssayCommentVo> getCommentPage(Page<EssayCommentVo> page, @Param(Constants.WRAPPER) QueryWrapper<EssayComment> queryWrapper);

    IPage<EssayCommentVo> getCommentPageByUserId(Page<EssayCommentVo> page, @Param(Constants.WRAPPER) QueryWrapper<EssayComment> queryWrapper);

}
