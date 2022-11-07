package com.example.cloverchatapp.web.http.user;

import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.domain.user.RequestUserCreateForm;
import com.example.cloverchatapp.web.domain.user.ResponseUser;
import com.example.cloverchatapp.web.http.RetrofitClient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRetrofitClient extends RetrofitClient {

    @POST("/user/login")
    Call<ResponseUser> login(@Body RequestLoginForm requestLoginForm);

    @POST("/user/register")
    Call<ResponseUser> register(@Body RequestUserCreateForm requestUserCreateForm);
}
