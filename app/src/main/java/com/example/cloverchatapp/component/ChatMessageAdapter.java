package com.example.cloverchatapp.component;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter {

    String currentUser;
    List<ChatMessage> itemList;

    public ChatMessageAdapter(String currentUser, List<ChatMessage> itemList) {
        this.currentUser = currentUser;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_message, parent, false);

        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder originHolder, int position) {
        ChatMessageViewHolder holder = (ChatMessageViewHolder) originHolder;

        // 현재 닉네임과 글쓴이의 닉네임이 같을 경우 배경을 노란색으로 변경
        if (currentUser.equals(itemList.get(position).getNickname())) {
            holder.getCard().setCardBackgroundColor(Color.parseColor("#FFF176"));
        }

        holder.getNickname().setText(itemList.get(position).getNickname());
        holder.getContents().setText(itemList.get(position).getContents());
        holder.getTime().setText(itemList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
