package com.example.cloverchatapp.global;

import com.example.cloverchatapp.web.websocket.ChatMessageSession;
import com.example.cloverchatapp.web.websocket.ChatRoomSession;
import com.example.cloverchatapp.web.websocket.ChatUserSession;

public class WebSocketSessionContext {

    public ChatRoomSession chatRoomSession;
    public ChatMessageSession messageSession;
    public ChatUserSession chatUserSession;
}
