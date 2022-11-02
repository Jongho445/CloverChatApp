package com.example.cloverchatapp.fragment.user;

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
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.user.RequestLoginForm;
import com.example.cloverchatapp.web.user.ResponseUser;

public class SignInFragment extends Fragment {

    MainActivity activity;
    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        httpClient = new AppClient();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);

        Button signInBtn = rootView.findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(view -> {
            httpClient.login(
                    new RequestLoginForm("user1@gmail.com", "1234"),
                    res -> {
                        ResponseUser responseUser = res.body();
                        String jSessionId = activity.authStorage.getJSessionId(res.headers());

                        activity.authStorage.sessionKey = jSessionId;
                        activity.authStorage.me = responseUser;

                        activity.navigate(FragmentEnum.INDEX);
                    },
                    e -> {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
            );
        });

        Button signInToSignUpBtn = rootView.findViewById(R.id.signInToSignUpBtn);
        signInToSignUpBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.SIGN_UP);
        });

        Button signInToIndexBtn = rootView.findViewById(R.id.signInToIndexBtn);
        signInToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.INDEX);
        });

        return rootView;
    }
}
