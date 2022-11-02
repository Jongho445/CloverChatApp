package com.example.cloverchatapp.component;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.web.ResponseChatRoom;

public class ChatRoomListItem {

    private TextView chatRoomTitle, chatRoomCreateBy;
    private Button removeBtn;

    private ResponseChatRoom chatRoom;
    private Integer idx;
    private ChatRoomAdapter adapter;
    private AppClient client = new AppClient();

    private static String PASSWORD = "1234";

    public ChatRoomListItem(ResponseChatRoom chatRoom, Integer idx, ChatRoomAdapter adapter) {
        this.chatRoom = chatRoom;
        this.idx = idx;
        this.adapter = adapter;
    }

    public void initFields(View convertView) {
        chatRoomTitle = convertView.findViewById(R.id.chatRoomTitle);
        chatRoomCreateBy = convertView.findViewById(R.id.chatRoomCreateBy);
        removeBtn = convertView.findViewById(R.id.removeBtn);
    }

    public void setView() {
        chatRoomTitle.setText(chatRoom.getTitle());
        chatRoomCreateBy.setText(chatRoom.getCreateBy());
    }

    public void setRemoveListener() {
        removeBtn.setOnClickListener(view -> {
            client.delete(
                    chatRoom.getId(), PASSWORD,
                    res -> {
                        System.out.println(idx);
                        adapter.remove(idx);
                        adapter.notifyDataSetChanged();
                    }, t -> {
                        System.out.println(t.getMessage());
                        t.printStackTrace();
                    }
            );
        });
    }
}
