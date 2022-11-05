package com.example.cloverchatapp.web.domain.chat;

public class RequestStompChatMessage {
    public final Long chatRoomId;
    public final Long createUserId;
    public final String content;

    public RequestStompChatMessage(Long chatRoomId, Long createUserId, String content) {
        this.chatRoomId = chatRoomId;
        this.createUserId = createUserId;
        this.content = content;
    }
}
