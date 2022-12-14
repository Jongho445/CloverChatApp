package com.example.cloverchatapp.web.http.chat;

import androidx.core.util.Consumer;

import com.example.cloverchatapp.global.AuthContext;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.http.AbstractHttpClient;
import com.example.cloverchatapp.web.http.RetrofitClientBuilder;

import java.util.List;

import retrofit2.Response;

public class ChatHttpClient extends AbstractHttpClient {

    private final ChatRetrofitClient retrofitClient;

    public ChatHttpClient(AuthContext authContext) {
        this.retrofitClient = RetrofitClientBuilder.getRetrofitClient(authContext, ChatRetrofitClient.class);
    }

    public void getChatMessageList(RequestChatMessagesReadForm form, Consumer<Response<List<ResponseStompChatMessage>>> onResponse) {
        templateCallback(() -> retrofitClient.getChatMessageList(form), onResponse);
    }

    public void createChatMessage(Long chatRoomId, RequestStompChatMessage form, Consumer<Response<ResponseStompChatMessage>> onResponse) {
        templateCallback(() -> retrofitClient.createChatMessage(chatRoomId, form), onResponse);
    }

    public void getChatUserList(Long chatRoomId, Consumer<Response<List<ResponseChatUser>>> onResponse) {
        templateCallback(() -> retrofitClient.getChatUserList(chatRoomId), onResponse);
    }
}
