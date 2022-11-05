package com.example.cloverchatapp.web.client;

import com.example.cloverchatapp.web.domain.board.ChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.chat.ResponseChatMessage;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.user.ResponseUser;
import com.example.cloverchatapp.web.domain.user.UserCreateForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("/user/login")
    Call<ResponseUser> login(@Body RequestLoginForm requestLoginForm);

    @POST("/user/register")
    Call<ResponseUser> register(@Body UserCreateForm userCreateForm);

    @GET("/board/list")
    Call<List<ResponseChatRoom>> getChatRoomList();

    @POST("/board/create")
    Call<String> createChatRoom(@Body ChatRoomCreateForm chatRoomCreateForm);

    @DELETE("/board/delete")
    Call<String> deleteChatRoom(@Query("chatRoomId") Long chatRoomId);

    @GET("/chat/{chatRoomId}")
    Call<List<ResponseChatMessage>> getChatMessagesByChatRoomId(@Path("chatRoomId") Long chatRoomId);
}
