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
import com.qxbase.blog.data.entity.EssayLike;
import com.qxbase.blog.data.vo.EssayCommentVo;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.essay.mapper.EssayCommentMapper;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import com.qxbase.blog.server.essay.service.IEssayLikeService;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EssayCommentServiceImpl extends ServiceImpl<EssayCommentMapper, EssayComment> implements IEssayCommentService {

    @Resource
    private IUserService userService;

    @Resource
    private IEssayInfoService essayInfoService;

    @Resource
    private IEssayCommentService essayCommentService;

    @Resource
    private IEssayLikeService essayLikeService;

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
    public IPage<EssayCommentVo> pageLJoinUser(Page page) {
        return essayCommentMapper.getCommentPage(page, new QueryWrapper<>());
    }

    @Override
    public IPage<EssayCommentVo> pageLJoinUser(Page page, QueryWrapper<EssayComment> queryWrapper) {
        return essayCommentMapper.getCommentPage(page, queryWrapper);
    }

    @Override
    public IPage<EssayCommentVo> getCommentPage(Page page) {
        if (page.getRecords().get(1) != null) {
            Object userId = page.getRecords().get(1);
            this.pageLJoinUser(page,
                    new QueryWrapper<EssayComment>()
                            .eq("tec.essay_id", page.getRecords().get(0))
                            .isNull("tec.reply_super_comment_id"));
            List<EssayCommentVo> records = page.getRecords();
            for (EssayCommentVo essayCommentVo : records) {
                EssayLike essayLike = essayLikeService.getOne(new LambdaQueryWrapper<EssayLike>()
                        .eq(EssayLike::getCommentId, essayCommentVo.getCommentId())
                        .eq(EssayLike::getUserId, userId));
                if (essayLike == null) {
                    continue;
                }
                Long likeId = essayLike.getLikeId();
                essayCommentVo.setLikeId(likeId);
            }
            return page;
        }
        return this.pageLJoinUser(page,
                new QueryWrapper<EssayComment>()
                        .eq("tec.essay_id", page.getRecords().get(0))
                        .isNull("tec.reply_super_comment_id"));
    }

    @Override
    public IPage<EssayCommentVo> getNextCommentPage(Page page) {
        if (page.getRecords().get(1) != null) {
            Object userId = page.getRecords().get(1);
            this.pageLJoinUser(page,
                    new QueryWrapper<EssayComment>()
                            .eq("tec.reply_super_comment_id", page.getRecords().get(0)));
            List<EssayCommentVo> records = page.getRecords();
            for (EssayCommentVo essayCommentVo : records) {
                EssayLike essayLike = essayLikeService.getOne(new LambdaQueryWrapper<EssayLike>()
                        .eq(EssayLike::getCommentId, essayCommentVo.getCommentId())
                        .eq(EssayLike::getUserId, userId));
                if (essayLike == null) {
                    continue;
                }
                Long likeId = essayLike.getLikeId();
                essayCommentVo.setLikeId(likeId);
            }
            return page;
        }
        return this.pageLJoinUser(page,
                new QueryWrapper<EssayComment>()
                        .eq("tec.reply_super_comment_id", page.getRecords().get(0)));
    }

    @Override
    public IPage<EssayCommentVo> pageByUserId(Page page) {
        return essayCommentMapper.getCommentPageByUserId(page, new QueryWrapper<>());
    }

    @Override
    public IPage<EssayCommentVo> pageByUserId(Page page, QueryWrapper<EssayComment> queryWrapper) {
        IPage page1 = this.page(page, new LambdaQueryWrapper<EssayComment>()
                .eq(EssayComment::getReplyCommentId, page.getRecords().get(0)));
        List<EssayCommentVo> records = page1.getRecords();
        for (EssayCommentVo essayCommentVo : records) {
            essayCommentVo.setLikeId(
                    essayLikeService.getOne(new LambdaQueryWrapper<EssayLike>()
                                    .eq(EssayLike::getCommentId, essayCommentVo.getReplyCommentId())
                                    .eq(EssayLike::getUserId, page.getRecords().get(1)))
                            .getLikeId());
        }
        page1.setRecords(records);
        return page1;
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
