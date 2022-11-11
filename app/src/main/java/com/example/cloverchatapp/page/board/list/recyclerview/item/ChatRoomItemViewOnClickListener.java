package com.example.cloverchatapp.page.board.list.recyclerview.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.cloverchatapp.R;
import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.util.DialogRenderer;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;

import java.util.List;

public class ChatRoomItemViewOnClickListener implements View.OnClickListener {

    private final ChatRoomItemContext context;
    private final ChatHttpClient chatHttpClient;

    public ChatRoomItemViewOnClickListener(ChatRoomItemContext context) {
        this.context = context;
        this.chatHttpClient = new ChatHttpClient(context.global.auth);
    }

    @Override
    public void onClick(View view) {
        goToChatRoomDetailFragment();
    }

    public void goToChatRoomDetailFragment() {
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
        chatHttpClient.getChatMessageList(form, res -> {
            if (!res.isSuccessful()) {
                DialogRenderer.showAlertDialog(context.activity, "비밀번호가 틀립니다");
                return;
            }

            setGlobalContext(res.body());

            context.itemList.clear();
            context.activity.navigator.navigate(FragmentEnum.CHAT_ROOM_DETAIL);
        });
    }

    private void setGlobalContext(List<ResponseStompChatMessage> chatMessages) {
        context.global.chat.curChatMessages = chatMessages;
        context.global.chat.curChatRoom = context.chatRoom;
    }

    private void showPasswordInputDialog(View dialogView, DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(context.activity)
                .setTitle("비밀번호 입력")
                .setView(dialogView)
                .setPositiveButton("확인", callback)
                .setNegativeButton("취소", null)
                .show();
    }
}
