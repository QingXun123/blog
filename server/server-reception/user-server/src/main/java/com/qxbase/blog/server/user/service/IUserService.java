package com.qxbase.blog.server.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.data.vo.UserInfoVo;

public interface IUserService extends IService<User> {

    UserInfoVo info();

    User login(String email, String password);

    boolean register(User user);

    User getOneByPhone(String phone);

    User getOneByEmail(String email);

    boolean checkUserByEmail(String email, String password);

    boolean existsById(Long userId);

    @Override
    default boolean save(User entity) {
        User oneByEmail = this.getOneByEmail(entity.getEmail());
        if (oneByEmail != null) {
            throw new ServiceException(300, "已存在该用户");
        }
        return IService.super.save(entity);
    }
}
