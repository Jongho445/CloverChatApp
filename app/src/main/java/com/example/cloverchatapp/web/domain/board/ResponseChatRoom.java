package com.example.cloverchatapp.web.domain.board;

import com.example.cloverchatapp.web.domain.user.ResponseUser;

public class ResponseChatRoom {

    public final Long id;
    public final ResponseUser createUser;
    public final String title;
    public final String createDate;
    public final ChatRoomType type;

    public ResponseChatRoom(Long id, ResponseUser createUser, String title, String createDate, ChatRoomType type) {
        this.id = id;
        this.createUser = createUser;
        this.title = title;
        this.createDate = createDate;
        this.type = type;
    }
}
