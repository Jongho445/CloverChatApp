package com.example.cloverchatapp.client;

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

import com.example.cloverchatapp.web.ChatRoomCreateForm;
import com.example.cloverchatapp.web.RequestLoginForm;
import com.example.cloverchatapp.web.ResponseChatRoom;
import com.example.cloverchatapp.web.ResponseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppClient {

    public void login(RequestLoginForm requestLoginForm, Consumer<Response<ResponseUser>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> RetrofitClient.getApiService().login(requestLoginForm), onResponse, onFailure);
    }

    public void getChatRoomList(Consumer<Response<List<ResponseChatRoom>>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> RetrofitClient.getApiService().getChatRoomList(), onResponse, onFailure);
    }

    public void createChatRoom(ChatRoomCreateForm chatRoomCreateForm, Consumer<Response<String>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> RetrofitClient.getApiService().createChatRoom(chatRoomCreateForm), onResponse, onFailure);
    }

    public void deleteChatRoom(
            Long chatRoomId, String password,
            Consumer<Response<String>> onResponse,
            Consumer<Throwable> onFailure
    ) {
        callback(() -> RetrofitClient.getApiService().deleteChatRoom(chatRoomId, password), onResponse, onFailure);
    }

    public <T> void callback(
            Supplier<Call<T>> requestFx,
            Consumer<Response<T>> onResponse,
            Consumer<Throwable> onFailure
    ) {
        Call<T> call = requestFx.get();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onResponse.accept(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailure.accept(t);
            }
        });
    }
}
