package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.dto.EssayCommentDeleteDto;
import com.qxbase.blog.data.dto.EssayInfoDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayCommentVo;

import java.util.List;

public interface IEssayCommentService extends IService<EssayComment> {

    EssayComment addComment(EssayComment essayComment);

    Long countByEssayId(Long essayId);

    IPage<EssayCommentVo> pageLJoinUser(Page page);

    IPage<EssayCommentVo> pageLJoinUser(Page page, QueryWrapper<EssayComment> queryWrapper);

    IPage<EssayCommentVo> getCommentPage(Page page);

    IPage<EssayCommentVo> getNextCommentPage(Page page);

    IPage<EssayCommentVo> pageByUserId(Page page);

    IPage<EssayCommentVo> pageByUserId(Page page, QueryWrapper<EssayComment> queryWrapper);

    boolean addLike(Long commentId);

    boolean reduceLike(Long commentId);

    boolean existsById(Long commentId);

    boolean deleteComment(EssayComment essayComment);
}
