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

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    @Override
    default boolean save(User entity) {
        if (this.existsByEmail(entity.getEmail())) {
            throw new ServiceException(300, "该邮箱已存在");
        }
        if (this.existsByUserName(entity.getUserName())) {
            throw new ServiceException(300, "该用户名已存在");
        }
        return IService.super.save(entity);
    }
}
