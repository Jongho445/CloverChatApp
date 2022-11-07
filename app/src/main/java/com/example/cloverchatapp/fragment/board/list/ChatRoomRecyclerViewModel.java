package com.example.cloverchatapp.fragment.board.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.fragment.board.list.adapter.ChatRoomAdapter;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomRecyclerViewModel {

    private final MainActivity activity;

    private final RecyclerView recyclerView;
    private ChatRoomAdapter adapter;
    private List<ResponseChatRoom> itemList;

    public ChatRoomRecyclerViewModel(MainActivity activity, RecyclerView recyclerView, List<ResponseChatRoom> chatRooms) {
        this.activity = activity;
        this.recyclerView = recyclerView;

        init(chatRooms);
    }

    private void init(List<ResponseChatRoom> chatRooms) {
        itemList = new ArrayList<>();
        itemList.addAll(chatRooms);

        adapter = new ChatRoomAdapter(activity, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
