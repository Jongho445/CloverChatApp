package com.example.cloverchatapp.web.chat;

public class RequestChatMessage {
    public final Long chatRoomId;
    public final Long createUserId;
    public final String content;

    public RequestChatMessage(Long chatRoomId, Long createUserId, String content) {
        this.chatRoomId = chatRoomId;
        this.createUserId = createUserId;
        this.content = content;
    }
}
