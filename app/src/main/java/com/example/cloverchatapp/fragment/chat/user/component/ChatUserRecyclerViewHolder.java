package com.example.cloverchatapp.fragment.chat.user.component;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;

import java.util.ArrayList;
import java.util.List;

public class ChatUserRecyclerViewHolder {

    private final MainActivity activity;
    private final ViewGroup rootView;

    private RecyclerView recyclerView;
    private ChatUserAdapter adapter;
    private List<ResponseChatUser> itemList;

    public ChatUserRecyclerViewHolder(MainActivity activity, ViewGroup rootView, List<ResponseChatUser> chatUsers) {
        this.activity = activity;
        this.rootView = rootView;

        init(chatUsers);
    }

    private void init(List<ResponseChatUser> chatUsers) {
        recyclerView = rootView.findViewById(R.id.rv_list);

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
