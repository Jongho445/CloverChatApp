package com.example.cloverchatapp.component.recyclerview.chatroom;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

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
