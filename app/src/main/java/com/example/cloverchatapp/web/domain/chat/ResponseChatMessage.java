package com.example.cloverchatapp.web.domain.chat;

import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.user.ResponseUser;

public class ResponseChatMessage {

    public final Long id;
    public final ResponseChatRoom chatRoom;
    public final ResponseUser createUser;
    public final String content;
    public final String createAt;

    public ResponseChatMessage(Long id, ResponseChatRoom chatRoom, ResponseUser createUser, String content, String createAt) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.createUser = createUser;
        this.content = content;
        this.createAt = createAt;
    }
}
