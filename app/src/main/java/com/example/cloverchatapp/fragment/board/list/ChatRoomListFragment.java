package com.example.cloverchatapp.fragment.board.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;

import java.util.List;

public class ChatRoomListFragment extends Fragment {

    private MainActivity activity;
    private GlobalContext global;

    private RecyclerView recyclerView;
    private ChatRoomRecyclerViewModel chatRoomRecyclerViewModel;

    private BoardHttpClient boardHttpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);
        this.activity = (MainActivity) getActivity();
        this.global = activity.global;
        this.boardHttpClient = new BoardHttpClient(global.auth);
        this.recyclerView = rootView.findViewById(R.id.chatRoomListView);

        setIndexToWriteBtn(rootView);

        boardHttpClient.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            chatRoomRecyclerViewModel = new ChatRoomRecyclerViewModel(activity, recyclerView, chatRooms);

            if (global.ws.messageSession != null) {
                global.ws.messageSession.disconnect();
                global.ws.messageSession = null;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        global.menu.findItem(R.id.chatUsersBtn).setVisible(false);
    }

    private void setIndexToWriteBtn(ViewGroup rootView) {
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);
        indexToCreateBtn.setOnClickListener(view -> {
            chatRoomRecyclerViewModel.clearList();
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }
}