package com.example.cloverchatapp.fragment.board.list.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.cloverchatapp.web.http.board.BoardHttpClient;

public class ChatRoomRemoveButtonModel {

    private final ChatRoomItemContext context;
    private final BoardHttpClient boardHttpClient;

    public ChatRoomRemoveButtonModel(ChatRoomItemContext context) {
        this.context = context;
        this.boardHttpClient = new BoardHttpClient(context.global.auth);

    }

    public void remove() {
        showConfirmDialog((DialogInterface dialogInterface, int i) -> requestDelete());
    }

    private void requestDelete() {
        boardHttpClient.deleteChatRoom(context.chatRoom.id, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("채팅방 생성자가 아닙니다");
                return;
            }

            showAlertDialog("삭제되었습니다.");

            context.itemList.remove(context.position);
            context.adapter.notifyDataSetChanged();
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
