package com.example.cloverchatapp.web.domain.chat;

import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.user.ResponseUser;

public class ResponseChatUser {

    public final Long id;
    public final ResponseChatRoom chatRoom;
    public final ResponseUser user;

    public ResponseChatUser(Long id, ResponseChatRoom chatRoom, ResponseUser user) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.user = user;
    }
}
