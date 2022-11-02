package com.example.cloverchatapp.fragment.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.component.ChatRoomAdapter;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;
    ListView chatRoomListView;
    List<ResponseChatRoom> list = new ArrayList<>();
    AppClient client = new AppClient();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment_xml를 MainFragment.java와 묶어주는 역할을 하는 메서드
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);

        setIndexToSignInBtn();
        setIndexToTestBtn();
        setIndexToWriteBtn();
        setPostListView();

        requestAndRender();

        return rootView;
    }

    @MainThread
    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        if (activity.authStorage.me == null) {
            Button indexToSignInBtn = rootView.findViewById(R.id.indexToSignInBtn);
            indexToSignInBtn.setVisibility(View.VISIBLE);
        }
    }

    private void requestAndRender() {
        client.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            if (chatRooms == null) {
                throw new RuntimeException("null");
            }

            setPostAdapter(chatRooms);
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

    private void setPostListView() {
        chatRoomListView = rootView.findViewById(R.id.chatRoomListView);

        chatRoomListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            ResponseChatRoom chatRoom = list.get(i);
            System.out.println(chatRoom.title);
            list.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_DETAIL);
        });
    }

    private void setPostAdapter(List<ResponseChatRoom> chatRooms) {
        ChatRoomAdapter adapter = new ChatRoomAdapter(activity, super.getContext(), list);

        for (ResponseChatRoom chatRoom : chatRooms) {
            System.out.println(chatRoom.title);
            list.add(chatRoom);
        }

        chatRoomListView.setAdapter(adapter);
    }
}