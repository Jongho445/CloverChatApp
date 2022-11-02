package com.example.cloverchatapp;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.cloverchatapp.fragment.chat.IndexFragment;
import com.example.cloverchatapp.fragment.chat.ChatRoomDetailFragment;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.fragment.TestFragment;
import com.example.cloverchatapp.fragment.chat.ChatRoomCreateFragment;
import com.example.cloverchatapp.fragment.user.SignInFragment;
import com.example.cloverchatapp.fragment.user.SignUpFragment;
import com.example.cloverchatapp.web.ResponseChatRoom;

public class MainActivity extends AppCompatActivity {

    IndexFragment indexFragment;
    ChatRoomCreateFragment chatRoomCreateFragment;
    ChatRoomDetailFragment chatRoomDetailFragment;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    TestFragment testFragment;

    FragmentEnum mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
    }

    @Override
    @MainThread
    public void onBackPressed() {
        switch (mainFragment) {
            case INDEX:
                super.onBackPressed(); break;
            case CHAT_ROOM_CREATE:
            case CHAT_ROOM_DETAIL:
            case SIGN_IN:
            case SIGN_UP:
            case TEST:
                navigate(indexFragment, FragmentEnum.INDEX); break;
        }
    }

    public void navigate(FragmentEnum fragment, ResponseChatRoom responseChatRoom) {
        switch (fragment) {
            case CHAT_ROOM_DETAIL:
                chatRoomDetailFragment.setChatRoom(responseChatRoom);
                navigate(chatRoomDetailFragment, FragmentEnum.CHAT_ROOM_DETAIL); break;
        }
    }

    public void navigate(FragmentEnum fragment) {
        switch (fragment) {
            case INDEX:
                navigate(indexFragment, FragmentEnum.INDEX); break;
            case CHAT_ROOM_CREATE:
                navigate(chatRoomCreateFragment, FragmentEnum.CHAT_ROOM_CREATE); break;
            case SIGN_IN:
                navigate(signInFragment, FragmentEnum.SIGN_IN); break;
            case SIGN_UP:
                navigate(signUpFragment, FragmentEnum.SIGN_UP); break;
            case TEST:
                navigate(testFragment, FragmentEnum.TEST); break;
        }
    }

    private void navigate(Fragment fragment, FragmentEnum fragmentEnum) {
        mainFragment = fragmentEnum;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_container, fragment)
                .commit();
    }

    private void initFragments() {
        FragmentManager fm = getSupportFragmentManager();
        indexFragment = (IndexFragment) fm.findFragmentById(R.id.mainFragment);

        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        testFragment = new TestFragment();
    }
}