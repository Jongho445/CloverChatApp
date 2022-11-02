package com.example.cloverchatapp.component;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomListItem {

    private TextView chatRoomTitle, chatRoomCreateBy;
    private Button removeBtn;

    private ResponseChatRoom chatRoom;
    private Integer idx;
    private ChatRoomAdapter adapter;
    List<ResponseChatRoom> list;
    private AppClient client = new AppClient();

    private static String PASSWORD = "1234";

    public ChatRoomListItem(ResponseChatRoom chatRoom, Integer idx, ChatRoomAdapter adapter, List<ResponseChatRoom> list) {
        this.chatRoom = chatRoom;
        this.idx = idx;
        this.adapter = adapter;
        this.list = list;
    }

    public void initFields(View convertView) {
        chatRoomTitle = convertView.findViewById(R.id.chatRoomTitle);
        chatRoomCreateBy = convertView.findViewById(R.id.chatRoomCreateBy);
        removeBtn = convertView.findViewById(R.id.removeBtn);
    }

    public void setView() {
        chatRoomTitle.setText(chatRoom.title);
        chatRoomCreateBy.setText(chatRoom.createUser.nickname);
    }

    public void setOnClickListener(MainActivity activity, View convertView) {
        convertView.setOnClickListener(view -> {
            list.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_DETAIL, chatRoom);
        });
    }

    public void setRemoveListener() {
        removeBtn.setOnClickListener(view -> {
            client.deleteChatRoom(
                    chatRoom.id, PASSWORD,
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
