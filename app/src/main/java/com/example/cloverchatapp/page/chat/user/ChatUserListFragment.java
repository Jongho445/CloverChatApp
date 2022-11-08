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
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;

import java.io.IOException;
import java.util.List;

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
        rvHolder.clearList();
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
        });
    }
}
