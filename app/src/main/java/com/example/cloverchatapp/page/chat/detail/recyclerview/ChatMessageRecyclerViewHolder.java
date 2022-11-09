package com.example.cloverchatapp.page.chat.detail.recyclerview;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageRecyclerViewHolder {

    private final MainActivity activity;

    private final RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ResponseStompChatMessage> itemList;

    public ChatMessageRecyclerViewHolder(MainActivity activity, ViewGroup rootView, List<ResponseStompChatMessage> chatMessages) {
        this.activity = activity;
        this.recyclerView = rootView.findViewById(R.id.rv_list);

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

            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void clearList() {
        itemList.clear();
    }
}
