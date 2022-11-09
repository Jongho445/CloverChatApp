package com.example.cloverchatapp.page.board.list.recyclerview;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomRecyclerViewHolder {

    private final MainActivity activity;

    private final RecyclerView recyclerView;
    private ChatRoomAdapter adapter;
    private List<ResponseChatRoom> itemList;

    public ChatRoomRecyclerViewHolder(MainActivity activity, ViewGroup rootView, List<ResponseChatRoom> chatRooms) {
        this.activity = activity;
        this.recyclerView = rootView.findViewById(R.id.chatRoomListView);

        init(chatRooms);
    }

    private void init(List<ResponseChatRoom> chatRooms) {
        itemList = new ArrayList<>();
        itemList.addAll(chatRooms);

        adapter = new ChatRoomAdapter(activity, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addItem(ResponseChatRoom chatRoom) {
        activity.runOnUiThread(() -> {
            itemList.add(chatRoom);

            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void removeItem(ResponseChatRoom chatRoom) {
        activity.runOnUiThread(() -> {
            int idx = findChatRoom(chatRoom.id);
            itemList.remove(idx);

            adapter.notifyDataSetChanged();
        });
    }

    private int findChatRoom(Long chatRoomId) {
        int result = -1;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).id == chatRoomId) {
                result = i;
            }
        }

        return result;
    }

    public void clearList() {
        itemList.clear();
    }
}
