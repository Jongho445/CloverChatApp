package com.example.cloverchatapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.component.ChatRoomAdapter;
import com.example.cloverchatapp.web.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;
    ListView postListView;
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

        setIndexToTestBtn();
        setIndexToWriteBtn();
        setPostListView();

        requestAndRender();

        return rootView;
    }

    private void requestAndRender() {
        client.getPosts(res -> {
            List<ResponseChatRoom> posts = res.body();

            if (posts == null) {
                throw new RuntimeException("null");
            }

            setPostAdapter(posts);
        }, t -> {
            System.out.println(t.getMessage());
            t.printStackTrace();
        });
    }

    private void setIndexToWriteBtn() {
        //fragment_main.xml에 접근하기위해서는 rootView. 으로 접근해야한다
        Button indexToWriteBtn = rootView.findViewById(R.id.indexToWriteBtn);

        indexToWriteBtn.setOnClickListener(view -> {
            list.clear();
            activity.navigate(MainFragment.CHAT_ROOM_CREATE);
        });
    }

    private void setIndexToTestBtn() {
        Button indexToTestBtn = rootView.findViewById(R.id.indexToTestBtn);

        indexToTestBtn.setOnClickListener(view -> {
            list.clear();
            activity.navigate(MainFragment.TEST);
        });
    }

    private void setPostListView() {
        postListView = rootView.findViewById(R.id.postListView);

        postListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            ResponseChatRoom post = (ResponseChatRoom) list.get(i);
            System.out.println(post.getTitle());
            list.clear();
            activity.navigate(MainFragment.CHAT_ROOM_DETAIL);
        });
    }

    private void setPostAdapter(List<ResponseChatRoom> posts) {
        ChatRoomAdapter adapter = new ChatRoomAdapter(super.getContext(), list);

        for (ResponseChatRoom post : posts) {
            System.out.println(post.getTitle());
            list.add(post);
        }

        postListView.setAdapter(adapter);
    }
}