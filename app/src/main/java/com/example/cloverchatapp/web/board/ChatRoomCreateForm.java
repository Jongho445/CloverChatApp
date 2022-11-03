package com.example.cloverchatapp.web.board;

public class ChatRoomCreateForm {

    public final Long createUserId;
    public final String password;
    public final String title;
    public final ChatRoomType type;

    public ChatRoomCreateForm(Long createUserId, String password, String title, ChatRoomType type) {
        this.createUserId = createUserId;
        this.password = password;
        this.title = title;
        this.type = type;
    }
}
