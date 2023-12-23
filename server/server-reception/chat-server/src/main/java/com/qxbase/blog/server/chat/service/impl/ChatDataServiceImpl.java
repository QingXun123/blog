package com.qxbase.blog.server.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.ChatData;
import com.qxbase.blog.data.vo.ChatDataInfoVo;
import com.qxbase.blog.server.chat.mapper.ChatDataMapper;
import com.qxbase.blog.server.chat.service.IChatDataService;
import com.qxbase.blog.server.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatDataServiceImpl extends ServiceImpl<ChatDataMapper, ChatData> implements IChatDataService {

    @Resource
    private ChatDataMapper chatDataMapper;

    @Resource
    private IUserService userService;

    @Override
    public List<ChatDataInfoVo> chatListByRoomId(String roomId) {
        return chatDataMapper.chatList(new QueryWrapper<ChatData>().eq("tcd.room_id", roomId));
    }

    @Override
    public Boolean addChatData(ChatData chatData) {
        return this.save(chatData);
    }
}
