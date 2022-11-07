package com.example.cloverchatapp.web.http.board;

import com.example.cloverchatapp.web.domain.board.RequestChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.http.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardRetrofitClient extends RetrofitClient {

    @GET("/board/chatroom/list")
    Call<List<ResponseChatRoom>> getChatRoomList();

    @POST("/board/chatroom/create")
    Call<ResponseChatRoom> createChatRoom(@Body RequestChatRoomCreateForm requestChatRoomCreateForm);

    @DELETE("/board/chatroom/delete")
    Call<ResponseChatRoom> deleteChatRoom(@Query("chatRoomId") Long chatRoomId);
}
