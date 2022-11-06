package com.example.cloverchatapp.fragment.board.list.component.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.example.cloverchatapp.R;

public class ChatRoomRemoveButtonHolder {

    private final Button targetButton;
    private ChatRoomItemContext context;

    public ChatRoomRemoveButtonHolder(View itemView) {
        this.targetButton = itemView.findViewById(R.id.removeBtn);;
    }

    public void init(ChatRoomItemContext context) {
        this.context = context;

        setOnClickListener();
    }

    private void setOnClickListener() {
        targetButton.setOnClickListener(view -> {
            showConfirmDialog((DialogInterface dialogInterface, int i) -> requestDelete());
        });
    }

    private void requestDelete() {
        context.activity.httpClient.deleteChatRoom(context.chatRoom.id, res -> {
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
