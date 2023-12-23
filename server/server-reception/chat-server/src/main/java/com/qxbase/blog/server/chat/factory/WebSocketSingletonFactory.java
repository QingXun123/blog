package com.qxbase.blog.server.chat.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSingletonFactory {

    private static final Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();

    private WebSocketSingletonFactory() {
        // 私有构造方法，防止外部实例化
    }

    public static synchronized Map<String, WebSocket> getInstance() {
        return clients;
    }

}
