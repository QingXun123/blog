package com.qxbase.blog.server.essay.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.server.essay.mapper.EssayCommentMapper;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
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

    @Override
    public boolean addComment(EssayComment essayComment) {
        long userId = StpUtil.getLoginIdAsLong();
        if (!essayInfoService.existsById(essayComment.getEssayId())) {
            throw new ServiceException(300, "不存在该文章");
        }
        if (!userService.existsById(userId)) {
            throw new ServiceException(300, "不存在该用户");
        }
        return essayCommentService.save(essayComment);
    }

    @Override
    public List<EssayComment> getCommentListByEssayId(Long essayId) {
        return this.list(new LambdaQueryWrapper<EssayComment>().eq(EssayComment::getEssayId, essayId));
    }

    @Override
    public Long countByEssayId(Long essayId) {
        return this.count(new LambdaQueryWrapper<EssayComment>().eq(EssayComment::getEssayId, essayId));
    }
}
