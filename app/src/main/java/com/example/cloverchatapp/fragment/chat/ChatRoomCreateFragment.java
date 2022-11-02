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
    EditText inputCreateBy;
    EditText inputPassword;
    EditText inputTitle;

    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        httpClient = new AppClient();
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_create, container, false);

        initEditTexts();

        setToIndexBtn();
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
        inputCreateBy = rootView.findViewById(R.id.inputCreateBy);
        inputPassword = rootView.findViewById(R.id.inputPassword);
        inputTitle = rootView.findViewById(R.id.inputTitle);
    }

    private void clearInputs() {
        inputCreateBy.setText(null);
        inputPassword.setText(null);
        inputTitle.setText(null);
    }

    private void setToIndexBtn() {
        Button createToIndexBtn = rootView.findViewById(R.id.createToIndexBtn);
        createToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.INDEX);
        });
    }

    private void setCreateBtn() {
        Button createBtn = rootView.findViewById(R.id.createChatRoomBtn);
        createBtn.setOnClickListener((View v) -> {
            ChatRoomCreateForm chatRoomCreateForm = new ChatRoomCreateForm(
                    inputCreateBy.getText().toString(),
                    inputPassword.getText().toString(),
                    inputTitle.getText().toString(),
                    ChatRoomType.PUBLIC
            );

            httpClient.createChatRoom(
                    chatRoomCreateForm,
                    res -> activity.navigate(FragmentEnum.INDEX),
                    t -> {}
            );
        });
    }
}