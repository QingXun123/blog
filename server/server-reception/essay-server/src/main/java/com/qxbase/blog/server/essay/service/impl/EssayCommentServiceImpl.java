package com.qxbase.blog.server.essay.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.dto.EssayCommentDeleteDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.vo.EssayCommentVo;
import com.qxbase.blog.server.essay.mapper.EssayCommentMapper;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EssayCommentServiceImpl extends ServiceImpl<EssayCommentMapper, EssayComment> implements IEssayCommentService {

    @Resource
    private IUserService userService;

    @Resource
    private IEssayInfoService essayInfoService;

    @Resource
    private IEssayCommentService essayCommentService;

    @Resource
    private EssayCommentMapper essayCommentMapper;

    @Override
    public EssayComment addComment(EssayComment essayComment) {
        long userId = StpUtil.getLoginIdAsLong();
        if (!essayInfoService.existsById(essayComment.getEssayId())) {
            throw new ServiceException(300, "不存在该文章");
        }
        if (!userService.existsById(userId)) {
            throw new ServiceException(300, "不存在该用户");
        }
        if (essayComment.getContent() == null || essayComment.getContent().isEmpty()) {
            throw new ServiceException(300, "没有填写评论内容");
        }
        if (!essayCommentService.save(essayComment)) {
            throw new ServiceException(300, "发布失败");
        }
        return essayComment;
    }

    @Override
    public Long countByEssayId(Long essayId) {
        return this.count(new LambdaQueryWrapper<EssayComment>().eq(EssayComment::getEssayId, essayId));
    }

    @Override
    public IPage<EssayCommentVo> getCommentPage(Page page) {
        return essayCommentMapper.getCommentPage(page, new QueryWrapper<>());
    }

    @Override
    public IPage<EssayCommentVo> getCommentPage(Page page, QueryWrapper<EssayComment> queryWrapper) {
        return essayCommentMapper.getCommentPage(page, queryWrapper);

    }

    @Override
    public IPage<EssayCommentVo> getCommentPageByUserId(Page page) {
        return essayCommentMapper.getCommentPageByUserId(page, new QueryWrapper<>());
    }

    @Override
    public IPage<EssayCommentVo> getCommentPageByUserId(Page page, QueryWrapper<EssayComment> queryWrapper) {
        return essayCommentMapper.getCommentPageByUserId(page, queryWrapper);
    }

    @Override
    public boolean addLike(Long commentId) {
        EssayComment byId = this.getById(commentId);
        if (byId == null) {
            throw new ServiceException(300, "不存在该评论");
        }
        byId.setLike(byId.getLike() + 1);
        return this.updateById(byId);
    }

    @Override
    public boolean reduceLike(Long commentId) {
        EssayComment byId = this.getById(commentId);
        if (byId == null) {
            throw new ServiceException(300, "不存在该评论");
        }
        byId.setLike(Math.max(byId.getLike() - 1, 0));
        return this.updateById(byId);
    }

    @Override
    public boolean existsById(Long commentId) {
        EssayComment byId = this.getById(commentId);
        return byId != null;
    }

    @Override
    public boolean deleteComment(EssayComment essayComment) {
        if (!this.existsById(essayComment.getCommentId())) {
            throw new ServiceException(300, "不存在该评论");
        }
        if (!userService.existsById(essayComment.getUserId())) {
            throw new ServiceException(300, "不存在该用户");
        }
        this.remove(new LambdaQueryWrapper<EssayComment>()
                .eq(EssayComment::getReplySuperCommentId, essayComment.getCommentId()));
        return this.removeById(essayComment.getCommentId());
    }


}
