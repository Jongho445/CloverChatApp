package com.example.cloverchatapp.fragment.chatroom.detail.component;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.chatroom.detail.component.item.ChatMessageViewHolder;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {

    private final List<ResponseStompChatMessage> itemList;
    private final MainActivity activity;

    public ChatMessageAdapter(List<ResponseStompChatMessage> itemList, MainActivity activity) {
        this.itemList = itemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_message, parent, false);

        return new ChatMessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        ResponseStompChatMessage chatMessage = itemList.get(position);
        String myNickname = activity.authStorage.me.nickname;

        if (myNickname.equals(chatMessage.createUser.nickname)) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFf176"));
        }

        holder.nickname.setText(chatMessage.createUser.nickname);
        holder.contents.setText(chatMessage.content);
        holder.time.setText(chatMessage.createAt);
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
