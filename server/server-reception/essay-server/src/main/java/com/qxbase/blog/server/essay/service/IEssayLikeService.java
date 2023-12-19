package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.EssayLike;

public interface IEssayLikeService extends IService<EssayLike> {

    boolean like(EssayLike essayLike);

    boolean dislike(EssayLike essayLike);

    boolean existsByCommentIdAndUserId(EssayLike essayLike);
}
