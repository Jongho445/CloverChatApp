package com.example.cloverchatapp.fragment.board.list.component.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;

public class ClickableItemViewHolder {

    private final View itemView;
    private ChatRoomItemContext context;

    public ClickableItemViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public void init(ChatRoomItemContext context) {
        this.context = context;

        setOnClickItemListener();
    }

    private void setOnClickItemListener() {
        itemView.setOnClickListener(view -> {
            goToChatRoomDetailFragment();
        });
    }

    private void goToChatRoomDetailFragment() {
        if (context.chatRoom.type == ChatRoomType.PUBLIC) {
            request(new RequestChatMessagesReadForm(context.chatRoom.id, context.chatRoom.type, null));
            return;
        }

        View dialogView = View.inflate(context.activity, R.layout.dialog_chat_room_password, null);
        showPasswordInputDialog(dialogView, (DialogInterface dialogInterface, int i) -> {
            EditText editPassword = dialogView.findViewById(R.id.dialogPassword);
            String password = editPassword.getText().toString();

            request(new RequestChatMessagesReadForm(context.chatRoom.id, context.chatRoom.type, password));
        });
    }

    private void request(RequestChatMessagesReadForm form) {
        context.global.http.getChatMessageList(form, res -> {
            if (!res.isSuccessful()) {
                showFailureAlertDialog();
                return;
            }

            context.global.chat.curChatMessages = res.body();
            context.global.chat.curChatRoom = context.chatRoom;

            context.itemList.clear();
            context.activity.navigator.navigate(FragmentEnum.CHAT_ROOM_DETAIL);
        });
    }

    private void showPasswordInputDialog(View dialogView, DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(context.activity)
                .setTitle("비밀번호 입력")
                .setView(dialogView)
                .setPositiveButton("확인", callback)
                .setNegativeButton("취소", null)
                .show();
    }

    private void showFailureAlertDialog() {
        new AlertDialog.Builder(context.activity)
                .setTitle("알림")
                .setMessage("비밀번호가 틀립니다")
                .setPositiveButton("확인", null)
                .show();
    }
}
