package com.example.cloverchatapp.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.page.board.create.ChatRoomCreateFragment;
import com.example.cloverchatapp.page.board.list.ChatRoomListFragment;
import com.example.cloverchatapp.page.chat.detail.ChatRoomDetailFragment;
import com.example.cloverchatapp.page.chat.user.ChatUserListFragment;
import com.example.cloverchatapp.page.user.signin.SignInFragment;
import com.example.cloverchatapp.page.user.signup.SignUpFragment;

public class Navigator {

    private final MainActivity activity;

    private final ChatRoomListFragment chatRoomListFragment;
    private final ChatRoomCreateFragment chatRoomCreateFragment;
    private final ChatRoomDetailFragment chatRoomDetailFragment;
    private final ChatUserListFragment chatUserListFragment;
    private final SignInFragment signInFragment;
    private final SignUpFragment signUpFragment;

    public Navigator(MainActivity activity) {
        this.activity = activity;

        chatRoomListFragment = new ChatRoomListFragment();
        signInFragment = new SignInFragment();
        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        chatUserListFragment = new ChatUserListFragment();
        signUpFragment = new SignUpFragment();
    }

    public void navigate(FragmentEnum fragment) {
        switch (fragment) {
            case CHAT_ROOM_LIST:
                navigate(chatRoomListFragment, FragmentEnum.CHAT_ROOM_LIST); break;
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
                .add(R.id.action_container, chatRoomListFragment)
                .commit();
    }

    private void navigate(Fragment fragment, FragmentEnum fragmentEnum) {
        activity.global.mainFragment = fragmentEnum;
        activity.setTitle(fragmentEnum);

        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_container, fragment)
                .commit();
    }
}
