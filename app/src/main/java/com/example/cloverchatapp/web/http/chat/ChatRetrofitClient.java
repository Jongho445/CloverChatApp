package com.example.cloverchatapp.web.http.chat;

import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.http.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatRetrofitClient extends RetrofitClient {

    // /chat/message/**
    @POST("/chat/message/list")
    Call<List<ResponseStompChatMessage>> getChatMessageList(@Body RequestChatMessagesReadForm form);

    @POST("/chat/message/create/{chatRoomId}")
    Call<ResponseStompChatMessage> createChatMessage(@Path("chatRoomId") Long chatRoomId, @Body RequestStompChatMessage form);

    // /chat/user/**
    @GET("/chat/user/list/{chatRoomId}")
    Call<List<ResponseChatUser>> getChatUserList(@Path("chatRoomId") Long chatRoomId);

    @POST("/chat/user/create/{chatRoomId}")
    Call<ResponseChatUser> createChatUser(@Path("chatRoomId") Long chatRoomId);

    @DELETE("/chat/user/delete")
    Call<List<ResponseChatUser>> deleteChatUser();
}
