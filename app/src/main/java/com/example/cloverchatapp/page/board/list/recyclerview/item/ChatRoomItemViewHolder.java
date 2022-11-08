package com.example.cloverchatapp.page.board.list.recyclerview.item;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

public class ChatRoomItemViewHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView chatRoomTitle, chatRoomCreateBy;
    private final Button removeBtn;

    public ChatRoomItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        this.chatRoomTitle = itemView.findViewById(R.id.chatRoomTitle);
        this.chatRoomCreateBy = itemView.findViewById(R.id.chatRoomCreateBy);
        this.removeBtn = itemView.findViewById(R.id.removeBtn);
    }

    public void setOnClickListeners(ChatRoomItemContext context) {
        itemView.setOnClickListener(new ChatRoomItemViewOnClickListener(context));
        removeBtn.setOnClickListener(new ChatRoomRemoveButtonOnClickListener(context));

        setViewText(context);
    }

    private void setViewText(ChatRoomItemContext context) {
        chatRoomTitle.setText(context.chatRoom.title);
        chatRoomCreateBy.setText(context.chatRoom.createUser.nickname);
    }
}
