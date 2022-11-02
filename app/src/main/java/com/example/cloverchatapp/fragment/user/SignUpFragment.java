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
import com.example.cloverchatapp.fragment.FragmentEnum;

public class SignUpFragment extends Fragment {

    MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpToIndexBtn = rootView.findViewById(R.id.signUpToIndexBtn);
        signUpToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.INDEX);
        });

        return rootView;
    }
}
