package com.example.cloverchatapp.component.button;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.board.ChatRoomCreateForm;
import com.example.cloverchatapp.web.domain.board.ChatRoomType;

public class ChatRoomCreateButton {

    private final MainActivity activity;
    private final EditText inputPassword;
    private final EditText inputTitle;
    private final CheckBox isPrivateChkBox;

    private final Button targetButton;

    public final HttpClient httpClient;

    public ChatRoomCreateButton(MainActivity activity, ViewGroup rootView, EditText inputPassword, EditText inputTitle, CheckBox isPrivateChkBox) {
        this.activity = activity;
        this.inputPassword = inputPassword;
        this.inputTitle = inputTitle;
        this.isPrivateChkBox = isPrivateChkBox;

        this.httpClient = new HttpClient(activity.authStorage);
        this.targetButton = rootView.findViewById(R.id.createChatRoomBtn);

        setOnClickListener();
    }

    private void setOnClickListener() {
        targetButton.setOnClickListener((View v) -> {
            ChatRoomCreateForm chatRoomCreateForm = new ChatRoomCreateForm(
                    activity.authStorage.me.id,
                    inputPassword.getText().toString(),
                    inputTitle.getText().toString(),
                    getCurChatRoomType()
            );

            httpClient.createChatRoom(chatRoomCreateForm, res -> {
                activity.navigate(FragmentEnum.INDEX);
            });
        });
    }

    private ChatRoomType getCurChatRoomType() {
        if (isPrivateChkBox.isChecked()) {
            return ChatRoomType.PRIVATE;
        }

        return ChatRoomType.PUBLIC;
    }
}
