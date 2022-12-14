package com.example.cloverchatapp.page.chat.detail.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageItemViewHolder> {

    private final GlobalContext global;
    private final List<ResponseStompChatMessage> itemList;

    public ChatMessageAdapter(MainActivity activity, List<ResponseStompChatMessage> itemList) {
        this.global = activity.global;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ChatMessageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_message, parent, false);

        return new ChatMessageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageItemViewHolder holder, int position) {
        ResponseStompChatMessage chatMessage = itemList.get(position);
        String myNickname = global.auth.myInfo.nickname;

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
