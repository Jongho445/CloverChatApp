package com.example.cloverchatapp.web.client;

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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    // /user/**
    @POST("/user/login")
    Call<ResponseUser> login(@Body RequestLoginForm requestLoginForm);

    @POST("/user/register")
    Call<ResponseUser> register(@Body RequestUserCreateForm requestUserCreateForm);

    // /board/chatroom/**
    @GET("/board/chatroom/list")
    Call<List<ResponseChatRoom>> getChatRoomList();

    @POST("/board/chatroom/create")
    Call<String> createChatRoom(@Body RequestChatRoomCreateForm requestChatRoomCreateForm);

    @DELETE("/board/chatroom/delete")
    Call<String> deleteChatRoom(@Query("chatRoomId") Long chatRoomId);

    // /chat/message/**
    @POST("/chat/message/list")
    Call<List<ResponseStompChatMessage>> getChatMessageList(@Body RequestChatMessagesReadForm form);

    // /chat/user/**
    @GET("/chat/user/list/{chatRoomId}")
    Call<List<ResponseChatUser>> getChatUserList(@Path("chatRoomId") Long chatRoomId);

    @POST("/chat/user/create/{chatRoomId}")
    Call<ResponseChatUser> createChatUser(@Path("chatRoomId") Long chatRoomId);

    @DELETE("/chat/user/delete/{chatRoomId}")
    Call<ResponseChatUser> deleteChatUser(@Path("chatRoomId") Long chatRoomId);

}
