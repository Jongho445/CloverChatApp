package com.example.cloverchatapp.web.domain.chat;

import com.example.cloverchatapp.util.MethodType;

public class StompUpdateChatUser {

    public final MethodType type;
    public final ResponseChatUser chatUser;

    public StompUpdateChatUser(MethodType type, ResponseChatUser chatUser) {
        this.type = type;
        this.chatUser = chatUser;
    }
}
