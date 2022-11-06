package com.example.cloverchatapp.fragment.board.list;

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
import com.example.cloverchatapp.fragment.board.list.component.ChatRoomList;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomListFragment extends Fragment {

    MainActivity activity;
    GlobalContext global;

    ChatRoomList chatRoomList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);
        activity = (MainActivity) getActivity();
        global = activity.global;

        setIndexToWriteBtn(rootView);

        global.http.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            chatRoomList = new ChatRoomList(activity, rootView, chatRooms);

            if (global.ws != null) {
                global.ws.disconnect();
                global.ws = null;
            }
        });

        return rootView;
    }

    public void onResume() {
        super.onResume();
        global.menu.findItem(R.id.chatUsersBtn).setVisible(false);
    }

    private void setIndexToWriteBtn(ViewGroup rootView) {
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);
        indexToCreateBtn.setOnClickListener(view -> {
            chatRoomList.clearList();
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }
}