package com.example.cloverchatapp.client;

import com.example.cloverchatapp.web.RequestChatRoom;
import com.example.cloverchatapp.web.ResponseChatRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/list")
    Call<List<ResponseChatRoom>> getPosts();

    @POST("/create")
    Call<String> create(@Body RequestChatRoom requestChatRoom);

    @DELETE("/delete")
    Call<String> delete(@Query("postId") Long postId, @Query("password") String password);

}
