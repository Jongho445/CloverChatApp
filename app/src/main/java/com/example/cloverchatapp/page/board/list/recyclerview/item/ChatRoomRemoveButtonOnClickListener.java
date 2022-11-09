package com.example.cloverchatapp.page.board.list.recyclerview.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

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
        showConfirmDialog((DialogInterface dialogInterface, int i) -> delete());
    }

    private void delete() {
        boardHttpClient.deleteChatRoom(context.chatRoom.id, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("채팅방 생성자가 아닙니다");
                return;
            }

            showAlertDialog("삭제되었습니다.");
            StompUpdateChatRoom stompForm = new StompUpdateChatRoom(MethodType.DELETE, res.body());
            context.global.ws.chatRoomSession.sendChatChatRoom(stompForm);
        });
    }

    private void showConfirmDialog(DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context.activity)
                .setTitle("알림")
                .setMessage("정말로 지우시겠습니까?")
                .setPositiveButton("확인", listener)
                .setNegativeButton("취소", null)
                .show();
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(context.activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
    }
}
