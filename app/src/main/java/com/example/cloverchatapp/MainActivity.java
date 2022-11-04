package com.example.cloverchatapp;

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
import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.util.TestHelper;
import com.example.cloverchatapp.web.board.ResponseChatRoom;

public class MainActivity extends AppCompatActivity {

    IndexFragment indexFragment;
    ChatRoomCreateFragment chatRoomCreateFragment;
    ChatRoomDetailFragment chatRoomDetailFragment;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    TestFragment testFragment;

    FragmentEnum mainFragment;
    public AuthStorage authStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authStorage = new AuthStorage();

        if (BuildConfig.BUILD_TYPE == "debug") {
            TestHelper testHelper = new TestHelper(this);
            testHelper.login(this::init);
        } else {
            init();
        }
    }

    @Override
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

    private void init() {
        FragmentManager fm = getSupportFragmentManager();

        indexFragment = new IndexFragment();
        signInFragment = new SignInFragment();
        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        signUpFragment = new SignUpFragment();
        testFragment = new TestFragment();

        if (authStorage.me == null) {
            setTitle("로그인");
            fm.beginTransaction()
                    .add(R.id.action_container, signInFragment)
                    .commit();
        } else {
            setTitle("채팅방 리스트");
            fm.beginTransaction()
                    .add(R.id.action_container, indexFragment)
                    .commit();
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
        setTitle(fragmentEnum);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_container, fragment)
                .commit();
    }

    private void setTitle(FragmentEnum fragment) {
        switch (fragment) {
            case INDEX:
                setTitle("채팅방 리스트"); break;
            case CHAT_ROOM_CREATE:
                setTitle("채팅방 만들기"); break;
            case SIGN_IN:
                setTitle("로그인"); break;
            case SIGN_UP:
                setTitle("회원가입"); break;
            case TEST:
                setTitle("테스트 페이지"); break;
        }
    }
}