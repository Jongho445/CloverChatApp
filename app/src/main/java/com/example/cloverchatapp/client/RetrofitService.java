package com.example.cloverchatapp.client;

import com.example.cloverchatapp.web.ChatRoomCreateForm;
import com.example.cloverchatapp.web.RequestLoginForm;
import com.example.cloverchatapp.web.ResponseChatRoom;
import com.example.cloverchatapp.web.ResponseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("/user/login")
    Call<ResponseUser> login(@Body RequestLoginForm requestLoginForm);

    @GET("/board/list")
    Call<List<ResponseChatRoom>> getChatRoomList();

    @POST("/board/create")
    Call<String> createChatRoom(@Body ChatRoomCreateForm chatRoomCreateForm);

    @DELETE("/board/delete")
    Call<String> deleteChatRoom(@Query("chatRoomId") Long chatRoomId, @Query("password") String password);

}
