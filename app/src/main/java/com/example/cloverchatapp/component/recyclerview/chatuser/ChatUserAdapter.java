package com.example.cloverchatapp.component.recyclerview.chatuser;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserViewHolder> {

    private final List<ResponseChatUser> itemList;

    public ChatUserAdapter(List<ResponseChatUser> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ChatUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_user_item, parent, false);

        return new ChatUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserViewHolder holder, int position) {
        ResponseChatUser chatUser = itemList.get(position);

        holder.card.setCardBackgroundColor(Color.parseColor("#d3d3d3"));
        holder.nickname.setText(chatUser.user.nickname);
        holder.email.setText(chatUser.user.email);
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
