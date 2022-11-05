package com.example.cloverchatapp.component.recyclerview.chatmessage;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    public final CardView card;
    public final TextView nickname;
    public final TextView contents;
    public final TextView time;

    ChatMessageViewHolder(View itemView) {
        super(itemView);

        this.card = itemView.findViewById(R.id.chat_card_view);
        this.nickname = itemView.findViewById(R.id.chat_tv_nickname);
        this.contents = itemView.findViewById(R.id.chat_tv_contents);
        this.time = itemView.findViewById(R.id.chat_tv_time);
    }
}
