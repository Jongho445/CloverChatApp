package com.example.cloverchatapp.component;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;


import com.example.cloverchatapp.web.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {

    public final TextView chatRoomTitle, chatRoomCreateBy;
    public final Button removeBtn;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);

        this.chatRoomTitle = itemView.findViewById(R.id.chatRoomTitle);
        this.chatRoomCreateBy = itemView.findViewById(R.id.chatRoomCreateBy);
        this.removeBtn = itemView.findViewById(R.id.removeBtn);
    }
}
