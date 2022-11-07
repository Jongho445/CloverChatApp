package com.example.cloverchatapp.fragment.board.list.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.R;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView chatRoomTitle, chatRoomCreateBy;
    private final Button removeBtn;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        this.chatRoomTitle = itemView.findViewById(R.id.chatRoomTitle);
        this.chatRoomCreateBy = itemView.findViewById(R.id.chatRoomCreateBy);
        this.removeBtn = itemView.findViewById(R.id.removeBtn);
    }

    public void init(ChatRoomItemContext context) {
        setItemViewListener(context);
        setRemoveBtnListener(context);

        setViewText(context);
    }

    private void setItemViewListener(ChatRoomItemContext context) {
        ClickableItemViewModel clickableItemViewModel = new ClickableItemViewModel(context);
        itemView.setOnClickListener(view -> clickableItemViewModel.goToChatRoomDetailFragment());
    }

    private void setRemoveBtnListener(ChatRoomItemContext context) {
        ChatRoomRemoveButtonModel chatRoomRemoveButtonModel = new ChatRoomRemoveButtonModel(context);
        removeBtn.setOnClickListener(view -> chatRoomRemoveButtonModel.remove());
    }

    private void setViewText(ChatRoomItemContext context) {
        chatRoomTitle.setText(context.chatRoom.title);
        chatRoomCreateBy.setText(context.chatRoom.createUser.nickname);
    }
}
