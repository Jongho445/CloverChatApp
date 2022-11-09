package com.example.cloverchatapp.web.domain.board;

import com.example.cloverchatapp.util.MethodType;

public class StompUpdateChatRoom {

    public final MethodType type;
    public final ResponseChatRoom chatRoom;

    public StompUpdateChatRoom(MethodType type, ResponseChatRoom chatRoom) {
        this.type = type;
        this.chatRoom = chatRoom;
    }
}