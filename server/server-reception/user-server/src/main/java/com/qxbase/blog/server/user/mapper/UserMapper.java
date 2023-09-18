package com.qxbase.blog.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxbase.blog.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
