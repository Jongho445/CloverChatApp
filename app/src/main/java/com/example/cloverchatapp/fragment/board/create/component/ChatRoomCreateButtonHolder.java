package com.example.cloverchatapp.fragment.board.create.component;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.board.RequestChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;

public class ChatRoomCreateButtonHolder {

    private final MainActivity activity;
    private final GlobalContext global;

    private final EditText inputPassword;
    private final EditText inputTitle;
    private final CheckBox isPrivateChkBox;

    private final Button targetButton;

    private final BoardHttpClient boardHttpClient;

    public ChatRoomCreateButtonHolder(MainActivity activity, ViewGroup rootView, EditText inputPassword, EditText inputTitle, CheckBox isPrivateChkBox) {
        this.activity = activity;
        this.global = activity.global;
        this.inputPassword = inputPassword;
        this.inputTitle = inputTitle;
        this.isPrivateChkBox = isPrivateChkBox;

        this.boardHttpClient = new BoardHttpClient(global.auth);
        this.targetButton = rootView.findViewById(R.id.createChatRoomBtn);

        setOnClickListener();
    }

    private void setOnClickListener() {
        targetButton.setOnClickListener((View v) -> {
            RequestChatRoomCreateForm requestChatRoomCreateForm = new RequestChatRoomCreateForm(
                    global.auth.myInfo.id,
                    getPassword(),
                    inputTitle.getText().toString(),
                    getCurChatRoomType()
            );

            boardHttpClient.createChatRoom(requestChatRoomCreateForm, res -> {
                activity.navigator.navigate(FragmentEnum.CHAT_ROOM_LIST);
            });
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
