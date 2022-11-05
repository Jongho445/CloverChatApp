package com.example.cloverchatapp.component.recyclerview.chatroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomRemoveButtonSetter {

    private final MainActivity activity;

    private final Button targetButton;

    private final ResponseChatRoom chatRoom;
    private final int position;

    private final List<ResponseChatRoom> itemList;
    private final ChatRoomAdapter adapter;

    public ChatRoomRemoveButtonSetter(MainActivity activity, Button targetButton, ResponseChatRoom chatRoom, int position, List<ResponseChatRoom> itemList, ChatRoomAdapter adapter) {
        this.activity = activity;
        this.targetButton = targetButton;
        this.chatRoom = chatRoom;
        this.position = position;
        this.itemList = itemList;
        this.adapter = adapter;
    }

    public void setOnClickListener() {
        targetButton.setOnClickListener(view -> {
            showConfirmDialog((DialogInterface dialogInterface, int i) -> {
                requestDelete();
            });
        });
    }

    private void requestDelete() {
        activity.httpClient.deleteChatRoom(chatRoom.id, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("채팅방 생성자가 아닙니다");
                return;
            }

            showAlertDialog("삭제되었습니다.");

            itemList.remove(position);
            adapter.notifyDataSetChanged();
        });
    }

    private void showConfirmDialog(DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage("정말로 지우시겠습니까?")
                .setPositiveButton("확인", listener)
                .setNegativeButton("취소", null)
                .show();
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
    }
}
