package com.example.cloverchatapp.client;

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.web.board.ChatRoomCreateForm;
import com.example.cloverchatapp.web.chat.ResponseChatMessage;
import com.example.cloverchatapp.web.user.RequestLoginForm;
import com.example.cloverchatapp.web.board.ResponseChatRoom;
import com.example.cloverchatapp.web.user.ResponseUser;
import com.example.cloverchatapp.web.user.UserCreateForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppClient {

    private final RetrofitClient retrofitClient;

    public AppClient(AuthStorage authStorage) {
        this.retrofitClient = new RetrofitClient(authStorage);
    }

    public void login(RequestLoginForm requestLoginForm, Consumer<Response<ResponseUser>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().login(requestLoginForm), onResponse, onFailure);
    }

    public void register(UserCreateForm userCreateForm, Consumer<Response<ResponseUser>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().register(userCreateForm), onResponse, onFailure);
    }

    public void getChatRoomList(Consumer<Response<List<ResponseChatRoom>>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().getChatRoomList(), onResponse, onFailure);
    }

    public void createChatRoom(ChatRoomCreateForm chatRoomCreateForm, Consumer<Response<String>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().createChatRoom(chatRoomCreateForm), onResponse, onFailure);
    }

    public void deleteChatRoom(Long chatRoomId, String password, Consumer<Response<String>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().deleteChatRoom(chatRoomId, password), onResponse, onFailure);
    }

    public void getChatMessagesByChatRoomId(Long chatRoomId, Consumer<Response<List<ResponseChatMessage>>> onResponse, Consumer<Throwable> onFailure) {
        callback(() -> retrofitClient.getApiService().getChatMessagesByChatRoomId(chatRoomId), onResponse, onFailure);
    }

    private <T> void callback(
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
