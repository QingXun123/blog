package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.EssayComment;

import java.util.List;

public interface IEssayCommentService extends IService<EssayComment> {

    boolean addComment(EssayComment essayComment);

    List<EssayComment> getCommentListByEssayId(Long essayId);

    Long countByEssayId(Long essayId);
}
