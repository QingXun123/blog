package com.qxbase.blog.server.chat.service.impl;

import com.qxbase.blog.server.chat.service.IChatRoomService;
import com.qxbase.blog.server.user.service.IUserService;

import javax.annotation.Resource;

public class ChatRoomServiceImpl implements IChatRoomService {

    @Resource
    private IUserService userService;

}
