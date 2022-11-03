package com.example.cloverchatapp.component;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.chat.ResponseChatMessage;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {

    private final List<ResponseChatMessage> itemList;
    private final MainActivity activity;

    public ChatMessageAdapter(List<ResponseChatMessage> itemList, MainActivity activity) {
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
        // 현재 닉네임과 글쓴이의 닉네임이 같을 경우 배경을 노란색으로 변경
        if (activity.authStorage.me.nickname.equals(itemList.get(position).createUser.nickname)) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFF176"));
        }

        holder.nickname.setText(itemList.get(position).createUser.nickname);
        holder.contents.setText(itemList.get(position).content);
        holder.time.setText(itemList.get(position).createAt);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
