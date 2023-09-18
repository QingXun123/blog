package com.qxbase.blog.server.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.User;

public interface IUserService extends IService<User> {

    User login(String email, String password);

    boolean register(User user);

    boolean save(User user);

    User getOneByPhone(String phone);

    User getOneByEmail(String email);
}
