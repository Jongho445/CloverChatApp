package com.example.cloverchatapp.global;

import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.List;

public class ChatContext {

    public ResponseChatRoom curChatRoom;
    public List<ResponseStompChatMessage> curChatMessages;

}
