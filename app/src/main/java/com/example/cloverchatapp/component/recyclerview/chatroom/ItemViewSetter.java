package com.example.cloverchatapp.component.recyclerview.chatroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;

import java.util.List;

public class ItemViewSetter {


    private final MainActivity activity;
    private final View targetView;
    private final ResponseChatRoom chatRoom;
    private final List<ResponseChatRoom> itemList;

    public ItemViewSetter(MainActivity activity, View targetView, ResponseChatRoom chatRoom, List<ResponseChatRoom> itemList) {
        this.activity = activity;
        this.targetView = targetView;
        this.chatRoom = chatRoom;
        this.itemList = itemList;
    }

    public void setOnClickItemListener() {
        targetView.setOnClickListener(view -> {
            goToChatRoomDetailFragment();
        });
    }

    private void goToChatRoomDetailFragment() {
        if (chatRoom.type == ChatRoomType.PUBLIC) {
            request(new RequestChatMessagesReadForm(chatRoom.id, chatRoom.type, null));
            return;
        }

        View dialogView = View.inflate(activity, R.layout.dialog_chat_room_password, null);
        showPasswordInputDialog(dialogView, (DialogInterface dialogInterface, int i) -> {
            EditText editPassword = dialogView.findViewById(R.id.dialogPassword);
            String password = editPassword.getText().toString();

            request(new RequestChatMessagesReadForm(chatRoom.id, chatRoom.type, password));
        });
    }

    private void request(RequestChatMessagesReadForm form) {
        activity.httpClient.getChatMessageList(form, res -> {
            if (!res.isSuccessful()) {
                showFailureAlertDialog();
                return;
            }

            activity.curChatMessages = res.body();
            activity.curChatRoom = chatRoom;

            itemList.clear();
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_DETAIL);
        });
    }

    private void showPasswordInputDialog(View dialogView, DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(activity)
                .setTitle("비밀번호 입력")
                .setView(dialogView)
                .setPositiveButton("확인", callback)
                .setNegativeButton("취소", null)
                .show();
    }

    private void showFailureAlertDialog() {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage("비밀번호가 틀립니다")
                .setPositiveButton("확인", null)
                .show();
    }
}
