//package com.qxbase.blog.server.websocket.entity;
//
//import com.qxbase.blog.server.websocket.controller.WebSocket;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class WebSocketInstance {
//
//    private static ConcurrentHashMap<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
//
//    private static Map<String, Integer> onlineCountByRoomId = new ConcurrentHashMap<String, Integer>();
//
//    private static Integer onlineCount = 0;
//
//    public WebSocketInstance() {
//    }
//
//    public static ConcurrentHashMap<String, WebSocket> getClients() {
//        return clients;
//    }
//
//    public static void setClients(ConcurrentHashMap<String, WebSocket> clients) {
//        WebSocketInstance.clients = clients;
//    }
//
//    public static Map<String, Integer> getOnlineCountByRoomId() {
//        return onlineCountByRoomId;
//    }
//
//    public static void setOnlineCountByRoomId(Map<String, Integer> onlineCountByRoomId) {
//        WebSocketInstance.onlineCountByRoomId = onlineCountByRoomId;
//    }
//
//    public static Integer getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static void setOnlineCount(Integer onlineCount) {
//        WebSocketInstance.onlineCount = onlineCount;
//    }
//}
