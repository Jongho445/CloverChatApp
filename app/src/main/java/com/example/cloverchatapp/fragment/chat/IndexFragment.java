package com.example.cloverchatapp.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.component.ChatRoomAdapter;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.board.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    RecyclerView chatRoomListView;
    ChatRoomAdapter adapter;
    List<ResponseChatRoom> itemList;

    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        //fragment_xml를 MainFragment.java와 묶어주는 역할을 하는 메서드
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);
        httpClient = new AppClient(activity.authStorage);

        setIndexToTestBtn();
        setIndexToWriteBtn();

        requestAndRender();

        return rootView;
    }

    private void requestAndRender() {
        httpClient.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            if (chatRooms == null) {
                throw new RuntimeException("null");
            }

            setRecyclerView(chatRooms);
        }, e -> {
            System.out.println(e.getMessage());
            e.printStackTrace();
        });
    }

    private void setRecyclerView(List<ResponseChatRoom> chatRooms) {
        chatRoomListView = rootView.findViewById(R.id.chatRoomListView);
        itemList = new ArrayList<>();
        adapter = new ChatRoomAdapter(itemList, activity);

        for (ResponseChatRoom chatRoom : chatRooms) {
            itemList.add(chatRoom);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        chatRoomListView.setLayoutManager(layoutManager);
        chatRoomListView.setAdapter(adapter);
    }

    private void setIndexToWriteBtn() {
        //fragment_main.xml에 접근하기위해서는 rootView. 으로 접근해야한다
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);

        indexToCreateBtn.setOnClickListener(view -> {
            itemList.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }

    private void setIndexToTestBtn() {
        Button indexToTestBtn = rootView.findViewById(R.id.indexToTestBtn);

        indexToTestBtn.setOnClickListener(view -> {
            itemList.clear();
            activity.navigate(FragmentEnum.TEST);
        });
    }
}