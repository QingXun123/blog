package com.qxbase.blog.server.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.data.vo.UserInfoVo;
import com.qxbase.blog.server.user.mapper.UserMapper;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

import static com.qxbase.blog.common.constant.PatternConstant.EMAIL_NUMBER_PATTERN;
import static com.qxbase.blog.common.constant.PatternConstant.PHONE_NUMBER_PATTERN;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserInfoVo info() {
        long userId = StpUtil.getLoginIdAsLong();
        User user = this.getById(userId);
        if (user == null) {
            throw new ServiceException(300, "不存在该用户");
        }
        return BeanUtils.copyInstance(UserInfoVo.class, user);
    }

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

    @Override
    public boolean existsByUserName(String userName) {
        return lambdaQuery()
                .eq(User::getUserName, userName)
                .exists();
    }

    @Override
    public boolean existsByEmail(String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .exists();
    }
}
