package com.qxbase.blog.server.chat.factory;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.common.utils.MyApplicationContextUtil;
import com.qxbase.blog.data.entity.ChatData;
import com.qxbase.blog.data.entity.User;
import com.qxbase.blog.server.chat.service.IChatDataService;
import com.qxbase.blog.server.user.service.IUserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
//解决bean注入
@DependsOn("myApplicationContextUtil")
@ServerEndpoint("/websocket/{roomId}/{userId}")
@CrossOrigin
public class WebSocket {

    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();

    private static Map<String, Integer> onlineCountByRoomId = new ConcurrentHashMap<String, Integer>();

    private static Integer onlineCount = 0;

    private Session session;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private String roomId;

    // status身份标识： 0-游客 1-普通用户 2-管理员
    @Getter
    @Setter
    private Integer status;

    private static IUserService userService = MyApplicationContextUtil.getBean(IUserService.class);

    private static IChatDataService chatDataService = MyApplicationContextUtil.getBean(IChatDataService.class);

    @OnOpen
    public void onOpen(@PathParam("roomId") String roomId, @PathParam("userId") String userId, Session session) throws IOException {
        this.roomId = roomId;
        this.userId = userId;
        this.session = session;

        if (userId == null || userId.equals("null") || userId.equals("undefined")) {
            this.status = 0;
            this.userId = "0" + RandomUtil.randomNumbers(6);
        } else if (userService.existsById(Long.parseLong(userId))) {
            this.status = 1;
            User byId = userService.getById(Long.parseLong(userId));
            this.user = byId;
        } else if (userId.equals("1")) {
            this.status = 2;
            User byId = userService.getById(Long.parseLong(userId));
            this.user = byId;
        }

//        addOnlineCount();
        addOnlineCountByRoomId(roomId);
        clients.put(this.userId, this);
        System.out.println("已连接");

    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(userId);
//        subOnlineCount();
        subOnlineCountByRoomId(roomId);
    }

    @OnMessage
//    @Transactional
    public void onMessage(String message) throws IOException {
        JSONObject jsonTo = JSONObject.parseObject(message);
        // type标识： 2001 一对一 2002 房间群发 2003 所有房间群发
        String type = jsonTo.getString("type");
        String userId = jsonTo.getString("userId");
        String to = jsonTo.getString("to");

        jsonTo.put("userName", getuserName());
        String jsonString = jsonTo.toJSONString();

        System.out.println(userId + " -> " + to + ": " + jsonString);

        if (type.equals("2001")) {
            sendMessageTo(jsonString, to);
        } else if (type.equals("2002")) {
            sendMessageToRoom(jsonString, to);
        } else if (type.equals("2003")) {
            sendMessageAll(jsonString);
        } else {
            throw new ServiceException(300, "错误的type");
        }

        ChatData chatData = new ChatData();
        chatData.setChatContent(jsonTo.getString("msg"));
        chatData.setUserId(Long.parseLong(this.userId));
        chatData.setRoomId(to);
        chatData.setType(jsonTo.getInteger("status"));
        chatDataService.save(chatData);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error:" + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);  
        //session.getAsyncRemote().sendText(message);  
        for (WebSocket item : clients.values()) {
            if (item.userId.equals(To))
                item.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendMessageToRoom(String message, String To) throws IOException {
        for (WebSocket item : clients.values()) {
            if (item.roomId.equals(To)) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

//    public static synchronized void addOnlineCount() {
//        WebSocket.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocket.onlineCount--;
//    }

    public static synchronized int getOnlineCountByRoomId(String roomId) {
        if (!WebSocket.onlineCountByRoomId.containsKey(roomId)) {
            throw new ServiceException(300, "该房间号不存在");
        }
        return onlineCountByRoomId.get(roomId);
    }

    public static synchronized void addOnlineCountByRoomId(String roomId) {
        if (!WebSocket.onlineCountByRoomId.containsKey(roomId)) {
            WebSocket.onlineCountByRoomId.put(roomId, 1);

        }
        WebSocket.onlineCountByRoomId.put(roomId, WebSocket.onlineCountByRoomId.get(roomId) + 1);
        onlineCount++;
    }

    public static synchronized void subOnlineCountByRoomId(String roomId) {
        if (!WebSocket.onlineCountByRoomId.containsKey(roomId)) {
            throw new ServiceException(300, "该房间号不存在");
        }
        if (WebSocket.onlineCountByRoomId.get(roomId) == 1) {
            WebSocket.onlineCountByRoomId.remove(roomId);
        } else {
            WebSocket.onlineCountByRoomId.put(roomId, WebSocket.onlineCountByRoomId.get(roomId) - 1);
        }
        onlineCount--;
    }

    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }

    public static List<String> getOnlineUsers() {
        // 获取Map的所有键并转换为List
        List<String> keyList = new ArrayList<>(clients.keySet());
        return keyList;
    }

    public static List<String> getOnlineUsersByRoomId(String roomId) {
        List<String> keyList = new ArrayList<>();
        for (WebSocket item : clients.values()) {
            if (item.roomId == roomId) {
                keyList.add(item.userId);
            }
        }
        return keyList;
    }

    private String getuserName() {
        switch (this.status) {
            case 0:
                return this.userId;
            case 1:
                return this.user.getUserName();
            case 2:
                return "【大坤吧管理员】" + this.user.getUserName();
            default:
                throw new ServiceException(300, "该身份不存在");
        }
    }


}  