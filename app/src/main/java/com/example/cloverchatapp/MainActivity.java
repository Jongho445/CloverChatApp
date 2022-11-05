package com.example.cloverchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cloverchatapp.fragment.board.IndexFragment;
import com.example.cloverchatapp.fragment.chatroom.ChatRoomDetailFragment;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.fragment.TestFragment;
import com.example.cloverchatapp.fragment.board.ChatRoomCreateFragment;
import com.example.cloverchatapp.fragment.chatroom.ChatUserListFragment;
import com.example.cloverchatapp.fragment.user.SignInFragment;
import com.example.cloverchatapp.fragment.user.SignUpFragment;
import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.util.TestHelper;
import com.example.cloverchatapp.web.client.WebSocketClient;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

public class MainActivity extends AppCompatActivity {

    private IndexFragment indexFragment;
    private ChatRoomCreateFragment chatRoomCreateFragment;
    private ChatRoomDetailFragment chatRoomDetailFragment;
    private ChatUserListFragment chatUserListFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private TestFragment testFragment;

    private FragmentEnum mainFragment;
    public Menu menu;

    public WebSocketClient webSocketClient = null;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.chatUsersBtn).setVisible(false);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myStatusBtn:
                System.out.println("Status");
                break;
            case R.id.chatUsersBtn:
                navigate(FragmentEnum.CHAT_USER_LIST, chatRoomDetailFragment.getChatRoom());
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        switch (mainFragment) {
            case INDEX:
            case SIGN_IN:
                super.onBackPressed(); break;
            case SIGN_UP:
                navigate(FragmentEnum.SIGN_IN); break;
            case CHAT_USER_LIST:
                navigate(FragmentEnum.CHAT_ROOM_DETAIL); break;
            case CHAT_ROOM_CREATE:
            case CHAT_ROOM_DETAIL:
            case TEST:
                navigate(FragmentEnum.INDEX); break;
        }
    }

    private void init() {
        FragmentManager fm = getSupportFragmentManager();

        indexFragment = new IndexFragment();
        signInFragment = new SignInFragment();
        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        chatUserListFragment = new ChatUserListFragment();
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
            case CHAT_USER_LIST:
                chatUserListFragment.setChatRoom(responseChatRoom);
                navigate(chatUserListFragment, FragmentEnum.CHAT_USER_LIST); break;
        }
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