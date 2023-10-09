package com.qxbase.blog.server.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.server.user.mapper.UserMapper;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    private static final Pattern EMAIL_NUMBER_PATTERN = Pattern.compile("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public User login(String email, String password) {
        if (!EMAIL_NUMBER_PATTERN.matcher(email).matches()) {
            throw new ServiceException(300, "邮箱格式错误");
        }
        User user = this.lambdaQuery().eq(User::getEmail, email).eq(User::getPassword, password).one();
        if (user == null) {
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
        if (!PHONE_NUMBER_PATTERN.matcher(phone).matches()) {
            throw new ServiceException(300, "手机号格式错误");
        }
        return lambdaQuery()
                .eq(User::getPhone, phone)
                .one();
    }

    @Override
    public User getOneByEmail(String email) {
        if (!EMAIL_NUMBER_PATTERN.matcher(email).matches()) {
            throw new ServiceException(300, "邮箱格式错误");
        }
        return lambdaQuery()
                .eq(User::getEmail, email)
                .one();
    }

    @Override
    public boolean checkUserByEmail(String email, String password) {
        if (!EMAIL_NUMBER_PATTERN.matcher(email).matches()) {
            throw new ServiceException(300, "邮箱格式错误");
        }
        return lambdaQuery()
                .eq(User::getEmail, email)
                .eq(User::getPassword, password)
                .one() != null;
    }

    @Override
    public boolean existsById(Long userId) {
        User byId = this.getById(userId);
        return byId != null;
    }
}
