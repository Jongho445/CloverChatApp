package com.example.cloverchatapp.component.recyclerview.chatroom;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomList {

    private final MainActivity activity;
    private final ViewGroup rootView;

    RecyclerView chatRoomListView;
    ChatRoomAdapter adapter;
    List<ResponseChatRoom> itemList;

    public ChatRoomList(MainActivity activity, ViewGroup rootView, List<ResponseChatRoom> chatRooms) {
        this.activity = activity;
        this.rootView = rootView;

        init(chatRooms);
    }

    private void init(List<ResponseChatRoom> chatRooms) {
        chatRoomListView = rootView.findViewById(R.id.chatRoomListView);

        itemList = new ArrayList<>();
        itemList.addAll(chatRooms);

        adapter = new ChatRoomAdapter(itemList, activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        chatRoomListView.setLayoutManager(layoutManager);
        chatRoomListView.setAdapter(adapter);
    }

    public void addItem(ResponseChatRoom chatRoom) {
        activity.runOnUiThread(() -> {
            itemList.add(chatRoom);
            adapter.notifyDataSetChanged();
        });
    }

    public void clearList() {
        itemList.clear();
    }
}
