package com.example.cloverchatapp.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    ListView chatRoomListView;
    List<ResponseChatRoom> list = new ArrayList<>();
    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        httpClient = new AppClient();
        activity = (MainActivity) getActivity();
        //fragment_xml를 MainFragment.java와 묶어주는 역할을 하는 메서드
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);

        setIndexToSignInBtn();
        setIndexToTestBtn();
        setIndexToWriteBtn();

        requestAndRender();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.authStorage.me == null) {
            Button indexToSignInBtn = rootView.findViewById(R.id.indexToSignInBtn);
            indexToSignInBtn.setVisibility(View.VISIBLE);
        }
    }

    private void requestAndRender() {
        httpClient.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            if (chatRooms == null) {
                throw new RuntimeException("null");
            }

            setListView(chatRooms);
        }, t -> {
            System.out.println(t.getMessage());
            t.printStackTrace();
        });
    }

    private void setIndexToSignInBtn() {
        Button indexToSignInBtn = rootView.findViewById(R.id.indexToSignInBtn);

        indexToSignInBtn.setOnClickListener(view -> {
            list.clear();
            activity.navigate(FragmentEnum.SIGN_IN);
        });
    }

    private void setIndexToWriteBtn() {
        //fragment_main.xml에 접근하기위해서는 rootView. 으로 접근해야한다
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);

        indexToCreateBtn.setOnClickListener(view -> {
            list.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }

    private void setIndexToTestBtn() {
        Button indexToTestBtn = rootView.findViewById(R.id.indexToTestBtn);

        indexToTestBtn.setOnClickListener(view -> {
            list.clear();
            activity.navigate(FragmentEnum.TEST);
        });
    }

    private void setListView(List<ResponseChatRoom> chatRooms) {
        chatRoomListView = rootView.findViewById(R.id.chatRoomListView);

        ChatRoomAdapter adapter = new ChatRoomAdapter(activity, super.getContext(), list);

        for (ResponseChatRoom chatRoom : chatRooms) {
            list.add(chatRoom);
        }

        chatRoomListView.setAdapter(adapter);
    }
}