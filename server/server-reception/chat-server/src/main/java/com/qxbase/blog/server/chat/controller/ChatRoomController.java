package com.qxbase.blog.server.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.qxbase.blog.data.vo.ChatUserInfoVo;
import com.qxbase.blog.server.chat.factory.WebSocket;
import com.qxbase.blog.server.data.result.Result;
import com.qxbase.blog.server.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "聊天室接口")
@RestController
@RequestMapping("/room")
public class ChatRoomController {

    @Resource
    private IUserService userService;

    @ApiOperation("获取当前在线用户数量")
    @GetMapping("/getOnlineCount")
    public Result getOnlineCount() {
        int onlineCount = WebSocket.getOnlineCount();
        return Result.rSuccess(onlineCount);
    }

    @ApiOperation("根据房间号获取当前在线用户")
    @GetMapping("/getOnlineUserByRoomId")
    public Result getOnlineUserByRoomId(String roomId) {
        Map<String, WebSocket> clients = WebSocket.getClients();
        List<ChatUserInfoVo> userChatInfoVos = new ArrayList<>();
        for (WebSocket item : clients.values()) {
            if (item.getRoomId().equals(roomId)) {
                if (item.getStatus().equals(0)) {
                    userChatInfoVos.add(new ChatUserInfoVo(item.getUserId()));
                } else {
                    ChatUserInfoVo e = BeanUtil.toBean(item.getUser(), ChatUserInfoVo.class);
                    if (e.getUserId() == 1) {
                        e.setStatus(2);
                    } else {
                        e.setStatus(1);
                    }
                    userChatInfoVos.add(e);
                }
            }
        }
        return Result.rSuccess(userChatInfoVos);
    }

    @ApiOperation("根据房间号获取当前在线用户数量")
    @GetMapping("/getOnlineCountByRoomId")
    public Result getOnlineCountByRoomId(String roomId) {
        int onlineCount = WebSocket.getOnlineCountByRoomId(roomId);
        return Result.rSuccess(onlineCount);
    }

    @ApiOperation("根据房间号获取当前在线用户列表")
    @GetMapping("/getOnlineUsersByRoomId")
    public Result getOnlineUsersByRoomId(String roomId) {
        return Result.rSuccess(WebSocket.getOnlineUsersByRoomId(roomId));
    }
}
