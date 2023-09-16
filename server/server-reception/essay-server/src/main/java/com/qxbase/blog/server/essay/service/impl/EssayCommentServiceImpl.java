package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.server.essay.mapper.EssayCommentMapper;
import com.qxbase.blog.server.essay.service.IEssayCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EssayCommentServiceImpl extends ServiceImpl<EssayCommentMapper, EssayComment> implements IEssayCommentService {

    @Override
    public List<EssayComment> getCommentListByEssayId(Long essayId) {
        return this.list(new LambdaQueryWrapper<EssayComment>().eq(EssayComment::getEssayId, essayId));
    }

    @Override
    public Long countByEssayId(Long essayId) {
        return this.count(new LambdaQueryWrapper<EssayComment>().eq(EssayComment::getEssayId, essayId));
    }
}
