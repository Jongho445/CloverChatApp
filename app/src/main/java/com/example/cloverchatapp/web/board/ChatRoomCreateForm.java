package com.example.cloverchatapp.web.board;

public class ChatRoomCreateForm {

    public final String createUserId;
    public final String password;
    public final String title;
    public final ChatRoomType type;

    public ChatRoomCreateForm(String createUserId, String password, String title, ChatRoomType type) {
        this.createUserId = createUserId;
        this.password = password;
        this.title = title;
        this.type = type;
    }
}
