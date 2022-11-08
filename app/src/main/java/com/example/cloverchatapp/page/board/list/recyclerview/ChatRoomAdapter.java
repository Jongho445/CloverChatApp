package com.example.cloverchatapp.page.board.list.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.page.board.list.recyclerview.item.ChatRoomItemContext;
import com.example.cloverchatapp.page.board.list.recyclerview.item.ChatRoomItemViewHolder;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomItemViewHolder>  {

    private final MainActivity activity;

    private final List<ResponseChatRoom> itemList;

    public ChatRoomAdapter(MainActivity activity, List<ResponseChatRoom> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ChatRoomItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_room_list_item, parent, false);

        return new ChatRoomItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomItemViewHolder holder, int position) {
        ResponseChatRoom chatRoom = itemList.get(position);
        ChatRoomItemContext context = new ChatRoomItemContext(activity, chatRoom, position, itemList, this);

        holder.setOnClickListeners(context);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
