package com.example.cloverchatapp.fragment.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.component.recyclerview.chatroom.ChatRoomList;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class IndexFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    ChatRoomList chatRoomList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);

        activity.menu.findItem(R.id.chatUsersBtn).setVisible(false);

        setIndexToTestBtn();
        setIndexToWriteBtn();

        activity.httpClient.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            chatRoomList = new ChatRoomList(activity, rootView, chatRooms);

            if (activity.webSocketClient != null) {
                activity.webSocketClient.disconnect();
                activity.webSocketClient = null;
            }
        });

        return rootView;
    }

    private void setIndexToWriteBtn() {
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);
        indexToCreateBtn.setOnClickListener(view -> {
            chatRoomList.clearList();
            activity.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }

    private void setIndexToTestBtn() {
        Button indexToTestBtn = rootView.findViewById(R.id.indexToTestBtn);
        indexToTestBtn.setOnClickListener(view -> {
            chatRoomList.clearList();
            activity.navigate(FragmentEnum.TEST);
        });
    }
}