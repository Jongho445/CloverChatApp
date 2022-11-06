package com.example.cloverchatapp.fragment.board.list.component.item;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.fragment.board.list.component.ChatRoomAdapter;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomItemContext {

    public final MainActivity activity;

    public final ResponseChatRoom chatRoom;
    public final int position;

    public final List<ResponseChatRoom> itemList;
    public final ChatRoomAdapter adapter;

    public ChatRoomItemContext(MainActivity activity, ResponseChatRoom chatRoom, int position, List<ResponseChatRoom> itemList, ChatRoomAdapter adapter) {
        this.activity = activity;
        this.chatRoom = chatRoom;
        this.position = position;
        this.itemList = itemList;
        this.adapter = adapter;
    }
}
