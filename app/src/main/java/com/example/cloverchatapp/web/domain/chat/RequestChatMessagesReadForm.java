package com.example.cloverchatapp.web.domain.chat;

import com.example.cloverchatapp.web.domain.board.ChatRoomType;

public class RequestChatMessagesReadForm {

    public final Long chatRoomId;
    public final ChatRoomType type;
    public final String password;

    public RequestChatMessagesReadForm(Long chatRoomId, ChatRoomType type, String password) {
        this.chatRoomId = chatRoomId;
        this.type = type;
        this.password = password;
    }
}
