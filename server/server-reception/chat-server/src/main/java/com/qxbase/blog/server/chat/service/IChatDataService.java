package com.qxbase.blog.server.chat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.ChatData;
import com.qxbase.blog.data.vo.ChatDataInfoVo;

import java.util.List;

public interface IChatDataService extends IService<ChatData> {

    List<ChatDataInfoVo> chatListByRoomId(String roomId);

    Boolean addChatData(ChatData chatData);

}
