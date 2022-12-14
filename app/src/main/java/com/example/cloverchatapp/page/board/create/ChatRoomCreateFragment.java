package com.example.cloverchatapp.page.board.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.page.FragmentEnum;

public class ChatRoomCreateFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;

    private EditText inputPassword;
    private EditText inputTitle;
    private CheckBox isPrivateChkBox;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initFields(inflater, container);

        setCreateToIndexBtn();
        setIsPrivateChkBox();

        setCreateBtnListener();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (rootView != null) {
            clearInputs();
        }
    }

    private void initFields(LayoutInflater inflater, ViewGroup container) {
        this.activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_create, container, false);

        this.inputPassword = rootView.findViewById(R.id.inputPassword);
        this.inputTitle = rootView.findViewById(R.id.inputTitle);
    }

    private void clearInputs() {
        inputPassword.setText(null);
        inputTitle.setText(null);
        isPrivateChkBox.setChecked(false);
    }

    private void setCreateToIndexBtn() {
        Button createToIndexBtn = rootView.findViewById(R.id.createToIndexBtn);
        createToIndexBtn.setOnClickListener((View v) -> {
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_LIST);
        });
    }

    private void setIsPrivateChkBox() {
        isPrivateChkBox = rootView.findViewById(R.id.isPrivateChkBox);
        isPrivateChkBox.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {
            inputPassword.setEnabled(isChecked);
        });
    }

    private void setCreateBtnListener() {
        Button createBtn = rootView.findViewById(R.id.createChatRoomBtn);
        createBtn.setOnClickListener(new ChatRoomCreateButtonOnClickListener(
                activity, inputPassword, inputTitle, isPrivateChkBox
        ));
    }
}