package com.example.cloverchatapp.fragment;

import android.content.Context;
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
import com.example.cloverchatapp.web.RequestChatRoom;

public class ChatRoomCreateFragment extends Fragment {

    MainActivity activity;

    ViewGroup rootView;
    EditText inputUsername;
    EditText inputPassword;
    EditText inputTitle;

    AppClient client = new AppClient();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            clearInputs();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_create, container, false);

        initEditTexts();

        setToIndexBtn();
        setUploadBtn();

        return rootView;
    }

    private void initEditTexts() {
        inputUsername = rootView.findViewById(R.id.inputUsername);
        inputPassword = rootView.findViewById(R.id.inputPassword);
        inputTitle = rootView.findViewById(R.id.inputTitle);
    }

    private void clearInputs() {
        inputUsername.setText(null);
        inputPassword.setText(null);
        inputTitle.setText(null);
    }

    private void setToIndexBtn() {
        Button writeToIndexBtn = rootView.findViewById(R.id.writeToIndexBtn);
        writeToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(MainFragment.INDEX);
        });
    }

    private void setUploadBtn() {
        Button uploadBtn = rootView.findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener((View v) -> {
            RequestChatRoom requestChatRoom = new RequestChatRoom(
                    inputUsername.getText().toString(),
                    inputPassword.getText().toString(),
                    inputTitle.getText().toString()
            );

            client.upload(
                    requestChatRoom,
                    res -> activity.navigate(MainFragment.INDEX),
                    t -> {}
            );
        });
    }
}