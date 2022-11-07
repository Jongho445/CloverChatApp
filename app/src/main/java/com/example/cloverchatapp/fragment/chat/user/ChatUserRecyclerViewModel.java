package com.example.cloverchatapp.fragment.chat.user;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.fragment.chat.user.adapter.ChatUserAdapter;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;

import java.util.ArrayList;
import java.util.List;

public class ChatUserRecyclerViewModel {

    private final MainActivity activity;

    private RecyclerView recyclerView;
    private ChatUserAdapter adapter;
    private List<ResponseChatUser> itemList;

    public ChatUserRecyclerViewModel(MainActivity activity, RecyclerView recyclerView, List<ResponseChatUser> chatUsers) {
        this.activity = activity;
        this.recyclerView = recyclerView;

        init(chatUsers);
    }

    private void init(List<ResponseChatUser> chatUsers) {
        itemList = new ArrayList<>();
        itemList.addAll(chatUsers);

        adapter = new ChatUserAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void addItem(ResponseChatUser chatUser) {
        activity.runOnUiThread(() -> {
            itemList.add(chatUser);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void clearList() {
        itemList.clear();
    }
}
