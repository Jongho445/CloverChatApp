package com.example.cloverchatapp.component.recyclerview.chatmessage;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.chat.ResponseChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageList {

    private final MainActivity activity;
    private final ViewGroup rootView;

    RecyclerView recyclerView;
    ChatMessageAdapter adapter;
    List<ResponseChatMessage> itemList;

    public ChatMessageList(MainActivity activity, ViewGroup rootView, List<ResponseChatMessage> chatMessages) {
        this.activity = activity;
        this.rootView = rootView;

        init(chatMessages);
    }

    private void init(List<ResponseChatMessage> chatMessages) {
        recyclerView = rootView.findViewById(R.id.rv_list);

        itemList = new ArrayList<>();
        itemList.addAll(chatMessages);

        adapter = new ChatMessageAdapter(itemList, activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void addItem(ResponseChatMessage chatMsg) {
        activity.runOnUiThread(() -> {
            itemList.add(chatMsg);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void clearList() {
        itemList.clear();
    }
}
