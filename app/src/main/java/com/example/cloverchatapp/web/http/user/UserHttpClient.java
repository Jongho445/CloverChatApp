package com.example.cloverchatapp.web.http.user;

import androidx.core.util.Consumer;

import com.example.cloverchatapp.global.AuthContext;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.domain.user.RequestUserCreateForm;
import com.example.cloverchatapp.web.domain.user.ResponseUser;
import com.example.cloverchatapp.web.http.AbstractHttpClient;
import com.example.cloverchatapp.web.http.RetrofitClientBuilder;

import retrofit2.Response;

public class UserHttpClient extends AbstractHttpClient {

    private final UserRetrofitClient retrofitClient;

    public UserHttpClient(AuthContext authContext) {
        this.retrofitClient = RetrofitClientBuilder.getRetrofitClient(authContext, UserRetrofitClient.class);
    }

    public void login(RequestLoginForm requestLoginForm, Consumer<Response<ResponseUser>> onResponse) {
        templateCallback(() -> retrofitClient.login(requestLoginForm), onResponse);
    }

    public void register(RequestUserCreateForm requestUserCreateForm, Consumer<Response<ResponseUser>> onResponse) {
        templateCallback(() -> retrofitClient.register(requestUserCreateForm), onResponse);
    }
}
