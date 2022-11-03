package com.example.cloverchatapp.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.board.ChatRoomCreateForm;
import com.example.cloverchatapp.web.board.ChatRoomType;

public class ChatRoomCreateFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    EditText inputPassword;
    EditText inputTitle;

    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_create, container, false);
        httpClient = new AppClient(activity.authStorage);

        initEditTexts();

        setCreateToIndexBtn();
        setCreateBtn();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            clearInputs();
        }
    }

    private void initEditTexts() {
        inputPassword = rootView.findViewById(R.id.inputPassword);
        inputTitle = rootView.findViewById(R.id.inputTitle);
    }

    private void clearInputs() {
        inputPassword.setText(null);
        inputTitle.setText(null);
    }

    private void setCreateToIndexBtn() {
        Button createToIndexBtn = rootView.findViewById(R.id.createToIndexBtn);
        createToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.INDEX);
        });
    }

    private void setCreateBtn() {
        Button createBtn = rootView.findViewById(R.id.createChatRoomBtn);
        createBtn.setOnClickListener((View v) -> {
            ChatRoomCreateForm chatRoomCreateForm = new ChatRoomCreateForm(
                    activity.authStorage.me.id,
                    inputPassword.getText().toString(),
                    inputTitle.getText().toString(),
                    ChatRoomType.PUBLIC
            );

            httpClient.createChatRoom(
                    chatRoomCreateForm,
                    res -> activity.navigate(FragmentEnum.INDEX),
                    e -> {}
            );
        });
    }
}