package com.example.cloverchatapp.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.fragment.board.ChatRoomCreateFragment;
import com.example.cloverchatapp.fragment.board.IndexFragment;
import com.example.cloverchatapp.fragment.chatroom.ChatRoomDetailFragment;
import com.example.cloverchatapp.fragment.chatroom.ChatUserListFragment;
import com.example.cloverchatapp.fragment.user.SignInFragment;
import com.example.cloverchatapp.fragment.user.SignUpFragment;

public class Navigator {

    private final MainActivity activity;

    private IndexFragment indexFragment;
    private ChatRoomCreateFragment chatRoomCreateFragment;
    private ChatRoomDetailFragment chatRoomDetailFragment;
    private ChatUserListFragment chatUserListFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    public Navigator(MainActivity activity) {
        this.activity = activity;

        indexFragment = new IndexFragment();
        signInFragment = new SignInFragment();
        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        chatUserListFragment = new ChatUserListFragment();
        signUpFragment = new SignUpFragment();
    }

    public void navigate(FragmentEnum fragment) {
        switch (fragment) {
            case INDEX:
                navigate(indexFragment, FragmentEnum.INDEX); break;
            case CHAT_ROOM_CREATE:
                navigate(chatRoomCreateFragment, FragmentEnum.CHAT_ROOM_CREATE); break;
            case CHAT_ROOM_DETAIL:
                navigate(chatRoomDetailFragment, FragmentEnum.CHAT_ROOM_DETAIL); break;
            case CHAT_USER_LIST:
                navigate(chatUserListFragment, FragmentEnum.CHAT_USER_LIST); break;
            case SIGN_IN:
                navigate(signInFragment, FragmentEnum.SIGN_IN); break;
            case SIGN_UP:
                navigate(signUpFragment, FragmentEnum.SIGN_UP); break;
        }
    }

    public void goToLogin() {
        FragmentManager fm = activity.getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.action_container, signInFragment)
                .commit();
    }

    public void goToIndex() {
        FragmentManager fm = activity.getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.action_container, indexFragment)
                .commit();
    }

    private void navigate(Fragment fragment, FragmentEnum fragmentEnum) {
        activity.mainFragment = fragmentEnum;
        activity.setTitle(fragmentEnum);

        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_container, fragment)
                .commit();
    }
}
