package com.qxbase.blog.server.chat.controller;

import com.qxbase.blog.common.utils.BeanUtils;
import com.qxbase.blog.data.dto.ChatDataAddVo;
import com.qxbase.blog.data.entity.ChatData;
import com.qxbase.blog.server.chat.service.IChatDataService;
import com.qxbase.blog.server.data.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "聊天室接口")
@RequestMapping("/chat")
public class ChatDataController {

    @Resource
    private IChatDataService chatDataService;

    @ApiOperation("获取聊天记录")
    @GetMapping("/getChatList")
    public Result getChatList() {
        return Result.rSuccess(chatDataService.chatListByRoomId("1"));
    }

    @ApiOperation("发送聊天消息")
    @PostMapping("/addChatData")
    public Result addChatData(@RequestBody ChatDataAddVo chatDataAddVo) {
        return Result.assertBool(
                chatDataService.addChatData(
                        BeanUtils.copyInstance(ChatData.class, chatDataAddVo)),
                "发送成功", "发送失败");
    }
}
