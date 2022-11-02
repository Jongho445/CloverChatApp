package com.example.cloverchatapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.web.RequestChatRoom;

public class TestFragment extends Fragment {

    MainActivity activity;
    TextView textView;
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test, container, false);

        textView = rootView.findViewById(R.id.testResult);

        Button testToIndexBtn = rootView.findViewById(R.id.testToIndexBtn);
        testToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(MainFragment.INDEX);
        });

        Button RetTestBtn = rootView.findViewById(R.id.RetTestBtn);
        RetTestBtn.setOnClickListener((View v) -> {
            testRequest();
        });

        return rootView;
    }

    private void testRequest() {
        RequestChatRoom requestChatRoom = new RequestChatRoom("testUser", "1234", "testTitle");
        client.upload(
                requestChatRoom,
                res -> {
                    System.out.println("end");
                },
                t -> {
                    System.out.println(t.getMessage());
                    t.printStackTrace();
                }
        );
    }
}
