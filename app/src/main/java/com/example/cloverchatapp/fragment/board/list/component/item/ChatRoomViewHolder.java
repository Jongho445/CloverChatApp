package com.example.cloverchatapp.fragment.board.list.component.item;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {

    public final TextView chatRoomTitle, chatRoomCreateBy;

    public final ClickableItemViewHolder clickableItemViewHolder;
    public final ChatRoomRemoveButtonHolder removeButtonHolder;

    private ChatRoomItemContext context;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);

        this.removeButtonHolder = new ChatRoomRemoveButtonHolder(itemView);
        this.clickableItemViewHolder = new ClickableItemViewHolder(itemView);

        this.chatRoomTitle = itemView.findViewById(R.id.chatRoomTitle);
        this.chatRoomCreateBy = itemView.findViewById(R.id.chatRoomCreateBy);
    }

    public void init(ChatRoomItemContext context) {
        this.context = context;

        removeButtonHolder.init(context);
        clickableItemViewHolder.init(context);

        setViewText();
    }

    public void setViewText() {
        chatRoomTitle.setText(context.chatRoom.title);
        chatRoomCreateBy.setText(context.chatRoom.createUser.nickname);
    }
}
