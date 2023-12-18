package com.qxbase.blog.server.essay.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.data.dto.EssayInfoDto;
import com.qxbase.blog.data.entity.EssayComment;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EssayCommentMapper extends BaseMapper<EssayComment> {


    @Select("select\n" +
            "            tec.comment_id as commentId,\n" +
            "            tec.user_id as userId,\n" +
            "            tec.reply_comment_id as replyCommentId,\n" +
            "            tec.essay_id as essayId,\n" +
            "            tec.content as content,\n" +
            "            tec.type as type,\n" +
            "            tec.like as `like`,\n" +
            "            tec.create_time as createTime,\n" +
            "            tec.update_time as updateTime,\n" +
            "\n" +
            "            tu.user_name as userName\n" +
            "        from t_essay_comment tec\n" +
            "        left join t_user tu on tec.user_id = tu.user_id\n" +
            "        where\n" +
            "        tec.essay_id = #{essayId}")
    List<EssayCommentVo> getCommentVoListByEssayId(@Param("essayId") Long essayId);

    IPage<EssayCommentVo> getCommentPage(Page<EssayCommentVo> page, @Param(Constants.WRAPPER) QueryWrapper<EssayComment> queryWrapper);

}
