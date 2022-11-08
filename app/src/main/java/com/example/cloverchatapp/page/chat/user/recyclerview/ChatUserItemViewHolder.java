package com.example.cloverchatapp.page.chat.user.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

public class ChatUserItemViewHolder extends RecyclerView.ViewHolder {

    public final CardView card;
    public final TextView nickname;
    public final TextView email;

    public ChatUserItemViewHolder(View itemView) {
        super(itemView);

        this.card = itemView.findViewById(R.id.user_card_view);
        this.nickname = itemView.findViewById(R.id.user_tv_nickname);
        this.email = itemView.findViewById(R.id.user_tv_email);
    }
}
