package com.example.cloverchatapp.client;

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

import com.example.cloverchatapp.web.RequestChatRoom;
import com.example.cloverchatapp.web.ResponseChatRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppClient {

    public void getPosts(Consumer<Response<List<ResponseChatRoom>>> onResponse, Consumer<Throwable> onFailure) {
        callback(
                () -> RetrofitClient.getApiService().getPosts(),
                onResponse, onFailure
        );
    }

    public void upload(RequestChatRoom requestChatRoom, Consumer<Response<String>> onResponse, Consumer<Throwable> onFailure) {
        callback(
                () -> RetrofitClient.getApiService().create(requestChatRoom),
                onResponse, onFailure
        );
    }

    public void delete(
            Long postId, String password,
            Consumer<Response<String>> onResponse,
            Consumer<Throwable> onFailure) {

        callback(
                () -> RetrofitClient.getApiService().delete(postId, password),
                onResponse, onFailure
        );
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
