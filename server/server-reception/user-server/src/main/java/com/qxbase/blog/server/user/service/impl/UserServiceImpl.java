package com.qxbase.blog.server.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.server.user.mapper.UserMapper;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(String email, String password) {
        User user = this.lambdaQuery().eq(User::getEmail, email).eq(User::getPassword, password).one();
        if (user != null) {
            throw new ServiceException(300, "邮箱或密码错误");
        }
        return user;
    }

    @Override
    public boolean register(User user) {
        return this.save(user);
    }

    @Override
    public boolean save(User user) {
        User oneByEmail = this.getOneByEmail(user.getEmail());
        if (oneByEmail != null) {
            throw new ServiceException(300, "已存在该用户");
        }
        return super.save(user);
    }

    @Override
    public User getOneByPhone(String phone) {
        return lambdaQuery()
                .eq(User::getPhone, phone)
                .one();
    }

    @Override
    public User getOneByEmail(String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .one();
    }
}
