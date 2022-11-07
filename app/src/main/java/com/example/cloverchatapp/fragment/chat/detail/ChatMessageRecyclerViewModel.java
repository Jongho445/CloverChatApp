package com.example.cloverchatapp.fragment.chat.detail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.fragment.chat.detail.adapter.ChatMessageAdapter;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageRecyclerViewModel {

    private final MainActivity activity;

    private final RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ResponseStompChatMessage> itemList;

    public ChatMessageRecyclerViewModel(MainActivity activity, RecyclerView recyclerView, List<ResponseStompChatMessage> chatMessages) {
        this.activity = activity;
        this.recyclerView = recyclerView;

        init(chatMessages);
    }

    private void init(List<ResponseStompChatMessage> chatMessages) {
        itemList = new ArrayList<>();
        itemList.addAll(chatMessages);

        adapter = new ChatMessageAdapter(activity, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void addItem(ResponseStompChatMessage chatMessage) {
        activity.runOnUiThread(() -> {
            itemList.add(chatMessage);
            activity.global.chat.curChatMessages.add(chatMessage);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void clearList() {
        itemList.clear();
    }
}