package com.example.cloverchatapp.page.board.create;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.board.RequestChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;

public class ChatRoomCreateButtonOnClickListener implements View.OnClickListener {

    private final MainActivity activity;
    private final GlobalContext global;

    private final EditText inputPassword;
    private final EditText inputTitle;
    private final CheckBox isPrivateChkBox;


    private final BoardHttpClient boardHttpClient;

    public ChatRoomCreateButtonOnClickListener(MainActivity activity, EditText inputPassword, EditText inputTitle, CheckBox isPrivateChkBox) {
        this.activity = activity;
        this.global = activity.global;
        this.inputPassword = inputPassword;
        this.inputTitle = inputTitle;
        this.isPrivateChkBox = isPrivateChkBox;

        this.boardHttpClient = new BoardHttpClient(global.auth);
    }

    @Override
    public void onClick(View view) {
        RequestChatRoomCreateForm requestChatRoomCreateForm = new RequestChatRoomCreateForm(
                global.auth.myInfo.id,
                getPassword(),
                inputTitle.getText().toString(),
                getCurChatRoomType()
        );

        boardHttpClient.createChatRoom(requestChatRoomCreateForm, res -> {
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_LIST);
        });
    }

    private String getPassword() {
        if (isPrivateChkBox.isChecked()) {
            return inputPassword.getText().toString();
        }

        return null;
    }

    private ChatRoomType getCurChatRoomType() {
        if (isPrivateChkBox.isChecked()) {
            return ChatRoomType.PRIVATE;
        }

        return ChatRoomType.PUBLIC;
    }
}
