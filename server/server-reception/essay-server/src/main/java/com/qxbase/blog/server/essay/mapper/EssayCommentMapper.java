package com.qxbase.blog.server.essay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxbase.blog.data.entity.EssayComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EssayCommentMapper extends BaseMapper<EssayComment> {
}
