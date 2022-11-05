package com.example.cloverchatapp.fragment.chatroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;

import java.io.IOException;
import java.util.List;

public class ChatUserListFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_user_list, container, false);

        activity.menu.findItem(R.id.chatUsersBtn).setVisible(false);

        getChatUserList();

        return rootView;
    }

    public void getChatUserList() {
        activity.httpClient.getChatUserList(activity.curChatRoom.id, res -> {
            if (!res.isSuccessful()) {
                try {
                    String string = res.errorBody().string();
                    System.out.println(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            List<ResponseChatUser> chatUserList = res.body();
            for (ResponseChatUser chatUser : chatUserList) {
                System.out.println(chatUser.user.nickname);
            }
        });
    }
}
