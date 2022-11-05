package com.example.cloverchatapp.web.domain.board;

public class RequestChatRoomCreateForm {

    public final Long createUserId;
    public final String password;
    public final String title;
    public final ChatRoomType type;

    public RequestChatRoomCreateForm(Long createUserId, String password, String title, ChatRoomType type) {
        this.createUserId = createUserId;
        this.password = password;
        this.title = title;
        this.type = type;
    }
}
