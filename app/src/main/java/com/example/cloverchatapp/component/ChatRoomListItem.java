package com.example.cloverchatapp.component;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.web.ResponseChatRoom;

public class ChatRoomListItem {

    private TextView postTitle, postUsername;
    private Button removeBtn;

    private ResponseChatRoom post;
    private Integer idx;
    private ChatRoomAdapter adapter;
    private AppClient client = new AppClient();

    private static String PASSWORD = "1234";

    public ChatRoomListItem(ResponseChatRoom post, Integer idx, ChatRoomAdapter adapter) {
        this.post = post;
        this.idx = idx;
        this.adapter = adapter;
    }

    public void initFields(View convertView) {
        postTitle = convertView.findViewById(R.id.postTitle);
        postUsername = convertView.findViewById(R.id.postUsername);
        removeBtn = convertView.findViewById(R.id.removeBtn);
    }

    public void setView() {
        postTitle.setText(post.getTitle());
        postUsername.setText(post.getCreateBy());
    }

    public void setRemoveListener() {
        removeBtn.setOnClickListener(view -> {
            client.delete(
                    post.getId(), PASSWORD,
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
