package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.dto.EssayInfoDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayCommentVo;

import java.util.List;

public interface IEssayCommentService extends IService<EssayComment> {

    boolean addComment(EssayComment essayComment);

    List<EssayCommentVo>  getCommentListByEssayId(Long essayId);

    Long countByEssayId(Long essayId);

    IPage<EssayCommentVo> getCommentPage(Page page);

    IPage<EssayCommentVo> getCommentPage(Page page, QueryWrapper<EssayComment> queryWrapper);

    boolean likeClick(Long commentId);
}
