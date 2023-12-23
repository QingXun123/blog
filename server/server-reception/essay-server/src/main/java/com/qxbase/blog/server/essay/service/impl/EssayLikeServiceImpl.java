package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.EssayLike;
import com.qxbase.blog.server.essay.mapper.EssayLikeMapper;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import com.qxbase.blog.server.essay.service.IEssayLikeService;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("essayLikeService")
public class EssayLikeServiceImpl extends ServiceImpl<EssayLikeMapper, EssayLike> implements IEssayLikeService {

    @Resource
    private IUserService userService;

    @Resource
    private IEssayCommentService essayCommentService;

    @Override
    @Transactional
    public boolean like(EssayLike essayLike) {
        if (!userService.existsById(essayLike.getUserId())) {
            throw new ServiceException(300, "不存在该用户");
        }
        if (!essayCommentService.existsById(essayLike.getCommentId())) {
            throw new ServiceException(300, "不存在该评论");
        }
        if (this.existsByCommentIdAndUserId(essayLike)) {
            throw new ServiceException(300, "用户已点赞");
        }
        if (!this.save(essayLike)) {
            return false;
        }
        if (!essayCommentService.addLike(essayLike.getCommentId())) {
            throw new ServiceException(300, "评论点赞数量增加失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean dislike(EssayLike essayLike) {
        if (!userService.existsById(essayLike.getUserId())) {
            throw new ServiceException(300, "不存在该用户");
        }
        if (!essayCommentService.existsById(essayLike.getCommentId())) {
            throw new ServiceException(300, "不存在该评论");
        }
        if (!this.remove(new LambdaQueryWrapper<EssayLike>()
                .eq(EssayLike::getUserId, essayLike.getUserId())
                .eq(EssayLike::getCommentId, essayLike.getCommentId()))) {
            return false;
        }
        if (!essayCommentService.reduceLike(essayLike.getCommentId())) {
            throw new ServiceException(300, "评论点赞数量减少失败");
        }
        return true;
    }

    @Override
    public boolean existsByCommentIdAndUserId(EssayLike essayLike) {
        return lambdaQuery()
                .eq(EssayLike::getCommentId, essayLike.getCommentId())
                .eq(EssayLike::getUserId, essayLike.getUserId())
                .exists();
    }

    @Override
    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        return lambdaQuery()
                .eq(EssayLike::getCommentId, commentId)
                .eq(EssayLike::getUserId, userId)
                .exists();
    }
}
