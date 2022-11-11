package com.example.cloverchatapp.page.chat.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.page.chat.user.recyclerview.ChatUserRecyclerViewHolder;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;
import com.example.cloverchatapp.web.domain.chat.StompUpdateChatUser;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatUserListFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;
    private GlobalContext global;

    private ChatUserRecyclerViewHolder rvHolder;

    private ChatHttpClient chatHttpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.activity = (MainActivity) getActivity();
        this.global = activity.global;
        this.chatHttpClient = new ChatHttpClient(global.auth);
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_user_list, container, false);

        this.global.menu.findItem(R.id.chatUsersBtn).setVisible(false);

        getChatUserList();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (rvHolder != null) {
            rvHolder.clearList();
        }
    }

    public void getChatUserList() {
        chatHttpClient.getChatUserList(global.chat.curChatRoom.id, res -> {
            if (!res.isSuccessful()) {
                try {
                    String string = res.errorBody().string();
                    System.out.println(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            List<ResponseChatUser> chatUsers = res.body();
            this.rvHolder = new ChatUserRecyclerViewHolder(activity, rootView, chatUsers);

            setChatUserSessionListener();
        });
    }

    private void setChatUserSessionListener() {
        if (global.ws.chatUserSession == null) {
            return;
        }

        global.ws.chatUserSession.subscribeChatUser((StompMessage topicMessage) -> {
            StompUpdateChatUser stompForm = new Gson().fromJson(
                    topicMessage.getPayload(),
                    StompUpdateChatUser.class
            );

            switch (stompForm.type) {
                case CREATE:
                    rvHolder.addItem(stompForm.chatUser); break;
                case UPDATE:
                    break;
                case DELETE:
                    rvHolder.removeItem(stompForm.chatUser); break;
            }
        });
    }
}
