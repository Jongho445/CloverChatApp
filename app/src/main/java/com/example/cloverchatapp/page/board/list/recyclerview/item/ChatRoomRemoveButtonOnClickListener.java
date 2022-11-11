package com.example.cloverchatapp.page.board.list.recyclerview.item;

import android.content.DialogInterface;
import android.view.View;

import com.example.cloverchatapp.util.DialogRenderer;
import com.example.cloverchatapp.util.MethodType;
import com.example.cloverchatapp.web.domain.board.StompUpdateChatRoom;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;

public class ChatRoomRemoveButtonOnClickListener implements View.OnClickListener {

    private final ChatRoomItemContext context;
    private final BoardHttpClient boardHttpClient;

    public ChatRoomRemoveButtonOnClickListener(ChatRoomItemContext context) {
        this.context = context;
        this.boardHttpClient = new BoardHttpClient(context.global.auth);
    }

    @Override
    public void onClick(View view) {
        DialogRenderer.showConfirmDialog(
                context.activity,
                "정말로 지우시겠습니까?",
                (DialogInterface dialogInterface, int i) -> delete(),
                null
        );
    }

    private void delete() {
        boardHttpClient.deleteChatRoom(context.chatRoom.id, res -> {
            if (!res.isSuccessful()) {
                DialogRenderer.showAlertDialog(context.activity, "채팅방 생성자가 아닙니다");
                return;
            }

            DialogRenderer.showAlertDialog(context.activity, "삭제되었습니다.");
            StompUpdateChatRoom stompForm = new StompUpdateChatRoom(MethodType.DELETE, res.body());
            context.global.ws.chatRoomSession.sendChatRoom(stompForm);
        });
    }
}
