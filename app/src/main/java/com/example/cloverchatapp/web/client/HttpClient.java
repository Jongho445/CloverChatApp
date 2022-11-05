package com.example.cloverchatapp.web.client;

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.web.domain.board.RequestChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.user.ResponseUser;
import com.example.cloverchatapp.web.domain.user.RequestUserCreateForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpClient {

    private final RetrofitClient retrofitClient;

    public HttpClient(AuthStorage authStorage) {
        this.retrofitClient = new RetrofitClient(authStorage);
    }

    public void login(RequestLoginForm requestLoginForm, Consumer<Response<ResponseUser>> onResponse) {
        callback(() -> retrofitClient.getApiService().login(requestLoginForm), onResponse);
    }

    public void register(RequestUserCreateForm requestUserCreateForm, Consumer<Response<ResponseUser>> onResponse) {
        callback(() -> retrofitClient.getApiService().register(requestUserCreateForm), onResponse);
    }

    public void getChatRoomList(Consumer<Response<List<ResponseChatRoom>>> onResponse) {
        callback(() -> retrofitClient.getApiService().getChatRoomList(), onResponse);
    }

    public void createChatRoom(RequestChatRoomCreateForm requestChatRoomCreateForm, Consumer<Response<ResponseChatRoom>> onResponse) {
        callback(() -> retrofitClient.getApiService().createChatRoom(requestChatRoomCreateForm), onResponse);
    }

    public void deleteChatRoom(Long chatRoomId, Consumer<Response<ResponseChatRoom>> onResponse) {
        callback(() -> retrofitClient.getApiService().deleteChatRoom(chatRoomId), onResponse);
    }

    public void getChatMessageList(RequestChatMessagesReadForm form, Consumer<Response<List<ResponseStompChatMessage>>> onResponse) {
        callback(() -> retrofitClient.getApiService().getChatMessageList(form), onResponse);
    }

    public void getChatUserList(Long chatRoomId, Consumer<Response<List<ResponseChatUser>>> onResponse) {
        callback(() -> retrofitClient.getApiService().getChatUserList(chatRoomId), onResponse);
    }

    public void createChatUser(Long chatRoomId, Consumer<Response<ResponseChatUser>> onResponse) {
        callback(() -> retrofitClient.getApiService().createChatUser(chatRoomId), onResponse);
    }

    public void deleteChatUser(Consumer<Response<List<ResponseChatUser>>> onResponse) {
        callback(() -> retrofitClient.getApiService().deleteChatUser(), onResponse);
    }

    private <T> void callback(Supplier<Call<T>> requestFx, Consumer<Response<T>> onResponse) {
        Call<T> call = requestFx.get();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onResponse.accept(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
