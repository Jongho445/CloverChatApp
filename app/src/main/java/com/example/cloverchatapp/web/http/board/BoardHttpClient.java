package com.example.cloverchatapp.web.http.board;

import androidx.core.util.Consumer;

import com.example.cloverchatapp.global.AuthContext;
import com.example.cloverchatapp.web.domain.board.RequestChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.http.AbstractHttpClient;
import com.example.cloverchatapp.web.http.RetrofitClientBuilder;

import java.util.List;

import retrofit2.Response;

public class BoardHttpClient extends AbstractHttpClient {

    private final BoardRetrofitClient retrofitClient;

    public BoardHttpClient(AuthContext authContext) {
        this.retrofitClient = RetrofitClientBuilder.getRetrofitClient(authContext, BoardRetrofitClient.class);
    }

    public void getChatRoomList(Consumer<Response<List<ResponseChatRoom>>> onResponse) {
        templateCallback(() -> retrofitClient.getChatRoomList(), onResponse);
    }

    public void createChatRoom(RequestChatRoomCreateForm requestChatRoomCreateForm, Consumer<Response<ResponseChatRoom>> onResponse) {
        templateCallback(() -> retrofitClient.createChatRoom(requestChatRoomCreateForm), onResponse);
    }

    public void deleteChatRoom(Long chatRoomId, Consumer<Response<ResponseChatRoom>> onResponse) {
        templateCallback(() -> retrofitClient.deleteChatRoom(chatRoomId), onResponse);
    }
}
